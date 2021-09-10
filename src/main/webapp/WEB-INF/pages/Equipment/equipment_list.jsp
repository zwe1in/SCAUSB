<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>设备展示</title>
	<link rel="stylesheet" href="../resources/layui/css/layui.css">
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
	<script src="../resources/layui/layui.js"></script>
</head>
<body>
	<div class="side " style="width:20%; height: 100%; float: left; margin:5px;">
		<div class="layui-collapse">
			<div class="layui-colla-item">
				<h2 class="layui-colla-title">设备ID列表</h2>
				<div class="layui-colla-content layui-show">
					<div id="tree"></div>
				</div>
			</div>
		</div>
	</div>

	<div class="body" style="width:70%; height: 100%; float: left; margin:5px;">
		<table id="list"></table>
	</div>
</body>

<script type="text/html" id="bar">
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/html" id="switchTpl">
  <input type="checkbox" name="status" value="{{d.id}}" lay-skin="switch" lay-text="在线|下线" lay-filter="status" {{ d.status == 1 ? 'checked' : '' }}>
</script>

<script type="text/html" id="toolbar">
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-sm" lay-event="add">添加设备</button>
  </div>
</script>

<script type="text/javascript">
	var type = '${type}';
	var company_id = localStorage.getItem('company_id');
	var group_id = localStorage.getItem('group_id');
	var user_name = localStorage.getItem('user_name');
	var place = '';
	//获取树数据
	$.ajax({
		url : '../House/getHouseTree',
		data : {'type': type,'company_id':company_id,'group_id':group_id,'user_name':user_name},
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
		type :'POST',
		dataType : 'json',
		success : function(json){
			console.log(json)
			layui.use(['tree','table','form','layer'],function(){
				var tree = layui.tree;
				var table = layui.table;
				var layer = layui.layer;
				var form = layui.form;
				//渲染树
				tree.render({
					elem : '#tree',
					data : json,
					click : function(obj){
						place = obj.data.place;
						var unit = obj.data.id;
						if(unit != null){
							table.render({
								elem: '#list'
								    ,toolbar: '#toolbar'
								    ,even: true //开启隔行背景
								    ,url: '../Equipment/getEquipmentByUnit?unit='+unit+'&type='+type+'&company_id='+company_id 
								    ,page: true //开启分页
								    ,limits: [15,20,30]
						  			,limit : 15
								    ,cols: [[ //表头
								      {field: 'id', title: '设备ID', width:130, sort: true, fixed: 'left'}
								      ,{field: 'g', title: '主节点', width:75}
								      ,{field: 'n', title: '从节点', width:75} 
								      ,{field: 'type_value', title: '设备类型', width:90}		
								      ,{field: 'area', title: '所属区域', width: 90}
								      ,{field: 'town', title: '所属县', width: 90}
								      ,{field: 'unit', title: '所属舍单元', width: 140}
								      ,{field: 'time', title: '添加时间', width: 190}
								      ,{field:'status', title:'在线状态', width:100, templet: '#switchTpl', unresize: true}
								      ,{title:'操作',fixed: 'right', width:120, align:'center', toolbar: '#bar'}
								    ]]
							});
							//监听开关操作
							form.on('switch(status)', function(obj){
								var status = obj.elem.checked?1:0;
								var status_str = obj.elem.checked?'开启':'关闭';
								$.ajax({
									url : '../Equipment/switch',
									 data : {'id': obj.value,'status': status,'company_id':company_id},
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
							//头部菜单绑定
							table.on('toolbar', function(obj){
					  			var event = obj.event;
					  			if(event == 'add')
					  				layer.confirm('添加设备类型', {
					  				  btn: ['已有类型设备', '新类型设备'] //可以无限个按钮
					  				  ,btn1: function(index, layero){
					  					layer.open({
											  type : 2,
											  anim: 1,
											  area: ['700px','450px'],
											  title : '已有设备添加',
											  fixed: false,
											  content : '../System/existSystemAdd?place='+place,
											  end : function(){
												  location.reload();
											  }
										  })
					  				  },btn2 : function(index,layero){
					  					layer.open({
											  type : 2,
											  anim: 1,
											  area: ['700px','450px'],
											  title : '新设备添加',
											  fixed: false,
											  content : '../System/systemAdd?place='+place,
											  end : function(){
												  location.reload();
											  }
										  })
					  				  }
					  				});
					  		});
							 table.on('tool', function(obj){
								 var event = obj.event;
								 var data = obj.data;
								 if(event == 'del'){
									 var id = data.id;
									 var house = data.house;
									 var unit = data.unit;
									 layer.confirm('删除后设备数据将一概删除，确定删除设备吗？',function(){
										 $.ajax({
											 url : '../Equipment/delete_equip',
											 data : {'id': id,'house': house,'company_id':company_id,'unit':unit},
											 contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
											 type :'POST',
											 dataType : 'json',
											 success : function(json){
													if(json.success){
														if(json.success){
															layer.alert('删除成功',{
																title : '提示',
																icon : 1,
																end : function(){location.reload();}
															});
														}else{
															layer.alert('删除失败'+json.msg,{
																title : '提示',
																icon : 2,
																end : function(){location.reload();}
															});
														}
													}
											 }
										 })
									 })
								 }
							 });
						}
					}
				})
			})
		}
	})
</script>
</html>