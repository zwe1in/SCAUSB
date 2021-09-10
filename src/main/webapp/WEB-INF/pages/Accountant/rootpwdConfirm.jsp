<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新密码确认</title>
<link rel="stylesheet" href="../resources/layui/css/layui.css">
<script src="../resources/layui/layui.js"></script>
<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
</head>
<body>
<div style="margin:0 auto;">
<div class="layui-form-item">
	<label class="layui-form-label">输入新密码</label>
    <div class="layui-input-inline">
      <input type="password" id="new" name="password" required lay-verify="required" placeholder="请输入新密码" autocomplete="off" class="layui-input">
    </div>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">确认新密码</label>
    <div class="layui-input-inline">
      <input type="password" id="new1" name="new-password" required lay-verify="required" placeholder="确认新密码" autocomplete="off" class="layui-input">
    </div>
</div>
<div class="layui-form-item">
	<button type="button" class="layui-btn" onclick="commit()">确定</button>
</div>
</div>
</body>
<script type="text/javascript">
	var user_name = '${user_name}';
	function commit(){
		if($('#new').val() == $('#new1').val()){
			let datas = {};
			datas.password = $('#new').val();
			datas.user_name = user_name;
			$.ajax({
				url : 'newPwd',
				data : datas,
				contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
				type :'POST',
				success : function(data){
					if(data.success){
						layui.use('layer',function(){							
							layer.alert('修改成功',{
								title:'提示',
								icon : 1,
								yes : function(){
									window.location.href = '../Accountant/pwdEdit?user_name='+user_name;
								}
							})
						})
					}else{
						layui.use('layer',function(){							
							layer.alert('修改失败'+data.msg,{
								title:'提示',
								icon : 2,
								yes : function(){
									window.location.href = '../Accountant/pwdEdit?user_name='+user_name;
								}
							})
						})
					}
				}
			});
		}else{
			layui.use('layer',function(){							
				layer.alert('两次密码不同，请重新确认',{
					title:'提示',
					icon : 2
				})
			})
		}
	}
</script>
</html>