// Transaction.java
/*
 (provided code)
 Transaction is just a dumb struct to hold
 one transaction. Supports toString.
*/
public class Transaction {
	private int from;
	private int to;
	private int amount;
	
   	public Transaction(int from, int to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}


	public int getFromAcc(){ return new Integer(from) ; }

	public int getToAcc(){ return new Integer(to) ; }

	public int getAmount(){ return new Integer(amount) ; }


	public String toString() {
		return("from:" + from + " to:" + to + " amt:" + amount);
	}
}
