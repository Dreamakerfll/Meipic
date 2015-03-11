
	//获取页面URL参数
	function GetQueryString(name) {
	
	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
	
	   var r = window.location.search.substr(1).match(reg);
	
	   if (r!=null) return decodeURI(r[2]); return null;
	
	};

	//页面打开时加载
    $(function() {

    	var baseUrl='';
    	var isMonitor = GetQueryString("isMonitor");
    	var account = GetQueryString("account");
    	if(isMonitor == '1')
        {
            baseUrl = 'transfer.do?method=findAll';
        }
    	else
        {
//    		baseUrl = 'transfer.do?method=findTransferByAccount&account='+encodeURIComponent(account);
    		baseUrl = 'transfer.do?method=findAll';
        }
    	//设置明细部属性
        $('#transfer').datagrid({
            url:baseUrl,
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
        	$('.editcls1').linkbutton({plain:true,iconCls:'icon-reload'}); 
        	$('.editcls2').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'}); 
        	$('.editcls3').linkbutton({text:'删除',plain:true,iconCls:'icon-cancel'}); 
        }  
        });

    	//设置选择区域部分默认隐藏
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

			var data = $('#currentAreaCombo').combobox('getData');
    		 for(var i = 0 ; i < data.length ;i++)
    		 {
    		  $("#c00" + i).attr("style","display:none");
    		 }
    		 $("#c00" + id).attr("style","display:block");

       }
       }); 

		//设置弹出层默认隐藏并遮罩
        $('#addTransfer').dialog({
            closed: true,   
            cache: false,
            modal: true
        }); 
        $('#selectArea').dialog({
            closed: true,   
            cache: false,
            modal: true
        }); 
        $('#currentSelectArea').dialog({
            closed: true,   
            cache: false,
            modal: true
        });
        $('#confirm_delete').dialog({
            closed: true,   
            cache: false,
            modal: true
        });
        $('#editTransfer').dialog({
            closed: true,   
            cache: false,
            modal: true
        });

        //设置转接号码的启用状态
        $('#statusToAdd').combobox({
        	url:'json/status.json',
			valueField: 'statusvalue',
            textField: 'statusname',
            editable:false ,
			panelHeight: 'auto'
            });

		//设置转接号码的启用状态
        $('#statusToEdit').combobox({
        	url:'json/status.json',
			valueField: 'statusvalue',
            textField: 'statusname',
            editable:false ,
			panelHeight: 'auto'
            });

    });
    
    var toolbar = [{
		text:'添加转接',
		iconCls:'icon-add',
		handler:function(){

			if($('#transfer').datagrid('getRows').length>0)
			{
				$.messager.alert('系统消息','已经有转接号码','info');
				return;
			}
			openNewTransfer();
    	
		}
	}];

    //格式化三个按钮
	function formatter1(value,row,index){
		if(row.status == '1')
		{
			return "<a class='editcls1' onclick='changeRow("+ row.transferBatch +",0"+")' href='javascript:void(0)'>禁用</a>"+
			"<a class='editcls2' href='javascript:void(0)' onclick='editRow("+row.transferBatch+","+row.transferNumber+",\""+row.beginTime+"\",\""+row.endTime+"\",\""+row.status+"\",\""+row.submitTime+"\",\""+row.transferBatch+"\",\""+row.transferAreaId+"\",\""+row.transferArea+"\")' ></a>"+
			"<a class='editcls3' onclick='deleteRow("+ row.transferBatch +")' href='javascript:void(0)'></a>";
		}
		else
		{
			return "<a class='editcls1' onclick='changeRow("+ row.transferBatch +",1"+")' href='javascript:void(0)'>启用</a>"+
			"<a class='editcls2' href='javascript:void(0)' onclick='editRow("+row.transferBatch+","+row.transferNumber+",\""+row.beginTime+"\",\""+row.endTime+"\",\""+row.status+"\",\""+row.submitTime+"\",\""+row.transferBatch+"\",\""+row.transferAreaId+"\",\""+row.transferArea+"\")' ></a>"+
			"<a class='editcls3' onclick='deleteRow("+ row.transferBatch +")' href='javascript:void(0)'></a>";
		}
		};

    //打开添加转接的弹出层
	function openNewTransfer(){

    	//状态重置为启用
        $('#statusToAdd').combobox("setValue",'1');

		//添加转接时默认全部输入框清空
		$('#phoneToAdd').numberbox('setValue','');
		$('#beginTimeToAdd').datetimebox('setValue','');
		$('#endTimeToAdd').datetimebox('setValue','');
		
		//添加转接时默认区域未选择
		var items = $('#selectSub input');
		$('#makeSureItem').html("");
		$('#previewItem').html("");

		for(var i=0;i<items.length;i++)
		{
			items[i].checked = false;
		}
		
		$('#addTransfer').dialog('open');
		};

	function searchByCondition(){
		var phoneNumber = $('#transferPhoneForSearch').val().trim();
		var area = $('#areaForSearch').val().trim();
		var beginTime = $('#beginTimeForSearch').datetimebox('getValue');
		var endTime = $('#endTimeForSearch').datetimebox('getValue');
		var baseUrl='';
    	var isMonitor = GetQueryString("isMonitor");
    	var account = GetQueryString("account");
    	if(isMonitor == '1')
        {
            baseUrl = 'transfer.do?method=findByCondition&beginTime=' + encodeURIComponent(beginTime) + '&endTime=' + encodeURIComponent(endTime) + '&phoneNumber=' + encodeURIComponent(phoneNumber) + '&area=' + encodeURIComponent(area) + '&account=';
        }
    	else
        {
    		baseUrl = 'transfer.do?method=findByCondition&beginTime=' + encodeURIComponent(beginTime) + '&endTime=' + encodeURIComponent(endTime) + '&phoneNumber=' + encodeURIComponent(phoneNumber) + '&area=' + encodeURIComponent(area) + '&account=' + encodeURIComponent(account);
        }

    	//显示第一页数据   
        $('#transfer').datagrid("options").pageNumber = 1;
        
        //分页栏上跳转到第一页
        $('#transfer').datagrid('getPager').pagination({pageNumber: 1}); 
		$('#transfer').datagrid({singleSelect:true,url: baseUrl});
		};

	//添加转接号码到数据库
	function addTransferToDb(){
		var phoneNumber = $('#phoneToAdd').val().trim();
		var beginTime = $('#beginTimeToAdd').datetimebox('getValue');
		var endTime = $('#endTimeToAdd').datetimebox('getValue');
		var status = $('#statusToAdd').combobox('getValue');
		var account = GetQueryString("account");
		
		var d = new Date();
		var submitTime = d.getFullYear() + "-" +((d.getMonth()+1)<10?"0":"")+(d.getMonth()+1) + "-" + (d.getDate()<10?"0":"")+d.getDate() + " " + (d.getHours()<10?"0":"")+d.getHours() + ":" + (d.getMinutes()<10?"0":"")+d.getMinutes() + ":" + (d.getSeconds()<10?"0":"")+d.getSeconds();
		
		var areaId = "0592,";
 	 	var area = "厦门,";
// 	 	var items = $('#makeSureItem input');
// 	 	for(var i=0;i<items.length;i++)
// 	 	{
// 	 	 	if(items[i].checked)
// 	 	 	{
//	 	 		areaId += items[i].value+',';
//	 	 		area += items[i].name+',';
// 	 	 	}
// 	 	 }

		var varify;//用于查询验证,验证开始时间是否小于结束时间 
		varify=beginTime>endTime; 
		
		if(phoneNumber==''){
				$.messager.alert('系统消息','请输入转接号码','info');
				return ;
				}
			if(beginTime==''){
				$.messager.alert('系统消息','请输入开始时间','info');
				return ;
				}
			if(endTime==''){
				$.messager.alert('系统消息','请输入结束时间','info');
				return ;
				}
			if(beginTime!=''&&endTime!='')
			{
				if(varify){
					$.messager.alert('系统消息','结束时间要大于开始时间','warning');
					return ;
					}
			}
			if(status==''){
				$.messager.alert('系统消息','请选择状态','info');
				return ;
				}
//			if(areaId==''){
//				$.messager.alert('系统消息','请选择区域','info');
//				return ;
//				}
//			if(area==''){
//				$.messager.alert('系统消息','请选择区域','info');
//				return ;
//				}
//
//			//验证所设区域是否已经分配转接人员
//			$.ajax( {
//				cache : true,
//				type : "POST",
//				url : 'transfer.do?method=isAreaExist&area=' + encodeURIComponent(areaId),
//				async : false,
//				dataType : "html",
//				success : function(data) {
//			    	
//					var json = $.parseJSON(data);
//					if (json.statu == "200") {
						
						$.ajax( {
							cache : true,
							type : "POST",
							url : 'transfer.do?method=addTransfer&beginTime=' + encodeURIComponent(beginTime) + '&endTime=' + encodeURIComponent(endTime) + '&phoneNumber=' + encodeURIComponent(phoneNumber) + '&account=' + encodeURIComponent(account) + '&status=' + encodeURIComponent(status) + '&areaId=' + encodeURIComponent(areaId) + '&area=' + encodeURIComponent(area) + '&submitTime=' + encodeURIComponent(submitTime),
							async : false,
							dataType : "html",
							success : function(data) {
						    	
								var json = $.parseJSON(data);
								if (json.statu == "200") {
									$('#addTransfer').dialog('close');
									$.messager.alert('系统消息', '添加成功！', 'info');
									//显示第一页数据   
						            $('#transfer').datagrid("options").pageNumber = 1;
						            
						            //分页栏上跳转到第一页
						            $('#transfer').datagrid('getPager').pagination({pageNumber: 1}); 
									$('#transfer').datagrid('reload');
								}else{
									$.messager.alert('系统消息','添加失败！','error');
								}
							}
						});
						
//					}else{
//						$.messager.alert('系统消息',json.statu+"\n\t区域已经设置",'warning');
//					}
//				}
//			});
			

		
		};

	//编辑转接号码到数据库
	function editTransferToDb(){
		var phoneNumber = $('#phoneToEdit').val().trim();
		var beginTime = $('#beginTimeToEdit').datetimebox('getValue');
		var endTime = $('#endTimeToEdit').datetimebox('getValue');
		var status = $('#statusToEdit').combobox('getValue');
		var account = GetQueryString("account");
		var submitTime = $('#submitTimeToEdit').val();
		var batchId = $('#batchIdToEdit').val();
		var areaId = "0592,";
//		var newAreaId = "";
 	 	var area = "厦门,";
// 	 	var items = $('#currentMakeSureItem input');
// 	 	var old_items = $('#currentMakeSureItem_old input');
// 	 	
// 	 	for(var i=0;i<items.length;i++)
// 	 	{
// 	 	 	if(items[i].checked)
// 	 	 	{
//	 	 		areaId += items[i].value+',';
//	 	 		area += items[i].name+',';
// 	 	 	}
// 	 	 }
// 	 	
// 	 	for(var i=0;i<items.length;i++)
// 	 	{
// 	 		var count = 1;
// 	 		
// 	 		for(var j=0;j<old_items.length;j++)
// 	 	 	{
// 	 	 	 	if(items[i].checked && items[i].value == old_items[j].value)
// 	 	 	 	{
// 	 	 	 		break;
// 	 	 	 	}
// 	 	 	 	else
// 	 	 	 	{
// 	 	 	 		count++;
// 	 	 	 	}
// 	 	 	 }
//
// 	 		if(items[i].checked && count>old_items.length)
// 	 		{
// 	 			newAreaId += items[i].value+',';
// 	 		}
// 	 	 }

		var varify;//用于查询验证,验证开始时间是否小于结束时间 
		varify=beginTime>endTime; 
		
		if(phoneNumber==''){
				$.messager.alert('系统消息','请输入转接号码','info');
				return ;
				}
			if(beginTime==''){
				$.messager.alert('系统消息','请输入开始时间','info');
				return ;
				}
			if(endTime==''){
				$.messager.alert('系统消息','请输入结束时间','info');
				return ;
				}
			if(beginTime!=''&&endTime!='')
			{
				if(varify){
					$.messager.alert('系统消息','结束时间要大于开始时间','warning');
					return ;
					}
			}
			if(status==''){
				$.messager.alert('系统消息','请选择状态','info');
				return ;
				}
//			if(areaId==''){
//				$.messager.alert('系统消息','请选择区域','info');
//				return ;
//				}
//			if(area==''){
//				$.messager.alert('系统消息','请选择区域','info');
//				return ;
//				}
//			
//			//验证所设区域是否已经分配转接人员
//			$.ajax( {
//				cache : true,
//				type : "POST",
//				url : 'transfer.do?method=isAreaExist&area=' + encodeURIComponent(newAreaId),
//				async : false,
//				dataType : "html",
//				success : function(data) {
//			    	
//					var json = $.parseJSON(data);
//					if (json.statu == "200") {
						
						$.ajax( {
							cache : true,
							type : "POST",
							url : 'transfer.do?method=updateTransfer&beginTime=' + encodeURIComponent(beginTime) + '&endTime=' + encodeURIComponent(endTime) + '&phoneNumber=' + encodeURIComponent(phoneNumber) + '&account=' + encodeURIComponent(account) + '&status=' + encodeURIComponent(status) + '&areaId=' + encodeURIComponent(areaId) + '&area=' + encodeURIComponent(area) + '&submitTime=' + encodeURIComponent(submitTime)+ '&batchId=' + encodeURIComponent(batchId),
							async : false,
							dataType : "html",
							success : function(data) {
						    	
								var json = $.parseJSON(data);
								if (json.statu == "200") {
									$('#editTransfer').dialog('close');
									$.messager.alert('系统消息', '编辑成功！', 'info');
									$('#transfer').datagrid('reload');
								}else{
									$.messager.alert('系统消息','编辑失败！','error');
								}
							}
						});
						
//					}else{
//						$.messager.alert('系统消息',json.statu+"\n\t区域已经设置",'warning');
//					}
//				}
//			});


		};

	function deleteRow(batchId){
		$('#delete_id').val(batchId);
		$('#confirm_delete').dialog('open');
		};

	function deleteTransferByBatchId(batchId){
		$.ajax( {
			cache : true,
			type : "POST",
			url : 'transfer.do?method=deleteTransferByBatchId&batchId=' + encodeURIComponent(batchId),
			async : false,
			dataType : "html",
			success : function(data) {
				var json = $.parseJSON(data);
				if (json.statu == "200") {
					$('#confirm_delete').dialog('close');
					$.messager.alert('系统消息', '删除成功！', 'info');
					$('#transfer').datagrid('reload');
				}else{
					$.messager.alert('系统消息','删除失败！','error');
				}
			}
		});

		};
	function changeRow(batchId,status){
		$.ajax( {
			cache : true,
			type : "POST",
			url : 'transfer.do?method=updateStatus&batchId=' + encodeURIComponent(batchId)+'&status='+encodeURIComponent(status),
			async : false,
			dataType : "html",
			success : function(data) {
				var json = $.parseJSON(data);
				if (json.statu == "200") {
					$('#confirm_delete').dialog('close');
					$.messager.alert('系统消息', '更新成功！', 'info');
					$('#transfer').datagrid('reload');
				}else{
					$.messager.alert('系统消息','更新失败！','error');
				}
			}
		});
		};

	function editRow(batchId,transferNumber,beginTime,endTime,status,submitTime,transferBatch,areaId,area){

    	//设置启用状态
        $('#statusToEdit').combobox("setValue",status);

		//输入框重新赋值
		$('#phoneToEdit').numberbox('setValue',transferNumber);
		$('#beginTimeToEdit').datetimebox('setValue',beginTime);
		$('#endTimeToEdit').datetimebox('setValue',endTime);
		$('#submitTimeToEdit').val(submitTime);
		$('#batchIdToEdit').val(transferBatch);
		
		var areaIds = areaId.split(',');
		var areas = area.split(',');

		var mes = "";
 		$("#currentMakeSureItem").html("");

 		for(var i = 0 ; i < areaIds.length; i++)
 		{
 			mes += "<input type='checkbox' checked='true' value='"+ areaIds[i] +"' name='"+ areas[i] +"' onclick='currentCopyItem(\"" + "#currentMakesureItem"+ "\",\""+ "#currentPreviewItem" +"\");currentSame(this);'>" + areas[i];   
 	 	}
 		$("#currentMakeSureItem").html(mes);
 		$("#currentMakeSureItem_old").html(mes);

 		$('#editTransfer').dialog('open');
 		
		};

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
		
		
		$('#currentSelectArea').dialog('open');
	 	 	};


	//区域联动的一系列function
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