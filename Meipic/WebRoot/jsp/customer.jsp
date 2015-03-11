<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>客户资料</title>
    
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/demo.css">
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/easyloader.js"></script>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	<link rel="stylesheet" type="text/css" href="css/panel_datagrid_layout.css">

  </head>
  
  <body>
	<div class="easyui-panel" title="客户资料" style="padding: 10px">
		<table style="width: 100%" >
			<tr>
				<td width="60px"><a id="searchAccountByAccountAndAgent" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-export'">导入EXCEL</a></td>
				<td style="width: 180"><a id="searchAccountByAccountAndAgent" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-export'">导出EXCEL</a></td>
				<td style="width: 180"><a id="searchAccountByAccountAndAgent" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">模板下载</a></td>
				<td style="width: 180"><a id="searchAccountByAccountAndAgent" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a></td>
			</tr>
		</table>
	    <table style="width: 100%" >
			<tr>
				<td width="60px">联系电话：</td>
				<td style="width: 180"><input id="transferPhoneForSearch" class="easyui-numberbox" style="width: 150px"></input></td>
				<td width="60px">客户姓名：</td>
				<td style="width: 180"><input id="transferPhoneForSearch" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">QQ号：</td>
				<td style="width: 180"><input id="areaForSearch" class="easyui-numberbox" value="" style="width: 150px"></input></td>
				<td width="60px">淘宝账号：</td>
				<td style="width: 180"><input id="areaForSearch" class="easyui-textbox" value="" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">京东账号：</td>
				<td style="width: 180"><input id="transferPhoneForSearch" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">微信号：</td>
				<td style="width: 180"><input id="transferPhoneForSearch" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">IMEI号：</td>
				<td style="width: 180"><input id="areaForSearch" class="easyui-numberbox" value="" style="width: 150px"></input></td>
				<td width="60px"></td>
				<td style="width: 180"><a id="searchAccountByAccountAndAgent" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a></td>
			</tr>
		</table>
		<table id="roleTable" class="easyui-datagrid" title="客户列表"
			data-options="singleSelect:true,collapsible:true,url:''">
			<thead>
				<tr>
					<th field="itemid" width="180">客户编号</th>    
		            <th field="productid" width="180">来点时间</th>
		            <th data-options="field:'role',align:'left',width:80">来电号码</th>
					<th data-options="field:'rightContent',align:'left',width:80">客户姓名</th>
					<th data-options="field:'id',width:100,align:'center'">联系电话</th>
					<th data-options="field:'id',width:100,align:'center'">IMEI号</th>
					<th data-options="field:'id',width:100,align:'center'">反馈类型</th>
					<th data-options="field:'id',width:100,align:'center'">反馈渠道</th>
					<th data-options="field:'id',width:100,align:'center'">QQ号</th>
					<th data-options="field:'id',width:100,align:'center'">归属地</th>
					<th data-options="field:'id',width:100,align:'center'">标题</th>
					<th data-options="field:'id',width:100,align:'center'">处理过程</th>
					<th data-options="field:'id',width:100,align:'center'">提问大类</th>
					<th data-options="field:'id',width:100,align:'center'">问题类别</th>
					<th data-options="field:'id',width:100,align:'center'">结案判定</th>
					<th data-options="field:'id',width:100,align:'center'">处理状态</th>
					<th data-options="field:'id',width:100,align:'center'">处理客服</th>
					<th data-options="field:'id',width:100,align:'center'">手机版本</th>
					<th data-options="field:'id',width:100,align:'center'">手机型号</th>
					<th data-options="field:'id',width:100,align:'center'">淘宝账号</th>
					<th data-options="field:'id',width:100,align:'center'">京东账号</th>
					<th data-options="field:'id',width:100,align:'center'">微信号</th>
					<th data-options="field:'id',width:100,align:'center'">微博地址</th>
				</tr>
			</thead>
		</table>
	</div>
  </body>
</html>
