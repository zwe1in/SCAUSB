package com.xumu.programmer.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class InnerEquipment {
	private int id;			//子设备序号
	private String e_id;	//子设备id
	private String name;	//子设备名
	private String type;	//设备类型
	private int status;		//设备状态
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date time;		//添加日期
	
	public InnerEquipment() {
		
	}

	public InnerEquipment(int id, String e_id, String name, String type, int status, Date time) {
		super();
		this.id = id;
		this.e_id = e_id;
		this.name = name;
		this.type = type;
		this.status = status;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getE_id() {
		return e_id;
	}

	public void setE_id(String e_id) {
		this.e_id = e_id;
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



	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "InnerEquipment [id=" + id + ", e_id=" + e_id + ", name=" + name + ", type=" + type + ", status="
				+ status + ", time=" + time + "]";
	}

	
	
}
