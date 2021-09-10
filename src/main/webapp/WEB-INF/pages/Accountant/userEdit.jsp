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
    <label class="layui-form-label">性别</label>
    <div class="layui-input-block">
      <input type="radio" name="sex" value="1" title="男">
      <input type="radio" name="sex" value="0" title="女">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">联系电话</label>
    <div class="layui-input-block">
      <input type="text" name="phone" required  lay-verify="required" placeholder="请输入联系电话" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">邮箱</label>
    <div class="layui-input-block">
      <input type="text" name="email" required  lay-verify="email|required" placeholder="请输入邮箱" autocomplete="off" class="layui-input">
    </div>
  </div>
    <div class="layui-form-item">
    <label class="layui-form-label">地址</label>
    <div class="layui-input-block">
      <input type="text" name="address" required  lay-verify="required" placeholder="请输入住址" autocomplete="off" class="layui-input">
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
							  ,"sex": json.sex
							  ,"phone": json.phone
							  ,"email": json.email
							  ,"address": json.address
							});	
					}
				})
			  //监听提交
			  form.on('submit(formDemo)', function(data){
			    $.ajax({
					url : '../Accountant/update?account_name='+account_name,
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
</script>
</html>