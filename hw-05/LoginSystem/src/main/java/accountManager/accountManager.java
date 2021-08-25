package accountManager;

import java.util.HashMap;
import java.util.Map;

public class accountManager {
    Map<String, String> accounts;
    public accountManager(){
        accounts = new HashMap<>();
        initialize();
    }

    private void initialize() {
        accounts.put("Patrick", "1234");
        accounts.put("Molly", "FloPup");
    }


    public int numAccounts(){
        return accounts.size();
    }

    public void addAccount(String userName, String password){
        if(containsAccount(userName)) throw new UserInsertedException("User with current user name: " + userName + " is already inserted.");
        accounts.put(userName, password);
    }

    public boolean containsAccount(String userName){
        return accounts.containsKey(userName);
    }

    public String getPassword(String userName){
        if(!containsAccount(userName)) throw new UserNotRegistredException("User with user name: " + userName + " is not registred.");
        return accounts.get(userName);
    }





}
