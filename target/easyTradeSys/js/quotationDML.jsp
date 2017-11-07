/**
 * 报价功能的增删改查
 */
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    
    //新增功能
	function openAdd(){
		//跳转至新建报价单页面
		window.location.href="${pageContext.request.contextPath }/quotationManagement/newQuotation";
	}
	
	//修改功能
	function editQuotation(quotation_no){
		//修改页面通过quotation_no参数还原页面的原始信息
		window.location.href="${pageContext.request.contextPath }/quotationManagement/updateQuotation?quotation_no="+quotation_no;
	}
	
	//删除功能
	function removeQuotation(arr_quotation_no_json){
		$.ajax({
			type: "POST",
			url:"${pageContext.request.contextPath }/quotationManagement/deleteQuotationByHQL",
			data: JSON.stringify(arr_quotation_no_json),
			dataType:"json",
			contentType:"application/json;charset=UTF-8",
			success:function(data){
				bkToMenu();
			}
			});
	}
	
	//刷新至报价列表页面
	function bkToMenu(){
		window.location.href="${pageContext.request.contextPath }/quotationManagement/allInfos?page=1";
	}
	
	/**
	* Name 下推单据
	*/
	function flowDown(quotation_no){
		//跳转至合同页面
		window.location.href="${pageContext.request.contextPath }/piManagement/generatePi?quotation_no="+quotation_no;
	}