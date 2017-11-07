/**
 * 报价功能的增删改查
 */
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
	
	//删除功能
	function removePi(arr_pi_no_json){
		$.ajax({
			type: "POST",
			url:"${pageContext.request.contextPath }/piManagement/deletePiByHQL",
			data: JSON.stringify(arr_pi_no_json),
			dataType:"json",
			contentType:"application/json;charset=UTF-8",
			success:function(data){
				bkToMenu();
			}
			});
	}
	
	//刷新至报价列表页面
	function bkToMenu(){
		window.location.href="${pageContext.request.contextPath }/piManagement/allInfos?page=1";
	}
