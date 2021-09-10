package com.xumu.programmer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xumu.programmer.entity.Accountant;
import com.xumu.programmer.entity.House;
import com.xumu.programmer.entity.Unit;
import com.xumu.programmer.service.HouseService;

@Controller
@RequestMapping("/House")
public class HouseController {
	@Autowired
	HouseService houseService;
	
	/**
	 *根据权限获取猪舍列表 
	 * @param accountant
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getHouseListByLevel(Accountant accountant,Integer page,Integer limit){
		PageHelper.startPage(page, limit);
		List<House> list = new ArrayList<House>();
		list = houseService.queryForHouse(accountant);
		Map<String,Object> result = new HashMap<>();
		PageInfo<House> pageInfo = new PageInfo<House>(list);
		long total = pageInfo.getTotal();
		List<House> inner_list = pageInfo.getList();
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", total);
		result.put("data", inner_list);
		return result;
	}
	
	/**
	 * 添加猪舍
	 * @param company_id	企业名
	 * @param house	猪舍名
	 * @return
	 */
	@RequestMapping("/addHouse")
	@ResponseBody
	public Map<String,Object> addHouse(String company_id,House house){
		Map<String,Object> result = new HashMap<>();
		int status = houseService.addHouse(company_id,house);
		if(status == 500) {
			result.put("success",true);
		}else if(status == 405) {
			result.put("success", false);
			result.put("msg", "表名已存在");
		}else {
			result.put("success", false);
			result.put("msg", "添加失败");
		}
		return result;
	}
	/**
	 * 删除某猪舍
	 * @param company_id
	 * @param table_name
	 * @return
	 */
	@RequestMapping("/house_delete")
	@ResponseBody
	public Map<String,Object> deleteHouse(String company_id, String table_name){
		Map<String,Object> result = new HashMap<>();
		try {
			houseService.deleteHouse(company_id,table_name);
			result.put("success",true);
		}catch(Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 单元数据展示
	 * @param house_name	猪舍名
	 * @param page	
	 * @param limit
	 * @return
	 */
	@RequestMapping("/unit_list")
	@ResponseBody
	public Map<String,Object> getUnitList(String house_name,Integer page,Integer limit){
		PageHelper.startPage(page, limit);
		List<Unit> list = new ArrayList<Unit>();
		list = houseService.queryForUnit(house_name);
		Map<String,Object> result = new HashMap<>();
		PageInfo<Unit> pageInfo = new PageInfo<Unit>(list);
		long total = pageInfo.getTotal();
		List<Unit> inner_list = pageInfo.getList();
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", total);
		result.put("data", inner_list);
		return result;
	}
	
	/**
	 * 添加新单元
	 * @param house_name	猪舍名
	 * @param unit	单元数据
	 * @return
	 */
	@RequestMapping("/addUnit")
	@ResponseBody
	public Map<String,Object> addHouse(String house_name , Unit unit){
		Map<String,Object> result = new HashMap<>();
		int status = houseService.addUnit(house_name,unit);
		if(status == 500) {
			result.put("success",true);
		}else if(status == 405) {
			result.put("success", false);
			result.put("msg", "表名已存在");
		}else {
			result.put("success", false);
			result.put("msg", "添加失败");
		}
		return result;
	}
	/**
	 * 回显单元数据
	 * @param house_name
	 * @param unit
	 * @return
	 */
	@RequestMapping("/queryWithUnit")
	@ResponseBody
	public Unit queryWithUnit(String house_name, String unit) {
		System.out.println(unit);
		Unit u = houseService.queryUnitByName(house_name,unit);
		System.out.println(u);
		return u;
	}
	
	/**
	 * 回显猪舍数据
	 * @param house_name
	 * @param unit
	 * @return
	 */
	@RequestMapping("/queryWithHouse")
	@ResponseBody
	public House queryWithHouse(String name, String company_id) {
		House h = houseService.queryHouseByName(name,company_id);
		System.out.println(h);
		return h;
	}
	/**
	 * 单元更新
	 * @param house_name
	 * @param init_unit
	 * @return
	 */
	@RequestMapping("/update_unit")
	@ResponseBody
	public Map<String,Object> editUnit(String house_name ,String init_unit,Unit unit){
		Map<String,Object> result = new HashMap<>();
		int status = houseService.updateUnit(house_name,init_unit,unit);
		if(status == 500) {
			result.put("success",true);
		}else if(status == 405) {
			result.put("success", false);
			result.put("msg", "单元名重复");
		}else {
			result.put("success", false);
			result.put("msg", "添加失败");
		}
		return result;
	}
	
	/**
	 * 猪舍信息更新
	 * @param house_name
	 * @param init_unit
	 * @param unit
	 * @return
	 */
	@RequestMapping("/update_house")
	@ResponseBody
	public Map<String,Object> editHouse(String company_id ,String init_name,House house){
		Map<String,Object> result = new HashMap<>();
		int status = houseService.updateHouse(company_id,init_name,house);
		if(status == 500) {
			result.put("success",true);
		}else if(status == 405) {
			result.put("success", false);
			result.put("msg", "猪舍名重复");
		}else {
			result.put("success", false);
			result.put("msg", "添加失败");
		}
		return result;
	}
	
	/**
	 * 删除某单元
	 * @param company_id
	 * @param table_name
	 * @return
	 */
	@RequestMapping("/unit_delete")
	@ResponseBody
	public Map<String,Object> deleteUnit(String house_name, String unit){
		Map<String,Object> result = new HashMap<>();
		try {
			houseService.deleteUnit(house_name,unit);
			result.put("success",true);
		}catch(Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取猪舍树列表
	 * @param type
	 * @param group_id
	 * @param company_id
	 * @return
	 */
	@RequestMapping("/getHouseTree")
	@ResponseBody
	public List<Map<String, Object>> getHouseTree(String type, int group_id, String company_id, String user_name){
//		Map<String,Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> data = houseService.getHouseTree(type,group_id,user_name,company_id);
		return data;
	}
	
	/**
	 * 获取设备树状列表数据
	 * @param type
	 * @param group_id
	 * @param company_id
	 * @param user_name
	 * @return
	 */
	@RequestMapping("/getEquipTree")
	@ResponseBody
	public List<Map<String, Object>> getEquipTree(String type, int group_id, String company_id, String user_name){
		List<Map<String, Object>> data = houseService.getEquipmentTree(type,group_id,user_name,company_id);
		return data;
	}
}
