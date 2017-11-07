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
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/turnPage.css" type="text/css">
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
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">添加</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="reload()" plain="true">刷新</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="openAdd()" plain="true">导出</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="save()" plain="true">保存</a>
            </div>
            <div class="wu-toolbar-search">
                <label>客户名称：</label><input id="searchTextArea" class="wu-text" style="width:100px">
                <a class="easyui-linkbutton" iconCls="icon-search" onclick="findOneFirstTime()">开始检索</a>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid" toolbar="#wu-toolbar">
        	<tr id="tHeading">
        		<th style="width: 50px">请选择</th>
        		<th>客户编号<span class="mustFill"> *</span></th>
        		<th>客户名称<span class="mustFill"> *</span></th>
        		<th>地址<span class="mustFill"> *</span></th>
        		<th>联系电话</th>
        		<th>客户状态(合作:C 潜在:P)</th>
			</tr>
			<c:forEach items="${allInfos}" var="info">
				<tr class="tRow">
					<td><input id="${info.customer_no}" type="checkbox" name="checkedItem"></td>
					<td>${info.customer_no}</td>
					<td>${info.customer_name}</td>
					<td>${info.address}</td>
					<td>${info.tel}</td>
					<td>${info.customer_type}</td>
				</tr>
			</c:forEach>
        </table>
        <div id="turnPages">
        	<button class="turnPage" id="firstPage" onClick="javascript:void(window.location.href='${pageContext.request.contextPath }/customerManagement/allInfos?page=1')">首   页</button>
        	<button class="turnPage" id="prePage" onClick="javascript:void(window.location.href='${pageContext.request.contextPath }/customerManagement/allInfos?page=${sessionScope.page-1}')">上一页</button>
        	<button class="turnPage" id="nextPage" onClick="javascript:void(window.location.href='${pageContext.request.contextPath }/customerManagement/allInfos?page=${sessionScope.page+1}')">下一页</button>
        	<button class="turnPage" id="lastPage" onClick="javascript:void(window.location.href='${pageContext.request.contextPath }/customerManagement/allInfos?page=${sessionScope.totalPages }')">末   页</button>
        </div>
    </div>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript">
	/**
	* S:Name 添加一行
	*/
	var counter_times;//添加行计数器，记录提交前添加了几行，点击保存按钮，加载页面，数据库取的用户ID较大时，此值将被重置为0
	var counter_previousVal;//添加行计数器，记录上一次从数据库取出的customer_no是多少，点击保存按钮和加载页面时，此值将被重置为0
	//定义四个变量表示首页,上一页,下一页,末页,当前页
	var firstPage=1;//首页
	var prePage;//上一页
	var nextPage;//下一页
	var lastPage;//末页
	var currPage=firstPage;//当前页
	$(document).ready(function(){
		//加载页面时就把这两个计数器置0
		counter_times=0;
		counter_previousVal=0;
		
		if('${page}'=="1"){
			$("#prePage").attr("disabled","disabled");
		}
		if('${page}'=='${totalPages}'){
			$("#nextPage").attr("disabled","disabled");
		}
	});
	/**
	*刷新本页面
	*/
	function reload(){
		window.location.href="${pageContext.request.contextPath }/customerManagement/allInfos?page=1";
	}
	/**
	*Sally:设置当前页码和末页页码,设置按钮状态
	*/
	function setCurrPageAndBtn(page){
		currPage=page;
		if(currPage==firstPage){
			$("#prePage").attr("disabled","disabled");
		}
		else{
			$("#prePage").removeAttr("disabled");
		}
		if(currPage==lastPage){
			$("#nextPage").attr("disabled","disabled");
		}
		else{
			$("#nextPage").removeAttr("disabled");
		}
	}
	/**
	*Sally:异步发送查询全部记录的请求
	*/
	function refresh(){
		$.post("${pageContext.request.contextPath }/customerManagement/queryAfterDML?page=${sessionScope.page}",function(allRecords){
			//将页面原本的数据删除（table heading不删除）
        	$("#wu-datagrid tr[id!='tHeading']").remove();
			//再循环添加
        	$.each(allRecords,function(index,element){
        		var rowElements="<tr class='tRow'>"+
						"<td><input id='"+element.customer_no+"' type='checkbox' name='checkedItem'/></td>"+
			    		"<td>"+element.customer_no+"</td>"+
			    		"<td>"+element.customer_name+"</td>"+
			    		"<td>"+element.address+"</td>"+
			    		"<td>"+element.tel+"</td>"+
			    		"<td>"+element.customer_type+"</td>"+
			    	"</tr>";
			    	$("#wu-datagrid").append(rowElements);
        	});				
		});
	}
	/**
	*S:准备获取用户要查询的用户信息(查询后，用户点击上一页，下一页等的通用方法)
	*/
	function findOneSeveralTimes(customername,currPage){
		//包装JSON对象
		var json_customername={"customer_name":customername};
		
		$.ajax({
	            type: "POST",
	            url:"${pageContext.request.contextPath }/customerManagement/findSpecified?page="+currPage,
	            data: JSON.stringify(json_customername),
	            dataType:"json",
	            contentType:"application/json;charset=UTF-8",
	            success:function(data){
					//将页面原本的用户数据删除
	            	$(".tRow").remove();
					//再循环添加
	            	$.each(data,function(index,element){
	            		var rowElements="<tr class='tRow'>"+
											"<td><input id='"+element.customer_no+"' type='checkbox' name='checkedItem'/></td>"+
								    		"<td>"+element.customer_no+"</td>"+
								    		"<td>"+element.customer_name+"</td>"+
								    		"<td>"+element.address+"</td>"+
								    		"<td>"+element.tel+"</td>"+
								    		"<td>"+element.customer_type+"</td>"+
								    	"</tr>";
					    	$("#wu-datagrid").append(rowElements);
	            	});
					setCurrPageAndBtn(currPage);
					changeOnClick();
	            }
			});		
	}
	/**
	* S:准备获取用户要查询的用户信息(通过调用findOneSeveralTimes方法修改上一页，下一页等按钮的onclick属性)
	*/
	function changeOnClick(){
		//获取用户输入的用户名
		var customername=$("#searchTextArea").val();
		//更改“首页”，“上一页”，“下一页”，“末页”四个按钮的“onclick”属性值
		$("#firstPage").attr("onClick","findOneSeveralTimes('"+customername+"',"+firstPage+");");
		$("#prePage").attr("onClick","findOneSeveralTimes('"+customername+"',"+(currPage-1)+");");
		$("#nextPage").attr("onClick","findOneSeveralTimes('"+customername+"',"+(currPage+1)+");");
		$("#lastPage").attr("onClick","findOneSeveralTimes('"+customername+"',"+lastPage+");");
	}
	/**
	* S:获取用户要查询的用户信息
	*/
    function findOneFirstTime() {
		
		var customername=$("#searchTextArea").val();
		
		var json_customername={"customer_name":customername};
		if(customername!=null && customername!=""){
			$.ajax({
	            type: "POST",
				//一开始查询,肯定是从第一页开始查,所以这里的page参数是第一页
	            url:"${pageContext.request.contextPath }/customerManagement/findSpecified?page="+firstPage,
	            data: JSON.stringify(json_customername),
	            dataType:"json",
	            contentType:"application/json;charset=UTF-8",
	            success:function(data){
					//将页面原本的用户数据删除
	            	$(".tRow").remove();
					//再循环添加
	            	$.each(data,function(index,element){
	            		var rowElements="<tr class='tRow'>"+
											"<td><input id='"+element.customer_no+"' type='checkbox' name='checkedItem'/></td>"+
								    		"<td>"+element.customer_no+"</td>"+
								    		"<td>"+element.customer_name+"</td>"+
								    		"<td>"+element.address+"</td>"+
								    		"<td>"+element.tel+"</td>"+
								    		"<td>"+element.customer_type+"</td>"+
								    	"</tr>";
					    	$("#wu-datagrid").append(rowElements);
							//成功后,回调方法里设置当前页码和按钮状态
	            	});
	            	//此ajax请求用来获取新的总页数值
	    			$.ajax({
	    	            type: "POST",
	    				//一开始查询,肯定是从第一页开始查,所以这里的page参数是第一页
	    	            url:"${pageContext.request.contextPath }/customerManagement/getNewTotalPages",
	    	            data: JSON.stringify(json_customername),
	    	            dataType:"json",
	    	            contentType:"application/json;charset=UTF-8",
	    	            success:function(data){
	    					//获取总页数
	    					lastPage=data.count;
	    					//设置页码和按钮状态
	    					setCurrPageAndBtn(firstPage);
	    					//修改上一页,下一页,首页,末页的click事件
	    					changeOnClick();
	    	            	}
	    			});
	            }
			});
			
		}
		else{
			$.messager.alert('信息提示','查询供应商名称不能为空！','info');
		}
	}

	/**
	* Name 删除记录
	*/
	function remove(){
		if($("input[name='checkedItem']:checked").length==0){
			$.messager.alert('信息提示','请在删除前选中至少一行！','info');
			return false;//退出方法
		}		
		$.messager.confirm('信息提示','确定要删除勾选的记录吗？', function(result){
			
			if(result){
				//创建数组保存JSON对象
				var arr_customerInfos=[];
				//将所有checkbox禁用
				$("input[name='checkedItem']").attr("disabled","disabled");
				//将待删除的customerId封装成JSON对象
				$("input[name='checkedItem']:checked").each(function(){
					var checkbox_id=$(this).attr("id");
					var customerInfo={"customer_no":checkbox_id};
					arr_customerInfos.push(customerInfo);
				});
				//发送AJAX请求
		 		$.ajax({
                    type: "POST",
                    url:"${pageContext.request.contextPath }/customerManagement/delete",
                    data: JSON.stringify(arr_customerInfos),
                    contentType:"application/json",
			 		success:function(data){
			 			refresh();//刷新页面
						$.messager.alert('信息提示','成功删除'+data.count+'行','info');
					}
                });
			}	
		});
		//新增用户时，用来生成用户ID的两个计数器置0
		counter_times=0;
		counter_previousVal=0;
	}
	
	function openAdd(){
		//将所有checkbox禁用
		$("input[name='checkedItem']").attr("disabled","disabled");
		$.post("${pageContext.request.contextPath }/customerManagement/getNextVal",function(data){
			//从数据库取出的即将添加的用户ID
			var customer_no = data.customer_no;
			//判断从数据库取出的nextVal和此方法生成的nextVal哪个比较大，哪个大取哪个(此做法是为了防止有其他人也在增加用户，导致customer_no重复的情况)
			var customer_no_db=parseInt(customer_no.substring(1));//from database(将第一个字母去掉)
			var customer_no_fn=counter_previousVal+counter_times;//generated by this function
			//如果数据库取出的大，则代表有人同时也在创建用户，可直接使用customer_no的值
			if(customer_no_db>customer_no_fn){
				//将times计数器设为0
				counter_times=0;
				var rowElements="<tr>"+
							"<td><input id='"+customer_no+"' type='checkbox' name='checkedItem' disabled='disabled' checked='checked'/></td>"+
					    		"<td><input type='text' name='customerId' value='"+customer_no+"' disabled='disabled'/></td>"+
					    		"<td><input type='text' name='customerName' autofocus='autofocus'/></td>"+
					    		"<td><input type='text' name='customerAddress'/></td>"+
					    		"<td><input type='text' name='customerTel'/></td>"+
					    		"<td><input type='text' name='customerType'/></td>"+
					    	"</tr>";
				//添加至表格	    	
				$("#wu-datagrid").append(rowElements);
			}
			//如果方法生成的大，则代表该用户在保存前生成了若干个新增行
			else{
				var customer_no_fn_string=customer_no_fn+"";
				var deduction=5-customer_no_fn_string.length;
				var prefix="";
				for(var i=0; i<deduction; i++){
					prefix=prefix+"0";
				}
				customer_no_fn_string="C"+prefix+customer_no_fn_string;//拼接成“V00005”样式的字符串
				var rowElements="<tr>"+
							"<td><input id='"+customer_no_fn_string+"' type='checkbox' name='checkedItem' disabled='disabled' checked='checked'/></td>"+
					    		"<td><input type='text' name='customerId' value='"+customer_no_fn_string+"' disabled='disabled'/></td>"+
					    		"<td><input type='text' name='customerName' autofocus='autofocus'/></td>"+
					    		"<td><input type='text' name='customerAddress'/></td>"+
					    		"<td><input type='text' name='customerTel'/></td>"+
					    		"<td><input type='text' name='customerType'/></td>"+
					    	"</tr>";
				//添加至表格	 
				$("#wu-datagrid").append(rowElements);
			}	
			
			//为下次添加做准备
			counter_previousVal=parseInt(customer_no.substring(1));//这次从数据库取出的值，供下次添加使用
			counter_times++;//执行完一次回调方法加1
		});	
	}
	
	/**
	* Name 打开修改窗口
	*/
	function openEdit(){
		if($("input[name='checkedItem']:checked").length==0){
			$.messager.alert('信息提示','请在修改前选中至少一行！','info');
			return false;//退出方法
		}
		//将所有checkbox禁用
		$("input[name='checkedItem']").attr("disabled","disabled");
		//创建input框name的数组
		var arr_inputname=["customerId","customerName","customerAddress","customerTel","customerType"];
		//获取所有被选checkbox的父元素(<tr>元素）
		$("input[name='checkedItem']:checked").each(function(){
			//获取tr
			var obj_parent=$(this).parent().parent();
			//获取tr下所有的td
			var arr_children=obj_parent.children();
			//除了复选框那个td,每个td下增加一个文本框，文本框里是的值是原来td的文本
			$(arr_children).each(function(index){
				if(index<=1){
					return true;//相当于continue;
				}
				//获取td文本
				var text=$(this).text();
				//删除td文本
				$(this).text("");
				//添加文本框
				$(this).append("<input type='text' name='"+arr_inputname[index-1]+"'>");
				//给文本框赋初始值
				$(this).find("input").attr("value",text);
			});
		});
	}	
	/**
	*Name 保存
	*/
	function save(){
		//新增用户时，用来生成用户ID的两个计数器置0
		counter_times=0;
		counter_previousVal=0;
		//创建数组保存JSON对象
		var arr_customerInfos=[];
		//已选复选框的数组
		var arr_checked=$("input[name='checkedItem']:checked");
		//把已选复选框的输入值包装成JSON对象，放入customerInfos数组里
		arr_checked.each(function(){
			//获取tr
			var obj_tr=$(this).parent().parent();
			//获取tr下所有的input元素
			var arr_input=obj_tr.find("input");
			//定义用户ID,用户名，等字段变量
			var customer_no=$(this).attr("id");//customerNo赋值为checkbox的id
			var customer_name,address,tel,customer_type;
			//给上述字段变量赋值
			arr_input.each(function(){
				var inputVal=$(this).val();
				if(customer_name==undefined)
				customer_name=($(this).attr("name")=="customerName")?inputVal:undefined;
				if(address==undefined)
				address=($(this).attr("name")=="customerAddress")?inputVal:undefined;
				if(tel==undefined)
				tel=($(this).attr("name")=="customerTel")?inputVal:undefined;
				if(customer_type==undefined)
				customer_type=($(this).attr("name")=="customerType")?inputVal:undefined;
			});
			var customerInfo = {
                     "customer_no":customer_no,
                     "customer_name":customer_name,
                     "address":address,
                     "tel":tel,
				 	 "customer_type":customer_type
            };
			//将包装好的JSON对象放入数组
			arr_customerInfos.push(customerInfo);
		});
		//发送AJAX请求
		 $.ajax({
                    type: "POST",
                    url:"${pageContext.request.contextPath }/customerManagement/updateAndSave",
                    data: JSON.stringify(arr_customerInfos),
                    contentType:"application/json",
			 		success:function(data){
			 			refresh();//刷新页面
						$.messager.alert('成功提示','成功保存'+data.count+'行！','info');
					}
                });
	}																					
	
	/**
	* Name 分页过滤器
	*/
	function pagerFilter(data){            
		if (typeof data.length == 'number' && typeof data.splice == 'function'){// is array                
			data = {                   
				total: data.length,                   
				rows: data               
			}            
		}        
		var dg = $(this);         
		var opts = dg.datagrid('options');          
		var pager = dg.datagrid('getPager');          
		pager.pagination({                
			onSelectPage:function(pageNum, pageSize){                 
				opts.pageNumber = pageNum;                   
				opts.pageSize = pageSize;                
				pager.pagination('refresh',{pageNumber:pageNum,pageSize:pageSize});                  
				dg.datagrid('loadData',data);                
			}          
		});           
		if (!data.originalRows){               
			data.originalRows = (data.rows);       
		}         
		var start = (opts.pageNumber-1)*parseInt(opts.pageSize);          
		var end = start + parseInt(opts.pageSize);        
		data.rows = (data.originalRows.slice(start, end));         
		return data;       
	}
</script>
</body>
</body>
</html>