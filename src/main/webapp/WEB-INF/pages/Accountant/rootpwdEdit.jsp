<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理员密码修改</title>
<link rel="stylesheet" href="../resources/layui/css/layui.css">
<script src="../resources/layui/layui.js"></script>
<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
</head>
<body>
<div style="margin:0 auto;">
	<label class="layui-form-label">原密码</label>
    <div class="layui-input-inline">
      <input type="password" id="pwd" name="password" required lay-verify="required" placeholder="请输入原密码" autocomplete="off" class="layui-input">
    </div>
	<button type="button" class="layui-btn" onclick="commit()">确定</button>
</div>
</body>
<script type="text/javascript">
	var user_name = '${user_name}';
	function commit(){
		var datas = {};
		datas.user_name = user_name;
		$.ajax({
			url : 'oldPwd',
			data : datas,
			contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
			type :'POST',
			success : function(data){
				if($('#pwd').val() == data.password){
					window.location.href = ('${pageContext.request.contextPath}/Accountant/confirmPwd?user_name='+user_name);
				}else{
					$.messager.alert("提示","密码错误",'error');
				}
			}
		});
	}
	
</script>
</html>