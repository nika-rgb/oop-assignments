// Account.java

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 Simple, thread-safe Account class encapsulates
 a balance and a transaction count.
*/
public class Account {
	private int id;
	private Integer balance;
	private Integer transactions;
	private Lock accLock;
	
	// It may work out to be handy for the account to
	// have a pointer to its Bank.
	// (a suggestion, not a requirement)
	private Bank bank;  
	
	public Account(Bank bank, int id, int balance) {
		this.bank = bank;
		this.id = id;
		this.balance = balance;
		transactions = 0;
		accLock = new ReentrantLock();
	}

	public Account(int id, int balance){
		this(null, id, balance);
	}

	public int getAccountId(){
		return id;
	}

	public void setBalance(int newBalance) {  balance = newBalance; }

	public int getBalance() { return balance; }


	public int getNumTransactions() { return transactions; }

	public void setNumTransactions(int numTransactions) { transactions = numTransactions; }

	public void lockAccount() { accLock.lock(); }

	public void unlockAccount() { accLock.unlock(); }

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("acct:").append(id).append(" bal:").append(balance).append(" trans:").append(transactions);
		return builder.toString();
	}
	
}
