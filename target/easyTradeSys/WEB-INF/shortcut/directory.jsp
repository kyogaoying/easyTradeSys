<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导航页面</title>
<link href="${pageContext.request.contextPath}/css/forBody.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
<!--放大图标功能-->
<script src="${pageContext.request.contextPath}/js/zoomIcon.js"></script>
<style>
body {
	background-image: url(${pageContext.request.contextPath}/images/menu/directoryBG.jpg);
	padding: 0px;
	margin: 0px;
	font-family: Helvetica, 'Hiragino Sans GB', 'Microsoft Yahei', '微软雅黑', Arial, sans-serif;
	font-size: small;
}
#banner {
	display:table;
	width: 100%;
	height: 250px;
	margin-top: 250px;
	z-index: 2;
	background-color: white;
}
#mainDirectory1 {
	display: table-row;
	height: 40%;
	width: 500px;
	margin: auto;
}
#mainDirectory2 {
	display: table-row;
	height: 40%;
	width: 500px;
}
.row1 {
	display: table-cell;
	margin: 30px;
	margin-right: 30px;
	float: left;
	width: 50px;
	height: 50px;
	position: relative;
	left:30em;
}
.row2 {
	display: table-cell;
	margin: 15px;
	margin-right: 30px;
	float: left;
	width: 50px;
	height: 50px;
	position: relative;
	left:31em;
	}
#fastDirectory {
	width: 500px;
	height: 200px;
	margin: auto;
}
	
.row3 {
	margin: 15px;
	margin-left: 60px;
	margin-right: 70px;
	float: left;
    width: 90px;
	height: 90px;
}
img {
	width: 50px;
	height: 50px;
}
</style>
</head>
<body>
	<div id="banner">
  <div id="mainDirectory1">
    <div class="row1"><a href=""><img class="navImg" src="${pageContext.request.contextPath}/images/menu/sales.png"></a><br/>
      <span>销售</span></div>
    <div class="row1"><a href=""><img class="navImg" src="${pageContext.request.contextPath}/images/menu/purchase.png"></a><br/>
      <span>采购</span></div>
    <div class="row1"><a href=""><img class="navImg" src="${pageContext.request.contextPath}/images/menu/shipping.png"></a><br/>
      <span>船务报关</span></div>
    <div class="row1"><a href=""><img class="navImg" src="${pageContext.request.contextPath}/images/menu/warehouse.png"></a><br/>
      <span>仓库</span></div>
  </div>
  <div id="mainDirectory2">
    <div class="row2"><a href=""><img class="navImg" src="${pageContext.request.contextPath}/images/menu/finance.png"></a><br/>
      <span>财务</span></div>
    <div class="row2"><a href=""><img class="navImg" src="${pageContext.request.contextPath}/images/menu/table.png"></a><br/>
      <span>报单</span></div>
  </div>
</div>
<div id="fastDirectory">
  <div class="row3"><a href=""><img class="navImg" src="${pageContext.request.contextPath}/images/menu/1194797.png"></a><br/>
    库存查询</div>
  <div class="row3"><a href=""><img class="navImg" src="${pageContext.request.contextPath}/images/menu/1183300.png"></a><br/>
    产品查询</div>
</div>
</body>
</html>