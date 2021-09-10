<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>设备展示</title>
	<link rel="stylesheet" href="../resources/layui/css/layui.css">
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
	<script src="../resources/layui/layui.js"></script>
</head>
<body>
	<div class="side " style="width:20%; height: 100%; float: left; margin:5px;">
		<div class="layui-collapse">
			<div class="layui-colla-item">
				<h2 class="layui-colla-title">设备ID列表</h2>
				<div class="layui-colla-content layui-show">
					<div id="tree"></div>
				</div>
			</div>
		</div>
	</div>

	<div class="body" style="width:70%; height: 100%; float: left; margin:5px;">
		<form class="layui-form" action="" style="display : none;">
			<div class="layui-form-item">
			 	<div class="layui-inline">
			 		<label class="layui-form-label">起始时间</label>
				    <div class="layui-input-inline" style="width: 200px;">
				      <input type="text" name="time_start" id="time_start" placeholder="" autocomplete="off" class="layui-input">
				    </div>
			 	</div>
			 	<div class="layui-inline">
			 		<label class="layui-form-label">结束时间</label>
				    <div class="layui-input-inline" style="width: 200px;">
				      <input type="text" name="time_out" id="time_out" placeholder="" autocomplete="off" class="layui-input">
				    </div>
			 	</div>
			 	<div class="layui-inline">
			 		<button class="layui-btn" lay-submit lay-filter="formDemo">查询</button>
			 	</div>
			</div>
		</form>
		<table id="list"></table>
	</div>
</body>

<script type="text/html" id="bar">
  <a class="layui-btn layui-btn-xs" lay-event="edit">开启/关闭</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>



<script type="text/javascript">
	var type = '${type}';
	var company_id = localStorage.getItem('company_id');
	var group_id = localStorage.getItem('group_id');
	var user_name = localStorage.getItem('user_name');
	var table_name = '';
	var title = [];
//获取树数据
$.ajax({
	url : '../House/getEquipTree',
	data : {'type': type,'company_id':company_id,'group_id':group_id,'user_name':user_name},
	contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
	type :'POST',
	dataType : 'json',
	success : function(json){
		console.log(json)
		layui.use(['tree','table','layer','laydate','form'],function(){
			var tree = layui.tree;
			var table = layui.table;
			var layer = layui.layer;
			var laydate = layui.laydate;
			var form = layui.form;
			//渲染树
			tree.render({
				elem : '#tree',
				data : json,
				click : function(obj){
					table_name = obj.data.id;
			    	var titles = [];
					getTitle(table_name);
					titles.push(title);
					if(table_name != null){
						table.render({
						    elem: '#list'
						    ,toolbar: '#toolbar'
						    ,even: true //开启隔行背景
						    ,url: '../Equipment/getAllData?table_name='+table_name 
						    ,page: true //开启分页
						    ,limits: [10,20,30]
				  			,limit : 10
						    ,cols: titles
						  });
						document.getElementsByTagName('form')[0].style.display = "";
					}
				}
			});
			laydate.render({
				elem : '#time_start',
				type : 'datetime'
			});
			laydate.render({
				elem : '#time_out',
				type: 'datetime'
			});
			form.on('submit(formDemo)',function(data){
				var start = data.field.time_start;
				var out = data.field.time_out;
				var titles = [];
				getTitle(table_name);
				titles.push(title);
				table.render({
				    elem: '#list'
				    ,toolbar: '#toolbar'
				    ,even: true //开启隔行背景
				    ,url: '../Equipment/getAllDataByTime?start='+start+'&out='+out+'&table_name='+table_name 
				    ,page: true //开启分页
				    ,limits: [10,20,30]
		  			,limit : 10
				    ,cols: titles
				  });
				return false;
			})
		})
	}
})


	function getTitle(table_name){
		$.ajax({
			url : '../Equipment/getTableTitle',
			data : {'table_name':table_name},
			contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
			type :'POST',
			dataType : 'json',
			async : false,
			success : function(json){
				if(json.success){
					title = json.title;
					var temp = {field: "timestamp",title: "更新时间",width:300,sort:true,templet:function(d){return dateFormat(d.timestamp);}}
					title.push(temp);
				}else{
					alert('数据加载失败');
				}
			}
		})
	}

	function dateFormat(time){
		var date = new Date(time);
		return date.toLocaleString(date);
	}
	Date.prototype.toLocaleString = function() {
        return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate() + "-" + this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds();
  };
</script>
</html>