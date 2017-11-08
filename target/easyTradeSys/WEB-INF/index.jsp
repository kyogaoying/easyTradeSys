<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="copyright" content="All Rights Reserved, Copyright (C) 2017, Sally, Ltd." />
<title>Easy-Trade 贸易管理系统</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/1.3.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/wu.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/icon.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
<title>Insert title here</title>
</head>
<body class="easyui-layout">
	<!-- begin of header -->
	<div class="wu-header" data-options="region:'north',border:false,split:true">
    	<div class="wu-header-left">
        	<h1>Easy-Trade 贸易管理系统</h1>
        </div>
        <div class="wu-header-right">
        	<p><strong class="easyui-tooltip" title="2条未读消息">${userInfo.user_name}</strong>，欢迎您！</p>
            <p><a href="http://bbs.fobshanghai.com/" target="_blank">支持论坛</a>|<a onClick="logOut()">安全退出</a></p>
        </div>
    </div>
    <!-- end of header -->
    <!-- begin of sidebar -->
	<div class="wu-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'"> 
    	<div class="easyui-accordion" data-options="border:false,fit:true"> 
        	<div title="快捷菜单" data-options="iconCls:'icon-application-cascade'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
                <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-users" data-link="userManagement/userInfos?page=1" iframe="1" fun-flag="SHORT002">用户管理</a></li>
                <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-user-group" data-link="menu/authority/roleManagement" iframe="1" fun-flag="SHORT003">角色管理</a></li>
                <li iconCls="icon-application-osx-error"><a href="javascript:void(0)" data-icon="icon-application-osx-error" data-link="temp/layout-3.html" iframe="1" fun-flag="SHORT006">操作日志</a></li>
            </ul>
        </div>
        <div title="销售管理" data-options="iconCls:'icon-application-form-edit'" style="padding:5px;">  	
			<ul class="easyui-tree wu-side-tree">
            	    <li iconCls="icon-vcard-edit"><a href="javascript:void(0)" data-icon="icon-vcard-edit" data-link="customerManagement/allInfos?page=1" iframe="1" fun-flag="SALE001">客户信息管理</a></li>
                	<li iconCls="icon-page-white-stack"><a href="javascript:void(0)" data-icon="icon-page-white-stack" data-link="quotationManagement/allInfos?page=1" iframe="1" fun-flag="SALE002">报价单管理</a></li>
                	<li iconCls="icon-page-white-stack"><a href="javascript:void(0)" data-icon="icon-page-white-stack" data-link="piManagement/allInfos?page=1" iframe="1" fun-flag="SALE003">销售合同管理</a></li>
                	<li iconCls="icon-link-go"><a href="javascript:void(0)" data-icon="icon-link-go" data-link="menu/sales/intOrderManagement" iframe="1" fun-flag="SALE004">内部订单管理</a></li>
            </ul>
        </div>
        <div title="采购管理" data-options="iconCls:'icon-cart-full'" style="padding:5px;">  	
			<ul class="easyui-tree wu-side-tree">
            	<li iconCls="icon-vcard-edit"><a href="javascript:void(0)" data-icon="icon-vcard-edit" data-link="purchaseVendorManagement/allInfos?page=1" iframe="1" fun-flag="PURCH001">供应商信息管理</a></li>
                <li iconCls="icon-page-white-stack"><a href="javascript:void(0)" data-icon="icon-page-white-stack" data-link="menu/purchase/purchaseOrderManagement" iframe="1" fun-flag="PURCH002">采购订单管理</a></li>
                <li iconCls="icon-book"><a href="javascript:void(0)" data-icon="icon-book" data-link="purchaseProdManagement/allInfos?page=1" iframe="1" fun-flag="PURCH003">产品信息管理</a></li>
            </ul>
        </div>
        <div title="船务报关管理" data-options="iconCls:'icon-package-go'" style="padding:5px;">  	
			<ul class="easyui-tree wu-side-tree">
            	<li iconCls="icon-text-list-numbers"><a href="javascript:void(0)" data-icon="icon-text-list-numbers" data-link="menu/shipment/packingList" iframe="1" fun-flag="SHIP001">Packing List</a></li>
                <li iconCls="icon-money-dollar"><a href="javascript:void(0)" data-icon="icon-money-dollar" data-link="menu/shipment/invoice" iframe="1" fun-flag="SHIP002">Commercial Invoice</a></li>
                <li iconCls="icon-text-list-numbers"><a href="javascript:void(0)" data-icon="icon-text-list-numbers" data-link="menu/shipment/customPackingList" iframe="1" fun-flag="SHIP003">报关箱单</a></li>
                <li iconCls="icon-money-dollar"><a href="javascript:void(0)" data-icon="icon-money-dollar" data-link="menu/shipment/customInvoice" iframe="1" fun-flag="SHIP004">报关发票</a></li>
            </ul>
        </div>
        <div title="仓库管理" data-options="iconCls:'icon-bricks'" style="padding:5px;">  	
			<ul class="easyui-tree wu-side-tree">
            	<li iconCls="icon-page-white-stack"><a href="javascript:void(0)" data-icon="icon-page-white-stack" data-link="menu/inventory/inventoryInManagement" iframe="1" fun-flag="INVENT001">入库单管理</a></li>
             	<li iconCls="icon-page-white-stack"><a href="javascript:void(0)" data-icon="icon-page-white-stack" data-link="menu/inventory/inventoryOutManagement" iframe="1" fun-flag="INVENT002">出库单管理</a></li>
             	<li iconCls="icon-magnifier"><a href="javascript:void(0)" data-icon="icon-magnifier" data-link="menu/inventory/inventoryInfoManagement" iframe="1" fun-flag="INVENT003">库存管理</a></li>
         	</ul>
        </div>
        <div title="财务管理" data-options="iconCls:'icon-database'" style="padding:5px;">  	
			<ul class="easyui-tree wu-side-tree">
            	<li iconCls="icon-lock-open"><a href="javascript:void(0)" data-icon="icon-lock-open" data-link="menu/finance/financeManagement" iframe="1" fun-flag="FINANCE001">销售合同解锁</a></li>
            </ul>
        </div>
        <div title="报单管理" data-options="iconCls:'icon-chart-curve'" style="padding:5px;">  	
			<ul class="easyui-tree wu-side-tree">
            	<li iconCls="icon-arrow-in-longer"><a href="javascript:void(0)" data-icon="icon-arrow-in-longer" data-link="summary/findAll" iframe="1" fun-flag="REPORT001">订单盈亏</a></li>
            </ul>
        </div>
        <div title="权限管理" data-options="iconCls:'icon-lock'" style="padding:5px;">  	
 			<ul class="easyui-tree wu-side-tree">
             <li iconCls="icon-page-white-stack"><a href="javascript:void(0)" data-icon="icon-page-white-stack" data-link="menu/authority/roleManagement" iframe="1" fun-flag="AUTH001">角色管理</a></li>
             <li iconCls="icon-page-white-stack"><a href="javascript:void(0)" data-icon="icon-page-white-stack" data-link="menu/authority/roleUserManagement" iframe="1" fun-flag="AUTH002">用户角色管理</a></li>
             <li iconCls="icon-page-white-stack"><a href="javascript:void(0)" data-icon="icon-page-white-stack" data-link="menu/authority/moduleManagement" iframe="1" fun-flag="AUTH003">模块管理</a></li>
             <li iconCls="icon-page-white-stack"><a href="javascript:void(0)" data-icon="icon-page-white-stack" data-link="menu/authority/moduleFunsManagement" iframe="1" fun-flag="AUTH004">模块功能管理</a></li>
             <li iconCls="icon-page-white-stack"><a href="javascript:void(0)" data-icon="icon-page-white-stack" data-link="menu/authority/roleAuthFunManagement" iframe="1" fun-flag="AUTH005">角色权限管理</a></li>
         </ul>
        </div>
        <div title="系统设置" data-options="iconCls:'icon-wrench'" style="padding:5px;">  	
			<ul class="easyui-tree wu-side-tree">
                <li iconCls="icon-cog-edit"><a href="javascript:void(0)" data-icon="icon-cog-edit" data-link="menu/sysConfig/sysConfig" iframe="1">修改密码</a></li>
            </ul>
        </div>
      </div>
    </div>	
    <!-- end of sidebar -->    
    <!-- begin of main -->
    <div class="wu-main" data-options="region:'center'">
        <div id="wu-tabs" class="easyui-tabs" data-options="border:false,fit:true">  
            <div title="首页">
            	<iframe src="menu/shortcut/frontPage" width="100%" height="100%"></iframe>
            </div>
        </div>
    </div>
    <!-- end of main --> 
    <!-- begin of footer -->
	<div class="wu-footer" data-options="region:'south',border:true,split:true">
    	&copy; 2017 All Rights Reserved
    </div>
    <!-- end of footer -->  
    <script type="text/javascript">
		$(function(){
			$('.wu-side-tree a').bind("click",function(){
				var title = $(this).text();
				var url = $(this).attr('data-link');
				var iconCls = $(this).attr('data-icon');
				var iframe = $(this).attr('iframe')==1?true:false;
				// 获取fun_flag的值
				var fun_flag = $(this).attr('fun-flag');
				
				var i = 0;
				// 从contorller的Model获取的对象
				var data = ${userAuth};
				var is_admin = ${is_admin};
				if(!is_admin)
				{
					// 遍历对象
					$.each(data,function(index,d)
					{
						// 如果相等,说明该用户有此模块功能的访问权限,则i++,退出循环
						if(fun_flag == d.fun_flag)
						{
							i++;
							return false;
						}
					});
					
					// 经过遍历之后,如果i仍然为0,则说明没有权限
					if(i == 0)
					{
						$.messager.alert('信息提示','你没有访问该功能的权限！','warn');
						return false;
					} 
				}
				addTab(title,url,iconCls,iframe);
			});	
		})
		
		/**
		* Name 载入树形菜单 
		*/
		$('#wu-side-tree').tree({
			url:'temp/menu.php',
			cache:false,
			onClick:function(node){
				var url = node.attributes['url'];
				if(url==null || url == ""){
					return false;
				}
				else{
					addTab(node.text, url, '', node.attributes['iframe']);
				}
			}
		});
		
		/**
		* Name 选项卡初始化
		*/
		$('#wu-tabs').tabs({
			tools:[{
				iconCls:'icon-reload',
				border:false,
				handler:function(){
					$('#wu-datagrid').datagrid('reload');
				}
			}]
		});
			
		/**
		* Name 添加菜单选项
		* Param title 名称
		* Param href 链接
		* Param iconCls 图标样式
		* Param iframe 链接跳转方式（true为iframe，false为href）
		*/	
		function addTab(title, href, iconCls, iframe){
			var tabPanel = $('#wu-tabs');
			if(!tabPanel.tabs('exists',title)){
				var content = '<iframe scrolling="auto" frameborder="0"  src="'+ href +'" style="width:100%;height:100%;"></iframe>';
				if(iframe){
					tabPanel.tabs('add',{
						title:title,
						content:content,
						iconCls:iconCls,
						fit:true,
						cls:'pd3',
						closable:true
					});
				}
				else{
					tabPanel.tabs('add',{
						title:title,
						href:href,
						iconCls:iconCls,
						fit:true,
						cls:'pd3',
						closable:true
					});
				}
			}
			else
			{
				tabPanel.tabs('select',title);
			}
		}
		/**
		* Name 移除菜单选项
		*/
		function removeTab(){
			var tabPanel = $('#wu-tabs');
			var tab = tabPanel.tabs('getSelected');
			if (tab){
				var index = tabPanel.tabs('getTabIndex', tab);
				tabPanel.tabs('close', index);
			}
		}
		/**
		*登出
		*/
		function logOut(){
			window.location.href="${pageContext.request.contextPath}/login.jsp";
		}
	</script>
</body>
</html>