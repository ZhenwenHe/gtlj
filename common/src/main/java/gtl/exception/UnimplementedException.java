package gtl.exception;

public class UnimplementedException extends Exception {
    public UnimplementedException() {
    }

    public UnimplementedException(String message) {
        super(message);
    }

    public UnimplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnimplementedException(Throwable cause) {
        super(cause);
    }

    public UnimplementedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
