package com.xumu.programmer.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface BreedService {
	public Map<String, Object> getLineData(String date,String e_id);
	public Map<String, Object> getAllData(String e_id);
	public Map<String, Object> getFeedData(String e_id, String date);
}
