<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>未接来电</title>
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/demo.css">
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.3.2/easyloader.js"></script>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	<link rel="stylesheet" type="text/css" href="css/panel_datagrid_layout.css">
	
	<script src="js/loading/prefixfree.min.js"></script>
    <script src="js/loading/modernizr.js"></script>

<style type="text/css">
.loading{
    background:#FF6100;
    height:5px; 
    position:fixed;
    top:0;
    left:0;
    z-index:99999;
}
.cover{
	background: #fff;
	filter: alpha(opacity=100); /* IE的透明度 */
	opacity: 0.5; /* 透明度 */
	display: none;
	position: absolute;
	top: 0px;
	left: 0px;
	width: 100%;
	height: 100%;
	z-index: 99998; /* 此处的图层要大于页面 */
}

.loader {
  margin: 4em auto;
}
.loader--audioWave {
  width: 3em;
  height: 2em;
  background: linear-gradient(#9b59b6, #9b59b6) 0 50%, linear-gradient(#9b59b6, #9b59b6) 0.625em 50%, linear-gradient(#9b59b6, #9b59b6) 1.25em 50%, linear-gradient(#9b59b6, #9b59b6) 1.875em 50%, linear-gradient(#9b59b6, #9b59b6) 2.5em 50%;
  background-repeat: no-repeat;
  background-size: 0.5em 0.25em, 0.5em 0.25em, 0.5em 0.25em, 0.5em 0.25em, 0.5em 0.25em;
  animation: audioWave 1.5s linear infinite;
  position:absolute;
  top:50%;left:50%;margin-top:-1em;margin-left:-1em;
}
@keyframes audioWave {
  25% {
    background: linear-gradient(#3498db, #3498db) 0 50%, linear-gradient(#9b59b6, #9b59b6) 0.625em 50%, linear-gradient(#9b59b6, #9b59b6) 1.25em 50%, linear-gradient(#9b59b6, #9b59b6) 1.875em 50%, linear-gradient(#9b59b6, #9b59b6) 2.5em 50%;
    background-repeat: no-repeat;
    background-size: 0.5em 2em, 0.5em 0.25em, 0.5em 0.25em, 0.5em 0.25em, 0.5em 0.25em;
  }
  37.5% {
    background: linear-gradient(#9b59b6, #9b59b6) 0 50%, linear-gradient(#3498db, #3498db) 0.625em 50%, linear-gradient(#9b59b6, #9b59b6) 1.25em 50%, linear-gradient(#9b59b6, #9b59b6) 1.875em 50%, linear-gradient(#9b59b6, #9b59b6) 2.5em 50%;
    background-repeat: no-repeat;
    background-size: 0.5em 0.25em, 0.5em 2em, 0.5em 0.25em, 0.5em 0.25em, 0.5em 0.25em;
  }
  50% {
    background: linear-gradient(#9b59b6, #9b59b6) 0 50%, linear-gradient(#9b59b6, #9b59b6) 0.625em 50%, linear-gradient(#3498db, #3498db) 1.25em 50%, linear-gradient(#9b59b6, #9b59b6) 1.875em 50%, linear-gradient(#9b59b6, #9b59b6) 2.5em 50%;
    background-repeat: no-repeat;
    background-size: 0.5em 0.25em, 0.5em 0.25em, 0.5em 2em, 0.5em 0.25em, 0.5em 0.25em;
  }
  62.5% {
    background: linear-gradient(#9b59b6, #9b59b6) 0 50%, linear-gradient(#9b59b6, #9b59b6) 0.625em 50%, linear-gradient(#9b59b6, #9b59b6) 1.25em 50%, linear-gradient(#3498db, #3498db) 1.875em 50%, linear-gradient(#9b59b6, #9b59b6) 2.5em 50%;
    background-repeat: no-repeat;
    background-size: 0.5em 0.25em, 0.5em 0.25em, 0.5em 0.25em, 0.5em 2em, 0.5em 0.25em;
  }
  75% {
    background: linear-gradient(#9b59b6, #9b59b6) 0 50%, linear-gradient(#9b59b6, #9b59b6) 0.625em 50%, linear-gradient(#9b59b6, #9b59b6) 1.25em 50%, linear-gradient(#9b59b6, #9b59b6) 1.875em 50%, linear-gradient(#3498db, #3498db) 2.5em 50%;
    background-repeat: no-repeat;
    background-size: 0.5em 0.25em, 0.5em 0.25em, 0.5em 0.25em, 0.5em 0.25em, 0.5em 2em;
  }
}
.loader--snake {
  width: 1.25em;
  height: 1.25em;
  border-radius: 50%;
  transform: translate(-4.125em);
  box-shadow: 1.375em 0em #debf23, 1.375em 0em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 2.75em 0.29721em #b8b64c, 2.75em -0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 4.125em 0.18368em #92ae75, 4.125em -0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 5.5em -0.18368em #6ca59d, 5.5em 0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 6.875em -0.29721em #469cc6, 6.875em 0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52);
  animation: snake 2s linear infinite;
  position:absolute;
  top:50%;left:50%;margin-top:-1em;margin-left:-1em;
}
@keyframes snake {
  0% {
    box-shadow: 1.375em 0em #debf23, 1.375em 0em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 2.75em 0.29721em #b8b64c, 2.75em -0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 4.125em 0.18368em #92ae75, 4.125em -0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 5.5em -0.18368em #6ca59d, 5.5em 0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 6.875em -0.29721em #469cc6, 6.875em 0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52);
  }
  20% {
    box-shadow: 1.375em 0.29721em #b8b64c, 1.375em -0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 2.75em 0.18368em #92ae75, 2.75em -0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 4.125em -0.18368em #6ca59d, 4.125em 0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 5.5em -0.29721em #469cc6, 5.5em 0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 6.875em 0.0em #debf23, 6.875em 0.0em 0.625em -0.3125em rgba(0, 0, 0, 0.52);
  }
  40% {
    box-shadow: 1.375em 0.18368em #92ae75, 1.375em -0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 2.75em -0.18368em #6ca59d, 2.75em 0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 4.125em -0.29721em #469cc6, 4.125em 0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 5.5em 0.0em #debf23, 5.5em 0.0em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 6.875em 0.29721em #b8b64c, 6.875em -0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52);
  }
  60% {
    box-shadow: 1.375em -0.18368em #6ca59d, 1.375em 0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 2.75em -0.29721em #469cc6, 2.75em 0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 4.125em 0.0em #debf23, 4.125em 0.0em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 5.5em 0.29721em #b8b64c, 5.5em -0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 6.875em 0.18368em #92ae75, 6.875em -0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52);
  }
  80% {
    box-shadow: 1.375em -0.29721em #469cc6, 1.375em 0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 2.75em 0.0em #debf23, 2.75em 0.0em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 4.125em 0.29721em #b8b64c, 4.125em -0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 5.5em 0.18368em #92ae75, 5.5em -0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 6.875em -0.18368em #6ca59d, 6.875em 0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52);
  }
  100% {
    box-shadow: 1.375em 0.0em #debf23, 1.375em 0.0em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 2.75em 0.29721em #b8b64c, 2.75em -0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 4.125em 0.18368em #92ae75, 4.125em -0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 5.5em -0.18368em #6ca59d, 5.5em 0.18368em 0.625em -0.3125em rgba(0, 0, 0, 0.52), 6.875em -0.29721em #469cc6, 6.875em 0.29721em 0.625em -0.3125em rgba(0, 0, 0, 0.52);
  }
}
.loader--spinningDisc {
  border: solid 0.5em #9b59b6;
  border-right-color: transparent;
  border-left-color: transparent;
  padding: 0.5em;
  width: 2em;
  height: 2em;
  border-radius: 50%;
  background: #3498db;
  background-clip: content-box;
  animation: spinDisc 1.5s linear infinite;
  position:absolute;
  top:50%;left:50%;margin-top:-1em;margin-left:-1em;
}
@keyframes spinDisc {
  50% {
    border-top-color: #3498db;
    border-bottom-color: #3498db;
    background-color: #2ecc71;
  }
  100% {
    transform: rotate(1turn);
  }
}
.loader--glisteningWindow {
  width: 0.25em;
  height: 0.25em;
  box-shadow: 0.70711em 0.70711em 0 0em #2ecc71, -0.70711em 0.70711em 0 0.17678em #9b59b6, -0.70711em -0.70711em 0 0.25em #3498db, 0.70711em -0.70711em 0 0.17678em #f1c40f;
  animation: gw 1s ease-in-out infinite, rot 2.8s linear infinite;
  position:absolute;
  top:50%;left:50%;margin-top:-1em;margin-left:-1em;
}
@keyframes rot {
  to {
    transform: rotate(360deg);
  }
}
@keyframes gw {
  0% {
    box-shadow: 0.70711em 0.70711em 0 0.125em #2ecc71, -0.70711em 0.70711em 0 0.39017em #9b59b6, -0.70711em -0.70711em 0 0.5em #3498db, 0.70711em -0.70711em 0 0.39017em #f1c40f;
  }
  25% {
    box-shadow: 0.70711em 0.70711em 0 0.39017em #2ecc71, -0.70711em 0.70711em 0 0.5em #9b59b6, -0.70711em -0.70711em 0 0.39017em #3498db, 0.70711em -0.70711em 0 0.125em #f1c40f;
  }
  50% {
    box-shadow: 0.70711em 0.70711em 0 0.5em #2ecc71, -0.70711em 0.70711em 0 0.39017em #9b59b6, -0.70711em -0.70711em 0 0.125em #3498db, 0.70711em -0.70711em 0 0.39017em #f1c40f;
  }
  75% {
    box-shadow: 0.70711em 0.70711em 0 0.39017em #2ecc71, -0.70711em 0.70711em 0 0.125em #9b59b6, -0.70711em -0.70711em 0 0.39017em #3498db, 0.70711em -0.70711em 0 0.5em #f1c40f;
  }
  100% {
    box-shadow: 0.70711em 0.70711em 0 0.125em #2ecc71, -0.70711em 0.70711em 0 0.39017em #9b59b6, -0.70711em -0.70711em 0 0.5em #3498db, 0.70711em -0.70711em 0 0.39017em #f1c40f;
  }
}
.loader--circularSquare {
  width: 0;
  height: 0;
  box-shadow: -0.625em -0.625em 0 0.625em #9b59b6, 0.625em -0.625em 0 0.625em #9b59b6, -0.625em 0.625em 0 0.625em #9b59b6, 0.625em 0.625em 0 0.625em #9b59b6;
  animation: circSquare 1.5s ease-in-out infinite;
  position:absolute;
  top:50%;left:50%;margin-top:-1em;margin-left:-1em;
}
@keyframes circSquare {
  50% {
    width: 1.25em;
    height: 1.25em;
    border-radius: 50%;
    transform: rotate(0.5turn);
    box-shadow: -2.5em 0 0 #2ecc71, 2.5em 0 0 #e74c3c, -2.5em 0 0 #3498db, 2.5em 0 0 #f1c40f;
  }
  80%, 100% {
    transform: rotate(1turn);
  }
}
</style>

  </head>
  
   <body onload="hideOverlay()">
  <div class="loading"></div>
  <%-- 遮罩层 --%>
  <div class="cover"><div class='loader loader--spinningDisc'></div></div>
  
  	 <script type="text/javascript">
    $('.loading').animate({'width':'33%'},50);  //第一个进度节点
    // fadeTo第一个参数为速度，第二个为透明度
    // 多重方式控制透明度，保证兼容性，但也带来修改麻烦的问题
    $(".cover").fadeTo(0, 1);
	</script>
  
  	<%-- 分配座席的弹出层 --%>
	<div id="allocation" class="easyui-dialog" title="分配座席" data-options="buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						setNewAccount();
					}
				},{
					text:'取消',
					handler:function(){
						$('#allocation').dialog('close');
					}
				}]" style="width:300px;height:auto;padding:10px">
		<table style="width: 100%" >
			<tr>
				<td>分配座席：</td>
				<td><input id="account_seat" style="width:146px"/><input id="misscall_id" class="easyui-textbox" style="display:none"/></td>
			</tr>
		</table>
	</div>
	
	<script type="text/javascript">
		$('.loading').animate({'width':'55%'},50);  //第二个节点
	</script>
	
    
		<div  class="easyui-panel" title="未接来电" style="padding: 10px">
			<table style="width: 100%" >
			<tr>
				<td width="60px">开始时间：</td>
				<td style="width: 15%"><input id="beginTimeForSearch" editable="false" class="easyui-datetimebox" style="width:150px"></input></td>
				<td width="60px">结束时间：</td>
				<td style="width: 15%"><input id="endTimeForSearch" editable="false" class="easyui-datetimebox" style="width:150px"></input></td>
				<td width="60px">主叫号码：</td>
				<td style="width: 15%"><input id="phoneNumeberForSearch" class="easyui-numberbox" style="width:150px"></input></td>
				<td width="60px">经办人：</td>
				<td style="width: 15%"><input id="agentNumberForSearch" class="easyui-textbox" style="width:150px"></input></td>
				
				<td width="60px" style="display:none">被叫号码：</td>
				<td style="width: 15%,display:none"><input id="phoneNumeberForSearch_called" class="easyui-numberbox" style="display:none"></input></td>
				
				<td><a id="search_misscall_btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a></td>
			</tr>
		</table>
		<table id="misscall" title="搜索条件"
		data-options="collapsible:true,url:'misscall.do?method=findAll'">
			<thead>
			<tr>
				<th data-options="field:'id',width:80,hidden:true">ID</th>
				<th data-options="field:'personToDisplay',width:80">经办人</th>
				<th data-options="field:'answerTime',width:125,align:'center'">来电时间</th>
				<th data-options="field:'caller',width:100,align:'right'">主叫号码</th>
				<th data-options="field:'holdTime',width:100,align:'right'">等待时长（秒）</th>
				<th data-options="field:'answer',width:100,align:'right',hidden:true">被叫号码</th>
				<th data-options="field:'ringSec',width:100,align:'right'">拨打次数</th>
				<th data-options="field:'zuijin',width:100,align:'right'">最近拨打时间</th>
				<th data-options="field:'control',width:60,align:'center',formatter:rowformater">操作</th>
			</tr>
			</thead>
		</table>
		</div>
	<script type="text/javascript">
    	$('.loading').animate({'width':'78%'},50);  //第三个节点
	</script>
	<script type="text/javascript">
    	$('.loading').animate({'width':'100%'},50);  //第四个节点
	</script>
	<script type="text/javascript">
	    $(document).ready(function(){
	        $('.loading').fadeOut();
	    });
	    
	    /* 隐藏覆盖层 */
	    function hideOverlay() {
	    	$(".cover").fadeOut(200);
	    };
	</script>
	
	<%--页面控制相关 --%>
	<script type="text/javascript">
	$(function(){
		$('#allocation').dialog({
	           closed: true,   
	           cache: false,
	           modal: true
	       });
		$('#misscall').datagrid({
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
				$('#misscall').datagrid('hideColumn','control');
			}
	    } 
	    });
		});

		function rowformater(value,row,index)
		{
			var btn = '<a href="javascript:void(0)" class="editcls1" onclick="editRow(\''+row.id+'\',\''+row.personToDisplay+'\')">'+'</a>';
			return btn;
		};
	</script>
  </body>
</html>
