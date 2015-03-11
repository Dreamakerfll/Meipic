<%@ page language="java" import="java.util.*,com.dianfeng.entity.CustomerInfo,com.dianfeng.utils.IsNull,java.text.SimpleDateFormat" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//生成工单流水
String work_order_assembly_line = System.currentTimeMillis()+"";

String type1 = request.getParameter("type").toString();

//来电号码
String default_tel = request.getParameter("tel").toString().equals("notExist")?"":request.getAttribute("default_tel").toString();

//生成来电时间
Date now = new Date();
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String work_order_tel_time = dateFormat.format(now).toString();

String defaultArea = request.getAttribute("area").toString();
String defaultPostCode = request.getAttribute("postCode").toString();

//获取客户信息
CustomerInfo customerInfo = (CustomerInfo)request.getAttribute("customerInfo");
String isCustomerExist = customerInfo==null?"客户不存在":"客户已存在";
String customer_id = customerInfo==null?"":customerInfo.getId();
String customer_name = customerInfo==null?"":customerInfo.getName();
String customer_sex = customerInfo==null?"女":customerInfo.getSex();
String customer_age = customerInfo==null?"":customerInfo.getAge();
String customer_area = customerInfo==null?defaultArea:customerInfo.getArea();
String customer_qq =  customerInfo==null?"":customerInfo.getQq();

String customer_tel1 = request.getParameter("tel").toString().equals("notExist")?"":customerInfo==null?default_tel:"";


String customer_tel2 =  customerInfo==null?"":default_tel;
String customer_telJson = customerInfo==null?"[]":request.getAttribute("telNumberJson").toString();
String customer_address =  customerInfo==null?"":customerInfo.getAddress();
String customer_postal_code =  customerInfo==null?defaultPostCode:customerInfo.getPostalCode();
String customer_wechat =  customerInfo==null?"":customerInfo.getWechat();
String customer_weibo =  customerInfo==null?"":customerInfo.getWeibo();
String customer_nickname =  customerInfo==null?"":customerInfo.getNickname();
String customer_taobao =  customerInfo==null?"":customerInfo.getTaobao();
String customer_email =  customerInfo==null?"":customerInfo.getEmail();
String customer_jd =  customerInfo==null?"":customerInfo.getJd();
String customer_type =  customerInfo==null?"1类":customerInfo.getType();
String customer_agent =  customerInfo==null?request.getParameter("agent").toString():customerInfo.getAgent();
String customer_detail =  customerInfo==null?"":customerInfo.getDetail();

