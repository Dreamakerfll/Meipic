
//获取页面URL参数
function GetQueryString(name) {

   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");

   var r = window.location.search.substr(1).match(reg);

   if (r!=null) return decodeURI(r[2]); return null;

};

//js获取项目根路径，如： http://localhost:8083/uimcardprj
function getRootPath(){
//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
var curWwwPath=window.document.location.href;
//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
var pathName=window.document.location.pathname;
var pos=curWwwPath.indexOf(pathName);
//获取主机地址，如： http://localhost:8083
var localhostPaht=curWwwPath.substring(0,pos);
//获取带"/"的项目名，如：/uimcardprj
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
return(localhostPaht+projectName);
}

//页面标记初始化
var isCustomerInfoEdited = false;
var isPhoneInfoEdited = false;
var phoneInfoEditIndex = undefined;

//客户信息被编辑事件
function customerInfoChange()
{
	isCustomerInfoEdited = true;
};

//页面预加载
$(function(){
	
    //性别加载
	$('#customer_sex_search').combobox({
		url:'json/sex_search.json',
		valueField: 'sex',
        textField: 'sexDisplay',
        editable:false ,
		panelHeight: 'auto'
		});
	
	//客户类别加载
	$('#customer_type_search').combobox({
		url:'customer_type.do?method=findAll&type=0',
		valueField: 'customerType',
        textField: 'customerTypeDisplay',
        editable:false ,
		panelHeight: 200
		});
	
	//专属客服加载
	$('#customer_agent_search').combobox({
		url:'user_info.do?method=findAll&type=0',
		valueField: 'agent',
		textField: 'agentDisplay',
		editable:false ,
		panelHeight: 200
	});
	
	//手机型号加载
	$('#phone_model_search').combobox({
		url:'phone_model.do?method=findAll&type=0',
		valueField: 'phoneModel',
        textField: 'phoneModelDisplay',
        editable:false ,
		panelHeight: 200
		});
	
	//手机版本加载
	$('#phone_version_search').combobox({
		url:'phone_version.do?method=findAll&type=0',
		valueField: 'phoneVersion',
		textField: 'phoneVersionDisplay',
		editable:false ,
		panelHeight: 200
	});
	
});

var toolOfCustomer = [{
	text:'新增客户',
	iconCls:'icon-add',
	handler:function(){
		openCustomerDetail('',
    			'',
    			'女',
    			'',
    			'',
    			'',
    			'',
    			'',
    			'',
    			'',
    			'',
    			'',
    			'',
    			'',
    			'',
    			'1类',
    			'',
    			'');
		
	}
},'-', {
    text: '删除客户',
    iconCls: 'icon-remove',
    handler:function () {
        var rows = $("#customer_table").datagrid('getSelections');
        if (rows.length > 0) {
        	var isMonitor = GetQueryString('isMonitor');
        	var account = GetQueryString('account');
        	if(isMonitor != '1')
        	{
        		if(rows[0].agent != account)
        		{
        			$.messager.alert('提示', '非坐席班长无法删除其他坐席的客户！', 'error');
        			return;
        		}
        	}
            $.messager.confirm('请确认', '删除客户会一并删除工单信息和手机信息，您确定要删除当前所选择的客户吗?', function(b) {
                if(b) {
                	deleteCustomer(rows[0].customerId);
                }
            });
        } else {
            $.messager.alert('提示', '请选择要删除的记录', 'error');
        }
    }
},'-', {
    text: '客户详情',
    iconCls: 'icon-edit',
    handler:function () {
        var rows = $("#customer_table").datagrid('getSelections');
        if (rows.length > 0) {
        	openCustomerDetail(rows[0].customerId,
        			rows[0].name,
        			rows[0].sex,
        			rows[0].age,
        			rows[0].area,
        			rows[0].tel,
        			rows[0].qq,
        			rows[0].address,
        			rows[0].postalCode,
        			rows[0].wechat,
        			rows[0].weibo,
        			rows[0].nickname,
        			rows[0].taobao,
        			rows[0].email,
        			rows[0].jd,
        			rows[0].type,
        			rows[0].agent,
        			rows[0].detail);
        } else {
            $.messager.alert('提示', '请选择要查看详情的客户', 'info');
        }
    }
},'-', {
    text: '导入',
    iconCls: 'icon-import',
    handler:function () {
    	
//    	$.ajax( {
//    		cache : true,
//    		type : "POST",
//    		url : 'import.do?method=importCustomer',
//    		async : false,
//    		dataType : "json",
//    		success : function(data) {
//    			
//    			if (data.status == "200") {
//    				$('#customer_table').datagrid('reload');
//    				$.messager.alert('系统消息','导入成功！', 'info');
//    			}else{
//    				$.messager.alert('系统消息','导入失败！','error');
//    			}
//    		}
//    	});
    	$('#import_excel_dialog').dialog('open');
    }
},'-', {
    text: '导出',
    iconCls: 'icon-export',
    handler:function () {
    	$('#begin_page').val('1').validatebox('validate');
    	$('#end_page').val('1').validatebox('validate');
    	$('#data_row').val('10').validatebox('validate');
    	$('#export_excel_dialog').dialog('open');
    }
},'-', {
    text: '模板下载',
    iconCls: 'icon-download',
    handler:function () {
        exportModel();
    }
}];

