package com.xumu.programmer.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumu.programmer.dao.EquipmentDao;
import com.xumu.programmer.dao.HouseDao;
import com.xumu.programmer.dao.TableDataDao;
import com.xumu.programmer.entity.Equipment;
import com.xumu.programmer.entity.Unit;
import com.xumu.programmer.service.EquipmentService;

@Service
public class EquipmentServiceImpl implements EquipmentService{

	@Autowired
	EquipmentDao equipmentDao;
	
	@Autowired
	HouseDao houseDao;
	
	@Override
	public List<Equipment> getEquipmentByType(String type,int curPage,String company_id) {
		List<Equipment> equipments = equipmentDao.getEquipmentByType(type,(curPage-1)*5,company_id);
		return equipments;
	}

	@Override
	public int getTotalPage(String type,String company_id) {
		return equipmentDao.getEquipCount(type,company_id)/5+1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getEquipByAccount(String company_id) {
		List<Map<String,String>> data = equipmentDao.getEquipByAid(company_id);
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String, Object> content = new HashMap<>();
		Map<String, Object> title = new HashMap<>();
		for(Map<String,String> map : data) {
			//处理content
			String type = map.get("type");
			if(content.get(type) == null) {
				List<String> list = new ArrayList<>();
				list.add(map.get("id"));
				content.put(type, list);
			}else {
				List<String> temp = (List<String>) content.get(type);
				temp.add(map.get("id"));
				content.put(type, temp);
			}
			//处理title
			title.put(map.get("type"), map.get("type_value"));
		}
		result.put("title", title);
		result.put("content", content);
		System.out.println(result);
		return result;
	}

	@Override
	public int addExistSystem(Equipment equipment,String place) {
		String[] locations = place.split("_");
		System.out.println(locations);
		equipment.setArea(locations[0]);
		equipment.setTown(locations[1]);
		equipment.setHouse(locations[2]);
		equipment.setUnit(locations[2]+"_"+locations[3]);
		//设备ID是否重复，重复返回400
		if(!equipmentDao.idExist(equipment.getId()).isEmpty()) {
			return 400;
		};
		//主从节点是否重复，重复返回404
		if(!equipmentDao.NodeExist(equipment.getG(),equipment.getN(),equipment.getCompany_id()).isEmpty()) {
			return 404;
		}
		//成功返回500
		String house_name = houseDao.queryHouseByName("house_"+equipment.getCompany_id(), equipment.getHouse()).getTable_name();
		Unit unit = houseDao.queryUnitByName(house_name, equipment.getUnit());
		unit.setEquip_count(unit.getEquip_count()+1);
		String equip_list = unit.getEquip_list();
		if(equip_list == null) {
			unit.setEquip_list(equipment.getId());
		}else {			
			unit.setEquip_list(equip_list+","+equipment.getId());
		}
		houseDao.equipAdd(house_name,unit);
		equipmentDao.addSystem(equipment);
		equipmentDao.addSystemEquip(equipment.getId()+"_eq");
		return 500;
	}

	@Override
	public void createElementsTable(String table_name, List<Map<String, String>> elements) {
		equipmentDao.createElementTable(table_name,elements);
		
	}

	@Override
	public List<Map<String, Object>> getTableTitle(String table_name) {
		TableDataDao tableDao;
		try {
			tableDao = new TableDataDao();
			return tableDao.getTableTitle(table_name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String, String>> getAllData(String table_name) {
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		result = equipmentDao.getAllDataOfElement(table_name);
		return result;
	}

	@Override
	public List<Equipment> getEquipmentByType(String type, String unit, String company_id) {
		return equipmentDao.getEquipByType(type,unit,company_id);
	}

	@Override
	public void deleteEquip(String id, String house, String company_id,String unit) {
		//删除单元表里的设备信息
		String house_name = houseDao.queryHouseByName("house_"+company_id, house).getTable_name();
		Unit u = houseDao.queryUnitByName(house_name, unit);
		u.setEquip_count(u.getEquip_count()-1);
		String[] equip_list = u.getEquip_list().split(",");
		String new_list = "";
		for(String s : equip_list) {
			if(!s.equals(id))
				if(new_list.isEmpty()) {
					new_list += s;
				}else {
					new_list += (","+s);
				}
		}
		u.setEquip_list(new_list);
		houseDao.equipDelete(u,house_name);
		//删除equipment表数据
		equipmentDao.delete(company_id,id);
		//删除设备对应表
		String equip_table = id+"_eq";
		equipmentDao.deleteDataTable(id);
		equipmentDao.deleteTable(equip_table);
	}

	@Override
	public void switchEquip(String id, int status, String company_id) {
		equipmentDao.switchEquip(id,status,company_id);
	}

	@Override
	public List<Map<String, String>> getAllDataByTime(String table_name, String start, String out) {		
		return equipmentDao.getElementDataByTime(table_name,start,out);
	}

	@Override
	public List<Map<String, Object>> getFeedEquipTitle(String company_id) {
		List<Map<String, String>> equipments = equipmentDao.getFeedEquip(company_id);
		String town = "";
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(Map<String, String> map : equipments) {
			if(map.get("town").equals(town)) {
				continue;
			}else {
				town = map.get("town");
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("value", town);
				temp.put("label", town);
				List<String> houses = new ArrayList<String>();
				houses = equipmentDao.getHousesByTown(company_id, town);
				List<Map<String, Object>> children1 = new ArrayList<Map<String,Object>>();
				String house = "";
				for(String h : houses) {
					if(h.equals(house)) {
						continue;
					}else {
						house = h;
						Map<String, Object> temp1 = new HashMap<String, Object>();
						temp1.put("value", house);
						temp1.put("label", house);
						List<String> equipList = new ArrayList<String>();
						equipList = equipmentDao.getFeedEquipmentByHouse(town, house, company_id);
						List<Map<String, Object>> children2 = new ArrayList<Map<String,Object>>();
						for(String id : equipList) {
							Map<String, Object> temp2 = new HashMap<String, Object>();
							temp2.put("label", id);
							temp2.put("value", id);
							temp2.put("children", null);
							children2.add(temp2);
						}
						temp1.put("children", children2);
						children1.add(temp1);
					}
				}
				temp.put("children",children1);
				result.add(temp);
			}
			
		}
		return result;
	}

	@Override
	public List<Map<String, String>> getFeedEquip(String company_id, String town, String house, String e_id) {
		List<Map<String, String>> result = equipmentDao.getFeedEquip(company_id);
		if(town != null) {
			filter("town", town, result);
			if( house != null) {
				filter("house", house, result);
				if(e_id != null) {
					filter("id", e_id, result);
				}
			}
		}
		return result;
	}

	private void filter(String type, String param, List<Map<String, String>> items){
		for(int i = 0; i < items.size(); i++) {
			if(!items.get(i).get(type).equals(param)) {
				items.remove(i);
				i--;
			}
		}
	}
	
	@Override
	public Map<String, Object> getFeedEquipCombo(String company_id) {
		List<Map<String, String>> equipments = equipmentDao.getFeedEquip(company_id);
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> town = select("town", equipments);
		List<Map<String, String>> house = select("house", equipments);
		List<Map<String, String>> equipment = select("id", equipments);
		result.put("town", town);
		result.put("house", house);
		result.put("equipment", equipment);
		System.out.println("------------------");
		System.out.println(result);
		return result;
	}
	
	private List<Map<String, String>> select(String param, List<Map<String, String>> items){
		List<String> temp = new ArrayList<>();
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		for(Map<String, String> map : items) {
			if(temp.contains(map.get(param))) {
				continue;
			}else {
				temp.add(map.get(param));
				Map<String, String> mapObject = new HashMap<String, String>();
				mapObject.put("label", map.get(param));
				mapObject.put("value", map.get(param));
				result.add(mapObject);
			}
		}
		return result;
	}
}
