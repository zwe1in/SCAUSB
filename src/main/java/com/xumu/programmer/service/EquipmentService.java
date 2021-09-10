package com.xumu.programmer.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xumu.programmer.entity.Equipment;

@Service
public interface EquipmentService {
	public List<Equipment> getEquipmentByType(String type,int curPage,String company_id);

	public int getTotalPage(String type,String company_id);
	
	public Map<String,Object> getEquipByAccount(String company_id);

	public int addExistSystem(Equipment equipment, String place);

	public void createElementsTable(String string, List<Map<String, String>> elements);

	public List<Map<String, Object>> getTableTitle(String table_name);

	public List<Map<String, String>> getAllData(String table_name);

	public List<Equipment> getEquipmentByType(String type, String unit, String company_id);

	public void deleteEquip(String id, String house, String company_id,String unit);

	public void switchEquip(String id, int status, String company_id);

	public List<Map<String, String>> getAllDataByTime(String table_name, String start, String out);

	public List<Map<String, Object>> getFeedEquipTitle(String company_id);

	public List<Map<String, String>> getFeedEquip(String company_id, String town, String house, String e_id);
	
	public Map<String, Object> getFeedEquipCombo(String company_id);
}
