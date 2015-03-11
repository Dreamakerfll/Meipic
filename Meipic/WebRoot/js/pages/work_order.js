	
	//获取页面URL参数
	function GetQueryString(name) {
	
	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
	
	   var r = window.location.search.substr(1).match(reg);

	   if (r!=null) return decodeURI(r[2]); return null;
	
	};
	
	//历史记录datagrid格式化
	function rowformater(value,row,index)
	{
		var sContent = JSON.stringify(row.treatmentScheme);
		sContent=sContent.replace(" ","&nbsp;");
	    sContent=sContent.replace("\r\n","<br/>");
	    sContent=sContent.replace("\n","<br>");
		return "<a class='editcls' onclick='followWorkOrder(\""+ row.id +"\",\""+row.assemblyLine+"\","+sContent+",\""+row.finalDecision +"\")'href='javascript:void(0)'></a>";
	};
	
	//打开跟进工单
	function followWorkOrder(id,assemblyLine,treatmentScheme,finalDecision)
	{
		//编辑标记重置
		isFollowQuestionEdited = false;
		followQuestionEditIndex = undefined;
		var sc = treatmentScheme;
	    sc=sc.replace("<br/>","\r\n");
	    sc=sc.replace("<br>","\n");
	    sc=sc.replace("&nbsp;"," ");
	    
		//跟进问题datagrid加载
		$('#follow_question_table').datagrid({url:'work_order_question_link.do?method=findQuestionByWorkOrderId&workOrderId='+encodeURIComponent(id)});
		
		//跟进历史datagrid加载
		$('#follow_history_table').datagrid({url:'follow_history_info.do?method=findFollowHistory&workOrderId='+encodeURIComponent(id)});
		
		//给隐藏的工单ID赋值
		$('#work_order_id_follow').val(id);
		
		//给隐藏的工单流水赋值
		$('#work_order_assembly_line_follow').val(assemblyLine);
		
		//给跟进过程赋值
		$('#work_order_treatment_scheme_follow').val('');
		
		//给结案判定赋值
		$('#work_order_final_decision_follow').combobox('setValue',finalDecision);
		
		//打开跟进dialog
		$('#follow_work_order_dialog').dialog('open');
	};

	var type = GetQueryString("type");
	
	//页面预加载项目
	$(function(){
		
		if(type == '1')
		{
			$('#work_order_title_info').find('td').eq(0).attr('style','width:8%');
			$('#work_order_title_info').find('td').eq(2).hide();
			$('#work_order_title_info').find('td').eq(3).hide();
			$('#work_order_title_info').find('td').eq(4).hide();
			$('#work_order_title_info').find('td').eq(5).hide();
		}
		
		//设置弹出层默认隐藏并遮罩
        $('#select_customer_dialog').dialog({
            closed: true,   
            cache: false,
            modal: true
        }); 
        $('#follow_work_order_dialog').dialog({
        	closed: true,   
        	cache: false,
        	modal: true
        }); 
        
      //来电时间不可用
      $('#work_order_tel_time').datetimebox({ disabled: true});
        
      //性别加载
		$('#customer_sex').combobox({
			url:'json/sex.json',
			valueField: 'sex',
	        textField: 'sexDisplay',
	        editable:false ,
			panelHeight: 'auto',
			onSelect: function (rec) {
				customerInfoChange();
    		}
			});
		
		//客户类别加载
		$('#customer_type').combobox({
			url:'customer_type.do?method=findAll',
			valueField: 'customerType',
	        textField: 'customerTypeDisplay',
	        editable:false ,
			panelHeight: 200,
			onSelect: function (rec) {
				customerInfoChange();
    		}
			});
		
		//专属客服加载
		$('#customer_agent').combobox({
			url:'user_info.do?method=findAll',
			valueField: 'agent',
			textField: 'agentDisplay',
			editable:false ,
			panelHeight: 200,
			onSelect: function (rec) {
			customerInfoChange();
		}
		});
		
		//结案判定加载
		$('#work_order_final_decision_follow').combobox({
			url:'json/final_decision.json',
			valueField: 'decision',
	        textField: 'decisionDisplay',
	        editable:false ,
			panelHeight: 'auto'
		});
		
		//优先级加载
		$('#work_order_level').combobox({
			url:'json/level.json',
			valueField: 'level',
	        textField: 'levelDisplay',
	        editable:false ,
			panelHeight: 'auto'
		});
		$('#work_order_level').combobox('setValue','一般');
		
		
		//技能组加载
		$('#work_order_skill_group').combobox({
			url:'json/skill_group.json',
			valueField: 'skillGroup',
			textField: 'skillGroupDisplay',
			editable:false ,
			panelHeight: 'auto'
		});
		
		//反馈类型加载
		$('#work_order_feedback_type').combobox({
			url:'json/feedback_type.json',
			valueField: 'feedbackType',
			textField: 'feedbackTypeDisplay',
			editable:false ,
			panelHeight: 'auto'
		});
		$('#work_order_feedback_type').combobox('setValue','咨询');
		
		//反馈渠道加载
		$('#work_order_feedback_channel').combobox({
			url:'json/feedback_channel.json',
			valueField: 'feedbackChannel',
			textField: 'feedbackChannelDisplay',
			editable:false ,
			panelHeight: 'auto'
		});
		$('#work_order_feedback_channel').combobox('setValue','400电话');
		
		//结案判定加载
		$('#work_order_final_decision').combobox({
			url:'json/final_decision.json',
			valueField: 'decision',
	        textField: 'decisionDisplay',
	        editable:false ,
			panelHeight: 'auto'
		});
		$('#work_order_final_decision').combobox('setValue','已结案');
		
		//手机信息datagrid加载
        $('#phone_info_table').datagrid({
            pageSize : 5,//默认选择的分页是每页5行数据
            pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
            striped : true,//设置为true将交替显示行背景。
            singleSelect:true,//为true时只能选择单行
            remoteSort : false,
            pagination : true,//分页
            iconCls: 'icon-edit',
            collapsible:true,
            toolbar:toolOfPhone,
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
        $('#phone_info_table').datagrid('hideColumn','id');
        
        //工单历史datagrid加载
        $('#work_order_history_table').datagrid({
        	pageSize : 5,//默认选择的分页是每页5行数据
        	pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
        	striped : true,//设置为true将交替显示行背景。
        	singleSelect:true,//为true时只能选择单行
        	remoteSort : false,
        	pagination : true,//分页
        	iconCls: 'icon-edit',
        	collapsible:true,
        	onLoadSuccess:function(data){
            $('.editcls').linkbutton({text:'跟进',plain:true,iconCls:'icon-edit'});
        }
        });

		//客户详细信息datagrid加载
	    $('#customer_phone_info_old_table').datagrid({
	        pageSize : 5,//默认选择的分页是每页5行数据
	        pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
	        striped : true,//设置为true将交替显示行背景。
	        remoteSort : false,
	        pagination : true,//分页
	        onClickRow:function(rowIndex, rowData){
	        	var customerId = rowData.customerId;
	        	
	        	//取消所有行的选择
	        	$('#customer_phone_info_old_table').datagrid('clearSelections');
	        	
	        	//重新选择
	        	for ( var int = 0; int < $('#customer_phone_info_old_table').datagrid("getRows").length; int++) {
	    			if($('#customer_phone_info_old_table').datagrid('getData').rows[int]['customerId']==customerId)
	    				{
	    					$('#customer_phone_info_old_table').datagrid('selectRow',int);
	    				}
	    		}
	        },
	        onDblClickRow: function (rowIndex, rowData){
	    	
            	//页面上的编辑信息重置
	        	if($('#customer_tel1').val()=='')
	        	{
	        		isCustomerInfoEdited = false;
	        	}
	        	else
	        	{
	        		isCustomerInfoEdited = true;
	        	}
	    		isPhoneInfoEdited = false;
	    		phoneInfoEditIndex = undefined;

		    	//页面上客户的信息重新赋值
	    		$('#customer_id').val(rowData.customerId);
				$('#customer_name').val(rowData.name);
				$('#customer_sex').combobox('setValue',rowData.sex);
				$('#customer_age').val(rowData.age);
				
				$('#customer_area').val(rowData.area);
				
				//处理联系电话成combobox形式
				var tels = rowData.tel.split(',');
				var list = [];
				for ( var int = 0; int < tels.length; int++) 
				{
					var tel = {telNumber:tels[int]};
					list.push(tel); 
				}
				
				//联系电话2加载
				$('#customer_tel2').combobox({
					valueField: 'telNumber',
			        textField: 'telNumber',
			        data:list,
			        editable:false ,
					panelHeight: 'auto'
					});
				
				//联系电话存在时，默认显示第一个
				if(tels.length>0)
				{
					$('#customer_tel2').combobox('setValue',tels[0]);
				}
				
				$('#customer_qq').val(rowData.qq);
				$('#customer_address').val(rowData.address);
				$('#customer_postal_code').val(rowData.postalCode);
				
				$('#customer_wechat').val(rowData.wechat);
				$('#customer_weibo').val(rowData.weibo);
				$('#customer_nickname').val(rowData.nickname);
				
				$('#customer_taobao').val(rowData.taobao);
				$('#customer_email').val(rowData.email);
				$('#customer_jd').val(rowData.jd);
				
				$('#customer_type').combobox('setValue',rowData.type);
				$('#customer_agent').combobox('setValue',rowData.agent);
				$('#customer_detail').val(rowData.detail);
				
				$('#isCustomerExist').html('');
				$('#isCustomerExist').html('客户已存在');
				
				//重新加载手机信息table
				$('#phone_info_table').datagrid({url:'phone_info.do?method=findPhoneInfoByCustomerId&customerId='+encodeURIComponent(rowData.customerId)});
				
				//重新加载工单历史记录
				$('#work_order_history_table').datagrid({url:'customer_work_order_link.do?method=findWorkOrderByCustomerId&customerId='+encodeURIComponent(rowData.customerId)});
				
				//关闭弹出框
				$('#select_customer_dialog').dialog('close');
            },
            onLoadSuccess: function(data){
            	var colList = "customerId,name,sex,age,area,tel,qq,address,postalCode,wechat,weibo,nickname,taobao,email,jd,type,agent,detail";
            	 var ColArray = colList.split(",");
            	    var tTable = $('#customer_phone_info_old_table');
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
	    
	    //客户详细信息datagrid隐藏列
        $('#customer_phone_info_old_table').datagrid('hideColumn','customerId');
        
        //跟进问题信息datagrid加载
        $('#follow_question_table').datagrid({
        	pageSize : 5,//默认选择的分页是每页5行数据
        	pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
        	striped : true,//设置为true将交替显示行背景。
        	singleSelect:true,//为true时只能选择单行
        	remoteSort : false,
        	pagination : true,//分页
        	iconCls: 'icon-edit',
        	collapsible:true,
//        	toolbar:toolOfFollowQuestion,
        	onClickRow: selectFollowQuestion
        });
        
        //跟进问题信息datagrid隐藏列
        $('#follow_question_table').datagrid('hideColumn','id');
        
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
        
        //问题信息datagrid加载
        $('#question_table').datagrid({
        	pageSize : 5,//默认选择的分页是每页5行数据
        	pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
        	striped : true,//设置为true将交替显示行背景。
        	singleSelect:true,//为true时只能选择单行
        	remoteSort : false,
        	pagination : true,//分页
        	iconCls: 'icon-edit',
        	collapsible:true,
        	toolbar:toolOfQuestion,
        	onClickRow: selectQuestion
        });
        
        //问题信息datagrid隐藏列
        $('#question_table').datagrid('hideColumn','id');

		});
	
	//页面标记初始化
	var isCustomerInfoEdited = false;
	var isPhoneInfoEdited = false;
	var isFollowQuestionEdited = false;
	var isQuestionEdited = false;
	var phoneInfoEditIndex = undefined;
	var followQuestionEditIndex = undefined;
	var questionEditIndex = undefined;
	
	//客户信息被编辑事件
	function customerInfoChange()
	{
		isCustomerInfoEdited = true;
	};
	
	//清空客户信息按钮点击事件
	$('#clear_customer_info_btn').click(function(){
		
		//弹出确认框
		$.messager.confirm('请确认', '您确定要清空当前的客户信息吗?', function(b) {
            if(b) {
                
            	//页面上客户的信息重新赋值
        		$('#customer_id').val('');
        		$('#customer_name').val('');
        		$('#customer_sex').combobox('setValue','女');
        		$('#customer_age').val('');
        		
        		$('#customer_area').val('');
        		
        		//联系电话2清空
        		$('#customer_tel2').combobox({
        			valueField: 'telNumber',
        	        textField: 'telNumber',
        	        data:[],
        	        editable:false ,
        			panelHeight: 'auto'
        			});
        		$('#customer_tel2').combobox('clear');

        		$('#customer_qq').val('');
        		$('#customer_address').val('');
        		$('#customer_postal_code').val('');
        		
        		$('#customer_wechat').val('');
        		$('#customer_weibo').val('');
        		$('#customer_nickname').val('');
        		
        		$('#customer_taobao').val('');
        		$('#customer_email').val('');
        		$('#customer_jd').val('');
        		
        		$('#customer_type').combobox('setValue','1类');
        		$('#customer_agent').combobox('setValue','');
        		$('#customer_detail').val('');
        		
        		$('#isCustomerExist').html('');
        		$('#isCustomerExist').html('客户不存在');
        		
        		//清空手机信息
        		$('#phone_info_table').datagrid('loadData', { total: 0, rows: [] });
        		
        		//清空历史记录
        		$('#work_order_history_table').datagrid('loadData', { total: 0, rows: [] });
        		
            	//页面上的编辑信息重置
	    		isCustomerInfoEdited = false;
	    		isPhoneInfoEdited = false;
	    		phoneInfoEditIndex = undefined;
            }
        });
	});

	//绑定客户按钮点击事件
	$('#bind_customer_btn').click(function(){
		
		if($('#customer_id').val()!='')
		{
			//弹出确认框
			$.messager.confirm('请确认', '客户已存在，确认选择一个新客户吗？', function(b){
				if(b)
				{
					//打开绑定客户dialog
					openBindCustomerDialog();
				}
			});
		}
		else
		{
			//打开绑定客户dialog
			openBindCustomerDialog();
		}
	});
	
	//打开绑定客户dialog
	function openBindCustomerDialog()
	{
		//绑定客户性别加载
		$('#customer_sex_old').combobox({
			valueField: 'sex',
	        textField: 'sexDisplay',
	        url:'json/sex_search.json',
	        editable:false ,
			panelHeight: 'auto'
			});
		$('#customer_sex_old').combobox('setValue','');
		
		//绑定客户类别加载
		$('#customer_type_old').combobox({
			valueField: 'customerType',
	        textField: 'customerTypeDisplay',
	        url:'customer_type.do?method=findAll&type=0',
	        editable:false ,
			panelHeight: 200
			});
		$('#customer_type_old').combobox('setValue','');
		
		//绑定专属客服加载
		$('#customer_agent_old').combobox({
			valueField: 'agent',
			textField: 'agentDisplay',
			url:'user_info.do?method=findAll&type=0',
			editable:false ,
			panelHeight: 200
		});
		$('#customer_agent_old').combobox('setValue','');
		
		//手机型号加载
		$('#phone_model_old').combobox({
			url:'phone_model.do?method=findAll&type=0',
			valueField: 'phoneModel',
	        textField: 'phoneModelDisplay',
	        editable:false ,
			panelHeight: 200
			});
		$('#phone_model_old').combobox('setValue','');
		
		//手机版本加载
		$('#phone_version_old').combobox({
			url:'phone_version.do?method=findAll&type=0',
			valueField: 'phoneVersion',
			textField: 'phoneVersionDisplay',
			editable:false ,
			panelHeight: 200
		});
		$('#phone_version_old').combobox('setValue','');
		
		//打开绑定客户弹出层
		$('#select_customer_dialog').dialog('open');
	};

	//查找客户按钮点击事件
	$('#search_customer_phone_info_old_btn').click(function(){
		
		//页面上客户的信息
		var customer_name_old = $('#customer_name_old').val();
		var customer_sex_old = $('#customer_sex_old').combobox('getValue');
		var customer_age_old = $('#customer_age_old').val();
		
		var customer_area_old = $('#customer_area_old').val();
		var customer_tel_old = $('#customer_tel_old').val();
		var phone_buy_time_old = $('#phone_buy_time_old').datebox('getValue');
		
		var customer_qq_old = $('#customer_qq_old').val();
		var customer_address_old = $('#customer_address_old').val();
		var customer_postal_code_old = $('#customer_postal_code_old').val();
		
		var customer_wechat_old = $('#customer_wechat_old').val();
		var customer_weibo_old = $('#customer_weibo_old').val();
		var customer_nickname_old = $('#customer_nickname_old').val();
		
		var customer_taobao_old = $('#customer_taobao_old').val();
		var customer_email_old = $('#customer_email_old').val();
		var customer_jd_old = $('#customer_jd_old').val();
		
		var customer_type_old = $('#customer_type_old').combobox('getValue');
		var customer_agent_old = $('#customer_agent_old').combobox('getValue');
		var customer_detail_old = $('#customer_detail_old').val();
		
		var phone_imei_old = $('#phone_imei_old').val();
		var phone_model_old = $('#phone_model_old').combobox('getValue');
		var phone_version_old = $('#phone_version_old').combobox('getValue');
		var page = 1;
		var rows = $('#customer_phone_info_old_table').datagrid('options').pageSize; 
		
		var customer_phone_info_old_json=JSON.stringify({
				name:customer_name_old,
				sex:customer_sex_old,
				age:customer_age_old,

				area:customer_area_old,
				tel:customer_tel_old,

				qq:customer_qq_old,
				address:customer_address_old,
				postalCode:customer_postal_code_old,

				wechat:customer_wechat_old,
				weibo:customer_weibo_old,
				nickname:customer_nickname_old,

				taobao:customer_taobao_old,
				email:customer_email_old,
				jd:customer_jd_old,

				type:customer_type_old,
				agent:customer_agent_old,
				detail:customer_detail_old,
				
				buyTime:phone_buy_time_old,
				imei:phone_imei_old,
				model:phone_model_old,
				version:phone_version_old,
				page:page,
				rows:rows
		});
		
		//显示第一页数据   
        $('#customer_phone_info_old_table').datagrid("options").pageNumber = 1;
        
        //分页栏上跳转到第一页
        $('#customer_phone_info_old_table').datagrid('getPager').pagination({pageNumber: 1}); 
		
		$('#customer_phone_info_old_table').datagrid({url:'customer_phone_link.do?method=findCustomerPhoneInfoByCondition&customer_phone_info_json='+encodeURIComponent(customer_phone_info_old_json)});
		
		});

	//datagrid结束编辑
	function endEditing(type){
		if(type=='phoneInfo'){
			if (phoneInfoEditIndex == undefined){return true;}
			
			//检查当前编辑行的必输项是否全部填写完整
			if ($('#phone_info_table').datagrid('validateRow', phoneInfoEditIndex)){
				var imei = $('#phone_info_table').datagrid('getEditor', {index:phoneInfoEditIndex,field:'imei'});
				var imeiText = $(imei.target).combobox('getText');
				$('#phone_info_table').datagrid('getRows')[phoneInfoEditIndex]['imei'] = imeiText;

				var model = $('#phone_info_table').datagrid('getEditor', {index:phoneInfoEditIndex,field:'model'});
				var modelText = $(model.target).combobox('getText');
				$('#phone_info_table').datagrid('getRows')[phoneInfoEditIndex]['model'] = modelText;
				
				var version= $('#phone_info_table').datagrid('getEditor', {index:phoneInfoEditIndex,field:'version'});
				var versionText = $(version.target).combobox('getText');
				$('#phone_info_table').datagrid('getRows')[phoneInfoEditIndex]['version'] = versionText;
				
				$('#phone_info_table').datagrid('endEdit', phoneInfoEditIndex);
				phoneInfoEditIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		if(type=='followQuestion'){
			if (followQuestionEditIndex == undefined){return true;}
			//检查当前编辑行的必输项是否全部填写完整
			if ($('#follow_question_table').datagrid('validateRow', followQuestionEditIndex)){
				var mold = $('#follow_question_table').datagrid('getEditor', {index:followQuestionEditIndex,field:'mold'});
				var moldText = $(mold.target).combobox('getText');
				$('#follow_question_table').datagrid('getRows')[followQuestionEditIndex]['mold'] = moldText;

				var type = $('#follow_question_table').datagrid('getEditor', {index:followQuestionEditIndex,field:'type'});
				var typeText = $(type.target).combobox('getText');
				$('#follow_question_table').datagrid('getRows')[followQuestionEditIndex]['type'] = typeText;
				
				var describe = $('#follow_question_table').datagrid('getEditor', {index:followQuestionEditIndex,field:'describe'});
				var describeText = $(describe.target).val();
				$('#follow_question_table').datagrid('getRows')[followQuestionEditIndex]['describe'] = describeText;
				
				var status= $('#follow_question_table').datagrid('getEditor', {index:followQuestionEditIndex,field:'status'});
				var statusText = $(status.target).combobox('getText');
				$('#follow_question_table').datagrid('getRows')[followQuestionEditIndex]['status'] = statusText;
				
				$('#follow_question_table').datagrid('endEdit', followQuestionEditIndex);
				followQuestionEditIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		if(type=='question'){
			if (questionEditIndex == undefined){return true;}
			//检查当前编辑行的必输项是否全部填写完整
			if ($('#question_table').datagrid('validateRow', questionEditIndex)){
				var mold = $('#question_table').datagrid('getEditor', {index:questionEditIndex,field:'mold'});
				var moldText = $(mold.target).combobox('getText');
				$('#question_table').datagrid('getRows')[questionEditIndex]['mold'] = moldText;

				var type = $('#question_table').datagrid('getEditor', {index:questionEditIndex,field:'type'});
				var typeText = $(type.target).combobox('getText');
				$('#question_table').datagrid('getRows')[questionEditIndex]['type'] = typeText;
				
				var describe = $('#question_table').datagrid('getEditor', {index:questionEditIndex,field:'describe'});
				var describeText = $(describe.target).val();
				$('#question_table').datagrid('getRows')[questionEditIndex]['describe'] = describeText;
				
				var status= $('#question_table').datagrid('getEditor', {index:questionEditIndex,field:'status'});
				var statusText = $(status.target).combobox('getText');
				$('#question_table').datagrid('getRows')[questionEditIndex]['status'] = statusText;
				
				$('#question_table').datagrid('endEdit', questionEditIndex);
				questionEditIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
	};
	
    var toolOfPhone = [{
		text:'添加手机',
		iconCls:'icon-add',
		handler:function(){
			//判断信息是否已经填写完整
	    	if (endEditing('phoneInfo'))
	    	{
	    		//设定手机信息的编辑标记
    			isPhoneInfoEdited = true;
    			
		    	//追加行
				$('#phone_info_table').datagrid('appendRow',{});
				
				//标记追加行的行号
				phoneInfoEditIndex = $('#phone_info_table').datagrid('getRows').length-1;
				
				//设置当前追加的行为编辑状态
				$('#phone_info_table').datagrid('selectRow', phoneInfoEditIndex).datagrid('beginEdit', phoneInfoEditIndex);
	    	}
		}
	},'-', {
        text: '删除手机',
        iconCls: 'icon-remove',
        handler:function () {
            var rows = $("#phone_info_table").datagrid('getSelections');
            if (rows.length > 0) {
                $.messager.confirm('请确认', '您确定要删除当前所有选择的项目吗?', function(b) {
                    if(b) {
                        
                    	//设定手机信息的编辑标记
            			isPhoneInfoEdited = true;
            			
                        $('#phone_info_table').datagrid('cancelEdit', phoneInfoEditIndex).datagrid('deleteRow', phoneInfoEditIndex);
                		phoneInfoEditIndex = undefined;
                    }
                });
            } else {
                $.messager.alert('提示', '请选择要删除的记录', 'error');
            }
        }
    }];
    
    var toolOfFollowQuestion = [{
		text:'添加问题',
		iconCls:'icon-add',
		handler:function(){
    	
			//判断信息是否已经填写完整
	    	if (endEditing('followQuestion'))
	    	{
	    		//设定跟进问题的编辑标记
    			isFollowQuestionEdited = true;
    			
		    	//追加行
				$('#follow_question_table').datagrid('appendRow',{});
				
				//标记追加行的行号
				followQuestionEditIndex = $('#follow_question_table').datagrid('getRows').length-1;
				
				//设置当前追加的行为编辑状态
				$('#follow_question_table').datagrid('selectRow', followQuestionEditIndex).datagrid('beginEdit', followQuestionEditIndex);
	    	}
		}
	},'-', {
        text: '删除问题',
        iconCls: 'icon-remove',
        handler:function () {
            var rows = $("#follow_question_table").datagrid('getSelections');
            if (rows.length > 0) {
                $.messager.confirm('请确认', '您确定要删除当前所有选择的项目吗?', function(b) {
                    if(b) {
                    	
                    	//设定跟进问题的编辑标记
            			isFollowQuestionEdited = true;
            			
                        $('#follow_question_table').datagrid('cancelEdit', followQuestionEditIndex).datagrid('deleteRow', followQuestionEditIndex);
                        followQuestionEditIndex = undefined;
                    }
                });
            } else {
                $.messager.alert('提示', '请选择要删除的记录', 'error');
            }
        }
    }];
    
    function addQuestion()
    {
    	//判断信息是否已经填写完整
    	if (endEditing('question'))
    	{
    		//设定问题的编辑标记
			isQuestionEdited = true;
			
	    	//追加行
			$('#question_table').datagrid('appendRow',{});
			
			//标记追加行的行号
			questionEditIndex = $('#question_table').datagrid('getRows').length-1;
			
			//设置当前追加的行为编辑状态
			$('#question_table').datagrid('selectRow', questionEditIndex).datagrid('beginEdit', questionEditIndex);
			
//            var target1 = $('#question_table').datagrid('getEditor', { 'index': questionEditIndex, 'field': 'mold' }).target;
//            target1.combobox({
//            	url:'question_type.do?method=findAllQuestionMold',
//				panelHeight:200,
//				valueField:'mold',
//				textField:'moldDisplay',
//				required:true,
//				editable:true,
//				missingMessage:'不能为空'
//            });
//            var firstValue1 = target1.combobox('getData')[0];
//			target1.combobox('setValue',firstValue1);
			
			//设置IMEI的初始值
			var target2 = $('#question_table').datagrid('getEditor', { 'index': questionEditIndex, 'field': 'phoneImei' }).target;
            target2.combobox('reload','phone_info.do?method=findPhoneInfoByCustomerIdNoPage&customerId='+$('#customer_id').val());
    	}
    };
    
    var toolOfQuestion = [{
		text:'添加问题',
		iconCls:'icon-add',
		handler:function(){
			
			//保存客户信息
	    	if(isCustomerInfoEdited || isPhoneInfoEdited || $('#customer_id').val() == '')
	    	{
	    		saveCustomer('saveToQuestion',addQuestion);
	    		return;
	    	}
	    	
	    	addQuestion();
		}
	},'-', {
        text: '删除问题',
        iconCls: 'icon-remove',
        handler:function () {
            var rows = $("#question_table").datagrid('getSelections');
            if (rows.length > 0) {
                $.messager.confirm('请确认', '您确定要删除当前所有选择的项目吗?', function(b) {
                    if(b) {
                    	
                    	//设定问题的编辑标记
            			isQuestionEdited = true;
            			
                        $('#question_table').datagrid('cancelEdit', questionEditIndex).datagrid('deleteRow', questionEditIndex);
                        questionEditIndex = undefined;
                    }
                });
            } else {
                $.messager.alert('提示', '请选择要删除的记录', 'error');
            }
        }
    }];

    //选择手机信息
    function selectPhoneInfo(index){
    	if (phoneInfoEditIndex != index){
    		if (endEditing('phoneInfo')){
    			
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
    
    //选择跟进问题详情
    function selectFollowQuestion(index){
    	if (followQuestionEditIndex != index){
    		if (endEditing('followQuestion')){
    			
    			//设定跟进问题的编辑标记
    			isFollowQuestionEdited = true;
    			
    			//设置选中的行为编辑状态
    			$(this).datagrid('selectRow', index).datagrid('beginEdit', index);
    			
    			//设置问题类别的初始值
    			var target1 = $('#follow_question_table').datagrid('getEditor', { 'index': index, 'field': 'type' }).target;
    			var target2 = $('#follow_question_table').datagrid('getEditor', { 'index': index, 'field': 'mold' }).target;
                var mold = target2.combobox('getValue');
                target1.combobox('reload','question_type.do?method=findQuestionTypeByQuestionMold&questionMold='+mold);
    			
    			followQuestionEditIndex = index;
    		} else {
    			//如果必输项目没有填写完整的话无法选择其它行
    			$(this).datagrid('selectRow', followQuestionEditIndex);
    		}
    	}
    };
    
    //选择问题详情
    function selectQuestion(index){
    	if (questionEditIndex != index){
    		if (endEditing('question')){
    			
    			//设定跟进问题的编辑标记
    			isQuestionEdited = true;
    			
    			//设置选中的行为编辑状态
    			$(this).datagrid('selectRow', index).datagrid('beginEdit', index);
    			
    			//设置问题类别的初始值
    			var target1 = $('#question_table').datagrid('getEditor', { 'index': index, 'field': 'type' }).target;
    			var target2 = $('#question_table').datagrid('getEditor', { 'index': index, 'field': 'mold' }).target;
                var mold = target2.combobox('getValue');
                target1.combobox('reload','question_type.do?method=findQuestionTypeByQuestionMold&questionMold='+mold);
    			
                //设置IMEI的初始值
				var target1 = $('#question_table').datagrid('getEditor', { 'index': index, 'field': 'phoneImei' }).target;
                target1.combobox('reload','phone_info.do?method=findPhoneInfoByCustomerIdNoPage&customerId='+$('#customer_id').val());
                
                questionEditIndex = index;
    		} else {
    			//如果必输项目没有填写完整的话无法选择其它行
    			$(this).datagrid('selectRow', questionEditIndex);
    		}
    	}
    };
    
    function blank(){};

    //更新客户信息
    $('#update_cusomer_btn').click(function(){
    	
    	if(isCustomerInfoEdited || isPhoneInfoEdited || $('#customer_id').val() == '')
    	{
    		saveCustomer('saveToCustomer',blank);
    	}
    	else
    	{
    		$.messager.alert('系统消息','客户信息无变更！','info');
    	}
        });
    
    //保存跟进工单
    function saveFollowWorkOrder()
    {
		var account = GetQueryString('account');
		var agent = GetQueryString('agent');
		
    	//判断跟进问题信息是否填写完整
    	if (!$('#follow_question_table').datagrid('validateRow', followQuestionEditIndex))
    	{
    		$.messager.alert('系统消息','请填写完整问题信息！','warning');
    		return;
    	}
    	
    	//结束跟进问题信息的编辑
    	$('#follow_question_table').datagrid('endEdit', followQuestionEditIndex);
    	followQuestionEditIndex = undefined;
    	//取消跟进问题信息的选择
    	$('#follow_question_table').datagrid('uncheckAll');
    	
    	//收集跟进工单信息
    	var work_order_id_follow = $('#work_order_id_follow').val();
    	var work_order_assembly_line_follow = $('#work_order_assembly_line_follow').val();
    	var work_order_treatment_scheme_follow = $('#work_order_treatment_scheme_follow').val();
		var work_order_final_decision_follow = $('#work_order_final_decision_follow').combobox('getValue');
		var work_order_unique_id_follow = GetQueryString('uniqueId');
		
		if(work_order_treatment_scheme_follow =='')
		{
			$.messager.alert('系统消息','请填写跟进过程！','warning');
    		return;
		}
		
		var work_order_info_json=JSON.stringify({
			id:work_order_id_follow,
			userAgent:agent,
			userAccount:account,
			assemblyLine:work_order_assembly_line_follow,
			treatmentScheme:work_order_treatment_scheme_follow,
			finalDecision:work_order_final_decision_follow,
			uniqueId:work_order_unique_id_follow
		});
		
		//跟进历史信息收集
		var d = new Date();
		var submit_time = d.getFullYear() + "-" +((d.getMonth()+1)<10?"0":"")+(d.getMonth()+1) + "-" + (d.getDate()<10?"0":"")+d.getDate() + " " + (d.getHours()<10?"0":"")+d.getHours() + ":" + (d.getMinutes()<10?"0":"")+d.getMinutes() + ":" + (d.getSeconds()<10?"0":"")+d.getSeconds();
		var follow_history_info_json = JSON.stringify({
			workOrderId:work_order_id_follow,
			account:account,
			agent:agent,
			treatmentScheme:work_order_treatment_scheme_follow,
			time:submit_time
		});
		
		var question_info_follow_insert_json = JSON.stringify($('#follow_question_table').datagrid('getChanges','inserted'));
		var question_info_follow_update_json = JSON.stringify($('#follow_question_table').datagrid('getChanges','updated'));
		var question_info_follow_delete_json = JSON.stringify($('#follow_question_table').datagrid('getChanges','deleted'));
		
		//保存跟进工单
		$.ajax( {
			cache : true,
			type : "POST",
			url : 'work_order.do?method=updateWorkOrderQuestionInfo&customerId='+encodeURIComponent($('#customer_id').val())+'&workOrderInfo=' + encodeURIComponent(work_order_info_json) + '&question_info_insert_json=' + encodeURIComponent(question_info_follow_insert_json) +'&question_info_update_json='+encodeURIComponent(question_info_follow_update_json)+'&question_info_delete_json='+encodeURIComponent(question_info_follow_delete_json)+'&follow_history_info_json='+encodeURIComponent(follow_history_info_json),
			async : false,
			dataType : "html",
			success : function(data) {
				
				var json = $.parseJSON(data);
				if (json.status == "200") {
					
					$('#follow_work_order_dialog').dialog('close');
					
					$('#work_order_history_table').datagrid('reload');
					
					$.messager.alert('系统消息', '保存工单信息成功！', 'info');
				}else{
					$.messager.alert('系统消息', '保存工单信息失败！','error');
				}
			}
		});
    };
    
    //提交按钮点击事件
    $('#work_order_submit_btn').click(function(){
    	
		//保存客户信息
    	if(isCustomerInfoEdited || isPhoneInfoEdited || $('#customer_id').val() == '')
    	{
    		saveCustomer('saveToSubmit',submitWorkOrder);
    		
    		return;
    	}
    	submitWorkOrder();
    });
    
    function submitWorkOrder()
    {
    	//判断页面值的合法性
    	if(!$('#work_order_level').combobox('isValid'))
    	{
    		$.messager.alert('系统消息','请填写优先级！','info');
    		return;
    	}
    	if(!$('#work_order_feedback_time').datetimebox('isValid'))
    	{
    		$.messager.alert('系统消息','请填写反馈时间！','info');
    		return;
    	}
    	if(!$('#work_order_skill_group').combobox('isValid'))
    	{
    		$.messager.alert('系统消息','请填写技能组！','info');
    		return;
    	}
    	if(!$('#work_order_feedback_type').combobox('isValid'))
    	{
    		$.messager.alert('系统消息','请填写反馈类型！','info');
    		return;
    	}
    	if(!$('#work_order_feedback_channel').combobox('isValid'))
    	{
    		$.messager.alert('系统消息','请填写反馈渠道！','info');
    		return;
    	}
    	if(!$('#work_order_final_decision').combobox('isValid'))
    	{
    		$.messager.alert('系统消息','请填写结案判定！','info');
    		return;
    	}
    	
    	//判断问题信息是否有填写
    	if($('#question_table').datagrid('getRows').length==0)
		{
    		$.messager.alert('系统消息','请填写问题信息！','warning');
    		return;
		}
    	
    	//判断问题信息是否填写完整
    	if (!$('#question_table').datagrid('validateRow', questionEditIndex))
    	{
    		$.messager.alert('系统消息','请填写完整问题信息！','warning');
    		return;
    	}
    	
    	//结束问题信息的编辑
    	$('#question_table').datagrid('endEdit', questionEditIndex);
    	questionEditIndex = undefined;
    	
    	//取消问题信息的选择
    	$('#question_table').datagrid('uncheckAll');
    	
    	//收集工单信息
    	if(type == 1)
    	{
			$('#work_order_tel').val('');
			$('#work_order_tel_time').datetimebox('setValue', '');;
    	}
    	var work_order_id = '';
    	var work_order_assembly_line = $('#work_order_assembly_line').val();
    	var work_order_tel = $('#work_order_tel').val();
    	var work_order_tel_time = $('#work_order_tel_time').datetimebox('getValue');
    	var work_order_title = $('#work_order_title').val();
    	var work_order_level = $('#work_order_level').combobox('getValue');
    	var work_order_feedback_time = $('#work_order_feedback_time').datetimebox('getValue');
    	var work_order_feedback_type = $('#work_order_feedback_type').combobox('getValue');
    	var work_order_feedback_channel = $('#work_order_feedback_channel').combobox('getValue');
    	var work_order_treatment_scheme = $('#work_order_treatment_scheme').val();
		var work_order_final_decision = $('#work_order_final_decision').combobox('getValue');
		var work_order_user_agent = GetQueryString("agent");
		var work_order_user_account = GetQueryString("account");
		var work_order_unique_id = GetQueryString("uniqueId");
		var work_order_skill_group = $('#work_order_skill_group').combobox('getValue');
		
		if(work_order_treatment_scheme =='')
		{
			$.messager.alert('系统消息','请填写处理过程！','info');
    		return;
		}
		
		var work_order_info_json=JSON.stringify({
			id:work_order_id,
			assemblyLine:work_order_assembly_line,
			tel:work_order_tel,
			telTime:work_order_tel_time,
			title:work_order_title,
			level:work_order_level,
			feedbackTime:work_order_feedback_time,
			feedbackType:work_order_feedback_type,
			feedbackChannel:work_order_feedback_channel,
			treatmentScheme:work_order_treatment_scheme,
			finalDecision:work_order_final_decision,
			userAgent:work_order_user_agent,
			userAccount:work_order_user_account,
			skillGroup:work_order_skill_group,
			uniqueId:work_order_unique_id
			
			
		});
		
		//跟进历史信息收集
		var d = new Date();
		var submit_time = d.getFullYear() + "-" +((d.getMonth()+1)<10?"0":"")+(d.getMonth()+1) + "-" + (d.getDate()<10?"0":"")+d.getDate() + " " + (d.getHours()<10?"0":"")+d.getHours() + ":" + (d.getMinutes()<10?"0":"")+d.getMinutes() + ":" + (d.getSeconds()<10?"0":"")+d.getSeconds();
		var follow_history_info_json = JSON.stringify({
			workOrderId:'',
			account:work_order_user_account,
			agent:work_order_user_agent,
			treatmentScheme:work_order_treatment_scheme,
			time:submit_time
		});
		
		var question_info_insert_json = JSON.stringify($('#question_table').datagrid('getChanges','inserted'));
		var question_info_update_json = JSON.stringify($('#question_table').datagrid('getChanges','updated'));
		var question_info_delete_json = JSON.stringify($('#question_table').datagrid('getChanges','deleted'));
		
		//提交工单
		$.ajax( {
			cache : true,
			type : "POST",
			url : 'work_order.do?method=updateWorkOrderQuestionInfo&customerId='+encodeURIComponent($('#customer_id').val())+'&workOrderInfo=' + encodeURIComponent(work_order_info_json) + '&question_info_insert_json=' + encodeURIComponent(question_info_insert_json) +'&question_info_update_json='+encodeURIComponent(question_info_update_json)+'&question_info_delete_json='+encodeURIComponent(question_info_delete_json)+'&follow_history_info_json='+encodeURIComponent(follow_history_info_json),
			async : false,
			dataType : "html",
			success : function(data) {
				
				var json = $.parseJSON(data);
				if (json.status == "200") {
					
					$('#work_order_history_table').datagrid('reload');
					
					//新增工单
					if(GetQueryString('type')=='1')
					{
						$.messager.alert('系统消息', '提交工单信息成功！', 'info',function(){
							javascript:app.closeWebViewFrameFX('3');
						});
					}
					//电话弹单
					if(GetQueryString('type')=='0')
					{
						$.messager.alert('系统消息', '提交工单信息成功！', 'info',function(){
							javascript:app.closeWebViewFrameFX('4');
						});
					}
				}else{
					$.messager.alert('系统消息', '提交工单信息失败！','error');
				}
			}
		});
    };
    
    function saveCustomer(type,callback)
    {
    	
    	//验证页面项目合法性
    	if(!$('#customer_age').validatebox('isValid'))
    	{
    		$.messager.alert('系统消息','请输入正确的年龄！','info');
    		return false;
    	}
    	if(!$('#customer_tel1').validatebox('isValid'))
    	{
    		$.messager.alert('系统消息','请输入正确的联系电话！','info');
    		return false;
    	}
    	if(!$('#customer_qq').validatebox('isValid'))
    	{
    		$.messager.alert('系统消息','请输入正确的QQ号码！','info');
    		return false;
    	}
    	if(!$('#customer_postal_code').validatebox('isValid'))
    	{
    		$.messager.alert('系统消息','请输入正确的邮政编码！','info');
    		return false;
    	}
    	if(!$('#customer_email').validatebox('isValid'))
    	{
    		$.messager.alert('系统消息','请输入正确的邮箱！','info');
    		return false;
    	}
    	
    	
    	//判断页面必填项目
    	if($('#customer_tel1').val() == ''
    		&&$('#customer_tel2').combobox('getData').length == 0
    		&&$('#customer_qq').val() == ''
    		&&$('#customer_wechat').val() == ''
    		&&$('#customer_weibo').val() == ''
    		&&$('#customer_taobao').val() == ''
    		&&$('#customer_email').val() == ''
    		&&$('#customer_jd').val() == '')
    	{
    		$.messager.alert('系统消息','联系电话、QQ号码、微信号、微博地址、淘宝账号、电子邮件、京东账号不能同时为空！','warning');
    		return false;
    	}
    	
    	//判断手机信息是否填写完整
    	if (!$('#phone_info_table').datagrid('validateRow', phoneInfoEditIndex))
    	{
    		$.messager.alert('系统消息','请填写完整手机信息！','warning');
    		return false;
    	}
    	
    	//结束手机信息的编辑
    	$('#phone_info_table').datagrid('endEdit', phoneInfoEditIndex);
    	phoneInfoEditIndex = undefined;
    	
    	//取消手机信息的选择
    	$('#phone_info_table').datagrid('uncheckAll');
    	
    	//收集页面客户信息
    	var customer_id = $('#customer_id').val();
    	var customer_name = $('#customer_name').val();
		var customer_sex = $('#customer_sex').combobox('getValue');
		var customer_age = $('#customer_age').val();
		var customer_area = $('#customer_area').val();
		var customer_tel1 = $('#customer_tel1').val();
		var customer_tel2 = JSON.stringify($('#customer_tel2').combobox('getData'));
		var customer_qq = $('#customer_qq').val();
		var customer_address = $('#customer_address').val();
		var customer_postal_code = $('#customer_postal_code').val();
		var customer_wechat = $('#customer_wechat').val();
		var customer_weibo = $('#customer_weibo').val();
		var customer_nickname = $('#customer_nickname').val();
		var customer_taobao = $('#customer_taobao').val();
		var customer_email = $('#customer_email').val();
		var customer_jd = $('#customer_jd').val();
		var customer_type = $('#customer_type').combobox('getValue');
		var customer_agent = $('#customer_agent').combobox('getValue');
		var customer_detail = $('#customer_detail').val();
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
		
		var phone_info_insert_json = JSON.stringify($('#phone_info_table').datagrid('getChanges','inserted'));
		var phone_info_update_json = JSON.stringify($('#phone_info_table').datagrid('getChanges','updated'));
		var phone_info_delete_json = JSON.stringify($('#phone_info_table').datagrid('getChanges','deleted'));
		
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
					
					//清除编辑的标记
					isCustomerInfoEdited = false;
					isPhoneInfoEdited = false;
					
					//如果联系电话1不为空的情况
					if($('#customer_tel1').val()!= '')
					{
    					//获取联系电话2先前的数据
    					var data = $('#customer_tel2').combobox('getData');
    					
    					//追加联系电话1
    					var tel = {telNumber:$('#customer_tel1').val()};
    					data.push(tel); 
    					
    					//联系电话2重新加载
    					$('#customer_tel2').combobox({
    						valueField: 'telNumber',
    				        textField: 'telNumber',
    				        data:data,
    				        editable:false ,
    						panelHeight: 'auto'
    						});
    					$('#customer_tel2').combobox('setValue',$('#customer_tel1').val());
    					
    					//清空联系电话1
    					$('#customer_tel1').val('');
					}
					
					$('#customer_id').val(json.sign);
					$('#isCustomerExist').html('');
	        		$('#isCustomerExist').html('客户已存在');
	        		
	        		//显示第一页数据   
	                $('#phone_info_table').datagrid("options").pageNumber = 1;
	                
	                //分页栏上跳转到第一页
	                $('#phone_info_table').datagrid('getPager').pagination({pageNumber: 1}); 
	                
	        		//重新加载手机信息table
					$('#phone_info_table').datagrid({url:'phone_info.do?method=findPhoneInfoByCustomerId&customerId='+encodeURIComponent($('#customer_id').val())});
					
					if(type =='saveToCustomer')
					{
						$.messager.alert('系统消息', '保存客户信息成功！', 'info');
					}
					callback();
					
				}else{
					$.messager.alert('系统消息', '保存客户信息失败！','error');
					
					return false;
				}
				
			}
		});
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