String department = request.getAttribute("department") == null?"":request.getAttribute("department").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>工单</title>
    
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
 
  <body onload="hideOverlay();">
  
	<div class="loading"></div>
	<%-- 遮罩层 --%>
	<div class="cover"></div>
    <script type="text/javascript">
    $('.loading').animate({'width':'33%'},50);  //第一个进度节点
    // fadeTo第一个参数为速度，第二个为透明度
    // 多重方式控制透明度，保证兼容性，但也带来修改麻烦的问题
    $(".cover").fadeTo(0, 1);
	</script>
	
  	<%-- 绑定客户的弹出层 --%>
	<div id="select_customer_dialog" class="easyui-dialog" title="绑定客户" style="width:850px;height:auto;padding:10px">
		<table style="width: 100%" >
			<tr>
				<td width="60px">客户姓名：</td>
				<td style="width: 180"><input id="customer_name_old" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">性别：</td>
				<td style="width: 180"><input id="customer_sex_old" style="width: 150px"></input></td>
				<td width="60px">年龄：</td>
				<td style="width: 180"><input id="customer_age_old" class="easyui-validatebox" style="width: 150px" value=""></input></td>
			</tr>
			<tr>
				<td width="60px">地区/国籍：</td>
				<td style="width: 180"><input id="customer_area_old" class="easyui-textbox" style="width:150px" value=""></input></td>
				<td width="60px">联系电话：</td>
				<td style="width: 180"><input id="customer_tel_old" class="easyui-validatebox" style="width:150px" value=""></td>
				<td width="60px">购买时间：</td>
				<td style="width: 180"><input id="phone_buy_time_old" class="easyui-datebox" style="width: 150px"  value=""></input></td>
			</tr>
			<tr>
				<td width="60px">QQ号码：</td>
				<td style="width: 180"><input id="customer_qq_old" class="easyui-validatebox" style="width:150px" value=""></input></td>
				<td width="60px">客户地址：</td>
				<td style="width: 180"><input id="customer_address_old" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">邮政编码：</td>
				<td style="width: 180"><input id="customer_postal_code_old" class="easyui-validatebox" style="width: 150px" value=""></input></td>
			</tr>
			<tr>
				<td width="60px">微信号：</td>
				<td style="width: 180"><input id="customer_wechat_old" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">微博地址：</td>
				<td style="width: 180"><input id="customer_weibo_old" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">客户昵称：</td>
				<td style="width: 180"><input id="customer_nickname_old" class="easyui-textbox" style="width: 150px" value=""></input></td>
			</tr>
			<tr>
				<td width="60px">淘宝账号：</td>
				<td style="width: 180"><input id="customer_taobao_old" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">电子邮件：</td>
				<td style="width: 180"><input id="customer_email_old" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">京东账号：</td>
				<td style="width: 180"><input id="customer_jd_old" class="easyui-textbox" style="width: 150px" value=""></input></td>
			</tr>
			<tr>
				<td width="60px">客户类别：</td>
				<td style="width: 180"><input id="customer_type_old" style="width: 150px"></input></td>
				<td width="60px">专属客服：</td>
				<td style="width: 180"><input id="customer_agent_old" style="width: 150px" value=""></input></td>
				<td width="60px">客户情况：</td>
				<td style="width: 180"><input id="customer_detail_old" class="easyui-textbox" style="width: 150px" value=""></input></td>
			</tr>
			<tr>
				<td width="60px">IMEI：</td>
				<td style="width: 180"><input id="phone_imei_old" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">手机型号：</td>
				<td style="width: 180"><input id="phone_model_old" style="width: 150px" value=""></input></td>
				<td width="60px">手机版本：</td>
				<td style="width: 180"><input id="phone_version_old" style="width: 150px" value=""></input></td>
			</tr>
		</table>
		<table width = "100%">
			<tr>
			<td><a id="search_customer_phone_info_old_btn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查找</a></td>
			</tr>
		</table>
		<table id="customer_phone_info_old_table" title="客户详细信息">
			<thead>
			<tr>
				<th data-options="field:'customerId',width:80,align:'left'">客户ID</th>
				<th data-options="field:'name',width:80,align:'left'">客户姓名</th>
				<th data-options="field:'sex',width:80,align:'left'">性别</th>
				<th data-options="field:'age',width:80,align:'left'">年龄</th>
				<th data-options="field:'area',width:80,align:'left'">地区/国籍</th>
				<th data-options="field:'tel',width:80,align:'left',formatter:function(value,row)
									{if(value.length>0){
									  return value.substr(0,value.length-1);
									}else{
									 return value;
									}}">联系电话</th>
				<th data-options="field:'qq',width:80,align:'left'">QQ号码</th>
				<th data-options="field:'address',width:80,align:'left'">客户地址</th>
				<th data-options="field:'postalCode',width:80,align:'left'">邮政编码</th>
				<th data-options="field:'wechat',width:80,align:'left'">微信号</th>
				<th data-options="field:'weibo',width:80,align:'left'">微博地址</th>
				<th data-options="field:'nickname',width:80,align:'left'">客户昵称</th>
				<th data-options="field:'taobao',width:80,align:'left'">淘宝账号</th>
				<th data-options="field:'email',width:80,align:'left'">电子邮件</th>
				<th data-options="field:'jd',width:80,align:'left'">京东账号</th>
				<th data-options="field:'type',width:80,align:'left'">客户类别</th>
				<th data-options="field:'agent',width:80,align:'left'">专属客服</th>
				<th data-options="field:'detail',width:80,align:'left'">客户情况</th>
				<th data-options="field:'imei',width:80,align:'left'">IMEI</th>
				<th data-options="field:'model',width:80,align:'left'">手机型号</th>
				<th data-options="field:'version',width:80,align:'left'">手机版本</th>
				<th data-options="field:'buyTime',width:80,align:'left'">购买时间</th>
			</tr>
			</thead>
		</table>
	</div>
	<%-- 工单跟进的弹出层 --%>
	<div id="follow_work_order_dialog" class="easyui-dialog" title="跟进工单" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						saveFollowWorkOrder();
					}
				},{
					text:'取消',
					handler:function(){
						$('#follow_work_order_dialog').dialog('close');
					}
				}]" style="width:800px;height:auto;padding:10px">
		<table id="follow_question_table" title="问题详情">
			<thead>
				<tr>
					<th data-options="field:'id',width:100,align:'left'">id</th>
		            <th data-options="field:'mold',align:'left',width:200,
							editor:{type:'combobox',options:{
							url:'question_type.do?method=findAllQuestionMold',
							panelHeight:200,
							valueField:'mold',
							textField:'moldDisplay',
							required:true,
							editable:true,
							missingMessage:'不能为空',
							onSelect: function (rec) {
				        		var row = $('#follow_question_table').datagrid('getSelections');
                                var rowIndex = $('#follow_question_table').datagrid('getRowIndex', row[0]);
                                var target1 = $('#follow_question_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'type' }).target;
                                target1.combobox('clear');
                                target1.combobox('reload','question_type.do?method=findQuestionTypeByQuestionMold&questionMold='+encodeURIComponent(rec.mold));
			        		}
							}}">提问大类</th>
					<th data-options="field:'type',align:'left',width:200,
							editor:{type:'combobox',options:{
							valueField:'type',
							textField:'typeDisplay',
							panelHeight:200,
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
				        		var row = $('#follow_question_table').datagrid('getSelections');
                                var rowIndex = $('#follow_question_table').datagrid('getRowIndex', row[0]);
                                var target1 = $('#follow_question_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'phoneModel' }).target;
                                var target2 = $('#follow_question_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'phoneVersion' }).target;
                                target1.combobox('setValue',rec.model);
                                target2.combobox('setValue',rec.version);
			        		}
							}}">IMEI</th>
					<th data-options="field:'phoneModel',align:'left',width:100,
							editor:{type:'combobox',options:{
							url:'phone_model.do?method=findAll',
							valueField:'phoneModel',
							textField:'phoneModel',
							panelHeight:200,
							required:true,
							editable:false,
							missingMessage:'不能为空'
							}}">手机型号</th>
					<th data-options="field:'phoneVersion',width:100,align:'left',
							editor:{type:'combobox',options:{
							url:'phone_version.do?method=findAll',
							valueField:'phoneVersion',
							textField:'phoneVersion',
							panelHeight:200,
							required:true,
							editable:false,
							missingMessage:'不能为空'
							}}">手机版本</th>
					<th data-options="field:'status',width:100,align:'left',
							editor:{type:'combobox',options:{
							url:'json/question_status.json',
							valueField:'status',
							textField:'statusDisplay',
							panelHeight:'auto',
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
				<td><input id="work_order_id_follow" style="display:none"/>
				<input id="work_order_assembly_line_follow" style="display:none"/>
				<textarea id="work_order_treatment_scheme_follow" style="overflow:auto;border:1px solid #95b8e7;width:100%;height:100%;"></textarea></td>
			</tr>
			<tr>
				<td>结案判定：</td>
				<td><input id="work_order_final_decision_follow" style="width:150px"/></td>
			</tr>
		</table>
	</div>
	
	<script type="text/javascript">
		$('.loading').animate({'width':'55%'},50);  //第二个节点
	</script>
	
    <div  class="easyui-panel" title="工单" style="padding: 10px">
	    <div  class="easyui-panel" title="工单信息" style="padding: 10px" data-options="collapsible:true,">
			<table id = "work_order_title_info" style="width: 100%" >
				<tr>
					<td width="60px">工单流水：</td>
					<td style="width: 180"><input id=work_order_assembly_line readonly="readonly" class="easyui-validatebox" value="<%=work_order_assembly_line %>" style="width: 150px"></input></td>
					<td width="60px">来电号码：</td>
					<td style="width: 180"><input id="work_order_tel" readonly="readonly" class="easyui-validatebox" value="<%=default_tel %>" style="width: 150px"></input></td>
					<td width="60px">来电时间：</td>
					<td style="width: 180"><input id="work_order_tel_time" disable="true" editable="false" class="easyui-datetimebox" value="<%=work_order_tel_time %>" style="width:150px"></input></td>
				</tr>
			</table>
		</div>
		<div style="margin: 10px"></div>
		 <div  class="easyui-panel" title="客户信息" style="padding: 10px" data-options="collapsible:true,">
			<table style="width: 100%" >
				<tr>
					<td width="60px">客户姓名：</td>
					<td style="width: 180"><input id="customer_name" class="easyui-textbox" style="width: 150px" value="<%=customer_name %>" onchange="customerInfoChange()"></input><input id="customer_id" class="easyui-textbox" style="width: 150px;display:none" value=<%=customer_id %>></input></td>
					<td width="60px">性别：</td>
					<td style="width: 180"><input id="customer_sex" style="width: 150px" value="<%=customer_sex%>"></input></td>
					<td width="60px">年龄：</td>
					<td style="width: 180"><input id="customer_age" class="easyui-validatebox" data-options="validType:['number']" style="width: 150px" value="<%=customer_age %>" onchange="customerInfoChange()"></input></td>
				</tr>
				<tr>
					<td width="60px">地区/国籍：</td>
					<td style="width: 180"><input id="customer_area" class="easyui-textbox" style="width:150px" value="<%=customer_area %>" onchange="customerInfoChange()"></input></td>
					<td width="60px">联系电话1：</td>
					<td style="width: 180"><input id="customer_tel1" class="easyui-validatebox" data-options="validType:['number']" style="width:150px" value="<%=customer_tel1 %>" onchange="customerInfoChange()"></td>
					<td width="60px">联系电话2：</td>
					<td style="width: 180"><input id="customer_tel2" style="width: 150px" value="<%=customer_tel2 %>"></input></td>
				</tr>
				<tr>
					<td width="60px">QQ号码：</td>
					<td style="width: 180"><input id="customer_qq" class="easyui-validatebox" data-options="validType:['number']" style="width:150px" value="<%=customer_qq %>" onchange="customerInfoChange()"></input></td>
					<td width="60px">客户地址：</td>
					<td style="width: 180"><input id="customer_address" class="easyui-textbox" style="width: 150px" value="<%=customer_address %>" onchange="customerInfoChange()"></input></td>
					<td width="60px">邮政编码：</td>
					<td style="width: 180"><input id="customer_postal_code" class="easyui-validatebox" data-options="validType:['zipcode']" style="width: 150px" value="<%=customer_postal_code %>" onchange="customerInfoChange()"></input></td>
				</tr>
				<tr>
					<td width="60px">微信号：</td>
					<td style="width: 180"><input id="customer_wechat" class="easyui-textbox" style="width: 150px" value="<%=customer_wechat %>" onchange="customerInfoChange()"></input></td>
					<td width="60px">微博地址：</td>
					<td style="width: 180"><input id="customer_weibo" class="easyui-textbox" style="width: 150px" value="<%=customer_weibo %>" onchange="customerInfoChange()"></input></td>
					<td width="60px">客户昵称：</td>
					<td style="width: 180"><input id="customer_nickname" class="easyui-textbox" style="width: 150px" value="<%=customer_nickname %>" onchange="customerInfoChange()"></input></td>
				</tr>
				<tr>
					<td width="60px">淘宝账号：</td>
					<td style="width: 180"><input id="customer_taobao" class="easyui-textbox" style="width: 150px" value="<%=customer_taobao %>" onchange="customerInfoChange()"></input></td>
					<td width="60px">电子邮件：</td>
					<td style="width: 180"><input id="customer_email" class="easyui-validatebox" data-options="validType:['email']" style="width: 150px" value="<%=customer_email %>" onchange="customerInfoChange()"></input></td>
					<td width="60px">京东账号：</td>
					<td style="width: 180"><input id="customer_jd" class="easyui-textbox" style="width: 150px" value="<%=customer_jd %>" onchange="customerInfoChange()"></input></td>
				</tr>
				<tr>
					<td width="60px">客户类别：</td>
					<td style="width: 180"><input id="customer_type" style="width: 150px" value="<%=customer_type%>"></input></td>
					<td width="60px">专属客服：</td>
					<td style="width: 180"><input id="customer_agent" style="width: 150px" value="<%=customer_agent %>"></input></td>
					<td width="60px">客户情况：</td>
					<td style="width: 180"><input id="customer_detail" class="easyui-textbox" style="width: 150px" value="<%=customer_detail %>" onchange="customerInfoChange()"></input></td>
				</tr>
			</table>
			<div style="margin: 10px"></div>
			<table id="phone_info_table" title="手机信息" data-options="url:'phone_info.do?method=findPhoneInfoByCustomerId&customerId=<%=customer_id %>'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100,align:'left'">id</th>
			            <th data-options="field:'imei',align:'left',width:200,
								editor:{type:'combobox',options:{
								url:'json/imei.json',
								panelHeight:'auto',
								valueField:'imei',
								textField:'imei',
								required:true,
								editable:true,
								missingMessage:'不能为空',
								onSelect: function (rec) {
					        		var row = $('#phone_info_table').datagrid('getSelections');
	                                var rowIndex = $('#phone_info_table').datagrid('getRowIndex', row[0]);
	                                var target1 = $('#phone_info_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'model' }).target;
	                                var target2 = $('#phone_info_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'version' }).target;
	                                target1.combobox('clear');
	                                target2.combobox('clear');
				        		}
								}}">IMEI</th>
						<th data-options="field:'model',align:'left',width:100,
								editor:{type:'combobox',options:{
								url:'phone_model.do?method=findAll',
								valueField:'phoneModel',
								textField:'phoneModel',
								panelHeight:200,
								required:true,
								editable:false,
								missingMessage:'不能为空'
								}}">手机型号</th>
						<th data-options="field:'version',width:100,align:'left',
								editor:{type:'combobox',options:{
								url:'phone_version.do?method=findAll',
								valueField:'phoneVersion',
								textField:'phoneVersion',
								panelHeight:200,
								required:true,
								editable:false,
								missingMessage:'不能为空'
								}}">手机版本</th>
						<th data-options="field:'buyTime',width:100,align:'left',
								editor:{type:'datebox',options:{
								required:false,
								editable:false,
								missingMessage:'不能为空'
								}}">购买时间</th>
					</tr>
				</thead>
			</table>
			<div style="margin: 10px"></div>
			<table width = "100%">
				<tr>
				<td align="left" style = "width:33%"><a id="bind_customer_btn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-bind'">绑定客户</a></td>
				<td align="center" style = "width:33%"><div id="isCustomerExist"><%=isCustomerExist %></div></td>
				<td align="right"><a id="clear_customer_info_btn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-dustbin'">清空客户信息</a>
				<a id="update_cusomer_btn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">更新客户信息</a></td>
				</tr>
			</table>
			</div>
			
			<div style="margin: 10px"></div>
			<table id="work_order_history_table" title="历史记录"
			data-options="url:'customer_work_order_link.do?method=findWorkOrderByCustomerId&customerId=<%=customer_id %>'">
				<thead>
					<tr>
						<th data-options="field:'id',width:100,align:'center',hidden:true">工单ID</th>
			            <th data-options="field:'assemblyLine',align:'center',width:120">工单流水</th>
			            <th data-options="field:'feedbackType',align:'center',width:120">反馈类型</th>
			            <th data-options="field:'title',align:'left',width:80">标题</th>
						<th data-options="field:'tel',align:'left',width:80">来电号码</th>
						<th data-options="field:'telTime',width:120,align:'center'">来电时间</th>
						<th data-options="field:'userAgent',width:100,align:'center'">座席号</th>
						<th data-options="field:'userAccount',width:100,align:'center'">用户账号</th>
						<th data-options="field:'treatmentScheme',width:100,align:'left'">处理过程</th>
						<th data-options="field:'finalDecision',width:100,align:'center'">结案判定</th>
						<th data-options="field:'xx',width:100,align:'center',formatter:rowformater">操作</th>
					</tr>
				</thead>
			</table>
			<div style="margin: 10px"></div>
			<div  class="easyui-panel" title="工单详细" style="padding: 10px" data-options="collapsible:true,">
			<table style="width: 100%" >
				<tr>
					<td width="60px">标题：</td>
					<td style="width: 180"><input id="work_order_title" class="easyui-textbox" style="width: 150px"></input></td>
					<td width="60px">优先级：</td>
					<td style="width: 180"><input id="work_order_level" data-options="required:true" value="" style="width: 150px"></input></td>
				</tr>
				<tr>
					<td width="60px">反馈时间：</td>
					<td style="width: 180"><input id="work_order_feedback_time" data-options="required:true" editable="false" class="easyui-datetimebox" value="" style="width:150px"></input></td>
					<td width="60px">技能组：</td>
					<td style="width: 180"><input id="work_order_skill_group" data-options="required:true" editable="false" style="width: 150px" value="<%=department %>"></input></td>
				</tr>
				<tr>
					<td width="60px">反馈类型：</td>
					<td style="width: 180"><input id="work_order_feedback_type" data-options="required:true" style="width: 150px"></input></td>
					<td width="60px">反馈渠道：</td>
					<td style="width: 180"><input id="work_order_feedback_channel" data-options="required:true" style="width: 150px"></input></td>
				</tr>
			</table>
			<div style="margin: 10px"></div>
			<table id="question_table" class="easyui-datagrid" title="问题详情">
				<thead>
					<tr>
						<th data-options="field:'id',width:100,align:'left'">id</th>
			            <th data-options="field:'mold',align:'left',width:200,
								editor:{type:'combobox',options:{
								url:'question_type.do?method=findAllQuestionMold',
								panelHeight:200,
								valueField:'mold',
								textField:'moldDisplay',
								required:true,
								editable:true,
								missingMessage:'不能为空',
								onSelect: function (rec) {
					        		var row = $('#question_table').datagrid('getSelections');
	                                var rowIndex = $('#question_table').datagrid('getRowIndex', row[0]);
	                                var target1 = $('#question_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'type' }).target;
	                                target1.combobox('clear');
	                                target1.combobox('reload','question_type.do?method=findQuestionTypeByQuestionMold&questionMold='+encodeURIComponent(rec.mold));
	                                target1.combobox('setValue',rec.type);
				        		},
				        		onLoadSuccess:function(){
				        			var datagridlength = $('#question_table').datagrid('getRows').length;
				        			var row = $('#question_table').datagrid('getSelections');
	                                var rowIndex = $('#question_table').datagrid('getRowIndex', row[0]);
				        			if(datagridlength-rowIndex == 1)
				        			{
					        			if($(this).combobox('getData').length > 0)
					        			{
		                                	if($(this).combobox('getValue')=='')
		                                	{
						        				var firstValue = $(this).combobox('getData')[0].mold;
						        				$(this).combobox('setValue',firstValue);
					        					var row = $('#question_table').datagrid('getSelections');
		                                		var rowIndex = $('#question_table').datagrid('getRowIndex', row[0]);
		                                		var target1 = $('#question_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'type' }).target;
			                                	target1.combobox('clear');
			                                	target1.combobox('reload','question_type.do?method=findQuestionTypeByQuestionMold&questionMold='+encodeURIComponent(firstValue));
					        				}
					        			}
				        			}
				        		}
								}}">提问大类</th>
						<th data-options="field:'type',align:'left',width:200,
								editor:{type:'combobox',options:{
								valueField:'type',
								textField:'typeDisplay',
								panelHeight:200,
								required:true,
								editable:false,
								missingMessage:'不能为空',
				        		onLoadSuccess:function(){
				        			var datagridlength = $('#question_table').datagrid('getRows').length;
				        			var row = $('#question_table').datagrid('getSelections');
	                                var rowIndex = $('#question_table').datagrid('getRowIndex', row[0]);
				        			if(datagridlength-rowIndex == 1)
				        			{
					        			if($(this).combobox('getData').length > 0)
					        			{
					        				if($(this).combobox('getValue')=='')
		                                	{
						        				var firstValue = $(this).combobox('getData')[0].type;
						        				$(this).combobox('setValue',firstValue);
					        				}
					        			}
				        			}
				        		}
								}}" style="width: 200px">问题类别</th>
						<th data-options="field:'describe',width:100,align:'left',
								editor:{type:'validatebox',options:{
								required:true,
								missingMessage:'不能为空'
								}}">问题描述</th>
						<th data-options="field:'phoneImei',align:'left',width:200,
								editor:{type:'combobox',options:{
								panelHeight:'auto',
								valueField:'imei',
								textField:'imei',
								required:true,
								editable:true,
								missingMessage:'不能为空',
								onSelect: function (rec) {
					        		var row = $('#question_table').datagrid('getSelections');
	                                var rowIndex = $('#question_table').datagrid('getRowIndex', row[0]);
	                                var target1 = $('#question_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'phoneModel' }).target;
	                                var target2 = $('#question_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'phoneVersion' }).target;
	                                target1.combobox('setValue',rec.model);
	                                target2.combobox('setValue',rec.version);
				        		},
				        		onLoadSuccess:function(){
				        			var datagridlength = $('#question_table').datagrid('getRows').length;
				        			var row = $('#question_table').datagrid('getSelections');
	                                var rowIndex = $('#question_table').datagrid('getRowIndex', row[0]);
				        			if(datagridlength-rowIndex == 1)
				        			{
					        			if($(this).combobox('getData').length > 0)
					        			{
					        				if($(this).combobox('getValue')=='')
					        				{
						        				var firstValue = $(this).combobox('getData')[0].imei;
						        				$(this).combobox('setValue',firstValue);
						        				var row = $('#question_table').datagrid('getSelections');
			                                	var rowIndex = $('#question_table').datagrid('getRowIndex', row[0]);
			                                	var target1 = $('#question_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'phoneModel' }).target;
			                                	var target2 = $('#question_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'phoneVersion' }).target;
			                                	target1.combobox('setValue',$(this).combobox('getData')[0].model);
			                                	target2.combobox('setValue',$(this).combobox('getData')[0].version);
					        				}
					        			}
				        			}
				        		}
								}}">IMEI</th>
						<th data-options="field:'phoneModel',align:'left',width:100,
								editor:{type:'combobox',options:{
								url:'phone_model.do?method=findAll',
								valueField:'phoneModel',
								textField:'phoneModel',
								panelHeight:200,
								required:true,
								editable:false,
								missingMessage:'不能为空'
								}}">手机型号</th>
						<th data-options="field:'phoneVersion',width:100,align:'left',
								editor:{type:'combobox',options:{
								url:'phone_version.do?method=findAll',
								valueField:'phoneVersion',
								textField:'phoneVersion',
								panelHeight:200,
								required:true,
								editable:false,
								missingMessage:'不能为空',
								tipPosition:'left'
								}}">手机版本</th>
						<th data-options="field:'status',width:100,align:'left',
								editor:{type:'combobox',options:{
								url:'json/question_status.json',
								valueField:'status',
								textField:'statusDisplay',
								panelHeight:'auto',
								required:true,
								editable:false,
								missingMessage:'不能为空',
								tipPosition:'left',
				        		onLoadSuccess:function(){
				        			var datagridlength = $('#question_table').datagrid('getRows').length;
				        			var row = $('#question_table').datagrid('getSelections');
	                                var rowIndex = $('#question_table').datagrid('getRowIndex', row[0]);
				        			if(datagridlength-rowIndex == 1)
				        			{
					        			if($(this).combobox('getData').length > 0)
					        			{
					        				if($(this).combobox('getValue')=='')
					        				{
						        				var firstValue = $(this).combobox('getData')[2].status;
						        				$(this).combobox('setValue',firstValue);
					        				}
					        			}
				        			}
				        		}
								}}">处理状态</th>
					</tr>
				</thead>
			</table>

			<table style="width: 100%" >
				<tr>
					<td width="60px">处理过程：</td>
					<td style="width: 180"><textarea id="work_order_treatment_scheme" style="overflow:auto;border:1px solid #95b8e7;width:100%;height:100%;"></textarea></td>
				</tr>
				<tr>
					<td width="60px">结案判定：</td>
					<td style="width: 180"><input id="work_order_final_decision" data-options="required:true" editable="false" value="" style="width:150px"></input></td>
				</tr>
			</table>
			<div style="margin: 10px"></div>
			<table width = "100%">
				<tr>
				<td align="right" style = "width:50%"><a id="work_order_submit_btn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'">提交</a></td>
				<td align="left" style = "width:50%"><a id="searchAccountByAccountAndAgent1" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">取消</a></td>
				</tr>
			</table>
			</div>
		</div>
		
	<script type="text/javascript">
	

	
	//页面预加载项目
	$(function(){

		//联系电话2加载
		var data = <%=customer_telJson%>;
		$('#customer_tel2').combobox({
			valueField: 'telNumber',
	        textField: 'telNumber',
	        data:data,
	        editable:false ,
			panelHeight: 'auto'
			});
		
		});
	</script>
	
	<script type="text/javascript">
    	$('.loading').animate({'width':'78%'},50);  //第三个节点
	</script>
	
	<script type="text/javascript" src="js/pages/work_order.js"></script>
	
	<script type="text/javascript">
    	$('.loading').animate({'width':'100%'},50);  //第四个节点
	</script>
	
	<script type="text/javascript">
    $(document).ready(function(){
    	
        $('.loading').fadeOut();
    });
    /* 隐藏覆盖层 */
    function hideOverlay() {
    	var type1 = <%=type1%>;
    	if(type1=='0'){
    		var incomingTime = $('#work_order_tel_time').val();
			$('#work_order_feedback_time').datetimebox('setValue',incomingTime);
    	}
    	$('.cover').fadeOut(200);
    };
	</script>
	
  </body>
</html>
