package com.xumu.programmer.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xumu.programmer.util.CpachaUtil;
import com.xumu.programmer.entity.Accountant;
import com.xumu.programmer.service.AccountantService;
import com.xumu.programmer.service.LoggerService;

@Controller
@CrossOrigin
@RequestMapping("/System")
public class SystemController {
	
	@Autowired
	AccountantService accountantService;
	@Autowired
	LoggerService loggerService;
	/**
	 * 登录界面
	 * @return
	 */
	@RequestMapping("/login")
	public String goLogin() {
		return "Login/login";
	}
	
	/**
	 * 访问主页
	 * @return
	 */
	@RequestMapping("/main")
	public String test(String user_name,Model model) {
		System.out.println(user_name);
		Accountant accountant = accountantService.queryByUname(user_name);
		model.addAttribute("accountant", accountant);
		return "main/main";
	}
	
	
	/**
	 * 用户登录检验
	 * @param telephone
	 * @param password
	 * @return
	 */
	@RequestMapping("/loginCheck")
	@ResponseBody
	public Map<String, Object> userlogin(String user_name, String password,String cpacha,HttpServletRequest request) {
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> loginInformation = new HashMap<>();
		loginInformation = accountantService.loginCheck(user_name, password);
		System.out.println(loginInformation);
		int status = (Integer)loginInformation.get("status");//登录情况
		Object loginCpacha = request.getSession().getAttribute("loginCpacha");//验证码
		//验证码检验
		if(loginCpacha == null) {//会话超时
			result.put("success", false);
			result.put("msg", "会话超时，请刷新页面！");
			return result;
		}
		if(!cpacha.toUpperCase().equals(loginCpacha.toString().toUpperCase())){//验证码错误
			result.put("success", false);
			result.put("msg", "验证码错误！");
			return result;
		}
		//用户登录检验
		accountantService.loginCheck(user_name, password);
		switch(status) {
			case 404:
				result.put("success", false);
				result.put("msg", "用户不存在");
				break;
			case 403:
				result.put("success", false);
				result.put("msg", "密码错误");
				break;
			case 500:
				result.put("success", true);
				result.put("group_id", ((Accountant)loginInformation.get("account")).getGroup_id());
				result.put("company_id", ((Accountant)loginInformation.get("account")).getCompany_id());
				result.put("authorization_code", ((Accountant)loginInformation.get("account")).getAuthorization_code());
				request.getSession().setAttribute("authorization_code", loginInformation.get("account"));
				break;
		}
		return result;
	}
	
	@RequestMapping("quit")
	public String quit(HttpServletRequest request) {
		request.getSession().setAttribute("authorization_code", null);
		return "Login/login";
	}

	
	
	/**
	 * 获取验证码图片
	 * @param vcodeLen
	 * @param width
	 * @param height
	 * @param cpachaType
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/get_cpacha",method=RequestMethod.GET)
	public void generateCpacha(
			@RequestParam(name="vl",required=false,defaultValue="4") Integer vcodeLen,
			@RequestParam(name="w",required=false,defaultValue="100") Integer width,
			@RequestParam(name="h",required=false,defaultValue="30") Integer height,
			@RequestParam(name="type",required=true,defaultValue="loginCpacha") String cpachaType,
			HttpServletRequest request,
			HttpServletResponse response){
		CpachaUtil cpachaUtil = new CpachaUtil(vcodeLen, width, height);
		String generatorVCode = cpachaUtil.generatorVCode();
		System.out.println(generatorVCode);
		request.getSession().setAttribute(cpachaType, generatorVCode);
		BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
		try {
			ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//---------------------功能选择控制器--------------------
	/**
	 * 访问企业管理功能
	 * @param telephone
	 * @param model
	 * @return
	 */
	@RequestMapping("/info")
	public String info(String user_name,Model model) {
		model.addAttribute("user_name", user_name);
		return "Accountant/info";
	}
	
	
	@RequestMapping("/userLevel")
	public String userLevel(String user_name,Model model) {
		model.addAttribute("user_name", user_name);
		return "Accountant/levelAccount";
	}
	
	@RequestMapping("/addUser")
	public String addUser(String company_id,Model model) {
		model.addAttribute("company_id", company_id);
		return "Accountant/addUser";
	}
	
	@RequestMapping("/levelEdit")
	public String levelEdit(String user_name,Model model) {
		model.addAttribute("user_name", user_name);
		return "Accountant/levelEdit";
	}
	
	@RequestMapping("/powerSet")
	public String powerSet(String user_name,Model model) {
		model.addAttribute("user_name", user_name);
		return "Accountant/powerSet";
	}
	
	/**
	 * 访问设备管理页面
	 * @return
	 */
	@RequestMapping("/equipment")
	public String equipment() {
		return "Equipment/main";
	}
	
	@RequestMapping("/component")
	public String component(Model model,String type) {
		model.addAttribute("type", type);
		return "Equipment/component_list";
	}
	
	@RequestMapping("/equipments")
	public String equipments(Model model,String type) {
		model.addAttribute("type", type);
		return "Equipment/equipment_list";
	}
	
	@RequestMapping("/addInner_Equipment")
	public String addInner_Equipment(Model model, String table_name) {
		model.addAttribute("table_name", table_name);
		return "Equipment/addInner_Equipment";
	}
	
	/**
	 * 访问数据中心页面
	 * @return
	 */
	@RequestMapping("/data_list")
	public String data_list(Model model,String type) {
		model.addAttribute("type", type);
		return "Data/list";
	}
	/**
	 * 访问猪只管理页面
	 * @return
	 */
	@RequestMapping("/pig")
	public String pig() {
		return "Pig/main";
	}

