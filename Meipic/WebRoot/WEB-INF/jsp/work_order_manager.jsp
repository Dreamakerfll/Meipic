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
	
	<!--导出Excel表格提示dialog-->
	<div id="exporting_excel_dialog" class="easyui-dialog" title="系统消息" data-options="closed:true,modal:true" style="width:300px;height:auto;padding:10px">
		<div id="exporting">请勿关闭程序，正在导出，请稍后...</div>
	</div>
	<!--导出Excel表格提示dialog-->
	<div id="export_excel_dialog" class="easyui-dialog" title="工单信息详情" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						exportWorkOrder();
					}
				},{
					text:'取消',
					handler:function(){
						$('#export_excel_dialog').dialog('close');
					}
				}],closed:true,modal:true" style="width:350px;height:auto;padding:10px">
		<div id="warn_message">
		温馨提示：将会根据搜索条件导出符合条件的数据，大量数据导出会花费较多时间，经测试万级数据耗时在3分钟左右，请耐心等待！</div>

		<table style="width: 100%" >
		<tr>
		<td width="60px">起始页：</td>
		<td ><input id="begin_page" style="width: 30px" class="easyui-validatebox" data-options="required:true,validType:['number']"></input></td>
		<td width="60px">结束页：</td>
		<td><input id="end_page" style="width: 30px" class="easyui-validatebox" data-options="required:true,validType:['number']"></input></td>
		</tr>
		<tr>
		<td>每页条数：</td>
		<td><input id="data_row" style="width: 30px" class="easyui-validatebox" data-options="required:true,validType:['number']"></input></td>
		<td></td>
		<td></td>
		</tr>
		</table>
	</div>
	<%-- 新建工单的弹出window --%>
	<div id="add_work_order_window" class="easyui-window" title="新建工单" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:1000px;height:700px;padding:10px;">
	</div>
		<%-- 关联录音的弹出层 --%>
	<div id="link_record_dialog" class="easyui-dialog" title="关联录音" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						saveWorkOrderRecord();
					}
				},{
					text:'取消',
					handler:function(){
						$('#link_record_dialog').dialog('close');
					}
				}],closed:true,modal:true" style="width:280px;height:auto;padding:10px">
	<table id="record_link_table" width="100%">
	<tr>
	<td>录音ID：</td>
	<td><input id="work_order_assembly_line_link" style="display:none"/>
	<input id="record_id" class="easyui-validatebox" style="width:150px"/>
	</td></tr>
	</table>
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
		<div style="margin: 10px"></div>
		<table id="follow_history_table" title="处理历史">
			<thead>
				<tr>
					<th data-options="field:'id',width:100,align:'left'">id</th>
		            <th data-options="field:'workOrderId',align:'left',width:200">工单ID</th>
					<th data-options="field:'account',align:'center',width:100">处理客服</th>
					<th data-options="field:'treatmentScheme',width:500,align:'left'">处理过程</th>
					<th data-options="field:'time',align:'center',width:130">处理时间</th>
				</tr>
			</thead>
		</table>
		<table style="width: 100%" >
			<tr>
				<td>跟进过程：</td>
				<td><input id="work_order_id_detail" style="display:none"/>
				<textarea id="work_order_treatment_scheme_detail" style="overflow:auto;border:1px solid #95b8e7;width:100%;height:100%;"></textarea></td>
			</tr>
			<tr>
				<td>结案判定：</td>
				<td><input id="work_order_final_decision_detail" style="width:150px"/></td>
			</tr>
		</table>
	</div>
	
	<script type="text/javascript">
		$('.loading').animate({'width':'55%'},50);  //第二个节点
	</script>
	
	<%-- 分配座席的弹出层 --%>
	<div id="allocation_dialog" class="easyui-dialog" title="分配座席" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						setNewAccount();
					}
				},{
					text:'取消',
					handler:function(){
						$('#allocation_dialog').dialog('close');
					}
				}],closed:true,modal:true" style="width:300px;height:auto;padding:10px">
		<table style="width: 100%" >
			<tr>
				<td>分配座席：</td>
				<td><input id="account_seat" style="width:146px"/><input id="misscall_id" class="easyui-textbox" style="display:none"/></td>
			</tr>
		</table>
	</div>
    <div class="easyui-tabs">
	
		<div title="工单维护" style="padding: 10px">
    	<table style="width: 100%">
			<tr>
				<td width="9%">工单流水号：</td>
				<td style="width: 18%"><input id="work_order_assembly_line_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="9%">来电号码：</td>
				<td style="width: 18%"><input id="work_order_tel_wom" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="9%">来电时间：</td>
				<td style="width: 18%"><input id="work_order_tel_time_begin_wom" editable="false" class="easyui-datetimebox" value="" style="width: 150px"></input></td>
				<td width="9%">至</td>
				<td width="9%"><input id="work_order_tel_time_end_wom" editable="false" class="easyui-datetimebox" value="" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">标题：</td>
				<td style="width: 150px"><input id="work_order_title_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="60px">优先级：</td>
				<td style="width: 150px"><input id="work_order_level_wom" value="" style="width: 150px"></input></td>
				<td width="60px">反馈类型：</td>
				<td style="width: 150px"><input id="work_order_feedback_type_wom" editable="false" style="width:150px"></input></td>
				<td width="60px">反馈渠道：</td>
				<td style="width: 150px"><input id="work_order_feedback_channel_wom" editable="false" style="width:150px"></input></td>
			</tr>
			<tr>
				<td width="60px">处理过程：</td>
				<td style="width: 150px"><input id="work_order_treatment_scheme_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="60px">结案判定：</td>
				<td style="width: 150px"><input id="work_order_final_decision_wom" editable="false" value="" style="width:150px"></input></td>
				<td width="60px">处理客服：</td>
				<td style="width: 150px"><input id="work_order_user_account_wom" value="" style="width: 150px"></input></td>
				<td width="60px">技能组：</td>
				<td style="width: 150px"><input id="work_order_skill_group_wom" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">客户姓名：</td>
				<td style="width: 150px"><input id="customer_name_wom" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">归属地/国籍：</td>
				<td style="width: 150px"><input id="customer_area_wom" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">客户类别：</td>
				<td style="width: 150px"><input id="customer_type_wom" style="width: 150px"></input></td>
				<td width="60px">性别：</td>
				<td style="width: 150px"><input id="customer_sex_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">QQ号码：</td>
				<td style="width: 150px"><input id="customer_qq_wom" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">联系号码：</td>
				<td style="width: 150px"><input id="customer_tel_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="60px">京东账号：</td>
				<td style="width: 150px"><input id="customer_jd_wom" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">淘宝账号：</td>
				<td style="width: 150px"><input id="customer_taobao_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">微博地址：</td>
				<td style="width: 150px"><input id="customer_weibo_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="60px">微信号：</td>
				<td style="width: 150px"><input id="customer_wechat_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="60px">邮箱：</td>
				<td style="width: 150px"><input id="customer_email_wom" class="easyui-textbox" value="" style="width: 150px"></input></td>
				<td width="60px">提问大类：</td>
				<td style="width: 150px"><input id="question_mold_wom" value="" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">问题类别：</td>
				<td style="width: 150px"><input id="question_type_wom" value="" style="width: 150px"></input></td>
				<td width="60px">问题描述：</td>
				<td style="width: 150px"><input id="question_describe_wom" class="easyui-textbox" style="width:150px"></td>
				<td width="60px">IMEI号：</td>
				<td style="width: 150px"><input id="question_phone_imei_wom" class="easyui-textbox" style="width: 150px"></input></td>
				<td width="60px">手机型号：</td>
				<td style="width: 150px"><input id="question_phone_model_wom" value="" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">手机版本 ：</td>
				<td style="width: 150px"><input id="question_phone_version_wom" style="width: 150px"></input></td>
				<td width="60px">处理状态：</td>
				<td style="width: 150px"><input id="question_status_wom" style="width:150px"></td>
				<td width="60px"></td>
				<td style="width: 150px" align="left" colspan="3"><a id="work_order_manager_search_btn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a>
				<a id="export_btn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-export'">导出EXCEL</a>
				<a id="newWorkOrderBtn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新建工单</a>
				</td>
			</tr>
		</table>
		<table id="work_order_table" title="工单列表">
			<thead>
				<tr>
					<th field="workOrderId" hidden="true">工单ID</th>  
					<th field="customerId" hidden="true">客户ID</th>  
					<th field="assemblyLine" width="120" align="right">工单流水号</th>    
		            <th data-options="field:'workOrderTel',align:'right',width:100">来电号码</th>
		            <th field="telTime" width="120" align="center">来电时间</th>
					<th data-options="field:'title',width:100,align:'center'">标题</th>
					<th data-options="field:'level',width:100,align:'center'">优先级</th>
					<th data-options="field:'skillGroup',width:100,align:'center'">技能组</th>
					<th data-options="field:'feedbackType',width:100,align:'center'">反馈类型</th>
					<th data-options="field:'feedbackChannel',width:100,align:'center'">反馈渠道</th>
					<th data-options="field:'treatmentScheme',width:100,align:'center'">处理过程</th>
					<th data-options="field:'userAccount',width:100,align:'center'">处理客服</th>
					<th data-options="field:'finalDecision',width:100,align:'center'">结案判定</th>
					<th data-options="field:'name',align:'left',width:80">客户姓名</th>
					<th data-options="field:'sex',align:'center',width:80">性别</th>
					<th data-options="field:'tel',width:100,align:'left',formatter:function(value,row)
									{
									if(value == null||value =='')
									{
										return '';
									}
									if(value.length>0){
									  return value.substr(0,value.length-1);
									}else{
									 return value;
									}}">联系电话</th>
					<th data-options="field:'area',width:90,align:'left'">归属地/国籍</th>
					<th data-options="field:'qq',width:100,align:'right'">QQ号</th>
					<th data-options="field:'email',width:120,align:'left'">邮箱</th>
					<th data-options="field:'taobao',width:120,align:'left'">淘宝账号</th>
					<th data-options="field:'jd',width:120,align:'left'">京东账号</th>
					<th data-options="field:'wechat',width:100,align:'left'">微信号</th>
					<th data-options="field:'weibo',width:100,align:'left'">微博地址</th>
					<th data-options="field:'type',width:60,align:'center'">客户类别</th>
					<th data-options="field:'mold',width:100,align:'center'">提问大类</th>
					<th data-options="field:'questionType',width:100,align:'center'">问题类别</th>
					<th data-options="field:'describe',width:100,align:'left'">问题描述</th>
					<th data-options="field:'phoneImei',width:100,align:'center'">IMEI号</th>
					<th data-options="field:'phoneModel',width:100,align:'center'">手机型号</th>
					<th data-options="field:'phoneVersion',width:100,align:'center'">手机版本</th>
					<th data-options="field:'status',width:100,align:'center'">处理状态</th>
				</tr>
			</thead>
		</table>
    </div>
    <div title="未接电话" style="padding:20px">
			<table style="width: 100%" >
			<tr>
				<td width="60px">开始时间：</td>
				<td style="width: 20%"><input id="beginTimeForSearch" editable="false" class="easyui-datetimebox" style="width:150px"></input></td>
				<td width="60px">结束时间：</td>
				<td style="width: 20%"><input id="endTimeForSearch" editable="false" class="easyui-datetimebox" style="width:150px"></input></td>
				<td width="60px">主叫号码：</td>
				<td style="width: 20%"><input id="phoneNumeberForSearch" class="easyui-numberbox" style="width:150px"></input></td>
				
				<td width="60px" style="display:none">被叫号码：</td>
				<td style="width: 15%,display:none"><input id="phoneNumeberForSearch_called" class="easyui-numberbox" style="display:none"></input></td>
				
				<td><a id="search_misscall_btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a></td>
			</tr>
		</table>
			<table id="misscall_table" title="未接电话列表">
				<thead>
				<tr>
					<th data-options="field:'id',width:80,hidden:true">ID</th>
					<th data-options="field:'personToDisplay',width:80">经办人</th>
					<th data-options="field:'answerTime',width:125,align:'center'">来电时间</th>
					<th data-options="field:'caller',width:100,align:'right'">主叫号码</th>
					<th data-options="field:'answer',width:100,align:'right',hidden:true">被叫号码</th>
					<th data-options="field:'ringSec',width:100,align:'right'">来电时长（秒）</th>
					<th data-options="field:'control',width:60,align:'center',formatter:rowformater">操作</th>
				</tr>
				</thead>
			</table>
		</div>
    </div>
    
    <script type="text/javascript">
    	$('.loading').animate({'width':'78%'},50);  //第三个节点
	</script>

    <script type="text/javascript" src="js/pages/work_order_manager.js"></script>
    
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
