<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>猪只管理</title>
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

			<div title="数据管理" data-options="iconCls:'icon-save'"
				style="overflow: auto; padding: auto;">
				<ul id="tree" class="easyui-tree"></ul>
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

<!--初始化树信息-->
$(function(){
	var treeData = [{
		text : "猪只列表",
		attributes:{
			url : '<iframe width="100%" height="100%" frameborder="0" src="${pageContext.request.contextPath}/Data/pig" style="width:100%;height=100%;margin:0px 0px;"></iframe>'	
		}
	}];
	
	
	<!--实例化树-->
	$('#tree').tree({
		data : treeData,
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

</script>
</html>