package com.xumu.programmer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xumu.programmer.entity.Power;

@Service
public interface PowerService {

	List<Power> getAllData(String company_id);

	void switch_power(int status, int group_id, String company_id, String type);

}
