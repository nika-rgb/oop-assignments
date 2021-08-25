package accountManager;

public class UserNotRegistredException extends RuntimeException {
    public UserNotRegistredException(String message) {
        System.out.println(message);
    }
}
