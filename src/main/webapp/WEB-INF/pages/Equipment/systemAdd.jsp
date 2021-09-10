<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新类型系统添加</title>
	<link rel="stylesheet" href="../resources/layui/css/layui.css">
	<script src="../resources/layui/layui.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
</head>
<body>
<div style="width:50%; margin-left:25%;">
	<form class="layui-form layui-form-pane" lay-filter="formtest" action="">
		<div class="layui-form-item" pane>
			<label class="layui-form-label">设备ID</label>
			<div class="layui-input-block">
				<input type="text" name="id" id="id" required  lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">主节点</label>
			<div class="layui-input-block">
				<input type="text" name="G" required  lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">从节点</label>
			<div class="layui-input-block">
				<input type="text" name="N" required  lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">类型(中)</label>
			<div class="layui-input-block">
				<input type="text" name="type_value" required  lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">类型(英)</label>
			<div class="layui-input-block">
				<input type="text" name="type" required  lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">纬度位置</label>
			<div class="layui-input-block">
				<input type="text" name="longitude" required  lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item" pane>
			<label class="layui-form-label">经度位置</label>
			<div class="layui-input-block">
				<input type="text" name="latitude" required  lay-verify="required" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item layui-upload">
			<label class="layui-form-label">系统图标</label>
			<button type="button" class="layui-btn" id="test1">
				<i class="layui-icon">&#xe67c;</i>上传图片
			</button>
			<blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                	预览图：
                <div class="layui-upload-list" id="demo"></div>
            </blockquote>

		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" id="commit" lay-submit lay-filter="formDemo">下一步</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>
</div>

</body>
<script type="text/javascript">
	var place = '${place}';
	var fileObj;
	var company_id = localStorage.getItem("company_id");
	layui.use(['layer','form','upload'],function(){
		var upload = layui.upload;
		var layer = layui.layer;
		var form = layui.form;
		upload.render({
			elem: '#test1' 
			    ,url: '../Equipment/imagePreUpload'
			   	,accept:"images"
			   	,choose: function (obj) {
                    //预读本地文件示例，不支持ie8
                    obj.preview(function (index, file, result) {
                    	fileObj = file;
                        $('#demo').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img" style="width:184px;height:135px">')
                    });
                }
			    ,done: function(res){
			      if(res.success){
			    	  layer.alert('上传图片成功',{
			    		  title : '提示',
			    		  icon : 1
			    	  });
			      }
			    }
			    ,error: function(){
			      layer.alert('上传失败',{
			    	  title : '提示',
			    	  icon : 2
			      });
			    }
		});
		//表单提交
		form.on('submit(formDemo)', function(data){
			var id = $('#id').val();
			var name = $('#type_value').val();
			let formData = new FormData();
			formData.append('file',fileObj);
			localStorage.setItem("e_id",id);
			//信息上传
			imageload(formData,name);
			return false;
		});
		
	//图片上传保存
	function imageload(fileData,name){
		$.ajax({
			url : '../Equipment/imageUpload?name='+name,
			data : formData,
			type:"POST",
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false,
			success : function(data){
				if(data.success){
					layer.alert("图片保存成功", {
						title : '提示',
						icon : 1,
						end :function(){							
							//上传表单
							formupload(data.field);
						}
					});
				}else{
					layer.alert("图片保存失败,创建失败", {
						title : '提示',
						icon : 2
					});
				}
			}
		});
	}
	
	//表单内容上传
	function formupload(account_id,data){
		$.ajax({
			url : '../Equipment/addSystem?company_id='+company_id+'&place='+place,
			data : data,
			contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
			type :'POST',
			dataType : 'json',
			success : function(json){
				if(json.success){
					layer.alert('添加成功',{
						title : '提示',
						icon : 1,
						end : function(){
							window.location.href = "../System/addElement";
						}
					});
					
				}else{
					layer.alert('添加失败'+json.msg,{
						title : '提示',
						icon : 2
					});
				}
			}
		});
	}
	});
	
</script>
</html>