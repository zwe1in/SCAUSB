package com.xumu.programmer.entity;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class Accountant {
	private int id;		//用户id编号
	private String company_id;	//所属公司名
	private String user_name;	//用户名
	private String password;	//密码
	private String authorization_code;	//授权码
	private String phone;		//电话
	private String sex;			//性别
	private String email;		//邮箱
	private String address;		//住址
	private String area;		//所属单位的区域级
	private String town;		//所属单位的市级
	private String house;		//所属单位的舍级
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date birth;			//出生日期
	private int group_id;		//权限等级，1-4：企业级、区域级、市级、舍级
	private int message;		//是否有短信功能
	
	public Accountant() {
		
	}



	public Accountant(int id, String company_id, String user_name, String password, String authorization_code,
			String phone, String sex, String email, String address, String area, String town, String house, Date birth,
			int group_id, int message) {
		super();
		this.id = id;
		this.company_id = company_id;
		this.user_name = user_name;
		this.password = password;
		this.authorization_code = authorization_code;
		this.phone = phone;
		this.sex = sex;
		this.email = email;
		this.address = address;
		this.area = area;
		this.town = town;
		this.house = house;
		this.birth = birth;
		this.group_id = group_id;
		this.message = message;
	}




	public int getGroup_id() {
		return group_id;
	}



	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthorization_code() {
		return authorization_code;
	}

	public void setAuthorization_code(String authorization_code) {
		this.authorization_code = authorization_code;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
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



	public int getMessage() {
		return message;
	}



	public void setMessage(int message) {
		this.message = message;
	}



	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}



	@Override
	public String toString() {
		return "Accountant [id=" + id + ", company_id=" + company_id + ", user_name=" + user_name + ", password="
				+ password + ", authorization_code=" + authorization_code + ", phone=" + phone + ", sex=" + sex
				+ ", email=" + email + ", address=" + address + ", area=" + area + ", town=" + town + ", house=" + house
				+ ", birth=" + birth + ", group_id=" + group_id + ", message=" + message + "]";
	}



	
}
