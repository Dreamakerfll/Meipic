//编辑标记重置
var isDetailWorkOrderEdited = false;
var isDetailQuestionEdited = false;
var detailQuestionEditIndex = undefined;

var count = 0;
var items;

//获取页面URL参数
function GetQueryString(name) {

   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");

   var r = window.location.search.substr(1).match(reg);

   if (r!=null) return decodeURI(r[2]); return null;

};

$(function(){
	
	//性别加载
	$('#customer_sex_wom').combobox({
		url:'json/sex_search.json',
		valueField: 'sex',
        textField: 'sexDisplay',
        editable:false ,
		panelHeight: 'auto'
		});
	
	//用户类别加载
	$('#customer_type_wom').combobox({
		url:'customer_type.do?method=findAll&type=0',
		valueField: 'customerType',
        textField: 'customerTypeDisplay',
        editable:false ,
		panelHeight: 200
		});
	
	//专属客服加载
	$('#work_order_user_account_wom').combobox({
		url:'user_info.do?method=findAll&type=0',
		valueField: 'agent',
		textField: 'agentDisplay',
		editable:false ,
		panelHeight: 200
	});
	
	//手机型号加载
	$('#question_phone_model_wom').combobox({
		url:'phone_model.do?method=findAll&type=0',
		valueField: 'phoneModel',
        textField: 'phoneModelDisplay',
        editable:false ,
		panelHeight: 200
		});
	
	//手机版本加载
	$('#question_phone_version_wom').combobox({
		url:'phone_version.do?method=findAll&type=0',
		valueField: 'phoneVersion',
		textField: 'phoneVersionDisplay',
		editable:false ,
		panelHeight: 200
	});
	
	//优先级加载
	$('#work_order_level_wom').combobox({
		url:'json/level_search.json',
		valueField: 'level',
        textField: 'levelDisplay',
        editable:false ,
		panelHeight: 'auto'
	});
	
	//反馈类型加载
	$('#work_order_feedback_type_wom').combobox({
		url:'json/feedback_type_search.json',
		valueField: 'feedbackType',
		textField: 'feedbackTypeDisplay',
		editable:false ,
		panelHeight: 'auto'
	});
	
	//反馈渠道加载
	$('#work_order_feedback_channel_wom').combobox({
		url:'json/feedback_channel_search.json',
		valueField: 'feedbackChannel',
		textField: 'feedbackChannelDisplay',
		editable:false ,
		panelHeight: 'auto'
	});
	
	//结案判定加载
	$('#work_order_final_decision_wom').combobox({
		url:'json/final_decision_search.json',
		valueField: 'decision',
        textField: 'decisionDisplay',
        editable:false ,
		panelHeight: 'auto'
	});
	
	//结案判定加载
	$('#work_order_final_decision_detail').combobox({
		url:'json/final_decision.json',
		valueField: 'decision',
		textField: 'decisionDisplay',
		editable:false ,
		panelHeight: 'auto'
	});
	
	//提问大类加载
	$('#question_mold_wom').combobox({
		url:'question_type.do?method=findAllQuestionMold&type=0',
		valueField: 'mold',
		textField: 'moldDisplay',
		editable:false ,
		panelHeight: 200
	});
	
	//问题类别加载
	$('#question_type_wom').combobox({
		url:'question_type.do?method=findQuestionTypeByQuestionMold&type=0&questionMold='+encodeURIComponent(''),
		valueField: 'type',
		textField: 'typeDisplay',
		editable:false ,
		panelHeight: 200
	});
	
	//处理状态加载
	$('#question_status_wom').combobox({
		url:'json/question_status_search.json',
		valueField: 'status',
		textField: 'statusDisplay',
		editable:false ,
		panelHeight: 'auto'
	});
	
	//技能组加载
	$('#work_order_skill_group_wom').combobox({
		url:'json/skill_group_search.json',
		valueField: 'skillGroup',
		textField: 'skillGroupDisplay',
		editable:false ,
		panelHeight: 'auto'
	});
	
	//分配坐席加载
	$('#account_seat').combobox({
		url: 'agent_info.do?method=findAllAgent',
		valueField: 'agent',
		textField: 'name',
		editable:false ,
		panelHeight: 'auto'
 	 	});

});

