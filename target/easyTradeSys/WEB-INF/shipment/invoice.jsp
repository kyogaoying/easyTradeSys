<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="copyright" content="All Rights Reserved, Copyright (C) 2017, Sally, Ltd." />
<title>入库单管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/1.3.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/wu.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/icon.css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/header.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/tableList.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/dividePage.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/selectList.css" type="text/css">
<!-- 导入的jsp,实质是js,改成jsp的目的是为了在js中使用el表达式,头部的page一定要加,不然会乱码 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/selectList.jsp"></script>
</head>
<body class="easyui-layout">
   <div class="easyui-layout" data-options="fit:true">
	   <!-- <div data-options="region:'west',border:true,split:true," title="分类管理" style="width:150px; padding:5px;">
	      <ul id="wu-category-tree" class="easyui-tree"></ul>
	   </div> -->
    	   <div data-options="region:'center',border:false">
    	<!-- Begin of toolbar -->
	       <div id="wu-toolbar">
	            <div class="wu-toolbar-button">
	                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="reload()" plain="true">刷新</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="exportTo()" plain="true">导出</a>
				</div>
				<div class="wu-toolbar-search">
	                <label>发票号：</label><input id="invoice_no" class="wu-text" style="width:100px">
	                <label>客户名称：</label><input id="customer_name" class="wu-text" style="width:100px">
	                <label>发票日期起：</label><input id="invoice_date_from" class="easyui-datebox" style="width:100px">
	                <label>发票日期止：</label><input id="invoice_date_to" class="easyui-datebox" style="width:100px">
	                <a id="query" class="easyui-linkbutton" iconCls="icon-search">开始检索</a>
	            </div>
	        </div>
	        <!-- End of toolbar -->
	        <table id="wu-datagrid" toolbar="#wu-toolbar">
		        	<tr id="tHeading">
		        		<th style="width: 50px">请选择</th>
		        		<th>发票号</th>
		        		<th>客户名称</th>
		        		<th>发票日期</th>
		        	</tr>
	        </table>
	        <div id="pageDiv">
	        		<span id="currPage"></span>
	        		<button id="firstPage">首  页</button>
	        		<button id="prePage" >上一页</button>
	        		<button id="nextPage" >下一页</button>
	        		<button id="lastPage" >末  页</button>
	        </div>
    	  </div>
