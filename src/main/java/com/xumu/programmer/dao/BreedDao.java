package com.xumu.programmer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedDao {
	public Map<String,Integer> getLatestDataList(@Param("date")String date, @Param("e_id")String e_id);//��ȡ��������
	public Map<String,Object> getAllData(@Param("e_id")String e_id);//��ȡ���µ�����
	public Map<String,Integer> getFeedData(@Param("date")String date, @Param("e_id")String e_id);//��ȡÿ����Ϣ
	
}