$('#newWorkOrderBtn').click(function(){
	var baseURL = 'http://192.168.0.133:8080/Meitu/work_order.do?method=pageToTra&tel='+'notExist'+'&agent='+GetQueryString("agent")+'&account='+GetQueryString("account")+'&type=1'+'&uniqueId=';
	javascript:app.openNewWindow(baseURL,'1');
});

var toolOfWorkOrder = [{
    text: '删除工单',
    iconCls: 'icon-remove',
    handler:function () {
        var rows = $("#work_order_table").datagrid('getSelections');
        if (rows.length > 0) {
        	var isMonitor = GetQueryString('isMonitor');
        	if(isMonitor != '1')
        	{

    			$.messager.alert('提示', '非坐席班长无法删除工单！', 'error');
    			return;
        	}
            $.messager.confirm('请确认', '删除工单会一并删除问题信息，您确定要删除当前所有选择的工单吗?', function(b) {
                if(b) {
                	deleteWorkOrderDetail(rows[0].workOrderId);
                }
            });
        } else {
            $.messager.alert('提示', '请选择要删除的记录', 'error');
        }
    }
},'-', {
    text: '工单详情',
    iconCls: 'icon-edit',
    handler:function () {
        var rows = $("#work_order_table").datagrid('getSelections');
        if (rows.length > 0) {
        	openWorkOrderDetail(rows[0].workOrderId,rows[0].treatmentScheme,rows[0].finalDecision);
        } else {
            $.messager.alert('提示', '请选择要查看详情的工单', 'info');
        }
    }
},'-', {
    text: '关联录音',
    iconCls: 'icon-record',
    handler:function () {
        var rows = $("#work_order_table").datagrid('getSelections');
        if (rows.length > 0) {
            openRecordLink(rows[0].assemblyLine);
        } else {
            $.messager.alert('提示', '请选择要关联录音的工单', 'info');
        }
    }
}];

function openRecordLink(assemblyLine)
{
	//给隐藏的工单流水赋值
	$('#work_order_assembly_line_link').val(assemblyLine);
	$('#link_record_dialog').dialog('open');
};

//工单信息datagrid加载
$('#work_order_table').datagrid({
	url:'work_order.do?method=findDetailWorkOrder',
	toolbar:toolOfWorkOrder,
    pageSize : 10,//默认选择的分页是每页5行数据
    pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
    remoteSort : false,
    pagination : true,//分页
    iconCls: 'icon-edit',
    collapsible:true,
    onClickRow:function(rowIndex, rowData){
    	var workOrderId = rowData.workOrderId;
    	
    	//取消所有行的选择
    	$('#work_order_table').datagrid('clearSelections');
    	
    	//重新选择
    	for ( var int = 0; int < $('#work_order_table').datagrid("getRows").length; int++) {
			if($('#work_order_table').datagrid('getData').rows[int]['workOrderId']==workOrderId)
				{
					$('#work_order_table').datagrid('selectRow',int);
				}
		}
    },
    onLoadSuccess: function(data){
    	var colList = "workOrderId,assemblyLine,telTime,workOrderTel,name,sex,tel,feedbackType,feedbackChannel,qq,email,area,title,level,skillGroup,treatmentScheme,finalDecision,userAccount,taobao,jd,wechat,weibo,type";
    	 var ColArray = colList.split(",");
    	    var tTable = $('#work_order_table');
    	    var TableRowCnts = tTable.datagrid("getRows").length;
    	    var tmpA;
    	    var tmpB;
    	    var PerTxt = "";
    	    var CurTxt = "";
    	    var alertStr = "";
    	    for (j = ColArray.length - 1; j >= 0; j--) {
    	        PerTxt = "";
    	        tmpA = 1;
    	        tmpB = 0;

    	        for (i = 0; i <= TableRowCnts; i++) {
    	            if (i == TableRowCnts) {
    	                CurTxt = "";
    	            }
    	            else {
    	                CurTxt = tTable.datagrid("getRows")[i][ColArray[0]];
    	            }
    	            if (PerTxt == CurTxt) {
    	                tmpA += 1;
    	            }
    	            else {
    	                tmpB += tmpA;
    	                
    	                tTable.datagrid("mergeCells", {
    	                    index: i - tmpA,
    	                    field: ColArray[j], //合并字段
    	                    rowspan: tmpA,
    	                    colspan: null
    	                });
    	               
    	                tmpA = 1;
    	            }
    	            PerTxt = CurTxt;
    	        }
    	    }
    }
});

