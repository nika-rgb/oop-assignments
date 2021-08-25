package accountManager;



public class UserInsertedException extends RuntimeException {
    public UserInsertedException(String message){
        System.out.println(message);
    }
}
