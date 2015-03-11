<%@ page language="java" import="java.util.*,com.dianfeng.entity.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
KnowledgeBase knowledgeBase = (KnowledgeBase)request.getAttribute("knowledgeBase");
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String role  = request.getParameter("role")==null?"":request.getParameter("role").toString();
String msg = request.getParameter("msg")==null?"":request.getParameter("msg").toString();
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>知识库</title>
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
  
<body onload="load()">
 <!-- table  tool -->
<div id="knowledge_base_tool" style="height:auto">
	<a href="javascript:addKnowledgebase()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" >添加</a>
	<%if(role.equals("1")){%>
		<a href="javascript:editKnowledgebase()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
		<a href="javascript:removeKnowledgebase()" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
		<a href="javascript:checkimport()" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">导入Excel表格</a>
		<a href="javascript:exportModelExcel()" class="easyui-linkbutton">Excel表格模板</a>
	<%}%>
</div>

<div class="easyui-panel" title="知识库" >
	<div id="cc" class="easyui-layout" style="width:100%;height:600px;">  
	    <div region="west" split="true" style="width:200px;">
	    	 <ul id="knowledge_base_tree" class="easyui-tree" data-options="url:'knowledgeBase.do?method=loadTree',method:'post'"></ul>
	    </div>  
	    <div region="center" split="true" style="width:80%;margin-left: 0px;margin-top: 10px;">
   			 <div align="left" id="div_select_tab">
				<font size="2">关键字:</font>
				<input type="text" id="select_key_word" class="easyui-textbox" style="width: 500px"/>
				<a id="select_knowledge_base"  class="easyui-linkbutton">&nbsp;查&nbsp;&nbsp;询&nbsp;</a>
			</div>
			
			<div align="center" class="div_border" id="select_div_pair" style="width: 100%">
				<table id="knowledge_base_table"  style="width:100%" data-options="onDblClickRow: fun_open_data,toolbar: '#knowledge_base_tool',
				rownumbers:true,singleSelect:true,pagination:true,fitColumns:true">
					<thead>
						<tr>
							<th data-options="field:'title'" width="45%" align="center">标题</th>
							<th data-options="field:'keyWordChi'" width="30%" align="center">关键字
								<input type="hidden" data-options="field:'keyWordEng'"/>
							</th>
							<th data-options="field:'addTime'" width="23%" align="center">发布日期</th>
						</tr>
					</thead>
				</table>
			</div>
	    </div>  
	</div> 
<!--导入Excel表格-->
<div id="knowledgeBaseImport"  title="导入Excel表格"  style="position:absolute;width: 450px; z-index:100; left: 650px; top: 85px">
	<form id="knowledgeBase_From" action="knowledgeBase/save.do" method="post" enctype="multipart/form-data">
		<div> 
	      	<input id="knowledgeBaseFile" name='knowledgeBaseFile' type="file" style="width: 270px;height: 20px;">
	      	<input type="hidden" value="<%=role %>" id='role' name='role'>
	      	<input type="submit" value="提交" style="width: 70px;height: 23px;">
	    </div> 
	</form>
</div> 
<!-- 新增知识库 window -->		
<div id="knowledgebase_add_div" class="easyui-window" date-options="iconCls:'icon-save'" closed="true" title="新增知识库" style="width:930px;height: 550px;">
	<div style="padding:10px 60px 20px 60px">
    <form id="add_form" action="knowledgeBase.do?method=addKnowledgeBase" method="post">
    	<table cellpadding="5" style="width: 100%">
    		<tr>
    			<td align="right" width="12%">所属类型:</td>
    			<td>
    				<input id="add_parent_id" name="parentId" type="hidden"/>
    				<input id="add_parent_type_txt" name="parentType" readonly="readonly" class="easyui-textbox" style="width:200px"/>
    			</td>
    			<td align="right">类型:</td>
    			<td>
    				<input id="add_type_txt" name="myType" class="easyui-textbox" style="width:200px"/>
    			</td>
    		</tr>
    		<tr>
    			<td align="right">标题:</td>
    			<td><input id="add_title_txt" name="title" class="easyui-textbox" type="text"  style="width:200px"/></td>
    			<td align="right">关键字:</td>
    			<td><input id="add_keyWord_txt"  name="keyWordChi" class="easyui-textbox" type="text"  style="width:200px"/></td>
    		</tr>
    		<tr>
    			<td colspan="4" >
    				<div id='addEditor'></div>  
    			</td>
    		</tr>
    	</table>
    </form>
    <div style="text-align:center;padding:5px">
    	<a onclick="return fun_add_knowledgebase()" class="easyui-linkbutton" >&nbsp;&nbsp;提&nbsp;交&nbsp;&nbsp;</a>
    </div>
    </div>