$('#misscall_table').datagrid({
	collapsible:true,
	url:'misscall.do?method=findAll',
    pageSize : 10,//默认选择的分页是每页5行数据
    pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
    striped : true,//设置为true将交替显示行背景。
    singleSelect:true,//为true时只能选择单行
    fitColumns:true,//允许表格自动缩放，以适应父容器
    //sortName : 'xh',//当数据表格初始化时以哪一列来排序
    //sortOrder : 'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。
    remoteSort : false,
    pagination : true,//分页

    onLoadSuccess:function(data){

	var isMonitor = GetQueryString("isMonitor");
	if(isMonitor == '1')
	{
		$('.editcls1').linkbutton({text:'分配',plain:true,iconCls:'icon-edit'}); 
	}
	else
	{
		$('#misscall_table').datagrid('hideColumn','control');
	}
} 
});

//格式化操作按钮
function rowformater(value,row,index)
{
	var btn = '<a href="javascript:void(0)" class="editcls1" onclick="editRow(\''+row.id+'\',\''+row.operator+'\')">'+'</a>';
	return btn;
};


//分配坐席按钮点击事件
function editRow(id,operator){
	$('#misscall_id').val(id);
	
	if(count == 0)
	{
		items = $('#account_seat').combobox('getData');
		items.push({'agent':'0','name':'关闭'});
		count = 1;
	}
	$('#account_seat').combobox('loadData',items);
	$('#account_seat').combobox('setValue',operator);
	$('#allocation_dialog').dialog('open');
};

//分配新的用户
function setNewAccount(){
	var id = $('#misscall_id').val();
	var account = $('#account_seat').combobox('getValue');
	$.ajax( {
		cache : true,
		type : "POST",
		url : 'misscall.do?method=updateAccountById&id=' + encodeURIComponent(id) + '&account='+encodeURIComponent(account),
		async : false,
		dataType : "json",
		success : function(data) {
			
			if (data.status == "200") {
				$.messager.alert('系统消息', '分配成功！', 'info');
				$('#misscall_table').datagrid('reload');
				$('#allocation_dialog').dialog('close');
			}else{
				$.messager.alert('系统消息','分配失败！','error');
			}
		}
	});
};

//查询按钮点击事件
$('#search_misscall_btn').click(function(){

	   var beginTime = $('#beginTimeForSearch').datetimebox('getValue');
	   var endTime = $('#endTimeForSearch').datetimebox('getValue');
	   var phoneNumber = $('#phoneNumeberForSearch').val();
	   var phoneNumber_called = $('#phoneNumeberForSearch_called').val();

		var varify;//用于查询验证,验证开始时间是否小于结束时间 
		varify=beginTime>endTime; 

		if(beginTime!=''&&endTime!='')
		{
			if(varify){
				$.messager.alert('系统消息','结束时间要大于开始时间','warning');
				return ;
				}
		}
 	//显示第一页数据   
     $('#misscall_table').datagrid("options").pageNumber = 1;
     
     //分页栏上跳转到第一页
     $('#misscall_table').datagrid('getPager').pagination({pageNumber: 1}); 
		$('#misscall_table').datagrid({
			url : 'misscall.do?method=findMisscallByCondition&beginTime=' + encodeURIComponent(beginTime) + '&endTime='+encodeURIComponent(endTime)+'&phoneNumber='+encodeURIComponent(phoneNumber)+'&phoneNumber_called='+encodeURIComponent(phoneNumber_called)
		});
});

