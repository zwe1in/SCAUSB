package com.xumu.programmer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xumu.programmer.entity.InnerEquipment;

@Repository
public interface InnerEquipmentDao {

	List<InnerEquipment> getAllEquipment(@Param("table_name")String table_name);

	List<InnerEquipment> queryByNameWithTable(@Param("name")String name, @Param("table_name")String table_name);

	int getInnerCount(@Param("table_name")String table_name);

	void add(@Param("table_name")String table_name, @Param("name")String name, @Param("e_id")String e_id, @Param("status")String status, @Param("type")String type);

	int getErrorCount(@Param("table_name")String table_name);

	List<InnerEquipment> queryError_innerEquipment(@Param("table_name")String table_name);

	void switch_inner(@Param("status")int status, @Param("id")int id, @Param("table_name")String table_name);

	

}