</div>
<!-- 修改知识库 window -->	
<div id="knowledgebase_edit_div" class="easyui-window" date-options="iconCls:'icon-edit'" closed="true" title="修改知识库" style="width:930px;height: 550px;">
    <div style="padding:10px 60px 20px 60px">
    <form id="edit_form" action="knowledgeBase.do?method=updateKnowledgeBase" method="post">
    	<table cellpadding="5"  width="100%">
    		<tr>
    			<td align="right" width="12%">所属类型:</td>
    			<td>
    				<input id="edit_parentId" type="hidden" name="parentId"/>
    				<input id="edit_id" type="hidden" name="id"/>
    				<input id="edit_parent_type_txt" name="parentType" readonly="readonly" class="easyui-textbox" style="width:200px"/>
    			</td>
    			<td align="right">类型:</td>
    			<td>
    				<input id="edit_type_txt" name="myType" class="easyui-textbox" style="width:200px"/>
    			</td>
    		</tr>
    		<tr>
    			<td align="right">标题:</td>
    			<td><input id="edit_title_txt" name="title" class="easyui-textbox" style="width:200px"/></td>
    			<td align="right">关键字:</td>
    			<td><input id="edit_keyWord_txt" name="keyWordChi" class="easyui-textbox"  style="width:200px"/></td>
    		</tr>
    		<tr>
    			<td colspan="4" >
    				<div id='editEditor'></div>  
    			</td>
    		</tr>
    	</table>
    </form>
    <div style="text-align:center;padding:5px">
    	<a onclick="return fun_update_knowledgebase()" class="easyui-linkbutton" >&nbsp;&nbsp;提&nbsp;交&nbsp;&nbsp;</a>
    </div>
    </div>
</div>
<!-- 查看知识库 window -->	
<div id="knowledgebase_show_div" class="easyui-window" date-options="iconCls:'icon-edit'" closed="true" title="查看知识库" style="width:930px;height: 550px;">
    <div style="padding:10px 60px 20px 60px">
    	<table cellpadding="5"  width="100%">
    		<tr>
    			<td align="right" width="12%">所属类型:</td>
    			<td>
    				<input id="show_parent_type_txt" readonly="readonly" class="easyui-textbox" style="width:200px"/>
    			</td>
    			<td align="right">类型:</td>
    			<td>
    				<input id="show_type_txt"  class="easyui-textbox" style="width:200px" readonly="readonly"/>
    			</td>
    		</tr>
    		<tr>
    			<td align="right">标题:</td>
    			<td><input id="show_title_txt" class="easyui-textbox" style="width:200px" readonly="readonly"/></td>
    			<td align="right">关键字:</td>
    			<td><input id="show_keyWord_txt"  class="easyui-textbox"  style="width:200px" readonly="readonly"/></td>
    		</tr>
    		<tr>
    			<td align="right">创建时间:</td>
    			<td><input id="show_addTime_txt"  class="easyui-textbox"  style="width:200px" readonly="readonly"/></td>
    			<td align="right">id:</td>
    			<td><input id="show_id_txt"  class="easyui-textbox"  style="width:200px" readonly="readonly"/></td>
    		</tr>
    		<tr>
    			<td colspan="4" >
    				<div id='showEditor' ></div>
    			</td>
    		</tr>
    	</table>
	    <div style="text-align:center;padding:5px">
	    	<a onclick="fun_show_close()" class="easyui-linkbutton" >&nbsp;&nbsp;关&nbsp;闭&nbsp;&nbsp;</a>
	    </div>
    </div>
