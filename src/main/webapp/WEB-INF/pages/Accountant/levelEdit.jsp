<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>客户信息修改</title>
	<link rel="stylesheet" href="../resources/layui/css/layui.css">
	<script src="../resources/layui/layui.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
</head>
<body>
	<!-- 用户信息列表 -->
<form class="layui-form" lay-filter="formtest" action="" style="margin:30px;">
<div class="layui-form-item">
    <label class="layui-form-label">用户ID</label>
    <div class="layui-input-block layui-disabled">
      <input type="text"  readonly="readonly" name="id"   autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">用户名</label>
    <div class="layui-input-block">
      <input type="text" name="user_name" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
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
    <label class="layui-form-label">区域级</label>
    <div class="layui-input-block">
      <input type="text" name="area" id="area" required  lay-verify="required" placeholder="请输入区域" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">市级</label>
    <div class="layui-input-block">
      <input type="text" name="town" id="town" required  lay-verify="required" placeholder="请输入县/市" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">场(舍)级</label>
    <div class="layui-input-block">
      <input type="text" name="house" id="house" required  lay-verify="required" placeholder="请输入猪舍名" autocomplete="off" class="layui-input">
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
	var account_name = '${account_name}';
 	var user_name = '${user_name}';
 	var group_id = localStorage.getItem('group_id');
 	var level_data = {'区域级管理员' : 2, '市级管理员':3,'场级管理员':4};
 	getCombobox(group_id);
	$(function(){
		layui.use(['form','layer'], function(){
			  var form = layui.form;
			  var layer = layui.layer;
				$.ajax({
					url : '../Accountant/queryWithUname',
					data : {'user_name': user_name},
					contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
					type :'POST',
					dataType : 'json',
					success : function(json){
						  form.val("formtest", { //formTest 即 class="layui-form" 所在元素属性 lay-filter="" 对应的值
							  "id":json.id
							  ,"user_name": json.user_name // "name": "value"
							  ,"group_id": json.group_id
							  ,"area": json.area
							  ,"town": json.town
							  ,"house": json.house
							});	
						  isChange();
						  form.on('select',function(data){
							  isChange();
						  })
					}
				})
			  //监听提交
			  form.on('submit(formDemo)', function(data){
			    $.ajax({
					url : '../Accountant/levelUpdate',
					data : data.field,
					contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
					type :'POST',
					dataType : 'json',
					success : function(json){
						if(json.success){
							layer.alert('修改成功',{
								title : '提示',
								icon : 1
							});
						}else{
							layer.alert('修改失败'+json.msg,{
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
	
	//判断其他框是否可改
	function isChange(){
		var g = $('#group_id').val();
		switch(parseInt(g)){
		case 2 :
			$('#house').val('--');$('#house').attr('readonly','readonly');
			$('#town').val('--');$('#town').attr('readonly','readonly');
			break;
		case 3 :
			$('#town').removeAttr('readonly');
			$('#house').val('--');$('#house').attr('readonly','readonly');
			break;
		case 4 :
			$('#house').removeAttr('readonly');
			$('#town').removeAttr('readonly');
			break;
		}
	}
</script>
</html>