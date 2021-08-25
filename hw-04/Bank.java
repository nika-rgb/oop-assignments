// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import com.sun.corba.se.impl.oa.toa.TOA;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
	public static final int ACCOUNTS = 20;	 // number of accounts
	private BlockingQueue <Transaction> transactions;
	private Map <Integer, Account> allAccounts;
	public static Buffer buff; // for test purposes
	private int numStarted;
	private int joinedThreads;
	private Lock joinLock;

	public Bank(){
		transactions  = new ArrayBlockingQueue<>(Buffer.SIZE);
		allAccounts = new HashMap();
		buff = new Buffer();
		numStarted = 0;
		joinedThreads = 0;
		joinLock = new ReentrantLock();
	}


	public int numStarted(){
		return numStarted;
	}


	public int numJoinedThreads(){
		return joinedThreads;
	}


	
	/*
	 Reads transaction data (from/to/amt) from a file for processing.
	 (provided code)
	 */
	public void readFile(String file) {
			try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// Use stream tokenizer to get successive words from file
			StreamTokenizer tokenizer = new StreamTokenizer(reader);
			while (true) {
				int read = tokenizer.nextToken();
				if (read == StreamTokenizer.TT_EOF) break;  // detect EOF


				int from = (int)tokenizer.nval;

				allAccounts.putIfAbsent(from, new Account(this, from, 1000));
				tokenizer.nextToken();
				int to = (int)tokenizer.nval;
				allAccounts.putIfAbsent(to, new Account(this, to, 1000));
				tokenizer.nextToken();
				int amount = (int)tokenizer.nval;
				
				// Use the from/to/amount
				Transaction newTransaction = new Transaction(from, to, amount);

				buff.addTransactions(newTransaction);

				transactions.put(newTransaction);
				// YOUR CODE HERE
			}


		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}


	/*
	 Processes one file of transaction data
	 -fork off workers
	 -read file into the buffer
	 -wait for the workers to finish
	*/
	public void processFile(String file, int numWorkers) {
		process(file, numWorkers);
	}

	private void process(String file, int numWorkers) {
		CountDownLatch done = new CountDownLatch(numWorkers);
		for(int k = 0; k < numWorkers; k++){
			new Thread(new worker(done)).start();
			numStarted++;
		}

		readFile(file);

		for(int k = 0; k < numWorkers; k++){
			Transaction nullTrans = new Transaction(-1, 0, 0);
			transactions.add(nullTrans);
		}

		try {

			done.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}



	private class worker implements Runnable {
		private CountDownLatch done;

		public worker(CountDownLatch done){
			this.done = done;
		}



		/*
			helper function for handling transaction.
		 */
		private void doTransaction(Account fromAcc, Account toAcc, int amount){
			toAcc.setBalance(toAcc.getBalance() + amount);
			fromAcc.setBalance(fromAcc.getBalance() - amount);
			toAcc.setNumTransactions(toAcc.getNumTransactions() + 1);
			fromAcc.setNumTransactions(fromAcc.getNumTransactions() + 1);
		}



		/*
			function run is running in threads and it waits for new transaction to appear then makes it increasing number of \
			transactions participated for both account and transports from accounts money to to accounts money.
		 */
		@Override
		public void run() {
			while(true){
				Transaction current = null;
				try {
					current = transactions.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}



				if(current.getFromAcc() == -1){
					break;
				}

				int fromId = current.getFromAcc();
				int toId = current.getToAcc();
				int amount = current.getAmount();

				Account fromAcc = allAccounts.get(fromId);
				Account toAcc = allAccounts.get(toId);

				if(fromId == toId){
					fromAcc.lockAccount();
					fromAcc.setNumTransactions(fromAcc.getNumTransactions() + 1);
					fromAcc.unlockAccount();
				}

				if(fromId > toId){
					toAcc.lockAccount();
					fromAcc.lockAccount();
					doTransaction(fromAcc, toAcc, amount);
					fromAcc.unlockAccount();
					toAcc.unlockAccount();
				}else if(fromId < toId){
					fromAcc.lockAccount();
					toAcc.lockAccount();
					doTransaction(fromAcc, toAcc, amount);
					toAcc.unlockAccount();
					fromAcc.unlockAccount();
				}

			}
			joinLock.lock();
			joinedThreads++;
			joinLock.unlock();
			done.countDown();
		}


	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(Integer id : allAccounts.keySet()){
			builder.append(allAccounts.get(id)).append(System.getProperty("line.separator"));
		}
		return builder.toString();
	}
	
	
	/*
	 Looks at commandline args and calls Bank processing.
	*/
	public static void main(String[] args) {
		// deal with command-lines args
		if (args.length == 0) {
			System.out.println("Args: transaction-file [num-workers [limit]]");
			System.exit(1);
		}
		
		String file = args[0];
		
		int numWorkers = 1;
		if (args.length >= 2) {
			numWorkers = Integer.parseInt(args[1]);
		}

		// YOUR CODE HERE
		Bank bank = new Bank();
		bank.processFile(file, numWorkers);
		System.out.print(bank);
		System.out.println();
		System.out.println(bank.numStarted());
		System.out.println(bank.numJoinedThreads());
	}
}

