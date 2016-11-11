package cc.logirl.dbhelper.exception;

/**
 * Created by xinxi on 2016/10/21.
 */
public class DBHelperException extends RuntimeException {
    public DBHelperException() {
    }

    public DBHelperException(String message) {
        super(message);
    }

    public DBHelperException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBHelperException(Throwable cause) {
        super(cause);
    }

    public DBHelperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
