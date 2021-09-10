package com.xumu.programmer.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xumu.programmer.dao.AccountantDao;
import com.xumu.programmer.entity.Accountant;
import com.xumu.programmer.entity.Logger;
import com.xumu.programmer.service.AccountantService;
import com.xumu.programmer.service.LoggerService;

@Controller
@RequestMapping("/Accountant")
public class AccountantController {
	
	@Autowired
	AccountantService accountantService;
	@Autowired
	LoggerService loggerService;
	
	//-----------------����Ա������Ϣ--------------------
	/**
	 * ���ʹ���Ա������Ϣ����
	 * @param telephone
	 * @param model
	 * @return
	 */
	@RequestMapping("/Info")
	public String accountantInfo(String user_name, Model model) {
		Accountant account = accountantService.queryByUname(user_name);
		model.addAttribute("accountant", account);
		return "Accountant/rootInfo";
	}
	
	/**
	 * ����Ա��Ϣ���޸ı���
	 * @param accountant
	 * @return
	 */
	@RequestMapping("/rootsave")
	@ResponseBody
	public Map<String , Object> save(Accountant accountant){
		Map<String , Object> result = new HashMap<>();
		try {
			Accountant oldinfo = accountantService.queryByPhone(accountant.getPhone());
			accountantService.save(accountant);
			result.put("success", true);
			result.put("telephone", accountant.getPhone());
			List<String> changes = new ArrayList<String>();
			if(accountant.getPhone() != null) {
				changes.add("phone");
			}
			if(accountant.getSex()!= null) {
				changes.add("sex");
			}
			if(accountant.getEmail()!= null) {
				changes.add("email");
			}
			if(accountant.getAddress()!= null) {
				changes.add("address");
			}
			String log = "";
			for(String s : changes) {
				if(s.equals("phone")) {					
					log+= "��ϵ�绰:"+oldinfo.getPhone()+" ����Ϊ:"+accountant.getPhone()+"</br>";
				}
				if(s.equals("sex")) {					
					log+= "�Ա�:"+oldinfo.getSex()+" ����Ϊ:"+accountant.getSex()+"</br>";
				}
				if(s.equals("email")) {					
					log+= "����:"+oldinfo.getEmail()+" ����Ϊ:"+accountant.getEmail()+"</br>";
				}
				if(s.equals("address")) {					
					log+= "��ַ:"+oldinfo.getAddress()+" ����Ϊ:"+accountant.getAddress()+"</br>";
				}
			}
			loggerService.add(new Logger("����Ա:"+accountant.getUser_name(),"����Ա:"+oldinfo.getUser_name()+"</br>"+log,new Date()));
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success",false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	//-----------------�����޸�-----------------------
	/**
	 * ����������������
	 * @param password
	 * @param model
	 * @return
	 */
	@RequestMapping("/pwdEdit")
	public String pwdEditPage(String user_name, Model model) {
		model.addAttribute("user_name", user_name);
		return "Accountant/rootpwdEdit";
	}
	/**
	 * ������˶�
	 * @param user_name
	 * @return
	 */
	@RequestMapping("/oldPwd")
	@ResponseBody
	public Map<String, String> findOldPwd(String user_name){
		String pwd = accountantService.queryByUname(user_name).getPassword();
		Map<String , String> result = new HashMap<>();
		result.put("password", pwd);
		return result;
	}
	
	
	/**
	 *����������ȷ�Ͻ���
	 * @return
	 */
	@RequestMapping("/confirmPwd")
	public String pwdConfirm(String user_name, Model model) {
		model.addAttribute("user_name", user_name);
		return "Accountant/rootpwdConfirm";
	}
	/**
	 * �������������
	 * @param accountant
	 * @return
	 */
	@RequestMapping("/newPwd")
	@ResponseBody
	public Map<String, Object> pwdSave(Accountant accountant){
		Map<String , Object> result = new HashMap<>();
		try {
			accountantService.savePwd(accountant);
			result.put("success", true);
			loggerService.add(new Logger("����Ա:"+accountant.getUser_name(),"����Ա:"+accountant.getUser_name()+"-��������ɹ�",new Date()));
		}catch(Exception e ) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * ��ת�û��б�ҳ��
	 * @param user_name
	 * @param model
	 * @return
	 */
	@RequestMapping("userList")
	public String user(String user_name,Model model) {
		model.addAttribute("user_name", user_name);
		return "Accountant/userInfo";
	}
	
	/**
	 * Ա���б�
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> list(String company_id,int group_id,Integer page,Integer limit){
		PageHelper.startPage(page, limit);
		//��ȡ���пͻ���Ϣ
		List<Accountant> list = accountantService.queryAllStuff(company_id,group_id);
		//װ�ڷ��ؽ����map
		Map<String,Object> result = new HashMap<>();
		//ͨ��PageInfo����ͻ���Ϣ
		PageInfo<Accountant> pageInfo = new PageInfo<Accountant>(list);
		//�ͻ�����
		long total = pageInfo.getTotal();
		//��ȡ�����Ŀͻ���Ϣ
		List<Accountant> userList = pageInfo.getList();
		//������map
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", total);
		result.put("data", userList);
		return result;
	}
	
	/**
	 * �Ƿ����ö�������
	 * @param message
	 * @param id
	 * @param company_id
	 * @return
	 */
	@RequestMapping("/message")
	@ResponseBody
	public Map<String,Object> message(int message,int id ,String company_id){
		Map<String,Object> result = new HashMap<>();
		try {
			accountantService.message(message,id,company_id);
			result.put("success", true);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * Ա�����ݻ���
	 * @param user_name
	 * @return
	 */
	@RequestMapping("/queryWithUname")
	@ResponseBody
	public Accountant queryStuffByUname(String user_name) {
		return accountantService.queryByUname(user_name);
	}
	
	/**
	 * �޸�Ա������
	 * @param accountant
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String , Object> update(Accountant accountant, String accouant_name){
		Map<String,Object> result = new HashMap<>();
		System.out.println(accountant);
		try {
			Accountant u = accountantService.queryByID(accountant.getId());
			accountantService.updateStuff(accountant);
			result.put("success", true);
			List<String> changes = new ArrayList<String>();
			if(!accountant.getUser_name().equals(u.getUser_name())) {
				changes.add("uname");
			}
			if(!accountant.getSex().equals(u.getSex())) {
				changes.add("sex");
			}
			if(!accountant.getEmail().equals(u.getEmail())) {
				changes.add("email");
			}
			if(!accountant.getAddress().equals(u.getAddress())) {
				changes.add("address");
			}
			if(!accountant.getPhone().equals(u.getPhone())) {
				changes.add("telephone");
			}
			String log = "";
			for(String s : changes) {
				if(s.equals("uname")) {					
					log+= "�û���:"+u.getUser_name()+" ����Ϊ:"+accountant.getUser_name()+" ";
				}
				if(s.equals("sex")) {					
					log+= "�Ա�:"+u.getSex()+" ����Ϊ:"+accountant.getSex()+"; ";
				}
				if(s.equals("email")) {					
					log+= "����:"+u.getEmail()+" ����Ϊ:"+accountant.getEmail()+"; ";
				}
				if(s.equals("address")) {					
					log+= "��ַ:"+u.getAddress()+" ����Ϊ:"+accountant.getAddress()+"; ";
				}
				if(s.equals("telephone")) {					
					log+= "���ֻ�:"+u.getPhone()+" ����Ϊ:"+accountant.getPhone()+"; ";
				}
			}
			loggerService.add(new Logger("����Ա:"+accouant_name,"�û�:"+u.getUser_name()+"; "
					+log,new Date()));
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success",false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	/**
	 * Ȩ���޸�
	 * @param accountant
	 * @return
	 */
	@RequestMapping("/levelUpdate")
	@ResponseBody
	public Map<String,Object> levelUpdate(Accountant accountant){
		Map<String,Object> result = new HashMap<>();
		try {
			accountantService.levelUpdate(accountant);
			result.put("success", true);
		}catch(Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * �����û�
	 * @param accountant
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String,Object> add(Accountant accountant){
		Map<String,Object> result = new HashMap<>();
		try {
			//��ʼ����12345
			accountant.setPassword("12345");
			accountantService.addStuff(accountant);
			result.put("success",true);
			loggerService.add(new Logger("����Ա","�¿ͻ�:"+accountant.toString()+"</br>"
					+ "�ѱ��½�",new Date()));
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg",e.getMessage());
		}
		return result;
	}
	
	/**
	 * ɾ���û�
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(Integer[] id,String account_name){
		Map<String,Object> result = new HashMap<>();
		try {
			for(Integer i : id) {
				String uname = accountantService.queryByID(i).getUser_name();
				String telephone = accountantService.queryByID(i).getPhone();
				loggerService.add(new Logger("����Ա:"+account_name,"�û�"+uname+" id:"+i+"; "+"���ֻ�:"+telephone+";"+"�ѱ�ɾ��",new Date()));
			}
			accountantService.deleteStuffsById(id);
			result.put("success", true);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success",false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
}
