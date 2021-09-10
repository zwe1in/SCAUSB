package com.xumu.programmer.entity;

import org.springframework.stereotype.Component;

@Component
public class House {
	private String name;	//猪舍名
	private String type;	//猪舍类型
	private String table_name;	//猪舍对应的数据表名
	private String principal;	//猪舍负责人
	private String phone;		//负责人电话
	private String area;		//猪舍所属区域级
	private String town;		//猪舍所属市级
	
	public House() {
		
	}
	
	public House(String name, String type, String table_name, String principal, String phone, String area, String town) {
		super();
		this.name = name;
		this.type = type;
		this.table_name = table_name;
		this.principal = principal;
		this.phone = phone;
		this.area = area;
		this.town = town;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String pricipal) {
		this.principal = pricipal;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	@Override
	public String toString() {
		return "House [name=" + name + ", type=" + type + ", table_name=" + table_name + ", principal=" + principal
				+ ", phone=" + phone + ", area=" + area + ", town=" + town + "]";
	}
	
	
}
