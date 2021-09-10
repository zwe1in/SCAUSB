package com.xumu.programmer.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xumu.programmer.entity.Equipment;
import com.xumu.programmer.entity.InnerEquipment;
import com.xumu.programmer.service.EquipmentService;
import com.xumu.programmer.service.InnerEquipmentService;

@Controller
@RequestMapping("/Equipment")
public class EquipmentController {
	@Autowired
	EquipmentService equipmentService;
	
	@Autowired
	InnerEquipmentService inner_equip_service;
	
	/**
	 * 设备列表，分页
	 * @param type
	 * @param curPage
	 * @param company_id
	 * @return
	 */
	@RequestMapping("/type")
	@ResponseBody
	public Map<String,Object> getEquipmentsForPage(String type,int curPage,String company_id){
		List<Equipment> equipments = equipmentService.getEquipmentByType(type,curPage,company_id);
		int totalPage = equipmentService.getTotalPage(type,company_id);
		Map<String,Object> result = new HashMap<>();
		result.put("totalPage", totalPage);
		result.put("equipments", equipments);
		return result;
		
	}
	/**
	 * 根据用户id获取所拥有的设备
	 * @param account_id
	 * @return
	 */
	@RequestMapping("/myequip")
	@ResponseBody
	public Map<String,Object> getMyEquipment(String company_id){
		return equipmentService.getEquipByAccount(company_id);
	}
	
	/**
	 * 获取某系统故障设备数量
	 * @param table_name
	 * @return
	 */
	@RequestMapping("/error_count")
	@ResponseBody
	public Map<String,Object> getErrorCount(String table_name){
		Map<String,Object> result = new HashMap<>();
		int count = inner_equip_service.getErrorCount(table_name);
		result.put("count", count);
		return result;
	}
	
	/**
	 * 获取设备测量数据列表的表头
	 * @param table_name
	 * @return
	 */
	@RequestMapping("/getTableTitle")
	@ResponseBody
	public Map<String,Object> getTableTitle(String table_name){
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String,Object>> list = equipmentService.getTableTitle(table_name);
		System.out.println(list);
		if(list == null) {
			result.put("success", false);
		}else {
			result.put("success", true);
			result.put("title", list);
		}
		return result;
	}
	/**
	 * 获取所有某个设备数据
	 * @param table_name
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/getAllData")
	@ResponseBody
	public Map<String,Object> getAllData(String table_name,Integer page,Integer limit){
		PageHelper.startPage(page, limit);
		List<Map<String,String>> data = equipmentService.getAllData(table_name);
		Map<String,Object> result = new HashMap<>();
		PageInfo<Map<String,String>> pageInfo = new PageInfo<Map<String,String>>(data);
		long count = pageInfo.getTotal();
		List<Map<String,String>> list = pageInfo.getList();
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", count);
		result.put("data", list);
		return result;
	}
	
	/**
	 * 按照时间筛选某设备的数据
	 * @param table_name
	 * @param start
	 * @param out
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/getAllDataByTime")
	@ResponseBody
	public Map<String,Object> getAllDataByTime(String table_name,String start,String out,Integer page,Integer limit){
		PageHelper.startPage(page, limit);
		List<Map<String,String>> data = equipmentService.getAllDataByTime(table_name,start,out);
		Map<String,Object> result = new HashMap<>();
		PageInfo<Map<String,String>> pageInfo = new PageInfo<Map<String,String>>(data);
		long count = pageInfo.getTotal();
		List<Map<String,String>> list = pageInfo.getList();
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", count);
		result.put("data", list);
		return result;
	}
	
	/**
	 * 获取执行设备和传感设备的绘图数据
	 * @param table_name
	 * @return
	 */
	@RequestMapping("/inner_data")
	@ResponseBody
	public Map<String,Object> getInner_equip(String table_name){
		Map<String,Object> result = new HashMap<String, Object>();
		result = inner_equip_service.getInner_equip_data(table_name);
		return result;
	}
	
