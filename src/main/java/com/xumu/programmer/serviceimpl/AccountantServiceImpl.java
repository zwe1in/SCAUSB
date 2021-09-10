package com.xumu.programmer.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumu.programmer.dao.AccountantDao;
import com.xumu.programmer.entity.Accountant;
import com.xumu.programmer.service.AccountantService;

@Service
public class AccountantServiceImpl implements AccountantService {

	@Autowired
	AccountantDao accountDao;
	
	
	@Override
	public Accountant queryByUname(String user_name) {
		return accountDao.queryAccountByUname(user_name);
	}

	@Override
	public void save(Accountant accountant) {
		Accountant account = accountDao.queryAccountByUname(accountant.getUser_name());
		System.out.println(account);
		if(accountant.getUser_name()!=null) {
			account.setUser_name(accountant.getUser_name());
		}
		if(accountant.getSex()!=null) {
			account.setSex(accountant.getSex());
		}
		if(accountant.getEmail()!=null) {
			account.setEmail(accountant.getEmail());
		}
		if(accountant.getAddress()!=null) {
			account.setAddress(accountant.getAddress());
		}
		if(accountant.getPhone()!=null) {
			account.setPhone(accountant.getPhone());
		}
		accountDao.update(account);
	}

	@Override
	public void savePwd(Accountant accountant) {
		// TODO Auto-generated method stub
		accountDao.savePwd(accountant);
	}


	@Override
	public Map<String, Object> loginCheck(String user_name, String password) {
		Map<String, Object> result = new HashMap<String, Object>();
		Accountant accountant = accountDao.queryAccountByUname(user_name);
		//判断用户是否存在
		if(accountant == null) {
			result.put("status", 404);//404表示用户不存在
		}else if(accountant.getPassword().equals(password)) {//判断密码是否正确
			result.put("status", 500);//500表示登陆成功
			result.put("account", accountant);//返回用户信息
		}else {
			result.put("status", 403);//403表示密码错误
		}
		return result;
	}

	@Override
	public Accountant queryByPhone(String phone) {
		return null;
	}

	@Override
	public List<Accountant> queryAllStuff(String company_id,int group_id) {
		return accountDao.queryAllStuff(company_id,group_id);
	}

	@Override
	public Accountant queryByID(int id) {
		return accountDao.queryById(id);
	}

	@Override
	public void updateStuff(Accountant accountant) {
		try {
			accountDao.updateStuff(accountant);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteStuffsById(Integer[] id) {
		accountDao.deleteUsersById(id);
		
	}

	@Override
	public void addStuff(Accountant accountant) {
		accountDao.addStuff(accountant);
		
	}

	@Override
	public void message(int message, int id, String company_id) {
		accountDao.messageUpdate(message,id,company_id);
	}

	@Override
	public void levelUpdate(Accountant accountant) {
		accountDao.levelUpdate(accountant);
		
	}
	
	
}
