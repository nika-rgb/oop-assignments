// Buffer.java

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/*
 Holds the transactions for the worker
 threads.
*/
public class Buffer {
	public static final int SIZE = 64;
	List <Transaction> transactions;
	public Buffer(){
		transactions = new ArrayList<>();
	}

	public void addTransactions(Transaction trans){
		transactions.add(trans);
	}

	public Iterator<Transaction> getTransactions(){
		return transactions.iterator();
	}


	// YOUR CODE HERE
}
