package com.xumu.programmer.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xumu.programmer.entity.Accountant;
import com.xumu.programmer.entity.House;
import com.xumu.programmer.entity.Unit;

@Service
public interface HouseService {

	List<House> queryForHouse(Accountant accountant);

	int addHouse(String company_id, House house);

	List<Unit> queryForUnit(String house_name);

	void deleteHouse(String company_id, String table_name);

	int addUnit(String house_name, Unit unit);

	Unit queryUnitByName(String house_name, String unit);

	int updateUnit(String house_name, String init_unit, Unit unit);

	void deleteUnit(String house_name, String unit);

	House queryHouseByName(String name, String company_id);

	int updateHouse(String company_id, String init_name, House house);

	List<Map<String, Object>> getHouseTree(String type, int group_id, String user_name, String company_id);

	List<Map<String, Object>> getEquipmentTree(String type, int group_id, String user_name, String company_id);

}
