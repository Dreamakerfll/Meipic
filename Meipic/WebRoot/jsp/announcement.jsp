<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String agentNumber  = request.getParameter("agentNumber")==null?"":request.getParameter("agentNumber").toString();//操作员
String role = request.getParameter("role")==null?"":request.getParameter("role").toString();//操作员
%>

<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公告</title>
    
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
	
		    	 
	<script type="text/javascript" charset="utf-8" src="umeditor/umeditor.config.js"></script>  
	<script type="text/javascript" charset="utf-8" src="umeditor/umeditor.js"></script>
	<script type="text/javascript" charset="utf-8" src="umeditor/lang/zh-cn/zh-cn.js"></script>
	<link rel="stylesheet" type="text/css" href="umeditor/themes/default/css/umeditor.css">
  </head>
  
  <body>
<%if(role.equals("1")){%>
<div id="announcement_bar" style="height:auto;" >
	<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addRow()">发布公告</a>
</div>
<%} %>
<table id="announcement_table" title="公告">
	<thead>
		<tr>
			<th data-options="field:'title',align:'center',width:100">标题</th>
			<th data-options="field:'releaseDate',align:'center',width:100">发布时间</th>
			<th data-options="field:'dueDate',align:'center',width:100">截止时间</th>
			<th data-options="field:'promulgator',align:'center',width:100">发布人</th>
			<th data-options="field:'id',formatter:rowformater,align:'center',width:100">操作</th>
		</tr>
	</thead>
</table>

<div id="announcement_add_div" class="easyui-window" date-options="iconCls:'icon-save'" closed="true" title="新增公告">
	<div style="padding:10px 60px 20px 60px">
	    <form id="add_form" action="knowledgeBase.do?method=addKnowledgeBase" method="post">
	    	<table cellpadding="5">
	    		<tr>
	    			<td align="left" width="12%">标题:</td>
	    			<td>
	    				<input id="add_title" name="add_title" class="easyui-textbox" style="width:200px"/>
	    			</td>
	    		</tr>
	    		<tr>
		    		<td align="left">截止时间:</td>
		    			<td>
		    				<input id="add_dueDate" name="add_dueDate" class="easyui-datetimebox" style="width:200px"/>
		    			</td>
		    		</tr>
	    		<tr valign="top">
	    			<td align="left" >公告内容:</td>
	    			<td>
	    				<div id='addEditor'></div>  
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a onclick="return fun_add_announcement()" class="easyui-linkbutton" >&nbsp;&nbsp;提&nbsp;交&nbsp;&nbsp;</a>
	    </div>
	</div>
</div>
<div id="announcement_select_div" class="easyui-window" date-options="iconCls:'icon-save'" closed="true" title="查看公告">
	<table style="width: 640px;height: 530px" >
		<tr>
			<th colspan="2" style="height: 80px;">
				<span id="select_title" style="font-size: 25px;"></span>
			</th>
		</tr>
		<tr height="30px">
			<th width="2%"></th>
			<th align="center">
				<span id="select_promulgator"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span id="select_releaseDate"></span>
			</th>
		</tr>
		<tr valign="top" align="left">
			<th width="2%"></th>
			<th>
				<span id="select_content"></span>
			</th>
		</tr>
	</table>