var toolOfDetailQuestion = [{
	text:'添加问题',
	iconCls:'icon-add',
	handler:function(){
	
		//判断信息是否已经填写完整
    	if (endEditing('detailQuestion'))
    	{
    		//设定跟进问题的编辑标记
			isDetailQuestionEdited = true;
			
	    	//追加行
			$('#detail_question_table').datagrid('appendRow',{});
			
			//标记追加行的行号
			detailQuestionEditIndex = $('#detail_question_table').datagrid('getRows').length-1;
			
			//设置当前追加的行为编辑状态
			$('#detail_question_table').datagrid('selectRow', detailQuestionEditIndex).datagrid('beginEdit', detailQuestionEditIndex);
    	}
	}
},'-', {
    text: '删除问题',
    iconCls: 'icon-remove',
    handler:function () {
        var rows = $("#detail_question_table").datagrid('getSelections');
        if (rows.length > 0) {
            $.messager.confirm('请确认', '您确定要删除当前所有选择的项目吗?', function(b) {
                if(b) {
                	
                	//设定跟进问题的编辑标记
        			isDetailQuestionEdited = true;
        			
                    $('#detail_question_table').datagrid('cancelEdit', detailQuestionEditIndex).datagrid('deleteRow', detailQuestionEditIndex);
                    detailQuestionEditIndex = undefined;
                }
            });
        } else {
            $.messager.alert('提示', '请选择要删除的记录', 'error');
        }
    }
}];

//问题信息datagrid加载
$('#detail_question_table').datagrid({
	pageSize : 5,//默认选择的分页是每页5行数据
	pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
	striped : true,//设置为true将交替显示行背景。
	singleSelect:true,//为true时只能选择单行
	remoteSort : false,
	pagination : true,//分页
	iconCls: 'icon-edit',
	collapsible:true,
	//toolbar:toolOfDetailQuestion,
	onClickRow: selectDetailQuestion
});

//跟进问题信息datagrid隐藏列
$('#detail_question_table').datagrid('hideColumn','id');

//跟进历史信息datagrid加载
$('#follow_history_table').datagrid({
	pageSize : 5,//默认选择的分页是每页5行数据
	pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
	striped : true,//设置为true将交替显示行背景。
	singleSelect:true,//为true时只能选择单行
	remoteSort : false,
	pagination : true,//分页
	iconCls: 'icon-edit',
	collapsible:true
});

//跟进历史信息datagrid隐藏列
$('#follow_history_table').datagrid('hideColumn','id');
$('#follow_history_table').datagrid('hideColumn','workOrderId');

//选择问题详情
function selectDetailQuestion(index){
	if (detailQuestionEditIndex != index){
		if (endEditing('detailQuestion')){
			
			//设定跟进问题的编辑标记
			isDetailQuestionEdited = true;
			
			//设置选中的行为编辑状态
			$(this).datagrid('selectRow', index).datagrid('beginEdit', index);
			
			//设置问题类别的初始值
			var target1 = $('#detail_question_table').datagrid('getEditor', { 'index': index, 'field': 'type' }).target;
			var target2 = $('#detail_question_table').datagrid('getEditor', { 'index': index, 'field': 'mold' }).target;
            var mold = target2.combobox('getValue');
            target1.combobox('reload','question_type.do?method=findQuestionTypeByQuestionMold&questionMold='+mold);
			
			detailQuestionEditIndex = index;
		} else {
			//如果必输项目没有填写完整的话无法选择其它行
			$(this).datagrid('selectRow', detailQuestionEditIndex);
		}
	}
};

//打开工单详情dialog
function openWorkOrderDetail(id,treatmentScheme,finalDecision)
{
	//编辑标记重置
	isDetailWorkOrderEdited = false;
	isDetailQuestionEdited = false;
	detailQuestionEditIndex = undefined;
    
	//问题datagrid加载
	$('#detail_question_table').datagrid({url:'work_order_question_link.do?method=findQuestionByWorkOrderId&workOrderId='+encodeURIComponent(id)});
	
	//跟进历史datagrid加载
	$('#follow_history_table').datagrid({url:'follow_history_info.do?method=findFollowHistory&workOrderId='+encodeURIComponent(id)});
	
	//给隐藏的工单ID赋值
	$('#work_order_id_detail').val(id);
	
	//给处理过程赋值
	$('#work_order_treatment_scheme_detail').val('');
	
	//给结案判定赋值
	$('#work_order_final_decision_detail').combobox('setValue',finalDecision);
	
	//打开跟进dialog
	$('#work_order_detail_dialog').dialog('open');
};

