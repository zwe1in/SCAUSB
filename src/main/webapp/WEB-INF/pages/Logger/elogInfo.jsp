<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>设备操作日志</title>
	<link rel="stylesheet" type="text/css" href="../resources/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../resources/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../resources/css/demo/demo.css">
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../resources/js/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<!-- 日志信息列表 -->
	<table id="list"></table>
</body>

<script type="text/javascript">
$(function() {
	$('#list').datagrid({
		url : 'elogList',
		columns : [ [ {
			//对象
			field : "id",
			//标题
			title : "编号",
			width : 50,
			align : "center"
		},{
			//对象
			field : "e_id",
			//标题
			title : "设备id",
			width : 50,
			align : "center"
		},{
			//对象
			field : "content",
			//标题
			title : "日志内容",
			width : 300,
			align : "center"
		}, {
			//对象
			field : "time",
			//标题
			title : "操作日期",
			width : 200,
			align : "center"
		} ] ],
		//分页显示
		pagination : true
	});
});
</script>
</html>