<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户信息页面</title>
	<link rel="stylesheet" type="text/css" href="../resources/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../resources/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../resources/css/demo/demo.css">
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../resources/js/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',title:'后台管理',split:true"
		style="height: 120px;">
		<div class="header-left" style="height: 30px; float:left;">
        	<h1>客户个人信息管理</h1>
        </div>
        <div class="header-right" align="right" style="height: 30px; float:right; ">
        	<p><strong class="easyui-tooltip" title="0条未读消息">${user.uname}</strong>，欢迎您！</p>
            <p><a href="#">网站首页</a>|<a href="#">支持论坛</a>|<a href="#">帮助中心</a>|<a href="#" onclick="quit()" >安全退出</a></p>
        </div>
		</div>

	<div data-options="region:'west',title:'功能列表',split:true"
		style="width: 250px;">
		<div id="aa" class="easyui-accordion"
			style="width: auto; height: auto;">

			<div title="个人信息管理" data-options="iconCls:'icon-save'"
				style="overflow: auto; padding: auto;">
				<ul id="tree1" class="easyui-tree"></ul>
			</div>

		</div>
	</div>
	<div data-options="region:'center',title:''"
		style="padding: 5px; background: #eee; overflow: hidden" >
		<div id="tabs" class="easyui-tabs" style="height: 645px;">
			<div title="首页" style="padding: 20px; display: none;"></div>
		</div>
	</div>
	</div>
</body>
<script type="text/javascript">
	var telephone = '${user.telephone}';
	var password = '${user.password}';
	var url = "${pageContext.request.contextPath}/System/userlogout?telephone="+telephone;
<!--初始化树信息-->
$(function(){
	var treeData1 = [{
		text : "个人信息查询",
		attributes:{
			url : '<iframe width="100%" height="100%" frameborder="0" src="${pageContext.request.contextPath}/User/userInfo?telephone='+telephone+'" style="width:100%;height=100%;margin:0px 0px;"></iframe>'	
		}
	},{
		text : "密码修改",
		attributes:{
			url:'<iframe width="100%" height="100%" frameborder="0" src="${pageContext.request.contextPath}/User/pwdEdit?telephone='+telephone+'" style="width:100%;height=100%;margin:0px 0px;"></iframe>'
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
		window.location.href = url;
	}
</script>
</html>