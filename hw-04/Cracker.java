// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import com.sun.xml.internal.fastinfoset.algorithm.BooleanEncodingAlgorithm;

import java.security.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();

	private static class worker implements Runnable{
		private int workerId;
		private int startIndex;
		private int endIndex;
		private int maxLength;
		private CountDownLatch latch;
		private AtomicBoolean stop;
		private String hPass;

		public worker(int workerId, int startIndex, int endIndex, int maxLength,
					  CountDownLatch latch, AtomicBoolean stop, String hash){
			this.workerId = workerId;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.maxLength = maxLength;
			this.latch = latch;
			this.stop = stop;
			this.hPass = hash;
		}


		/*
			recursive function generates all passwords and then checks if current passwords hash is equal to given hash.
		 */
		private String getPassword(String currentPass){
			if(hPass.equals(hashPassword(currentPass))){
				System.out.println(currentPass);
				return currentPass;
			}

			if(currentPass.length() == maxLength)
				return "";


			if(stop.get())
				return "";

			String current;

			for(int k = 0; k < CHARS.length; k++){
				current = getPassword(currentPass + CHARS[k]);
				if(!current.equals("")) return current;
				if(stop.get())
					return "";
			}


			return "";
		}

		/*
			starts to generate passwords witch might be real password and then checks if returned string is not equal to "".
			if it's not equal then all threads stop working.
		 */
		public void run(){
			for(int k = startIndex; k < endIndex; k++){
				char startChar = CHARS[k];
				String currentPass = startChar + "";
				String password = getPassword(currentPass);
				boolean stop = this.stop.get();
				if(!stop) this.stop.set(!password.equals(""));
				else break;
			}
			latch.countDown();
		}
	}




	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}

	/*
		takes password and hashes with sha algorithm.
	 */
	private static String hashPassword(String password){
		try {
			MessageDigest msg = MessageDigest.getInstance("SHA-1");
			byte [] result = msg.digest(password.getBytes());
			return hexToString(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}


	/*
		starts threads gives them their part to work on and then waits for all threads.
	 */
	private static void initialize(int num, int maxLength, String hPass){
		CountDownLatch latch =  new CountDownLatch(num);
		Thread [] threads = new Thread[num];
		int perThread = CHARS.length / num;
		int startIndex = 0;
		int endIndex = perThread;
		AtomicBoolean stop = new AtomicBoolean(false);
		for(int k = 0; k < num; k++){
			if(k == num - 1)
				endIndex = CHARS.length;
			threads[k] = new Thread(new worker(k + 1, startIndex, endIndex, maxLength, latch, stop, hPass));
			threads[k].start();
			startIndex = endIndex;
			endIndex += perThread;
		}


		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		if (args.length == 1) {
			String hPass = hashPassword(args[0]);
			System.out.println(hPass);
			return;
		}

		if (args.length < 2) {
			System.out.println("Args: target length [workers]");
			System.exit(1);
		}
		// args: targ len [num]
		String targ = args[0];
		int len = Integer.parseInt(args[1]);
		int num = 1;
		if (args.length>2) {
			num = Integer.parseInt(args[2]);
		}

		// a! 34800e15707fae815d7c90d49de44aca97e2d759
		// xyz 66b27417d37e024c46526c2f6d358a754fc552f3

		// YOUR CODE HERE

		initialize(num, len, targ);

		System.out.println("all done");

	}
}
