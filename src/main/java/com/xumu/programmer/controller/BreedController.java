package com.xumu.programmer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xumu.programmer.service.BreedService;
import com.xumu.programmer.service.EquipmentService;

@Controller
@RequestMapping("/Breed")
@CrossOrigin
public class BreedController {
	@Autowired
	BreedService breedService;
	@Autowired
	EquipmentService euipmentService;
	
	@RequestMapping("/feedData")
	@ResponseBody
	public Map<String, Object> getAllData(String e_id){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> data = breedService.getAllData(e_id);
			result.put("success", true);
			result.put("data", data);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		System.out.println(result);
		return result;
	}
	
	@RequestMapping("/feedMonth")
	@ResponseBody
	public Map<String, Object> getFeedOfMonth(String e_id, String date){
		System.out.println(e_id);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> data = breedService.getLineData(date, e_id);
			result.put("success", true);
			result.put("data", data);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
	}
	
	@RequestMapping("/feedDay")
	@ResponseBody
	public Map<String, Object> getFeedOfDay(String e_id, String date){
		System.out.println(e_id);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> data = breedService.getAllData(e_id);
			Map<String, Object> line_data = breedService.getFeedData(e_id, date);
			result.put("success", true);
			result.put("line_data", line_data);
			result.put("data", data);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
	}
	
	@RequestMapping("/equipInfo")
	@ResponseBody
	public Map<String, Object> getEquipInfo(String company_id, String town, String house, String e_id){

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> title = euipmentService.getFeedEquipTitle(company_id);
			List<Map<String, String>> equipments = euipmentService.getFeedEquip(company_id, town, house, e_id);
			Map<String,Object> select = euipmentService.getFeedEquipCombo(company_id);
			result.put("success", true);
			result.put("table", equipments);
			result.put("select", select);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
	}
}
