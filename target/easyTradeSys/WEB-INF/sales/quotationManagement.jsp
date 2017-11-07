<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="copyright" content="All Rights Reserved, Copyright (C) 2013, Wuyeguo, Ltd." />
<title>供应商管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/1.3.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/wu.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/icon.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/header.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/tableList.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/turnPage.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/quotationDML.jsp"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>


</head>
<body>
	<body class="easyui-layout">
   <div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
    	<!-- Begin of toolbar -->
        <div id="wu-toolbar">
            <div class="wu-toolbar-button">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">添加</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="reload()" plain="true">刷新</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="openAdd()" plain="true" style="display: none">导出</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="save()" plain="true" style="display: none">保存</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-arrow-down" onclick="openFlowDown()" plain="true">下推</a>
            </div>
            <div class="wu-toolbar-search">
                <label>报价单编号：</label><input class="wu-text" style="width:100px" id="search_quotation_no" name="searchArea">&nbsp;
                <label>客户名称：</label><input class="wu-text" style="width:100px" id="search_customer_name" name="searchArea">&nbsp;
                <label>开始日期：</label><input type="date" id="search_from" name="searchArea" style="width:100px">&nbsp;
                <label>截止日期：</label><input type="date" id="search_to"  name="searchArea" style="width:100px">
                <a class="easyui-linkbutton" iconCls="icon-search" onclick="findOneFirstTime()">开始检索</a>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid" toolbar="#wu-toolbar">
        	<tr id="tHeading">
        		<th style="width: 50px">请选择</th>
        		<th>报价单编号</th>
        		<th>客户编号</th>
        		<th>客户名称</th>
			</tr>
			<c:forEach items="${allInfos}" var="info">
				<tr class="tRow">
					<td><input id="${info[0]}" type="checkbox" name="checkedItem"></td>
					<td>
						<a class="quotationNo" href="queryQuotation?quotation_no=${info[0]}">
							${info[0]}
						</a>
					</td>
					<td>${info[1]}</td>
					<td>${info[2]}</td>
				</tr>
			</c:forEach>
        </table>
        <div id="turnPages">
        	<button class="turnPage" id="firstPage" onClick="javascript:void(window.location.href='${pageContext.request.contextPath }/quotationManagement/allInfos?page=1')">首   页</button>
        	<button class="turnPage" id="prePage" onClick="javascript:void(window.location.href='${pageContext.request.contextPath }/quotationManagement/allInfos?page=${sessionScope.page-1}')">上一页</button>
        	<button class="turnPage" id="nextPage" onClick="javascript:void(window.location.href='${pageContext.request.contextPath }/quotationManagement/allInfos?page=${sessionScope.page+1}')">下一页</button>
        	<button class="turnPage" id="lastPage" onClick="javascript:void(window.location.href='${pageContext.request.contextPath }/quotationManagement/allInfos?page=${sessionScope.totalPages }')">末   页</button>
        </div>
    </div>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript">
	/**
	* S:Name 添加一行
	*/
	var counter_times;//添加行计数器，记录提交前添加了几行，点击保存按钮，加载页面，数据库取的用户ID较大时，此值将被重置为0
	var counter_previousVal;//添加行计数器，记录上一次从数据库取出的customer_no是多少，点击保存按钮和加载页面时，此值将被重置为0
	//定义四个变量表示首页,上一页,下一页,末页,当前页
	var firstPage=1;//首页
	var prePage;//上一页
	var nextPage;//下一页
	var lastPage;//末页
	var currPage=firstPage;//当前页
	var arr_global_conditions_json=[];//搜索条件
	$(document).ready(function(){
		//加载页面时就把这两个计数器置0
		counter_times=0;
		counter_previousVal=0;
		
		if('${page}'=="1"){
			$("#prePage").attr("disabled","disabled");
		}
		if('${page}'=='${totalPages}'){
			$("#nextPage").attr("disabled","disabled");
		}
	});
	/**
	*刷新本页面
	*/
	function reload(){
		location.reload();
	}
	/**
	*Sally:设置当前页码和末页页码,设置按钮状态
	*/
	function setCurrPageAndBtn(page){
		currPage=page;
		if(currPage==firstPage){
			$("#prePage").attr("disabled","disabled");
		}
		else{
			$("#prePage").removeAttr("disabled");
		}
		if(currPage==lastPage){
			$("#nextPage").attr("disabled","disabled");
		}
		else{
			$("#nextPage").removeAttr("disabled");
		}
	}
	/**
	*Sally:异步发送查询全部记录的请求
	*/
	function refresh(){
		$.post("${pageContext.request.contextPath }/quotationManagement/queryAfterDML?page=${sessionScope.page}",function(allRecords){
			//将页面原本的数据删除（table heading不删除）
        	$("#wu-datagrid tr[id!='tHeading']").remove();
			//再循环添加
        	$.each(allRecords,function(index,element){
        		var rowElements="<tr class='tRow'>"+
						"<td><input id='"+element[0]+"' type='checkbox' name='checkedItem'/></td>"+
			    		"<td>"+element[0]+"</td>"+
			    		"<td>"+element[1]+"</td>"+
			    		"<td>"+element[2]+"</td>"
			    	"</tr>";
			    	$("#wu-datagrid").append(rowElements);
        	});				
		});
	}
	/**
	*S:准备获取用户要查询的报价信息(查询后，用户点击上一页，下一页等的通用方法)
	*/
	function findOneSeveralTimes(currPage){
		$.ajax({
	            type: "POST",
	            url:"${pageContext.request.contextPath }/quotationManagement/queryOne?page="+currPage,
	            data: JSON.stringify(arr_global_conditions_json),
	            dataType:"json",
	            contentType:"application/json;charset=UTF-8",
	            success:function(data){
					//将页面原本的用户数据删除
	            	$(".tRow").remove();
					//再循环添加
	            	$.each(data,function(index,element){
	            		var rowElements="<tr class='tRow'>"+
							"<td><input id='"+element[0]+"' type='checkbox' name='checkedItem'/></td>"+
							"<td>"+
							"<a class='quotationNo' href='queryQuotation?quotation_no="+element[0]+"'>"+element[0]+"</a>"+
							"</td>"+
				    		"<td>"+element[1]+"</td>"+
				    		"<td>"+element[2]+"</td>"
				    		"</tr>";
			    		$("#wu-datagrid").append(rowElements);
	            	});
					setCurrPageAndBtn(currPage);
					changeOnClick();
	            }
			});		
	}
	/**
	* S:准备获取用户要查询的用户信息(通过调用findOneSeveralTimes方法修改上一页，下一页等按钮的onclick属性)
	*/
	function changeOnClick(){
		//获取用户输入的用户名
		var customername=$("#searchTextArea").val();
		//更改“首页”，“上一页”，“下一页”，“末页”四个按钮的“onclick”属性值
		$("#firstPage").attr("onClick","findOneSeveralTimes("+firstPage+");");
		$("#prePage").attr("onClick","findOneSeveralTimes("+(currPage-1)+");");
		$("#nextPage").attr("onClick","findOneSeveralTimes("+(currPage+1)+");");
		$("#lastPage").attr("onClick","findOneSeveralTimes("+lastPage+");");
	}
	/**
	* S:获取用户要查询的用户信息
	*/
    function findOneFirstTime() {
		//储存查询条件的变量并拼接
		var arr_searchConditions_json=[
			{"condition_key":"quotation_no","condition_value":$("#search_quotation_no").val(),"condition_type":"string"},
			{"condition_key":"customer_name","condition_value":$("#search_customer_name").val(),"condition_type":"string"},
			{"condition_key":"from","condition_value":$("#search_from").val(),"condition_type":"dateFrom"},
			{"condition_key":"to","condition_value":$("#search_to").val(),"condition_type":"dateTo"}
		];
		
		if(($("#search_quotation_no").val()==null || $("#search_quotation_no").val()=="") 
		   		&& ($("#search_customer_name")==null || $("#search_customer_name").val()=="")
		  		&& ($("#search_from").val()==null || $("#search_from").val()=="")
		  		&& ($("#search_to").val()==null || $("#search_to").val()==""))
		{
			$.messager.alert('信息提示','查询信息不能为空！','info');
		}
		else
		{
			$.ajax({
	            type: "POST",
				//一开始查询,肯定是从第一页开始查,所以这里的page参数是第一页
	            url:"${pageContext.request.contextPath }/quotationManagement/queryOne?page=1",
	            data: JSON.stringify(arr_searchConditions_json),
	            dataType:"json",
	            contentType:"application/json;charset=UTF-8",
	            success:function(data){
					//将页面原本的用户数据删除
	            	$(".tRow").remove();
					//再循环添加
	            	$.each(data,function(index,element){
	            		var rowElements="<tr class='tRow'>"+
						"<td><input id='"+element[0]+"' type='checkbox' name='checkedItem'/></td>"+
						"<td>"+
						"<a class='quotationNo' href='queryQuotation?quotation_no="+element[0]+"'>"+element[0]+"</a>"+
						"</td>"+
			    		"<td>"+element[1]+"</td>"+
			    		"<td>"+element[2]+"</td>"
			    		"</tr>";
			    		$("#wu-datagrid").append(rowElements);
	            	});
	            	//此ajax请求用来获取新的总页数值
	    			$.ajax({
	    	            type: "POST",
	    				//一开始查询,肯定是从第一页开始查,所以这里的page参数是第一页
	    	            url:"${pageContext.request.contextPath }/quotationManagement/getNewTotalPages",
	    	            data: JSON.stringify(arr_searchConditions_json),
	    	            dataType:"json",
	    	            contentType:"application/json;charset=UTF-8",
	    	            success:function(data){
	    					//获取总页数
	    					lastPage=data.count;
	    					//设置页码和按钮状态
	    					setCurrPageAndBtn(firstPage);
	    					//修改上一页,下一页,首页,末页的click事件
	    					changeOnClick();
	    	            	}
	    			}); 
	            }
			});
		}
		//查询成功后,给arr_global_conditions_json(全局)变量赋值
		arr_global_conditions_json=arr_searchConditions_json;
	}

	/**
	* Name 删除记录
	*/
	function remove(){
		if($("input[name='checkedItem']:checked").length==0){
			$.messager.alert('信息提示','请在删除前选中至少一行！','info');
			return false;//退出方法
		}		
		$.messager.confirm('信息提示','确定要删除单据吗？', function(result){
			if(result){
				//创建存放quotationInfo json对象的数组
				var arr_quotation_no_json=[];
				//获取待删除行checkbox的id，id即quotation_no
				$("input[name='checkedItem']:checked").each(function(){
						var checkbox_id=$(this).attr("id");
						var quotation_no_json={"quotation_no":checkbox_id};
						arr_quotation_no_json.push(quotation_no_json);
				});
				//执行删除
				removeQuotation(arr_quotation_no_json);
			}
		});
		//新增用户时，用来生成用户ID的两个计数器置0
		counter_times=0;
		counter_previousVal=0;
	}
	
	/**
	* Name 打开修改窗口
	*/
	function openEdit(){
		//一条都没选
		if($("input[name='checkedItem']:checked").length==0){
			$.messager.alert('信息提示','请在修改前选中至少一行！','info');
			return false;//退出方法
		}
		//选了多于一行
		if($("input[name='checkedItem']:checked").length>1){
			$.messager.alert('信息提示','请选择唯一单据！','info');
			return false;//退出方法
		}
		//获取被选checkbox
		var quotation_no=$("input[name='checkedItem']:checked").eq(0).attr("id");
		//调用editQuotation方法
		editQuotation(quotation_no);
	}
	
	/**
	*下推单据
	*/
	function openFlowDown(){
		//一条都没选
		if($("input[name='checkedItem']:checked").length==0){
			$.messager.alert('信息提示','请选择至少一张单据！','info');
			return false;//退出方法
		}
		//选了多于一行
		if($("input[name='checkedItem']:checked").length>1){
			$.messager.alert('信息提示','请选择唯一单据！','info');
			return false;//退出方法
		}
		//获取被选checkbox
		var quotation_no=$("input[name='checkedItem']:checked").eq(0).attr("id");
		//调用flowDown方法
		flowDown(quotation_no);
	}
</script>
</body>
</body>
</html>