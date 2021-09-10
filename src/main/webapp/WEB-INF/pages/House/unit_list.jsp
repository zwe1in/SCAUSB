<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>猪舍单元</title>
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
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑舍单元</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除单元</a>
</script>

<script type="text/html" id="toolbar">
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-sm" lay-event="add">添加新单元</button>
  </div>
</script>

<script type="text/javascript">
	var house_name = '${house_name}';
	$(function(){
		layui.use(['table','layer'], function(){
			  var table = layui.table;	
			  var layer = layui.layer;
			  //第一个实例
			  table.render({
			    elem: '#list'
			    ,toolbar : '#toolbar'
			    ,even: true //开启隔行背景
			    ,width: 1210
			    ,url: '../House/unit_list?house_name='+house_name //数据接口
			    ,page: true //开启分页
			    ,limits: [10,20,30]
			    ,cols: [[ //表头
			      {field: 'unit', title: '单元名称', width:150, sort: true, fixed: 'left'}
			      ,{field: 'type', title: '单元类型', width:150}
			      ,{field: 'equip_count', title: '设备数量', width:150}
			      ,{field: 'equip_list', title: '设备ID列表', width:150}
			      ,{field: 'principal', title: '负责人', width:150} 
			      ,{field: 'phone', title: '负责人电话', width: 150}
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
						  title : '新单元添加',
						  fixed: false,
						  content : '../System/addUnit?house_name='+house_name,
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
						  area: ['700px','350px'],
						  title : '单元信息修改',
						  fixed: false,
						  content : '../System/editUnit?house_name='+house_name+'&unit='+data.unit,
						  end : function(){
							  location.reload();
						  }
					  })
				  }else if(event == 'del'){
					  var unit = data.unit;
					  layer.confirm('确定要删除该单元吗?其所有的单元及设备信息将一概删除!!',function(index){
						  $.ajax({
								url : '../House/unit_delete',
								data : {'house_name': house_name,'unit': unit},
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