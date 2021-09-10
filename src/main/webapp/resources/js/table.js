layui.use('table',function(){
    var table = layui.table;
    table.render({
        elem: '#equipment_list',
        height: 300,
        url: '/equipment/list',
        page: true,
        cols: [[
            {field: 'id', title: '节点ID', width:80, sort: true, fixed: 'left'}
            ,{field: 'username', title: '用户名', width:80}
            ,{field: 'sex', title: '性别', width:80, sort: true}
            ,{field: 'city', title: '城市', width:80} 
            ,{field: 'sign', title: '签名', width: 177}
        ]]
    })
})