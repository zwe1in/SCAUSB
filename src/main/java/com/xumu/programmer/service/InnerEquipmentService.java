package com.xumu.programmer.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xumu.programmer.entity.InnerEquipment;

@Service
public interface InnerEquipmentService {

	Map<String, Object> getInner_equip_data(String table_name);

	List<InnerEquipment> queryByNameAndTable(String name, String table_name);

	List<InnerEquipment> queryByAllTableName(String table_name);

	void add(InnerEquipment iequipment,String table_name);

	int getErrorCount(String table_name);

	List<InnerEquipment> queryError_innerEquipment(String table_name);

	void switch_inner(int status, int id, String table_name);
	
}
