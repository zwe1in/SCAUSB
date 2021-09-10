let role = 0;     //0-管理员，1-普通用户
let data_map = {}
var user_name = localStorage.getItem("user_name");

window.onload = function () {
	startTime();
	//时间日期生成
	function startTime(){
        var week=["日","一","二","三","四","五","六"];
        var today=new Date();
        var year = today.getFullYear();
        var month = today.getMonth();
        var date = today.getDate();
        var h=today.getHours();
        var m=today.getMinutes();
        var s=today.getSeconds();// 在小于10的数字钱前加一个‘0’
        var day=today.getDay();
        m=checkTime(m);
        s=checkTime(s);
        var cur = document.getElementById('Date');
        var span = cur.getElementsByTagName('span');
        span[0].innerText = year+"年"+month+"月"+date+"日";
        span[1].innerText = "星期"+week[day];
        span[2].innerText = h+":"+m+":"+s;
        t=setTimeout(function(){startTime()},500);
    }
    function checkTime(i){
        if (i<10){
            i="0" + i;
        }
        return i;
    }
    document.getElementsByClassName("admin")[0].innerText = user_name;
  let second = document.getElementsByClassName('second')[0],
      second_1 = document.getElementsByClassName('second-1')[0],
      second_2 = document.getElementsByClassName('second-2')[0],
      content_image = document.getElementsByClassName('content-image')[0],
      switches = document.getElementsByClassName('switches')[0],
      des = document.getElementsByClassName('des')[0],
      equip_s = document.getElementsByClassName('equip-s')[0],
      time = document.getElementsByClassName('header-time')[0];
  if(role === 0) {
    second.style.height = 'auto';
    second_1.style.padding = '0 20px';
    second_1.style.flex = '1 1 560px';
    content_image.style.height = '530px';
    switches.style.display = 'none';
    des.style.top = '15px';
    second_2.style.display = 'block';
    equip_s.style.display = 'none';
    time.style.display = 'block';
  } else {
    second.style.height = '860px';
    second_1.style.padding = '20px';
    second_1.style.flex = '1 1 860px';
    content_image.style.height = '760px';
    switches.style.display = 'block';
    des.style.top = '140px';
    second_2.style.display = 'none';
    equip_s.style.display = 'block';
    time.style.display = 'none';
  }

  layui.use(['form', 'slider', 'laydate', 'layedit','form'], function(){
    let $ = layui.$ ,
        slider = layui.slider,
        layDate = layui.laydate,
        form = layui.form;
    $.ajax({
    	url : '../Command/get',
    	data : {},
    	contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
        type:'POST',
        dataType: 'json',
        async: false,
        success : function(json){
        	//除臭剂
        	form.val("formtest",{
            	"ccj": json.n79
            });
        	//滑动条数据
        	data_map.n0 = [0,json.n0];
        	data_map.n1 = [0,json.n1];
        	data_map.n2 = [0,json.n2];
        	data_map.n3 = [0,json.n3];
        	for(var key in json){
        		console.log(key+":"+json[key]);
        		if(json[key] == 1){
        			var el = document.getElementById(key);
        			var className = el.className;        			
        			trigger(el, className.split(" ")[1],false);
        		}
        	}
        	board(json);
        }
    })
    //数据栏填充
    function board(json){
    	var obj = document.getElementById('board');
    	var text = "<p>液位高度:"+json['n2']+"cm</p>";
    	text += "<p>PH值:"+json['n0']+"</p>";
    	text += "<p>EC值:"+json['n1']+"</p>";
    	text += "<p>压差:"+json['n3']+"Pa</p>";
    	text += "<p>温度:"+json['n41']+"°C</p>";
    	text += "<p>湿度:"+json['n42']+"</p>";
    	text += "<p>气压值:"+json['n43']+"Pa</p>";
    	text += "<p>舍外含雨量:"+json['n46']+"mm</p>";
    	obj.innerHTML += text;
    }
    
    //滑动条变化提交
    Array.from(document.getElementsByClassName('slider')).forEach(el => {
      var id = el.id;
      slider.render({
        elem: el,
        value: data_map[id],
        max: 100,
        range: true,
        change: function(vals){
        	console.log(vals[0]);
        	console.log(vals[1]);
//        	alert(vals[0]+"->"+vals[1]);
        	console.log(id);
        	$.ajax({
        		url:'../Command/data',
                data: {'type':id,'value_up':vals[1],"value_down":0},
                contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
                type:'POST',
                dataType: 'json',
                success:function(json){
                	if(json.success){
                		
                	}else{
                		alert(json.msg);
                	}
                }
        	})
        }
      });

      for(let i = 1;i <= 4;i++) {
        layDate.render({
          elem: '#time' + i
          ,type: 'datetime'
          ,range: true
        });
      }
    });

  });

  document.getElementById('mode-control').addEventListener('click', function (evt) {
    let el = evt.target;
    if(!el.classList.contains('active')) {
      el.classList.add('active');
    }
    if(el.nextSibling.className && el.nextSibling.classList.contains('active')) {
      el.nextSibling.classList.remove('active');
    }
    if(el.previousSibling.className && el.previousSibling.classList.contains('active')) {
      el.previousSibling.classList.remove('active');
    }
  })
};

