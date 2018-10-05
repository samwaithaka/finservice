package main.java.com.finservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.java.com.finservice.dao.TransactionDAO;

@RestController
public class ServiceController {
	@RequestMapping("/")
	public String index() {
		return "Welcome to Fin Microservice";
	}

	@GetMapping("/balance")
	public int checkBalance(){
		return TransactionDAO.checkBalance();
	}
}