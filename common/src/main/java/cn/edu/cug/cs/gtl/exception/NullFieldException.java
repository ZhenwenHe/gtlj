package cn.edu.cug.cs.gtl.exception;

public class NullFieldException extends Exception {
    public NullFieldException(String s) {
        super(s);
    }

    public NullFieldException(int pos) {
        super(Integer.valueOf(pos).toString());
    }
}
