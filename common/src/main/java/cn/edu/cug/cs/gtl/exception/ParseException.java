package cn.edu.cug.cs.gtl.exception;

/**
 * Thrown by a <code>WKTReader</code> when a parsing problem occurs.
 */
public class ParseException extends Exception {

    /**
     * Creates a <code>ParseException</code> with the given detail message.
     *
     * @param message a description of this <code>ParseException</code>
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Creates a <code>ParseException</code> with <code>e</code>s detail message.
     *
     * @param e an exception that occurred while a <code>WKTReader</code> was
     *          parsing a Well-known Text string
     */
    public ParseException(Exception e) {
        this(e.toString(), e);
    }

    /**
     * Creates a <code>ParseException</code> with <code>e</code>s detail message.
     *
     * @param message a description of this <code>ParseException</code>
     * @param e       a throwable that occurred while a com.vividsolutions.jts.io reader was
     *                parsing a string representation
     */
    public ParseException(String message, Throwable e) {
        super(message, e);
    }
}

