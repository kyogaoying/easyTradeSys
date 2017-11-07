<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="copyright" content="All Rights Reserved, Copyright (C) 2013, Wuyeguo, Ltd." />
<title>新的PI</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/1.3.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/wu.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/icon.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/header.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/tableList.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/quotation.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/pi.css" type="text/css">
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
      	<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true" style="display:none;">添加</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true" style="display:none;">修改</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true" >删除</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="reload()" plain="true" >刷新</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="openAdd()" plain="true" >导出</a> 
      	<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="save()" plain="true" >保存</a>
      </div>
    </div>
    <!-- End of toolbar --> 
    <!-- Begin of header -->
    <div id="mainBody">
      <div id="documentTitle">
        <h1>XXX Trading Co., Ltd</h1>
        <p>Add: No.1 Chikan Industry Zone, Shaping Town, Heshan City, GD Province, China.</p>
        <p>Tel: +86-0757-22139589&nbsp;&nbsp;Fax：+86-0757-28105616</p>
        <h1>Proforma Invoice</h1>
      </div>
      <form>
		  <div id="documentHeading">
			<div> 
				<span id="buyerInfo"> 
					Buyer name: <input type="text" name="customer_name" value='${quotationInfos[0].customer_name}' style="color: grey" disabled="disabled"><br/><br/>
					Add: <span id="add">${quotationInfos[0].address}</span><br/><br/>
					Tel: <span id="tel">${quotationInfos[0].tel}</span>
				</span> 
				<span id="quotationInfo"> 
				  PI No.: <span id="piNo">${flowNo }</span><br/><br/>
				  Date: <span id="piDate">${generateDate }</span>
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
			  <th>QTY(Ctn)</th>
			  <th>Sub-total Amount(USD)</th>
			</tr>
			<c:forEach items="${quotationInfos}" var="info" varStatus="status">
				<tr class="tRow">
				  <td>
					  <span id="seq1" style="left: 0px;">${status.count}</span>
				  </td>
				  <td>
				  	<input type="text" name="prod_no" onClick="clearOnClick(this);" onblur="verifyProdNo(this);" id="prod_no${status.count}" disabled="disabled" value='${info.prod_no}'>
				  </td>
				  <td><span class="prod_name_eng" name="prod_name_eng" id="prod_name_eng${status.count}">${info.prod_name_eng}</span></td>
				  <td><input type="text" name="prod_desc" id="prod_desc${status.count}" disabled="disabled" value='${info.prod_desc}'></td>
				  <td><input type="text" name="price" id="price${status.count}" disabled="disabled" value='${info.price}'></td>
				  <td><input type="number" name="qty" id="qty${status.count}" onBlur="getSubTotalAndTotal(this)" onClick="clearOnClick(this)" value="0"></td>
				  <td><span class="sub_total_price" name="sub_total_price" id="sub_total_price${status.count}"></span></td>
				</tr>
			</c:forEach>
		  </table>
		  <div id="ttlAndetd">
		    <label>Expected Delivery Date:</label><input type="date" id="etd">
		  	<span id="ttl">Total Amount: </span>
		  </div>
		  <!-- End of table -->
		  <!-- Strat of footer -->
		  <div id="footer">
			<p><span class="terms" style="font-size: 120%;">Terms: </span></p>
			<p><span class="terms">Price Term: </span><span class="remark">${quotationInfos[0].quotation_remark[0]}</span></p>
			<p><span class="terms">Loading Port: </span><span class="remark">${quotationInfos[0].quotation_remark[1]}</span></p>
			<p><span class="terms">Terms of Payment: </span><span class="remark">${quotationInfos[0].quotation_remark[2]}</span></p>
			<p><span class="terms">Delivery time: </span><span class="remark">${quotationInfos[0].quotation_remark[3]}</span></p>
		  </div>
     	  <div id="beneficiary">
     	  	<p style="font-size: 120%">BANK INFORMATION:</p>
     	  	<p>Beneficiary: XXX Trading Co., Ltd</p>
     	  	<p>Beneficiary Bank: The Hongkong and Shanghai Banking Corporation Limited</p>
			<p>ACCOUNT NO.: 421896321471210</p>
     	  	<p>SWIFT CODE: ABOCCNBJ090</p>
     	  	<p>Bank Address: Causeway Bay Plaza Ⅱ,463-486 Lockhart Road,Hong Kong</p>
     	  </div>
     	  <div id="signatures">
     	  	<span id="seller">
     	  		Seller<br/><br/><br/><br/><br/>
     	  		<hr/>
     	  	</span>
     	  	<span id="buyer">
     	  		Buyer<br/><br/><br/><br/><br/>
     	  		<hr/>
     	  	</span>
     	  </div>
      </form>
      <!-- End of footer -->
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
	//
	var module = "piManagement";
	
	
	$(document).ready(function(){
		//设置remark框长度
		setRemarkWidth();
	});
	//设置remark输入框的长度，使其右对齐
	function setRemarkWidth(){
		for(var i=0; i<$(".terms").length; i++){
			var labelWidth=$(".terms").eq(i).width();
			var inputWidth=300-labelWidth;
			$(".remark").eq(i).css("width",inputWidth);
		}
	}
	//消除文本框文本
	function clearOnClick(obj){
		$(obj).val("").css("color","black");
	}
	//自动算sub-total，并显示在页面上
	function getSubTotalAndTotal(obj){
		//获取数量框的数量和id
		var qty=($(obj).val()==""|| $(obj).val()== null)? "0":$(obj).val();
		//截取出行数
		var qty_id=$(obj).attr("id");
		var qty_id_no=qty_id.substr(3);
		//得出price框和sub-total框的id
		var price_id="price"+qty_id_no;
		var sub_total_price_id="sub_total_price"+qty_id_no;
		//设置sub-total框的值
		var price=$("#"+price_id).val();
		if((qty!="0")){
			var subTotal=parseInt(qty)*parseInt(price);
			$("#"+sub_total_price_id).text(subTotal);
		}
		//设置PI总金额到页面
		getTotal();
	}
	function getTotal(){
		//设置PI总金额到页面
		var totalAmount=0;
		$(".sub_total_price").each(function(index,element){
			var eachAmount=($(element).text()=="" || $(element).text()== null)? 0:parseInt($(element).text());
			totalAmount+=eachAmount;
		});
		$("#ttl").text("Total Amount: $"+totalAmount);
	}
	
	
	
	/**
	保存功能js部分
	*/
	//保存
	function save(){
		//先获取公司名，PI单号，日期等统一信息
		var pi_no=$("#piNo").text();
		var quotation_no='${quotationInfos[0].quotation_no}';
		var customer_no='${quotationInfos[0].customer_no}';
		var pi_date=$("#piDate").text();
		var expected_delivery_date=$("#etd").val();
		var pi_remark="";
		for(var i=0; i<$(".remark").length; i++){
			pi_remark=pi_remark+$(".remark").eq(i).text()+"@@";
		}
		//创建保存报价行的JSON数组
		var arr_piInfos_json=[];
		//拼接
		//循环获取每一行PI行，并放进arr_piInfos_json数组
		for(var i=0; i<$(".tRow").length; i++){
			var prod_no=$("#prod_no"+(i+1)).val();
			var qty=$("#qty"+(i+1)).val();
			var sub_total_price=$("#sub_total_price"+(i+1)).text();
			//封装JSON对象，将每个对象存入arr_quotationInfos数组
			var piInfo_json={
				"pi_no":pi_no,
				"pi_date":pi_date,
				"quotation_no":quotation_no,
				"customer_no":customer_no,
				"prod_no":prod_no,
				"qty":qty,
				"sub_total_price":sub_total_price,
				"expected_delivery_date":expected_delivery_date,
				"pi_remark":pi_remark,
			};
			arr_piInfos_json.push(piInfo_json);
		}
		//发送AJAX请求
		$.ajax({
		type: "POST",
		url:"${pageContext.request.contextPath }/"+module+"/savePi",
		data: JSON.stringify(arr_piInfos_json),
		dataType:"json",
		contentType:"application/json;charset=UTF-8",
		success:function(data){
			window.location.href="${pageContext.request.contextPath }/"+module+"/queryPi?pi_no="+pi_no;
		}
		});
	}
	//删除整张单据
	function remove(){
		$.messager.confirm('信息提示','确定要删除吗？编辑的内容将丢失！', function(result){
			if(result){
				window.location.href="${pageContext.request.contextPath }/quotationManagement/allInfos?page=1";
			}
		});
	}

</script>
</body>
</body>
</html>