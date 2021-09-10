package com.xumu.programmer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xumu.programmer.entity.Logger;

@Service
public interface LoggerService {
	public void add(Logger logger);
	public void delete(Integer[] id);
	public List<Logger> queryAllLogger();
}
