<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>日志信息</title>
	<link rel="stylesheet" href="../resources/layui/css/layui.css">
	<script src="../resources/layui/layui.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
</head>
<body>
	<!-- 日志信息列表 -->
	<table id="list"></table>
</body>

<script type="text/html" id="toolbar">
  <div class="layui-btn-container">
    <span>用户日志列表</span>
  </div>
</script>

<script type="text/javascript">
$(function() {
	layui.use('table',function(){
		var table = layui.table;
		table.render({
		    elem: '#list'
		    ,toolbar : 'toolbar'
		    ,even: true //开启隔行背景
		    ,width: 900
		    ,url: 'logList' //数据接口
		    ,page: true //开启分页
		    ,limits: [10,20,30]
		    ,cols: [[ //表头
		      {field: 'id', title: '编号', width:80, sort: true, fixed: 'left'}
		      ,{field: 'content', title: '内容', width:400}
		      ,{field: 'author', title: '操作者', width:200} 
		      ,{field: 'date', title: '日期', width: 200}
	
		    ]]
		  });
	});
});
</script>
</html>