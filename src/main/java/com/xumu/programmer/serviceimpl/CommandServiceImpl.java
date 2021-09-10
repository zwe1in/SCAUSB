package com.xumu.programmer.serviceimpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumu.programmer.dao.CommandDao;
import com.xumu.programmer.entity.DeoEquipment;
import com.xumu.programmer.service.CommandService;

@Service
public class CommandServiceImpl implements CommandService {

	@Autowired
	CommandDao commandDao;

	@Override
	public void statusSet(int status, String type) {
		String time = commandDao.getLatestTime();
		commandDao.setStatus(status, type, time);
	}

	@Override
	public void valueSet(float value_up, float value_down, String type) {
		String time = commandDao.getLatestTime();
//		commandDao.setValue(value_down, type+"_down",time);
		commandDao.setValue(value_up, type, time);
	}

	@Override
	public Map<String, Object> getData() {
		return commandDao.getDataAndStatus().get(0);
	}

	@Override
	public DeoEquipment getDeoEquipment() {
		return commandDao.getDeoEquipment();
	}

	@Override
	public void addChange(DeoEquipment deo) {
		commandDao.addChange(deo);
		
	}

}
