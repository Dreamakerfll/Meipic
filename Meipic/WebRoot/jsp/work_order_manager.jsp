<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>工单维护</title>
    
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
  
  
	<%-- 新建工单的弹出window --%>
	<div id="add_work_order_window" class="easyui-window" title="新建工单" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:1000px;height:700px;padding:10px;">
	</div>

	<%-- 工单详情的弹出层 --%>
	<div id="work_order_detail_dialog" class="easyui-dialog" title="工单详情" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						saveDetailWorkOrder();
					}
				},{
					text:'取消',
					handler:function(){
						$('#work_order_detail_dialog').dialog('close');
					}
				}],closed:true,modal:true" style="width:800px;height:auto;padding:10px">
		<table id="detail_question_table" title="问题详情">
			<thead>
				<tr>
					<th data-options="field:'id',width:100,align:'left'">id</th>
		            <th data-options="field:'mold',align:'left',width:200,
							editor:{type:'combobox',options:{
							url:'question_type.do?method=findAllQuestionMold',
							panelHeight:'auto',
							valueField:'mold',
							textField:'moldDisplay',
							required:true,
							editable:true,
							missingMessage:'不能为空',
							onSelect: function (rec) {
				        		var row = $('#detail_question_table').datagrid('getSelections');
                                var rowIndex = $('#detail_question_table').datagrid('getRowIndex', row[0]);
                                var target1 = $('#detail_question_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'type' }).target;
                                target1.combobox('clear');
                                target1.combobox('reload','question_type.do?method=findQuestionTypeByQuestionMold&questionMold='+encodeURIComponent(rec.mold));
			        		}
							}}">提问大类</th>
					<th data-options="field:'type',align:'left',width:200,
							editor:{type:'combobox',options:{
							valueField:'type',
							textField:'typeDisplay',
							required:true,
							editable:false,
							missingMessage:'不能为空'
							}}" style="width: 200px">问题类别</th>
					<th data-options="field:'describe',width:100,align:'left',
							editor:{type:'validatebox',options:{
							required:true,
							missingMessage:'不能为空'
							}}">问题描述</th>
					<th data-options="field:'phoneImei',align:'left',width:200,
							editor:{type:'combobox',options:{
							url:'phone_info.do?method=findPhoneInfoByCustomerIdNoPage&customerId='+$('#customer_id').val(),
							panelHeight:'auto',
							valueField:'imei',
							textField:'imei',
							required:true,
							editable:true,
							missingMessage:'不能为空',
							onSelect: function (rec) {
				        		var row = $('#detail_question_table').datagrid('getSelections');
                                var rowIndex = $('#detail_question_table').datagrid('getRowIndex', row[0]);
                                var target1 = $('#detail_question_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'phoneModel' }).target;
                                var target2 = $('#detail_question_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'phoneVersion' }).target;
                                target1.combobox('setValue',rec.model);
                                target2.combobox('setValue',rec.version);
			        		}
							}}">IMEI</th>
					<th data-options="field:'phoneModel',align:'left',width:100,
							editor:{type:'combobox',options:{
							url:'phone_model.do?method=findAll',
							valueField:'phoneModel',
							textField:'phoneModel',
							required:true,
							editable:false,
							missingMessage:'不能为空'
							}}">手机型号</th>
					<th data-options="field:'phoneVersion',width:100,align:'left',
							editor:{type:'combobox',options:{
							url:'phone_version.do?method=findAll',
							valueField:'phoneVersion',
							textField:'phoneVersion',
							required:true,
							editable:false,
							missingMessage:'不能为空'
							}}">手机版本</th>
					<th data-options="field:'status',width:100,align:'left',
							editor:{type:'combobox',options:{
							url:'json/question_status.json',
							valueField:'status',
							textField:'statusDisplay',
							required:true,
							editable:false,
							missingMessage:'不能为空'
							}}">处理状态</th>
				</tr>
			</thead>
		</table>
		<table style="width: 100%" >
			<tr>
				<td>处理过程：</td>
				<td><input id="work_order_id_detail" style="display:none"/>
				<textarea id="work_order_treatment_scheme_detail" style="overflow:auto;border:1px solid #95b8e7;width:100%;height:100%;"></textarea></td>
			</tr>
			<tr>
				<td>结案判定：</td>
				<td><input id="work_order_final_decision_detail" style="width:150px"/></td>
			</tr>
		</table>
	</div>
    <div class="easyui-tabs">
	
		<div title="工单维护" style="padding: 10px">
    	<table style="width: 100%" >
			<tr>
				<td width="60px">工单流水号：</td>
				<td style="width: 180"><input id="work_order_assembly_line_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="60px">来电号码：</td>
				<td style="width: 180"><input id="work_order_tel_wom" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">来电时间：</td>
				<td style="width: 180"><input id="work_order_tel_time_begin_wom" class="easyui-datebox" value="" style="width: 150px"></input></td>
				<td width="60px">至：</td>
				<td style="width: 180"><input id="work_order_tel_time_end_wom" class="easyui-datebox" value="" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">标题：</td>
				<td style="width: 180"><input id="work_order_title_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="60px">优先级：</td>
				<td style="width: 180"><input id="work_order_level_wom" value="" style="width: 150px"></input></td>
				<td width="60px">反馈类型：</td>
				<td style="width: 180"><input id="work_order_feedback_type_wom" editable="false" style="width:150px"></input></td>
				<td width="60px">反馈渠道：</td>
				<td style="width: 180"><input id="work_order_feedback_channel_wom" editable="false" style="width:150px"></input></td>
			</tr>
			<tr>
				<td width="60px">处理过程：</td>
				<td style="width: 180"><input id="work_order_treatment_scheme_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="60px">结案判定：</td>
				<td style="width: 180"><input id="work_order_final_decision_wom" editable="false" value="" style="width:150px"></input></td>
				<td width="60px">处理客服：</td>
				<td style="width: 180"><input id="work_order_user_account_wom" value="" style="width: 150px"></input></td>
				<td width="60px">技能组：</td>
				<td style="width: 180"><input id="work_order_skill_group_wom" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">客户姓名：</td>
				<td style="width: 180"><input id="customer_name_wom" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">客户归属地：</td>
				<td style="width: 180"><input id="customer_area_wom" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">客户类别：</td>
				<td style="width: 180"><input id="customer_type_wom" style="width: 150px"></input></td>
				<td width="60px">性别：</td>
				<td style="width: 180"><input id="customer_sex_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">QQ号码：</td>
				<td style="width: 180"><input id="customer_qq_wom" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">联系号码：</td>
				<td style="width: 180"><input id="customer_tel_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="60px">京东账号：</td>
				<td style="width: 180"><input id="customer_jd_wom" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">淘宝账号：</td>
				<td style="width: 180"><input id="customer_taobao_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">微博地址：</td>
				<td style="width: 180"><input id="customer_weibo_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="60px">微信号：</td>
				<td style="width: 180"><input id="customer_wechat_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="60px">邮箱：</td>
				<td style="width: 180"><input id="customer_email_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="60px">提问大类：</td>
				<td style="width: 180"><input id="question_mold_wom" value="" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">问题类别：</td>
				<td style="width: 180"><input id="question_type_wom" value="" style="width: 150px"></input></td>
				<td width="60px">问题描述：</td>
				<td style="width: 180"><input id="question_describe_wom" class="easyui-textbox" style="width:150px"></td>
				<td width="60px">IMEI号：</td>
				<td style="width: 180"><input id="question_phone_imei_wom" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">手机型号：</td>
				<td style="width: 180"><input id="question_phone_model_wom" value="" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">手机版本 ：</td>
				<td style="width: 180"><input id="question_phone_version_wom" style="width: 150px"></input></td>
				<td width="60px">处理状态：</td>
				<td style="width: 180"><input id="question_status_wom" style="width:150px"></td>
				<td width="60px"></td>
				<td style="width: 180" align="left"><a id="work_order_manager_search_btn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查找</a>
				<a id="searchAccountByAccountAndAgent" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-export'">导出EXCEL</a>
				</td>
			</tr>
		</table>
		<table id="work_order_table" title="工单列表">
			<thead>
				<tr>
					<th field="workOrderId" width="180" hidden="true">工单ID</th>  
					<th field="customerId" width="180" hidden="true">客户ID</th>  
					<th field="assemblyLine" width="180">工单流水</th>    
		            <th data-options="field:'workOrderTel',align:'left',width:80">来电号码</th>
		            <th field="telTime" width="180">来电时间</th>
					<th data-options="field:'title',width:100,align:'center'">标题</th>
					<th data-options="field:'level',width:100,align:'center'">优先级</th>
					<th data-options="field:'feedbackType',width:100,align:'center'">反馈类型</th>
					<th data-options="field:'feedbackChannel',width:100,align:'center'">反馈渠道</th>
					<th data-options="field:'treatmentScheme',width:100,align:'center'">处理过程</th>
					<th data-options="field:'userAccount',width:100,align:'center'">处理客服</th>
					<th data-options="field:'finalDecision',width:100,align:'center'">结案判定</th>
					<th data-options="field:'name',align:'left',width:80">客户姓名</th>
					<th data-options="field:'sex',align:'left',width:80">性别</th>
					<th data-options="field:'tel',width:100,align:'center'">联系电话</th>
					<th data-options="field:'area',width:100,align:'center'">归属地</th>
					<th data-options="field:'qq',width:100,align:'center'">QQ号</th>
					<th data-options="field:'taobao',width:100,align:'center'">淘宝账号</th>
					<th data-options="field:'jd',width:100,align:'center'">京东账号</th>
					<th data-options="field:'wechat',width:100,align:'center'">微信号</th>
					<th data-options="field:'weibo',width:100,align:'center'">微博地址</th>
					<th data-options="field:'mold',width:100,align:'center'">提问大类</th>
					<th data-options="field:'questionType',width:100,align:'center'">问题类别</th>
					<th data-options="field:'describe',width:100,align:'center'">问题描述</th>
					<th data-options="field:'phoneImei',width:100,align:'center'">IMEI号</th>
					<th data-options="field:'phoneModel',width:100,align:'center'">手机型号</th>
					<th data-options="field:'phoneVersion',width:100,align:'center'">手机版本</th>
					<th data-options="field:'status',width:100,align:'center'">处理状态</th>
				</tr>
			</thead>
		</table>
    </div>
    </div>

    <script type="text/javascript" src="js/pages/work_order_manager.js"></script>
  </body>
</html>
