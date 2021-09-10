<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户权限分配</title>
	<link rel="stylesheet" href="../resources/layui/css/layui.css">
	<script src="../resources/layui/layui.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
</head>
<body>
	<!-- 用户信息列表 -->
	<table id="list"></table>

</body>

<!-- 工具条 -->

<script type="text/html" id="toolbar">
  <div class="layui-btn-container">
  </div>
</script>

<script type="text/html" id="checkboxTpl1">
  <input type="checkbox" name="log" value="{{d.group_id}}" title="可用" lay-filter="lockDemo" {{ d.log == 1 ? 'checked' : '' }}>
</script>

<script type="text/html" id="checkboxTpl2">
  <input type="checkbox" name="modify" value="{{d.group_id}}" title="可用" lay-filter="lockDemo" {{ d.modify == 1 ? 'checked' : '' }}>
</script>

<script type="text/html" id="checkboxTpl3">
  <input type="checkbox" name="equipment" value="{{d.group_id}}" title="可用" lay-filter="lockDemo" {{ d.equipment == 1 ? 'checked' : '' }}>
</script>

<script type="text/html" id="checkboxTpl4">
  <input type="checkbox" name="data" value="{{d.group_id}}" title="可用" lay-filter="lockDemo" {{ d.data == 1 ? 'checked' : '' }}>
</script>

<script type="text/html" id="checkboxTpl5">
  <input type="checkbox" name="efficiency" value="{{d.group_id}}" title="可用" lay-filter="lockDemo" {{ d.efficiency == 1 ? 'checked' : '' }}>
</script>

<script type="text/javascript">
	var accountant_name = '${user_name}';
	var company_id = localStorage.getItem('company_id');
	var group_id = localStorage.getItem('group_id');
	$(function(){
		layui.use(['table','layer','form'], function(){
			  var table = layui.table;	
			  var layer = layui.layer;
			  var form = layui.form;
			  //第一个实例
			  table.render({
			    elem: '#list'
			    ,toolbar : '#toolbar'
			    ,even: true //开启隔行背景
			    ,width: 1500
			    ,url: '../Power/list?company_id='+company_id//数据接口
			    ,page: true //开启分页
			    ,limits: [10,20,30]
			    ,cols: [[ //表头
			      {field: 'group_id', title: '权限', width: 200, templet:function(d){return authorLevel(d.group_id);}}
			      ,{field:'log', title:'日志查看', width:150, templet: '#checkboxTpl1', unresize: true}
			      ,{field:'modify', title:'设备修改', width:150, templet: '#checkboxTpl2', unresize: true}
			      ,{field:'equipment', title:'设备查看', width:150, templet: '#checkboxTpl3', unresize: true}
			      ,{field:'data', title:'设备数据查看', width:150, templet: '#checkboxTpl4', unresize: true}
			      ,{field:'efficiency', title:'设备效率查看', width:150, templet: '#checkboxTpl5', unresize: true}
			    ]]
			  });
			  
			  //监听开关操作
			  form.on('checkbox(lockDemo)', function(obj){
			    var type = this.name;
			    var status = obj.elem.checked?1:0;
				var status_str = obj.elem.checked?'授权':'撤销';
				$.ajax({
					url : '../Power/switch',
					 data : {'group_id': obj.value,'type':type,'status': status,'company_id':company_id},
					 contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
					 type :'POST',
					 dataType : 'json',
					 success : function(json){
						 if(json.success){
							 	layer.tips(obj.value + ' ' + status_str +'成功',obj.othis);
							}else{
								layer.tips(obj.value + ' ' + status_str +'失败',obj.othis);
							}
						 }
				});
			  });
			  table.on('toolbar',function(obj){
				  var event = obj.event;
			  });
			  
			});
	});

	function authorLevel(id){
		switch(parseInt(id)){
			case 1 : return '企业级管理员';
			case 2 : return '区域级管理员';
			case 3 : return '市级管理员';
			case 4 : return '场级管理员';
		}
	}
</script>
</html>