//导出工单详情excel
function exportWorkOrder()
{
	//验证信息的合法性
	if(!$('#begin_page').validatebox('isValid')||$('#begin_page').val()<=0)
	{
		$.messager.alert('系统消息','请输入正确的起始页！','info');
		return false;
	}
	if(!$('#end_page').validatebox('isValid')||$('#end_page').val()<=0)
	{
		$.messager.alert('系统消息','请输入正确的结束页！','info');
		return false;
	}
	if(!$('#data_row').validatebox('isValid')||$('#data_row').val()<=0)
	{
		$.messager.alert('系统消息','请输入正确的每页条数！','info');
		return false;
	}
	var begin_page = $('#begin_page').val();
	var end_page = $('#end_page').val();
	var data_row = $('#data_row').val();
	if((end_page-begin_page)<0)
	{
		$.messager.alert('系统消息','结束页不可小于起始页！','info');
		return false;
	}
	if(data_row<=0)
	{
		$.messager.alert('系统消息','每页条数要大于0！','info');
		return false;
	}
	
	//获取页面的值
	//工单信息
	var work_order_assembly_line_wom = $('#work_order_assembly_line_wom').val();
	var work_order_title_wom = $('#work_order_title_wom').val();
	var work_order_tel_wom = $('#work_order_tel_wom').val();
	var work_order_tel_time_begin_wom = $('#work_order_tel_time_begin_wom').datetimebox('getValue');
	var work_order_tel_time_end_wom = $('#work_order_tel_time_end_wom').datetimebox('getValue');
	var work_order_level_wom = $('#work_order_level_wom').combobox('getValue');
	var work_order_feedback_type_wom = $('#work_order_feedback_type_wom').combobox('getValue');
	var work_order_feedback_channel_wom = $('#work_order_feedback_channel_wom').combobox('getValue');
	var work_order_treatment_scheme_wom = $('#work_order_treatment_scheme_wom').val();
	var work_order_final_decision_wom = $('#work_order_final_decision_wom').combobox('getValue');
	var work_order_user_account_wom = $('#work_order_user_account_wom').combobox('getValue');
	var work_order_skill_group_wom = $('#work_order_skill_group_wom').combobox('getValue');
	
	//客户信息
	var customer_name_wom = $('#customer_name_wom').val();
	var customer_sex_wom = $('#customer_sex_wom').combobox('getValue');
	var customer_area_wom = $('#customer_area_wom').val();
	var customer_qq_wom = $('#customer_qq_wom').val();
	var customer_tel_wom = $('#customer_tel_wom').val();
	var customer_wechat_wom = $('#customer_wechat_wom').val();
	var customer_weibo_wom = $('#customer_weibo_wom').val();
	var customer_taobao_wom = $('#customer_taobao_wom').val();
	var customer_email_wom = $('#customer_email_wom').val();
	var customer_jd_wom = $('#customer_jd_wom').val();
	var customer_type_wom = $('#customer_type_wom').combobox('getValue');
	
	//问题信息
	var question_mold_wom = $('#question_mold_wom').combobox('getValue');
	var question_type_wom = $('#question_type_wom').combobox('getValue');
	var question_describe_wom = $('#question_describe_wom').val();
	var question_phone_imei_wom = $('#question_phone_imei_wom').val();
	var question_phone_model_wom = $('#question_phone_model_wom').combobox('getValue');
	var question_phone_version_wom = $('#question_phone_version_wom').combobox('getValue');
	var question_status_wom = $('#question_status_wom').combobox('getValue');
	
	//设置页数和每页的条数
//	var page = 1;
//	var rows = $('#work_order_table').datagrid('options').pageSize;
	var page = ($('#begin_page').val()-1)*$('#data_row').val();
	var rows = ($('#end_page').val()-$('#begin_page').val()+1)*$('#data_row').val(); 
	
	//封装成对象
	var work_order_detail_info = JSON.stringify({
		customerId:'',
		name:customer_name_wom,
		sex:customer_sex_wom,
		age:'',
		area:customer_area_wom,
		qq:customer_qq_wom,
		tel:customer_tel_wom,
		address:'',
		postalCode:'',
		wechat:customer_wechat_wom,
		weibo:customer_weibo_wom,
		nickname:'',
		taobao:customer_taobao_wom,
		email:customer_email_wom,
		jd:customer_jd_wom,
		type:customer_type_wom,
		agent:'',
		detail:'',
		questionId:'',
		mold:question_mold_wom,
		questionType:question_type_wom,
		describe:question_describe_wom,
		phoneImei:question_phone_imei_wom,
		phoneModel:question_phone_model_wom,
		phoneVersion:question_phone_version_wom,
		status:question_status_wom,
		workOrderId:'',
		assemblyLine:work_order_assembly_line_wom,
		workOrderTel:work_order_tel_wom,
		telTime:'',
		title:work_order_title_wom,
		level:work_order_level_wom,
		feedbackTime:'',
		feedbackType:work_order_feedback_type_wom,
		feedbackChannel:work_order_feedback_channel_wom,
		treatmentScheme:work_order_treatment_scheme_wom,
		finalDecision:work_order_final_decision_wom,
		userAgent:'',
		userAccount:work_order_user_account_wom,
		skillGroup:work_order_skill_group_wom,
		uniqueId:'',
		page:page,
		rows:rows,
		beginTime:work_order_tel_time_begin_wom,
		endTime:work_order_tel_time_end_wom
	});
	alert(work_order_detail_info);
	//window.location.href = 'export.do?method=exportWorkOrder&work_order_detail_info='+encodeURIComponent(work_order_detail_info);
	
	javascropt:exportApp.exportWorkOrderExcel(work_order_detail_info);
};

