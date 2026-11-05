package client;

public class ErreurApi extends Exception {
    public ErreurApi(String message) {
        super(message);
    }

    public ErreurApi(String message, Throwable cause) {
        super(message, cause);
    }
}
