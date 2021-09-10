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
import com.xumu.programmer.entity.Logger;
import com.xumu.programmer.service.LoggerService;

@Controller
@RequestMapping("/Logger")
public class LogController {
	
	@Autowired
	LoggerService loggerService;
	
	
	@RequestMapping("/log")
	public String logPage() {
		return "Logger/LogInfo";
	}
	
	@RequestMapping("/elog")
	public String elogPage() {
		return "Logger/elogInfo";
	}
	
	/**
	 * 查询所有日志信息
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/logList")
	@ResponseBody
	public Map<String,Object> loggerList(Integer page,Integer limit){
		PageHelper.startPage(page, limit);
		List<Logger> loggers = loggerService.queryAllLogger();
		Map<String,Object> result = new HashMap<>();
		PageInfo<Logger> pageInfo = new PageInfo<>(loggers);
		long total = pageInfo.getTotal();
		//获取处理后的日志信息
		List<Logger> loggerList = pageInfo.getList();
		//放入结果map
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", total);
		result.put("data", loggerList);
		return result;
		
	}
	
}
