package main.java.com.finservice.dao;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import main.java.com.finservice.exceptions.DailyLimitException;
import main.java.com.finservice.exceptions.FrequencyLimitException;
import main.java.com.finservice.exceptions.InsufficientFundsException;
import main.java.com.finservice.exceptions.TransactionLimitException;
import main.java.com.finservice.models.Transaction;
import main.java.com.finservice.utils.ConfigReader;

/**
 * This is a transactions data access object that uses JPA to persist and query transaction data 
 * It has the business logic
 * @author Samuel
 *
 */
public class TransactionDAO {
	private static final String PERSISTENCE_UNIT_NAME = "finservice";
	private static EntityManager em;
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	/**
	 * Posts a transaction, either a deposit or a withdrawal
	 * @return transaction
	 */
	public static boolean postTransaction(Transaction transaction) throws InsufficientFundsException,
	DailyLimitException, TransactionLimitException, FrequencyLimitException {
		boolean result = true;
		/* Get transaction limit parameters from the configuration file */
		int drDailyLimit = Integer.parseInt(ConfigReader.getParam("drlimit.daily"));
		int drTransactionLimit = Integer.parseInt(ConfigReader.getParam("drlimit.each"));
		int drFrequencyLimit = Integer.parseInt(ConfigReader.getParam("drfrequencylimit"));
		int crDailyLimit = Integer.parseInt(ConfigReader.getParam("crlimit.daily"));
		int crTransactionLimit = Integer.parseInt(ConfigReader.getParam("crlimit.each"));
		int crFrequencyLimit = Integer.parseInt(ConfigReader.getParam("crfrequencylimit"));
		
		/* Check balance */
		int balance = checkBalance();
		/* Get today's withdrawal frequency */
		int withdrawalFrequency = getDailyWithdrawalsCount();
		/* Get today's total withdrawals amount */
		int withdrawalsTotal = getDailyWithdrawalsTotal();
		/* Get today's deposit frequency */
		int depositFrequency = getDailyDepositsCount();
		/* Get today's deposits total */
		int depositsTotal = getDailyDepositsTotal();
		
		/* For withdrawals (transaction type D - debit), transaction amount must be less than the balance
		 * or else there will be an exception */
		if(transaction.getTransactionType().equalsIgnoreCase("D")) {
			if(transaction.getAmount() < balance) {
				result = false;
				throw new InsufficientFundsException();
			}
			if(transaction.getAmount() > drTransactionLimit) {
				result = false;
				throw new TransactionLimitException(drTransactionLimit, "withdrawal");
			}
			if(withdrawalsTotal > drDailyLimit) {
				result = false;
				throw new DailyLimitException(drDailyLimit, "withdrawal");
			}
			if(withdrawalFrequency > drFrequencyLimit) {
				result = false;
				throw new FrequencyLimitException(drFrequencyLimit, "withdrawal");
			}
		}

		if(transaction.getTransactionType().equalsIgnoreCase("C")) {
			if(transaction.getAmount() > drTransactionLimit) {
				result = false;
				throw new TransactionLimitException(drTransactionLimit, "deposit");
			}
			if(depositsTotal > crDailyLimit) {
				result = false;
				throw new DailyLimitException(crDailyLimit, "deposit");
			}
			if(depositFrequency > crFrequencyLimit) {
				result = false;
				throw new FrequencyLimitException(crFrequencyLimit, "deposit");
			}
		}
		
		transaction.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		transaction.setEditedOn(new Timestamp(System.currentTimeMillis()));
		transaction.setCreatedBy(transaction.getEditedBy());
		em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(transaction);
		em.getTransaction().commit();
		em.close();
		return result;
	}

	/**
	 * Gets the total number of deposits made in a day
	 * @return balance
	 */
	public static int checkBalance() {
		em = factory.createEntityManager();
		String sql = "SELECT ((SELECT SUM(amount) FROM transactions WHERE tran_type LIKE 'C')-(SELECT SUM(amount) FROM transactions WHERE tran_type LIKE 'D')) balance";
		Query q = em.createNativeQuery(sql);
		int balance = 0;
		try {
			balance = (int) q.getSingleResult();
		} catch(NoResultException e) {}
		em.close();
		return balance;
	}

	/**
	 * Gets the total number of deposits made in a day
	 * @return depositCount
	 */
	public static int getDailyDepositsCount() {
		em = factory.createEntityManager();
		String sql = "SELECT COUNT(amount) frequency FROM transactions WHERE transaction_type LIKE 'C' AND created_on=NOW()";
		Query q = em.createNativeQuery(sql);
		int dailyDepositsCount = 0;
		try {
			dailyDepositsCount = (int) q.getSingleResult();
		} catch(NoResultException e) {}
		em.close();
		return dailyDepositsCount;
	}

	/**
	 * Gets the total amount of deposits made in a day
	 * @return depositCount
	 */
	public static int getDailyDepositsTotal() {
		em = factory.createEntityManager();
		String sql = "SELECT SUM(amount) total FROM transactions WHERE transaction_type LIKE 'C' AND created_on=NOW()";
		Query q = em.createNativeQuery(sql);
		int dailyDepositsTotal = 0;
		try {
			dailyDepositsTotal = (int) q.getSingleResult();
		} catch(NoResultException e) {}
		em.close();
		return dailyDepositsTotal;
	}

	/**
	 * Gets the total number of withdrawals made in a day
	 * @return depositCount
	 */
	public static int getDailyWithdrawalsCount() {
		em = factory.createEntityManager();
		String sql = "SELECT COUNT(amount) frequency FROM transactions WHERE transaction_type LIKE 'D' AND created_on=NOW()";
		Query q = em.createNativeQuery(sql);
		int dailyWithdrawalsCount = 0;
		try {
			dailyWithdrawalsCount = (int) q.getSingleResult();
		} catch(NoResultException e) {}
		em.close();
		return dailyWithdrawalsCount;
	}

	/**
	 * Gets the total amount of withdrawls made in a day
	 * @return depositCount
	 */
	public static int getDailyWithdrawalsTotal() {
		em = factory.createEntityManager();
		String sql = "SELECT SUM(amount) total FROM transactions WHERE transaction_type LIKE 'D' AND created_on=NOW()";
		Query q = em.createNativeQuery(sql);
		int dailyWithdrawalsTotal = 0;
		try {
			dailyWithdrawalsTotal = (int) q.getSingleResult();
		} catch(NoResultException e) {}
		em.close();
		return dailyWithdrawalsTotal;
	}
}
