<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>猪舍信息修改</title>
	<link rel="stylesheet" href="../resources/layui/css/layui.css">
	<script src="../resources/layui/layui.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
</head>
<body>
	<!-- 用户信息列表 -->
<form class="layui-form" lay-filter="formtest" action="" style="margin:30px;">
  <div class="layui-form-item">
    <label class="layui-form-label">猪舍名称</label>
    <div class="layui-input-block">
      <input type="text" name="name" required  lay-verify="required" placeholder="格式：舍名n" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">负责人</label>
    <div class="layui-input-block">
      <input type="text" name="principal" required  lay-verify="required" placeholder="请输负责人姓名" autocomplete="off" class="layui-input">
    </div>
  </div>
	<div class="layui-form-item">
		    <label class="layui-form-label">所属舍类型</label>
		    <div class="layui-input-block">
		      <select name="type" lay-verify="required" id="table_name">
		        <option value="哺乳舍">哺乳舍</option>
		        <option value="育肥舍">育肥舍</option>
		        <option value="妊娠舍">妊娠舍</option>
		        <option value="保育舍">保育舍</option>
		        <option value="后备舍">后备舍</option>
		        <option value="公猪舍">公猪舍</option>
		      </select>
		    </div>
	</div>
  <div class="layui-form-item">
    <label class="layui-form-label">负责人电话</label>
    <div class="layui-input-block">
      <input type="text" name="phone" required  lay-verify="required" placeholder="请输入电话" autocomplete="off" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-input-block">
      <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
  </div>
</form>

</body>

<script type="text/javascript">
	var init_name = '${name}';
	var company_id = localStorage.getItem('company_id');
	$(function(){
		layui.use(['form','layer'], function(){
			  var form = layui.form;
			  var layer = layui.layer;
				$.ajax({
					url : '../House/queryWithHouse',
					data : {'name': init_name,'company_id': company_id},
					contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
					type :'POST',
					dataType : 'json',
					success : function(json){
						  form.val("formtest", { //formTest 即 class="layui-form" 所在元素属性 lay-filter="" 对应的值
							  "name":json.name
							  ,"type":json.type
							  ,"principal": json.principal // "name": "value"
							  ,"phone": json.phone
							});	
					}
				})
			  //监听提交
			  form.on('submit(formDemo)', function(data){
			    $.ajax({
					url : '../House/update_house?init_name='+init_name+'&company_id='+company_id,
					data : data.field,
					contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
					type :'POST',
					dataType : 'json',
					success : function(json){
						if(json.success){
							layer.alert('修改成功',{
								title : '提示',
								icon : 1
							});
						}else{
							layer.alert('修改失败'+json.msg,{
								title : '提示',
								icon : 2
							});
						}
					}
				});
			    return false;
			  });
			});
	})
</script>
</html>