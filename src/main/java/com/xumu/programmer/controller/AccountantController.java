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
	
	//-----------------管理员个人信息--------------------
	/**
	 * 访问管理员个人信息界面
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
	 * 管理员信息的修改保存
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
					log+= "联系电话:"+oldinfo.getPhone()+" 更改为:"+accountant.getPhone()+"</br>";
				}
				if(s.equals("sex")) {					
					log+= "性别:"+oldinfo.getSex()+" 更改为:"+accountant.getSex()+"</br>";
				}
				if(s.equals("email")) {					
					log+= "邮箱:"+oldinfo.getEmail()+" 更改为:"+accountant.getEmail()+"</br>";
				}
				if(s.equals("address")) {					
					log+= "地址:"+oldinfo.getAddress()+" 更改为:"+accountant.getAddress()+"</br>";
				}
			}
			loggerService.add(new Logger("管理员:"+accountant.getUser_name(),"管理员:"+oldinfo.getUser_name()+"</br>"+log,new Date()));
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success",false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	//-----------------密码修改-----------------------
	/**
	 * 进入旧密码输入界面
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
	 * 旧密码核对
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
	 *进入新密码确认界面
	 * @return
	 */
	@RequestMapping("/confirmPwd")
	public String pwdConfirm(String user_name, Model model) {
		model.addAttribute("user_name", user_name);
		return "Accountant/rootpwdConfirm";
	}
	/**
	 * 保存更新新密码
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
			loggerService.add(new Logger("管理员:"+accountant.getUser_name(),"管理员:"+accountant.getUser_name()+"-更改密码成功",new Date()));
		}catch(Exception e ) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 跳转用户列表页面
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
	 * 员工列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> list(String company_id,int group_id,Integer page,Integer limit){
		PageHelper.startPage(page, limit);
		//获取所有客户信息
		List<Accountant> list = accountantService.queryAllStuff(company_id,group_id);
		//装在返回结果的map
		Map<String,Object> result = new HashMap<>();
		//通过PageInfo处理客户信息
		PageInfo<Accountant> pageInfo = new PageInfo<Accountant>(list);
		//客户总数
		long total = pageInfo.getTotal();
		//获取处理后的客户信息
		List<Accountant> userList = pageInfo.getList();
		//放入结果map
		result.put("code", 0);
		result.put("msg", "");
		result.put("count", total);
		result.put("data", userList);
		return result;
	}
	
	/**
	 * 是否设置短信提醒
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
	 * 员工数据回显
	 * @param user_name
	 * @return
	 */
	@RequestMapping("/queryWithUname")
	@ResponseBody
	public Accountant queryStuffByUname(String user_name) {
		return accountantService.queryByUname(user_name);
	}
	
	/**
	 * 修改员工数据
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
					log+= "用户名:"+u.getUser_name()+" 更改为:"+accountant.getUser_name()+" ";
				}
				if(s.equals("sex")) {					
					log+= "性别:"+u.getSex()+" 更改为:"+accountant.getSex()+"; ";
				}
				if(s.equals("email")) {					
					log+= "邮箱:"+u.getEmail()+" 更改为:"+accountant.getEmail()+"; ";
				}
				if(s.equals("address")) {					
					log+= "地址:"+u.getAddress()+" 更改为:"+accountant.getAddress()+"; ";
				}
				if(s.equals("telephone")) {					
					log+= "绑定手机:"+u.getPhone()+" 更改为:"+accountant.getPhone()+"; ";
				}
			}
			loggerService.add(new Logger("管理员:"+accouant_name,"用户:"+u.getUser_name()+"; "
					+log,new Date()));
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success",false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	/**
	 * 权限修改
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
	 * 增加用户
	 * @param accountant
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String,Object> add(Accountant accountant){
		Map<String,Object> result = new HashMap<>();
		try {
			//初始密码12345
			accountant.setPassword("12345");
			accountantService.addStuff(accountant);
			result.put("success",true);
			loggerService.add(new Logger("管理员","新客户:"+accountant.toString()+"</br>"
					+ "已被新建",new Date()));
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg",e.getMessage());
		}
		return result;
	}
	
	/**
	 * 删除用户
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
				loggerService.add(new Logger("管理员:"+account_name,"用户"+uname+" id:"+i+"; "+"绑定手机:"+telephone+";"+"已被删除",new Date()));
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
