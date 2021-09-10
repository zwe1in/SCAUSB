<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
	<link rel="stylesheet" type="text/css" href="../resources/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../resources/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../resources/css/demo/demo.css">
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../resources/js/easyui-lang-zh_CN.js"></script>
</head>

<body class="easyui-layout">

	<div data-options="region:'west',title:'功能列表',split:true"
		style="width: 250px;">
		<div id="aa" class="easyui-accordion"
			style="width: auto; height: auto;">

			<div title="个人信息管理" data-options="iconCls:'icon-man'"
				style="overflow: auto; padding: auto;">
				<ul id="tree1" class="easyui-tree"></ul>
			</div>
			
			<div title="用户信息管理"
				data-options="iconCls:'icon-sum',selected:true"
				style="padding: auto;">
				<ul id="tree2" class="easyui-tree"></ul>
			</div>
			<div title="日志管理" 
				data-options="iconCls:'icon-save',selected:true"
				style="padding: auto;">
				<ul id="tree3" class="easyui-tree"></ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center',title:''"
		style="padding: 5px; background: #eee; overflow: hidden" >
		<div id="tabs" class="easyui-tabs" style="height: 645px;">
			<div title="欢迎" style="padding: 20px; display: none;"></div>
		</div>
	</div>
</body>

<script type="text/javascript">
	var user_name = '${user_name}';
<!--初始化树信息-->
$(function(){
	var treeData1 = [{
		text : "个人信息查询",
		attributes:{
			url : '<iframe width="100%" height="100%" frameborder="0" src="${pageContext.request.contextPath}/Accountant/Info?user_name='+user_name+'" style="width:100%;height=100%;margin:0px 0px;"></iframe>'	
		}
	},{
		text : "密码修改",
		attributes:{
			url:'<iframe width="100%" height="100%" frameborder="0" src="${pageContext.request.contextPath}/Accountant/pwdEdit?user_name='+user_name+'" style="width:100%;height=100%;margin:0px 0px;"></iframe>'
		}
	}];
	
	var treeData2 = [{
		text : "员工信息管理",
		attributes:{
			url : '<iframe width="100%"  height="100%"  frameborder="0"  src="${pageContext.request.contextPath}/Accountant/userList?user_name='+user_name+'" style="width:100%;height:100%;"></iframe>'	
		}
	},{
		text : "客户权限管理",
		attributes:{
			url:''
		}
	}];
	
	var treeData3 = [{
		text : "日志信息管理",
		attributes:{
			url : '<iframe width="100%"  height="100%"  frameborder="0"  src="${pageContext.request.contextPath}/Logger/log" style="width:100%;height:100%;"></iframe>'	
		}
	}];
	
	<!--实例化树-->
	$('#tree1').tree({
		data : treeData1,
		lines : true,
		onClick : function(node){
			if(node.attributes){
				Open(node.text,node.attributes.url)
			}
		}
	});
	
	$('#tree2').tree({
		data : treeData2,
		lines : true,
		onClick : function(node){
			if(node.attributes){
				Open(node.text,node.attributes.url)
			}
		}
	});
	
	$('#tree3').tree({
		data : treeData3,
		lines : true,
		onClick : function(node){
			if(node.attributes){
				Open(node.text,node.attributes.url)
			}
		}
	});
	
	function Open(text,url){
		if($('#tabs').tabs('exists',text)){
			$('#tabs').tabs('select',text);
		}else{
			$('#tabs').tabs('add',{
				title : text,
				closable : true,
				content : url
				})
			}
		}
	});
	//退出登录
	function quit(){
		window.location.href = "main.html";
	}
</script>
</html>