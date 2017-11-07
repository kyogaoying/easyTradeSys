<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<style type="text/css">
	th{
		border: 1px black solid;
		height: 30px;
	}
	tr{
		border: 1px black solid;
	}
	table{
		border-spacing: 0px;
		margin: auto;
		width: 60%;
	}
	td{
		border: 1px black solid;
		height:20px;
	}
</style>
</head>
<body class="easyui-layout">
  <div id="mainPart" style="margin: auto">
	   <table>
		   <tr>
			   <th></th>
			   <th>内部订单号</th>
			   <th>产品编号</th>
			   <th>产品名称</th>
			   <th>采购总价</th>
			   <th>销售总价</th>
			   <th>盈亏情况</th>
		   </tr>
		   <c:forEach items="${summary}" var="info" varStatus="status">
			   <tr class="tRow">
				   <td>
					   <span style="left: 0px;">${status.count}</span>
				   </td>
				   <td>${info.int_order_no}</td>
				   <td>${info.prod_no}</td>
				   <td>${info.prod_name}</td>
				   <td>${info.pur_amount}</td>
				   <td>${info.sales_amount}</td>
				   <td>${info.result}</td>
			   </tr>
		   </c:forEach>
	   </table>
   </div>
</body>
</html>