	@RequestMapping("/OneEquipment")
	public String oneEquipment(String table_name,Model model) {
		model.addAttribute("table_name", table_name);
		return "Equipment/list";
	}
	
	//--------------------------
	@RequestMapping("/index")
	public String index() {
		return "main/index";
	}
	
	@RequestMapping("/vedio")
	public String vedio() {
		return "main/vedio";
	}
	
	@RequestMapping("/accountant")
	public String account() {
		return "Accountant/main";
	}
	
	@RequestMapping("/equipStatus")
	public String equipStatus() {
		return "main/equipStatus";
	}
	
	@RequestMapping("/warn")
	public String warn() {
		return "main/warn";
	}
	
	@RequestMapping("/alarm")
	public String alarm() {
		return "main/alarm";
	}
	
	@RequestMapping("/edit")
	public String userEdit(Model model, String user_name,String account_name) {
		model.addAttribute("user_name", user_name);
		model.addAttribute("accountant_name", account_name);
		return "Accountant/userEdit";
	}
	
	@RequestMapping("/inner_equip")
	public String inner_equip() {
		return "main/inner_equip";
	}
	
	@RequestMapping("/system")
	public String system(Model model,String type) {
		System.out.println(type);
		model.addAttribute("type", type);
		return "main/system";
	}
	
	@RequestMapping("/systemAdd")
	public String systemAdd(String place, Model model) {
		model.addAttribute("place", place);
		return "Equipment/systemAdd";
	}
	
	@RequestMapping("/existSystemAdd")
	public String existSystemAdd(String place, Model model) {
		model.addAttribute("place", place);
		return "Equipment/oldsystem";
	}
	
	@RequestMapping("/addElement")
	public String addElement() {
		return "Equipment/element";
	}
	
	@RequestMapping("/error_inner_equip")
	public String error_innerEquip(String table_name,Model model) {
		model.addAttribute("error_table", table_name);
		return "main/error";
	}
	
	//------猪舍页面--------
	/**
	 * 猪舍管理列表页面
	 * @return
	 */
	@RequestMapping("/houseList")
	public String house_manage(String user_name,Model model) {
		model.addAttribute("user_name", user_name);
		return "House/manager";
	}
	
	@RequestMapping("/addHouse")
	public String addHouse() {
		return "House/addHouse";
	}
	
	@RequestMapping("/unit")
	public String unit(String house_name, Model model) {
		model.addAttribute("house_name", house_name);
		return "House/unit_list";
	}
	
	@RequestMapping("/addUnit")
	public String addUnit(String house_name,Model model) {
		model.addAttribute("house_name", house_name);
		return "House/addUnit";
	}
	
	@RequestMapping("/editUnit")
	public String editUnit(String house_name, String unit, Model model) {
		model.addAttribute("house_name", house_name);
		model.addAttribute("unit", unit);
		return "House/unit_edit";
	}
	
	@RequestMapping("/editHouse")
	public String editHouse(String name, Model model) {
		model.addAttribute("name", name);
		return "House/house_edit";
	}
	
	@RequestMapping("/air")
	public String air_demo(String user_name , Model model) {
		model.addAttribute("user_name", user_name);
		return "air_demo/index";
	}

	@RequestMapping("/sw")
	public String scanboard(String user_name, Model model) {
		model.addAttribute("user_name", user_name);
		return "main/scanboard";
	}
	
    @RequestMapping("/getFeedingData")
    @ResponseBody
    public Map<String, Object> getFeedingData(String gatewayId) {
        Integer gatewayIdInt = Integer.parseInt(gatewayId);
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", 200);
        
//        List<Map<String, Object>> feedingData = new ArrayList<Map<String, Object>>();
        
        //鏍规嵁鍗忚锛屼笂浼犵殑鍐呭鍙渶瑕佹渶鏂扮殑鏁版嵁銆傛晠涓�涓嬪惊鐜厛娉ㄩ噴
//        for (int i = 0; i < 49; i ++) {
//        	Map<String, Object> data = new HashMap<String, Object>();
//        	data.put("n0", 0);
//        	data.put("n1", 1);
//        	data.put("n2", 1);
//        	data.put("n3", 2);
//        	data.put("n4", 13);
//        	data.put("n5", 30);
//        	data.put("n6", 20);
//        	data.put("n7", 80);
//        	data.put("n8", 80);
//        	data.put("n9", 80);
//        	data.put("n10", 10);
//        	data.put("n11", 80);
//        	data.put("n12", 30);
//        	data.put("n13", 0);
//        	data.put("n14", 0);
//        	data.put("n15", 0);
//        	data.put("n16", 1);
//        	feedingData.add(data);
//        }
        
        //鐢熸垚涓�鏉0瀛楁鏁板�奸殢鏈虹殑鏁版嵁锛岃〃绀�0涓虹敤鎴锋ā寮忥紝1鏄粯璁ゆā寮�
        Random random = new Random();
	    int n0 = random.nextInt(2);
        Map<String, Object> data = new HashMap<String, Object>();
    	data.put("n0", n0);
    	data.put("n1", 1);
    	data.put("n2", 1);
    	data.put("n3", 2);
    	data.put("n4", 13);
    	data.put("n5", 30);
    	data.put("n6", 20);
    	data.put("n7", 80);
    	data.put("n8", 80);
    	data.put("n9", 80);
    	data.put("n10", 10);
    	data.put("n11", 80);
    	data.put("n12", 30);
    	data.put("n13", 0);
    	data.put("n14", 0);
    	data.put("n15", 0);
    	data.put("n16", 1);
        result.put("data", data);
        
        return result;
    }
}
