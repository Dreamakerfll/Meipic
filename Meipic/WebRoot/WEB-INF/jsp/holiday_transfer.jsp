<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String isMonitor = request.getParameter("isMonitor");
String account = request.getParameter("account");
//String selectArea = (String)request.getAttribute("selectArea");
//String currentSelectArea = (String)request.getAttribute("currentSelectArea");
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>假日转接</title>
    
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/demo.css">
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/easyloader.js"></script>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	<link rel="stylesheet" type="text/css" href="css/panel_datagrid_layout.css">
	<link rel="stylesheet" type="text/css" href="css/cover.css">

</head>
  
  <body onload="hideOverlay()">
  
	<div class="loading"></div>
	<%-- 遮罩层 --%>
	<div class="cover"></div>
    <script type="text/javascript">
    $('.loading').animate({'width':'33%'},50);  //第一个进度节点
    // fadeTo第一个参数为速度，第二个为透明度
    // 多重方式控制透明度，保证兼容性，但也带来修改麻烦的问题
    $(".cover").fadeTo(0, 1);
	</script>
	
    <%-- 确定删除的弹出层 --%>
	<div id="confirm_delete" class="easyui-dialog" title="系统消息" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						deleteTransferByBatchId($('#delete_id').val());
					}
				},{
					text:'取消',
					handler:function(){
						$('#confirm_delete').dialog('close');
					}
				}]" style="width:300px;height:auto;padding:10px">
		<table style="width: 100%" >
			<tr>
				<td>确定删除此条转接信息？</td>
				<td><input type="text" id="delete_id" style="display:none"/></td>
			</tr>
		</table>
	</div>
	<%--
	
	 选择区域的弹出层 
	<div id="selectArea" class="easyui-dialog" title="选择区域" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						copyItem('#previewItem','#makeSureItem');
						$('#selectArea').dialog('close');
					}
				},{
					text:'取消',
					handler:function(){
						$('#selectArea').dialog('close');
					}
				}]" style="width:400px;height:auto;padding:10px">
			
		<%=selectArea %>
	</div>
	
	 当前用户选择区域的弹出层 
	<div id="currentSelectArea" class="easyui-dialog" title="选择区域" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						currentCopyItem('#currentPreviewItem','#currentMakeSureItem');
						$('#currentSelectArea').dialog('close');
					}
				},{
					text:'取消',
					handler:function(){
						$('#currentSelectArea').dialog('close');
					}
				}]" style="width:400px;height:auto;padding:10px">
		<%=currentSelectArea %>
		
	</div>
  
  --%>
  <%-- 添加转接的弹出层 --%>
 	 <div id="addTransfer" class="easyui-dialog" title="添加转接" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						addTransferToDb();
					}
				},{
					text:'取消',
					handler:function(){
						$('#addTransfer').dialog('close');
					}
				}]" style="width:300px;height:auto;padding:10px">
			<table style="width: 100%" >
			<tr>
				<td>转接号码：</td>
				<td><input id="phoneToAdd" class="easyui-numberbox" style="width:146px"></input></td>
			</tr>
			<tr>
				<td>开始时间：</td>
				<td><input id="beginTimeToAdd" class="easyui-datetimebox" editable="false" style="width:150px"></input></td>
			</tr>
			<tr>
				<td>结束时间：</td>
				<td><input id="endTimeToAdd" class="easyui-datetimebox" editable="false" style="width:150px"></input></td>
			</tr>
			<tr>
				<td>状态：</td>
				<td><input id="statusToAdd" style="width:150px"></input></td>
			</tr>
			</table><%--
			<table>
			<tr>
				<td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="$('#selectArea').dialog('open')">选择区域</a></td>
			</tr>
			<tr>
				<td>所属区域：</td>
			</tr>
			</table>
			<div id="makeSureItem"></div>
	--%></div>
	
	<%-- 编辑转接的弹出层 --%>
 	 <div id="editTransfer" class="easyui-dialog" title="编辑转接" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						editTransferToDb();
					}
				},{
					text:'取消',
					handler:function(){
						$('#editTransfer').dialog('close');
					}
				}]" style="width:300px;height:auto;padding:10px">
			<table style="width: 100%" >
			<tr>
				<td>转接号码：</td>
				<td><input id="phoneToEdit" class="easyui-numberbox" style="width:146px"></input></td>
			</tr>
			<tr>
				<td>开始时间：</td>
				<td><input id="beginTimeToEdit" class="easyui-datetimebox" editable="false" style="width:150px"></input></td>
			</tr>
			<tr>
				<td>结束时间：</td>
				<td><input id="endTimeToEdit" class="easyui-datetimebox" editable="false" style="width:150px"></input></td>
			</tr>
			<tr>
				<td>状态：</td>
				<td><input id="statusToEdit" style="width:150px"></input><input id="submitTimeToEdit" class="easyui-textbox" style="display:none"></input><input id="batchIdToEdit" class="easyui-textbox" style="display:none"></input></td>
			</tr>
			</table>
			<%--<table>
			<tr>
				<td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="openCurrentSelectArea()">选择区域</a></td>
			</tr>
			<tr>
				<td>所属区域：</td>
			</tr>
			</table>
			<div id="currentMakeSureItem"></div>
			<div id="currentMakeSureItem_old" style="display:none"></div>
	--%></div>
	
	<script type="text/javascript">
		$('.loading').animate({'width':'55%'},50);  //第二个节点
	</script>
	
    <div  class="easyui-panel" title="假日转接" style="padding: 10px">
		<table style="width: 100%" >
			<tr>
				<td width="60px">转接号码：</td>
				<td style="width: 180"><input id="transferPhoneForSearch" class="easyui-numberbox" style="width: 146px"></input>
				<input id="areaForSearch" class="easyui-textbox" value="" style="display:none"></input></td>
				<%--<td width="60px">设定区域：</td>
				<td style="width: 180"><input id="areaForSearch" class="easyui-textbox" value="" style="width: 146px"></input></td>
				--%><td width="60px">开始时间：</td>
				<td style="width: 180"><input id="beginTimeForSearch" editable="false" class="easyui-datetimebox" value="" style="width:150px"></input></td>
				<td width="60px">结束时间：</td>
				<td style="width: 180"><input id="endTimeForSearch" editable="false" class="easyui-datetimebox" value="" style="width:150px"></input></td>
				<td><a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchByCondition()" data-options="plain:true,iconCls:'icon-search'">查询</a></td>
			</tr>
		</table>
		
		<table id="transfer" title="假日转接"
			data-options="toolbar:toolbar">
		<thead>
			<tr>
				<th data-options="field:'id',width:80,align:'left',hidden:true">ID</th>
				<th data-options="field:'account',width:50,align:'left'">用户姓名</th>
				<th data-options="field:'transferNumber',width:80,align:'right'">转接号码</th>
				<th data-options="field:'beginTime',width:90,align:'center'">开始时间</th>
				<th data-options="field:'endTime',width:90,align:'center'">结束时间</th>
				<th data-options="field:'transferArea',width:140,align:'left',hidden:true">区域</th>
				<th data-options="field:'transferAreaId',width:80,hidden:true">区域ID</th>
				<th data-options="field:'status',width:40,align:'center',formatter:function(value,row)
									{if(value=='1'){
									  return '已启用';
									}else{
									 return '未启用';
									}},align:'center'">状态</th>
				<th data-options="field:'submitTime',width:90,align:'center'">提交时间</th>
				<th data-options="field:'transferBatch',width:90,align:'center',hidden:true">BatchId</th>
				<th data-options="field:'xxx',width:140,align:'center',formatter:formatter1">操作</th>
			</tr>
		</thead>
	</table>
	</div>
	
	<script type="text/javascript">
    	$('.loading').animate({'width':'78%'},50);  //第三个节点
	</script>
	
	<script type="text/javascript" src="js/pages/holiday_transfer.js"></script>
	
	<script type="text/javascript">
    $(document).ready(function(){
        $('.loading').fadeOut();
    });
    /* 隐藏覆盖层 */
    function hideOverlay() {
    	$('.cover').fadeOut(200);
    };
	</script>
  </body>
</html>