//导出excel
$('#export_btn').click(function(){
	
	$('#begin_page').val('1').validatebox('validate');
	$('#end_page').val('1').validatebox('validate');
	$('#data_row').val('10').validatebox('validate');
	$('#export_excel_dialog').dialog('open');
});

//删除工单详情
function deleteWorkOrderDetail(workOrderId)
{
	//删除工单
	$.ajax( {
		cache : true,
		type : "POST",
		url : 'work_order.do?method=deleteWorkOrder&workOrderId='+encodeURIComponent(workOrderId),
		async : false,
		dataType : "json",
		success : function(data) {
			
			if (data.status == "200") {
				
				$('#work_order_table').datagrid('reload');
				
				$.messager.alert('系统消息', '删除工单信息成功！', 'info');
			}else{
				$.messager.alert('系统消息', '删除工单信息失败！','error');
			}
		}
	});
};

//保存工单详情
function saveDetailWorkOrder()
{
	var account = GetQueryString('account');
	var agent = GetQueryString('agent');
	var isMonitor = GetQueryString('isMonitor');
	if(isMonitor != '1')
	{

		$.messager.alert('提示', '非坐席班长无法修改工单！', 'error');
		return;
	}
	
	//判断问题信息是否填写完整
	if (!$('#detail_question_table').datagrid('validateRow', detailQuestionEditIndex))
	{
		$.messager.alert('系统消息','请填写完整问题信息！','warning');
		return;
	}
	
	//结束问题信息的编辑
	$('#detail_question_table').datagrid('endEdit', detailQuestionEditIndex);
	detailQuestionEditIndex = undefined;
	//取消问题信息的选择
	$('#detail_question_table').datagrid('uncheckAll');
	
	//收集工单信息
	var work_order_id_detail = $('#work_order_id_detail').val();
	var work_order_treatment_scheme_detail = $('#work_order_treatment_scheme_detail').val();
	var work_order_final_decision_detail = $('#work_order_final_decision_detail').combobox('getValue');
	
	if(work_order_treatment_scheme_detail =='')
	{
		$.messager.alert('系统消息','请填写跟进过程！','warning');
		return;
	}
	
	var work_order_info_json=JSON.stringify({
		id:work_order_id_detail,
		userAgent:agent,
		userAccount:account,
		treatmentScheme:work_order_treatment_scheme_detail,
		finalDecision:work_order_final_decision_detail
	});
	
	//跟进历史信息收集
	var d = new Date();
	var submit_time = d.getFullYear() + "-" +((d.getMonth()+1)<10?"0":"")+(d.getMonth()+1) + "-" + (d.getDate()<10?"0":"")+d.getDate() + " " + (d.getHours()<10?"0":"")+d.getHours() + ":" + (d.getMinutes()<10?"0":"")+d.getMinutes() + ":" + (d.getSeconds()<10?"0":"")+d.getSeconds();
	var follow_history_info_json = JSON.stringify({
		workOrderId:work_order_id_detail,
		account:account,
		agent:agent,
		treatmentScheme:work_order_treatment_scheme_detail,
		time:submit_time
	});
	
	var question_info_detail_insert_json = JSON.stringify($('#detail_question_table').datagrid('getChanges','inserted'));
	var question_info_detail_update_json = JSON.stringify($('#detail_question_table').datagrid('getChanges','updated'));
	var question_info_detail_delete_json = JSON.stringify($('#detail_question_table').datagrid('getChanges','deleted'));
	
	//保存工单
	$.ajax( {
		cache : true,
		type : "POST",
		url : 'work_order.do?method=updateWorkOrderQuestionInfo&customerId='+encodeURIComponent($('#customer_id').val())+'&workOrderInfo=' + encodeURIComponent(work_order_info_json) + '&question_info_insert_json=' + encodeURIComponent(question_info_detail_insert_json) +'&question_info_update_json='+encodeURIComponent(question_info_detail_update_json)+'&question_info_delete_json='+encodeURIComponent(question_info_detail_delete_json)+'&follow_history_info_json='+encodeURIComponent(follow_history_info_json),
		async : false,
		dataType : "json",
		success : function(data) {
			
			if (data.status == "200") {
				
				$('#work_order_detail_dialog').dialog('close');
				
				$('#work_order_table').datagrid('reload');
				
				$.messager.alert('系统消息', '保存工单信息成功！', 'info');
			}else{
				$.messager.alert('系统消息', '保存工单信息失败！','error');
			}
		}
	});
};

