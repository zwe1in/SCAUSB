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
	 *����Ȩ�޻�ȡ�����б� 
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
	 * �������
	 * @param company_id	��ҵ��
	 * @param house	������
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
			result.put("msg", "�����Ѵ���");
		}else {
			result.put("success", false);
			result.put("msg", "���ʧ��");
		}
		return result;
	}
	/**
	 * ɾ��ĳ����
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
	 * ��Ԫ����չʾ
	 * @param house_name	������
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
	 * ����µ�Ԫ
	 * @param house_name	������
	 * @param unit	��Ԫ����
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
			result.put("msg", "�����Ѵ���");
		}else {
			result.put("success", false);
			result.put("msg", "���ʧ��");
		}
		return result;
	}
	/**
	 * ���Ե�Ԫ����
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
	 * ������������
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
	 * ��Ԫ����
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
			result.put("msg", "��Ԫ���ظ�");
		}else {
			result.put("success", false);
			result.put("msg", "���ʧ��");
		}
		return result;
	}
	
	/**
	 * ������Ϣ����
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
			result.put("msg", "�������ظ�");
		}else {
			result.put("success", false);
			result.put("msg", "���ʧ��");
		}
		return result;
	}
	
	/**
	 * ɾ��ĳ��Ԫ
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
	 * ��ȡ�������б�
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
	 * ��ȡ�豸��״�б�����
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
