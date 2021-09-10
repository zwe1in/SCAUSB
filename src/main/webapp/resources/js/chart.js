var equipment_count1 = echarts.init(document.getElementById('equipment_online'));
var equipment_count2 = echarts.init(document.getElementById('equipment_count'));
var environment = echarts.init(document.getElementById('environment_data'));
//设备饼图
option1 = {
    legend: {
        orient: 'horizontal',
        x: 'left',
        y: 'bottom',
        padding: [50,0,0,0],
        fontSize: 15,
        data: [{
            name:'在线设备',
            textStyle:{
                color: 'red',
                fontSize: 15
            }
        },{
            name:'离线设备',
            textStyle:{
                color: 'yellow',
                fontSize: 15
            }
        },{
            name:'故障设备',
            textStyle:{
                color: 'green',
                fontSize: 15
            }
        }]
    },tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    color:['red','yellow','green'],
    series: [
        {
            name: '设备类型',
            type: 'pie',
            radius: '55%',
            data:[
                {value:235, name:'在线设备'},
                {value:274, name:'离线设备'},
                {value:310, name:'故障设备'}
            ],
            radius: ['15%','40%']
        }],
        roseType: 'angle'
};


//设备条形图
function paintBar(title,value){
	option2 = {
		    color: ['#3398DB'],
		    tooltip: {
		        trigger: 'axis',
		        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
		            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: title,
		            axisTick: {
		                alignWithLabel: true
		            },
		            axisLabel: {        
                        show: true,
                        textStyle: {
                            color: '#fff',
                            fontSize:'11'
                        }
                    },
                    axisLine:{
                        lineStyle:{
                            color:'#fff'
                        }
                    }
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            axisLabel: {        
                        show: true,
                        textStyle: {
                            color: '#fff',
                            fontSize:'16'
                        }
                    },
                    axisLine:{
                        lineStyle:{
                            color:'#fff',
                        }
                    }
		        }
		        
		    ],
		    series: [
		        {
		            name: '设备数量',
		            type: 'bar',
		            barWidth: '50%',
		            data: value
		        }
		    ]
		};
	equipment_count2.setOption(option2);
}
equipment_count2.on('click',function(params){
	inner_equipStatus(params.name);
})


//环境数据折线图
function paintLine(data){
	 option3 = {
		        tooltip : {
		            textStyle:{
		                align:'left'
		            },
		            trigger: 'axis',
		            //自定义echarts tooltip的显示位置，当鼠标移动到图表的左部时tip显示在右边，当鼠标移动到图表的右部时tip显示在左边
		            position:function(position){
		                //获取容器的宽度
		                var chartsWidth = $("#threadtrend").width();
		                //判断悬停点落在容器的哪测
		                if(position[0] < (chartsWidth/2)){
		                    position[0] = position[0];
		                }else{
		                position[0] = position[0] - 130;
		                }
		                return [position[0], position[1]];
		            }
		        },
		        xAxis:{
		            type: 'time',
		            splitNumber: 18,
		            axisLabel:{
		                color:'#ffffff'
		            },
		            axisLine:{
		                show: true,
		                symbol: ['none','arrow'],
		                symbolSize: [10,10],
		                symbolOffset: [0,20],
		                lineStyle:{
		                    color:'#FFFFFF',
		                    width:1,
		                    type:'solid',
		                }
		            },
		            splitLine:{
		                show: false
		            }
		        },
		        yAxis:{
		            type: 'value',
		            axisLabel:{
		                formatter:'{value} ',
		                color: '#ffffff',
		                fontSize: 15
		            },
		            axisLine:{
		                show: true,
		                symbol: ['none','arrow'],
		                symbolSize: [10,10],
		                symbolOffset: [0,20],
		                lineStyle:{
		                    color:'#FFFFFF',
		                    width:1,
		                    type:'solid',
		                }
		            },
		            splitLine:{
		                show: false
		            }
		        },
		        series:{
		            name: '氨气含量',
		            type: 'line',
		            data: data,
		            symbolSize: 8
		        }
		    };
environment.setOption(option3);
}

equipment_count1.setOption(option1);
equipment_count1.on('click',function(params){
	equipStatus(params.name);
})