</div>
</body>
<script type="text/javascript">
	var addEditor = UM.getEditor('addEditor');
	function rowformater(value,row,index){
		<%if(role.equals("1")){%>
			return "<a class='select' onclick='selRow("+row.id+")' href='javascript:void(0)'></a>"+
			   "<a class='delete' onclick='delRow("+row.id+","+index+")' href='javascript:void(0)'></a>";
		<%}else{%>
			return "<a class='select' onclick='selRow("+row.id+")' href='javascript:void(0)'></a>";
		<%}%>
	};
	//新增
	function addRow(){
		$('#announcement_add_div').window('open');
	}
	function fun_add_announcement(){
		var addTitle = $('#add_title').val();
		var addContent = addEditor.getContent();	//内容
		var addDueDate = $("#add_dueDate").datetimebox('getValue');
		
		if(addTitle==""){$.messager.alert('', '请填写标题!', 'info');return ;}
		if(addContent==""){$.messager.alert('', '请填内容!', 'info');return ;}
		if(addDueDate==""){$.messager.alert('', '请选择截止日期!', 'info');return ;}
		
		if(!check_dueDate(addDueDate)){$.messager.alert('', '截止日期小于当前时间!', 'info');return ;}
		
		$.post("announcement.do?method=addAnnouncement",{
				title: addTitle,
				content: addContent,
				promulgator:'<%=agentNumber%>',
				dueDate:addDueDate
			},
			function(data){
				if (data == "200") {
					$('#announcement_table').datagrid('reload');	//更新table
					$('#announcement_add_div').window('close');	//关闭窗体
					$.messager.alert('', '添加成功!', 'info');
					$('#add_title').val('');
					$('#add_dueDate').datetimebox('setValue', '');
					addEditor.setContent('');
				}else{
					$.messager.alert('', '添加失败!', 'error');
				}
		});
	}
	
	function check_dueDate(dueDate){
		var myDate = new Date();
		
		var date = dueDate.split(' ');
	    var yyyy_MM_dd = date[0].split('-');
	    var hh_mm_ss = date[1].split(':');
	    
		var nowYear = myDate.getFullYear();
	    var nowMonth = (myDate.getMonth()+1)<10?"0"+(myDate.getMonth()+1):(myDate.getMonth()+1);
	    var nowDay = (myDate.getDate())<10?"0"+(myDate.getDate()):(myDate.getDate());
	    var nowHours = (myDate.getHours())<10?"0"+(myDate.getHours()):(myDate.getHours());
	    var nowMinutes  = (myDate.getMinutes())<10?"0"+(myDate.getMinutes()):(myDate.getMinutes());
	    var nowSeconds  = (myDate.getSeconds())<10?"0"+(myDate.getSeconds()):(myDate.getSeconds());
	    
	    var nowTime = nowYear + "" + nowMonth + "" + nowDay + "" +nowHours+ "" + nowMinutes + "" + nowSeconds;

	    var dueTime = yyyy_MM_dd[0] + "" + yyyy_MM_dd[1] + "" + yyyy_MM_dd[2] + "" + hh_mm_ss[0] + "" + hh_mm_ss[1] + "" + hh_mm_ss[2];

		if(nowTime > dueTime)
			return false;

		return true;
	}

	//查看
	function selRow(id){
 		$.ajax({ 
             type: "POST", 
             url: "announcement.do?method=selectAnnouncementById&announcementId="+id, 
             dataType: "json", 
             success: function (data) {
                 $('#select_title').html(data.title);
                 $('#select_promulgator').html("发布人:" + data.promulgator);
                 $('#select_releaseDate').html("发布时间:" + data.releaseDate);
                 $('#select_content').html( data.content);
                 $('#announcement_select_div').window('open');
             }
		});
	}
	//删除
	function delRow(id,index){
		$.messager.confirm("操作提示", "您确定要删除吗？", function (data) {
			  if (data) {
				$.ajax({ 
		            type: "POST", 
		            url: "announcement.do?method=deleteAnnouncement&announcementId="+id, 
		            dataType: "json", 
		            success: function () {
		           		$.messager.alert('','删除成功','info');
		           		$('#announcement_table').datagrid('reload');
		            }
				});
			  }
		});
	}
	
	$('#announcement_table').datagrid({
		url:'announcement.do?method=selectAllAnnouncement',
		<%if(role.equals("1")){%>
			toolbar:'#announcement_bar',
		<%}%>
        pageSize:10,//默认选择的分页是每页5行数据
        pageList:[5,10,15,20,25,30],//可以选择的分页集合
        singleSelect:true,//为true时只能选择单行
        sortName:'releaseDate',//当数据表格初始化时以哪一列来排序
        sortOrder:'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。
        remoteSort:false,
        pagination:true,//分页
        fitColumns:true,//允许表格自动缩放，以适应父容器
        rownumbers:true,
        onLoadSuccess:function(data){
    		$('.select').linkbutton({text:'查看',plain:true,iconCls:'icon-search'}); 
        	$('.delete').linkbutton({text:'删除',plain:true,iconCls:'icon-cancel'});
		}  
    });
    
</script>
</html>
