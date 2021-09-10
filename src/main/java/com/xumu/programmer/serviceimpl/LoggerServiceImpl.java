package com.xumu.programmer.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumu.programmer.dao.LoggerDao;
import com.xumu.programmer.entity.Logger;
import com.xumu.programmer.service.LoggerService;

@Service
public class LoggerServiceImpl implements LoggerService{

	@Autowired
	LoggerDao loggerDao;
	
	
	@Override
	public void add(Logger logger) {
		loggerDao.add(logger);
	}

	@Override
	public void delete(Integer[] id) {
		loggerDao.delete(id);
	}

	@Override
	public List<Logger> queryAllLogger() {
		return loggerDao.queryAllLog();
	}

	
}
