package com.xumu.programmer.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumu.programmer.dao.InnerEquipmentDao;
import com.xumu.programmer.entity.InnerEquipment;
import com.xumu.programmer.service.InnerEquipmentService;

@Service
public class InnerEquipmentServiceImpl implements InnerEquipmentService{
	@Autowired
	InnerEquipmentDao innerDao;

	@Override
	public Map<String, Object> getInner_equip_data(String table_name) {
		List<InnerEquipment> inner_equips = innerDao.getAllEquipment(table_name);
		//先分两大类统计
		Map<String,Integer> perform = new HashMap<>();
		Map<String,Integer> remote = new HashMap<>();
		for(InnerEquipment ie : inner_equips) {
			if(ie.getType().equals("perform")) {
				perform.compute(ie.getName(), (k,v)->{
					if(v == null) {
						v = 1;
					}else {
						v += 1;
					}
					return v;
				});
			}else if(ie.getType().equals("remote")) {
				remote.compute(ie.getName(), (k,v)->{
					if(v == null) {
						v = 1;
					}else {
						v += 1;
					}
					return v;
				});
			}
		}
		Map<String,Object> result = new HashMap<>();
		//再转化数据格式，每组最多只有6个
		//执行器统计
		int time1 = (perform.size()-1)/6+1;
		for(int i = 0; i < time1; i++) {
			int j = 0;
			String name = "perform"+(i+1);
			Map<String,Object> temp = new HashMap<>();
			List<String> title = new ArrayList<String>();
			List<Integer> value = new ArrayList<Integer>();
			for(Map.Entry<String, Integer> entry:perform.entrySet()) {
				if(j >= 6) { 
					break;
				}
				title.add(entry.getKey());
				value.add(entry.getValue());
				j++;
			}
			for(String t : title) {
				perform.remove(t);
			}
			temp.put("title", title);
			temp.put("value", value);
			result.put(name, temp);
		}
		
		//传感器统计
		int time2 = (remote.size()-1)/6+1;
		for(int i = 0; i < time2; i++) {
			int j = 0;
			String name = "remote"+(i+1);
			Map<String,Object> temp = new HashMap<>();
			List<String> title = new ArrayList<String>();
			List<Integer> value = new ArrayList<Integer>();
			for(Map.Entry<String, Integer> entry:remote.entrySet()) {
				if(j >= 6) { 
					break;
				}
				title.add(entry.getKey());
				value.add(entry.getValue());
				j++;
			}
			for(String r : title) {
				remote.remove(r);
			}
			temp.put("title", title);
			temp.put("value", value);
			result.put(name, temp);
		}
		System.out.println(result);
		return result;
	}

	@Override
	public List<InnerEquipment> queryByNameAndTable(String name, String table_name) {
		
		return innerDao.queryByNameWithTable(name,table_name);
	}

	@Override
	public List<InnerEquipment> queryByAllTableName(String table_name) {
		return innerDao.getAllEquipment(table_name);
	}

	/**
	 * 添加新设备
	 */
	@Override
	public void add(InnerEquipment iequipment,String table_name) {
		//获取已有设备数，创建设备编号
		int count =	innerDao.getInnerCount(table_name)+1;
		String num = count<10?"0"+count:count+"";
		String e_id = table_name.toUpperCase().substring(0,8)+num;
		String status = "1";
		String type = iequipment.getType();
		String name = iequipment.getName();
		innerDao.add(table_name,name,e_id,status,type);
	}

	@Override
	public int getErrorCount(String table_name) {
		return innerDao.getErrorCount(table_name);
	}

	@Override
	public List<InnerEquipment> queryError_innerEquipment(String table_name) {
		return innerDao.queryError_innerEquipment(table_name);
	}

	@Override
	public void switch_inner(int status, int id, String table_name) {
		// TODO Auto-generated method stub
		innerDao.switch_inner(status,id,table_name);
	}
}
