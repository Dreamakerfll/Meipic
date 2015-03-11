<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=utf-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String isMonitor = request.getParameter("isMonitor");
String account = request.getParameter("account");
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>黑名单</title>
	
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
						deleteBlacklistById($('#delete_id').val());
					}
				},{
					text:'取消',
					handler:function(){
						$('#confirm_delete').dialog('close');
					}
				}]" style="width:300px;height:auto;padding:10px">
		<table style="width: 100%" >
			<tr>
				<td>确定删除此条黑名单？</td>
				<td><input type="text" id="delete_id" style="display:none"/></td>
				
			</tr>
		</table>
	</div>
	
	<%-- 审核黑名单的弹出层 --%>
	<div id="check_blacklist" class="easyui-dialog" title="审核黑名单" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						checkBlacklistById();
					}
				},{
					text:'取消',
					handler:function(){
						$('#check_blacklist').dialog('close');
					}
				}]" style="width:300px;height:auto;padding:10px">
		<table style="width: 100%" >
			<tr>
				<td>班长备注：</td>
				<td><input type="text" id="check_monitor_comment" class="easyui-textbox" style="width:146px"/></td>
			</tr>
			<tr>
				<td>审核状态：</td>
				<td><input id="check_status" style="width:150px"/><input id="check_id" class="easyui-textbox" style="display:none"/></td>
			</tr>
		</table>
	</div>
	
	<%-- 添加黑名单的弹出层 --%>
	<div id="add_blacklist" class="easyui-dialog" title="添加黑名单" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						addNewBlacklist();
					}
				},{
					text:'取消',
					handler:function(){
						$('#add_blacklist').dialog('close');
					}
				}]" style="width:300px;height:auto;padding:10px">
		<table style="width: 100%" >
			<tr>
				<td>开始时间：</td>
				<td><input id="begin_time_add" class="easyui-datetimebox" editable="false" style="width:150px"/></td>
			</tr>
			<tr>
				<td>结束时间：</td>
				<td><input id="end_time_add" class="easyui-datetimebox" editable="false" style="width:150px"/></td>
			</tr>
			<tr>
				<td>电话号码：</td>
				<td><input id="phone_number_add" class="easyui-validatebox" data-options="required:true,validType:['number']" style="width:146px"/></td>
			</tr>
			<tr>
				<td>坐席备注：</td>
				<td><input id="comment_add" class="easyui-textbox" style="width:146px"/></td>
			</tr>
		</table>
	</div>
	
	<%-- 编辑黑名单的弹出层 --%>
	<div id="edit_blacklist" class="easyui-dialog" title="编辑黑名单" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						editBlacklist();
					}
				},{
					text:'取消',
					handler:function(){
						$('#edit_blacklist').dialog('close');
					}
				}],closed:true,modal:true" style="width:300px;height:auto;padding:10px">
		<table style="width: 100%" >
			<tr>
				<td>开始时间：</td>
				<td><input id="begin_time_edit" class="easyui-datetimebox" editable="false" style="width:150px"/>
				<input type="text" id="edit_id" style="display:none"/></td>
			</tr>
			<tr>
				<td>结束时间：</td>
				<td><input id="end_time_edit" class="easyui-datetimebox" editable="false" style="width:150px"/></td>
			</tr>
			<tr>
				<td>电话号码：</td>
				<td><input id="phone_number_edit" class="easyui-validatebox" data-options="validType:['number']" style="width:146px"/></td>
			</tr>
			<tr>
				<td>坐席备注：</td>
				<td><input id="comment_edit" class="easyui-textbox" style="width:146px"/></td>
			</tr>
		</table>
	</div>
	
	<script type="text/javascript">
		$('.loading').animate({'width':'55%'},50);  //第二个节点
	</script>
	
	<div class="easyui-panel" title="黑名单" style="padding: 10px">
		<table style="width: 100%" >
			<tr>
				<td width="60px">开始时间：</td>
				<td style="width: 29%"><input id="begin_time" editable="false" class="easyui-datetimebox" value="" style="width:150px"></input></td>
				<td width="60px">结束时间：</td>
				<td style="width: 29%"><input id="end_time" editable="false" class="easyui-datetimebox" value="" style="width:150px"></input></td>
				<td width="60px">电话号码：</td>
				<td><input id="phone_number" class="easyui-validatebox" data-options="validType:['number']" style="width:146px"></input></td>
			</tr>
			<tr>
				<td>提交人：</td>
				<td><input id="submit_person" class="easyui-textbox" style="width:146px"></input></td>
				<td>座席备注：</td>
				<td><input id="agent_comment" class="easyui-textbox" style="width:146px"></input></td>
				<td>审核状态：</td>
				<td><input id="check_status_for_search" style="width:150px"/></td>
				<td><a id="search_blacklist_btn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查找</a></td>
			</tr>
		</table>
		
		<table id="blacklist" title="黑名单"
			data-options="pagination:true,singleSelect:true,url:'blacklist.do?method=findAll',toolbar:toolbar">
		<thead>
			<tr>
				<th data-options="field:'beginTime',width:123,align:'center'">开始时间</th>
				<th data-options="field:'endTime',width:123,align:'center'">结束时间</th>
				<th data-options="field:'phoneNumber',width:95,align:'right'">联系号码</th>
				<th data-options="field:'agentComment',width:105">座席备注</th>
				<th data-options="field:'status',width:50,formatter:function(value,row)
								{if(value=='0'){
								  return '未审核';
								}else if(value=='1'){
								 return '已通过';
								}else{
								 return '未通过';
								}},align:'center'">状态</th>
				<th data-options="field:'monitorComment',width:105">班长备注</th>
				<th data-options="field:'submitPerson',width:65,align:'center'">提交人</th>
				<th data-options="field:'checkPerson',width:65,align:'center'">审核人</th>
				<th data-options="field:'submitTime',width:125,align:'center'">提交时间</th>
				<th data-options="field:'id',width:180,formatter:rowformater,align:'center'">操作</th>
			</tr>
		</thead>
	</table>
	</div>
	
	<script type="text/javascript">
    	$('.loading').animate({'width':'78%'},50);  //第三个节点
	</script>

	<script type="text/javascript" src="js/pages/blacklist.js"></script>
	
	<script type="text/javascript">
    	$('.loading').animate({'width':'100%'},50);  //第四个节点
	</script>
	
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
