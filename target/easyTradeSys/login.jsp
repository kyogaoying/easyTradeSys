<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录框</title>
<link href="css/login.css" type="text/css" rel="stylesheet">
<link href="css/forBody.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-3.2.1.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//用户名密码错误提示
	if('${warning}'!=""){
		$("p").text('${warning}');
	}
	//输入框点击事件
	$(".textField").click(function(){
		$(this).attr("value","");
	});
	//登录框点击事件
	$("#submitBtn").click(function(){
		var username = $(".textField:eq(0)").val();
		var password = $(".textField:eq(1)").val();
		if(username =="请输入用户名" && password=="请输入密码"){
			$(".textField").attr("value","");
			$("p").text("用户名密码不能为空！");
			return true;
		}
		if(username == "" || password == ""){
			$("p").text("用户名密码不能为空！");
		}
		else{
			$("form").submit();
		}
	});
});

//定义回车事件
$(document).keydown(function(event){
  if(event.keyCode == 13)
  {
    $("#submitBtn").click();
  }
});
</script>
</head>
<body>
<h1>Easy-Trade 贸易管理系统</h1>
	<div>
		<!--输出警告提示信息-->
 		<p></p>
  		<form id="loginForm" action="login" method="post">
    		<input class="textField" type="text" name="username" value="请输入用户名"><br>
    		<input class="textField" type="password" name="password" value="请输入密码"><br>
    		<input id="submitBtn" type="button" value="登  录">
  		</form>
	</div>
</body>
</html>