//提交上传的文件
function submitFile()
{
	$('#customer_form').ajaxSubmit({ 
		type : "POST",
        dataType:"json",
        url : 'import.do?method=importCustomer',
        success : function(data) {
        	
        	if (data.status == "200") {
        		$('#customer_table').datagrid('reload');
        		$('#import_excel_dialog').dialog('close');
        		$.messager.alert('系统消息','导入成功！', 'info');
        	}else{
        		$.messager.alert('系统消息','导入失败！'+data.sign,'error');
        	}
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){ 
        	$.messager.alert('系统消息','导入失败！','error');
        } 
    }); 
     
};

//打开客户详情的dialog
function openCustomerDetail(customerId,name,sex,age,area,tel,qq,address,postalCode,wechat,weibo,nickname,taobao,email,jd,type,agent,detail)
{
	//页面初始值设置
	$('#customer_id_edit').val(customerId);
	$('#customer_name_edit').val(name);
	$('#customer_agent_tem_edit').val(agent);
	
    //性别加载
	$('#customer_sex_edit').combobox({
		url:'json/sex.json',
		valueField: 'sex',
        textField: 'sexDisplay',
        editable:false ,
		panelHeight: 'auto',
		onSelect: function (rec) {
			customerInfoChange();
		}
		});
	$('#customer_sex_edit').combobox('setValue',sex);
	$('#customer_age_edit').val(age).validatebox('validate');
	$('#customer_area_edit').val(area);
	$('#customer_tel1_edit').val('').validatebox('validate');
	
	//联系电话2加载
	var tel2 = [];
	var tel_items = tel.split(',');
	for(var i=0;i<tel_items.length-1;i++)
	{
		tel2.push({'telNumber':tel_items[i]});
	}
	
	$('#customer_tel2_edit').combogrid({
		value: 'telNumber',
        idField: 'telNumber',
        textField: 'telNumber',
        multiple: true,
        editable:false ,
        fitColumns: true,
		columns: [[
					{field:'ck',checkbox:true},
					{field:'telNumber',title:'联系电话',width:120,align:'right'}
				]],
		onSelect: function (rowIndex, rowData) {
			customerInfoChange();
		},
		onUnselect: function (rowIndex, rowData) {
			customerInfoChange();
		}
		});
	$('#customer_tel2_edit').combogrid('clear');
	$('#customer_tel2_edit').combogrid("grid").datagrid("loadData", tel2);
	$('#customer_tel2_edit').combogrid('setValues',tel2);
	$('#customer_qq_edit').val(qq).validatebox('validate');
	$('#customer_address_edit').val(address);
	$('#customer_postal_code_edit').val(postalCode).validatebox('validate');
	$('#customer_wechat_edit').val(wechat);
	$('#customer_weibo_edit').val(weibo);
	$('#customer_nickname_edit').val(nickname);
	$('#customer_taobao_edit').val(taobao);
	$('#customer_email_edit').val(email).validatebox('validate');
	$('#customer_jd_edit').val(jd);
	
	//客户类别
	$('#customer_type_edit').combobox({
		url:'customer_type.do?method=findAll',
		valueField: 'customerType',
        textField: 'customerTypeDisplay',
        editable:false ,
		panelHeight: 'auto',
		onSelect: function (rec) {
			customerInfoChange();
		}
		});
	$('#customer_type_edit').combobox('setValue',type);
	
	//专属客服加载
	$('#customer_agent_edit').combobox({
		url:'user_info.do?method=findAll',
		valueField: 'agent',
		textField: 'agentDisplay',
		editable:false ,
		panelHeight: 200,
		onSelect: function (rec) {
			customerInfoChange();
		}
	});
	
	if($('#customer_id_edit').val()=='')
	{
		var account = GetQueryString('account');
		$('#customer_agent_edit').combobox('setValue',account);
	}
	else
	{
		$('#customer_agent_edit').combobox('setValue',agent);
	}
	
	
	$('#customer_detail_edit').val(detail);
	
	//加载手机信息table
	$('#phone_info_edit_table').datagrid({url:'phone_info.do?method=findPhoneInfoByCustomerId&customerId='+encodeURIComponent(customerId)});
	
	$('#show_customer_detail_dialog').dialog('open');
	
	//页面标记初始化
	isCustomerInfoEdited = false;
	isPhoneInfoEdited = false;
	phoneInfoEditIndex = undefined;
};

