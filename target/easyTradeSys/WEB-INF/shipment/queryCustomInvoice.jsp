<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="copyright" content="All Rights Reserved, Copyright (C) 2013, Wuyeguo, Ltd." />
<title>新的invoice</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/1.3.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/wu.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/icon.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/header.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/tableList.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/quotation.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/ci.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/quotationDML.jsp"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/piDML.jsp"></script>
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
      	<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true" style="display:none;">添加</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true" style="display:none;">修改</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true" >删除</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="reload()" plain="true" >刷新</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="openAdd()" plain="true" >导出</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="save()" plain="true" >保存</a>
      	<a href="#" class="easyui-linkbutton" iconCls="icon-arrow-down" onclick="openFlowDown()" plain="true"  style="display: none">下推</a>
      </div>
    </div>
    <!-- End of toolbar --> 
    <!-- Begin of header -->
    <div id="mainBody">
      <div id="documentTitle">
        <h1>XXX Trading Co., Ltd</h1>
        <p>地址: No.1 Chikan Industry Zone, Shaping Town, Heshan City, GD Province, China.</p>
        <p>电话: +86-0757-22139589&nbsp;&nbsp;传真：+86-0757-28105616</p>
        <h1>商 业 发 票</h1>
      </div>
      <form>
		  <div id="documentHeading">
			<div> 
				<span id="buyerInfo"> 
					公司名: ${invoiceInfos[0].customer_name}<br/><br/>
					地址: <span id="add">${invoiceInfos[0].address}</span><br/><br/>
					电话: <span id="tel">${invoiceInfos[0].tel}</span>
				</span> 
				<span id="quotationInfo"> 
				  发票号: <span id="invoiceNo">${invoiceInfos[0].invoice_no}</span><br/><br/>
				  发票日期: <span id="invoiceDate">${invoiceInfos[0].invoice_date}</span>
				</span> 
			</div>
		  </div>
		  <!-- End of header --> 
		  <!-- Begin of table -->
		  <table id="mainTable">
			<tr id="tHeading">
			  <th>序号</th>
			  <th>产品编号</th>
			  <th>产品名称</th>
			  <th>单价(USD)</th>
			  <th>数量(箱)</th>
			  <th>金额小计(USD)</th>
			</tr>
			<c:forEach items="${invoiceInfos}" var="info" varStatus="status">
				<tr class="tRow">
				  <td>
					  <span id="seq1" style="left: 0px;">${status.count}</span>
				  </td>
				  <td>${info.prod_no}</td>
				  <td>${info.prod_name}</td>
				  <td>${info.price}</td>
				  <td>${info.qty}</td>
				  <td class="sub_total_price">${info.sub_total_price}</td>
				</tr>
			</c:forEach>
		  </table>
		  <div id="ttlAndetd">
		  	<span id="ttl">总计: </span>
		  </div>
		  <!-- End of table -->
		  <!-- Strat of footer -->
     	  <div id="signatures">
     	  	<span id="seller">
     	  		卖方签章:<br/><br/><br/><br/><br/>
     	  		<hr/>
     	  	</span>
     	  	<span id="buyer">
     	  		买方签章:<br/><br/><br/><br/><br/>
     	  		<hr/>
     	  	</span>
     	  </div>
		  <!-- End of footer -->
      </form>
    </div>
  </div>
</div>
<!-- End of easyui-dialog --> 
<script type="text/javascript">
	/**
	公共js部分
	*/
	//根据此变量判断本报价单是否能提交
	var commitable=true;
	$(document).ready(function(){
		//设置remark框长度
		setRemarkWidth();
		//在页面显示PI总金额
		getTotal();
	});
	//设置remark输入框的长度，使其右对齐
	function setRemarkWidth(){
		for(var i=0; i<$(".terms").length; i++){
			var labelWidth=$(".terms").eq(i).width();
			var inputWidth=300-labelWidth;
			$(".remark").eq(i).css("width",inputWidth);
		}
	}
	function getTotal(){
		//设置PI总金额到页面
		var totalAmount=0;
		$(".sub_total_price").each(function(index,element){
			var eachAmount=($(element).text()=="" || $(element).text()== null)? 0:parseInt($(element).text());
			totalAmount+=eachAmount;
		});
		$("#ttl").text("总计: $"+totalAmount);
	}
	//删除整张单据
	function remove(){
		$.messager.confirm('信息提示','确定要删除单据吗？', function(result){
			if(result){
				var arr_pi_no_json=[];
				//获取PI号
				var pi_no='${invoiceInfos[0].pi_no}';
				var pi_no_json={"pi_no":pi_no};
				arr_pi_no_json.push(pi_no_json);
				removePi(arr_pi_no_json);
			}
		});
	}
</script>
</body>
</body>
</html>