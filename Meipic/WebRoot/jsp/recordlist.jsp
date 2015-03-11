<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String seatNumber = request.getParameter("agentNumber");
String role  = request.getParameter("role")==null?"":request.getParameter("role").toString();
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '录音调听' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/demo.css">
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/easyloader.js"></script>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<link rel="stylesheet" type="text/css" href="css/panel_datagrid_layout.css">
  </head>
<body>
<div class="easyui-panel" title="录音调听">
	<table cellspacing="5px" cellpadding="5px">
		<tr>
			<td>录音开始时间：</td>
			<td><input class="easyui-datetimebox" editable="false" id="select_first_time" name="select_first_time"/></td>
			
			<td>录音结束时间：</td>
			<td><input class="easyui-datetimebox" editable="false" id="select_end_time" name="select_end_time" /></td>
		
			<td>电话号码：</td>
			<td><input class="easyui-textbox" id="select_inPhone" name="select_inPhone"/></td>
			
			<td rowspan="2"> <input type="button" id="select_report_btn" value="查询" style="width: 60px;height: 40px;"/></td>
		</tr>
		<tr>
			<td colspan="2">请选择并点击批量下载到指定路径：</td>
			<td colspan="2">
				<input type="button" value="导出选中数据" id="export_sel_report_btn" style="width: 100px; height: 25px;"/>
				<input type="button" value="导出全部数据" id="export_all_report_btn" style="width: 100px;height: 25px;"/>
			</td>
			<td>满意度评价：</td>
			<td>
				<input class="easyui-combobox" id="select_score" name="select_score"/>
			</td>
		</tr>
	</table>	
	
	<table id="record_table" title="录音调听列表">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',align:'center',width:90">录音id</th>
				<th data-options="field:'assemblyLine',align:'center',width:90">工单流水号</th>
				<th data-options="field:'recordDay',align:'center',width:90">录音日期</th>
				<th data-options="field:'startTime',align:'center',width:90">开始时间</th>
				<th data-options="field:'recordTime',align:'center',width:60">录音时长</th>
				<th data-options="field:'callType',align:'center',width:60">通话类型</th>
				<th data-options="field:'callers',align:'center',width:70">from</th>
				<th data-options="field:'answers',align:'center',width:70">to</th>
				<th data-options="field:'score',align:'center',width:80">坐席满意度评价</th>
				<th data-options="field:'playRecord',formatter:recordformater,align:'center',width:40">播放</th>
				<th data-options="field:'loadRecord',formatter:loadformater,align:'center',width:50">下载</th>
			</tr>
		</thead>
	</table>
</div>

<script type="text/javascript">
function recordformater(value,row,index){
	if (typeof(row.recordPath) == "undefined"){
		return '';
	}
	if(row.recordPath == ''){
		return '';
	}
	return "<a class='playRecord' onclick=openRecord('"+row.recordPath+"',0)></a>";
}

function openRecord(path,type){
	exportApp.openNewWindow(path,type);
}


function loadformater(value,row,index){
	if (typeof(row.recordPath) == "undefined"){
		return '';
	}
	if(row.recordPath == ''){
		return '';
	}
	return "<a class='loadRecord' onclick=loadRecord('"+row.recordPath+"')></a>";
};

function loadRecord(path){
	exportApp.downloadRecord(path);
}

$('#record_table').datagrid({
    pageSize:10,//默认选择的分页是每页5行数据
    pageList:[5,10,15,20,25,30],//可以选择的分页集合
    singleSelect:false,//为true时只能选择单行
    remoteSort:false,
    pagination:true,//分页
    fitColumns:true,//允许表格自动缩放，以适应父容器
    onLoadSuccess:function(data){
		$('.playRecord').linkbutton({plain:true,iconCls:'icon-record'}); 
    	$('.loadRecord').linkbutton({plain:true,iconCls:'icon-download'});
	}  
});

//查询报表
jQuery("#select_report_btn").click(function(){
	var select_first_time = $('#select_first_time').datetimebox('getValue');
	var select_end_time = $('#select_end_time').datetimebox('getValue');
	var select_inPhone = $('#select_inPhone').val();
	var select_score = $('#select_score').combobox('getValue');
	var urlPath = "record.do?method=selectAllRecord&seatNumber=<%=seatNumber%>&role=<%=role%>";
	urlPath = urlPath + "&select_first_time="+select_first_time;
	urlPath = urlPath + "&select_end_time="+select_end_time;
	urlPath = urlPath + "&select_inPhone="+select_inPhone;
	urlPath = urlPath + "&select_score="+select_score;
	$('#record_table').datagrid({ url: urlPath});
});

//导出选中数据
jQuery("#export_sel_report_btn").click(function(){
	var selRows = $('#record_table').datagrid('getChecked');
	if(selRows.length == 0 ){
		$.messager.alert('', '未选择导出数据!', 'error');
		return;
	}
	
	var ids = '';
	for(var i = 0 ; i<selRows.length ; i++){
		ids += selRows[i].id + '_';
	}
	var select_first_time = $('#select_first_time').datetimebox('getValue');
	var select_end_time = $('#select_end_time').datetimebox('getValue');
	var select_inPhone = $('#select_inPhone').val();
	var select_score = $('#select_score').combobox('getValue');
	
	exportApp.exportExcel('<%=seatNumber%>','<%=role%>',select_first_time,select_end_time,select_inPhone,select_score,ids,1);
});

//导出全部数据
jQuery("#export_all_report_btn").click(function(){
	var rows = $('#record_table').datagrid('getRows');
	if(rows.length == 0 ){
		$.messager.alert('', '导出数据不能为空!', 'error');
		return;
	}
	var select_first_time = $('#select_first_time').datetimebox('getValue');
	var select_end_time = $('#select_end_time').datetimebox('getValue');
	var select_inPhone = $('#select_inPhone').val();
	var select_score = $('#select_score').combobox('getValue');

	exportApp.exportExcel('<%=seatNumber%>','<%=role%>',select_first_time,select_end_time,select_inPhone,select_score,'',2);
});

$('#select_score').combobox({
    url: 'record.do?method=loadScoreType',
    valueField: 'id',
    textField: 'score'
});
</script>
  </body>
</html>
