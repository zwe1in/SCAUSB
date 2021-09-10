<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>设备图标统计</title>
	<script type="text/javascript" src="../resources/echarts/echarts.min.js"></script>
	<link rel="stylesheet" type="text/css" href="../resources/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../resources/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../resources/css/demo/demo.css">
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../resources/js/easyui-lang-zh_CN.js"></script>
</head>
<body>
	 <div id="main" style="width: 600px;height:400px;"></div>
</body>
<script type="text/javascript">
	var data;
	$.ajax({
		url :'${pageContext.request.contextPath}/Equipment/getPaint',
		data:'',
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		type : 'POST',
		success : function(data){
			if(data.success){
				var msg = data.msg;
				data = data.data;
				paint(data);
			}else{
				$.messager.alert("警告","数据传输错误"+msg,"error");
			}
		}
	})
	
	//绘饼图函数
	function paint(data){
		option = {
				title : {
					text : '设备情况统计',
					x : 'center'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				legend : {
					orient : 'vertical',
					left : 'left',
					data : [ '饲养设备', '环控设备', '监控设备', '故障设备', '中间服务设备' ]
				},
				series : [ {
					name : '访问来源',
					type : 'pie',
					radius : '55%',
					center : [ '50%', '60%' ],
					data : data,
					itemStyle : {
						emphasis : {
							shadowBlur : 10,
							shadowOffsetX : 0,
							shadowColor : 'rgba(0, 0, 0, 0.5)'
						}
					}
				} ]
			};
			var myChart = echarts.init(document.getElementById('main'));
			myChart.setOption(option);
	}
</script>
</html>