</div>
</div>
</body>
<script type="text/javascript">
var addEditor = UM.getEditor('addEditor');
var editEditor = UM.getEditor('editEditor');
var showEditor = UM.getEditor('showEditor')
var addparentId;
var addparentType;
function load(){
	$('#knowledge_base_tree').tree({
		onClick: function(node){
			var id = node.id;
			addparentId = node.id;
			addparentType = node.text;
		    var urlPath = "knowledgeBase.do?method=selectKnowledgeBaseById&id="+id;
		    $('#knowledge_base_table').datagrid({
				url:urlPath,
				toolbar:'#knowledge_base_tool',
				pageSize : 10,//默认选择的分页是每页5行数据
				pageList : [ 5, 10, 15, 20, 25, 30 ],//可以选择的分页集合
				singleSelect:true,//为true时只能选择单行
				remoteSort : false,
				pagination : true,//分页
				fitColumns:true,//允许表格自动缩放，以适应父容器
				rownumbers:true
		    });
		}
	});
	$('#knowledge_base_tree').tree({url:'knowledgeBase.do?method=loadTree',method:'POST'});
	$("#knowledgeBaseImport" ).css("display", "none");
	$('#knowledge_base_table').datagrid().datagrid('getPager');
}
	//给查询按钮添加点击事件
jQuery("#select_knowledge_base").click(function (){
	var select_key_word = $("#select_key_word").val();
 	var urlPath = "knowledgeBase.do?method=selectKnowledgeBaseByKeyWord&keywordChi="+encodeURIComponent(select_key_word);
	$('#knowledge_base_table').datagrid({singleSelect:true,url: urlPath});  
})
//提交文件
function export_submit(){
	if($('#file').filebox('getValue')==''){$.messager.alert('','请选择文件','error');return;}
	if(!/\.(xls|xlsx)$/.test($('#file').filebox('getValue'))){$.messager.alert('','格式错误,支持Excel表格格式','error');return ;}
}
//查看
function fun_open_data(rowIndex,data){
	$.ajax({
		url: "knowledgeBase.do?method=findKnowledgeBaseById&id="+data.id,
		type : "POST",
		dataType: "json", 
		success: function (returnJson) {
			showEditor.setContent(data.content);
			$('#show_parent_type_txt').val(data.parentType);
			$('#show_type_txt').val(data.myType);
			$('#show_title_txt').val(data.title);
			$('#show_keyWord_txt').val(data.keyWordChi);
			$('#show_addTime_txt').val(data.addTime);
			$('#show_id_txt').val(data.id);
			$('#knowledgebase_show_div').window('open');
		}
	});
	showEditor.setDisabled(); 	//禁用
}
	
function fun_show_close(){$('#knowledgebase_show_div').window('close');}
	
//添加
function fun_add_knowledgebase(){
	var addContent = addEditor.getContent();
	var add_parent_id = $('#add_parent_id').val();
	var add_parent_type_txt = $('#add_parent_type_txt').val();
	var add_type_txt = $('#add_type_txt').val();
	var add_title_txt = $('#add_title_txt').val();
	var add_keyWord_txt = $('#add_keyWord_txt').val();
	if(add_type_txt==""){$.messager.alert('', '请选择类型!', 'info');return ;}
	if(add_title_txt==""){$.messager.alert('', '请填写标题!', 'info');return ;}
	if(add_keyWord_txt==""){$.messager.alert('', '请填写关键字!', 'info');return ;}
	$.post("knowledgeBase.do?method=addKnowledgeBase",
	{ 	
		content: addContent,
		parentId:add_parent_id,
		parentType:add_parent_type_txt,
		myType:add_type_txt,
		title:add_title_txt,
		keyWordChi:add_keyWord_txt
	},
	function(data){
		if (data == "200") {
			$.messager.alert('', '添加成功!', 'info');
			$('#knowledge_base_table').datagrid('reload');	//更新table
			$('#knowledge_base_tree').tree({
				 url:'knowledgeBase.do?method=loadTree',method:'POST'}
			);
			$('#knowledgebase_add_div').window('close');	//关闭窗体
		}else{
			$.messager.alert('', '添加失败!', 'error');
		}
	});
}
//修改
function fun_update_knowledgebase(){
	var editContent = editEditor.getContent();
	var edit_id = $('#edit_id').val();
	var edit_parent_id = $('#edit_parent_id').val();
	var edit_parent_type_txt = $('#edit_parent_type_txt').val();
	var edit_type_txt = $('#edit_type_txt').val();
	var edit_title_txt = $('#edit_title_txt').val();
	var edit_keyWord_txt = $('#edit_keyWord_txt').val();
	if(edit_type_txt==""){$.messager.alert('', '请填写类型!', 'info');return ;}
	if(edit_title_txt==""){$.messager.alert('', '请填写标题!', 'info');return ;}
	if(edit_keyWord_txt==""){$.messager.alert('', '请填写关键字!', 'info');return ;}
	$.post("knowledgeBase.do?method=updateKnowledgeBase",
	{ 	
		id:edit_id,
		content: editContent,
		parentId:edit_parent_id,
		parentType:edit_parent_type_txt,
		myType:edit_type_txt,
		title:edit_title_txt,
		keyWordChi:edit_keyWord_txt
	},
	function(data){
		if (data == "200") {
			$.messager.alert('', '修改成功!', 'info');
			$('#knowledge_base_table').datagrid('reload');	//更新table
			$('#knowledge_base_tree').tree({
				 url:'knowledgeBase.do?method=loadTree',method:'POST'}
			);
			$('#knowledgebase_edit_div').window('close');	//关闭窗体
		}else{
			$.messager.alert('', '修改失败!', 'error');
		}
	});
}
	
