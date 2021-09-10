package com.xumu.programmer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xumu.programmer.entity.House;
import com.xumu.programmer.entity.Unit;

@Repository
public interface HouseDao {

	List<House> queryAllHouse(@Param("table_name")String table_name);

	List<House> queryAreaHouse(@Param("table_name")String table_name, @Param("area")String area);

	List<House> queryTownHouse(@Param("table_name")String table_name, @Param("town")String town, @Param("area")String area);

	void addHouse(@Param("table_name")String table_name, @Param("house")House house);

	int getHouseCount(@Param("table_name")String table_name, @Param("type")String type);

	int isHouseExist(@Param("table_name")String table_name, @Param("name")String name);

	void addHouseTable(@Param("table_name")String table_name);

	List<Unit> queryAllUnitByHouse_name(@Param("house_name")String house_name);

	void deleteHouse(@Param("table_name")String table_name, @Param("house_name")String house_name);

	void deleteHouseTable(@Param("house_name")String house_name);

	void addUnit(@Param("house_name")String house_name, @Param("unit")Unit unit);

	int isUnitExist(@Param("house_name")String house_name, @Param("unit")String unit);

	Unit queryUnitByName(@Param("house_name")String house_name, @Param("unit")String unit);

	void unitUpdate(@Param("house_name")String house_name, @Param("init_unit")String init_unit, @Param("unit")Unit unit);

	void deleteUnit(@Param("house_name")String house_name, @Param("unit")String unit);

	House queryHouseByName(@Param("table_name")String table_name, @Param("name")String name);

	void houseUpdate(@Param("table_name")String table_name, @Param("init_name")String init_name, @Param("house")House house);

	List<House> queryHouse(@Param("table_name")String table_name, @Param("house")String house);

	void equipAdd(@Param("house_name")String house_name, @Param("unit")Unit unit);

	void equipDelete(@Param("unit")Unit u, @Param("house_name")String house_name);


}
