//侧栏菜单按钮提示
$(document).ready(function(){
    $(".bttn").mouseover(function(){
        var id = $(this).attr('id');
        var text = '#tips_'+id;
        $(text).show('fast');
    });
    $('.bttn').mouseout(function(){
        $('.tips').hide('fast');
    })
});

function getformatDate(date) {  
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? '0' + m : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    return y + '-' + m + '-' + d;  
}

//环境数据按钮
function change(num,type){
	var date = $('#date').val();
    for(var i=1; i<=6;i++){
        var id = 'btn'+i;
        document.getElementById(id).style.backgroundColor = 'rgb(4, 51, 138)';
    }
    var n = 'btn'+num;
    document.getElementById(n).style.backgroundColor = 'rgb(1, 1, 59)';
    btnOn = num+index;
  //改变图表格
    table_name = $('#place').val();
    $.ajax({
        url:'../Paint/lineChart',
        data: {'type':type,'date':date,"table_name":table_name},
        contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
        type:'POST',
        dataType: 'json',
        success:function(data){
      	  paintLine(data);
        }
    });
}

//设备警告按钮
function warn(){
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.open({
            type: 2,
            area: ['700px','450px'],
            title: '设备警报信息',
            fixed: false,
            // maxmin: true,
            content: 'warn.html' 
        })
    })
}

//查询故障设备信息
function getError(){
	var table_name = $('#inner_equip').val();
	layui.use('layer', function(){
        var layer = layui.layer;
        layer.open({
            type: 2,
            area: ['700px','450px'],
            title: status+'列表',
            fixed: false,
            // maxmin: true,
            content: 'error_inner_equip?table_name='+table_name 
        })
    });
}

//查看各个执行设备数据
function inner_equipStatus(name){
	var itable_name = $('#inner_equip').val();
	localStorage.setItem("itable_name",itable_name);
	localStorage.setItem("name",name);
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.open({
            type: 2,
            area: ['700px','450px'],
            title: status+'列表',
            fixed: false,
            // maxmin: true,
            content: 'inner_equip' 
        })
    })
}

//查看各个状态的设备
function equipStatus(status){
	var content = "";
	if(status == "在线设备"){
		content = "on";
	}else if(status == "离线设备"){
		content = "close";
	}else if(status == "故障设备"){
		content = "error";
	}
	localStorage.setItem("equipStatus",content);
	localStorage.setItem("text",status);
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.open({
            type: 2,
            area: ['700px','450px'],
            title: status+'列表',
            fixed: false,
            // maxmin: true,
            content: 'equipStatus.html' 
        })
    })
}


//环境因素警报按钮
function alarm(){
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.open({
            type: 2,
            area: ['700px','450px'],
            title: '环境预警信息',
            fixed: false,
            // maxmin: true,
            content: 'alarm.html' 
        })
    })
}

//判断该页是否有按钮按下
function isBtnOn(){
    //若已按下的按钮不在该列，则情况所有背景色
    if(btnOn<=index || btnOn>index+6){
        for(var i=1; i<=6;i++){
            var id = 'btn'+i;
            document.getElementById(id).style.backgroundColor = 'rgb(4, 51, 138)';
        }
    }else{
    	var id = "";
    	if(btnOn%6 == 0){
    		id = 'btn6';
    	}else{
    		id = 'btn'+btnOn%6;
    	}
    	document.getElementById(id).style.backgroundColor = 'rgb(1, 1, 59)';
    }
}

//环境数据筛选按钮
function toRight(){
    index +=6;
    isBtnOn();
    var len = dataType.length<index+6?dataType.length%6:6;
    var i = 0;
    for(i; i < len; i++){
        var id =  "btn"+(i+1);
        document.getElementById(id).innerText = dataType[index+i];
        (function(arg){
        	document.getElementById(id).onclick = function(){
        		change(arg+1,innerdata[arg+index]);
            };
        })(i) 
    }
    //清空标题
    while(i<6){
        var id =  "btn"+(i+1);
        document.getElementById(id).innerText = "";
        i++;
    }
    document.getElementById('img1').src = "../resources/images/arrow_left.png";
    var left = document.getElementById('left');
    left.setAttribute('href','javascript:void(0)');
    left.onclick = toLeft;
    if(dataType.length<index+6){
        document.getElementById('img2').src = "../resources/images/page_last.png";
        var right = document.getElementById('right');
        right.removeAttribute('href');
        right.onclick = null;
    }
}

function toLeft(){
    index-=6;
    isBtnOn();
    for(var i = 0; i < 6; i++){
        var id = "btn"+(i+1);
        document.getElementById(id).innerText = dataType[index+i];
        (function(arg){
        	document.getElementById(id).onclick = function(){
        		change(arg+1,innerdata[arg+index]);
            };
        })(i) 
    }
    document.getElementById('img2').src = "../resources/images/arrow_right.png";
    var right = document.getElementById('right');
    right.setAttribute('href','javascript:void(0)');
    right.onclick = toRight;
    if(index <= 0){
        document.getElementById('img1').src = "../resources/images/page_first.png";
        var left = document.getElementById('left');
        left.removeAttribute('href');
        left.onclick = null;
    }
}