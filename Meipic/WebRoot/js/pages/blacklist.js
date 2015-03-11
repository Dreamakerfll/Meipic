
	//获取页面URL参数
	function GetQueryString(name) {
	
	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
	
	   var r = window.location.search.substr(1).match(reg);
	
	   if (r!=null) return decodeURI(r[2]); return null;
	
	};
    var toolbar = [{
		text:'添加黑名单',
		iconCls:'icon-add',
		handler:function(){

    	openAddBlacklistDialog();
    	
		}
	}];
    
    
    function openAddBlacklistDialog(){
    	
    	$('#begin_time_add').datetimebox('setValue','');
		$('#end_time_add').datetimebox('setValue','');
		$('#phone_number_add').val('').validatebox('validate');
		$('#comment_add').val('');
    	$('#add_blacklist').dialog('open');
    };
	function rowformater(value,row,index)
		{
			var isMonitor = GetQueryString("isMonitor");
			if(isMonitor == '1')
			{
				return "<a class='editcls2' onclick='checkRow("+ row.id+","+row.status+",\""+row.monitorComment +"\")' href='javascript:void(0)'></a>"+
				"<a class='editcls3' onclick='editRow("+ row.id +",\""+row.beginTime+"\",\""+row.endTime+"\",\""+row.phoneNumber+"\",\""+row.agentComment+"\",\""+row.submitPerson+"\",\""+row.status +"\")'href='javascript:void(0)'></a>"+
				"<a class='editcls' onclick='delRow("+ row.id +",\""+row.submitPerson+"\",\""+row.status +"\")'href='javascript:void(0)'></a>";
			}
			else
			{
				return "<a class='editcls3' onclick='editRow("+ row.id +",\""+row.beginTime+"\",\""+row.endTime+"\",\""+row.phoneNumber+"\",\""+row.agentComment+"\",\""+row.submitPerson+"\",\""+row.status +"\")'href='javascript:void(0)'></a>"+
				"<a class='editcls' onclick='delRow("+ row.id +",\""+row.submitPerson+"\",\""+row.status +"\")' href='javascript:void(0)'></a>";
			}
		};
    $(function() {
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
        	$('.editcls3').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'}); 
            $('.editcls').linkbutton({text:'删除',plain:true,iconCls:'icon-cancel'});
        }  
        });
        
      //设置黑名单的审核状态
        $('#check_status').combobox({
        	url:'json/check_status.json',
			valueField: 'statusvalue',
            textField: 'statusname',
            editable:false ,
			panelHeight: 'auto'
            });
        //设置黑名单的审核状态（条件）
        $('#check_status_for_search').combobox({
        	url:'json/check_status_search.json',
        	valueField: 'statusvalue',
        	textField: 'statusname',
        	editable:false ,
        	panelHeight: 'auto'
        });
        $('#check_status_for_search').combobox('setValue','');
        
    });

    $('#search_blacklist_btn').click(function(){
    		if(!$('#phone_number').validatebox('isValid'))
    			{
    			return false;
    			}
			var begin_time = $('#begin_time').datetimebox('getValue');
			var end_time = $('#end_time').datetimebox('getValue');
			var phone_number = $('#phone_number').val();
			var submit_person = $('#submit_person').val().trim();
			var agent_comment = $('#agent_comment').val().trim();
			var status = $('#check_status_for_search').combobox('getValue');

			var varify;//用于查询验证,验证开始时间是否小于结束时间 
			varify=begin_time>end_time; 

			if(begin_time!=''&&end_time!='')
			{
				if(varify){
					$.messager.alert('系统消息','结束时间要大于开始时间','warning');
					return ;
					}
			}

			var urlPath = "blacklist.do?method=findBlacklistByCondition";

			urlPath = urlPath + "&beginTime="+encodeURIComponent(begin_time);
			urlPath = urlPath + "&endTime="+encodeURIComponent(end_time);
			urlPath = urlPath + "&phoneNumber="+encodeURIComponent(phone_number);
			urlPath = urlPath + "&submitPerson="+encodeURIComponent(submit_person);
			urlPath = urlPath + "&agentComment="+encodeURIComponent(agent_comment);
			urlPath = urlPath + "&status="+encodeURIComponent(status);

			//显示第一页数据   
            $('#blacklist').datagrid("options").pageNumber = 1;
            
            //分页栏上跳转到第一页
            $('#blacklist').datagrid('getPager').pagination({pageNumber: 1}); 
            
			$('#blacklist').datagrid({singleSelect:true,url: urlPath});
        });
    
    function addNewBlacklist(){
    	
    	var begin_time = $('#begin_time_add').datetimebox('getValue');
		var end_time = $('#end_time_add').datetimebox('getValue');
		var phone_number = $('#phone_number_add').val();
		var submit_person = GetQueryString("account");
		var agent_comment = $('#comment_add').val().trim();
		
		var d = new Date();
		
		var submit_time = d.getFullYear() + "-" +((d.getMonth()+1)<10?"0":"")+(d.getMonth()+1) + "-" + (d.getDate()<10?"0":"")+d.getDate() + " " + (d.getHours()<10?"0":"")+d.getHours() + ":" + (d.getMinutes()<10?"0":"")+d.getMinutes() + ":" + (d.getSeconds()<10?"0":"")+d.getSeconds();

		var varify;//用于查询验证,验证开始时间是否小于结束时间 
		varify=begin_time>end_time; 
		
		if(begin_time==''){
			$.messager.alert('系统消息','请输入开始时间','info');
			return ;
			}
		if(end_time==''){
			$.messager.alert('系统消息','请输入结束时间','info');
			return ;
			}

		if(varify){
			$.messager.alert('警告','结束时间要大于开始时间','warning');
			return ;
			} 
		if(!$('#phone_number_add').validatebox('isValid')){
			$.messager.alert('系统消息','请输入正确的电话号码','info');
			return ;
			}
		if(agent_comment==''){
			$.messager.alert('系统消息','请输入备注','info');
			return ;
			}

		$.ajax( {
			cache : true,
			type : "POST",
			url : 'blacklist.do?method=addBlacklist&beginTime=' + begin_time +'&phoneNumber=' + encodeURIComponent(phone_number) + '&endTime=' + end_time +'&submitPerson='+encodeURIComponent(submit_person)+'&agentComment='+encodeURIComponent(agent_comment)+'&submitTime='+submit_time,
			async : false,
			dataType : "json",
			success : function(data) {
				
				if (data.status == "200") {
					$.messager.alert('系统消息', '添加成功！', 'info');
					$('#add_blacklist').dialog('close');
					//显示第一页数据   
			        $('#blacklist').datagrid("options").pageNumber = 1;
			        
			        //分页栏上跳转到第一页
			        $('#blacklist').datagrid('getPager').pagination({pageNumber: 1}); 
			        
					$('#blacklist').datagrid('reload');
				}else{
					$.messager.alert('系统消息','添加失败！','error');
				}
			}
		});
    	
    };


    function editRow(id,beginTime,endTime,phoneNumber,agentComment,submitPerson,status)
    {
    	var account = GetQueryString("account");
    	var isMonitor = GetQueryString("isMonitor");
    	if(isMonitor==1)
    	{
    		$('#edit_id').val(id);
    		$('#begin_time_edit').datetimebox('setValue',beginTime);
    		$('#end_time_edit').datetimebox('setValue',endTime);
    		$('#phone_number_edit').val(phoneNumber).validatebox('validate');
    		$('#comment_edit').val(agentComment);
    		$('#edit_blacklist').dialog('open');
    	}
    	else
    	{
    		if(submitPerson==account)
    		{
    			if(status == '0')
    			{
    				$('#edit_id').val(id);
    	    		$('#begin_time_edit').datetimebox('setValue',beginTime);
    	    		$('#end_time_edit').datetimebox('setValue',endTime);
    	    		$('#phone_number_edit').val(phoneNumber).validatebox('validate');
    	    		$('#comment_edit').val(agentComment);
    	    		$('#edit_blacklist').dialog('open');
    			}
    			else
    			{
    				$.messager.alert('系统消息', '此黑名单已被审核，无法编辑！', 'warning');
    			}
    		}
    		else
    		{
    			$.messager.alert('系统消息', '只能编辑自己提交的黑名单！', 'warning');
    		}
    	}
    };
    
    function editBlacklist()
    {
    	var begin_time = $('#begin_time_edit').datetimebox('getValue');
		var end_time = $('#end_time_edit').datetimebox('getValue');
		var phone_number = $('#phone_number_edit').val();
		var agent_comment = $('#comment_edit').val().trim();
		var id = $('#edit_id').val();
		
		var varify;//用于查询验证,验证开始时间是否小于结束时间 
		varify=begin_time>end_time; 
		
		if(begin_time==''){
			$.messager.alert('系统消息','请输入开始时间','info');
			return ;
			}
		if(end_time==''){
			$.messager.alert('系统消息','请输入结束时间','info');
			return ;
			}

		if(varify){
			$.messager.alert('警告','结束时间要大于开始时间','warning');
			return ;
			} 
		if(!$('#phone_number_edit').validatebox('isValid')){
			$.messager.alert('系统消息','请输入正确的电话号码','info');
			return ;
			}
		if(agent_comment==''){
			$.messager.alert('系统消息','请输入备注','info');
			return ;
			}

		$.ajax( {
			cache : true,
			type : "POST",
			url : 'blacklist.do?method=editBlacklist&beginTime=' + begin_time +'&phoneNumber=' + encodeURIComponent(phone_number) + '&endTime=' + end_time +'&agentComment='+encodeURIComponent(agent_comment)+'&id='+encodeURIComponent(id),
			async : false,
			dataType : "json",
			success : function(data) {
				
				if (data.status == "200") {
					$.messager.alert('系统消息', '编辑成功！', 'info');
					$('#edit_blacklist').dialog('close');
			        
					$('#blacklist').datagrid('reload');
				}else{
					$.messager.alert('系统消息','编辑失败！','error');
				}
			}
		});
    }
    
    function delRow(id,submitPerson,status)
    {
	    	var account = GetQueryString("account");
	    	var isMonitor = GetQueryString("isMonitor");
	    	if(isMonitor==1)
	    	{
	    		$('#delete_id').val(id);
	    		$('#confirm_delete').dialog('open');
	    	}
	    	else
	    	{
	    		if(submitPerson==account)
		    	{
	    			if(status == '0')
	    			{
	    				$('#delete_id').val(id);
	    				$('#confirm_delete').dialog('open');
	    			}
	    			else
	    			{
	    				$.messager.alert('系统消息', '此黑名单已被审核，无法删除！', 'warning');
	    			}
		    	}
		    	else
		    	{
		    		$.messager.alert('系统消息', '只能删除自己提交的黑名单！', 'warning');
		    	}
	    	}
        };
        
    function checkRow(id,status,monitorComment){
    	$('#check_id').val(id);
    	if(status=='')
    	{
    		$('#check_status').combobox('setValue','0');
    	}
    	else
    	{
    		$('#check_status').combobox('setValue',status);
    	}
    	
    	$('#check_monitor_comment').val(monitorComment);
    	$('#check_blacklist').dialog('open');
    };
	function deleteBlacklistById(id)
	{
		$.ajax( {
			cache : true,
			type : "GET",
			url : 'blacklist.do?method=deleteBlacklistById&id=' + id,
			async : false,
			dataType : "json",
			success : function(data) {
				
				if (data.status == "200") {
					$.messager.alert('系统消息', '删除成功！', 'info');
					$('#blacklist').datagrid('reload');
				}else{
					$.messager.alert('系统消息','删除失败！','error');
				}
			}
		});
		$('#confirm_delete').dialog('close');
		};
		
	function checkBlacklistById(){
		var id = $('#check_id').val();
		var account = GetQueryString("account");
		var monitorComment = $('#check_monitor_comment').val();
		var status = $('#check_status').combobox('getValue');
		
		if(monitorComment==''){
			$.messager.alert('系统消息','请填写班长备注','info');
			return ;
			}
		
		if(status=='0'){
			$.messager.alert('系统消息','请选择审核状态','info');
			return ;
			}
		
		$.ajax( {
			cache : true,
			type : "POST",
			url : 'blacklist.do?method=checkBlacklistById&id=' + id+"&account="+encodeURIComponent(account)+"&status="+encodeURIComponent(status)+"&monitorComment="+encodeURIComponent(monitorComment),
			async : false,
			dataType : "json",
			success : function(data) {
				
				if (data.status == "200") {
					$.messager.alert('系统消息', '审核成功！', 'info');
					$('#blacklist').datagrid('reload');

					$('#check_blacklist').dialog('close');
				}else{
					$.messager.alert('系统消息','审核失败！','error');
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