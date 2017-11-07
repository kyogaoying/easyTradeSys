<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="copyright" content="All Rights Reserved, Copyright (C) 2013, Wuyeguo, Ltd." />
<title>财务管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/1.3.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/wu.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/icon.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/header.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/tableList.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/turnPage.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/quotationDML.jsp"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/piDML.jsp"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/dividePage.css" type="text/css">
</head>
<body>
	<body class="easyui-layout">
   <div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
    	<!-- Begin of toolbar -->
        <div id="wu-toolbar">
            <div class="wu-toolbar-button">
                <a href="#" class="easyui-linkbutton" iconCls="icon-arrow-down" onclick="openFlowDown()" plain="true">下推</a>
            </div>
            <div class="wu-toolbar-search">
            	<label>PI编号：</label><input id="pi_no" class="wu-text" style="width:100px">
                <label>客户名称：</label><input id="customer_name" class="wu-text" style="width:100px">
                <label>开始日期：</label><input id="pi_date_start" class="easyui-datebox" style="width:100px">
                <label>截止日期：</label><input id="pi_date_date_end" class="easyui-datebox" style="width:100px">
                <a id="query" class="easyui-linkbutton" iconCls="icon-search">开始检索</a>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid" toolbar="#wu-toolbar">
        	<tr id="tHeading">
        		<th style="width: 50px">请选择</th>
        		<th>PI编号</th>
        		<th>客户编号</th>
        		<th>客户名称</th>
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
//模块编号
var module = "finance";
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

/**
*下推单据
*/
function openFlowDown()
{
	//一条都没选
	if($("input[name='checkedItem']:checked").length==0)
	{
		$.messager.alert('信息提示','请选择至少一张单据！','info');
		return false;//退出方法
	}
	//选了多于一行
	if($("input[name='checkedItem']:checked").length>1)
	{
		$.messager.alert('信息提示','请选择唯一单据！','info');
		return false;//退出方法
	}
	
	// 确认是否要删除
	$.messager.confirm('信息提示','确定要下推？这将生成全套单据!', function(result)
	{
		// 如果点击确定
		if(result)
		{
			//获取被选checkbox
			var pi_no=$("input[name='checkedItem']:checked").eq(0).attr("id");
			// 向服务器发送ajax请求
			$.ajax({
		        type: "POST",
		        url:"${pageContext.request.contextPath }/"+module+"/generate?pi_no="+pi_no,
		        dataType:"json",
		        contentType:"application/json;charset=UTF-8",
		        success:function(data){
		        	$.messager.alert('信息提示','下推成功！','info');
		        }
			});
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
		{"condition_key":"pi_no","condition_value":$("#pi_no").val(),"condition_type":"string"},
		{"condition_key":"customer_name","condition_value":$("#customer_name").val(),"condition_type":"string"},
		{"condition_key":"pi_date_from","condition_value":$(".combo-value:eq(0)").val(),"condition_type":"dateFrom"},
		{"condition_key":"pi_date_to","condition_value":$(".combo-value:eq(1)").val(),"condition_type":"dateTo"}
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
    				var idObj = d.pi_no;
    				// 要添加的行内容
    				var rowElements="<tr>"+
    									"<td><input type='checkbox' id='"+idObj+"' name='checkedItem' /></td>"+
    									"<td>"+d.pi_no+"</td>"+
    									"<td>"+d.customer_no+"</td>"+
    									"<td>"+d.customer_name+"</td>"+
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
    	{"condition_key":"pi_no","condition_value":$("#pi_no").val(),"condition_type":"string"},
		{"condition_key":"customer_name","condition_value":$("#customer_name").val(),"condition_type":"string"},
		{"condition_key":"pi_date_from","condition_value":$(".combo-value:eq(0)").val(),"condition_type":"dateFrom"},
		{"condition_key":"pi_date_to","condition_value":$(".combo-value:eq(1)").val(),"condition_type":"dateTo"}
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
                    "pi_no":element.pi_no,
                    "customer_name":element.int_order_no,
                    "customer_name":element.prod_no
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
</body>
</html>