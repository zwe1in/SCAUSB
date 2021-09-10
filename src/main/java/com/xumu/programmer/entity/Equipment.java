package com.xumu.programmer.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class Equipment {
	private int account_id;	//所属用户的id
	private String id;		//设备id
	private int G;			//主节点
	private int N;			//从节点
	private String type;	//设备类型
	private String type_value;	//设备类型值(中)
	private String longitude;	//所在位置纬度
	private String latitude;	//所在位置经度
	private int status;			//开启状态
	private String area;		//所属区域级
	private String town;		//所属市级
	private String house;		//所属舍级
	private String unit;		//所属单元
	private String company_id;	//所属公司
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date time;			//添加时间
	
	public Equipment() {
		
	}



	public Equipment(int account_id, String id, int g, int n, String type, String type_value, String longitude,
			String latitude, int status, String area, String town, String house, String unit, String company_id,
			Date time) {
		super();
		this.account_id = account_id;
		this.id = id;
		G = g;
		N = n;
		this.type = type;
		this.type_value = type_value;
		this.longitude = longitude;
		this.latitude = latitude;
		this.status = status;
		this.area = area;
		this.town = town;
		this.house = house;
		this.unit = unit;
		this.company_id = company_id;
		this.time = time;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public String getHouse() {
		return house;
	}


	public void setHouse(String house) {
		this.house = house;
	}




	public String getType_value() {
		return type_value;
	}

	public void setType_value(String type_value) {
		this.type_value = type_value;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getG() {
		return G;
	}
	public void setG(int g) {
		G = g;
	}
	public int getN() {
		return N;
	}
	public void setN(int n) {
		N = n;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}

	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getTown() {
		return town;
	}


	public void setTown(String town) {
		this.town = town;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getCompany_id() {
		return company_id;
	}


	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}



	@Override
	public String toString() {
		return "Equipment [account_id=" + account_id + ", id=" + id + ", G=" + G + ", N=" + N + ", type=" + type
				+ ", type_value=" + type_value + ", longitude=" + longitude + ", latitude=" + latitude + ", status="
				+ status + ", area=" + area + ", town=" + town + ", house=" + house + ", unit=" + unit + ", company_id="
				+ company_id + ", time=" + time + "]";
	}



	
}
