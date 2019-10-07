package gtl.exception;

/**
 * Created by ZhenwenHe on 2017/3/30.
 */
public class WarningException extends Exception {
    public WarningException() {
    }

    public WarningException(String message) {
        super(message);
    }

    public WarningException(String message, Throwable cause) {
        super(message, cause);
    }

    public WarningException(Throwable cause) {
        super(cause);
    }
}
