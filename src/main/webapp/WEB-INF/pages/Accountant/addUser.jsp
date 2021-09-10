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
			<label class="layui-form-label">用户名称</label>
			<div class="layui-input-block">
				<input type="text" name="user_name" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">联系方式</label>
			<div class="layui-input-block">
				<input type="text" name="phone" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">授权码</label>
			<div class="layui-input-block">
				<input type="text" name="authorization_code" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
    <label class="layui-form-label">用户等级</label>
			<div class="layui-input-block">
				<select name="group_id" id="group_id" lay-verify="required">
				</select>
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
	var level_data = {'区域级管理员' : 2, '市级管理员':3,'场级管理员':4};
	var company_id = '${company_id}';
	var group_id = localStorage.getItem('group_id');
	getCombobox(group_id);
	
	//生成设备下拉框
	function getCombobox(group_id){
		var select = document.getElementById('group_id');
		var j = 0;
		for(var key in level_data){
			if(j > (4-parseInt(group_id))) break;
			var option = document.createElement('option');
			option.value = level_data[key];
			option.innerText = key;
			select.appendChild(option);
			j++;
		}
	}
	$(function(){
		layui.use(['form','layer'], function(){
			  var form = layui.form;
			  var layer = layui.layer;
			  //监听提交
			  form.on('submit(formDemo)', function(data){
			    $.ajax({
					url : '../Accountant/add?company_id='+company_id,
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