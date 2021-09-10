package com.xumu.programmer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedDao {
	public Map<String,Integer> getLatestDataList(@Param("date")String date, @Param("e_id")String e_id);//获取下料数据
	public Map<String,Object> getAllData(@Param("e_id")String e_id);//获取最新的数据
	public Map<String,Integer> getFeedData(@Param("date")String date, @Param("e_id")String e_id);//获取每餐信息
	
}
