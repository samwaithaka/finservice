package test.java.com.finservice.tests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import main.java.com.finservice.dao.TransactionDAO;

public class TransactionDAOTest {

    @Test
    public void checkBalanceTest() {
    	int balance = TransactionDAO.checkBalance();
    	assertTrue(balance >= 0);
    }
    
    @Test
    public void getDailyWithdrawalsCountTest() {
    	int balance = TransactionDAO.getDailyWithdrawalsCount();
    	assertTrue(balance >= 0);
    }
    
    @Test
    public void getDailyDepositsCountTest() {
    	int balance = TransactionDAO.getDailyDepositsCount();
    	assertTrue(balance >= 0);
    }
    
    @Test
    public void getDailyDepositsTotalTest() {
    	int balance = TransactionDAO.getDailyDepositsTotal();
    	assertTrue(balance >= 0);
    }
    
    @Test
    public void getDailyWithdrawalsTotalTest() {
    	int balance = TransactionDAO.getDailyWithdrawalsTotal();
    	assertTrue(balance >= 0);
    }
}
