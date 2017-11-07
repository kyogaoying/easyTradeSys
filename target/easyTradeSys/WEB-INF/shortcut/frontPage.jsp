<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<link href="${pageContext.request.contextPath}/css/forBody.css" rel="stylesheet" type="text/css">
<style>
	#gt1{
		color: gray;
		font-size: 170%;
	}
	img{
		height: 50%;
	}
</style>
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
<script type="text/javascript">
    //自动补0
    function addZero(indivNum) {
		if(indivNum<10){
			return "0"+indivNum;
		}
		return indivNum;
	}
	$(document).ready(function(){
		getSysDate();
		window.setInterval("getSysDate()",1000);
	});	
	function getSysDate()
	{
		var obj_date=new Date();
		var show_day=new Array('星期日','星期一','星期二','星期三','星期四','星期五','星期六'); 
		$("#gt2").text(function(){
			return "当前时间："+obj_date.getFullYear()+"年"+(obj_date.getMonth()+1)+"月"+obj_date.getDate()+"日，"
				+addZero(obj_date.getHours())+" : "+addZero(obj_date.getMinutes())+" : "+addZero(obj_date.getSeconds())+"，"
				+show_day[obj_date.getDay()];
		}
		);
	}	
</script>
</head>
<body>
  <div>
  	<p id="gt1">Hello ${userInfo.user_name}, 欢迎使用Easy-Trade贸易管理系统</p>
  	<img src="${pageContext.request.contextPath}/images/menu/frontPage.jpg">
  	<p id="gt2">当前时间: </p>
  </div>
</body>
</html>