<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>猪舍管理</title>
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
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑舍</a>
  <a class="layui-btn layui-btn-xs" lay-event="unit">查看舍单元</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除舍</a>
</script>

<script type="text/html" id="toolbar">
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-sm" lay-event="add">添加新舍</button>
  </div>
</script>

<script type="text/javascript">
	var user_name = '${user_name}';
	var group_id = localStorage.getItem('group_id');
	var company_id = localStorage.getItem('company_id');
	$(function(){
		layui.use(['table','layer'], function(){
			  var table = layui.table;	
			  var layer = layui.layer;
			  //第一个实例
			  table.render({
			    elem: '#list'
			    ,toolbar : '#toolbar'
			    ,even: true //开启隔行背景
			    ,width: 1360
			    ,url: '../House/list?company_id='+company_id+'&group_id='+group_id+'&user_name='+user_name //数据接口
			    ,page: true //开启分页
			    ,limits: [10,20,30]
			    ,cols: [[ //表头
			      {field: 'name', title: '猪舍名称', width:150, sort: true, fixed: 'left'}
			      ,{field: 'type', title: '类型', width:150}
			      ,{field: 'table_name', title: '数据表名', width:150}
			      ,{field: 'principal', title: '负责人', width:150}
			      ,{field: 'phone', title: '负责人电话', width:150} 
			      ,{field: 'area', title: '所属区域', width: 150}
			      ,{field: 'town', title: '所属地', width: 150}
			      ,{fixed: 'right', width:300, align:'center', toolbar: '#bar'}
			    ]]
			  });
			  
			  table.on('toolbar',function(obj){
				  var event = obj.event;
				  if(event == 'add'){
					  layer.open({
						  type : 2,
						  anim: 1,
						  area: ['700px','450px'],
						  title : '新舍添加',
						  fixed: false,
						  content : '../System/addHouse',
						  end : function (){
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
						  title : data.name+'信息修改',
						  fixed: false,
						  content : '../System/editHouse?name='+data.name,
						  end : function(){
							  location.reload();
						  }
					  })
				  }else if(event == 'unit'){
					  var house_name = data.table_name;
					  var name = data.name;
					  layer.open({
						  type : 2,
						  anim: 1,
						  area: ['1200px','450px'],
						  title : name+'单元',
						  fixed: false,
						  content : '../System/unit?house_name='+house_name,
						  
					  });
				  }else if(event == 'del'){
					  var table_name = data.table_name;
					  layer.confirm('确定要删除该舍吗?其所有的单元及设备信息将一概删除!!',function(index){
						  $.ajax({
								url : '../House/house_delete',
								data : {'company_id': company_id,'table_name':table_name},
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

</script>
</html>