var user_name = localStorage.getItem("user_name");
var group_id = localStorage.getItem("group_id");
console.log(group_id);
createHeader(group_id, user_name);
function createHeader(group_id, user_name) {
	switch (parseInt(group_id)) {
	case 1:
		$('#level').append('企业级管理员');
		break;
	case 2:
		$('#level').append('区域级管理员');
		break;
	case 3:
		$('#level').append('市级管理员');
		break;
	case 4:
		$('#level').append('场级管理员');
		break;
	}
	$('#user_name').append(user_name);
}
var account = [{
    title :'个人信息管理',
    icon : 'layui-icon-friends',
    children : [{
      id : 'rootInfo',
      icon : 'layui-icon-username',
      title : '个人信息查询',
      dataurl : '../Accountant/Info?user_name='+user_name
    },{
      id : 'pwdChange',
      icon : 'layui-icon-password',
      title : '密码修改',
      dataurl : "../Accountant/pwdEdit?user_name="+user_name
    }]},{
      title : '用户信息管理',
      icon : 'layui-icon-group',
      children : [{
        id : 'uerInfo',
        icon : 'layui-icon-user',
        title : '用户信息管理',
        dataurl : "../Accountant/userList?user_name="+user_name
      },{
        id : 'userAuthor',
        icon : 'layui-icon-auz',
        title : '用户权限管理',
        dataurl : "../System/userLevel?user_name="+user_name
      }]},{
    	  title : '猪舍管理',
    	  icon : 'layui-icon-release',
    	  children : [{
    		  id : 'pig_house',
    		  icon : 'layui-icon-release',
    		  title : '猪舍操作',
    		  dataurl : "../System/houseList?user_name="+user_name
    	  }]
      },{
        title : '日志管理',
        icon : 'layui-icon-read',
        children : [{
          id : 'log',
          icon : 'layui-icon-release',
          title : '日志信息管理',
          dataurl : "../Logger/log"
        }]}];

var equipments = [{
    title :'设备信息',
    icon : 'layui-icon-util'
    },{
        title :'设备组件信息',
        icon : 'layui-icon-util'
        },{
        title : '日志管理',
        icon : 'layui-icon-read',
        children : [{
          id : 'e_log',
          title : '设备日志信息',
          icon : 'layui-icon-release',
          dataurl : "../Logger/log"
        }]}];      

var pig = [{
	title : '猪只列表',
	icon : 'layui-icon-form',
	children : [{
		id : 'pig_data_list',
		title : '猪只数据列表',
		icon : 'layui-icon-table',
		dataurl : ''
	},{
		id : 'pig_data_paint',
		title : '猪只数据图表',
		icon : 'layui-icon-chart-screen',
		dataurl : ''
	}]
},{
	title : '猪只监控',
	icon : 'layui-icon-play',
	children : [{
		id : 'pig_vedio',
		title : '猪只视频',
		icon : 'layui-icon-camera-fill',
		dataurl : ''
	},{
		id : 'pig_photo',
		title : '猪只照片记录',
		icon : 'layui-icon-picture',
		dataurl : ''
	}]
}];

var data_center = [{
    title :'系统数据列表',
    icon : 'layui-icon-util'
    },{
      title : '系统设备添加',
      icon : 'layui-icon-add-1',
      children : [{
    	id : 'add_exist',
        title : '已有系统添加',
        icon : 'layui-icon-add-circle-fine',
        dataurl : "../System/existSystemAdd"
      },{
    	id : 'add_new',
        title : '新系统添加',
        icon : 'layui-icon-upload-circle',
        dataurl : "../System/systemAdd"
      }]},{
        title : '日志管理',
        icon : 'layui-icon-read',
        children : [{
          id : 'e_log',
          title : '设备日志信息',
          icon : 'layui-icon-release',
          dataurl : "../Logger/log"
        }]}];  