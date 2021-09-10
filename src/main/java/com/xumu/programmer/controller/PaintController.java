package com.xumu.programmer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xumu.programmer.service.PaintService;

@Controller
@RequestMapping("/Paint")
public class PaintController {

	@Autowired
	PaintService paintService;
	
	@RequestMapping("/title")
	@ResponseBody
	public Map<String,String> getDataType(String table_name){
		Map<String,String> result = new HashMap<String, String>();
		result = paintService.getDataType(table_name);
		System.out.println(result);
		return result;
	}
	
	@RequestMapping("/lineChart")
	@ResponseBody
	public List<Map<String,Object>> getPaintLineData(String table_name,String date,String type){
		List<Map<String,Object>> result = paintService.getLineData(table_name,date,type);
		System.out.println(result);
		return result;
	}
}
