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
	#mainPart{
		width: 400px;
		margin: auto;
		margin-top: 500px;
	}
</style>
</head>
<body class="easyui-layout">
  <div id="mainPart" style="margin: auto">
	  <label>请输入新密码：</label>
	  <input type="text">
	  <input type="button" value="提 交" onclick="resetPw()">
  </div>
  <script>
		function resetPw() {
		    var user_no='${userInfo.user_no}';
			var newPw=$("input[type='text']").val();
			$.get("${pageContext.request.contextPath }/sysConfig/resetPw?user_no="+user_no+"&password="+newPw,function (data) {
			    var result=data.result;
			    if(result!="fail"){
                    $.messager.alert('信息提示','密码修改成功,新密码是：'+result,'info');
				}
                else {
                    $.messager.alert('信息提示','密码修改失败,请重新修改！','info');
				}
            });
        }
  </script>
</body>
</html>