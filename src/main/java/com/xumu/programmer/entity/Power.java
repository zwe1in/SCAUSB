package com.xumu.programmer.entity;

import org.springframework.stereotype.Component;

@Component
public class Power {
	private int group_id;
	private int log;
	private int modify;
	private int equipment;
	private int data;
	private int efficiency;
	
	public Power() {}

	public Power(int group_id, int log, int modify, int equipment, int data, int efficiency) {
		super();
		this.group_id = group_id;
		this.log = log;
		this.modify = modify;
		this.equipment = equipment;
		this.data = data;
		this.efficiency = efficiency;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public int getLog() {
		return log;
	}

	public void setLog(int log) {
		this.log = log;
	}

	public int getModify() {
		return modify;
	}

	public void setModify(int modify) {
		this.modify = modify;
	}

	public int getEquipment() {
		return equipment;
	}

	public void setEquipment(int equipment) {
		this.equipment = equipment;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public int getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(int efficiency) {
		this.efficiency = efficiency;
	}

	@Override
	public String toString() {
		return "Power [group_id=" + group_id + ", log=" + log + ", modify=" + modify + ", equipment=" + equipment
				+ ", data=" + data + ", efficiency=" + efficiency + "]";
	}
	
	
}
