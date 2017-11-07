<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
// JavaScript Document
// 当前页码
var selCurrPage = 1;
// 总页数
var selTotalPages = 0;
// 查询的模块名
var selModule = "";
// 选择列表中的主键名
var selKey = "";
//
var targetObj;
//
var relation_columns = [];
// 初始化查询条件的input
function initSearch(className)
{
	var searchElement = "";
	// 清空之前的表头
	$("#conditionSpan").children().remove();
	// 根据触发事件对象的class的值来分别定义查询条件元素
	// is-key属性等于1代表选中某行数据点击确定时回写到触发事件的input的value的列，0代表不写入
	if(className == "inventoryInfo")
	{
		searchElement = "<label>产品编号:</label><input name='prod_no' is-key='1' class='wu-text' style='width:100px'>"+
					    "<label>产品名称:</label><input name='prod_name' is-key='0' class='wu-text' style='width:100px'>"
	}
	else if(className == "userManagement")
	{
		searchElement = "<label>用户ID:</label><input name='user_no' is-key='1' class='wu-text' style='width:100px'>"+
					    "<label>用户名:</label><input name='user_name' is-key='0' class='wu-text' style='width:100px'>"
	}
	else if(className == "role")
	{
		searchElement = "<label>角色ID:</label><input name='role_no' is-key='1' class='wu-text' style='width:100px'>"+
					    "<label>角色名:</label><input name='role_name' is-key='0' class='wu-text' style='width:100px'>"
	}
	else if(className == "module")
	{
		searchElement = "<label>模块ID:</label><input name='module_no' is-key='1' class='wu-text' style='width:100px'>"+
					    "<label>模块名:</label><input name='module_name' is-key='0' class='wu-text' style='width:100px'>"
	}
	else if(className == "moduleFuns")
	{
		searchElement = "<label>模块ID:</label><input name='module_no' is-key='0' class='wu-text' style='width:100px'>"+
					    "<label>模块名:</label><input name='module_name' is-key='0' class='wu-text' style='width:100px'>"+
						"<label>功能ID:</label><input name='fun_flag' is-key='1' class='wu-text' style='width:100px'>"+
					    "<label>功能名:</label><input name='fun_desc' is-key='0' class='wu-text' style='width:100px'>"
	}
	// 
	$("#conditionSpan").append(searchElement);
}

// 初始化选择列表表头
function initRowHead(className)
{
	// 清空之前的表头
	$("#sHeading").remove();
	// 存储表头的元素
	var rowHead = "";
	// 找到查询条件部分所包含的label对象
	var labels = $("#selectSearch").find("label");
	// 找到查询条件部分所包含的input对象
	var inputs = $("#selectSearch").find("input");
	
	rowHead = "<tr id='sHeading'>"+"<th style='width: 50px'>请选择</th>";
	// 遍历查询条件部分所包含的label对象
	$(labels).each(function(index,element){
		// 根据label包含的文本来初始化表头文本(去掉冒号)
		rowHead += "<th>"+$(element).text().replace(":","")+"</th>";
		// 获取当前label同级的input对象
		var input = inputs[index];
		// 获取is_key的值
		var is_key = $(input).attr("is-key");
		// 如果为1
		if(is_key == "1")
		{
			// 将此input的name属性作为列表中的主键名
			selKey = $(input).attr("name");
		}
		
	});			  
	rowHead += "</tr>";
	// 将模块名设置成触发pop对象的class名
	selModule = className;
	// 向表格添加行
	$("#selectTable").append(rowHead);

	return rowHead;
}

// 重置所有变量,解除绑定事件
function clearArgs()
{
	selCurrPage = 1;
	selTotalPages = 0;
	relation_columns = [];
	selModule = ""
	selKey = "";
	// 取消确定按钮的单击事件
	$("#confirmSelectBtn").unbind("click");
	// 取消取消按钮的单击事件
	$("#cancelBtn").unbind("click");
	// 取消首页按钮的单击事件
	$("#selectFirstPage").unbind("click");
	// 取消上一页按钮的单击事件
	$("#selectPrePage").unbind("click");
	// 取消下一页按钮的单击事件
	$("#selectNextPage").unbind("click");
	// 取消末页按钮的单击事件
	$("#selectLastPage").unbind("click");
	// 取消选择列表查询按钮的单击事件
	$("#selectQuery").unbind("click");
}

