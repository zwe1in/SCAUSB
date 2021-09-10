package com.xumu.programmer.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumu.programmer.dao.BreedDao;
import com.xumu.programmer.service.BreedService;

@Service
public class BreedServiceImpl implements BreedService{

	@Autowired
	BreedDao breedDao;
	
	@Override
	public Map<String, Object> getLineData(String date, String e_id) {
		Map<String, Object> result = new HashMap<String, Object>();
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		int dayOfMonth = getDaysByYearMonth(year, month);
		for(int i = 1; i <= dayOfMonth; i++) {
			String day = year+"-"+month+"-"+i;
			System.out.println(day);
			Map<String, Integer> dataOfday = breedDao.getLatestDataList(day, e_id);
			System.out.println(dataOfday);
			if(dataOfday != null) {				
				for(Map.Entry<String, Integer> entry : dataOfday.entrySet()) {
					String key = entry.getKey();
					List<Integer> list = new ArrayList<Integer>();
					if( result.get(key)!= null) {					
						list = (List<Integer>) result.get(key);
					}
					list.add(entry.getValue());
					result.put(key, list);
				}
			}
		}
		System.out.println(result);
		return result;
	}

	@Override
	public Map<String, Object> getAllData(String e_id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result = breedDao.getAllData(e_id);
		return result;
	}

	@Override
	public Map<String, Object> getFeedData(String e_id, String date) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Integer> map = breedDao.getFeedData(date,e_id);
		List<Integer> water = new ArrayList<>();
		List<Integer> meal = new ArrayList<>();
		List<Integer> meal_plan = new ArrayList<>();
		List<Integer> pole = new ArrayList<Integer>();
		List<String> feed_time = new ArrayList<>();
		List<Integer> meal_proportion = new ArrayList<Integer>();
		foreach(map, water, 18, 23);
		foreach(map, meal, 12, 17);
		foreach(map, meal_plan, 6, 11);
		foreach(map, pole, 47, 52);
		for(int i = 60; i <= 65; i++) {
			feed_time.add(map.get("n"+i)+":"+map.get("n"+(i+6)));
		}
		foreach(map, meal_proportion, 72, 77);
		result.put("n5", 6);
		result.put("meal", meal);
		result.put("water", water);
		result.put("meal_plan", meal_plan);
		result.put("pole", pole);
		result.put("feed_time", feed_time);
		result.put("meal_proportion", meal_proportion);
		return result;
	}
	
	private void foreach(Map<String, Integer> map,List<Integer> list, int start, int end) {
		for(int i = start; i <= end; i++) {
			list.add(map.get("n"+i));
		}
	}
	
	private int getDaysByYearMonth(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

}
