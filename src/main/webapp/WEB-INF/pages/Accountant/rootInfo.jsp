<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理员个人信息</title>
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
	  <link rel="stylesheet" href="../resources/layui/css/layui.css">
  <script src="../resources/layui/layui.js"></script>
</head>
<body>
	<!-- 管理员个人信息 -->
	<div class = "body" style="width:90%; margin:0 auto; font-size: 15px;">	
	<table id="pg" style="width:85%; align:center;" ></table>
	<div style="margin:20px 0;">
		<button type="button" class="layui-btn" onclick="getChanges()">保存</button>
	</div>
	</div>
	
</body>

<script type="text/javascript">
	let datas = {};
	$(function(){
		var sex = '${accountant.sex}';
		var user_name = '${accountant.user_name}';
		var address = '${accountant.address}';
		var phone = '${accountant.phone}';
		var email = '${accountant.email}';
		var authorization_code = '${accountant.authorization_code}';
		var birth = '${accountant.birth}';
		var group_id = '${accountant.group_id}';
		datas.user_name = user_name;
		var sex_name = sex==0?"男":"女";
		var group = group_id =="1"?"超级管理员":"普通用户";
		var row = [
		    {"name":"*用户名(不可改)","value":user_name,"group":"个人信息"},
		    {"name":"性别","value":sex_name,"group":"个人信息","editor":{
		    	'type':"combobox",
		    	'options' : {'data':[{"value":"男","text":"男"},{"value":"女","text":"女"}],"panelHeight":"auto"}
		    }},
		    {"name":"*授权码(不可改)","value":authorization_code,"group":"个人信息"},
		    {"name":"*特权","value":group,"group":"个人信息"},
		    {"name":"地址","value":address,"group":"个人信息","editor":"text"},
		    {"name":"联系电话","value":phone,"group":"联系方式","editor":"text"},
		    {"name":"邮箱","value":email,"group":"联系方式","editor":{
				"type":"validatebox",
				"options":{
					"validType":"email"
				}
		    }}
		];
		$('#pg').propertygrid({
			        width: 1000,
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
				if(rows[i].name == "性别"){
					datas.sex = rows[i].value=="男"?0:1;
				}else if(rows[i].name == "地址"){
					datas.address = rows[i].value;
				}else if(rows[i].name == "邮箱"){
					datas.email = rows[i].value;
				}else if(rows[i].name == "联系电话"){
					datas.phone = rows[i].value;
				}
				
			}
			$.ajax({
				url : 'rootsave',
				data : datas,
				contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
				type :'POST',
				success : function(data){
//					data = eval("(" + data + ")");
					if(data.success){
						$.messager.alert("提示","保存成功",function(r){
							if(r){
								window.location.href = ("${pageContext.request.contextPath}/Accountant/Info?phone="+data.phone);
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