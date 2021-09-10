package com.xumu.programmer.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumu.programmer.dao.AccountantDao;
import com.xumu.programmer.dao.EquipmentDao;
import com.xumu.programmer.dao.HouseDao;
import com.xumu.programmer.entity.Accountant;
import com.xumu.programmer.entity.Equipment;
import com.xumu.programmer.entity.House;
import com.xumu.programmer.entity.Unit;
import com.xumu.programmer.service.HouseService;

@Service
public class HouseServiceImpl implements HouseService{
	
	@Autowired
	HouseDao houseDao;

	@Autowired
	AccountantDao accountantDao;
	
	@Autowired
	EquipmentDao equipmentDao;
	
	@Override
	public List<House> queryForHouse(Accountant accountant) {
		String area,town;
		String table_name = "house_"+accountant.getCompany_id();
		int group_id = accountant.getGroup_id();
		String user_name = accountant.getUser_name();
		List<House> result = new ArrayList<House>();
		switch(group_id) {
			case 1 : 
				result = houseDao.queryAllHouse(table_name); 
				break;
			case 2 :
				area = accountantDao.queryArea(user_name);
				result = houseDao.queryAreaHouse(table_name,area); 
				break;
			case 3 :
				area = accountantDao.queryArea(user_name);
				town = accountantDao.queryTown(user_name);
				result = houseDao.queryTownHouse(table_name, town, area);
				break;
		}
		return result;
	}

	@Override
	public int addHouse(String company_id, House house) {
		String table_name = "house_"+company_id;
		//判断表名是否已经存在
		if(houseDao.isHouseExist(table_name,house.getName()) != 0) {
			return 405;
		}
		//处理表名
		try {			
			int num = houseDao.getHouseCount(table_name,house.getType())+1;
			house.setTable_name(company_id+"_"+house.getTable_name()+num);
			houseDao.addHouseTable(house.getTable_name());
			houseDao.addHouse(table_name,house);
			return 500;
		}catch(Exception e) {
			e.printStackTrace();
			return 400;
		}
	}

	@Override
	public List<Unit> queryForUnit(String house_name) {
		return houseDao.queryAllUnitByHouse_name(house_name);
	}

	@Override
	public void deleteHouse(String company_id, String house_name) {
		String table_name = "house_"+company_id;
		houseDao.deleteHouseTable(house_name);
		houseDao.deleteHouse(table_name,house_name);
	}

	@Override
	public int addUnit(String house_name, Unit unit) {
		// 判断单元名是否存在
		if(houseDao.isUnitExist(house_name,unit.getUnit()) != 0) {
			return 405;
		}
		try {			
			houseDao.addUnit(house_name,unit);
			return 500;
		}catch(Exception e) {
			e.printStackTrace();
			return 400;
		}
	}

	@Override
	public Unit queryUnitByName(String house_name, String unit) {
		return houseDao.queryUnitByName(house_name,unit);
	}

	@Override
	public int updateUnit(String house_name, String init_unit,Unit unit) {
		// 判断单元名是否会有重复
		if(houseDao.isUnitExist(house_name, unit.getUnit()) != 0 && !unit.getUnit().equals(init_unit)) {
			return 405;
		}
		// 修改单元信息
		try {
			houseDao.unitUpdate(house_name,init_unit,unit);
			return 500;
		}catch(Exception e) {
			e.printStackTrace();
			return 400;
		}
	}

	@Override
	public void deleteUnit(String house_name, String unit) {
		houseDao.deleteUnit(house_name,unit);		
	}

	@Override
	public House queryHouseByName(String name, String company_id) {
		String table_name = "house_"+company_id;
		return houseDao.queryHouseByName(table_name,name);
	}

	@Override
	public int updateHouse(String company_id, String init_name, House house) {
		String table_name = "house_"+company_id;
		// 判断猪舍名是否会有重复
		if(houseDao.isHouseExist(table_name, house.getName()) != 0 && !house.getName().equals(init_name)) {
			return 405;
		}
		// 修改单元信息
		try {
			houseDao.houseUpdate(table_name,init_name,house);
			return 500;
		}catch(Exception e) {
			e.printStackTrace();
			return 400;
		}
	}

