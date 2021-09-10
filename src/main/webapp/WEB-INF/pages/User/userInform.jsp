<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户个人信息列表</title>
<link rel="stylesheet" type="text/css"
	href="../resources/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../resources/css/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="../resources/css/demo/demo.css">
<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
<script type="text/javascript"
	src="../resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../resources/js/easyui-lang-zh_CN.js"></script>
</head>
<body>
		<!-- 管理员个人信息 -->
	<table id="pg" style="width:600px" align="center" ></table>
	
	<div style="margin:20px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="getChanges()">保存</a>
	</div>
</body>
<script type="text/javascript">
	let datas = {};
	$(function(){
		var sex = '${user.sex}';
		var uname = '${user.uname}';
		var address = '${user.address}';
		var telephone = '${user.telephone}';
		var email = '${user.email}';
		datas.telephone = telephone;
		var row = [
		    {"name":"姓名","value":uname,"group":"个人信息","editor":"text"},
		    {"name":"性别","value":sex,"group":"个人信息","editor":{
		    	'type':"combobox",
		    	'options' : {'data':[{"value":"男","text":"男"},{"value":"女","text":"女"}],"panelHeight":"auto"}
		    }},
		    {"name":"地址","value":address,"group":"个人信息","editor":"text"},
		    {"name":"*绑定手机号(不可改)","value":telephone,"group":"联系方式"},
		    {"name":"邮箱","value":email,"group":"联系方式","editor":{
				"type":"validatebox",
				"options":{
					"validType":"email"
				}
		    }}
		];
		$('#pg').propertygrid({
			        width: 800,
			        height: 'auto',
			        showGroup: true,
			        scrollbarSize: 0,
					showHeader:false,
			        columns: [[
			                { field: 'name', title: 'Name', width: 100, resizable: true  },
			                { field: 'value', title: 'Value', width: 100, resizable: false }
			        ]]
			    });
		$('#pg').propertygrid('loadData', row);
	});
	
	function getChanges(){
			var s = '';
			var rows = $('#pg').propertygrid('getChanges');
			for(var i=0; i<rows.length; i++){
//				s += rows[i].name + ':' + rows[i].value + ',';
				if(rows[i].name == "姓名"){
					datas.uname = rows[i].value;
				}else if(rows[i].name == "性别"){
					datas.sex = rows[i].value;
				}else if(rows[i].name == "地址"){
					datas.address = rows[i].value;
				}else if(rows[i].name == "邮箱"){
					datas.email = rows[i].value;
				}
				
			}
			$.ajax({
				url : 'usersave',
				data : datas,
				contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
				type :'POST',
				success : function(data){
//					data = eval("(" + data + ")");
					if(data.success){
						$.messager.alert("提示","保存成功",function(r){
							if(r){
								window.location.href = ("${pageContext.request.contextPath}/User/userInfo?telephone="+data.telephone);
							}
						});
					}else{
						$.messager.alert("警告","保存失败"+data.msg,"error");
					}
				}
			})
		}
	
</script>
</html>