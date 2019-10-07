package cn.edu.cug.cs.gtl.exception;

public class StoreException extends Exception {
    public static final int CollectionInstance = 0;
    public static final int MapInstance = 1;

    public static String getMessage(int errorCode) {
        switch (errorCode) {
            case CollectionInstance:
                return "不可实例化一个除java.util.Collection/java.util.List/java.util.Set以外的接口或者抽象类";
            case MapInstance:
                return "不可实例化一个除java.util.Map以外的接口或者抽象类";
            default:
                return null;
        }
    }

    public StoreException(int errorCode) {
        super(getMessage(errorCode));
    }

}
