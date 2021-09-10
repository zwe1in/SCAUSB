<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>视频播放</title>
<script type="text/javascript" src="../resources/js/ezuikit.js"></script>
</head>
<body>
	<fieldset>
		<legend>惠州镇隆</legend>
		<video id="hzzl" controls playsInline webkit-playsinline [autoplay]>
			<source src="rtmp://rtmp01open.ys7.com/openlive/9b1f9c8d9ddf44adba7acf069b02add4" type=""/>
		</video>
	</fieldset>
	
	<fieldset>
		<legend>阳西新圩</legend>
		<video id="yxxy" controls playsInline webkit-playsinline [autoplay]>
			<source src="rtmp://rtmp01open.ys7.com/openlive/c2f3c0d5dfd642df912a3f2a87ced100" type=""/>	
		</video>	
	</fieldset>
</body>

<script type="text/javascript">
	var hzzl = new EZUIPlayer('hzzl');
	var yxxy = new EZUIPlayer('yxxy');
</script>
</html>