//保存客户信息到数据库
function saveEditCustomer()
{
	//修改客户信息权限控制
	if($('#customer_id_edit').val()!='')
	{
		if(GetQueryString('isMonitor')!='1')
		{
			var account = GetQueryString('account');
			if($('#customer_agent_tem_edit').val()!=account)
			{
				$.messager.alert('系统消息','非班长坐席无法修改其他坐席的客户！','error');
				return false;
			}
		}
	}
	//判断页面必填项目
	if($('#customer_tel1_edit').val() == ''
		&&$('#customer_tel2_edit').combogrid('getValues').length == 0
		&&$('#customer_qq_edit').val() == ''
		&&$('#customer_wechat_edit').val() == ''
		&&$('#customer_weibo_edit').val() == ''
		&&$('#customer_taobao_edit').val() == ''
		&&$('#customer_email_edit').val() == ''
		&&$('#customer_jd_edit').val() == '')
	{
		$.messager.alert('系统消息','联系电话、QQ号码、微信号、微博地址、淘宝账号、电子邮件、京东账号不能同时为空！','warning');
		return false;
	}
	
	//数据没变更则直接关闭
	if(!isCustomerInfoEdited&&!isPhoneInfoEdited)
	{
		$('#show_customer_detail_dialog').dialog('close');
		
		//清空手机信息
		$('#phone_info_edit_table').datagrid('loadData', { total: 0, rows: [] });
		
		return false;
	}
	
	//验证页面项目合法性
	if(!$('#customer_age_edit').validatebox('isValid'))
	{
		$.messager.alert('系统消息','请输入正确的年龄！','info');
		return false;
	}
	if(!$('#customer_tel1_edit').validatebox('isValid'))
	{
		$.messager.alert('系统消息','请输入正确的联系电话！','info');
		return false;
	}
	if(!$('#customer_qq_edit').validatebox('isValid'))
	{
		$.messager.alert('系统消息','请输入正确的QQ号码！','info');
		return false;
	}
	if(!$('#customer_postal_code_edit').validatebox('isValid'))
	{
		$.messager.alert('系统消息','请输入正确的邮政编码！','info');
		return false;
	}
	if(!$('#customer_email_edit').validatebox('isValid'))
	{
		$.messager.alert('系统消息','请输入正确的邮箱！','info');
		return false;
	}
	
	//判断手机信息是否填写完整
	if (!$('#phone_info_edit_table').datagrid('validateRow', phoneInfoEditIndex))
	{
		$.messager.alert('系统消息','请填写完整手机信息！','warning');
		return;
	}
	
	//结束手机信息的编辑
	$('#phone_info_edit_table').datagrid('endEdit', phoneInfoEditIndex);
	phoneInfoEditIndex = undefined;
	//取消手机信息的选择
	$('#phone_info_edit_table').datagrid('uncheckAll');
	
	//收集页面客户信息
	var customer_id = $('#customer_id_edit').val();
	var customer_name = $('#customer_name_edit').val();
	var customer_sex = $('#customer_sex_edit').combobox('getValue');
	var customer_age = $('#customer_age_edit').val();
	
	var customer_area = $('#customer_area_edit').val();
	var customer_tel1 = $('#customer_tel1_edit').val();
	var customer_tel2 = JSON.stringify($('#customer_tel2_edit').combogrid("grid").datagrid('getSelections'));
	
	var customer_qq = $('#customer_qq_edit').val();
	var customer_address = $('#customer_address_edit').val();
	var customer_postal_code = $('#customer_postal_code_edit').val();
	
	var customer_wechat = $('#customer_wechat_edit').val();
	var customer_weibo = $('#customer_weibo_edit').val();
	var customer_nickname = $('#customer_nickname_edit').val();
	
	var customer_taobao = $('#customer_taobao_edit').val();
	var customer_email = $('#customer_email_edit').val();
	var customer_jd = $('#customer_jd_edit').val();
	
	var customer_type = $('#customer_type_edit').combobox('getValue');
	var customer_agent = $('#customer_agent_edit').combobox('getValue');
	var customer_detail = $('#customer_detail_edit').val();
	var customer_info_json=JSON.stringify({
			id:customer_id,
			name:customer_name,
			sex:customer_sex,
			age:customer_age,

			area:customer_area,
			tel:customer_tel2,

			qq:customer_qq,
			address:customer_address,
			postalCode:customer_postal_code,

			wechat:customer_wechat,
			weibo:customer_weibo,
			nickname:customer_nickname,

			taobao:customer_taobao,
			email:customer_email,
			jd:customer_jd,

			type:customer_type,
			agent:customer_agent,
			detail:customer_detail
	});
	
	var phone_info_insert_json = JSON.stringify($('#phone_info_edit_table').datagrid('getChanges','inserted'));
	var phone_info_update_json = JSON.stringify($('#phone_info_edit_table').datagrid('getChanges','updated'));
	var phone_info_delete_json = JSON.stringify($('#phone_info_edit_table').datagrid('getChanges','deleted'));
	
	//客户不存在则新建客户，否则修改客户信息
	$.ajax( {
		cache : true,
		type : "POST",
		url : 'work_order.do?method=updateCustomerPhoneInfo&tel=' + encodeURIComponent(customer_tel1) +'&customer_info_json=' + encodeURIComponent(customer_info_json) + '&phone_info_insert_json=' + encodeURIComponent(phone_info_insert_json) +'&phone_info_update_json='+encodeURIComponent(phone_info_update_json)+'&phone_info_delete_json='+encodeURIComponent(phone_info_delete_json),
		async : false,
		dataType : "html",
		success : function(data) {
			
			var json = $.parseJSON(data);
			if (json.status == "200") {
				
				//获取联系电话2先前的数据
				var data1 = [];
				var tel2_items = $('#customer_tel2_edit').combogrid("grid").datagrid('getSelections');
				
				for(var i=0;i<tel2_items.length;i++)
				{
					data1.push(tel2_items[i]);
				}
				//如果联系电话1不为空的情况
				if($('#customer_tel1_edit').val()!= '')
				{
					//追加联系电话1
					var tel = {telNumber:$('#customer_tel1_edit').val()};
					data1.push(tel); 
				}
				
				//联系电话2重新加载
				$('#customer_tel2_edit').combogrid('clear');
				$('#customer_tel2_edit').combogrid("grid").datagrid("loadData", data1);
				
				$('#customer_tel2_edit').combogrid('setValues',data1);
				
				//清空联系电话1
				$('#customer_tel1_edit').val('');
				
				
				$('#customer_id_edit').val(json.sign);
        		
				//清除编辑的标记
				isCustomerInfoEdited = false;
				isPhoneInfoEdited = false;
				
        		//显示第一页数据   
                $('#phone_info_edit_table').datagrid("options").pageNumber = 1;
                
                //分页栏上跳转到第一页
                $('#phone_info_edit_table').datagrid('getPager').pagination({pageNumber: 1}); 
                
        		//重新加载手机信息table
				$('#phone_info_edit_table').datagrid({url:'phone_info.do?method=findPhoneInfoByCustomerId&customerId='+encodeURIComponent($('#customer_id_edit').val())});
				
				$('#customer_table').datagrid('reload');
				
				//弹出确认框
				$.messager.confirm('请确认', '保存客户信息成功，要关闭客户修改窗口吗？', function(b){
					if(b)
					{
						//关闭客户详情dialog
						$('#show_customer_detail_dialog').dialog('close');
					}
				});
				
				
				
			}else{
				$.messager.alert('系统消息', '保存客户信息失败！','error');
			}
		}
	});
};

