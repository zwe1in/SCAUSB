<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有设备展示</title>
	<link rel="stylesheet" type="text/css" href="../resources/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../resources/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../resources/css/demo/demo.css">
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../resources/js/easyui-lang-zh_CN.js"></script>	
</head>
<body>
	<h2>设备信息总览</h2>
	<table id="list"></table>
	<!-- 工具条 -->
	<div id="tb">
		<a id="addBtn" href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true">添加新设备</a> 
		<a id="deleteBtn" href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove',plain:true">移除设备</a>
		<a id="excelBtn" href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-print',plain:true">导出为excel</a>
	</div>
	
	<!-- 设备配置窗口 -->
	<div id="windowEdit" class="easyui-window" title="设备配置"
		style="width: 500px; height: 400px;"
		data-options="iconCls:'icon-save',modal:true,closed:true">
		<form id="editForm" method="post" align="center">
			<div style="margin-bottom: 20px">
				<input class="easyui-textbox" name="id" style="width: 80%"
					data-options="label:'设备id:',required:true" readonly="readonly">
			</div>
			<div style="margin-bottom: 20px">
				<input class="easyui-textbox" name="time" style="width: 80%"
					data-options="label:'开启时间:',required:true">
			</div>
			<div style="margin-bottom: 20px">
				<input class="easyui-textbox" name="time_long" style="width: 80%"
					data-options="label:'开启时长:',required:true">
			</div>
			<div style="margin-bottom: 20px">
				<select class="easyui-combobox" name="type" label="设备类型"
					style="width: 80%">
					<option value="feed">饲养设备</option>
					<option value="environment">环境设备</option>
					<option value="mornitoring">监控设备</option>
					<option value="error">故障设备</option>
					<option value="mid">中间服务设备</option>
				</select>
			</div>
			<div style="margin-bottom: 20px">
				<select class="easyui-combobox" name="isrepeat" label="重复状态"
					style="width: 80%">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</div>
			<a id="saveBtnEdit" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
		</form>
	</div>

	<!-- 设备添加窗口 -->
	<div id="windowAdd" class="easyui-window" title="设备配置"
		style="width: 500px; height: 400px;"
		data-options="iconCls:'icon-save',modal:true,closed:true">
		<form id="AddForm" method="post" align="center">
			<div style="margin-bottom: 20px">
				<input class="easyui-textbox" name="name" style="width: 80%"
					data-options="label:'设备名称:',required:true">
			</div>
			<div style="margin-bottom: 20px">
				<select class="easyui-combobox" name="type" label="设备类型"
					style="width: 80%">
					<option value="perform">执行器</option>
					<option value="remote">传感器</option>
				</select>
			</div>
			<a id="saveBtnAdd" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
		</form>
	</div>
</body>