	@Override
	public List<Map<String, Object>> getHouseTree(String type, int group_id, String user_name, String company_id) {
		String area,town,house;
		String table_name = "house_"+company_id;
		List<House> houses = new ArrayList<House>();
		List<Map<String,Object>> tree = new ArrayList<Map<String,Object>>();
		switch(group_id) {
			case 1 : 
				houses = houseDao.queryAllHouse(table_name);
				tree = getTree(houses);
				break;
			case 2 :
				area = accountantDao.queryArea(user_name);
				houses = houseDao.queryAreaHouse(table_name,area);
				tree = getTree(houses);
				break;
			case 3 :
				area = accountantDao.queryArea(user_name);
				town = accountantDao.queryTown(user_name);
				houses = houseDao.queryTownHouse(table_name, town, area);
				tree = getTree(houses);
				break;
			case 4 :
				house = accountantDao.queryHouse(user_name);
				houses = houseDao.queryHouse(table_name, house);
				tree = getTree(houses);
		}
		return tree;
	}


	private List<Map<String,Object>> getTree(List<House> houses){
		List<Map<String,Object>> tree = new ArrayList<Map<String,Object>>();
		Map<String,List<House>> area_selector = new HashMap<String, List<House>>();
		Map<String,Map<String,List<House>>> selector = new HashMap<>();
		//分区
		for(House house : houses) {
			Map<String,List<House>> town_selector = new HashMap<String, List<House>>();
			if(area_selector.get(house.getArea()) == null) {
				List<House> t = new ArrayList<House>();
				t.add(house);
				area_selector.put(house.getArea(), t);
				town_selector.put(house.getTown(),t);
			}else {
				List<House> t2 = area_selector.get(house.getArea());
				t2.add(house);
				area_selector.put(house.getArea(), t2);
				//分县
				for(House h : t2) {
					if(town_selector.get(h.getTown()) == null) {
						List<House> tt = new ArrayList<House>();
						tt.add(h);
						town_selector.put(h.getTown(), tt);
					}else {
						List<House> tt2 = town_selector.get(h.getTown());
						tt2.add(h);
						town_selector.put(h.getTown(), tt2);
					}
				}
			}
			selector.put(house.getArea(), town_selector);
		}
		System.out.println(selector);
		for(Map.Entry<String, Map<String,List<House>>> mmap : selector.entrySet()) {
			Map<String,Object> branch1 = new HashMap<>();
			List<Map<String,Object>> children1 = new ArrayList<Map<String,Object>>();
			branch1.put("title", mmap.getKey());
			for(Map.Entry<String, List<House>> map : mmap.getValue().entrySet()) {
				Map<String,Object> branch2 = new HashMap<>();
				List<Map<String,Object>> children2 = new ArrayList<Map<String,Object>>();
				branch2.put("title", map.getKey());
				for(House house : map.getValue()) {
					Map<String,Object> branch3 = new HashMap<>();
					List<Unit> units = houseDao.queryAllUnitByHouse_name(house.getTable_name());
					List<Map<String,Object>> children = new ArrayList<Map<String,Object>>();
					for(Unit unit : units) {
						Map<String,Object> temp = new HashMap<>();
						temp.put("title", unit.getUnit());
						temp.put("id", unit.getUnit());
						temp.put("place",house.getArea()+'_'+house.getTown()+'_'+unit.getUnit());
						children.add(temp);
					}
					branch3.put("title", house.getName());
					branch3.put("children", children);
					children2.add(branch3);
					branch2.put("children", children2);
				}
				children1.add(branch2);
				branch1.put("children", children1);
			}
			tree.add(branch1);
		}
		return tree;
	}

