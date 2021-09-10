package com.xumu.programmer.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface PaintService {
	public Map<String,String> getDataType(String table_name);

	public List<Map<String, Object>> getLineData(String table_name, String date, String type);
}
