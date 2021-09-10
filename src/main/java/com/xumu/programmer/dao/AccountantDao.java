package com.xumu.programmer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xumu.programmer.entity.Accountant;

@Repository
public interface AccountantDao {
	public Accountant queryAccountByUname(String user_name);
	
	public String queryPwdByUname(String user_name);
	
	public void update(Accountant accountant);
	
	public void savePwd(Accountant accountant);
	
	public Accountant queryAccountByCode(String authorization_code);
	
	public List<Accountant> queryAllStuff(@Param("company_id")String company_id, @Param("group_id")int group_id);
	
	public Accountant queryById(int id);
	
	public void deleteUsersById(Integer[] id);
	
	public void addStuff(Accountant accountant);
	
	public void updateStuff(Accountant accountant);
	
	public String queryArea(String user_name);
	
	public String queryTown(String user_name);
	
	public String queryHouse(String user_name);

	public void messageUpdate(@Param("message")int message, @Param("id")int id, @Param("company_id")String company_id);

	public void levelUpdate(Accountant accountant);
	
}