//开关数据回显
function trigger_data(){
	//访问数据
	//遍历，已经开启则强行触发对应div的点击事件
}

//开关数据传入后台
function shift(type,status){
	$.ajax({
		url:'../Command/status',
        data: {'type':type,'status':status},
        contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
        type:'POST',
        dataType: 'json',
        success:function(json){
        	if(json.success){
        		alert("ok");
        	}else{
        		alert(json.msg);
        	}
        }
	})
}

function trigger(el, id, flag) {
  let imgDom = el.childNodes[3];
  if(el.getElementsByClassName('switch-open').length !== 0) {
    el.getElementsByClassName('switch-open')[0].className = 'switch-close';
    el.getElementsByClassName('switch-close')[0].innerHTML = '关闭';
    imgDom.className = 'slide-left';
  } else {
    el.getElementsByClassName('switch-close')[0].className = 'switch-open';
    el.getElementsByClassName('switch-open')[0].innerHTML = '开启';
    imgDom.className = 'slide-right';
  }

  if(role === 0) {
    let flag = Array.from($('.switch')).some(el => {
      return el.getElementsByClassName('switch-open').length !== 0;
    });

    if(flag) {
      $('#x-yws-i').hide(); $('#y-yws-i').hide(); $('#p-yws-i').hide(); $('#e-yws-i').hide();
      $('#x-yws-g').show(); $('#y-yws-g').show(); $('#p-yws-g').show(); $('#e-yws-g').show();
    } else {
      $('#x-yws-g').hide(); $('#y-yws-g').hide(); $('#p-yws-g').hide(); $('#e-yws-g').hide();
      $('#x-yws-i').show(); $('#y-yws-i').show(); $('#p-yws-i').show(); $('#e-yws-i').show();
    }
  }

  if(imgDom.className === 'slide-left') {         //关闭
    switch (id) {
      case 'equip-s':
        $('#lb-cur1-g').hide(); $('#lb-cur2-g').hide(); $('#chuchouguan-g').hide();
        $('#y-straight-g').hide(); $('#y-curved-g').hide(); $('#lg-curved-g').hide();
        $('#shuichi-g').hide(); $('#x-yws-g').hide(); $('#b-curved2-g').hide();
        $('#b-curved-g').hide(); $('#b-straight-g').hide(); $('#y-yws-g').hide();
        $('#p-yws-g').hide(); $('#e-yws-g').hide(); $('#lg-straight-g').hide();
        $('#feiyechi-g').hide(); $('#g-curved1-g').hide(); $('#g-curved2-g').hide();
        $('#pwc-g').hide();

        $('#lb-cur1-i').show(); $('#lb-cur2-i').show(); $('#chuchouguan-i').show();
        $('#y-straight-i').show(); $('#y-curved-i').show(); $('#lg-curved-i').show();
        $('#shuichi-i').show(); $('#x-yws-i').show(); $('#b-curved2-i').show();
        $('#b-curved-i').show(); $('#b-straight-i').show(); $('#y-yws-i').show();
        $('#p-yws-i').show(); $('#e-yws-i').show(); $('#lg-straight-i').show();
        $('#feiyechi-i').show(); $('#g-curved1-i').show(); $('#g-curved2-i').show();
        $('#pwc-i').show();
        break;
      case 'chuchou':
        $('#y-straight-g').hide(); $('#y-curved-g').hide(); $('#chuchouguan-g').hide();
        $('#y-straight-i').show(); $('#y-curved-i').show(); $('#chuchouguan-i').show();
        if(flag)
        shift(el.id,0);
        break;
      case 'xidi':
        let hasXidi = Array.from($('.xidi')).some(el => {
          return el.getElementsByClassName('switch-open').length !== 0;
        });

        if(!hasXidi) {
          $('#b-curved2-g').hide(); $('#b-curved-g').hide();
          $('#b-curved2-i').show(); $('#b-curved-i').show();
        }
        if(flag)
        shift(el.id,0);
        break;
      case 'xunhuan':
        $('#lb-cur1-g').hide(); $('#lb-cur2-g').hide(); $('#shuichi-g').hide();
        $('#lb-cur1-i').show(); $('#lb-cur2-i').show(); $('#shuichi-i').show();
        if(flag)
        shift(el.id,0);
        break;
      case 'xiwu':
        $('#g-curved1-g').hide(); $('#g-curved2-g').hide(); $('#pwc-g').hide();
        $('#g-curved1-i').show(); $('#g-curved2-i').show(); $('#pwc-i').show();
        if(flag)
        shift(el.id,0);
        break;
      case 'tingpen':
        let hasTingpen = Array.from($('.tingpen')).some(el => {
          return el.getElementsByClassName('switch-open').length !== 0;
        });

        if(!hasTingpen) {
          $('#b-straight-g').hide(); $('#b-curved-g').hide();
          $('#b-straight-i').show(); $('#b-curved-i').show();
        }
        if(flag)
        shift(el.id,0);
        break;
      case 'paiye':
        $('#lg-curved-g').hide(); $('#lg-straight-g').hide(); $('#feiyechi-g').hide();
        $('#lg-curved-i').show(); $('#lg-straight-i').show(); $('#feiyechi-i').show();
        if(flag)
        shift(el.id,0);
        break;
    }
  } else {       //开启
    switch (id) {
      case 'equip-s':
        $('#lb-cur1-i').hide(); $('#lb-cur2-i').hide(); $('#chuchouguan-i').hide();
        $('#y-straight-i').hide(); $('#y-curved-i').hide(); $('#lg-curved-i').hide();
        $('#shuichi-i').hide(); $('#x-yws-i').hide(); $('#b-curved2-i').hide();
        $('#b-curved-i').hide(); $('#b-straight-i').hide(); $('#y-yws-i').hide();
        $('#p-yws-i').hide(); $('#e-yws-i').hide(); $('#lg-straight-i').hide();
        $('#feiyechi-i').hide(); $('#g-curved1-i').hide(); $('#g-curved2-i').hide();
        $('#pwc-i').hide();

        $('#lb-cur1-g').show(); $('#lb-cur2-g').show(); $('#chuchouguan-g').show();
        $('#y-straight-g').show(); $('#y-curved-g').show(); $('#lg-curved-g').show();
        $('#shuichi-g').show(); $('#x-yws-g').show(); $('#b-curved2-g').show();
        $('#b-curved-g').show(); $('#b-straight-g').show(); $('#y-yws-g').show();
        $('#p-yws-g').show(); $('#e-yws-g').show(); $('#lg-straight-g').show();
        $('#feiyechi-g').show(); $('#g-curved1-g').show(); $('#g-curved2-g').show();
        $('#pwc-g').show();

        $('#y-curved-g').css({ 'top': '26.1%' });
        $('#b-curved2-g').css({ 'left': '39%', 'width': '9.5%', 'top': '63.3%' });
        $('#lg-straight-g').css({ 'width': '12%' });
        $('#y-straight-g').css({ 'left': '18.5%' });
        $('#b-straight-g').css({ 'width': '15.5%', 'left': '70.7%' });
        $('#lb-cur2-g').css({ 'top': '40.3%', 'height': '33%' });
        $('#g-curved1-g').css({ 'width': '38.5%', 'height': '10.5%' });
        $('#g-curved2-g').css({ 'width': '28.5%' });
        $('#lg-curved-g').css({ 'width': '27.8%' });
        break;
      case 'chuchou':
        $('#y-straight-i').hide(); $('#y-curved-i').hide(); $('#chuchouguan-i').hide();
        $('#y-straight-g').show(); $('#y-curved-g').show(); $('#chuchouguan-g').show();
        $('#y-straight-g').css({ 'left': '18.5%' });
        $('#y-curved-g').css({ 'top': '26.1%' });
        if(flag)
        shift(el.id,1);
        break;
      case 'xidi':
        $('#b-curved2-i').hide(); $('#b-curved-i').hide();
        $('#b-curved2-g').show(); $('#b-curved-g').show();
        $('#b-curved2-g').css({ 'left': '39%', 'width': '9.5%', 'top': '63.3%' });
        if(flag)
        shift(el.id,1);
        break;
      case 'xunhuan':
        $('#lb-cur1-i').hide(); $('#lb-cur2-i').hide(); $('#shuichi-i').hide();
        $('#lb-cur1-g').show(); $('#lb-cur2-g').show(); $('#shuichi-g').show();
        $('#lb-cur2-g').css({ 'top': '40.3%', 'height': '33%' });
        if(flag)
        shift(el.id,1);
        break;
      case 'xiwu':
        $('#g-curved1-i').hide(); $('#g-curved2-i').hide(); $('#pwc-i').hide();
        $('#g-curved1-g').show(); $('#g-curved2-g').show(); $('#pwc-g').show();
        $('#lb-cur2-g').css({ 'top': '40.3%', 'height': '33%' });
        $('#g-curved1-g').css({ 'width': '38.5%', 'height': '10.5%' });
        $('#g-curved2-g').css({ 'width': '28.5%' });
        if(flag)
        shift(el.id,1);
        break;
      case 'tingpen':
        $('#b-straight-i').hide(); $('#b-curved-i').hide();
        $('#b-straight-g').show(); $('#b-curved-g').show();
        $('#b-straight-g').css({ 'width': '15.5%', 'left': '70.7%' });
        if(flag)
        shift(el.id,1);
        break;
      case 'paiye':
        $('#lg-curved-i').hide(); $('#lg-straight-i').hide(); $('#feiyechi-i').hide();
        $('#lg-curved-g').show(); $('#lg-straight-g').show(); $('#feiyechi-g').show();
        $('#lg-curved-g').css({ 'width': '27.8%' });
        $('#lg-straight-g').css({ 'width': '12%' });
        if(flag)
        shift(el.id,1);
        break;
    }
  }
}