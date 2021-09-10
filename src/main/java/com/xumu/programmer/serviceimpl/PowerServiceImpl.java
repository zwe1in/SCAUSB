package com.xumu.programmer.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumu.programmer.dao.PowerDao;
import com.xumu.programmer.entity.Power;
import com.xumu.programmer.service.PowerService;

@Service
public class PowerServiceImpl implements PowerService {

	@Autowired
	PowerDao powerDao;
	
	@Override
	public List<Power> getAllData(String company_id) {
		String table_name = "power_"+company_id;
		return powerDao.getAllData(table_name);
	}

	@Override
	public void switch_power(int status, int group_id, String company_id, String type) {
		String table_name = "power_"+company_id;
		powerDao.switch_power(table_name,status,group_id,type);
		
	}

}
