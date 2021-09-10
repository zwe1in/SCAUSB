package test;

import org.springframework.beans.factory.annotation.Autowired;

import com.xumu.programmer.service.AccountantService;
import com.xumu.programmer.serviceimpl.AccountantServiceImpl;

public class test {
	@Autowired
	AccountantService accountantService ;
	public static void main(String[] args) {
		AccountantServiceImpl accountantServiceImpl = new AccountantServiceImpl();
		System.out.println(accountantServiceImpl.loginCheck("liborong", "123456"));
	}
}