// 
function pop(obj)
{
	targetObj = obj;
	clearArgs();
	// 设置弹出选择列表框的位置
	makeCenter();
	// 移除之前已经生成的列表行(如果存在的话)
	$(".dataRows").remove();
	var className = $(obj).attr("class");
	var rc = $(obj).attr("related-column");
	if(typeof(rc)!="undefined")
	{
		relation_columns = rc.split(";");
	}
	initSearch(className);
	// 初始化选择列表表头 
	initRowHead(className);
	// 设置确定按钮的单击事件
	$("#confirmSelectBtn").click(function(){doSelect(targetObj);});
	// 设置取消按钮的单击事件
	$("#cancelBtn").click(function(){hide();});
	// 设置首页按钮的单击事件
	$("#selectFirstPage").click(function(){
		selCurrPage = 1;
		selectRefresh(selCurrPage);
	});
	// 设置上一页按钮的单击事件
	$("#selectPrePage").click(function(){
		selCurrPage--;
		selectRefresh(selCurrPage);
	});
	// 设置下一页按钮的单击事件
	$("#selectNextPage").click(function(){
		selCurrPage++;
		selectRefresh(selCurrPage);
	});
	// 设置末页按钮的单击事件
	$("#selectLastPage").click(function(){
		selectRefresh(selTotalPages);
	});
	// 选择列表查询按钮的单击事件
	$("#selectQuery").click(function(){
		selCurrPage = 1;
		selectRefresh(selCurrPage);
	});

	// 刷新选择列表的数据
	selectRefresh(selCurrPage);
}

// 刷新数据
function selectRefresh(selCurrPage)
{
	// 移除之前已经生成的列表行(如果存在的话)
	$("#selectTable tr[id!='sHeading']").remove();
	// 查找所有查询条件的input
	var condition_input = $("#selectSearch").find("input");
	// 定义查询条件数组
	var condition = [];
	// 存储查询条件的input的name属性的数组
	var input_names = [];
	// 遍历所有查询条件的input
	$(condition_input).each(function(index,element){
		// 定义每一个input对应的查询条件对象
		var condition_element = {"condition_key":$(element).attr("name"),"condition_value":$(element).val(),"condition_type":"string"};
		// 添加到查询条件数组
		condition.push(condition_element);
		// 添加到存储查询条件的input的name属性的数组
		input_names[index] = $(element).attr("name");
	});
	// 如果有设置关联的列
	if(relation_columns.length > 0)
	{
		// 获取当前对象所在的tr对象
		var tr = $(targetObj).parent().parent();
		// 遍历设置了关联的列
		$(relation_columns).each(function(i,e){
			// 遍历所有查询条件的input
			$(input_names).each(function(index,element){
				// 如果input_name和关联列的值相同
				if(element == e)
				{
					// 根据tr对象获取input的name等于关联列名的value值
					var condition_value = $(tr).find("input[name='"+element+"']").val();
					// 使用关联列的值替换查询条件数组中已有的条件的值
					condition.splice(index,1, {"condition_key":element,"condition_value":condition_value,"condition_type":"string"});
				}
			});
		});
	}
	// 向服务器发送ajax请求
	$.ajax({
		type: "POST",
		url:"${pageContext.request.contextPath }/"+selModule+"/find?currPage="+selCurrPage,
		data: JSON.stringify(condition),
		dataType:"json",
		contentType:"application/json;charset=UTF-8",
		success:function(data){
				var total = data.total;
				selTotalPages = data.totalPages;
				var rows = data.rows;
				// 遍历数据行
				$.each(rows,function(index,d){
					var id = d[selKey];
					// 将要添加的行内容
					var rowElements="<tr class='dataRows'>"+
										"<td><input type='radio' id='"+id+"' name='checkedSelectItem'/></td>";
					// 遍历input_name数组
					$.each(input_names,function(index,element){
						// 根据input_name数组中的名称,以及根据名称获取其中的值,逐行拼接
						rowElements += "<td name='"+element+"'>"+d[element]+"</td>";
					});

					rowElements += "</tr>";
					// 添加到表
					$("#selectTable").append(rowElements);
				});
				
				// 根据当前页数和总页数来对上一页,下一页的按钮进行控制
				if(selCurrPage == 1)
				{
					$("#selectPrePage").attr("disabled",true);
				}
				else
				{
					$("#selectPrePage").attr("disabled",false);
				}

				if(selCurrPage == selTotalPages || total == 0)
				{
					$("#selectNextPage").attr("disabled",true);
				}
				else
				{
					$("#selectNextPage").attr("disabled",false);
				}
				// 设置页数
				$("#selectCurrPage").text("当前第"+selCurrPage+"页,共"+selTotalPages+"页");
		  }
	});
}

//设置弹出窗口的显示位置  
function makeCenter()  
{    
	$('#selectDiv').css("display","block");    
	$('#selectDiv').css("position","absolute");    
	$('#selectDiv').css("top", "0px");    
	var left = ($(document).width() - $('#selectDiv').width())/2;   
	$('#selectDiv').css("left", left+"px");    
}
//隐藏窗口    
function hide() {
	// 隐藏
	$('#selectDiv').css("display", "none");
}    
//获取选择值    
function doSelect(obj) {
	// 获取选择列表中选中的对象
	var checkedSelectItem = $("input[name='checkedSelectItem']:checked");
	var id = "";
	// 遍历选择列表中选中的对象
	$(checkedSelectItem).each(function(index,element){
		id = $(element).attr("id");
		return;
	});
	// 将选中的行的指定字段的值赋给触发弹出选择列表的input 
	$(obj).val("").val(id);
	//关闭弹窗    
	hide();
}  