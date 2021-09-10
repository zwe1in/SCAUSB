package com.xumu.programmer.entity;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class Accountant {
	private int id;		//�û�id���
	private String company_id;	//������˾��
	private String user_name;	//�û���
	private String password;	//����
	private String authorization_code;	//��Ȩ��
	private String phone;		//�绰
	private String sex;			//�Ա�
	private String email;		//����
	private String address;		//סַ
	private String area;		//������λ������
	private String town;		//������λ���м�
	private String house;		//������λ���ἶ
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date birth;			//��������
	private int group_id;		//Ȩ�޵ȼ���1-4����ҵ�������򼶡��м����ἶ
	private int message;		//�Ƿ��ж��Ź���
	
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
