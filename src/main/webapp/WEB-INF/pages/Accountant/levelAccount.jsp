<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>客户信息查询</title>
	<link rel="stylesheet" href="../resources/layui/css/layui.css">
	<script src="../resources/layui/layui.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
</head>
<body>
	<!-- 用户信息列表 -->
	<table id="list"></table>

</body>

<!-- 工具条 -->
<script type="text/html" id="bar">
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
</script>

<script type="text/html" id="switchTpl">
  <input type="checkbox" name="status" value="{{d.id}}" lay-skin="switch" lay-text="开启|关闭" lay-filter="status" {{ d.message == 1 ? 'checked' : '' }}>
</script>

<script type="text/html" id="toolbar">
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-sm" lay-event="add">添加新用户</button>
  </div>
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
			    ,url: '../Accountant/list?company_id='+company_id+'&group_id='+group_id //数据接口
			    ,page: true //开启分页
			    ,limits: [10,20,30]
			    ,cols: [[ //表头
			      {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
			      ,{field: 'user_name', title: '用户名', width:100}
			      ,{field: 'phone', title: '联系电话', width:200}
			      ,{field: 'email', title: '邮箱', width: 200}
			      ,{field: 'area', title: '区域级', width: 150}
			      ,{field: 'town', title: '市级', width: 150}
			      ,{field: 'house', title: '舍级', width: 150}
			      ,{field: 'group_id', title: '权限', width: 200, templet:function(d){return authorLevel(d.group_id);}}
			      ,{field:'message', title:'短信消息', width:100, templet: '#switchTpl', unresize: true}
			      ,{title: '操作',fixed: 'right', width:150, align:'center', toolbar: '#bar'}
			    ]]
			  });
			//监听开关操作
				form.on('switch(status)', function(obj){
					var message = obj.elem.checked?1:0;
					var status_str = obj.elem.checked?'开启':'关闭';
					$.ajax({
						url : '../Accountant/message',
						 data : {'id': obj.value,'message': message,'company_id':company_id},
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
					}) 
				});
			  table.on('toolbar',function(obj){
				  var event = obj.event;
				  if(event == 'add'){
					  layer.open({
						  type : 2,
						  anim: 1,
						  area: ['700px','450px'],
						  title : '新用户添加',
						  fixed: false,
						  content : '../System/addUser?company_id='+company_id,
						  end : function(){
							  location.reload();
						  }
					  })
				  }
			  })
			  
			  table.on('tool', function(obj){
				  var data = obj.data;
				  var event = obj.event;
				  if(event == 'edit'){
					  layer.open({
						  type : 2,
						  anim: 1,
						  area: ['700px','450px'],
						  title : '用户信息修改',
						  fixed: false,
						  content : '../System/levelEdit?user_name='+data.user_name
					  })
				  }
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