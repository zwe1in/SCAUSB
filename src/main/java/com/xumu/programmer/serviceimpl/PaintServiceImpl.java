package com.xumu.programmer.serviceimpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumu.programmer.dao.TableDataDao;
import com.xumu.programmer.service.PaintService;

@Service
public class PaintServiceImpl implements PaintService {

	@Autowired
	TableDataDao dataDao;
	
	@Override
	public Map<String, String> getDataType(String table_name) {
		Map<String,String> result = dataDao.getDataType(table_name);
    	result.remove("id");
    	result.remove("timestamp");
		return result;
	}

	@Override
	public List<Map<String, Object>> getLineData(String table_name, String date, String type) {
		try {
			List<Map<String,String>> data = dataDao.getPaintData(table_name, type, date);
			List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
			for(Map<String,String> map : data) {
				Map<String,Object> temp = new HashMap<String, Object>();
				List<Object> list = new ArrayList<Object>();
				list.add(map.get("timestamp").substring(0, map.get("timestamp").length()-2));
				list.add(map.get(type));
				temp.put("value", list);
				result.add(temp);
			}
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
