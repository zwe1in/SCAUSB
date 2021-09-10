var table_name = localStorage.getItem("e_id");
function add(){
    var div = document.createElement('div');
    div.className = "layui-form-item item";
    var div_value = document.createElement('div');
    div_value.className = "layui-inline";
    var label_value = document.createElement('label');
    label_value.className = "layui-form-label";
    label_value.innerText = "测量指标";
    var div_value_block = document.createElement('div');
    div_value_block.className = "layui-input-block";
    var input_value = document.createElement('input');
    input_value.type = "text";
    input_value.name = "value";
    input_value.setAttribute("lay-verify","required");
    input_value.setAttribute("autocomplete","off");
    input_value.className = "layui-input value";
    div_value_block.appendChild(input_value);
    div_value.appendChild(label_value);
    div_value.appendChild(div_value_block);
  
    var div_key = document.createElement('div');
    div_key.className = "layui-inline";
    var label_key = document.createElement('label');
    label_key.className = "layui-form-label";
    label_key.innerText = "指标简称";
    var div_key_block = document.createElement('div');
    div_key_block.className = "layui-input-block";
    var input_key = document.createElement('input');
    input_key.type = "text";
    input_key.name = "key";
    input_key.setAttribute("lay-verify","required");
    input_key.setAttribute("autocomplete","off");
    input_key.className = "layui-input key";
    div_key_block.appendChild(input_key);
    div_key.appendChild(label_key);
    div_key.appendChild(div_key_block);
  
    div.appendChild(div_value);
    div.appendChild(div_key);
    document.getElementsByClassName('container')[0].appendChild(div);
  }

function remove(){
    var div = document.getElementsByClassName('item');
    if(div.length > 1)
    div[div.length-1].remove();
  }

function commit(){
    var keys = document.getElementsByClassName('key');
    var values = document.getElementsByClassName('value');
    var elements = [];
    for(var i = 0; i < keys.length; i++){
        var map = {};
        map.key = keys[i].value;
        map.value = values[i].value;
        elements.push(map);
    }
    layui.use('layer',function(){
        var layer = layui.layer;
        $.ajax({
            url : '../Equipment/addElement',
            data : {"elements":JSON.stringify(elements),"table_name":table_name},
            contentType : 'application/x-www-form-urlencoded;charset=UTF-8', 
            type :'POST',
            dataType : 'json',
            success : function(data){
                if(data.success){
                    layer.alert('添加成功,重新登陆即刻查看新设备',{
                        title : '提示',
                        icon : 1,
                        end : function(){
                            window.location.href = "../System/oldsystem";
                        }
                    })
                }else{
                    layer.alert('添加失败'+data.msg+' 请联系客服',{
                        title : '提示',
                        icon : 2
                    })
                }
            }
        });
    })
  }