//取消客户详情的编辑
function cancelSaveEditCustomer()
{
	if(isCustomerInfoEdited||isPhoneInfoEdited)
	{
		//弹出确认框
		$.messager.confirm('请确认', '用户信息有修改，确认取消编辑？', function(b){
			if(b)
			{
				$('#show_customer_detail_dialog').dialog('close');
				
				isCustomerInfoEdited = false;
				isPhoneInfoEdited = false;
				
				//清空手机信息
				$('#phone_info_edit_table').datagrid('loadData', { total: 0, rows: [] });
			}
		});
	}
	else
	{
		$('#show_customer_detail_dialog').dialog('close');
		
		//清空手机信息
		$('#phone_info_edit_table').datagrid('loadData', { total: 0, rows: [] });
	}
	
};

//客户详细信息datagrid加载
$('#customer_table').datagrid({
	url:'customer_phone_link.do?method=findCustomerPhoneInfoByCondition',
	toolbar:toolOfCustomer,
    pageSize : 10,//默认选择的分页是每页5行数据
    pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
    striped : true,//设置为true将交替显示行背景。
    remoteSort : false,
    pagination : true,//分页
    onClickRow:function(rowIndex, rowData){
    	var customerId = rowData.customerId;
    	
    	//取消所有行的选择
    	$('#customer_table').datagrid('clearSelections');
    	
    	//重新选择
    	for ( var int = 0; int < $('#customer_table').datagrid("getRows").length; int++) {
			if($('#customer_table').datagrid('getData').rows[int]['customerId']==customerId)
				{
					$('#customer_table').datagrid('selectRow',int);
				}
		}
    },
    onLoadSuccess: function(data){
    	var colList = "customerId,name,sex,age,area,tel,qq,address,postalCode,wechat,weibo,nickname,taobao,email,jd,type,agent,detail";
    	 var ColArray = colList.split(",");
    	    var tTable = $('#customer_table');
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

//删除客户信息
function deleteCustomer(customerId)
{
	$.ajax( {
		cache : true,
		type : "POST",
		url : 'customer_info.do?method=deleteCustomer&customerId=' + encodeURIComponent(customerId),
		async : false,
		dataType : "json",
		success : function(data) {
			
			if (data.status == "200") {
				$.messager.alert('系统消息', '删除成功！', 'info');
				$('#customer_table').datagrid('reload');
			}else{
				$.messager.alert('系统消息','删除失败！','error');
			}
		}
	});
	
};

//客户详细信息datagrid隐藏列
$('#customer_table').datagrid('hideColumn','customerId');

var toolOfEditPhone = [{
	text:'添加手机',
	iconCls:'icon-add',
	handler:function(){
		//判断信息是否已经填写完整
    	if (endEditing('phoneInfoEdit'))
    	{
    		//设定手机信息的编辑标记
			isPhoneInfoEdited = true;
			
	    	//追加行
			$('#phone_info_edit_table').datagrid('appendRow',{});
			
			//标记追加行的行号
			phoneInfoEditIndex = $('#phone_info_edit_table').datagrid('getRows').length-1;
			
			//设置当前追加的行为编辑状态
			$('#phone_info_edit_table').datagrid('selectRow', phoneInfoEditIndex).datagrid('beginEdit', phoneInfoEditIndex);
    	}
	}
},'-', {
    text: '删除手机',
    iconCls: 'icon-remove',
    handler:function () {
        var rows = $("#phone_info_edit_table").datagrid('getSelections');
        if (rows.length > 0) {
            $.messager.confirm('请确认', '您确定要删除当前所有选择的项目吗?', function(b) {
                if(b) {
                    
                	//设定手机信息的编辑标记
        			isPhoneInfoEdited = true;
        			
                    $('#phone_info_edit_table').datagrid('cancelEdit', phoneInfoEditIndex).datagrid('deleteRow', phoneInfoEditIndex);
            		phoneInfoEditIndex = undefined;
                }
            });
        } else {
            $.messager.alert('提示', '请选择要删除的记录', 'error');
        }
    }
}];

//手机信息datagrid加载
$('#phone_info_edit_table').datagrid({
    pageSize : 5,//默认选择的分页是每页5行数据
    pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
    striped : true,//设置为true将交替显示行背景。
    singleSelect:true,//为true时只能选择单行
    remoteSort : false,
    pagination : true,//分页
    iconCls: 'icon-edit',
    collapsible:true,
    toolbar:toolOfEditPhone,
    onClickRow: selectPhoneInfo,
    
    //发送加载数据的请求前触发，如果返回 false加载动作就会取消
    onBeforeLoad:function (param){
	if(isPhoneInfoEdited)
	{
		$.messager.alert('系统消息','手机信息已修改，请先保存！','warning');
		return false;
	}
	
		return true;
	}
});

//手机信息datagrid隐藏列
$('#phone_info_edit_table').datagrid('hideColumn','id');

//查找客户按钮点击事件
$('#search_customer_phone_info_search_btn').click(function(){
	
	//页面上客户的信息
	var customer_name_search = $('#customer_name_search').val();
	var customer_sex_search = $('#customer_sex_search').combobox('getValue');
	var customer_age_search = $('#customer_age_search').val();
	
	var customer_area_search = $('#customer_area_search').val();
	var customer_tel_search = $('#customer_tel_search').val();
	var phone_buy_time_search = $('#phone_buy_time_search').datebox('getValue');
	
	var customer_qq_search = $('#customer_qq_search').val();
	var customer_address_search = $('#customer_address_search').val();
	var customer_postal_code_search = $('#customer_postal_code_search').val();
	
	var customer_wechat_search = $('#customer_wechat_search').val();
	var customer_weibo_search = $('#customer_weibo_search').val();
	var customer_nickname_search = $('#customer_nickname_search').val();
	
	var customer_taobao_search = $('#customer_taobao_search').val();
	var customer_email_search = $('#customer_email_search').val();
	var customer_jd_search = $('#customer_jd_search').val();
	
	var customer_type_search = $('#customer_type_search').combobox('getValue');
	var customer_agent_search = $('#customer_agent_search').combobox('getValue');
	var customer_detail_search = $('#customer_detail_search').val();
	
	var phone_imei_search = $('#phone_imei_search').val();
	var phone_model_search = $('#phone_model_search').combobox('getValue');
	var phone_version_search = $('#phone_version_search').combobox('getValue');
	var page = 1;
	var rows = $('#customer_table').datagrid('options').pageSize; 
	
	var customer_phone_info_search_json=JSON.stringify({
			name:customer_name_search,
			sex:customer_sex_search,
			age:customer_age_search,

			area:customer_area_search,
			tel:customer_tel_search,

			qq:customer_qq_search,
			address:customer_address_search,
			postalCode:customer_postal_code_search,

			wechat:customer_wechat_search,
			weibo:customer_weibo_search,
			nickname:customer_nickname_search,

			taobao:customer_taobao_search,
			email:customer_email_search,
			jd:customer_jd_search,

			type:customer_type_search,
			agent:customer_agent_search,
			detail:customer_detail_search,
			
			buyTime:phone_buy_time_search,
			imei:phone_imei_search,
			model:phone_model_search,
			version:phone_version_search,
			page:page,
			rows:rows
	});
	
	//显示第一页数据   
    $('#customer_table').datagrid("options").pageNumber = 1;
    
    //分页栏上跳转到第一页
    $('#customer_table').datagrid('getPager').pagination({pageNumber: 1}); 
	
	$('#customer_table').datagrid({url:'customer_phone_link.do?method=findCustomerPhoneInfoByCondition&customer_phone_info_json='+encodeURIComponent(customer_phone_info_search_json)});
	
});

//导出客户详情到excel
function exportCustomer()
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
	
	//页面上客户的信息
	var customer_name_search = $('#customer_name_search').val();
	var customer_sex_search = $('#customer_sex_search').combobox('getValue');
	var customer_age_search = $('#customer_age_search').val();
	
	var customer_area_search = $('#customer_area_search').val();
	var customer_tel_search = $('#customer_tel_search').val();
	var phone_buy_time_search = $('#phone_buy_time_search').datebox('getValue');
	
	var customer_qq_search = $('#customer_qq_search').val();
	var customer_address_search = $('#customer_address_search').val();
	var customer_postal_code_search = $('#customer_postal_code_search').val();
	
	var customer_wechat_search = $('#customer_wechat_search').val();
	var customer_weibo_search = $('#customer_weibo_search').val();
	var customer_nickname_search = $('#customer_nickname_search').val();
	
	var customer_taobao_search = $('#customer_taobao_search').val();
	var customer_email_search = $('#customer_email_search').val();
	var customer_jd_search = $('#customer_jd_search').val();
	
	var customer_type_search = $('#customer_type_search').combobox('getValue');
	var customer_agent_search = $('#customer_agent_search').combobox('getValue');
	var customer_detail_search = $('#customer_detail_search').val();
	
	var phone_imei_search = $('#phone_imei_search').val();
	var phone_model_search = $('#phone_model_search').combobox('getValue');
	var phone_version_search = $('#phone_version_search').combobox('getValue');
	//var page = 1;
	//var rows = $('#customer_table').datagrid('options').pageSize; 
	var page = ($('#begin_page').val()-1)*$('#data_row').val();
	var rows = ($('#end_page').val()-$('#begin_page').val()+1)*$('#data_row').val(); 
	
	var customer_phone_info_search_json=JSON.stringify({
			name:customer_name_search,
			sex:customer_sex_search,
			age:customer_age_search,

			area:customer_area_search,
			tel:customer_tel_search,

			qq:customer_qq_search,
			address:customer_address_search,
			postalCode:customer_postal_code_search,

			wechat:customer_wechat_search,
			weibo:customer_weibo_search,
			nickname:customer_nickname_search,

			taobao:customer_taobao_search,
			email:customer_email_search,
			jd:customer_jd_search,

			type:customer_type_search,
			agent:customer_agent_search,
			detail:customer_detail_search,
			
			buyTime:phone_buy_time_search,
			imei:phone_imei_search,
			model:phone_model_search,
			version:phone_version_search,
			page:page,
			rows:rows
	});
	
	//window.location.href = 'export.do?method=exportCustomer&customer_phone_info_json='+encodeURIComponent(customer_phone_info_search_json);
	
	javascropt:exportApp.exportCustomerExcel(customer_phone_info_search_json);
};

//模板下载
function exportModel()
{
//	window.location.href = 'export.do?method=exportModel';
	javascropt:exportApp.exportModel(getRootPath()+'/model/model.xls');
};
	
//datagrid结束编辑
function endEditing(type){
	if(type=='phoneInfoEdit'){
		if (phoneInfoEditIndex == undefined){return true;}
		
		//检查当前编辑行的必输项是否全部填写完整
		if ($('#phone_info_edit_table').datagrid('validateRow', phoneInfoEditIndex)){
			var imei = $('#phone_info_edit_table').datagrid('getEditor', {index:phoneInfoEditIndex,field:'imei'});
			var imeiText = $(imei.target).combobox('getText');
			$('#phone_info_edit_table').datagrid('getRows')[phoneInfoEditIndex]['imei'] = imeiText;

			var model = $('#phone_info_edit_table').datagrid('getEditor', {index:phoneInfoEditIndex,field:'model'});
			var modelText = $(model.target).combobox('getText');
			$('#phone_info_edit_table').datagrid('getRows')[phoneInfoEditIndex]['model'] = modelText;
			
			var version= $('#phone_info_edit_table').datagrid('getEditor', {index:phoneInfoEditIndex,field:'version'});
			var versionText = $(version.target).combobox('getText');
			$('#phone_info_edit_table').datagrid('getRows')[phoneInfoEditIndex]['version'] = versionText;
			
			$('#phone_info_edit_table').datagrid('endEdit', phoneInfoEditIndex);
			phoneInfoEditIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
};

//选择手机信息
function selectPhoneInfo(index){
	if (phoneInfoEditIndex != index){
		if (endEditing('phoneInfoEdit')){
			
			//设定手机信息的编辑标记
			isPhoneInfoEdited = true;
			
    		//设置选中的行为编辑状态
			$(this).datagrid('selectRow', index).datagrid('beginEdit', index);

			phoneInfoEditIndex = index;
		} else {
    		//如果必输项目没有填写完整的话无法选择其它行
			$(this).datagrid('selectRow', phoneInfoEditIndex);
		}
	}
};

//扩展easyui表单的验证
$.extend($.fn.validatebox.defaults.rules, {
    //验证汉字
    CHS: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '只能输入汉字'
    },
    //移动手机号码验证
    mobile: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            return reg.test(value);
        },
        message: '输入手机号码格式不准确'
    },
  //验证数字
    number: {
        validator: function (value) {
            var reg = /^[0-9]*$/;
            return reg.test(value);
        },
        message: '只能输入数字'
    },
    //国内邮编验证
    zipcode: {
        validator: function (value) {
            var reg = /^[1-9]\d{5}$/;
            return reg.test(value);
        },
        message: '邮编必须是非0开始的6位数字'
    },
    //用户账号验证(只能包括 _ 数字 字母) 
    account: {//param的值为[]中值
        validator: function (value, param) {
            if (value.length < param[0] || value.length > param[1]) {
                $.fn.validatebox.defaults.rules.account.message = '用户名长度必须在' + param[0] + '至' + param[1] + '范围';
                return false;
            } else {
                if (!/^[A-Za-z0-9_@.]*$/g.test(value)) {
                    $.fn.validatebox.defaults.rules.account.message = '用户名只能数字、字母、下划线、@、.符号组成';
                    return false;
                } else {
                    return true;
                }
            }
        }, message: ''
    },
    //发送远程数据验证用户是否已经存在
    remote:{
        validator: function(value, url){
    	var flag;
    	$.ajax({
    		url: url,
    		data:{usernameToAdd:value},
    		type: "POST",
    		async : false,
    		dataType:'json',
    		success:function(data){
    			if(data.statu=="200"){
               	 flag = false;
               }
               else
               {
               	 flag = true;
               }
    		}
    		});
        return flag;
    },
        	message:'账号已存在'
    }
});