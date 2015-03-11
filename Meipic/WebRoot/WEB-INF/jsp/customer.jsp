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
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery.form.js"></script>
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
	<div id="export_excel_dialog" class="easyui-dialog" title="客户信息详情" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						exportCustomer();
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
	<!--导入Excel表格-->
	<div id="import_excel_dialog" class="easyui-dialog" title="导入客户信息详情" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						submitFile();
					}
				},{
					text:'取消',
					handler:function(){
						$('#import_excel_dialog').dialog('close');
					}
				}],closed:true,modal:true" style="width:300px;height:auto;padding:10px">
		<form id="customer_form" method="post" enctype="multipart/form-data">
		      <input id="customer_info_file" name="customer_info_file" type="file" style="width: 270px;height: 26px;">
		</form>
	</div>
	<%-- 客户详情的弹出层 --%>
	<div id="show_customer_detail_dialog" class="easyui-dialog" title="客户详情" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						saveEditCustomer();
					}
				},{
					text:'取消',
					handler:function(){
						cancelSaveEditCustomer();
					}
				}],closed:true,modal:true" style="width:900px;height:auto;padding:10px">
		
		<table style="width: 100%" >
			<tr>
				<td width="60px">客户姓名：</td>
				<td style="width: 180"><input id="customer_name_edit" class="easyui-textbox" style="width: 150px" onchange="customerInfoChange()"></input><input id="customer_id_edit" class="easyui-textbox" style="width: 150px;display:none"></input></td>
				<td width="60px">性别：</td>
				<td style="width: 180"><input id="customer_sex_edit" style="width: 150px"></input></td>
				<td width="60px">年龄：</td>
				<td style="width: 180"><input id="customer_age_edit" class="easyui-validatebox" data-options="validType:['number']" style="width: 150px" onchange="customerInfoChange()"></input></td>
			</tr>
			<tr>
				<td width="60px">地区/国籍：</td>
				<td style="width: 180"><input id="customer_area_edit" class="easyui-textbox" style="width:150px" onchange="customerInfoChange()"></input></td>
				<td width="60px">联系电话1：</td>
				<td style="width: 180"><input id="customer_tel1_edit" class="easyui-validatebox" data-options="validType:['number']" style="width:150px" onchange="customerInfoChange()"></td>
				<td width="60px">联系电话2：</td>
				<td style="width: 180"><input id="customer_tel2_edit" style="width: 150px"></input></td>
			</tr>
			<tr>
				<td width="60px">QQ号码：</td>
				<td style="width: 180"><input id="customer_qq_edit" class="easyui-validatebox" data-options="validType:['number']" style="width:150px" onchange="customerInfoChange()"></input></td>
				<td width="60px">客户地址：</td>
				<td style="width: 180"><input id="customer_address_edit" class="easyui-textbox" style="width: 150px" onchange="customerInfoChange()"></input></td>
				<td width="60px">邮政编码：</td>
				<td style="width: 180"><input id="customer_postal_code_edit" class="easyui-validatebox" data-options="validType:['zipcode']" style="width: 150px" onchange="customerInfoChange()"></input></td>
			</tr>
			<tr>
				<td width="60px">微信号：</td>
				<td style="width: 180"><input id="customer_wechat_edit" class="easyui-textbox" style="width: 150px" onchange="customerInfoChange()"></input></td>
				<td width="60px">微博地址：</td>
				<td style="width: 180"><input id="customer_weibo_edit" class="easyui-textbox" style="width: 150px" onchange="customerInfoChange()"></input></td>
				<td width="60px">客户昵称：</td>
				<td style="width: 180"><input id="customer_nickname_edit" class="easyui-textbox" style="width: 150px" onchange="customerInfoChange()"></input></td>
			</tr>
			<tr>
				<td width="60px">淘宝账号：</td>
				<td style="width: 180"><input id="customer_taobao_edit" class="easyui-textbox" style="width: 150px" onchange="customerInfoChange()"></input></td>
				<td width="60px">电子邮件：</td>
				<td style="width: 180"><input id="customer_email_edit" class="easyui-validatebox" data-options="validType:['email']" style="width: 150px" onchange="customerInfoChange()"></input></td>
				<td width="60px">京东账号：</td>
				<td style="width: 180"><input id="customer_jd_edit" class="easyui-textbox" style="width: 150px" onchange="customerInfoChange()"></input></td>
			</tr>
			<tr>
				<td width="60px">客户类别：</td>
				<td style="width: 180"><input id="customer_type_edit" style="width: 150px"></input></td>
				<td width="60px">专属客服：</td>
				<td style="width: 180"><input id="customer_agent_edit" style="width: 150px"></input>
				<input id="customer_agent_tem_edit" class="easyui-textbox" style="width: 150px;display:none"></input></td>
				<td width="60px">用户情况：</td>
				<td style="width: 180"><input id="customer_detail_edit" class="easyui-textbox" style="width: 150px" onchange="customerInfoChange()"></input></td>
			</tr>
		</table>
		<div style="margin: 10px"></div>
		<table id="phone_info_edit_table" title="手机信息" data-options="url:'phone_info.do?method=findPhoneInfoByCustomerId&customerId='">
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
				        		var row = $('#phone_info_edit_table').datagrid('getSelections');
                                var rowIndex = $('#phone_info_edit_table').datagrid('getRowIndex', row[0]);
                                var target1 = $('#phone_info_edit_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'model' }).target;
                                var target2 = $('#phone_info_edit_table').datagrid('getEditor', { 'index': rowIndex, 'field': 'version' }).target;
                                target1.combobox('clear');
                                target2.combobox('clear');
			        		}
							}}">IMEI</th>
					<th data-options="field:'model',align:'left',width:100,
							editor:{type:'combobox',options:{
							url:'phone_model.do?method=findAll',
							valueField:'phoneModel',
							textField:'phoneModel',
							required:true,
							editable:false,
							missingMessage:'不能为空'
							}}">手机型号</th>
					<th data-options="field:'version',width:100,align:'left',
							editor:{type:'combobox',options:{
							url:'phone_version.do?method=findAll',
							valueField:'phoneVersion',
							textField:'phoneVersion',
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
	</div>
	
	<script type="text/javascript">
		$('.loading').animate({'width':'55%'},50);  //第二个节点
	</script>
	
	<div class="easyui-panel" title="客户资料" style="padding: 10px">
	    <table style="width: 100%" >
			<tr>
				<td width="60px">客户姓名：</td>
				<td style="width: 150"><input id="customer_name_search" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">性别：</td>
				<td style="width: 150"><input id="customer_sex_search" style="width: 150px"></input></td>
				<td width="60px">年龄：</td>
				<td style="width: 150"><input id="customer_age_search" class="easyui-numberbox" style="width: 150px" value=""></input></td>
			</tr>
			<tr>
				<td width="60px">地区/国籍：</td>
				<td style="width: 180"><input id="customer_area_search" class="easyui-textbox" style="width:150px" value=""></input></td>
				<td width="60px">联系电话：</td>
				<td style="width: 180"><input id="customer_tel_search" class="easyui-numberbox" style="width:150px" value=""></td>
				<td width="60px">购买时间：</td>
				<td style="width: 180"><input id="phone_buy_time_search" editable="false" class="easyui-datebox" style="width: 150px"  value=""></input></td>
			</tr>
			<tr>
				<td width="60px">QQ号码：</td>
				<td style="width: 180"><input id="customer_qq_search" class="easyui-numberbox" style="width:150px" value=""></input></td>
				<td width="60px">客户地址：</td>
				<td style="width: 180"><input id="customer_address_search" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">邮政编码：</td>
				<td style="width: 180"><input id="customer_postal_code_search" class="easyui-numberbox" style="width: 150px" value=""></input></td>
			</tr>
			<tr>
				<td width="60px">微信号：</td>
				<td style="width: 180"><input id="customer_wechat_search" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">微博地址：</td>
				<td style="width: 180"><input id="customer_weibo_search" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">客户昵称：</td>
				<td style="width: 180"><input id="customer_nickname_search" class="easyui-textbox" style="width: 150px" value=""></input></td>
			</tr>
			<tr>
				<td width="60px">淘宝账号：</td>
				<td style="width: 180"><input id="customer_taobao_search" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">电子邮件：</td>
				<td style="width: 180"><input id="customer_email_search" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">京东账号：</td>
				<td style="width: 180"><input id="customer_jd_search" class="easyui-textbox" style="width: 150px" value=""></input></td>
			</tr>
			<tr>
				<td width="60px">客户类别：</td>
				<td style="width: 180"><input id="customer_type_search" style="width: 150px"></input></td>
				<td width="60px">专属客服：</td>
				<td style="width: 180"><input id="customer_agent_search" style="width: 150px" value=""></input></td>
				<td width="60px">用户情况：</td>
				<td style="width: 180"><input id="customer_detail_search" class="easyui-textbox" style="width: 150px" value=""></input></td>
			</tr>
			<tr>
				<td width="60px">IMEI：</td>
				<td style="width: 180"><input id="phone_imei_search" class="easyui-textbox" style="width: 150px" value=""></input></td>
				<td width="60px">手机型号：</td>
				<td style="width: 180"><input id="phone_model_search" style="width: 150px" value=""></input></td>
				<td width="60px">手机版本：</td>
				<td style="width: 180"><input id="phone_version_search" style="width: 150px" value=""></input></td>
			</tr>
			<tr>
			<td colspan="5" align="right"><a id="search_customer_phone_info_search_btn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查找</a></td>
			</tr>
		</table>
		<table id="customer_table" title="客户列表">
			<thead>
				<tr>
				<th data-options="field:'customerId',width:80,align:'left'">客户ID</th>
				<th data-options="field:'name',width:80,align:'left'">客户姓名</th>
				<th data-options="field:'sex',width:80,align:'center'">性别</th>
				<th data-options="field:'age',width:80,align:'right'">年龄</th>
				<th data-options="field:'area',width:80,align:'left'">地区/国籍</th>
				<th data-options="field:'tel',width:80,align:'left',formatter:function(value,row)
									{if(value.length>0){
									  return value.substr(0,value.length-1);
									}else{
									 return value;
									}}">联系电话</th>
				<th data-options="field:'qq',width:80,align:'right'">QQ号码</th>
				<th data-options="field:'address',width:80,align:'left'">客户地址</th>
				<th data-options="field:'postalCode',width:80,align:'right'">邮政编码</th>
				<th data-options="field:'wechat',width:80,align:'left'">微信号</th>
				<th data-options="field:'weibo',width:80,align:'left'">微博地址</th>
				<th data-options="field:'nickname',width:80,align:'left'">客户昵称</th>
				<th data-options="field:'taobao',width:80,align:'left'">淘宝账号</th>
				<th data-options="field:'email',width:80,align:'left'">电子邮件</th>
				<th data-options="field:'jd',width:80,align:'left'">京东账号</th>
				<th data-options="field:'type',width:80,align:'center'">客户类别</th>
				<th data-options="field:'agent',width:80,align:'left'">专属客服</th>
				<th data-options="field:'detail',width:80,align:'left'">用户情况</th>
				<th data-options="field:'imei',width:80,align:'left'">IMEI</th>
				<th data-options="field:'model',width:80,align:'center'">手机型号</th>
				<th data-options="field:'version',width:80,align:'center'">手机版本</th>
				<th data-options="field:'buyTime',width:80,align:'center'">购买时间</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<script type="text/javascript">
    	$('.loading').animate({'width':'78%'},50);  //第三个节点
	</script>
	
	<script type="text/javascript" src="js/pages/customer.js"></script>
	
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
