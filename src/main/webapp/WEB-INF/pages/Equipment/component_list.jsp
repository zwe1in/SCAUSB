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
		<span class="layui-breadcrumb">
		</span>
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
    <button class="layui-btn layui-btn-sm" lay-event="add">添加子设备</button>
  </div>
</script>

<script type="text/javascript">
	var type = '${type}';
	var company_id = localStorage.getItem('company_id');
	var group_id = localStorage.getItem('group_id');
	var user_name = localStorage.getItem('user_name');
	var table_name = '';

	//获取树数据
	$.ajax({
		url : '../House/getEquipTree',
		data : {'type': type,'company_id':company_id,'group_id':group_id,'user_name':user_name},
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
		type :'POST',
		dataType : 'json',
		success : function(json){
			console.log(json)
			layui.use(['tree','table','layer','form'],function(){
				var tree = layui.tree;
				var table = layui.table;
				var layer = layui.layer;
				var form = layui.form;
				//渲染树
				tree.render({
					elem : '#tree',
					data : json,
					click : function(obj){
						var bread = +obj.data.title;
						table_name = obj.data.id+'_eq';
						if(obj.data.id != null){
							table.render({
								elem: '#list'
								    ,toolbar: '#toolbar'
								    ,even: true //开启隔行背景
								    ,url: '../Equipment/getAllInnerOfEquipment?table_name='+table_name
								    ,page: true //开启分页
								    ,limits: [15,20,30]
						  			,limit : 15
								    ,cols: [[ //表头
								    	{field: 'id', title: '元件序号', width:130, sort: true, fixed: 'left'}
									      ,{field: 'e_id', title: '元件ID', width:130}
									      ,{field: 'name', title: '设备元件名称', width:130} 
									      ,{field: 'type', title: '元件类型', width:100,templet:function(d){return d.type == 'perform'?'执行器':'传感器';}}
									      ,{field:'status', title:'元件开启状态', width:100, templet: '#switchTpl', unresize: true}
									      ,{field: 'time', title: '添加日期', width: 200}
									      ,{title:'操作',fixed: 'right', width:150, align:'center', toolbar: '#bar'}
								    ]]
							});
							//监听开关操作
							form.on('switch(status)', function(obj){
								var status = obj.elem.checked?1:0;
								var status_str = obj.elem.checked?'开启':'关闭';
								$.ajax({
									url : '../Equipment/switch_inner',
									 data : {'id': obj.value,'status': status,'table_name':table_name},
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
							//头部菜单绑定
					  		table.on('toolbar', function(obj){
					  			var event = obj.event;
					  			if(event == 'add')
					  			layer.open({
									  type : 2,
									  anim: 1,
									  area: ['700px','450px'],
									  title : '子设备添加',
									  fixed: false,
									  content : '../System/addInner_Equipment?table_name='+table_name
								  })
					  		});
					  		//点击事件绑定
							  table.on('tool', function(obj){
								  var data = obj.data;
								  var event = obj.event;
								  if(event == 'edit'){
									  layer.open({
										  type : 2,
										  anim: 1,
										  area: ['700px','450px'],
										  title : '元件信息修改',
										  fixed: false,
										  content : '../System/edit?user_name='+data.user_name+'&accouant_name='+accountant_name,
										  end : function(){
											  window.reload();
										  }
									  })
								  }else if(event == 'del'){
									  var id = data.id;
									  layer.confirm('确定要删除该元件吗?',function(index){
										  $.ajax({
												url : 'delete',
												data : {'id': id,'table_name':table_name},
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
						}
					}
				})
			})
		}
	})
	
	

</script>
</html>