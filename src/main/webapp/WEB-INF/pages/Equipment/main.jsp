<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>设备管理</title>
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
			<div title="系统设备列表" data-options="iconCls:'icon-tip'"
				style="overflow: auto; padding: auto;">
				<ul id="tree1" class="easyui-tree"></ul>
			</div>
			
			<div title="系统设备添加"
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
			<div title="首页" style="padding: 20px; display: none;" ></div>
		</div>
	</div>
</body>

<script type="text/javascript">

$(function(){
	var account_id = localStorage.getItem("account_id");
	var equipment = {};
	var title = {};
	$.ajax({
		url:'../Equipment/myequip',
        data: {'account_id':account_id},
        contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
        type:'POST',
        async:false,
        dataType: 'json',
        success:function(data){
        	console.log(data)
        	equipment = data.content;
        	title = data.title;
        	console.log(data);
        }
	})
	var tab = $('#tabs').tabs('getTab', '首页');
	$('#tabs').tabs('update', {
			tab : tab,
			options : {
				content:'<iframe width="100%" height="100%" frameborder="0" src="${pageContext.request.contextPath}/Equipment/list" style="width:100%;height=100%;margin:0px 0px;"></iframe>'
			}
		});
	tab.panel('refresh', '');
	var treeData1 =[];
	for(var key in equipment){
		var temp = {};
		temp.text = getEquipType(key);
		var child_temp = [];
		for(var i = 0; i<equipment[key].length; i++){
			var intemp = {};
			intemp.text = equipment[key][i];
			intemp.attributes = {url : '<iframe width="100%" height="100%" frameborder="0" src="${pageContext.request.contextPath}/System/OneEquipment?table_name='+equipment[key][i]+'" style="width:100%;height=100%;margin:0px 0px;"></iframe>'};
			child_temp.push(intemp);
		}
		temp.children = child_temp;
		treeData1.push(temp);
	}
	
	function getEquipType(key){
		for(var p in title){
			if(p == key)
				return title[p];
		}
	}

	
	var treeData2 = [{
		text : "已有系统添加",
		attributes:{
			url : '<iframe width="100%"  height="100%"  frameborder="0"  src="${pageContext.request.contextPath}/System/existSystemAdd" style="width:100%;height:100%;"></iframe>'	
		}
	},{
		text : "新系统添加",
		attributes:{
			url:'<iframe width="100%"  height="100%"  frameborder="0"  src="${pageContext.request.contextPath}/System/systemAdd" style="width:100%;height:100%;"></iframe>'
		}
	}];
	
	var treeData3 = [{
		text : "设备操作日志",
		attributes:{
			url : '<iframe width="100%"  height="100%"  frameborder="0"  src="${pageContext.request.contextPath}/Logger/elog" style="width:100%;height:100%;"></iframe>'	
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
</script>
</html>