//datagrid结束编辑
function endEditing(type){
	if(type=='detailQuestion'){
		if (detailQuestionEditIndex == undefined){return true;}
		//检查当前编辑行的必输项是否全部填写完整
		if ($('#detail_question_table').datagrid('validateRow', detailQuestionEditIndex)){
			var mold = $('#detail_question_table').datagrid('getEditor', {index:detailQuestionEditIndex,field:'mold'});
			var moldText = $(mold.target).combobox('getText');
			$('#detail_question_table').datagrid('getRows')[detailQuestionEditIndex]['mold'] = moldText;

			var type = $('#detail_question_table').datagrid('getEditor', {index:detailQuestionEditIndex,field:'type'});
			var typeText = $(type.target).combobox('getText');
			$('#detail_question_table').datagrid('getRows')[detailQuestionEditIndex]['type'] = typeText;
			
			var describe = $('#detail_question_table').datagrid('getEditor', {index:detailQuestionEditIndex,field:'describe'});
			var describeText = $(describe.target).val();
			$('#detail_question_table').datagrid('getRows')[detailQuestionEditIndex]['describe'] = describeText;
			
			var status= $('#detail_question_table').datagrid('getEditor', {index:detailQuestionEditIndex,field:'status'});
			var statusText = $(status.target).combobox('getText');
			$('#detail_question_table').datagrid('getRows')[detailQuestionEditIndex]['status'] = statusText;
			
			$('#detail_question_table').datagrid('endEdit', detailQuestionEditIndex);
			detailQuestionEditIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
}

//关联录音
function saveWorkOrderRecord()
{
	
	//获取页面信息
	var record_id = $('#record_id').val();
	var assemblyLine = $('#work_order_assembly_line_link').val();
	
	//保存录音
	$.ajax( {
		cache : true,
		type : "POST",
		url : 'record.do?method=linkRecord&recordId='+encodeURIComponent(record_id)+'&assemblyLine=' + encodeURIComponent(assemblyLine),
		async : false,
		dataType : "json",
		success : function(data) {
			
			if (data.status == "200") {
				
				$('#link_record_dialog').dialog('close');
				
				$.messager.alert('系统消息', '关联录音成功！', 'info');
			}else{
				$.messager.alert('系统消息', '关联录音失败！','error');
			}
		}
	});
};

//查找按钮点击事件
$('#work_order_manager_search_btn').click(function(){
	
	//获取页面的值
	//工单信息
	var work_order_assembly_line_wom = $('#work_order_assembly_line_wom').val();
	var work_order_title_wom = $('#work_order_title_wom').val();
	var work_order_tel_wom = $('#work_order_tel_wom').val();
	var work_order_tel_time_begin_wom = $('#work_order_tel_time_begin_wom').datetimebox('getValue');
	var work_order_tel_time_end_wom = $('#work_order_tel_time_end_wom').datetimebox('getValue');
	var work_order_level_wom = $('#work_order_level_wom').combobox('getValue');
	var work_order_feedback_type_wom = $('#work_order_feedback_type_wom').combobox('getValue');
	var work_order_feedback_channel_wom = $('#work_order_feedback_channel_wom').combobox('getValue');
	var work_order_treatment_scheme_wom = $('#work_order_treatment_scheme_wom').val();
	var work_order_final_decision_wom = $('#work_order_final_decision_wom').combobox('getValue');
	var work_order_user_account_wom = $('#work_order_user_account_wom').combobox('getValue');
	var work_order_skill_group_wom = $('#work_order_skill_group_wom').combobox('getValue');
	
	//客户信息
	var customer_name_wom = $('#customer_name_wom').val();
	var customer_sex_wom = $('#customer_sex_wom').combobox('getValue');
	var customer_area_wom = $('#customer_area_wom').val();
	var customer_qq_wom = $('#customer_qq_wom').val();
	var customer_tel_wom = $('#customer_tel_wom').val();
	var customer_wechat_wom = $('#customer_wechat_wom').val();
	var customer_weibo_wom = $('#customer_weibo_wom').val();
	var customer_taobao_wom = $('#customer_taobao_wom').val();
	var customer_email_wom = $('#customer_email_wom').val();
	var customer_jd_wom = $('#customer_jd_wom').val();
	var customer_type_wom = $('#customer_type_wom').combobox('getValue');
	
	//问题信息
	var question_mold_wom = $('#question_mold_wom').combobox('getValue');
	var question_type_wom = $('#question_type_wom').combobox('getValue');
	var question_describe_wom = $('#question_describe_wom').val();
	var question_phone_imei_wom = $('#question_phone_imei_wom').val();
	var question_phone_model_wom = $('#question_phone_model_wom').combobox('getValue');
	var question_phone_version_wom = $('#question_phone_version_wom').combobox('getValue');
	var question_status_wom = $('#question_status_wom').combobox('getValue');
	
	//设置页数和每页的条数
	var page = 1;
	var rows = $('#work_order_table').datagrid('options').pageSize;
	
	//封装成对象
	var work_order_detail_info = JSON.stringify({
		customerId:'',
		name:customer_name_wom,
		sex:customer_sex_wom,
		age:'',
		area:customer_area_wom,
		qq:customer_qq_wom,
		tel:customer_tel_wom,
		address:'',
		postalCode:'',
		wechat:customer_wechat_wom,
		weibo:customer_weibo_wom,
		nickname:'',
		taobao:customer_taobao_wom,
		email:customer_email_wom,
		jd:customer_jd_wom,
		type:customer_type_wom,
		agent:'',
		detail:'',
		questionId:'',
		mold:question_mold_wom,
		questionType:question_type_wom,
		describe:question_describe_wom,
		phoneImei:question_phone_imei_wom,
		phoneModel:question_phone_model_wom,
		phoneVersion:question_phone_version_wom,
		status:question_status_wom,
		workOrderId:'',
		assemblyLine:work_order_assembly_line_wom,
		workOrderTel:work_order_tel_wom,
		telTime:'',
		title:work_order_title_wom,
		level:work_order_level_wom,
		feedbackTime:'',
		feedbackType:work_order_feedback_type_wom,
		feedbackChannel:work_order_feedback_channel_wom,
		treatmentScheme:work_order_treatment_scheme_wom,
		finalDecision:work_order_final_decision_wom,
		userAgent:'',
		userAccount:work_order_user_account_wom,
		skillGroup:work_order_skill_group_wom,
		uniqueId:'',
		page:page,
		rows:rows,
		beginTime:work_order_tel_time_begin_wom,
		endTime:work_order_tel_time_end_wom
	});
	
	//显示第一页数据   
    $('#work_order_table').datagrid("options").pageNumber = 1;
    
    //分页栏上跳转到第一页
    $('#work_order_table').datagrid('getPager').pagination({pageNumber: 1}); 
    
	//重新加载工单列表
	$('#work_order_table').datagrid({url:'work_order.do?method=findDetailWorkOrder&work_order_detail_info='+encodeURIComponent(work_order_detail_info)});
	
});