package com.xumu.programmer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xumu.programmer.entity.Equipment;

@Repository
public interface EquipmentDao {
	public List<Equipment> getEquipmentByType(@Param("type")String type, @Param("start")int start, @Param("company_id")String company_id);

	public int getEquipCount(@Param("type")String type,@Param("company_id")String company_id);
	
	public List<Map<String,String>>getEquipByAid(String company_id);

	public List<Map<String,String>> idExist(String id);

	public List<Map<String,String>> NodeExist(@Param("G")int g, @Param("N")int n,@Param("company_id")String company_id);

	public void addSystem(Equipment equipment);

	public void addSystemEquip(@Param("id")String id);

	public void createElementTable(@Param("table_name")String table_name, @Param("elements")List<Map<String, String>> elements);

	public List<Map<String, String>> getAllDataOfElement(@Param("table_name")String table_name);

	public List<Equipment> getEquipByType(@Param("type")String type, @Param("unit")String unit, @Param("company_id")String company_id);

	public List<Equipment> queryEquipmentOfUnit(@Param("unit")String unit, @Param("type")String type,@Param("company_id")String company_id);

	public void delete(@Param("company_id")String company_id, @Param("id")String id);

	public void deleteDataTable(@Param("id")String id);

	public void deleteTable(@Param("equip_table")String equip_table);

	public void switchEquip(@Param("id")String id, @Param("status")int status, @Param("company_id")String company_id);

	public List<Map<String, String>> getElementDataByTime(@Param("table_name")String table_name, @Param("start")String start, @Param("out")String out);

	public List<String> getFeedEquipmentByHouse(@Param("town")String town, @Param("house")String house, @Param("company_id")String company_id);

	public List<String> getHousesByTown(@Param("company_id")String company_id, @Param("town")String town);

	public List<Map<String, String>> getFeedEquip(@Param("company_id")String company_id);


}
