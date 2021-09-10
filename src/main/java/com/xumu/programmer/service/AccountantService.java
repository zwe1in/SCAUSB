package com.xumu.programmer.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xumu.programmer.entity.Accountant;

@Service
public interface AccountantService {
	public Accountant queryByUname(String user_name);
	public void save(Accountant accountant);
	public void savePwd(Accountant accountant);
	public Map<String,Object> loginCheck(String user_name, String password);
	public Accountant queryByPhone(String phone);
	public List<Accountant> queryAllStuff(String company_id,int group_id);
	public Accountant queryByID(int id);
	public void updateStuff(Accountant accountant);
	public void deleteStuffsById(Integer[] id);
	public void addStuff(Accountant accountant);
	public void message(int message, int id, String company_id);
	public void levelUpdate(Accountant accountant);
}
