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
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/quotation.css" type="text/css">
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
      	<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true" class="disappear">添加</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true" class="disappear">修改</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true" class="disappear">删除</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="reload()" plain="true" >刷新</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="openAdd()" plain="true" >导出</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="save()" plain="true" style="display:none;">保存</a>
      	<a href="#" class="easyui-linkbutton" iconCls="icon-arrow-down" onclick="openFlowDown()" plain="true">下推</a>
      </div>
    </div>
    <!-- End of toolbar --> 
    <!-- Begin of header -->
    <div id="mainBody">
      <div id="documentTitle">
        <h1>XXX Trading Co., Ltd</h1>
        <p>Add: No.1 Chikan Industry Zone, Shaping Town, Heshan City, GD Province, China.</p>
        <p>Tel: +86-0757-22139589&nbsp;&nbsp;Fax：+86-0757-28105616</p>
        <h1>QUOTATION</h1>
      </div>
      <div id="documentHeading">
        <div> 
        	<span id="buyerInfo"> 
         		Buyer name: ${quotationInfos[0].customer_name}<br/><br/>
          		Add: ${quotationInfos[0].address}<br/><br/>
          		Tel: ${quotationInfos[0].tel}
          	</span> 
          	<span id="quotationInfo"> 
          	  Quotation No.:${quotationInfos[0].quotation_no}<br/><br/>
          	  Date: ${quotationInfos[0].quotation_date}
          	</span> 
        </div>
      </div>
      <!-- End of header --> 
      <!-- Begin of table -->
      <table id="mainTable">
        <tr id="tHeading">
          <th>No.</th>
          <th>Item No.</th>
          <th>Item Name</th>
          <th>Description of Goods</th>
          <th>Unit Price(USD)</th>
        </tr>
        <c:forEach items="${quotationInfos}" var="info" varStatus="status">
          <tr class="tRow">
            <td>${status.count}</td><!--行号-->
            <td>${info.prod_no}</td>
            <td>${info.prod_name_eng}</td>
            <td>${info.prod_desc}</td>
            <td>${info.price}</td>
          </tr>
        </c:forEach>
      </table>
      <!-- End of table -->
      <!-- Strat of footer -->
      <div id="footer">
        <p><span class="terms" style="font-size: 120%;">Terms: </span></p>
		<p><span class="terms">Price Term: </span>${quotationInfos[0].quotation_remark[0]}</p>
     	<p><span class="terms">Loading Port: </span>${quotationInfos[0].quotation_remark[1]}</p>
     	<p><span class="terms">Terms of Payment: </span>${quotationInfos[0].quotation_remark[2]}</p>
     	<p><span class="terms">Delivery time: </span>${quotationInfos[0].quotation_remark[3]}</p>
     	<p><span class="terms">Validity: </span>${quotationInfos[0].quotation_remark[4]}</p>
      </div>
      <!-- End of footer -->
    </div>
  </div>
</div>
<!-- End of easyui-dialog --> 
<script>
	//刷新本页面
	function reload(){
		location.reload();
	}
	//删除报价单
	function remove(){
		$.messager.confirm('信息提示','确定要删除单据吗？', function(result){
			if(result){
				var arr_quotation_no_json=[];
				//获取qoutation_no
				var quotation_no='${quotationInfos[0].quotation_no}';
				//将quotation_no封装为JSON对象
				var quotation_no_json={"quotation_no":quotation_no};
				arr_quotation_no_json.push(quotation_no_json);
				//删除
				removeQuotation(arr_quotation_no_json);
			}
		});
	}
	//修改报价单
	function openEdit(){
		//获取报价单号
		var quotation_no='${quotationInfos[0].quotation_no}';
		//跳转到修改页面
		editQuotation(quotation_no);
	}
	// 下推
	function openFlowDown(){
		flowDown('${quotationInfos[0].quotation_no}');
	}
</script>
</body>
</body>
</html>