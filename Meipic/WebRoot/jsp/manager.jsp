<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String selectArea = (String)request.getAttribute("selectArea");
String currentSelectArea = (String)request.getAttribute("currentSelectArea");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>管理员</title>
    
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
  
	<%-- 添加用户的弹出层 --%>
	<div id="add_user_dialog" class="easyui-dialog" title="用户信息" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						saveAddUser();
					}
				},{
					text:'取消',
					handler:function(){
						$('#add_user_dialog').dialog('close');
					}
				}]" style="width:400px;height:auto;padding:10px">
			<table style="width: 100%" >
				<tr>
					<td width="80px">用户账号：</td>
					<td><input id="user_account_add" class="easyui-validatebox" data-options="required:true,validType:['account[4,20]','remote[\'user_info.do?method=checkAccount\']']" style="width:150px"></input></td>
				</tr>
				<tr>
					<td width="80px">用户姓名：</td>
					<td><input id="user_name_add" class="easyui-validatebox" data-options="required:true" style="width:150px"></input></td>
				</tr>
				<tr>
					<td width="80px">联系电话：</td>
					<td><input id="user_tel_add" class="easyui-validatebox" style="width:150px" data-options="required:true,validType:['number']"></input></td>
				</tr>
				<tr>
					<td width="80px">座席分机号：</td>
					<td><input id="user_agent_add" style="width:150px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td width="80px">用户角色：</td>
					<td><input id="user_role_add" style="width:150px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td width="80px">所属部门：</td>
					<td><input id="user_department_add" style="width:150px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td width="80px">用户状态：</td>
					<td><input id="user_status_add" style="width:150px" data-options="required:true"></input></td>
				</tr>
			</table>

			<table>
			<tr>
				<td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="$('#select_area_dialog').dialog('open')">选择区域</a></td>
			</tr>
			<tr>
				<td>所属区域：</td>
			</tr>
			</table>
			<div id="makeSureItem"></div>
	</div>
	<%-- 编辑用户的弹出层 --%>
	<div id="edit_user_dialog" class="easyui-dialog" title="用户信息" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						saveEditUser();
					}
				},{
					text:'取消',
					handler:function(){
						$('#edit_user_dialog').dialog('close');
					}
				}]" style="width:400px;height:auto;padding:10px">
			<table style="width: 100%" >
				<tr>
					<td width="80px">用户姓名：</td>
					<td><input id="user_name_edit" class="easyui-validatebox" data-options="required:true" style="width:150px"></input>
					<input id="user_id_edit" class="easyui-textbox" style="display:none"></input></td>
				</tr>
				<tr>
					<td width="80px">联系电话：</td>
					<td><input id="user_tel_edit" class="easyui-validatebox" style="width:150px" data-options="required:true,validType:['number']"></input></td>
				</tr>
				<tr>
					<td width="80px">座席分机号：</td>
					<td><input id="user_agent_edit" style="width:150px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td width="80px">用户角色：</td>
					<td><input id="user_role_edit" style="width:150px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td width="80px">所属部门：</td>
					<td><input id="user_department_edit" style="width:150px" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td width="80px">用户状态：</td>
					<td><input id="user_status_edit" style="width:150px" data-options="required:true"></input></td>
				</tr>
			</table>

			<table>
			<tr>
				<td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" 
				onclick="openCurrentSelectArea()">选择区域</a></td>
			</tr>
			<tr>
				<td>所属区域：</td>
			</tr>
			</table>
			<div id="currentMakeSureItem"></div>
	</div>
	<%-- 选择区域的弹出层 --%>
	<div id="select_area_dialog" class="easyui-dialog" title="选择区域" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						copyItem('#previewItem','#makeSureItem');
						$('#select_area_dialog').dialog('close');
					}
				},{
					text:'取消',
					handler:function(){
						$('#select_area_dialog').dialog('close');
					}
				}]" style="width:400px;height:auto;padding:10px">
			
		<%=selectArea %>
	</div>
	<%-- 当前用户选择区域的弹出层 --%>
	<div id="current_select_area_dialog" class="easyui-dialog" title="选择区域" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						currentCopyItem('#currentPreviewItem','#currentMakeSureItem');
						$('#current_select_area_dialog').dialog('close');
					}
				},{
					text:'取消',
					handler:function(){
						$('#current_select_area_dialog').dialog('close');
					}
				}]" style="width:400px;height:auto;padding:10px">
			
		<%=currentSelectArea %>
	</div>
	<%-- 新建角色的弹出层 --%>
	<div id="add_role_dialog" class="easyui-dialog" title="新建角色" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						saveAddRole();
					}
				},{
					text:'取消',
					handler:function(){
						$('#add_role_dialog').dialog('close');
					}
				}]" style="width:400px;height:auto;padding:10px">
		<table style="width: 100%" >
			<tr>
				<td width="60px">角色名称：</td>
				<td><input id="role_name_add" class="easyui-validatebox" data-options="required:true" style="width:150px"></input></td>
			</tr>
		</table>
		<table>
			<tr>
			<td>
				权限内容：
			</td>
			</tr>
		</table>
		<div id="rightCheckbox" style="width: 100%;padding: 5px">
		<table>
			<tr>
			<td>
				<input type="checkbox" value="1" name="工作任务单"/>工作任务单
			</td>
			<td>
				<input type="checkbox" value="2" name="工单维护"/>工单维护
			</td>
			<td>
				<input type="checkbox" value="3" name="话务报表"/>话务报表
			</td>
			<td>
				<input type="checkbox" value="4" name="用户管理"/>用户管理
			</td>
			</tr>
			<tr>
			<td>
				<input type="checkbox" value="5" name="座席班长"/>座席班长
			</td>
			<td>
				<input type="checkbox" value="6" name="队列状态"/>队列状态
			</td>
			<td>
				<input type="checkbox" value="7" name="黑名单"/>黑名单
			</td>
			</tr>
			<tr>
			<td>
				<input type="checkbox" value="8" name="假日转接"/>假日转接
			</td>
			<td>
				<input type="checkbox" value="9" name="CRM系统"/>CRM系统
			</td>
			<td>
				<input type="checkbox" value="10" name="座席外呼"/>座席外呼
			</td>
			</tr>
		</table>
		</div>
	</div>
	<%-- 编辑角色的弹出层 --%>
	<div id="edit_role_dialog" class="easyui-dialog" title="编辑角色" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						saveEditRole();
					}
				},{
					text:'取消',
					handler:function(){
						$('#edit_role_dialog').dialog('close');
					}
				}]" style="width:400px;height:auto;padding:10px">
		<table style="width: 100%" >
			<tr>
				<td width="60px">角色名称：</td>
				<td><input id="role_name_edit" class="easyui-validatebox" data-options="required:true" style="width:150px"></input>
				<input id="role_id_edit" class="easyui-textbox" style="display:none"></input></td>
			</tr>
		</table>
		<table>
			<tr>
			<td>
				权限内容：
			</td>
			</tr>
		</table>
		<div id="editRightCheckbox" style="width: 100%;padding: 5px">
		<table>
			<tr>
			<td>
				<input type="checkbox" value="1" name="工作任务单"/>工作任务单
			</td>
			<td>
				<input type="checkbox" value="2" name="工单维护"/>工单维护
			</td>
			<td>
				<input type="checkbox" value="3" name="话务报表"/>话务报表
			</td>
			<td>
				<input type="checkbox" value="4" name="用户管理"/>用户管理
			</td>
			</tr>
			<tr>
			<td>
				<input type="checkbox" value="5" name="座席班长"/>座席班长
			</td>
			<td>
				<input type="checkbox" value="6" name="队列状态"/>队列状态
			</td>
			<td>
				<input type="checkbox" value="7" name="黑名单"/>黑名单
			</td>
			</tr>
			<tr>
			<td>
				<input type="checkbox" value="8" name="假日转接"/>假日转接
			</td>
			<td>
				<input type="checkbox" value="9" name="CRM系统"/>CRM系统
			</td>
			<td>
				<input type="checkbox" value="10" name="座席外呼"/>座席外呼
			</td>
			</tr>
		</table>
		</div>
	</div>
	<%-- 新建部门的弹出层 --%>
	<div id="add_department_dialog" class="easyui-dialog" title="新建部门" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						saveAddDepartment();
					}
				},{
					text:'取消',
					handler:function(){
						$('#add_department_dialog').dialog('close');
					}
				}]" style="width:400px;height:auto;padding:10px">
		<table style="width: 100%" >
			<tr>
				<td width="80px">部门名称：</td>
				<td><input id="department_name_add" class="easyui-validatebox" data-options="required:true" style="width:150px"></input></td>
			</tr>
			<tr>
				<td width="80px">部门负责人：</td>
				<td><input id="department_manager_add" class="easyui-validatebox" data-options="required:true" style="width:150px"></input></td>
			</tr>
		</table>
	</div>
	<%-- 编辑部门的弹出层 --%>
	<div id="edit_department_dialog" class="easyui-dialog" title="编辑部门" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						saveEditDepartment();
					}
				},{
					text:'取消',
					handler:function(){
						$('#edit_department_dialog').dialog('close');
					}
				}]" style="width:400px;height:auto;padding:10px">
		<table style="width: 100%" >
			<tr>
				<td width="80px">部门名称：</td>
				<td><input id="department_name_edit" class="easyui-validatebox" data-options="required:true" style="width:150px"></input>
				<input id="department_id_edit" class="easyui-textbox" style="display:none"></input></td>
			</tr>
			<tr>
				<td width="80px">部门负责人：</td>
				<td><input id="department_manager_edit" class="easyui-validatebox" data-options="required:true" style="width:150px"></input></td>
			</tr>
		</table>
	</div>
    <div class="easyui-tabs">
    	<div title="用户管理" style="padding: 10px">
    		<table style="width: 100%" >
				<tr>
					<td width="60px">用户账号：</td>
					<td style="width: 180"><input id="user_account_search" class="easyui-textbox" style="width: 150px"></input></td>
					<td width="60px">用户姓名：</td>
					<td style="width: 180"><input id="user_name_search" class="easyui-textbox" style="width: 150px"></input></td>
					<td width="60px">座席号：</td>
					<td style="width: 180"><input id="user_agent_search" class="easyui-textbox" style="width: 150px"></input></td>
				</tr>
				<tr>
					<td width="60px">用户角色：</td>
					<td style="width: 180"><input id="user_role_search" style="width: 150px"></input></td>
					<td width="60px">所属部门：</td>
					<td style="width: 180"><input id="user_department_search" style="width: 150px"></input></td>
					<td width="60px">负责区域：</td>
					<td style="width: 180"><input id="user_area_search" class="easyui-textbox" style="width: 150px"></input></td>
				</tr>
				<tr>
					<td width="60px">联系电话：</td>
					<td style="width: 180"><input id="user_tel_search" class="easyui-textbox" style="width: 150px"></input></td>
					<td width="60px">状态：</td>
					<td style="width: 180"><input id="user_status_search" style="width: 150px"></input></td>
					<td width="60px"><a id="search_user_btn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a></td>
				</tr>
			</table>
    		<table id="user_table" title="用户列表">
				<thead>
					<tr>
						<th field="id" width="180">ID</th>    
						<th field="account" width="180">用户账号</th>    
						<th field="name" width="180">用户姓名</th>    
			            <th field="agent" width="180">座席号</th>
						<th field="roleId" width="180">用户角色</th>    
						<th field="roleDisplay" width="180">用户角色</th>    
			            <th data-options="field:'departmentId',align:'left',width:80">所属部门</th>
			            <th data-options="field:'departmentDisplay',align:'left',width:80">所属部门</th>
						<th data-options="field:'queue',width:100,align:'center'">队列</th>
						<th data-options="field:'area',width:100,align:'center',formatter:function(value,row)
									{if(value.length>0){
									  return value.substr(0,value.length-1);
									}else{
									 return value;
									}},">负责区域</th>
						<th data-options="field:'tel',width:100,align:'center'">联系电话</th>
						<th data-options="field:'status',width:100,align:'center'">状态</th>
						<th data-options="field:'xxx',width:100,align:'center',formatter:userOperate">操作</th>
					</tr>
				</thead>
			</table>
    	</div>
    	<div title="权限管理" style="padding: 10px">
    		<table id="role_table" title="角色列表">
				<thead>
					<tr>
						<th field="id" width="100">角色名称</th>    
						<th field="name" width="80">角色名称</th>    
						<th field="rightId" width="100">角色权限</th>    
						<th data-options="field:'rightContent',align:'left',width:500,formatter:function(value,row)
									{if(value.length>0){
									  return value.substr(0,value.length-1);
									}else{
									 return value;
									}},">权限内容</th>
			            <th data-options="field:'xxx',width:100,align:'center',formatter:roleOperate">操作</th>
					</tr>
				</thead>
			</table>
    	</div>
    	<div title="部门管理" style="padding: 10px">
    		<table id="department_table" title="部门列表">
				<thead>
					<tr>
						<th field="id" width="180">部门ID</th>    
						<th field="name" width="180">部门名称</th>    
						<th data-options="field:'headcount',width:100,align:'center'">人数</th>
						<th data-options="field:'manager',width:100,align:'center'">部门负责人</th>
						<th data-options="field:'xxx',width:100,align:'center',formatter:departmentOperate">操作</th>
					</tr>
				</thead>
			</table>
    	</div>
    </div>
    <script type="text/javascript" src="js/pages/manager.js"></script>
    
  </body>
</html>