	/**
	 * 根据设备ID和子设备名获取所有子设备的分页数据
	 * @param name	子设备名
	 * @param table_name	设备ID
	 * @param page	当前页数
	 * @param limit	限定一页的页数
	 * @return
	 */
	@RequestMapping("/inner_list")
	@ResponseBody
	public Map<String,Object> getInnerListByType(String name,String table_name,Integer page,Integer limit){
		PageHelper.startPage(page, limit);
		List<InnerEquipment> data = inner_equip_service.queryByNameAndTable(name,table_name);
		Map<String,Object> result = new HashMap<>();
		PageInfo<InnerEquipment> pageInfo = new PageInfo<InnerEquipment>(data);
		long count = pageInfo.getTotal();
		List<InnerEquipment> inner_list = pageInfo.getList();
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", count);
		result.put("data", inner_list);
		return result;
	}
	/**
	 * 故障的子设备列表
	 * @param table_name
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/error_list")
	@ResponseBody
	public Map<String,Object> getErrorInnerEquipment(String table_name,Integer page,Integer limit){
		PageHelper.startPage(page, limit);
		List<InnerEquipment> data = inner_equip_service.queryError_innerEquipment(table_name);
		Map<String,Object> result = new HashMap<>();
		PageInfo<InnerEquipment> pageInfo = new PageInfo<InnerEquipment>(data);
		long count = pageInfo.getTotal();
		List<InnerEquipment> inner_list = pageInfo.getList();
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", count);
		result.put("data", inner_list);
		return result;
	}
	
	/**
	 * 根据设备ID查询所有子设备
	 * @param table_name
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/getAllInnerOfEquipment")
	@ResponseBody
	public Map<String,Object> getAllInnerOfEquipment(String table_name,Integer page,Integer limit){
		PageHelper.startPage(page, limit);
		List<InnerEquipment> list = inner_equip_service.queryByAllTableName(table_name);
		Map<String,Object> result = new HashMap<>();
		PageInfo<InnerEquipment> pageInfo = new PageInfo<InnerEquipment>(list);
		long total = pageInfo.getTotal();
		List<InnerEquipment> inner_list = pageInfo.getList();
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", total);
		result.put("data", inner_list);
		return result;
	}
	
	/**
	 * 获取某单元设备
	 * @param unit
	 * @param company_id
	 * @param type
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/getEquipmentByUnit")
	@ResponseBody
	public Map<String,Object> getEquipmentByUnit(String unit,String company_id,String type,Integer page,Integer limit){
		PageHelper.startPage(page, limit);
		List<Equipment> list = equipmentService.getEquipmentByType(type, unit, company_id);
		Map<String,Object> result = new HashMap<>();
		PageInfo<Equipment> pageInfo = new PageInfo<Equipment>(list);
		long total = pageInfo.getTotal();
		List<Equipment> inner_list = pageInfo.getList();
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", total);
		result.put("data", inner_list);
		return result;
	}
	
	
	/**
	 * 增加子设备
	 * @param equipment
	 * @return
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> add(InnerEquipment iequipment ,String table_name){
		Map<String,Object> result = new HashMap<>();
		try {
			inner_equip_service.add(iequipment,table_name);
			result.put("success", true);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 子设备开关状态设置
	 * @param status
	 * @param id
	 * @param table_name
	 * @return
	 */
	@RequestMapping("/switch_inner")
	@ResponseBody
	public Map<String,Object> switch_inner(int status,int id ,String table_name){
		Map<String,Object> result = new HashMap<>();
		try {
			inner_equip_service.switch_inner(status,id,table_name);
			result.put("success", true);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 添加新系统
	 * @param equipment
	 * @return
	 */
	@RequestMapping("/addSystem")
	@ResponseBody
	public Map<String,Object> addSystem(Equipment equipment,String place){
		Map<String,Object> result = new HashMap<>();
		try {
			int status = equipmentService.addExistSystem(equipment,place);
			if(status == 400) {
				result.put("success", false);
				result.put("msg", "设备ID已存在");
			}
			if(status == 404) {
				result.put("success", false);
				result.put("msg", "主从节点重复");
			}
			if(status == 500) {
				result.put("success", true);
			}
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		System.out.println(result);
		return result;
	}
	/**
	 * 删除单元设备
	 * @param id
	 * @param house
	 * @param company_id
	 * @param unit
	 * @return
	 */
	@RequestMapping("/delete_equip")
	@ResponseBody
	public Map<String,Object> delete_equip(String id, String house, String company_id,String unit){
		Map<String,Object> result = new HashMap<>();
		try {			
			equipmentService.deleteEquip(id,house,company_id,unit);
			result.put("success", true);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	/**
	 * 设备状态开关
	 * @param id
	 * @param status
	 * @param company_id
	 * @return
	 */
	@RequestMapping("/switch")
	@ResponseBody
	public Map<String,Object> equip_switch(String id, int status,String company_id){
		Map<String,Object> result = new HashMap<>();
		try {			
			equipmentService.switchEquip(id,status,company_id);
			result.put("success", true);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 添加系统的测量元素表
	 * @param map
	 * @return
	 */
	@RequestMapping("/addElement")
	@ResponseBody
	public Map<String,Object> addElement(@RequestParam Map<String,String> map){
		System.out.println(map);
		Map<String,Object> result = new HashMap<>();
		List<Map<String, String>> elements = new ArrayList<Map<String,String>>();
		JSONArray jarray = new JSONArray(map.get("elements"));
		List<Object> list = jarray.toList();
		for(Object object : list) {
			@SuppressWarnings("unchecked")
			Map<String,String> temp = (Map<String, String>) object;
			elements.add(temp);
		}
		System.out.println(elements);
		try {
			equipmentService.createElementsTable(map.get("table_name"),elements);
			result.put("success", true);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 图片预览上传
	 * @param file
	 * @return
	 */
	@RequestMapping("/imagePreUpload")
	@ResponseBody
	public Map<String,Object> preuploadImage(HttpServletRequest request,@RequestParam("file")MultipartFile file){
		System.out.println(file.getOriginalFilename());
		Map<String,Object> result = new HashMap<>();
		try {
			result.put("success", true);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 上传图片到资源文件夹
	 * @param file
	 * @return
	 */
	@RequestMapping("/imageUpload")
	@ResponseBody
	public Map<String,Object> uploadImage(HttpServletRequest request,@RequestParam("file")MultipartFile file,String name){
		System.out.println(file.getOriginalFilename());
		System.out.println(name);
		String fileName = name+".png";
		String path = request.getSession().getServletContext().getRealPath("resources/images/");
		File newfile = new File(path+fileName);
		Map<String,Object> result = new HashMap<>();
		try {
			file.transferTo(newfile);
			result.put("success", true);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
}
