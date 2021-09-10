<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户密码修改</title>
	<link rel="stylesheet" type="text/css" href="../resources/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../resources/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../resources/css/demo/demo.css">
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../resources/js/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<form id="pwdFrom" method="post" align="center">
		<div style="margin-bottom: 20px">
		<input class="easyui-passwordbox" id= "pwd" name="password" style="width: 30%"
					data-options="label:'请输入原密码:',required:true">
		</div>
		<a id="submitBtn" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="iconCls:'icon-save'">确定</a>
	</form>
</body>

<script type="text/javascript">
	var telephone = '${telephone}';
	$('#submitBtn').click(function(){
		let datas = {};
		datas.telephone = telephone;
		$.ajax({
			url : 'oldPwd',
			data : datas,
			contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
			type :'POST',
			success : function(data){
				if($('#pwd').val() == data.password){
					window.location.href = ('${pageContext.request.contextPath}/User/confirmPwd?telephone='+telephone);
				}else{
					$.messager.alert("提示","密码错误",'error');
				}
			}
		});
	});
</script>
</html>