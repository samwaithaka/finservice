package main.java.com.finservice.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.java.com.finservice.dao.TransactionDAO;
import main.java.com.finservice.exceptions.DailyLimitException;
import main.java.com.finservice.exceptions.FrequencyLimitException;
import main.java.com.finservice.exceptions.InsufficientFundsException;
import main.java.com.finservice.exceptions.TransactionLimitException;
import main.java.com.finservice.models.Transaction;

@RestController
public class ServiceController {
	@RequestMapping("/")
	public String index() {
		return "Welcome to Fin Microservice";
	}

	@GetMapping("/balance")
	public Map<String,String> checkBalance(){
		Map<String,String> response = new HashMap<String,String>();
		int balance = TransactionDAO.checkBalance();
		response.put("status", "200");
		response.put("balance", Integer.toString(balance));
		return response;
	}
	
	@GetMapping("/deposit/{amount}")
	public Map<String,String> depositFunds(@PathVariable int amount) {
		Map<String,String> response = new HashMap<String,String>();
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		/* A deposit has transaction type C */
		transaction.setTransactionType("C");
		response.put("status", "200");
		try {
			TransactionDAO.postTransaction(transaction);
			response.put("code", "1001");
			response.put("message","Deposit successful");
		} catch (InsufficientFundsException e) {
			response.put("code", "2001");
			response.put("message",e.getMessage());
		} catch (DailyLimitException e) {
			response.put("code", "2002");
			response.put("message",e.getMessage());
		} catch (TransactionLimitException e) {
			response.put("code", "2003");
			response.put("message",e.getMessage());
		} catch (FrequencyLimitException e) {
			response.put("code", "2004");
			response.put("message",e.getMessage());
		}
		return response;
	}
	
	@GetMapping("/withdraw/{amount}")
	public Map<String,String> withdrawFunds(@PathVariable int amount) {
		Map<String,String> response = new HashMap<String,String>();
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		/* A withdrawal has transaction type D */
		transaction.setTransactionType("D");
		response.put("status", "200");
		try {
			TransactionDAO.postTransaction(transaction);
			response.put("code", "1001");
			response.put("message","Withdrawal successful");
		} catch (InsufficientFundsException e) {
			response.put("code", "3001");
			response.put("message",e.getMessage());
		} catch (DailyLimitException e) {
			response.put("code", "2002");
			response.put("message",e.getMessage());
		} catch (TransactionLimitException e) {
			response.put("code", "2003");
			response.put("message",e.getMessage());
		} catch (FrequencyLimitException e) {
			response.put("code", "2004");
			response.put("message",e.getMessage());
		}
		return response;
	}
}