//删除
function removeKnowledgebase() {
	var row = $('#knowledge_base_table').datagrid('getSelected');
	$.messager.confirm("操作提示", "您确定要执行删除吗？", function (data) {
		if (data) {
			$.ajax({
		    	type : "POST",
		    	url : 'knowledgeBase.do?method=delKnowledgebaseById&id='+row.id,
		    	success : function(returnData) {
					if (returnData == "200") {
						$.messager.alert('', '删除成功!', 'info');
						$('#knowledge_base_tree').tree('reload');
						$('#knowledge_base_table').datagrid('reload');
					}else{
						$.messager.alert('', '删除失败!', 'error');
					}
		    	}
		    })
		}
	});
}
	
//打开新增
function addKnowledgebase(){
	if(addparentId == undefined){
		$.messager.alert('', '请选择所属类型!', 'error');
		return;
	}
	$('#add_type_txt').val('');
	$('#add_title_txt').val('');
	$('#add_keyWord_txt').val('');
	$('#add_parent_id').val(addparentId);
	$('#add_parent_type_txt').val(addparentType);
	addEditor.setContent('');
	$('#knowledgebase_add_div').window('open');
}
//打开编辑
function editKnowledgebase(){
	var row = $('#knowledge_base_table').datagrid('getSelected');
	if(row){
	  	$.ajax({
			type : "get",
			url : 'knowledgeBase.do?method=findKnowledgeBaseById&id='+row.id,
			dataType : 'json',
			method : 'Post',
			success : function(data) {
				$('#edit_parent_type_txt').val(data.parentType);
				$('#edit_type_txt').val(data.myType);
				$('#edit_title_txt').val(data.title);
				$('#edit_keyWord_txt').val(data.keyWordChi);
				editEditor.setContent(data.content);
				$('#edit_parentId').val(data.parentId);
				$('#edit_id').val(data.id);
				$('#knowledgebase_edit_div').window('open');
			}
		});
	}
}
//导入
var display = 0 ;
function checkimport(){
	display = display + 1;
	if(display%2==0)
		$("#knowledgeBaseImport" ).css("display", "none");
	else
		$("#knowledgeBaseImport" ).css("display", "block");
} 

function exportModelExcel(){
	exportApp.exportModelExcel();
}
if(<%=!msg.equals("")%>){
	if(<%=msg.equals("1")%>){
		$.messager.alert('', '上传成功!', 'info');
	}else if(<%=msg.equals("2")%>){
		$.messager.alert('', '上传数据出错!', 'error');
	}else if(<%=msg.equals("3")%>){
		$.messager.alert('', '上传数据为空!', 'error');
	}else if(<%=msg.equals("4")%>){
		$.messager.alert('', '上传文件错误!', 'error');
	}
}
</script>
</html>
