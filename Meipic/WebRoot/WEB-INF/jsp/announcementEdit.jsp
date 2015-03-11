<%@ page language="java" import="java.util.*,com.dianfeng.entity.Announcement" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Announcement announcement = (Announcement)(request.getAttribute("announcement"));//操作员
%>

<!DOCTYPE>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查看公告</title>
    
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
<table style="width: 800px;margin-left: 115px" >
	<tr>
		<th colspan="2" style="height: 80px;font-size: 28px" align="center">
			<span id="select_title" style="font-size: 28px">${announcement.title}</span>
		</th>
	</tr>
	<tr style="height: 50px">
		
		<th align="center" colspan="2">
			<span id="select_promulgator" style="font-size: 16px">${announcement.promulgator}</span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span id="select_releaseDate" style="font-size: 16px">${announcement.releaseDate}</span>
		</th>
	</tr>
	<tr valign="top" align="left">
	
		<th>
			<span id="select_content">${announcement.content}</span>
		</th>
	</tr>
</table>
</body>
</html>
