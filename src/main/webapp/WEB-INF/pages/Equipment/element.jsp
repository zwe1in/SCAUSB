<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>观测元素添加</title>
	<link rel="stylesheet" href="../resources/layui/css/layui.css">
	<script src="../resources/layui/layui.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
</head>
<body>
	<div style="width:50%; margin-left:25%;">
		设备简称指标添加
		<form class="layui-form layui-form-pane" lay-filter="formtest" action="">
		<div class="container">
		<div class="layui-form-item item" >
			<div class="layui-inline">
				<label class="layui-form-label">测量指标</label>
				<div class="layui-input-block">
					<input type="text" name="value" lay-verify="required" autocomplete="off" class="layui-input value">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">指标简称</label>
				<div class="layui-input-block">
					<input type="text" name="key" lay-verify="required" autocomplete="off" class="layui-input key">
				</div>
			</div>
		</div>
		<div class="layui-form-item item" >
			<div class="layui-inline">
				<label class="layui-form-label">测量指标</label>
				<div class="layui-input-block">
					<input type="text" name="value" lay-verify="required" autocomplete="off" class="layui-input value">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">指标简称</label>
				<div class="layui-input-block">
					<input type="text" name="key" lay-verify="required" autocomplete="off" class="layui-input key">
				</div>
			</div>
		</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button type="button" class="layui-btn layui-btn-warm"  onclick="add()"><i class="layui-icon">&#xe608;</i>添加观测元素</button>
				<button type="button" class="layui-btn layui-btn-danger"  onclick="remove()"><i class="layui-icon">&#xe640;</i>删除观察元素</button>
				<button type="button" class="layui-btn" onclick="commit()"><i class="layui-icon">&#xe679;</i>确认并创建</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>
	</div>
</body>
<script type="text/javascript" src="../resources/js/element_create.js" charset="UTF-8"></script>

</html>