<script type="text/javascript">
	var table_name = '${table_name}'+"_eq";
	$(function(){
		$('#list').datagrid({
			url : '../Equipment/getAllInnerOfEquipment?table_name='+table_name,
			pagination : true,
			toolbar : '#tb',
			columns : [[{
				field : "id",
				title : "设备id",
				align : 'center',
				width : 100
			}, {
				//对象
				field : "e_id",
				//标题
				title : "设备编号",
				align : 'center',
				width : 150,
			}, {
				//对象
				field : "name",
				//标题
				title : "设备名称",
				align : 'center',
				width : 150,
			}, {
				//对象
				field : "type",
				//标题
				title : "设备类型",
				align : 'center',
				width : 150,
				formatter : function(value, row, index){
					return value=="perform"?"执行器":"传感器";
				}
			}, {
				//对象
				field : "status",
				//标题
				title : "开启状态",
				align : 'center',
				width : 150,
				formatter : function(value, row, index){
					var result;
					if(value == '1'){
						result = '<span style="color:green;">正常</span>';
					}else{
						result = '<span style="color:red;">异常</span>'
					}
					return result;
				}
			}, {
				//对象
				field : "opt",
				//标题
				title : "操作",
				align : 'center',
				width : 150,
				formatter:function(value,row,index){  
					var btn = '<a class="editcls" onclick="editRow('+index+')" href="javascript:void(0)">编辑</a>';   
	                return btn;  
	            }  
			},{
				//对象
				field : "opt2",
				//标题
				title : "开关",
				align : 'center',
				width : 150,
				formatter:function(value,row,index){  
	                var btn = '<a class="open" onclick="opcl('+index+')" href="javascript:void(0)">开启/关闭</a>';  
	                return btn;  
	            }  
			}]],
			onLoadSuccess:function(data){  
				$('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
		        $('.open').linkbutton({text:'开启/关闭',plain:true,iconCls:'icon-tip'}); 
		    }  
		})
	})
	
	//编辑按钮绑定函数
	function editRow(index){
		$('#list').datagrid('selectRow',index);
		var row = $('#list').datagrid('getSelected');
		if(row){
			//数据回显
			$('#editForm').form('load', 'getById?id=' + row.id);
			//打开窗口
			$('#windowEdit').window("open");
		}
	}

	//保存修改后的数据
	$('#saveBtnEdit').click(function() {
		$('#editForm').form("submit", {
			url : 'update',
			onSubmint : function() {
				//判断表单的验证是否通过
				return $('#editForm').form("validate");
			},
			success : function(data) {
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert("提示", "配置成功", "info");
					$('#windowEdit').window("close");
					$('#list').datagrid("reload");
				} else {
					$.messager.alert("提示", "配置失败" + data.msg, "error");
				}
			}
		});
	});
	
	//开关按钮
	function opcl(index){
		$('#list').datagrid('selectRow',index);
		var row = $('#list').datagrid('getSelected');
		if(row){
			let data = {};
			data.id = row.id;
			$.ajax({
				url:'${pageContext.request.contextPath}/Equipment/opcl',
				data:data,
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				type:'POST',
				success : function(data){
					if(data.success){
						$.messager.alert("提示","操作成功","info");
						$('#list').datagrid('reload');
					}else{
						$.messager.alert("提示","操作失败"+data.msg,"error");
					}
				}
			})
		}
	}
	
	//增添设备按钮
	$('#addBtn').click(function() {
		//清空原有数据
		$('#addForm').form("clear");
		$('#windowAdd').window("open");
	})
	
	//保存新增设备的数据
	$('#saveBtnAdd').click(function() {
		$('#AddForm').form("submit", {
			url : '../Equipment/add?table_name='+table_name,
			onSubmint : function() {
				//判断表单的验证是否通过
				return $('#editForm').form("validate");
			},
			success : function(data) {
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert("提示", "添加成功", "info");
					$('#windowAdd').window("close");
					$('#list').datagrid("reload");
				} else {
					$.messager.alert("提示", "添加失败" + data.msg, "error");
				}
			}
		});
	});
	
	//删除设备按钮
	$('#deleteBtn').click(function(){
		//获取删除的所有行
		var rows = $('#list').datagrid("getSelections");
		if(rows.length == 0){
			$.messager.alert("提示","删除至少选择一行","warning");
			return;
		}
		//格式:id=1&id=3&id=4
		$.messager.confirm("提示","确认删除数据？",function(value){
			if(value){
				var idStr = "";
				$(rows).each(function(i){
					idStr+=("id="+rows[i].id+'&');
				});
				idStr = idStr.substring(0,idStr.length-1);				
				//删除的id编号传向后台
				$.post("delete",idStr,function(data){
					if(data.success){
						$.messager.alert("提示", "删除成功", "info");
						$('#list').datagrid("reload");
					}else{
						$.messager.alert("提示", "删除失败" + data.msg, "error");
					}
				},"json");
			}
		})
	});
	
	//导出excel按钮
	$('#excelBtn').click(function(){
		window.location.href = "${pageContext.request.contextPath}/Equipment/print";
	})
</script>
</html>