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
					Add: <span id="add">${quotationInfos[0].address}</span><br/><br/>
					Tel: <span id="tel">${quotationInfos[0].tel}</span>
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
	/**
	公共js部分
	*/
	//根据此变量判断本报价单是否能提交
	var commitable=true;
	//根据此变量判断新增的行号是几,初始是2
	var rowNo=2;
	//保存客户编号
	var orig_customer_no="";
	$(document).ready(function(){
		//设置remark框长度
		setRemarkWidth();
		//获取原本页面信息，并将信息显示在修改页面上
		getPreInfo();
		//客户编号框获取焦点
		$("input[name='customer_name']").focus();
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
	
	
	
	
	/**
	添加功能js部分
	*/
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
		//先获取公司名，报价单号，日期等统一信息
		var quotation_no=$("#quoteNo").text();
		var customer_no=orig_customer_no;
		var quotation_date=$("#quoteDate").text();
		var quotation_remark="";
		//判断哪些报价条目是报价单里没有,但数据库里有的(造成这种情况的原因是,已被更改的产品,无法用Hibernate的savaOrUpdate从数据库删除,所以需要手动删除)
		//获取本页面所有报价条目的prod_no,存在一个数组里
		var arr_prod_no_page=[];
		$.each($("input[name='prod_no']"),function(index,element){
			arr_prod_no_page.push($(element).val());
		});
		
		//获取数据库中本报价单的所有报价条目,存在一个数组里
		var arr_prod_no_db=[];
		var quotation_no_json={"quotation_no":quotation_no};
		$.ajax({
		type: "POST",
		url:"${pageContext.request.contextPath }/quotationManagement/findQuotationNo",
		data: JSON.stringify(quotation_no_json),
		dataType:"json",
		contentType:"application/json;charset=UTF-8",
		success:function(data){
			$.each(data,function(index,element){
				arr_prod_no_db.push(element.prod_no);
			});
			
			
			//循环比较,将只有数据库里有的记录选出,存在一个数组里
			var arr_extra_json=[]
			$.each(arr_prod_no_db,function(indexDB,elementDB){
				var compareFlag=false;
				$.each(arr_prod_no_page,function(indexPG,elementPG){			
					if(elementDB==elementPG){
						compareFlag==true;
					}
				});
				//如果到这里仍旧等于false,则此条目是页面没有但是数据库有的,将存入arr_extra数组
				if(compareFlag==false){
					var elementDB_json={
						"prod_no":elementDB,
						"quotation_no":quotation_no,
						"customer_no":customer_no,
						"price":0
					};
					arr_extra_json.push(elementDB_json);
				}
			});
			//发送异步请求,将arr_extra里的QuotationInfo对象在数据库里删除
			$.ajax({
			type: "POST",
			url:"${pageContext.request.contextPath }/quotationManagement/deleteQuotation",
			data: JSON.stringify(arr_extra_json),
			dataType:"json",
			contentType:"application/json;charset=UTF-8",
			success:function(){				
				//创建保存报价行的数组
				var arr_quotationInfos_json=[];
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
			});		
			}
		});
	}
	//删除整张单据
	function remove(){
		$.messager.confirm('信息提示','确定要删除单据吗？', function(result){
			if(result){
				var arr_quotation_no_json=[];
				//获取报价号
				var quotation_no='${quotation_no }';
				var quotation_no_json={"quotation_no":quotation_no};
				arr_quotation_no_json.push(quotation_no_json);
				removeQuotation(arr_quotation_no_json);
			}
		});
	}
	
	
	/**
	修改功能js部分
	*/
	//获取修改前的信息
	function getPreInfo(){
		//将quotation_no封装为JSON对象，异步查询此报价单号的数据库记录
		var quotation_no='${quotation_no}';
		var json_quotation_no={"quotation_no":quotation_no};
		//发送AJAX请求
		$.ajax({
		type: "POST",
		url:"${pageContext.request.contextPath }/quotationManagement/findQuotationNo",
		data: JSON.stringify(json_quotation_no),
		dataType:"json",
		contentType:"application/json;charset=UTF-8",
		success:function(data){
			$.each(data,function(index,element){
				//第一次循环才设置公共信息，后面无需设置
				if(index==0){
					//设置报价单公共信息
					setPublicInfo(element);	
				}
				if(index!=0){
					//添加一行,再设置报价条目
					openAdd();
				}
				setPriceList(element,index);	
			});
		}
		});
	}
	//设置报价单公共信息
	function setPublicInfo(obj){
		//单号
		var quotation_no=obj.quotation_no;
		//日期
		var quotation_date=obj.quotation_date;
		//客户号
		var customer_no=obj.customer_no;
		//客户名
		var customer_name=obj.customer_name;
		//地址
		var address=obj.address;
		//电话
		var tel=obj.tel;
		//remark
		var quotation_remark=obj.quotation_remark;	
		//设置进input框
		$("#quoteNo").text(quotation_no);
		$("#quoteDate").text(quotation_date);
		$("input[name='customer_name']").val(customer_no);
		
		$("#add").text(address);
		$("#tel").text(tel);
		for(var i=0; i<quotation_remark.length; i++){
			$(".remark").eq(i).val(quotation_remark[i]);
		}
	}
	//设置报价条目
	function setPriceList(obj,index){
		//产品号
		var prod_no=obj.prod_no;
		//产品名
		var prod_name_eng=obj.prod_name_eng;
		//产品描述
		var prod_desc=obj.prod_desc;
		//产品价格
		var price=obj.price;
		
		//开始设置
		//产品号
		$("input[name='prod_no']").eq(index).val(prod_no);
		//产品名
		$("span[name='prod_name_eng']").eq(index).text(prod_name_eng);
		//产品描述
		$("input[name='prod_desc']").eq(index).val(prod_desc);
		//产品价格
		$("input[name='price']").eq(index).val(price);
	}

</script>
</body>
</body>
</html>