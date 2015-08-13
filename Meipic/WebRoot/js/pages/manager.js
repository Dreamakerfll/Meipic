	//获取页面URL参数
	function GetQueryString(name) {
	
	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
	
	   var r = window.location.search.substr(1).match(reg);
	
	   if (r!=null) return decodeURI(r[2]); return null;
	
	};
	
	//页面预加载
	$(function(){
		
		//dialog默认隐藏并遮罩
		$('#add_user_dialog').dialog({
	           closed: true,
	           cache: false,
	           modal: true
	       }); 
		$('#edit_user_dialog').dialog({
			closed: true,
			cache: false,
			modal: true
		}); 
		$('#select_area_dialog').dialog({
			closed: true,
			cache: false,
			modal: true
		}); 
		$('#current_select_area_dialog').dialog({
			closed: true,
			cache: false,
			modal: true
		});
		$('#add_role_dialog').dialog({
	           closed: true,
	           cache: false,
	           modal: true
	       }); 
		$('#edit_role_dialog').dialog({
			closed: true,
			cache: false,
			modal: true
		}); 
		$('#add_department_dialog').dialog({
			closed: true,
			cache: false,
			modal: true
		}); 
		$('#edit_department_dialog').dialog({
			closed: true,
			cache: false,
			modal: true
		});
		
		//用户管理条件部combobox加载
    	
    	//搜索条件用户角色加载
    	$('#user_role_search').combobox({
    		url:'role_info.do?method=findAll&type=0',
    		valueField:'id',
    		textField:'nameDisplay',
    		panelHeigh:'auto',
    		editable:false
    	});
    	
    	//搜索条件所属部门加载
    	$('#user_department_search').combobox({
    		url:'department_info.do?method=findAll&type=0',
    		valueField:'id',
    		textField:'nameDisplay',
    		panelHeigh:'auto',
    		editable:false
    	});
    	
    	//搜索条件用户状态加载
    	$('#user_status_search').combobox({
    		url:'json/user_status_search.json',
    		valueField:'status',
    		textField:'statusDisplay',
    		panelHeigh:'auto',
    		editable:false
    	});
		
		//选择区域框的控制，默认显示第一个div
		$("#selectSub div").hide();
		$("#selectSub div").eq(0).show();

		$("#currentSelectSub div").hide();
		$("#currentSelectSub div").eq(0).show();
		 
    	$('#AreaCombo').combobox({
        	onChange:function showSelect(id){
        	
			var com = $(".combobox-item").length;

    		 for(var i = 0 ; i < com ;i++)
    		 {
    		  $("#c0" + i).attr("style","display:none");
    		 }
    		 $("#c0" + id).attr("style","display:block");

       }
       });
    	$('#currentAreaCombo').combobox({
        	onChange:function showSelect(id){

			//var com = $(".combobox-item").length;

			var data = $('#currentAreaCombo').combobox('getData');
    		 for(var i = 0 ; i < data.length ;i++)
    		 {
    		  $("#c00" + i).attr("style","display:none");
    		 }
    		 $("#c00" + id).attr("style","display:block");

       }
       }); 
		
    	//用户datagrid加载
        $('#user_table').datagrid({
        	url:'user_info.do?method=findAllWithPage',
            pageSize : 10,//默认选择的分页是每页5行数据
            pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
            striped : true,//设置为true将交替显示行背景。
            singleSelect:true,//为true时只能选择单行
            fitColumns:true,//允许表格自动缩放，以适应父容器
            remoteSort : false,
            pagination : true,//分页
            collapsible:true,
            toolbar:toolOfUser,
            onLoadSuccess:function(data){
        	$('.editcls1').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'}); 
        	$('.delcls1').linkbutton({text:'删除',plain:true,iconCls:'icon-del'}); 
        }  
        });
        //用户datagrid隐藏列
        $('#user_table').datagrid('hideColumn','id');
        $('#user_table').datagrid('hideColumn','roleId');
        $('#user_table').datagrid('hideColumn','departmentId');
        $('#user_table').datagrid('hideColumn','queue');
        
        //角色datagrid加载
        $('#role_table').datagrid({
        	url:'role_info.do?method=findAllWithPage',
            pageSize : 10,//默认选择的分页是每页5行数据
            pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
            striped : true,//设置为true将交替显示行背景。
            singleSelect:true,//为true时只能选择单行
            fitColumns:true,//允许表格自动缩放，以适应父容器
            remoteSort : false,
            pagination : true,//分页
            toolbar:toolOfRole,

            onLoadSuccess:function(data){
        	$('.editcls2').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'}); 
        	$('.delcls2').linkbutton({text:'删除',plain:true,iconCls:'icon-cancel'}); 
        }  
        });
        //角色datagrid隐藏列
        $('#role_table').datagrid('hideColumn','id');
        $('#role_table').datagrid('hideColumn','rightId');
        
        //部门datagrid加载
        $('#department_table').datagrid({
        	url:'department_info.do?method=findAllWithPage',
        	pageSize : 10,//默认选择的分页是每页5行数据
        	pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
        	striped : true,//设置为true将交替显示行背景。
        	singleSelect:true,//为true时只能选择单行
        	fitColumns:true,//允许表格自动缩放，以适应父容器
        	remoteSort : false,
        	pagination : true,//分页
        	toolbar:toolOfDepartment,
        	
        	onLoadSuccess:function(data){
        	$('.editcls3').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'}); 
        	$('.delcls3').linkbutton({text:'删除',plain:true,iconCls:'icon-cancel'}); 
        }
        });
        //部门datagrid隐藏列
        $('#department_table').datagrid('hideColumn','id');
		
	});
	
	//添加用户工具栏
    var toolOfUser = [{
		text:'添加用户',
		iconCls:'icon-add',
		handler:function(){

    	openAddUserDialog();
    	
		}
	}];
    
    //添加角色工具栏
    var toolOfRole = [{
    	text:'添加角色',
    	iconCls:'icon-add',
    	handler:function(){
    	
    	openAddRoleDialog();
    	
    }
    }];
    
    //添加部门工具栏
    var toolOfDepartment = [{
    	text:'添加部门',
    	iconCls:'icon-add',
    	handler:function(){
    	
    	openAddDepartmentDialog();
    	
    }
    }];
    
    //用户数据操作
    function userOperate(value,row,index)
    {
    	var btn = '<a href="javascript:void(0)" class="editcls1" onclick="openEditUserDialog(\''+row.id+'\',\''+row.name+'\',\''+row.agent+'\',\''+row.roleId+'\',\''+row.departmentId+'\',\''+row.queue+'\',\''+row.area+'\',\''+row.tel+'\',\''+row.status+'\')">'+'</a>'+
    	'<a href="javascript:void(0)" class="delcls1" onclick="delUserInfo(\''+row.id+'\')">'+'</a>';
		return btn;
    }
    
    //角色数据操作
    function roleOperate(value,row,index)
    {
    	var btn = '<a href="javascript:void(0)" class="editcls2" onclick="openEditRoleDialog(\''+row.id+'\',\''+row.name+'\',\''+row.rightId+'\',\''+row.rightContent+'\')">'+'</a>'+
    	'<a href="javascript:void(0)" class="delcls2" onclick="delRoleInfo(\''+row.id+'\')">'+'</a>';
    	return btn;
    }
    
    //部门数据操作
    function departmentOperate(value,row,index)
    {
    	var btn = '<a href="javascript:void(0)" class="editcls3" onclick="openEditDepartmentDialog(\''+row.id+'\',\''+row.name+'\',\''+row.manager+'\')">'+'</a>'+
    	'<a href="javascript:void(0)" class="delcls3" onclick="delDepartmentInfo(\''+row.id+'\',\''+row.headcount+'\')">'+'</a>';
    	return btn;
    }
    
    //打开添加用户dialog
    function openAddUserDialog()
    {
    	//座席分机号加载
    	$('#user_agent_add').combobox({
    		url:'agent_info.do?method=findNoUseAgent&agent='+encodeURIComponent(""),
    		valueField:'number',
    		textField:'numberDisplay',
    		panelHeigh:'auto',
    		editable:false
    	});
    	
    	//用户角色加载
    	$('#user_role_add').combobox({
    		url:'role_info.do?method=findAll',
    		valueField:'id',
    		textField:'nameDisplay',
    		panelHeigh:'auto',
    		editable:false
    	});
    	
    	//所属部门加载
    	$('#user_department_add').combobox({
    		url:'department_info.do?method=findAll',
    		valueField:'id',
    		textField:'nameDisplay',
    		panelHeigh:'auto',
    		editable:false
    	});
    	
    	//用户状态加载
    	$('#user_status_add').combobox({
    		url:'json/user_status.json',
    		valueField:'status',
    		textField:'statusDisplay',
    		panelHeigh:'auto',
    		editable:false
    	});
    	
    	//值清空并重新验证合法性，目的为的是页面打开时所有输入框都是处于验证状态
    	$('#user_account_add').val('').validatebox('validate');
    	$('#user_name_add').val('').validatebox('validate');
    	$('#user_agent_add').combobox('setValue','').combobox('validate');
    	$('#user_role_add').combobox('setValue','').combobox('validate');
    	$('#user_department_add').combobox('setValue','').combobox('validate');
    	$('#user_tel_add').val('').validatebox('validate');
    	$('#user_status_add').combobox('setValue','').combobox('validate');
    	
    	//区域值清空
    	var items = $('#selectSub input');
		$('#makeSureItem').html("");
		$('#previewItem').html("");

		for(var i=0;i<items.length;i++)
		{
			items[i].checked = false;
		}
    	
    	$('#add_user_dialog').dialog('open');
    };
    
    //查询用户按钮点击事件
    $('#search_user_btn').click(function(){
    	
    	//页面信息收集
    	var account = $('#user_account_search').val();
    	var name = $('#user_name_search').val();
    	var agent = $('#user_agent_search').val();
    	var role = $('#user_role_search').combobox('getValue');
    	var department = $('#user_department_search').combobox('getValue');
    	var area =$('#user_area_search').val();
    	var tel = $('#user_tel_search').val();
    	var status = $('#user_status_search').combobox('getValue');
    	
    	//封装成对象
    	var user_info = JSON.stringify({
    		
    		id:'',
    		account:account,
    		name:name,
    		agent:agent,
    		roleId:role,
    		departmentId:department,
    		area:area,
    		tel:tel,
    		status:status
    	});
    	
    	//显示第一页数据   
        $('#user_table').datagrid("options").pageNumber = 1;
        
        //分页栏上跳转到第一页
        $('#user_table').datagrid('getPager').pagination({pageNumber: 1}); 
        
    	$('#user_table').datagrid({url:'user_info.do?method=findUserByCondition&user_info='+encodeURIComponent(user_info)});
    });
    
    //打开添加角色dialog
    function openAddRoleDialog()
    {
    	//数据清空并验证，目地是打开时dialog处于验证状态
    	$('#role_name_add').val('').validatebox('validate');
	 	var items = $('#rightCheckbox input');
	 	for ( var i = 0; i < items.length;i++) {
	 		items[i].checked = false;
		}
	 		
	    $('#add_role_dialog').dialog('open');
    };
    
    //打开编辑用户信息dialog
    function openEditUserDialog(id,name,agent,role,department,queue,area,tel,status)
    {
    	//页面信息赋值并重新验证合法性
    	
    	//设置隐藏的用户ID值
    	$('#user_id_edit').val(id);
    	
    	//座席分机号加载
    	$('#user_agent_edit').combobox({
    		url:'agent_info.do?method=findNoUseAgent&agent='+encodeURIComponent(agent),
    		valueField:'number',
    		textField:'numberDisplay',
    		panelHeigh:'auto',
    		editable:false
    	});
    	$('#user_agent_edit').combobox('setValue',agent).combobox('validate');
    	
    	//用户角色加载
    	$('#user_role_edit').combobox({
    		url:'role_info.do?method=findAll',
    		valueField:'id',
    		textField:'nameDisplay',
    		panelHeigh:'auto',
    		editable:false
    	});
    	$('#user_role_edit').combobox('setValue',role).combobox('validate');
    	
    	//用户姓名赋值
    	$('#user_name_edit').val(name).validatebox('validate'); 
    	
    	//联系电话赋值
    	$('#user_tel_edit').val(tel).validatebox('validate');
    	
    	//所属部门加载
    	$('#user_department_edit').combobox({
    		url:'department_info.do?method=findAll',
    		valueField:'id',
    		textField:'nameDisplay',
    		panelHeigh:'auto',
    		editable:false
    	});
    	$('#user_department_edit').combobox('setValue',department).combobox('validate');
    	
    	//用户状态加载
    	$('#user_status_edit').combobox({
    		url:'json/user_status.json',
    		valueField:'status',
    		textField:'statusDisplay',
    		panelHeigh:'auto',
    		editable:false
    	});
    	$('#user_status_edit').combobox('setValue',status).combobox('validate');
    	
    	//区域赋值
    	var currentAreas = area.split(',');
	 		var currentQueues = queue.split(',');
	 		var mes = "";
	 		$("#currentMakeSureItem").html("");

	 		for(var i = 0 ; i < currentQueues.length-1; i++)
	 		{
	 			mes += "<input type='checkbox' checked='true' value='"+ currentQueues[i] +"' name='"+ currentAreas[i] +"' onclick='currentCopyItem(\"" + "#currentMakesureItem"+ "\",\""+ "#currentPreviewItem" +"\");currentSame(this);'>" + currentAreas[i];   
	 	 	}
	 		$("#currentMakeSureItem").html(mes);
    	
    	$('#edit_user_dialog').dialog('open');
    };
    
    //打开添加部门dialog
    function openAddDepartmentDialog()
    {
    	//值清空并验证，目地是使dialog打开时处于验证状态
    	$('#department_name_add').val('').validatebox('validate');
    	$('#department_manager_add').val('').validatebox('validate');
    	
    	$('#add_department_dialog').dialog('open');
    };
    
    //打开编辑部门dialog
    function openEditDepartmentDialog(id,name,manager)
    {
    	$('#department_id_edit').val(id);
    	
    	//赋值并验证，目地是使dialog打开时处于验证状态
    	$('#department_name_edit').val(name).validatebox('validate');
    	$('#department_manager_edit').val(manager).validatebox('validate');
    	
    	$('#edit_department_dialog').dialog('open');
    };
    
    //保存新增的用户
    function saveAddUser()
    {
    	//页面值的验证
 	 	if(!$('#user_account_add').validatebox('isValid')){
			$.messager.alert('系统消息','请填写正确的用户账号','info');
			return ;
		}
 	 	if(!$('#user_name_add').validatebox('isValid')){
 	 		$.messager.alert('系统消息','请填写用户姓名','info');
 	 		return ;
 	 	}
 	 	if(!$('#user_tel_add').validatebox('isValid')){
 	 		$.messager.alert('系统消息','请填写正确的联系电话','info');
 	 		return ;
 	 	}
		if(!$('#user_agent_add').combobox('isValid')){
			$.messager.alert('系统消息','请选择座席分机号','info');
			return ;
			}
		if(!$('#user_role_add').combobox('isValid')){
			$.messager.alert('系统消息','请选择用户角色','info');
			return ;
			}
		if(!$('#user_department_add').combobox('isValid')){
			$.messager.alert('系统消息','请选择所属部门','info');
			return ;
		}
		if(!$('#user_status_add').combobox('isValid')){
			$.messager.alert('系统消息','请选择用户状态','info');
			return ;
		}
		
    	//获取页面的值
    	var user_account = $('#user_account_add').val();
    	var user_name = $('#user_name_add').val();
    	var user_tel = $('#user_tel_add').val();
    	var user_agent = $('#user_agent_add').combobox('getValue');
    	var user_department = $('#user_department_add').combobox('getValue');
    	var user_role = $('#user_role_add').combobox('getValue');
    	var user_status = $('#user_status_add').combobox('getValue');
    	var area = "";
    	var queue = "";
	 	var items = $('#makeSureItem input');
 	 	for(var i=0;i<items.length;i++)
 	 	{
 	 	 	if(items[i].checked)
 	 	 	{
 	 	 		area += items[i].name+',';
 	 	 		queue += items[i].value+',';
 	 	 	}
 	 	 }
 	 	
 	 	//检查区域是否有选择
		if(area==''){
			$.messager.alert('系统消息','请选择区域','info');
			return ;
			}
		if(queue==''){
			$.messager.alert('系统消息','请选择区域','info');
			return ;
			}
		
		//封装成用户对象
 	 	var user_info = JSON.stringify({
 	 			id:'',
 	 			account:user_account,
 	 			name:user_name,
 	 			tel:user_tel,
 	 			agent:user_agent,
 	 			departmentId:user_department,
 	 			roleId:user_role,
 	 			status:user_status,
 	 			queue:queue,
 	 			area:area
 	 			});

		//提交数据到数据库
 	 	$.ajax( {
				cache : true,
				type : "POST",
				url : 'user_info.do?method=insertUserInfo&user_info=' + encodeURIComponent(user_info),
				async : false,
				dataType : "json",
				success : function(data) {
					
					if (data.status == "200") {
						$('#add_user_dialog').dialog('close');
						
						//显示第一页数据   
				        $('#user_table').datagrid("options").pageNumber = 1;
				        
				        //分页栏上跳转到第一页
				        $('#user_table').datagrid('getPager').pagination({pageNumber: 1}); 
				        
						$('#user_table').datagrid('reload');
						$('#department_table').datagrid('reload');
						$.messager.alert('系统消息', '添加成功！', 'info');
					}else{
						$.messager.alert('系统消息','添加失败！','error');
					}
				}
			});
 	 	
    };
    
    //更新用户信息
    function saveEditUser()
    {
    	//页面值的验证
 	 	if(!$('#user_name_edit').validatebox('isValid')){
 	 		$.messager.alert('系统消息','请填写用户姓名','info');
 	 		return ;
 	 	}
 	 	if(!$('#user_tel_edit').validatebox('isValid')){
 	 		$.messager.alert('系统消息','请填写正确的联系电话','info');
 	 		return ;
 	 	}
		if(!$('#user_agent_edit').combobox('isValid')){
			$.messager.alert('系统消息','请选择座席分机号','info');
			return ;
			}
		if(!$('#user_role_edit').combobox('isValid')){
			$.messager.alert('系统消息','请选择用户角色','info');
			return ;
			}
		if(!$('#user_department_edit').combobox('isValid')){
			$.messager.alert('系统消息','请选择所属部门','info');
			return ;
		}
		if(!$('#user_status_edit').combobox('isValid')){
			$.messager.alert('系统消息','请选择用户状态','info');
			return ;
		}
		
    	//获取页面的值
		var user_id = $('#user_id_edit').val();
    	var user_name = $('#user_name_edit').val();
    	var user_tel = $('#user_tel_edit').val();
    	var user_agent = $('#user_agent_edit').combobox('getValue');
    	var user_department = $('#user_department_edit').combobox('getValue');
    	var user_role = $('#user_role_edit').combobox('getValue');
    	var user_status = $('#user_status_edit').combobox('getValue');
    	var area = "";
    	var queue = "";
	 	var items = $('#currentMakeSureItem input');
 	 	for(var i=0;i<items.length;i++)
 	 	{
 	 	 	if(items[i].checked)
 	 	 	{
 	 	 		area += items[i].name+',';
 	 	 		queue += items[i].value+',';
 	 	 	}
 	 	 }
 	 	
 	 	//检查区域是否有选择
		if(area==''){
			$.messager.alert('系统消息','请选择区域','info');
			return ;
			}
		if(queue==''){
			$.messager.alert('系统消息','请选择区域','info');
			return ;
			}
		
		//封装成用户对象
 	 	var user_info = JSON.stringify({
 	 		id:user_id,
 			name:user_name,
 			tel:user_tel,
 			agent:user_agent,
 			departmentId:user_department,
 			roleId:user_role,
 			status:user_status,
 			queue:queue,
 			area:area
 			});

		//提交数据到数据库
 	 	$.ajax( {
				cache : true,
				type : "POST",
				url : 'user_info.do?method=updateUserInfo&user_info=' + encodeURIComponent(user_info),
				async : false,
				dataType : "json",
				success : function(data) {
					
					if (data.status == "200") {
						$('#edit_user_dialog').dialog('close');
						
						$('#user_table').datagrid('reload');
						$('#department_table').datagrid('reload');
						$.messager.alert('系统消息','更新成功！','info');
					}else{
						$.messager.alert('系统消息','更新失败！','error');
					}
				}
			});
    };
    
    //删除用户
    function delUserInfo(id)
    {
    	//弹出确认框
		$.messager.confirm('请确认', '您确定要删除此用户吗?', function(b) {
            if(b) {
                
            	//提交数据到数据库
         	 	$.ajax( {
        				cache : true,
        				type : "POST",
        				url : 'user_info.do?method=deleteUserInfo&user_id=' + encodeURIComponent(id),
        				async : false,
        				dataType : "json",
        				success : function(data) {
        					
        					if (data.status == "200") {
        						
        						$('#user_table').datagrid('reload');
        						$('#department_table').datagrid('reload');
        						$.messager.alert('系统消息','删除成功！','info');
        					}else{
        						$.messager.alert('系统消息','删除失败！','error');
        					}
        				}
        			});
            }
        });
    };
    
    //新增角色信息到数据库
    function saveAddRole()
    {
    	//验证页面信息的合法性
    	if(!$('#role_name_add').validatebox('isValid'))
    	{
    		$.messager.alert('系统消息','请填写角色名称','info');
    		return false;
    	}
    	
    	var role_name = $('#role_name_add').val().trim();
		var right_id = "";
		var right_content = "";

		var items2 = $('#rightCheckbox input');
		 for(var i = 0 ; i < items2.length ; i++)
		 {
		  if(items2[i].checked == true)
		  {
			  right_id += items2[i].value +",";
			  right_content +=   items2[i].name +",";
		  }
		 }

		 if(right_id==''){
				$.messager.alert('系统消息','请选择角色权限','info');
				return false;
				}
		 
		 //封装成角色对象
		 var role_info = JSON.stringify({
			 id:'',
			 name:role_name,
			 rightId:right_id,
			 rightContent:right_content
		 });

		 $.ajax( {
				cache : true,
				type : "POST",
				url : 'role_info.do?method=insertRoleInfo&role_info=' + encodeURIComponent(role_info),
				async : false,
				dataType : "json",
				success : function(data) {
					if (data.status == "200") {
						
						//显示第一页数据   
				        $('#role_table').datagrid("options").pageNumber = 1;
				        
				        //分页栏上跳转到第一页
				        $('#role_table').datagrid('getPager').pagination({pageNumber: 1});
						$('#role_table').datagrid('reload');
						
						$.messager.alert('系统消息', '添加成功！', 'info');
						
						$('#add_role_dialog').dialog('close');
					}else{
						$.messager.alert('系统消息','添加失败！','error');
					}
				}
			});
    };
    
    //打开编辑角色信息dialog
    function openEditRoleDialog(id,name,rightId,rightContent)
    {
    	$('#role_id_edit').val(id);
    	$('#role_name_edit').val(name);
    	
    	var rights = rightContent.split(',');

 		var items = $('#editRightCheckbox input');

 		for(var i = 0 ; i < items.length ; i++)
 		{
 			items[i].checked = false;
 		}
 		for(var i = 0 ; i < items.length ; i++)
 		{
	 		for(var j = 0 ; j < rights.length; j++)
			 {
	  			  if(items[i].name == rights[j])
	  			  {
	  				items[i].checked = true;
	  			  }
			 }
 		}
		 
 		$('#edit_role_dialog').dialog('open');
	 		
    };
    
    //修改角色信息到数据库
    function saveEditRole()
    {
    	//验证页面信息的合法性
    	if(!$('#role_name_edit').validatebox('isValid'))
    	{
    		$.messager.alert('系统消息','请填写角色名称','info');
    		return false;
    	}
    	
    	//获取页面信息
    	var role_id = $('#role_id_edit').val().trim();
    	var role_name = $('#role_name_edit').val().trim();
		var right_id = "";
		var right_content = "";
		 
		var items = $('#editRightCheckbox input');
		for(var i = 0 ; i < items.length ; i++)
		{
			if(items[i].checked == true)
			{
				right_id += items[i].value +",";
				right_content +=   items[i].name +",";
			}
		}
		 if(right_id==''){
			 $.messager.alert('系统消息','请选择角色权限','info');
			 return ;
		 }

		 if(right_content==''){
			 $.messager.alert('系统消息','请选择角色权限','info');
			 return ;
		 }
		 
		 //封装成对象
		 var role_info = JSON.stringify({
			 id:role_id,
			 name:role_name,
			 rightId:right_id,
			 rightContent:right_content
		 });

		 $.ajax( {
				cache : true,
				type : "POST",
				url : 'role_info.do?method=updateRoleInfo&role_info=' + encodeURIComponent(role_info),
				async : false,
				dataType : "json",
				success : function(data) {
					
					if (data.status == "200") {
						$.messager.alert('系统消息', '编辑成功！', 'info');
						$('#edit_role_dialog').dialog('close');

						$('#role_table').datagrid('reload');
						$('#user_table').datagrid('reload');
					}else{
						$.messager.alert('系统消息','编辑失败！','error');
					}
				}
			});
    };
    
    //删除角色
    function delRoleInfo(id)
    {
    	//弹出确认框
		$.messager.confirm('请确认', '您确定要删除此角色吗?', function(b) {
            if(b) {
                
            	//提交数据到数据库
         	 	$.ajax( {
        				cache : true,
        				type : "POST",
        				url : 'role_info.do?method=deleteRoleInfo&roleId=' + encodeURIComponent(id),
        				async : false,
        				dataType : "json",
        				success : function(data) {
        					
        					if (data.status == "200") {
        						
        						$('#role_table').datagrid('reload');
        						$.messager.alert('系统消息','删除成功！','info');
        					}else{
        						$.messager.alert('系统消息','删除失败！此角色已有用户指定！','error');
        					}
        				}
        			});
            }
        });
    };
    
    //新增部门信息到数据库
    function saveAddDepartment()
    {
    	//验证页面信息的合法性
    	if(!$('#department_name_add').validatebox('isValid'))
    	{
    		$.messager.alert('系统消息','请填写部门名称','info');
    		return false;
    	}
    	if(!$('#department_manager_add').validatebox('isValid'))
    	{
    		$.messager.alert('系统消息','请填写部门负责人','info');
    		return false;
    	}
    	
    	//获取页面信息
    	var department_name = $('#department_name_add').val();
    	var department_manager = $('#department_manager_add').val();
    	
    	//封装成对象
    	var department_info = JSON.stringify({
    		id:'',
    		name:department_name,
    		manager:department_manager
    	});
    	
    	//提交数据到数据库
 	 	$.ajax( {
				cache : true,
				type : "POST",
				url : 'department_info.do?method=insertDepartmentInfo&department_info=' + encodeURIComponent(department_info),
				async : false,
				dataType : "json",
				success : function(data) {
					
					if (data.status == "200") {
						
						$('#department_table').datagrid('reload');
						$('#add_department_dialog').dialog('close');
						$.messager.alert('系统消息','添加成功！','info');
					}else{
						$.messager.alert('系统消息','添加失败！','error');
					}
				}
			});
    };
    
    //修改部门信息到数据库
    function saveEditDepartment()
    {
    	//验证页面信息的合法性
    	if(!$('#department_name_edit').validatebox('isValid'))
    	{
    		$.messager.alert('系统消息','请填写部门名称','info');
    		return false;
    	}
    	if(!$('#department_manager_edit').validatebox('isValid'))
    	{
    		$.messager.alert('系统消息','请填写部门负责人','info');
    		return false;
    	}
    	
    	//获取页面信息
    	var department_id = $('#department_id_edit').val();
    	var department_name = $('#department_name_edit').val();
    	var department_manager = $('#department_manager_edit').val();
    	
    	//封装成对象
    	var department_info = JSON.stringify({
    		id:department_id,
    		name:department_name,
    		manager:department_manager
    	});
    	
    	//提交数据到数据库
 	 	$.ajax( {
				cache : true,
				type : "POST",
				url : 'department_info.do?method=updateDepartmentInfo&department_info=' + encodeURIComponent(department_info),
				async : false,
				dataType : "json",
				success : function(data) {
					
					if (data.status == "200") {
						
						$('#department_table').datagrid('reload');
						$('#user_table').datagrid('reload');
						$('#edit_department_dialog').dialog('close');
						$.messager.alert('系统消息','更新成功！','info');
					}else{
						$.messager.alert('系统消息','更新失败！','error');
					}
				}
			});
    };
    
	//删除部门
    function delDepartmentInfo(id,headcount)
    {
    	if(headcount > 0)
    	{
    		$.messager.alert('系统消息','删除失败！此部门已有用户指定！','error');
    		return false;
    	}
    	
    	//弹出确认框
		$.messager.confirm('请确认', '您确定要删除此部门吗?', function(b) {
            if(b) {
                
            	//提交数据到数据库
         	 	$.ajax( {
        				cache : true,
        				type : "POST",
        				url : 'department_info.do?method=deleteDepartmentInfo&departmentId=' + encodeURIComponent(id),
        				async : false,
        				dataType : "json",
        				success : function(data) {
        					
        					if (data.status == "200") {
        						
        						$('#department_table').datagrid('reload');
        						$.messager.alert('系统消息','删除成功！','info');
        					}else{
        						$.messager.alert('系统消息','删除失败！此部门已有用户指定！','error');
        					}
        				}
        			});
            }
        });
    };
    
    //打开选择区域dialog
 	function openCurrentSelectArea(){
		var items = $('#currentSelectSub input');
		var items2 = $('#currentMakeSureItem input');

		for(var i=0;i<items.length;i++)
		{
			items[i].checked = false;
			
			for(var j=0;j<items2.length;j++)
			{
				if(items[i].name == items2[j].name && items2[j].checked)
				{
					items[i].checked = true;
				}
			}
		}
 		var mes = "";
 		$("#currentPreviewItem").html("");

 		for(var i = 0 ; i < items2.length; i++)
 		{
 	 		if(items2[i].checked)
 	 		{
 				mes += "<input type='checkbox' checked='true' value='"+ items2[i].value +"' name='"+ items2[i].name +"' onclick='currentCopyItem(\"" + "#currentPreviewItem"+ "\",\""+ "#currentPreviewItem" +"\");currentSame(this);'>" + items2[i].name;   
 	 		}
 	 	}
 		$("#currentPreviewItem").html(mes);
		
		
 		$('#current_select_area_dialog').dialog('open');
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
        		data:{user_account_add:value},
        		type: "POST",
        		async : false,
        		dataType:'json',
        		success:function(data){
        			if(data.sign=="isExist"){
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
    
    //选择区域联动
    function addPreItem(){ 
   	 $("#previewItem").html("");
   	 var len = 0 ;
   	 var items = $("#selectSub input");
   	 for(var i = 0 ; i < items.length ; i++)
   	 {
   	  if(items[i].checked == true)
   	  {
       	  
   	   //len++;
   	   //if(len > lenMax)
   	   //{
   	   // alert("不能超过" + lenMax +"个选项！")
   	   // return false;
   	   //}
   		  var mes = "<input type='checkbox' checked='true' name='"+ items[i].name +"' value='"+ items[i].value +"' onclick='copyItem(\"#previewItem\",\"#previewItem\");same(this);'>" + items[i].name;

   		  $("#previewItem").append(mes);

   	  }
   	 }
   	};
   	function addCurrentPreItemAndSureItem(){
   		$("#currentPreviewItem").html("");
   		var items = $("#currentSelectSub input");

			for(var i = 0 ; i < items.length ; i++)
			{
				if(items[i].checked == true)
				{
					var mes = "<input type='checkbox' name='" + items[i].name +"' checked='true' value='"+ items[i].value +"' onclick='currentCopyItem(\"#currentPreviewItem\",\"#currentPreviewItem\");currentSame(this);'>" + items[i].name;
					$("#currentPreviewItem").append(mes);
			
				}
			}

       	};
   	function copyItem(id1,id2){
   		 var mes = "";
   		 var items2 = $(id1+" input");
   		 for(var i = 0 ; i < items2.length ; i++)
   		 {
   		  if(items2[i].checked == true)
   		  {
   		   mes += "<input type='checkbox' checked='true' name='"+items2[i].name+"' value='"+ items2[i].value +"' onclick='copyItem(\"" + id2+ "\",\""+ id1 +"\");same(this);'>" + items2[i].name;   
   		  }
   		 }
   		 $(id2).html("");
   		 $(id2).html(mes);
   		};
   	function currentCopyItem(id1,id2){
   		 var mes = "";
   		 var items2 = $(id1+" input");
   		 for(var i = 0 ; i < items2.length ; i++)
   		 {
   		  if(items2[i].checked == true)
   		  {
   		   mes += "<input type='checkbox' checked='true' name='"+ items2[i].name +"' value='"+ items2[i].value +"' onclick='currentCopyItem(\"" + id2+ "\",\""+ id1 +"\");currentSame(this);'>" + items2[i].name;   
   		  }
   		 }
   		 $(id2).html("");
   		 $(id2).html(mes);
   		};

  		function same(ck){
  			var items = $("#selectSub input");
  			 for(var i = 0 ; i < items.length ; i++)
  			 {
  			  if(ck.value == items[i].value)
  			  {
  			   items[i].checked = ck.checked;
  			  }
  			 }
  			};
  		function currentSame(ck){
  			var items = $("#currentSelectSub input");
  			 for(var i = 0 ; i < items.length ; i++)
  			 {
  			  if(ck.value == items[i].value)
  			  {
  			   items[i].checked = ck.checked;
  			  }
  			 }
  			};