</div>
<div id="selectDiv" style="display: none;background:rgb(242,250,255);width:50%;border:1px solid;">
	<div id="selectSearch" class="wu-toolbar-search">
		<a id="selectQuery" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
	</div>
	<table id="selectTable" width="100%" cellspacing="0" border="1">
	</table>
	<div id="selectPageDiv">
		<span id="selectCurrPage"></span>
		<button id="confirmSelectBtn">确定</button>
		<button id="cancelBtn">取消</button>
		<button id="selectFirstPage">首  页</button>
		<button id="selectPrePage" >上一页</button>
		<button id="selectNextPage" >下一页</button>
		<button id="selectLastPage" >末  页</button>
	</div>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript">
	// 模块编号
	var module = "invoice";
	// 单号前缀
	var prefix = "P";
	// 流水号长度
	var flowLength = 3;
	// 当前页码
	var currPage = 1;
	// 总页数
	var totalPages = 0;
	// 操作标识,delete代表执行删除,update代表修改,add代表添加
	var actionType = "";
	
	// 当页面加载完毕
	$(document).ready(function(){
		// 刷新数据
		refresh(currPage);
		$("#firstPage").click(function(){
			currPage = 1;
			refresh(currPage);
		});
		$("#prePage").click(function(){
			currPage--;
			refresh(currPage);
		});
		$("#nextPage").click(function(){
			currPage++;
			refresh(currPage);
		});
		$("#lastPage").click(function(){
			refresh(totalPages);
		});
		$("#query").click(function(){
			currPage = 1;
			refresh(currPage);
		});
	});
		
	/**
	 * 向服务器发送ajax请求
	 * objs-请求参数
	 * dialogTitle-提示框的标题内容
	 * dialogInfo-提示框的内容 
	 * dialogType-提示框的类型
	 */
	function sendSaveAjax(objs,dialogTitle,dialogInfo,dialogType)
	{
		// 向服务器发送ajax请求
		$.ajax({
            type: "POST",
            url:"${pageContext.request.contextPath }/"+module+"/"+actionType,
            data: JSON.stringify(objs),
            dataType:"json",
            contentType:"application/json;charset=UTF-8",
            success:function(data){
	            	if(data){
	    				$.messager.alert(dialogTitle,dialogInfo+"成功",dialogType);
	    				$('#wu-dialog').dialog('close');
	    				refresh(currPage);
	    			}
	    			else
	    			{
	    				$.messager.alert(dialogTitle,dialogInfo+"失败",dialogType);
	    			}
            }
        });
	}
	
	// 刷新数据
	function refresh(currPage)
	{
		// 清空除表格标题外的所有行
		$("#wu-datagrid tr[id!='tHeading']").remove();

		var condition = [
			{"condition_key":"invoice_no","condition_value":$("#invoice_no").val(),"condition_type":"string"},
			{"condition_key":"customer_name","condition_value":$("#customer_name").val(),"condition_type":"string"},
			{"condition_key":"invoice_date_from","condition_value":$(".combo-value:eq(0)").val(),"condition_type":"dateFrom"},
			{"condition_key":"invoice_date_to","condition_value":$(".combo-value:eq(1)").val(),"condition_type":"dateTo"}
		];
		
		// 向服务器发送ajax请求
		$.ajax({
            type: "POST",
            url:"${pageContext.request.contextPath }/"+module+"/find?currPage="+currPage,
            data: JSON.stringify(condition),
            dataType:"json",
            contentType:"application/json;charset=UTF-8",
            success:function(data){
            		var total = data.total;
	            	totalPages = data.totalPages;
	    			var rows = data.rows;
	    			// 重置actionType
	    			actionType = "";
	    			// 遍历数据行
	    			$.each(rows,function(index,d){
	    				// 拼接联合主键的ID
	    				var idObj = d.invoice_no;
	    				var url = "${pageContext.request.contextPath }/"+module+"/findById?invoice_no="+idObj+"&des=queryInvoice";
	    				// 要添加的行内容
	    				var rowElements="<tr>"+
	    									"<td><input type='checkbox' id='"+idObj+"' name='checkedItem' /></td>"+
	    									"<td>"+
											"<a href='"+url+"'>"+d.invoice_no+"</a>"+"</td>"+
	    									"<td>"+d.customer_name+"</td>"+
	    									"<td>"+d.invoice_date+"</td>"+
	    								"</tr>";
	    				// 向表格添加行				
	    				$("#wu-datagrid").append(rowElements);
	    			});
	    			if(currPage == 1)
	    			{
	    				$("#prePage").attr("disabled",true);
	    			}
	    			else
	    			{
	    				$("#prePage").attr("disabled",false);
	    			}
	    			
	    			if(currPage == totalPages || total == 0)
	    			{
	    				$("#nextPage").attr("disabled",true);
	    			}
	    			else
	    			{
	    				$("#nextPage").attr("disabled",false);
	    			}
	    			
	    			$("#currPage").text("当前第"+currPage+"页,共"+totalPages+"页");
            }
        });
	}
	
    function reload()
    {
        refresh(currPage);
    }

    // 导出到EXCEL
    function exportTo()
    {
        // 存储传入服务器的对象
        var objs = [];
        // 查询条件
        var condition = [
            {"condition_key":"invoice_no","condition_value":$("#invoice_no").val(),"condition_type":"string"},
            {"condition_key":"customer_name","condition_value":$("#customer_name").val(),"condition_type":"string"},
            {"condition_key":"invoice_date_from","condition_value":$(".combo-value:eq(0)").val(),"condition_type":"dateFrom"},
            {"condition_key":"invoice_date_to","condition_value":$(".combo-value:eq(1)").val(),"condition_type":"dateTo"}
        ];
        // 存储表中的列名
        var colNames = [];
        // 遍历表头,初始化colNames
        $("#tHeading").children().each(function(i,th){
            if(i == 0) return true;
            colNames[i-1] = $(th).text();
        });
        // 向服务器发送ajax请求
        $.ajax({
            type: "POST",
            url:"${pageContext.request.contextPath }/"+module+"/findAll",
            data: JSON.stringify(condition),
            dataType:"json",
            contentType:"application/json;charset=UTF-8",
            success:function(data){
                // 遍历从服务器获取到的所有数据行
                $(data.rows).each(function(index,element){
                    // 封装成JSON对象
                    var obj = {
                        "invoice_no":element.invoice_no,
                        "customer_name":element.customer_name,
                        "invoice_date":element.invoice_date
                    };
                    // 将JSON对象添加到数组
                    objs.push(obj);
                });
                // 设置操作标识为export
                actionType = "export";
                // 向服务器发送ajax请求
                $.ajax({
                    type: "POST",
                    url:"${pageContext.request.contextPath }/"+module+"/"+actionType+"?colNames="+colNames.join(","),
                    data: JSON.stringify(objs),
                    dataType:"json",
                    contentType:"application/json;charset=UTF-8",
                    success:function(data){
                        window.location.href = "${pageContext.request.contextPath }/"+module+"/realExport";
                    }
                });
            }
        });
    }

	// 保存
	function save()
	{
		//$("input[class='invalid']").blur();
		// 如果仍然存在验证非法的数据,则提示并退出		
		if($(".invalid").length > 0)
		{
			$.messager.alert('信息提示','在没有通过数据验证的情况下不能保存!','info');
			return false;
		}
		else
		{
			// 要传入服务器的数组
			var objs = [];
			// 提示对话框信息
			var dialogInfo = "";
			var flag = false;
			// 如果是添加
			if(actionType == "add")
			{
				// 遍历添加的行
				$(".addRow").each(function(index,element){
					// 找到各个元素的value
					var invoice_no = $(element).find("input[name='invoice_no']").val();
					var prod_no = $(element).find("input[name='prod_no']").val();
					var in_qty = $(element).find("input[name='in_qty']").val();
					if(in_qty <= 0)
					{
						flag = true;
					}
					// 封装成JSON对象
					var obj = {
					 "invoice_no":invoice_no,
					 "prod_no":prod_no,
					 "in_qty":in_qty
					};
					// 将JSON对象添加到数组
					objs.push(obj);
				});
				// 提示框信息为添加
				dialogInfo = "添加";
			}
			// 如果是修改
			else if(actionType == "update")
			{
				// 获取被选中的所有checkbox
				var checkedItems = $("input[name='checkedItem']:checked");
				// 遍历
				$(checkedItems).each(function(index,element)
				{
					// 获取checkbox的id，即联合主键,将其分割成数组
					var ids = $(element).attr("id").split("-");
					var invoice_no = ids[0];
					var prod_no = ids[1];
					// 获取行
					var tr = $(element).parent().parent();
					// 获取其他列的
					var in_qty = $(tr).find("input[name='in_qty']").val();
					// 封装成JSON对象
					var obj = {
					 "invoice_no":invoice_no,
					 "prod_no":prod_no,
					 "in_qty":in_qty
					};
					// 将JSON对象添加到数组 
					objs.push(obj);
				});
				// 提示对话框信息为修改
				dialogInfo = "修改";
			}
			// 向服务器发送ajax请求
			sendSaveAjax(objs,"信息提示",dialogInfo,"info");
			
		}
	}
	
    /**
	 * Name 删除记录
	 */
	function remove()
    {
		if(actionType == "add")
		{
			var cancelObj = $(".addRow").find("input[name='checkedItem']:checked").parent().parent();
			$(cancelObj).remove();
		}
		else
		{
	    		// 获取所有被选中的checkbox
			var checkedItems = $("input[name='checkedItem']:checked");
	    		// 如果没有被选中,则提示需要至少选择一项
			if(checkedItems.length == 0)
			{
				$.messager.alert('信息提示','请至少选择一项再进行删除！','info');
			}
			else
			{
				// 确认是否要删除
				$.messager.confirm('信息提示','确定要删除记录？', function(result){
					// 如果点击确定
					if(result){
						// 存储将要删除的数据行的id
						var ids = [];
						// 遍历被选中的checkbox,从中获取id
						$(checkedItems).each(function(index,element){
							var id_arr = $(element).attr("id").split("-");
							var obj = {"invoice_no":id_arr[0],"prod_no":id_arr[1]};
							ids.push(obj);	
						});
						// 将标识变为delete
						actionType = "delete";
						// 向服务器发送ajax请求
						sendSaveAjax(ids,"信息提示","删除","info");
					}	
				});
			}
		}
	}
	/**
	 * 加入前导0
	 */
	function addPrefixZero(num,length)
	{
		var numstr = num.toString();
		var l=numstr.length;
		if (numstr.length>=length) {return numstr;}

		for(var  i = 0 ;i<length - l;i++){
		  numstr = "0" + numstr;  
		 }
		return numstr; 
    }
	
    /**
	 * 添加
	 */
	function openAdd(){
		$.post("${pageContext.request.contextPath }/"+module+"/getId",function(data){
			// 获取服务器返回的数据
			var invoice_no = $.parseJSON(data).invoice_no;
			// 获取系统日期字符串
			var sysdate = invoice_no.substring(prefix.length,prefix.length+7);
			// 将数据中的数字截取出来,不包含0
			var numPart = parseInt(invoice_no.replace(/[^1-9]/ig,""));
			// 如果之前已经添加过行
			if($(".addRow").length != 0)
			{
				// 找出添加的最后一行
				var lastTr = $("#wu-datagrid .addRow").last();
				// 找出添加的最后一行中的input的值
				var last_invoice_no = $(lastTr).find("td input[name='invoice_no']").val();
				// 将数据中的数字截取出来,不包含0 
				var maxNo = parseInt(last_invoice_no.replace(/[^1-9]/ig,""));
				// 如果前端生成的数字比服务器返回的数字大,则在前端直接加1,然后生成单号
				if(numPart < maxNo + 1)
				{
					prod_no = prefix + sysdate + addPrefixZero(maxNo+1,flowLength);
				}
			}
	    		// 将要添加的行内容
			var rowElements="<tr class='addRow'>"+
								"<td><input type='checkbox' name='checkedItem' disabled='disabled' checked/></td>"+
								"<td><input type='text' name='invoice_no' value='"+invoice_no+"'/></td>"+
								"<td><input type='text' name='prod_no' class='inventoryInfo' onclick='pop(this)' onblur='checkExist(this)'/></td>"+
								"<td name='customer_name'></td>"+
								"<td><input type='text' name='in_qty' value='0'/></td>"+
								"<td name='inventory_loc'></td>"+
								"<td name='invoice_date'></td>"+
								"<td><input type='text' name='pur_order_no' /></td>"+
							"</tr>";
			// 向表格添加行				
			$("#wu-datagrid").append(rowElements);
			// 设置焦点
			$("input[name='prod_no']").focus();
			// 
			actionType = "add";
		});
	}
	  
    
	/**
	 *  修改
	 */
	function openEdit(){
		// 获取所有已被选中的checkbox
		var checkedItems = $("input[name='checkedItem']:checked");
		// 如果没有被选中,则提示需要至少选择一项
		if(checkedItems.length == 0)
		{
			$.messager.alert('信息提示','请至少选择一项再进行修改！','info');
		}
		else
		{
			// 将标识变为update,当点击保存按钮时,会执行修改
			actionType = "update";
			// 存储input的name,用于添加input
			var names = ["in_qty"];
			var name_index = 0;
			// 禁用所有的checkbox
			$("input[name='checkedItem']").attr("disabled","disabled");
			// 遍历所有被选中的checkbox
			$(checkedItems).each(function(index,element)
			{
				// 获取行
				var tr = $(element).parent().parent();
				// 获取每行的td
				var td = $(tr).find("td");
				// 遍历tr中的td
				$(td).each(function(index,element)
				{
					if(index == 4)
					{
						// 获取td的文本内容
						var text = $(element).text();
						// 清空td的内容
						$(element).html("");
						// 将input添加到td
						$(element).append("<input type='text' name='"+names[name_index]+"' value='"+text+"' />");
					}
				});
			});
		}	
	}	
</script>

</body>
</html>