	@Override
	public List<Map<String, Object>> getEquipmentTree(String type, int group_id, String user_name,
			String company_id) {
		String area,town,house;
		String table_name = "house_"+company_id;
		List<House> houses = new ArrayList<House>();
		List<Map<String,Object>> tree = new ArrayList<Map<String,Object>>();
		switch(group_id) {
			case 1 : 
				houses = houseDao.queryAllHouse(table_name);
				tree = getEquipTree(houses,type,company_id);
				break;
			case 2 :
				area = accountantDao.queryArea(user_name);
				houses = houseDao.queryAreaHouse(table_name,area);
				tree = getEquipTree(houses,type,company_id);
				break;
			case 3 :
				area = accountantDao.queryArea(user_name);
				town = accountantDao.queryTown(user_name);
				houses = houseDao.queryTownHouse(table_name, town, area);
				tree = getEquipTree(houses,type,company_id);
				break;
			case 4 :
				house = accountantDao.queryHouse(user_name);
				houses = houseDao.queryHouse(table_name, house);
				tree = getEquipTree(houses,type,company_id);
		}
		System.out.println(tree);
		return tree;

	}
	
	private List<Map<String,Object>> getEquipTree(List<House> houses,String type,String company_id){
		List<Map<String,Object>> tree = new ArrayList<Map<String,Object>>();
		Map<String,List<House>> area_selector = new HashMap<String, List<House>>();
		Map<String,Map<String,List<House>>> selector = new HashMap<>();
		//分区
		for(House house : houses) {
			Map<String,List<House>> town_selector = new HashMap<String, List<House>>();
			if(area_selector.get(house.getArea()) == null) {
				List<House> t = new ArrayList<House>();
				t.add(house);
				area_selector.put(house.getArea(), t);
				town_selector.put(house.getTown(),t);
			}else {
				List<House> t2 = area_selector.get(house.getArea());
				t2.add(house);
				area_selector.put(house.getArea(), t2);
				//分县
				for(House h : t2) {
					if(town_selector.get(h.getTown()) == null) {
						List<House> tt = new ArrayList<House>();
						tt.add(h);
						town_selector.put(h.getTown(), tt);
					}else {
						List<House> tt2 = town_selector.get(h.getTown());
						tt2.add(h);
						town_selector.put(h.getTown(), tt2);
					}
				}
			}
			selector.put(house.getArea(), town_selector);
		}
		System.out.println(selector);
		for(Map.Entry<String, Map<String,List<House>>> mmap : selector.entrySet()) {
			Map<String,Object> branch1 = new HashMap<>();
			List<Map<String,Object>> children1 = new ArrayList<Map<String,Object>>();
			branch1.put("title", mmap.getKey());
			for(Map.Entry<String, List<House>> map : mmap.getValue().entrySet()) {
				Map<String,Object> branch2 = new HashMap<>();
				List<Map<String,Object>> children2 = new ArrayList<Map<String,Object>>();
				branch2.put("title", map.getKey());
				for(House house : map.getValue()) {
					Map<String,Object> branch3 = new HashMap<>();
					List<Unit> units = houseDao.queryAllUnitByHouse_name(house.getTable_name());
					List<Map<String,Object>> children = new ArrayList<Map<String,Object>>();
					for(Unit unit : units) {
						Map<String,Object> temp = new HashMap<>();
						temp.put("title", unit.getUnit());
						List<Equipment> equipments = equipmentDao.queryEquipmentOfUnit(unit.getUnit(),type,company_id);
						List<Map<String,Object>> equip_children = new ArrayList<Map<String,Object>>();
						for(Equipment equipment : equipments) {
							Map<String,Object> temp_equip = new HashMap<>();
							temp_equip.put("title", equipment.getId());
							temp_equip.put("id", equipment.getId());
							equip_children.add(temp_equip);
						}
						temp.put("children", equip_children);

						children.add(temp);
					}
					branch3.put("title", house.getName());
					branch3.put("children", children);
					children2.add(branch3);
					branch2.put("children", children2);
				}
				children1.add(branch2);
				branch1.put("children", children1);
			}
			tree.add(branch1);
		}
		return tree;
	}
}
