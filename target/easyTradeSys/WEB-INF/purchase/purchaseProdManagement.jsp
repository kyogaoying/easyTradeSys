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
                <label>采购件编号：</label><input id="searchTextArea" class="wu-text" style="width:100px">
                <a class="easyui-linkbutton" iconCls="icon-search" onclick="findOneFirstTime()">开始检索</a>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="wu-datagrid" toolbar="#wu-toolbar">
        	<tr id="tHeading">
        		<th style="width: 50px">请选择</th>
        		<th>采购件编号<span class="mustFill">*</span></th>
        		<th>产品名称<span class="mustFill">*</span></th>
        		<th>产品英文名称<span class="mustFill">*</span></th>
        		<th>采购单价(￥)<span class="mustFill">*</span></th>
        		<th>供应商编号</th>
        		<th>产品状态(已销售:S 未销售:NS 已作废：R)</th>
			</tr>
			<c:forEach items="${allInfos}" var="info">
				<tr class="tRow">
					<td><input id="${info.prod_no}" type="checkbox" name="checkedItem"></td>
					<td>${info.prod_no}</td>
					<td>${info.prod_name}</td>
					<td>${info.prod_name_eng}</td>
					<td>${info.pur_price}</td>
					<td>${info.vendor_no}</td>
					<td>${info.prod_status}</td>
				</tr>
			</c:forEach>
        </table>
        <div id="turnPages">
        	<button class="turnPage" id="firstPage" onClick="javascript:void(window.location.href='${pageContext.request.contextPath }/purchaseProdManagement/allInfos?page=1')">首   页</button>
        	<button class="turnPage" id="prePage" onClick="javascript:void(window.location.href='${pageContext.request.contextPath }/purchaseProdManagement/allInfos?page=${sessionScope.page-1}')">上一页</button>
        	<button class="turnPage" id="nextPage" onClick="javascript:void(window.location.href='${pageContext.request.contextPath }/purchaseProdManagement/allInfos?page=${sessionScope.page+1}')">下一页</button>
        	<button class="turnPage" id="lastPage" onClick="javascript:void(window.location.href='${pageContext.request.contextPath }/purchaseProdManagement/allInfos?page=${sessionScope.totalPages }')">末   页</button>
        </div>
    </div>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript">
	/**
	* S:Name 添加一行
	*/
	var counter_times;//添加行计数器，记录提交前添加了几行，点击保存按钮，加载页面，数据库取的用户ID较大时，此值将被重置为0
	var counter_previousVal;//添加行计数器，记录上一次从数据库取出的prod_no是多少，点击保存按钮和加载页面时，此值将被重置为0
	//定义四个变量表示首页,上一页,下一页,末页,当前页
	var firstPage=1;//首页
	var prePage;//上一页
	var nextPage;//下一页
	var lastPage;//末页
	var currPage=firstPage;//当前页
	var commitable=true;//表示新的信息是否可以提交,默认为true
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
		window.location.href="${pageContext.request.contextPath }/purchaseProdManagement/allInfos?page=1";
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
		$.post("${pageContext.request.contextPath }/purchaseProdManagement/queryAfterDML?page=${sessionScope.page}",function(allRecords){
			//将页面原本的数据删除（table heading不删除）
        	$("#wu-datagrid tr[id!='tHeading']").remove();
			//再循环添加
        	$.each(allRecords,function(index,element){
        		var rowElements="<tr class='tRow'>"+
						"<td><input id='"+element.prod_no+"' type='checkbox' name='checkedItem'/></td>"+
			    		"<td>"+element.prod_no+"</td>"+
			    		"<td>"+element.prod_name+"</td>"+
			    		"<td>"+element.prod_name_eng+"</td>"+
			    		"<td>"+element.pur_price+"</td>"+
			    		"<td>"+element.vendor_no+"</td>"+
			    		"<td>"+element.prod_status+"</td>"+
			    	"</tr>";
			    	$("#wu-datagrid").append(rowElements);
        	});				
		});
	}
	/**
	* S:准备获取用户要查询的用户信息(通过调用findOneSeveralTimes方法修改上一页，下一页等按钮的onclick属性)
	*/
	function changeOnClick(){
		//获取用户输入的用户名
		var prodno=$("#searchTextArea").val();
		//更改“首页”，“上一页”，“下一页”，“末页”四个按钮的“onclick”属性值
		$("#firstPage").attr("onClick","findOneSeveralTimes('"+prodno+"',"+firstPage+");");
		$("#prePage").attr("onClick","findOneSeveralTimes('"+prodno+"',"+(currPage-1)+");");
		$("#nextPage").attr("onClick","findOneSeveralTimes('"+prodno+"',"+(currPage+1)+");");
		$("#lastPage").attr("onClick","findOneSeveralTimes('"+prodno+"',"+lastPage+");");
	}
	/**
	* S:获取用户要查询的用户信息
	*/
    function findOneFirstTime() {
		
		var prodno=$("#searchTextArea").val();
		
		var json_prodno={"prod_no":prodno};
		if(prodno!=null && prodno!=""){
			$.ajax({
	            type: "POST",
				//一开始查询,肯定是从第一页开始查,所以这里的page参数是第一页
	            url:"${pageContext.request.contextPath }/purchaseProdManagement/findSpecified?page="+firstPage,
	            data: JSON.stringify(json_prodno),
	            dataType:"json",
	            contentType:"application/json;charset=UTF-8",
	            success:function(data){
					//将页面原本的用户数据删除
	            	$(".tRow").remove();
					//再循环添加
	            	$.each(data,function(index,element){
	            		var rowElements="<tr class='tRow'>"+
											"<td><input id='"+element.prod_no+"' type='checkbox' name='checkedItem'/></td>"+
								    		"<td>"+element.prod_no+"</td>"+
								    		"<td>"+element.prod_name+"</td>"+
								    		"<td>"+element.prod_name_eng+"</td>"+
								    		"<td>"+element.pur_price+"</td>"+
								    		"<td>"+element.vendor_no+"</td>"+
								    		"<td>"+element.prod_status+"</td>"+
								    	"</tr>";
					    	$("#wu-datagrid").append(rowElements);
							//成功后,回调方法里设置当前页码和按钮状态
	            	});
	            	//此ajax请求用来获取新的总页数值
	    			$.ajax({
	    	            type: "POST",
	    				//一开始查询,肯定是从第一页开始查,所以这里的page参数是第一页
	    	            url:"${pageContext.request.contextPath }/purchaseProdManagement/getNewTotalPages",
	    	            data: JSON.stringify(json_prodno),
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
	*S:准备获取用户要查询的用户信息(查询后，用户点击上一页，下一页等的通用方法)
	*/
	function findOneSeveralTimes(prodno,currPage){
		//包装JSON对象
		var json_prodno={"prod_no":prodno};
		
		$.ajax({
	            type: "POST",
	            url:"${pageContext.request.contextPath }/purchaseProdManagement/findSpecified?page="+currPage,
	            data: JSON.stringify(json_prodno),
	            dataType:"json",
	            contentType:"application/json;charset=UTF-8",
	            success:function(data){
					//将页面原本的用户数据删除
	            	$(".tRow").remove();
					//再循环添加
	            	$.each(data,function(index,element){
	            		var rowElements="<tr class='tRow'>"+
											"<td><input id='"+element.prod_no+"' type='checkbox' name='checkedItem'/></td>"+
								    		"<td>"+element.prod_no+"</td>"+
								    		"<td>"+element.prod_name+"</td>"+
								    		"<td>"+element.prod_name_eng+"</td>"+
								    		"<td>"+element.pur_price+"</td>"+
								    		"<td>"+element.vendor_no+"</td>"+
								    		"<td>"+element.prod_status+"</td>"+
								    	"</tr>";
					    	$("#wu-datagrid").append(rowElements);
	            	});
					setCurrPageAndBtn(currPage);
					changeOnClick();
	            }
			});		
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
				var arr_purchaseProdInfos=[];
				//将所有checkbox禁用
				$("input[name='checkedItem']").attr("disabled","disabled");
				//将待删除的VendorId封装成JSON对象
				$("input[name='checkedItem']:checked").each(function(){
					var checkbox_id=$(this).attr("id");
					var purchaseProdInfo={"prod_no":checkbox_id};
					arr_purchaseProdInfos.push(purchaseProdInfo);
				});
				//发送AJAX请求
		 		$.ajax({
                    type: "POST",
                    url:"${pageContext.request.contextPath }/purchaseProdManagement/delete",
                    data: JSON.stringify(arr_purchaseProdInfos),
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
	/**
	*添加和修改时验证关联编号是否存在
	*/
	function verifyAddandEdit(obj){
		//获取用户输入
		var relatedNo=$(obj).val();
		//将用户输入封装为JSON对象
		var json_relatedNo={"vendor_no":relatedNo};
		//发送AJAX请求
		$.ajax({
                   type: "POST",
                   url:"${pageContext.request.contextPath }/purchaseProdManagement/verifyAddandEdit",
                   data: JSON.stringify(json_relatedNo),
                   contentType:"application/json",
			 	   success:function(data){
					   //关联号不存在,将字体设为红色提醒用户
			 		   if(data.result==false){
			 			  $(obj).val(relatedNo+"不存在!");
			 			  $(obj).css("color","red");
			 			  commitable=false;
					   }
					   //关联号存在
					   else{
						   $(obj).css("color","black");
						   commitable=true;
					   }
					}
               });
	}
	/**
	* Name 添加记录
	*/
	function openAdd(){
		//将所有checkbox禁用
		$("input[name='checkedItem']").attr("disabled","disabled");
		$.post("${pageContext.request.contextPath }/purchaseProdManagement/getNextVal",function(data){
			//从数据库取出的即将添加的用户ID
			var prod_no = data.prod_no;
			//判断从数据库取出的nextVal和此方法生成的nextVal哪个比较大，哪个大取哪个(此做法是为了防止有其他人也在增加用户，导致prod_no重复的情况)
			var prod_no_db=parseInt(prod_no.substring(1));//from database(将第一个字母去掉)
			var prod_no_fn=counter_previousVal+counter_times;//generated by this function
			//如果数据库取出的大，则代表有人同时也在创建用户，可直接使用prod_no的值
			if(prod_no_db>prod_no_fn){
				//将times计数器设为0
				counter_times=0;
				var rowElements="<tr>"+
							"<td><input id='"+prod_no+"' type='checkbox' name='checkedItem' disabled='disabled' checked='checked'/></td>"+
					    		"<td><input type='text' name='prodId' value='"+prod_no+"' disabled='disabled'/></td>"+
					    		"<td><input type='text' name='prodName' autofocus='autofocus'/></td>"+
					    		"<td><input type='text' name='prodNameEng'/></td>"+
					    		"<td><input type='text' name='purPrice'/></td>"+
					    		"<td><input type='text' name='vendorNo' onBlur='verifyAddandEdit(this);'/></td>"+
					    		"<td><input type='text' name='prodStatus'/></td>"+
					    	"</tr>";
				//添加至表格	    	
				$("#wu-datagrid").append(rowElements);
			}
			//如果方法生成的大，则代表该用户在保存前生成了若干个新增行
			else{
				var prod_no_fn_string=prod_no_fn+"";
				var deduction=6-prod_no_fn_string.length;
				var prefix="";
				for(var i=0; i<deduction; i++){
					prefix=prefix+"0";
				}
				prod_no_fn_string="P"+prefix+prod_no_fn_string;//拼接成“V00005”样式的字符串
				var rowElements="<tr>"+
							"<td><input id='"+prod_no_fn_string+"' type='checkbox' name='checkedItem' disabled='disabled' checked='checked'/></td>"+
					    		"<td><input type='text' name='prodId' value='"+prod_no_fn_string+"' disabled='disabled'/></td>"+
					    		"<td><input type='text' name='prodName' autofocus='autofocus'/></td>"+
					    		"<td><input type='text' name='prodNameEng'/></td>"+
					    		"<td><input type='text' name='purPrice'/></td>"+
					    		"<td><input type='text' name='vendorNo' onBlur='verifyAddandEdit(this);'/></td>"+
					    		"<td><input type='text' name='prodStatus'/></td>"+
					    	"</tr>";
				//添加至表格	 
				$("#wu-datagrid").append(rowElements);
			}	
			
			//为下次添加做准备
			counter_previousVal=parseInt(prod_no.substring(1));//这次从数据库取出的值，供下次添加使用
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
		var arr_inputname=["prodId","prodName","prodNameEng","purPrice","vendorNo","prodStatus"];
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
				//给vendorNo文本框添加是否存在的验证
				if(arr_inputname[index-1]=="vendorNo"){
					$(this).append("<input type='text' name='"+arr_inputname[index-1]+"' onBlur='verifyAddandEdit(this);'>");
				}
				else{
					$(this).append("<input type='text' name='"+arr_inputname[index-1]+"'>");
				}
				//给文本框赋初始值
				$(this).find("input").attr("value",text);
			});
		});
	}	
	/**
	*Name 保存
	*/
	function save(){
		if(commitable==true){
		//新增用户时，用来生成用户ID的两个计数器置0
		counter_times=0;
		counter_previousVal=0;
		//创建数组保存JSON对象
		var arr_purchaseProdInfos=[];
		//已选复选框的数组
		var arr_checked=$("input[name='checkedItem']:checked");
		//把已选复选框的输入值包装成JSON对象，放入purchaseProdInfos数组里
		arr_checked.each(function(){
			//获取tr
			var obj_tr=$(this).parent().parent();
			//获取tr下所有的input元素
			var arr_input=obj_tr.find("input");
			//定义用户ID,用户名，等字段变量
			var prod_no=$(this).attr("id");//vendorNo赋值为checkbox的id
			var prod_name,prod_name_eng,pur_price,vendor_no,prod_status;
			//给上述字段变量赋值
			arr_input.each(function(){
				var inputVal=$(this).val();
				if(prod_name==undefined)
				prod_name=($(this).attr("name")=="prodName")?inputVal:undefined;
				if(prod_name_eng==undefined)
				prod_name_eng=($(this).attr("name")=="prodNameEng")?inputVal:undefined;
				if(pur_price==undefined)
				pur_price=($(this).attr("name")=="purPrice")?inputVal:undefined;
				if(vendor_no==undefined)
				vendor_no=($(this).attr("name")=="vendorNo")?inputVal:undefined;
				if(prod_status==undefined)
				prod_status=($(this).attr("name")=="prodStatus")?inputVal:undefined;
			});
			var purchaseProdInfo = {
                     "prod_no":prod_no,
                     "prod_name":prod_name,
                     "prod_name_eng":prod_name_eng,
                     "pur_price":pur_price,
                     "vendor_no":vendor_no,
                     "prod_status":prod_status
            };
			//将包装好的JSON对象放入数组
			arr_purchaseProdInfos.push(purchaseProdInfo);
		});
		//发送AJAX请求
		 $.ajax({
                    type: "POST",
                    url:"${pageContext.request.contextPath }/purchaseProdManagement/updateAndSave",
                    data: JSON.stringify(arr_purchaseProdInfos),
                    contentType:"application/json",
			 		success:function(data){
			 			refresh();//刷新页面
						$.messager.alert('成功提示','成功保存'+data.count+'行！','info');
					}
                });
		}
		else{
			$.messager.alert('失败提示','页面包含验证未通过的内容,请先修改再提交!','info');
		}
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