<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=utf-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
  </head>
  
   <body>
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
				<td><input id="phone_number_add" class="easyui-validatebox" data-options="required:true" style="width:146px"/></td>
			</tr>
			<tr>
				<td>备注：</td>
				<td><input id="comment_add" class="easyui-textbox" style="width:146px"/></td>
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
				<td><input id="phone_number" class="easyui-numberbox" style="width:146px"></input></td>
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
			data-options="pagination:true,singleSelect:true,url:'',toolbar:toolbar">
		<thead>
			<tr>
				<th data-options="field:'beginTime',width:125,align:'center'">开始时间</th>
				<th data-options="field:'endTime',width:125,align:'center'">结束时间</th>
				<th data-options="field:'phoneNumber',width:100,align:'right'">联系号码</th>
				<th data-options="field:'agentComment',width:120">座席备注</th>
				<th data-options="field:'status',width:50,formatter:function(value,row)
								{if(value=='0'){
								  return '未审核';
								}else if(value=='1'){
								 return '已通过';
								}else{
								 return '未通过';
								}},align:'center'">状态</th>
				<th data-options="field:'monitorComment',width:120">班长备注</th>
				<th data-options="field:'submitPerson',width:65,align:'center'">提交人</th>
				<th data-options="field:'checkPerson',width:65,align:'center'">审核人</th>
				<th data-options="field:'submitTime',width:125,align:'center'">提交时间</th>
				<th data-options="field:'id',width:125,formatter:rowformater,align:'center'">操作</th>
			</tr>
		</thead>
	</table>
	</div>
	<script type="text/javascript">
		$(function(){
			$('#confirm_delete').dialog({
	            closed: true,   
	            cache: false,
	            modal: true
	        }); 
	    	$('#check_blacklist').dialog({
	    		closed: true,
	    		cache: false,
	    		modal: true
	    	}); 
	    	$('#add_blacklist').dialog({
	    		closed: true,
	    		cache: false,
	    		modal: true
	    	}); 
			});
	    var toolbar = [{
			text:'添加黑名单',
			iconCls:'icon-add',
			handler:function(){

	    	openAddBlacklistDialog();
	    	
			}
		}];

		function rowformater(value,row,index)
		{
			var isMonitor = GetQueryString("isMonitor");
			if(isMonitor == '1')
			{
				return "<a class='editcls2' onclick='checkRow("+ row.id+","+row.status+",\""+row.monitorComment +"\")' href='javascript:void(0)'></a>"+
				"<a class='editcls' onclick='editRow("+ row.id +",\""+row.submitPerson+"\",\""+row.status +"\")'href='javascript:void(0)'></a>";
			}
			else
			{
				return "<a class='editcls' onclick='editRow("+ row.id +",\""+row.submitPerson+"\",\""+row.status +"\")' href='javascript:void(0)'></a>";
			}
		};

		$('#blacklist').datagrid({
            pageSize : 10,//默认选择的分页是每页5行数据
            pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
            striped : true,//设置为true将交替显示行背景。
            singleSelect:true,//为true时只能选择单行
            //fitColumns:true,//允许表格自动缩放，以适应父容器
            sortName : 'submitTime',//当数据表格初始化时以哪一列来排序
            sortOrder : 'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。
            remoteSort : false,
            pagination : true,//分页

            onLoadSuccess:function(data){
        	$('.editcls2').linkbutton({text:'审核',plain:true,iconCls:'icon-edit'}); 
            $('.editcls').linkbutton({text:'删除',plain:true,iconCls:'icon-cancel'});
        }  
        });
	</script>
</body>
</html>
