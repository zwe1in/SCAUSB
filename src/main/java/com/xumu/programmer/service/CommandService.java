package com.xumu.programmer.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.xumu.programmer.entity.DeoEquipment;

@Service
public interface CommandService {
	public void valueSet(float value_up,float value_down, String type);
	public void statusSet(int status, String type);
	public Map<String, Object> getData();
	public DeoEquipment getDeoEquipment();
    	public void addChange(DeoEquipment deo);
}
