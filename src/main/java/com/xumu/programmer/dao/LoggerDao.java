package com.xumu.programmer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xumu.programmer.entity.Logger;

@Repository
public interface LoggerDao {
	public void add(Logger logger);
	public void delete(Integer[] id);
	public List<Logger> queryAllLog();
}
