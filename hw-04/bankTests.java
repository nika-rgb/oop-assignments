import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.net.www.http.PosterOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import static org.junit.Assert.assertTrue;

public class bankTests {

    private ByteArrayOutputStream out;
    private final PrintStream sout = System.out;
    @Before
    public void init(){
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @After
    public void after(){
        System.setOut(sout);
    }


    @Test
    public void bankTest1(){
        String fileName = "5k.txt";
        Bank.main(new String[]{fileName, "2"});
        String result = out.toString();
        System.setOut(sout);
        assertTrue(checkResult(result, 2));
    }

    private boolean checkResult(String result, int numWorkers) {
        HashMap <Integer, Account> returnedInfo = new HashMap<>();
        HashMap <Integer, Account> actual = new HashMap<>(); // key - id value - num transactions
        ArrayList <Integer> results = new ArrayList<>();
        parse(result, returnedInfo, results);
        generateActualResult(actual);
        return balanceCorrect(returnedInfo, actual) && transactionsCorrect(returnedInfo, actual)
                && results.get(0) == numWorkers && results.get(1) == numWorkers;
    }

    private void generateActualResult(HashMap<Integer, Account> actual) {
        Iterator <Transaction> it = Bank.buff.getTransactions();
        while(it.hasNext()){
            Transaction current = it.next();

            Account from = new Account(current.getFromAcc(), 1000);
            Account to = new Account(current.getToAcc(), 1000);

            if(!actual.containsKey(to.getAccountId())) actual.put(to.getAccountId(), to);
            else to = actual.get(to.getAccountId());

            if(!actual.containsKey(from.getAccountId())) actual.put(from.getAccountId(), from);
            else from = actual.get(from.getAccountId());
            if(from.getAccountId() == to.getAccountId()) {
                from.setNumTransactions(from.getNumTransactions() + 1);
                continue;
            }
            from.setBalance(from.getBalance() - current.getAmount());
            to.setBalance(to.getBalance() + current.getAmount());
            from.setNumTransactions(from.getNumTransactions() + 1);
            to.setNumTransactions(to.getNumTransactions() + 1);

        }
    }



    private boolean transactionsCorrect(HashMap<Integer, Account> returnedInfo, HashMap<Integer, Account> fileInfo) {

        for(Integer id : returnedInfo.keySet()){
            if(returnedInfo.get(id).getNumTransactions() != fileInfo.get(id).getNumTransactions()) return false;
        }
        return true;
    }

    private boolean balanceCorrect(HashMap<Integer, Account> returnedInfo, HashMap <Integer, Account> fileInfo) {
        for(Integer id : returnedInfo.keySet()){
            if(returnedInfo.get(id).getBalance() != fileInfo.get(id).getBalance()) return  false;
        }
        return true;
    }


    private void parse(String result, HashMap<Integer,Account> returnedInfo, ArrayList <Integer> results) {
        StringTokenizer tok = new StringTokenizer(result, "" + System.getProperty("line.separator"));
        String last = "";
        while(tok.hasMoreTokens()){
            String next = tok.nextToken();
            if(isOnlyDigits(next)){
                last = next;
                break;
            }

            Account newAcc = createAccount(next);
            returnedInfo.put(newAcc.getAccountId(), newAcc);
        }
        results.add(Integer.parseInt(last));
        last = tok.nextToken();
        results.add(Integer.parseInt(last));
    }

    private boolean isOnlyDigits(String next) {
        for(int k = 0; k < next.length(); k++) if(!Character.isDigit(next.charAt(k))) return false;
        return true;
    }

    private Account createAccount(String accInfo) {
        int lastOcc = 0;
        int id = -1;
        int balance = -1;
        int numTransactions = -1;
        for(int k = 0; k < 3; k++){
            lastOcc = accInfo.indexOf(':', lastOcc) + 1;
            int res = extractInt(accInfo, lastOcc);
            switch(k){
                case 0 : id = res; break;
                case 1 : balance = res; break;
                case 2 : numTransactions = res; break;
                default : new IllegalAccessException();
            }
        }
        Account acc = new Account(id, balance);
        acc.setNumTransactions(numTransactions);
        return acc;
    }

    private int extractInt(String accInfo, int current) {

        String integer = "";
        while(current < accInfo.length() && Character.isDigit(accInfo.charAt(current))){
            integer += accInfo.charAt(current);
            current++;
        }
        int result = Integer.parseInt(integer);
        return result;
    }


    @Test
    public void bankTest2(){
        String fileName = "100k.txt";
        Bank.main(new String[] {fileName, "11"});
        String result = out.toString();
        assertTrue(checkResult(result, 11));
    }

    @Test
    public void bankTest3(){
        String fileName = "small.txt";
        Bank.main(new String[] {fileName, "20"});
        String result = out.toString();
        assertTrue(checkResult(result, 20));
        //assertEquals();
    }



}
