<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>录音调听</title>
    
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
	<div class="easyui-panel" title="录音调听" style="padding: 10px">
		<table style="width: 100%" >
			<tr>
				<td width="60px">录音开始日期：</td>
				<td style="width: 180"><input id="transferPhoneForSearch" class="easyui-datebox" style="width: 150px"></input></td>
				<td width="60px">录音结束日期：</td>
				<td style="width: 180"><input id="transferPhoneForSearch" class="easyui-datebox" style="width: 150px"></input></td>
				<td width="60px">来电号码：</td>
				<td style="width: 180"><input id="areaForSearch" class="easyui-numberbox" value="" style="width: 150px"></input></td>
			</tr>
		</table>
		<table style="width: 100%" >
			<tr>
				<td width="240px">请选择并点击批量下载到指定路径：</td>
				<td style="width: 240"><a id="searchAccountByAccountAndAgent" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">批量下载</a>
				<a id="searchAccountByAccountAndAgent" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-export'">导出全部报表</a>
				</td>
				<td width="50px">满意度评价：</td>
				<td style="width: 150"><input id="transferPhoneForSearch" class="easyui-combobox" style="width: 150px"></input></td>
				<td width="20px"><a id="searchAccountByAccountAndAgent" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a></td>
			</tr>
		</table>
		<table id="roleTable" class="easyui-datagrid" title="录音列表"
			data-options="singleSelect:true,collapsible:true,url:''">
			<thead>
				<tr>
					<th field="itemid" width="180">录音编号</th>    
		            <th field="productid" width="180">工单流水</th>
		            <th data-options="field:'role',align:'left',width:80">录音日期</th>
					<th data-options="field:'rightContent',align:'left',width:80">开始时间</th>
					<th data-options="field:'id',width:100,align:'center'">声音时长</th>
					<th data-options="field:'id',width:100,align:'center'">通话类型</th>
					<th data-options="field:'id',width:100,align:'center'">from</th>
					<th data-options="field:'id',width:100,align:'center'">to</th>
					<th data-options="field:'id',width:100,align:'center'">座席满意度评价</th>
					<th data-options="field:'id',width:100,align:'center'">播放</th>
					<th data-options="field:'id',width:100,align:'center'">下载</th>
				</tr>
			</thead>
		</table>
	
	</div>
  </body>
</html>
