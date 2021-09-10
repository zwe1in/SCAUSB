<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>已有类型系统添加</title>
	<link rel="stylesheet" href="../resources/layui/css/layui.css">
	<script src="../resources/layui/layui.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
</head>
<body>
<div style="width:50%; margin-left:25%;">
	<form class="layui-form layui-form-pane" lay-filter="formtest" action="">
		<div class="layui-form-item" pane>
			<label class="layui-form-label">设备ID</label>
			<div class="layui-input-block">
				<input type="text" name="id" id="id" required  lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">主节点</label>
			<div class="layui-input-block">
				<input type="text" name="G" required  lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">从节点</label>
			<div class="layui-input-block">
				<input type="text" name="N" required  lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">设备类型</label>
			<div class="layui-input-block">
				<select name="type" id="type" lay-verify="required">
				</select>
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">纬度位置</label>
			<div class="layui-input-block">
				<input type="text" name="longitude" required  lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">经度位置</label>
			<div class="layui-input-block">
				<input type="text" name="latitude" required  lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit lay-filter="formDemo">下一步</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>
</div>
</body>
<script type="text/javascript">
	var company_id = localStorage.getItem('company_id');
	var equipments;
	var company_id;
	var place = '${place}';
	console.log(place);
	$(function(){		
		equipments = JSON.parse(localStorage.getItem("titleOfType"));
		layui.use(['form','layer'],function(){
			var form = layui.form;
			var layer = layui.layer;
			getCombobox(equipments);
			form.render('select');		
			form.on('submit(formDemo)', function(data){
				  	var type_value = $('#type').find("option:selected").text();
				  	var id = $('#id').val();
				  	alert(id);
				  	localStorage.setItem("e_id",id);
				    $.ajax({
						url : '../Equipment/addSystem?company_id='+company_id+'&type_value='+type_value+'&place='+place,
						data : data.field,
						contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
						type :'POST',
						dataType : 'json',
						success : function(json){
							if(json.success){
								layer.alert('添加成功',{
									title : '提示',
									icon : 1,
									end : function(){
										window.location.href = "../System/addElement";
									}
									
								});
								
							}else{
								layer.alert('添加失败'+json.msg,{
									title : '提示',
									icon : 2
								});
							}
						}
					});
				    return false;
				  });
		});
	})
	
	//生成设备下拉框
	function getCombobox(type){
		var select = document.getElementById('type');
		for(var key in type){
			var option = document.createElement('option');
			option.value = key;
			option.innerText = type[key];
			select.appendChild(option);
		}
	}
</script>
</html>