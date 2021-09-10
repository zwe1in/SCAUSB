package com.xumu.programmer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xumu.programmer.entity.Power;

@Repository
public interface PowerDao {

	List<Power> getAllData(@Param("table_name")String table_name);

	void switch_power(@Param("table_name")String table_name, @Param("status")int status, @Param("group_id")int group_id, @Param("type")String type);

}
