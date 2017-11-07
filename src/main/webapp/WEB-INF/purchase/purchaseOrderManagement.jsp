<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="copyright" content="All Rights Reserved, Copyright (C) 2017, Sally, Ltd." />
<title>采购订单管理</title>
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
	                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">添加</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="reload()" plain="true">刷新</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="exportTo()" plain="true">导出</a>
	                <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="save()" plain="true">保存</a>
				</div>
				<div class="wu-toolbar-search">
	                <label>采购订单号：</label><input id="order_no" class="wu-text" style="width:100px">
	                <label>产品编号：</label><input id="prod_no" class="wu-text" style="width:100px">
	                <label>产品名称：</label><input id="prod_name" class="wu-text" style="width:100px">
	                <label>合同编号：</label><input id="pi_no" class="wu-text" style="width:100px">
	                <label>供应商编号：</label><input id="vendor_no" class="wu-text" style="width:100px">
	                <label>期望交货日期起：</label><input id="expected_delivery_date_start" class="easyui-datebox" style="width:100px">
	                <label>期望交货日期止：</label><input id="expected_delivery_date_end" class="easyui-datebox" style="width:100px">
	                <a id="query" class="easyui-linkbutton" iconCls="icon-search">开始检索</a>
	            </div>
	        </div>
	        <!-- End of toolbar -->
	        <table id="wu-datagrid" toolbar="#wu-toolbar">
		        	<tr id="tHeading">
		        		<th style="width: 50px">请选择</th>
		        		<th>采购订单号</th>
		        		<th>产品编号</th>
		        		<th>产品名称</th>
		        		<th>数量</th>
		        		<th>期望交货日期</th>
		        		<th>合同编号</th>
		        		<th>供应商编号</th>
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
<!-- End of easyui-dialog -->
<script type="text/javascript">
	// 模块编号
	var module = "purchaseOrder";
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
		// 
		var condition = [
			{"condition_key":"order_no","condition_value":$("#order_no").val(),"condition_type":"string"},
			{"condition_key":"prod_no","condition_value":$("#prod_no").val(),"condition_type":"string"},
			{"condition_key":"prod_name","condition_value":$("#prod_name").val(),"condition_type":"string"},
			{"condition_key":"pi_no","condition_value":$("#pi_no").val(),"condition_type":"string"},
			{"condition_key":"vendor_no","condition_value":$("#vendor_no").val(),"condition_type":"string"},
			{"condition_key":"expected_delivery_date_start_from","condition_value":$(".combo-value:eq(0)").val(),"condition_type":"dateFrom"},
			{"condition_key":"expected_delivery_date_start_to","condition_value":$(".combo-value:eq(1)").val(),"condition_type":"dateTo"}
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
	    				var idObj = d.order_no+"-"+d.prod_no;
	    				// 要添加的行内容
	    				var rowElements="<tr>"+
	    									"<td><input type='checkbox' id='"+idObj+"' name='checkedItem' /></td>"+
	    									"<td>"+d.order_no+"</td>"+
	    									"<td>"+d.prod_no+"</td>"+
	    									"<td>"+d.prod_name+"</td>"+
	    									"<td>"+d.qty+"</td>"+
	    									"<td>"+d.expected_delivery_date+"</td>"+
	    									"<td>"+d.pi_no+"</td>"+
	    									"<td>"+d.vendor_no+"</td>"+
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
			{"condition_key":"order_no","condition_value":$("#order_no").val(),"condition_type":"string"},
			{"condition_key":"prod_no","condition_value":$("#prod_no").val(),"condition_type":"string"},
			{"condition_key":"prod_name","condition_value":$("#prod_name").val(),"condition_type":"string"},
			{"condition_key":"pi_no","condition_value":$("#pi_no").val(),"condition_type":"string"},
			{"condition_key":"vendor_no","condition_value":$("#vendor_no").val(),"condition_type":"string"},
			{"condition_key":"expected_delivery_date_start_from","condition_value":$(".combo-value:eq(0)").val(),"condition_type":"dateFrom"},
			{"condition_key":"expected_delivery_date_start_to","condition_value":$(".combo-value:eq(1)").val(),"condition_type":"dateTo"}
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
                        "order_no":element.order_no,
                        "prod_no":element.prod_no,
                        "prod_name":element.prod_name,
                        "qty":element.qty,
                        "expected_delivery_date":element.expected_delivery_date,
                        "pi_no":element.pi_no,
                        "vendor_no":element.int_order_remark
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
    
	  
    
</script>

</body>
</html>