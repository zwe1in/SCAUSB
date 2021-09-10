<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<title>新密码确认</title>
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
		<input class="easyui-passwordbox" id= "new" name="password" style="width: 30%"
					data-options="label:'请输新密码:',required:true">
		</div>
		<div style="margin-bottom: 20px">
		<input class="easyui-passwordbox" id= "new1" name="password" style="width: 30%"
					data-options="label:'请确认新密码:',required:true">
		</div>
		<a id="submitBtn" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="iconCls:'icon-save'">确定</a>
	</form>
</body>
<script type="text/javascript">
	var telephone = '${telephone}';
	$('#submitBtn').click(function(){
		if($('#new').val() == $('#new1').val()){
			let datas = {};
			datas.password = $('#new').val();
			datas.telephone = telephone;
			$.ajax({
				url : 'newPwd',
				data : datas,
				contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
				type :'POST',
				success : function(data){
					if(data.success){
						$.messager.alert("提示","更改成功",function(r){
							if(r){
								location.reload();
							}
						});
					}else{
						$.messager.alert("提示","更改失败"+data.msg,'error');
					}
				}
			});
		}else{
			$.messager.alert("提示","两次密码输入不同","error");
		}
	});
</script>
</html>