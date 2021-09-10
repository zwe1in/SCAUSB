package com.xumu.programmer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.xumu.programmer.entity.DeoEquipment;

@Repository
public interface CommandDao {
	public void setValue(@Param("value")float value, @Param("type")String type, @Param("time")String time);
	public void setStatus(@Param("status")int status, @Param("type")String type, @Param("time")String time);
	public List<Map<String,Object>> getDataAndStatus();
	
	@Select("select * from command order by id desc limit 1")
	public DeoEquipment getDeoEquipment();
	
	@Insert("insert into command (id, n01, n0, n1, n2, n3, n4, n5, n6, n20, n21, n22, n23, n24, n25, n26, n28, n29, n30, n31, n32, n33 ) "
			+ "value "
			+ "(${id}, ${n01}, ${n0}, ${n1}, ${n2}, ${n3}, ${n4}, ${n5}, ${n6}, ${n20}, ${n21}, ${n22}, ${n23}, ${n24}, ${n25}, ${n26}, ${n28}, ${n29}, ${n30}, ${n31}, ${n32}, ${n33})" + 
			"")
	public void addChange(DeoEquipment deo);
	@Select("select time from ctrl order by time desc limit 1")
	public String getLatestTime();
}
