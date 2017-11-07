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
        <h1>QUOTATION</h1>
      </div>
      <form>
		  <div id="documentHeading">
			<div> 
				<span id="buyerInfo"> 
					Buyer name: <input type="text" name="customer_name" value="请输入完整的客户编号" style="color: grey" onClick="clearOnClick(this)" onBlur="verifyBuyerNo(this)">
					<span id="warning" style="color: red;visibility: hidden;">客户编号不正确！</span><br/><br/>
					Add: <span id="add"></span><br/><br/>
					Tel: <span id="tel"></span>
				</span> 
				<span id="quotationInfo"> 
				  Quotation No.: <span id="quoteNo"></span><br/><br/>
				  Date: <span id="quoteDate"></span>
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
			<tr class="tRow">
			  <td>
			  	  <input type="button" id="minorRowBtn" onClick="openMinor()">
				  <input type="button" id="addRowBtn" onClick="openAdd()">
				  <span id="seq1">1</span>
			  </td>
			  <td><input type="text" name="prod_no" onClick="clearOnClick(this);" onblur="verifyProdNo(this);" id="prod_no1"></td>
			  <td><span class="prod_name_eng" name="prod_name_eng" id="prod_name_eng1"></span></td>
			  <td><input type="text" name="prod_desc" id="prod_desc1"></td>
			  <td><input type="text" name="price" id="price1"></td>
			</tr>
		  </table>
		  <!-- End of table -->
		  <!-- Strat of footer -->
		  <div id="footer">
			<p><span class="terms" style="font-size: 120%;">Terms: </span></p>
			<p><span class="terms">Price Term: </span><input type="text" class="remark" width="200" value="FOB"></p>
			<p><span class="terms">Loading Port: </span><input type="text" class="remark" width="200" value="SHENZHEN PORT,CHINA"></p>
			<p><span class="terms">Terms of Payment: </span><input type="text" class="remark" width="200" value="T/T or L/C at sight"></p>
			<p><span class="terms">Delivery time: </span><input type="text" class="remark" width="200" value="60 working days"></p>
			<p><span class="terms">Validity: </span><input type="text" class="remark" width="200" value="3 month"></p>
		  </div>
      </form>
      <!-- End of footer -->
    </div>
  </div>
