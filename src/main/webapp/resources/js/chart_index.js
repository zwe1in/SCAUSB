var en_list = echarts.init(document.getElementById('equipment_count'));
var environment = echarts.init(document.getElementById('environment_data'));
var pig_list = echarts.init(document.getElementById('pig_count'));
var d_list = echarts.init(document.getElementById('deodorization_count'));

//猪只信息
option1 = {
    color: ['#006699', '#e5323e'],
    splitLine:{
        show: false
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    xAxis: {
        type: 'category',
        data: ['保育', '育肥', '妊娠', '公猪', '哺乳'],
        axisLabel:{
            textStyle:{
                color: '#FFFFFF',
                fontSize: 13
            }
        },
        axisLine:{
            show: true,
            symbolSize: [10,10],
            symbolOffset: [0,20],
            lineStyle:{
                color:'#FFFFFF',
                width:1,
                type:'solid',
            }
        },
    },
    yAxis: {
        axisLine: {
            show: false
        },
        axisTick: {
            show: false
        },
        axisLabel: {
            show: false,
            textStyle: {
                color: '#FFFFFF',
                fontSize: 15
            }
        },
        splitLine:{
            show: false
        }
    },
    legend:{
        data: ['健康猪只','生病猪只'],
        textStyle:{
            color: '#ffffff',
            fontSize: 15
        }
    },
    series: [
        {
            name: '健康猪只',
            type: 'bar',
            barGap: 0,
            label:{
                show: true,
                position: "top",
                textStyle: {
                    color: '#ffffff',
                    fontSize: 16,
                    fontWeight: 600
                    }
                },
            data: [10, 42, 9, 34, 10]
        },
        {
            name: '生病猪只',
            type: 'bar',
            label:{
                show: true,
                position: "top",
                textStyle: {
                    color: '#ffffff',
                    fontSize: 16,
                    fontWeight: 600
                    }
                },
            data: [10, 32, 50, 15, 19]
        }
    ]
};

//设备条形图
option2 = {
    title:{
        show: true,
        text: '环控设备信息',
        x: '40',
        y: 'top',
        textStyle: {
            color: '#ffffff',
        }
    },
    color: ['#006699', '#f9f900', '#e5323e'],
    splitLine:{
        show: false
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    xAxis: {
        type: 'category',
        data: ['风机', '通风口', '电机', '温度仪', '报警器'],
        axisLabel:{
            textStyle:{
                color: '#FFFFFF',
                fontSize: 13
            }
        },
        axisLine:{
            show: true,
            symbolSize: [10,10],
            symbolOffset: [0,20],
            lineStyle:{
                color:'#FFFFFF',
                width:1,
                type:'solid',
            }
        },
    },
    yAxis: {
        axisLine: {
            show: false
        },
        axisTick: {
            show: false
        },
        axisLabel: {
            show: false,
            textStyle: {
                color: '#FFFFFF',
                fontSize: 15
            }
        },
        splitLine:{
            show: false
        }
    },
    legend:{
        data: ['在线设备','离线设备','故障设备'],
        textStyle:{
            color: '#ffffff',
            fontSize: 15
        },
        x: 'center',
        y: '25'
    },
    series: [
        {
            name: '在线设备',
            type: 'bar',
            barGap: 0,
            label:{
                show: true,
                position: "top",
                textStyle: {
                    color: '#ffffff',
                    fontSize: 16,
                    fontWeight: 600
                    }
                },
            data: [20, 32, 9, 34, 30]
        },
        {
            name: '离线设备',
            type: 'bar',
            label:{
                show: true,
                position: "top",
                textStyle: {
                    color: '#ffffff',
                    fontSize: 16,
                    fontWeight: 600
                    }
                },
            data: [20, 12, 11, 34, 20]
        },
        {
            name: '故障设备',
            type: 'bar',
            label:{
                show: true,
                position: "top",
                textStyle: {
                    color: '#ffffff',
                    fontSize: 16,
                    fontWeight: 600
                    }
                },
            data: [10, 32, 20, 15, 19]
        }
    ]
};

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

//除臭设备
option4 = {
    title:{
        show: true,
        text: '除臭设备信息',
        x: '40',
        y: 'top',
        textStyle: {
            color: '#ffffff'
        }
    },
    color: ['#006699', 'rebeccapurple', '#e5323e'],
    splitLine:{
        show: false
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    xAxis: {
        type: 'category',
        data: ['风机', '通风口', '电机', '温度仪', '报警器'],
        axisLabel:{
            textStyle:{
                color: '#FFFFFF',
                fontSize: 13
            }
        },
        axisLine:{
            show: true,
            symbolSize: [10,10],
            symbolOffset: [0,20],
            lineStyle:{
                color:'#FFFFFF',
                width:1,
                type:'solid',
            }
        },
    },
    yAxis: {
        axisLine: {
            show: false
        },
        axisTick: {
            show: false
        },
        axisLabel: {
            show: false,
            textStyle: {
                color: '#FFFFFF',
                fontSize: 15
            }
        },
        splitLine:{
            show: false
        }
    },
    legend:{
        data: ['在线设备','离线设备','故障设备'],
        textStyle:{
            color: '#ffffff',
            fontSize: 15
        },
        x: 'center',
        y: '25'
    },
    series: [
        {
            name: '在线设备',
            type: 'bar',
            barGap: 0,
            label:{
                show: true,
                position: "top",
                textStyle: {
                    color: '#ffffff',
                    fontSize: 16,
                    fontWeight: 600
                    }
                },
            data: [20, 32, 9, 34, 15]
        },
        {
            name: '离线设备',
            type: 'bar',
            label:{
                show: true,
                position: "top",
                textStyle: {
                    color: '#ffffff',
                    fontSize: 16,
                    fontWeight: 600
                    }
                },
            data: [10, 12, 23, 14, 20]
        },
        {
            name: '故障设备',
            type: 'bar',
            label:{
                show: true,
                position: "top",
                textStyle: {
                    color: '#ffffff',
                    fontSize: 16,
                    fontWeight: 600
                    }
                },
            data: [13, 23, 12, 15, 10]
        }
    ]
};
pig_list.setOption(option1);
en_list.setOption(option2);
d_list.setOption(option4);