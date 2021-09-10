<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>子设备添加</title>
	<link rel="stylesheet" href="../resources/layui/css/layui.css">
	<script src="../resources/layui/layui.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
</head>
<body>
	<!-- 用户信息列表 -->
	<form class="layui-form" lay-filter="formtest" action=""
		style="margin: 30px;">
		<div class="layui-form-item">
			<label class="layui-form-label">设备名称</label>
			<div class="layui-input-block">
				<input type="text" name="name" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">设备类型</label>
			<div class="layui-input-block">
				<input type="radio" name="type" value="perform" title="执行器">
				<input type="radio" name="type" value="remote" title="传感器">
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>

</body>

<script type="text/javascript">
	var table_name = '${table_name}';
	$(function(){
		layui.use(['form','layer'], function(){
			  var form = layui.form;
			  var layer = layui.layer;
			  //监听提交
			  form.on('submit(formDemo)', function(data){
			    $.ajax({
					url : '../Equipment/add?table_name='+table_name,
					data : data.field,
					contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
					type :'POST',
					dataType : 'json',
					success : function(json){
						if(json.success){
							layer.alert('添加成功',{
								title : '提示',
								icon : 1
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
</script>
</html>