</div>
<!-- End of easyui-dialog --> 
<script type="text/javascript">
	//根据此变量判断本报价单是否能提交
	var commitable=true;
	//根据此变量判断新增的行号是几,初始是2
	var rowNo=2;
	//保存客户编号
	var orig_customer_no="";
	//一进来页面就将报价单流水号和报价日期显示在新增的报价单上
	//并且设置remark输入框的长度
	$(document).ready(function(){
		$("#quoteNo").text('${quotation_no}');
		$("#quoteDate").text('${quotation_date}');
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
	//验证客户编号是否存在
	function verifyBuyerNo(obj){
		var customer_no=$(obj).val();
		//客户编号为空的情况
		if(customer_no==null || customer_no==""){
			$("#warning").text("客户编号不能为空！");
			$("#warning").css("visibility","visible");
			commitable=false;
		}
		//客户编号不为空的情况
		else{
			//将客户编号封装为JSON对象
			var json_customer_no={"customer_no":customer_no};
			//发送AJAX请求
			$.ajax({
			type: "POST",
			url:"${pageContext.request.contextPath }/quotationManagement/findCustomerNo",
			data: JSON.stringify(json_customer_no),
			dataType:"json",
			contentType:"application/json;charset=UTF-8",
			success:function(data){
				//客户编号正确
				if(data.customer_no!="" && data.customer_no!=null){
					//获取客户名，电话和页码
					var customer_name=data.customer_name;
					var add=data.address;
					var tel=data.tel;
					//设置进页面显示
					$(obj).val(customer_name);
					$("#add").text(add);
					$("#tel").text(tel);
					$("#warning").css("visibility","hidden");
					commitable=true;
					//将customer_no保存，以便发送AJAX请求
					orig_customer_no=customer_no;
				}
				//客户编号错误
				else{
					$("#warning").css("visibility","visible");
					commitable=false;
				}
			}
			});
		}
	}
	//验证产品编号是否存在
	function verifyProdNo(obj){
		var prod_no=$(obj).val();
		//产品编号为空的情况
		if(prod_no==null || prod_no==""){
			$(obj).val("产品编号不能为空！").css("color","red");
			commitable=false;
		}
		//产品编号不为空的情况
		else{
			//将客户编号封装为JSON对象
			var json_prod_no={"prod_no":prod_no};
			//发送AJAX请求
			$.ajax({
			type: "POST",
			url:"${pageContext.request.contextPath }/quotationManagement/findProdNo",
			data: JSON.stringify(json_prod_no),
			dataType:"json",
			contentType:"application/json;charset=UTF-8",
			success:function(data){
				//客户编号正确
				if(data.prod_no!="" && data.prod_no!=null){
					//获取客户名，电话和页码
					var prod_name_eng=data.prod_name_eng;
					//获取本行行号
					var rowNo=$(obj).attr("id").substr(7);
					//设置进页面显示
					$(".prod_name_eng").eq(rowNo-1).text(prod_name_eng);
					$(obj).css("color","black");
					commitable=true;
				}
				//客户编号错误
				else{
					$(obj).val("产品编号错误！").css("color","red");
					commitable=false;
				}
			}
			});
		}
	}
	//增加一行
	function openAdd(){
		//拼接各个<td>元素的id值
		var	prod_no_id="prod_no"+rowNo;
		var	prod_name_eng_id="prod_name_eng"+rowNo;
		var	prod_desc_id="prod_desc"+rowNo;
		var	price_id="price"+rowNo;
		
		var element="<tr class='tRow'>"+
				  "<td>"+rowNo+"</td>"+
				  "<td><input type='text' name='prod_no' onClick='clearOnClick(this);' onblur='verifyProdNo(this);' id='"+prod_no_id+"'></td>"+
				  "<td><span class='prod_name_eng' name='prod_name_eng' id='"+prod_name_eng_id+"'></span></td>"+
				  "<td><input type='text' name='prod_desc' id='"+prod_desc_id+"'></td>"+
				  "<td><input type='text' name='price' id='"+price_id+"'></td>"+
				"</tr>";
		$("table").append(element);	
		rowNo++;
	}
	//删除一行（每次删除最后一行，无行可删时会提示用户）
	function openMinor(){
		//无行可删了
		if($(".tRow").length==1){
			$.messager.alert('失败提示','单据至少包含一条记录！','info');
		}
		//有行可删
		else{
			$.messager.confirm('信息提示','确定要删除一行吗？', function(result){
				if(result){
					$(".tRow:last").remove();
					rowNo--;
				}
			});				 	
		}
	}
	//保存
	function save(){
		//创建保存报价行的数组
		var arr_quotationInfos_json=[];
		//先获取公司名，报价单号，日期等统一信息
		var quotation_no=$("#quoteNo").text();
		var customer_no=orig_customer_no;
		var quotation_date=$("#quoteDate").text();
		var quotation_remark="";
		for(var i=0; i<$(".remark").length; i++){
			quotation_remark=quotation_remark+$(".remark").eq(i).val()+"@@";
		}//拼接
		//循环获取每一行报价，并放进arr_quotationInfo数组
		for(var i=0; i<$(".tRow").length; i++){
			var prod_no=$("#prod_no"+(i+1)).val();
			var prod_desc=$("#prod_desc"+(i+1)).val();
			var price=$("#price"+(i+1)).val();
			//封装JSON对象，将每个对象存入arr_quotationInfos数组
			var quotationInfo_json={
				"prod_no":prod_no,
				"prod_desc":prod_desc,
				"price":price,
				"quotation_no":quotation_no,
				"customer_no":customer_no,
				"quotation_remark":quotation_remark,
				"quotation_date":quotation_date
			};
			arr_quotationInfos_json.push(quotationInfo_json);
		}
		//发送AJAX请求
		$.ajax({
		type: "POST",
		url:"${pageContext.request.contextPath }/quotationManagement/saveQuotation",
		data: JSON.stringify(arr_quotationInfos_json),
		dataType:"json",
		contentType:"application/json;charset=UTF-8",
		success:function(data){
			window.location.href="${pageContext.request.contextPath }/quotationManagement/queryQuotation?quotation_no="+quotation_no;
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