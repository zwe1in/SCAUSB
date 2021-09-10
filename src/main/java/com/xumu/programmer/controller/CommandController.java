package com.xumu.programmer.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xumu.programmer.entity.DeoEquipment;
import com.xumu.programmer.service.CommandService;
import com.xumu.programmer.util.CommandSender;
import com.xumu.programmer.util.pool.SocketInfo;
import com.xumu.programmer.util.pool.SocketPool;

@Controller
@RequestMapping("/Command")
public class CommandController {
    
    @Autowired
    CommandService commandService;
    
    @RequestMapping("/data")
    @ResponseBody
    public Map<String, Object> dataSet(String type, float value_up, float value_down){
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            commandService.valueSet(value_up, value_down, type);
            result.put("success", true);
        }catch(Exception e) {
            result.put("success",false);
            result.put("msg", e.getMessage());
        }
        return result;
    }
    
    //涓嬪彂鍛戒护
    @RequestMapping("/status")
    @ResponseBody
    public Map<String, Object> statusSet(String type, int status){
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            DeoEquipment deoEquipment = commandService.getDeoEquipment();
            deoEquipment.update(type, status);
            deoEquipment.setId(deoEquipment.getId()+1);
            
            long l = System.currentTimeMillis();
            Timestamp time = new Timestamp(l);
            deoEquipment.setTime(time);
            
            //寰楀埌鍛戒护
            byte[] command = deoEquipment.getCommand(type);
            
            SocketInfo socket = SocketPool.getInstance().getSocketInfo();
            CommandSender commandSender = new CommandSender(socket);
            byte[] res = new byte[600];
            byte[] equipmentId = {(byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x01};
            res = commandSender.send(equipmentId, command);
            
            //鎸佷箙鍖栧懡浠ゅ唴瀹�
            commandService.addChange(deoEquipment);
            commandService.statusSet(status, type);
            result.put("success", true);
        }catch(Exception e) {
            result.put("success",false);
            System.out.println(e.getMessage());
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @RequestMapping("/get")
    @ResponseBody
    public Map<String,Object> getData(){
        Map<String,Object> result = new HashMap<String, Object>();
        result = commandService.getData();
        System.out.println(result);
        return result;
    }
    
}
