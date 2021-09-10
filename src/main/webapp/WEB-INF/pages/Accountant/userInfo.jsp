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
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
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
		layui.use(['table','layer'], function(){
			  var table = layui.table;	
			  var layer = layui.layer;
			  //第一个实例
			  table.render({
			    elem: '#list'
			    ,toolbar : '#toolbar'
			    ,even: true //开启隔行背景
			    ,width: 1500
			    ,url: 'list?company_id='+company_id+'&group_id='+group_id //数据接口
			    ,page: true //开启分页
			    ,limits: [10,20,30]
			    ,cols: [[ //表头
			      {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
			      ,{field: 'user_name', title: '用户名', width:100}
			      ,{field: 'phone', title: '联系电话', width:150}
			      ,{field: 'authorization_code', title: '授权码', width:100} 
			      ,{field: 'sex', title: '性别', width: 100,templet:function(d){return d.sex == '1'?'男':'女';}}
			      ,{field: 'email', title: '邮箱', width: 200}
			      ,{field: 'address', title: '地址', width: 200}
			      ,{field: 'birth', title: '出生日期', width: 200}
			      ,{field: 'group_id', title: '权限', width: 200, templet:function(d){return authorLevel(d.group_id);}}
			      ,{fixed: 'right', width:150, align:'center', toolbar: '#bar'}
			    ]]
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
						  content : '../System/addUser?company_id='+company_id
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
						  content : '../System/edit?user_name='+data.user_name+'&accouant_name='+accountant_name
					  })
				  }else if(event == 'del'){
					  var id = data.id;
					  layer.confirm('确定要删除该用户吗?',function(index){
						  $.ajax({
								url : 'delete',
								data : {'id': id,'account_name':accountant_name},
								contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
								type :'POST',
								dataType : 'json',
								success : function(json){
									if(json.success){
										obj.del();
										layer.alert('删除成功',{
											title : '提示',
											icon : 1
										});
									}else{
										layer.alert('删除失败，请重试',{
											title : '提示',
											icon : 2
										});
									}
								}
						  });
						  layer.close(index);
					  });
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