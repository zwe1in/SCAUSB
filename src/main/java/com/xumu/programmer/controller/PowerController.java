package com.xumu.programmer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xumu.programmer.entity.Power;
import com.xumu.programmer.service.PowerService;

@Controller
@RequestMapping("/Power")
public class PowerController {

	@Autowired
	PowerService powerService;
	
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getAllDataByTime(String company_id,Integer page,Integer limit){
		PageHelper.startPage(page, limit);
		List<Power> data = powerService.getAllData(company_id);
		Map<String,Object> result = new HashMap<>();
		PageInfo<Power> pageInfo = new PageInfo<Power>(data);
		long count = pageInfo.getTotal();
		List<Power> list = pageInfo.getList();
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", count);
		result.put("data", list);
		return result;
	}
	
	@RequestMapping("/switch")
	@ResponseBody
	public Map<String,Object> switch_power(int status,String type ,String company_id,int group_id){
		Map<String,Object> result = new HashMap<>();
		try {
			powerService.switch_power(status,group_id,company_id,type);
			result.put("success", true);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
}
