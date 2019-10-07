package cn.edu.cug.cs.gtl.exception;

import cn.edu.cug.cs.gtl.common.Identifier;

/**
 * Created by ZhenwenHe on 2016/12/8.
 */
public class InvalidPageException extends Exception {
    public InvalidPageException() {
        super("Invalid Page");
    }

    public InvalidPageException(Identifier page) {
        super("Invalid Page:" + page.toString());
    }

    public InvalidPageException(String message) {
        super(message);
    }
}
