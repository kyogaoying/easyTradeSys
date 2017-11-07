<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="copyright" content="All Rights Reserved, Copyright (C) 2017, Sally, Ltd." />
<title>模块功能管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/1.3.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/wu.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/icon.css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/header.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/tableList.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/selectList.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/dividePage.css" type="text/css">
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
	        </div>
	        <!-- End of toolbar -->
	        <table id="wu-datagrid" toolbar="#wu-toolbar">
		        	<tr id="tHeading">
		        		<th style="width: 50px">请选择</th>
		        		<th>模块ID</th>
		        		<th>模块名称</th>
		        		<th>功能ID</th>
		        		<th>功能描述</th>
		        		<th>功能控制</th>
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
		<span id="conditionSpan"></span>
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
	/**
	* Name 载入菜单树
	*/
	/* $('#wu-category-tree').tree({
		url:'temp/menu.php',
		onClick:function(node){
			alert(node.text);
		}
	}); */
	// 模块名
	var module="moduleFuns";
	// 当前页码
	var currPage = 1;
	// 总页数
	var totalPages = 0;
	// 操作标识,delete代表执行删除,update代表修改,add代表添加
	var actionType = "";
	
	// 当页面加载完毕，刷新数据
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
			{"condition_key":"","condition_value":"","condition_type":"string"}
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
	    				// 根据实际的值来确定之后要添加的checkbox的选中状态
	    				var checked = d.auth_control == 1 ? "checked" : "";
	    				// 拼接联合主键的ID
	    				var idObj = d.module_no+"-"+d.fun_flag;
	    				
	    				// 要添加的行内容
	    				var rowElements="<tr class='tableRows'>"+
	    									"<td><input type='checkbox' id='"+idObj+"' name='checkedItem' /></td>"+
	    									"<td>"+d.module_no+"</td>"+
	    									"<td>"+d.module_name+"</td>"+
	    									"<td>"+d.fun_flag+"</td>"+
	    									"<td>"+d.fun_desc+"</td>"+
	    									"<td><input type='checkbox' name='auth_control' disabled='disabled' "+checked+"/></td>"+
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

    // 导出到EXCEL
    function exportTo()
    {
        // 存储传入服务器的对象
        var objs = [];
        // 查询条件
        var condition = [
            {"condition_key":"","condition_value":"","condition_type":"string"}
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
                        "module_no":element.module_no,
                        "module_name":element.module_name,
                        "fun_flag":element.fun_flag,
                        "fun_desc":element.fun_desc,
                        "auth_control":element.auth_control
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

    function reload()
	{
		refresh(currPage);
    }

	// 保存
	function save()
	{
		// 要传入服务器的数组
		var objs = [];
		// 提示对话框信息
		var dialogInfo = "";
		// 如果是添加
		if(actionType == "add")
		{
			// 遍历添加的行
			$(".addRow").each(function(index,element){
				// 找到各个元素的value
				var module_no = $(element).find("input[name='module_no']").val();
				var fun_flag = $(element).find("input[name='fun_flag']").val();
				var fun_desc = $(element).find("input[name='fun_desc']").val();
				var auth_control = $(element).find("input[name='auth_control']").is(":checked");
				// 封装成JSON对象
				var obj = {
				 "module_no":module_no,
				 "fun_flag":fun_flag,
				 "fun_desc":fun_desc,
				 "auth_control":auth_control
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
				var module_no = ids[0];
				var fun_flag = ids[1];
				// 获取行
				var tr = $(element).parent().parent();
				// 获取其他列的
				var fun_desc = $(tr).find("input[name='fun_desc']").val();
				var auth_control = $(tr).find("input[name='auth_control']").is(":checked");
				// 封装成JSON对象
				var obj = {
				 "module_no":module_no,
				 "fun_flag":fun_flag,
				 "fun_desc":fun_desc,
				 "auth_control":auth_control
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
							var obj = {"module_no":id_arr[0],"fun_flag":id_arr[1]};
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
	 * 添加
	 */
	function openAdd(){
    		// 将要添加的行内容
		var rowElements="<tr class='addRow'>"+
							"<td><input type='checkbox' name='checkedItem' disabled='disabled' checked/></td>"+
							"<td><input type='text' name='module_no' class='module' onclick='pop(this)' /></td>"+
							"<td name='module_name'></td>"+
							"<td><input type='text' name='fun_flag' /></td>"+
							"<td><input type='text' name='fun_desc' /></td>"+
							"<td><input type='checkbox' name='auth_control' /></td>"+
						"</tr>";
		// 向表格添加行				
		$("#wu-datagrid").append(rowElements);
		$("input[name='module_no']").focus();
		actionType = "add";
	}
	
	/**
	 *  修改
	 */
	function openEdit()
	{
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
			// 存储input的name,用于下面拼接input
			var names = ["fun_desc","auth_control"];
			// 禁用所有的checkbox
			$("input[name='checkedItem']").attr("disabled","disabled");
			// 遍历所有被选中的checkbox
			$(checkedItems).each(function(index,element)
			{
				// 获取行
				var tr = $(element).parent().parent();
				// 获取每行的td
				var td = $(tr).find("td");
				var name_index = 0;
				// 遍历tr中的td
				$(td).each(function(index,element)
				{
					// 排除第一列的checkbox和id列,继续保持只读
					if(index <= 3) return true;// 相当于continue
					// 从第2列到倒数第2列
					if(index < td.length-1)
					{
						// 获取td的文本内容
						var text = $(element).text();
						// 清空td的内容
						$(element).html("");
						// 将input添加到td
						$(element).append("<input type='text' name='"+names[name_index]+"' value='"+text+"' />");
						name_index++;
					}
					// 最后一列为checkbox
					else
					{
						// 获取td中的input
						var auth_control = $(element).find("input[name='auth_control']");
						// 设置为可修改
						$(auth_control).attr("disabled",false);
					}
			    });
			});
		}
	}	
</script>
</body>
</html>