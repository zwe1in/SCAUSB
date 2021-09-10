package com.xumu.programmer.entity;

import org.springframework.stereotype.Component;

@Component
public class Unit {
	private String unit;		//��Ԫ��
	private String type;		//��������
	private int equip_count;	//�豸����
	private String equip_list;	//�����е��豸�б�
	private String principal;	//������
	private String phone;		//��������ϵ�绰
	
	public Unit() {
		
	}

	public Unit(String unit, String type, int equip_count, String equip_list, String principal, String phone) {
		super();
		this.unit = unit;
		this.type = type;
		this.equip_count = equip_count;
		this.equip_list = equip_list;
		this.principal = principal;
		this.phone = phone;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getEquip_count() {
		return equip_count;
	}

	public void setEquip_count(int equip_count) {
		this.equip_count = equip_count;
	}

	public String getEquip_list() {
		return equip_list;
	}

	public void setEquip_list(String equip_list) {
		this.equip_list = equip_list;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Unit [unit=" + unit + ", type=" + type + ", equip_count=" + equip_count + ", equip_list=" + equip_list
				+ ", principal=" + principal + ", phone=" + phone + "]";
	}
	
	
}
