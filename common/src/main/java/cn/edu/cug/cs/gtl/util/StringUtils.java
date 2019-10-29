package cn.edu.cug.cs.gtl.util;

/**
 * Created by hadoop on 17-3-20.
 */

import cn.edu.cug.cs.gtl.io.type.StorableInt;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;


public class StringUtils {

    public final static String NEWLINE = System.getProperty("line.separator");
    private static NumberFormat SIMPLE_ORDINATE_FORMAT = new DecimalFormat("0.#");
    public static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Mimics the the Java SE {@link String#split(String)} method.
     *
     * @param s         the string to split.
     * @param separator the separator to use.
     * @return the array of split strings.
     */
    public static String[] split(String s, String separator) {
        int separatorlen = separator.length();
        ArrayList tokenList = new ArrayList();
        String tmpString = "" + s;
        int pos = tmpString.indexOf(separator);
        while (pos >= 0) {
            String token = tmpString.substring(0, pos);
            tokenList.add(token);
            tmpString = tmpString.substring(pos + separatorlen);
            pos = tmpString.indexOf(separator);
        }
        if (tmpString.length() > 0)
            tokenList.add(tmpString);
        String[] res = new String[tokenList.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = (String) tokenList.get(i);
        }
        return res;
    }

    /**
     * Returns an throwable's stack trace
     */
    public static String getStackTrace(Throwable t) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        t.printStackTrace(ps);
        return os.toString();
    }

    public static String getStackTrace(Throwable t, int depth) {
        String stackTrace = "";
        StringReader stringReader = new StringReader(getStackTrace(t));
        LineNumberReader lineNumberReader = new LineNumberReader(stringReader);
        for (int i = 0; i < depth; i++) {
            try {
                stackTrace += lineNumberReader.readLine() + NEWLINE;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stackTrace;
    }

    public static String toString(double d) {
        return SIMPLE_ORDINATE_FORMAT.format(d);
    }

    public static String spaces(int n) {
        return chars(' ', n);
    }

    public static String chars(char c, int n) {
        char[] ch = new char[n];
        for (int i = 0; i < n; i++) {
            ch[i] = c;
        }
        return new String(ch);
    }

    public static String loadString(DataInput in) throws IOException {
        try {
            int bytesLength = in.readInt();
            if (bytesLength == -1) return null;
            if (bytesLength == 0) return new String("");
            byte[] bs = new byte[bytesLength];
            in.readFully(bs, 0, bytesLength);
            return new String(bs, 0, bytesLength);
        } catch (Exception e) {
            e.printStackTrace();
            return new String("");
        }
    }

    public static int storeString(String s, DataOutput out) throws IOException {
        if (s == null) {
            out.writeInt(-1);
            return -1;
        }
        if (s.equals("")) {
            out.writeInt(0);
            return 0;
        }

        try {
            byte[] bs = s.getBytes();
            out.writeInt(bs.length);
            if (bs.length > 0)
                out.write(bs);
            return bs.length;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 从输入流中读取数据填充对象
     *
     * @param in 输入流
     * @return 成功返回true，否则返回false
     * @throws IOException
     */
    public static String read(InputStream in) throws IOException {
        try {
            byte[] lenBytes = new byte[4];
            in.read(lenBytes);
            int bytesLength = StorableInt.decode(lenBytes);
            if (bytesLength == 0) return new String("");
            byte[] bs = new byte[bytesLength];
            in.read(bs);
            return new String(bs, 0, bytesLength);
        } catch (IOException e) {
            e.printStackTrace();
            return new String("");
        }
    }

    /**
     * 向输出流中写出数据
     *
     * @param out 输出流
     * @return 成功返回非0，否则返回0
     * @throws IOException
     */
    public static int write(String s, OutputStream out) throws IOException {
        try {
            byte[] bs = s.getBytes();
            out.write(StorableInt.encode(bs.length));
            if (bs.length > 0)
                out.write(bs);
            return 4 + bs.length;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String load(DataInput in) throws IOException {
        return loadString(in);
    }

    public static int store(String s, DataOutput out) throws IOException {
        return storeString(s, out);
    }

    public static long getByteArraySize(String s) {
        return s.getBytes().length + 4;
    }

    /**
     * Returns a string representation of the given array. This method takes an Object
     * to allow also all types of primitive type arrays.
     *
     * @param array The array to create a string representation for.
     * @return The string representation of the array.
     * @throws IllegalArgumentException If the given object is no array.
     */
    public static String arrayToString(Object array) {
        if (array == null) {
            throw new NullPointerException();
        }

        if (array instanceof int[]) {
            return Arrays.toString((int[]) array);
        }
        if (array instanceof long[]) {
            return Arrays.toString((long[]) array);
        }
        if (array instanceof Object[]) {
            return Arrays.toString((Object[]) array);
        }
        if (array instanceof byte[]) {
            return Arrays.toString((byte[]) array);
        }
        if (array instanceof double[]) {
            return Arrays.toString((double[]) array);
        }
        if (array instanceof float[]) {
            return Arrays.toString((float[]) array);
        }
        if (array instanceof boolean[]) {
            return Arrays.toString((boolean[]) array);
        }
        if (array instanceof char[]) {
            return Arrays.toString((char[]) array);
        }
        if (array instanceof short[]) {
            return Arrays.toString((short[]) array);
        }

        if (array.getClass().isArray()) {
            return "<unknown array type>";
        } else {
            throw new IllegalArgumentException("The given argument is no array.");
        }
    }

    /**
     * This method calls {@link Object#toString()} on the given object, unless the
     * object is an array. In that case, it will use the {@link #arrayToString(Object)}
     * method to create a string representation of the array that includes all contained
     * elements.
     *
     * @param o The object for which to create the string representation.
     * @return The string representation of the object.
     */
    public static String arrayAwareToString(Object o) {
        if (o == null) {
            return "null";
        }
        if (o.getClass().isArray()) {
            return arrayToString(o);
        }

        return o.toString();
    }

    /**
     * Given a hex string this will return the byte array corresponding to the
     * string .
     *
     * @param hex the hex String array
     * @return a byte array that is a hex string representation of the given
     * string. The size of the byte array is therefore hex.length/2
     */
    public static byte[] hexStringToByte(final String hex) {
        final byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bts;
    }

    /**
     * Given an array of bytes it will convert the bytes to a hex string
     * representation of the bytes.
     *
     * @param bytes the bytes to convert in a hex string
     * @param start start index, inclusively
     * @param end   end index, exclusively
     * @return hex string representation of the byte array
     */
    public static String byteToHexString(final byte[] bytes, final int start, final int end) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes == null");
        }


        int length = end - start;
        char[] out = new char[length * 2];

        for (int i = start, j = 0; i < end; i++) {
            out[j++] = HEX_CHARS[(0xF0 & bytes[i]) >>> 4];
            out[j++] = HEX_CHARS[0x0F & bytes[i]];
        }

        return new String(out);
    }

    /**
     * Given an array of bytes it will convert the bytes to a hex string
     * representation of the bytes.
     *
     * @param bytes the bytes to convert in a hex string
     * @return hex string representation of the byte array
     */
    public static String byteToHexString(final byte[] bytes) {
        return byteToHexString(bytes, 0, bytes.length);
    }

    /**
     * Replaces control characters by their escape-coded version. For example,
     * if the string contains a line break character ('\n'), this character will
     * be replaced by the two characters backslash '\' and 'n'. As a consequence, the
     * resulting string will not contain any more control characters.
     *
     * @param str The string in which to replace the control characters.
     * @return The string with the replaced characters.
     */
    public static String showControlCharacters(String str) {
        int len = str.length();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i += 1) {
            char c = str.charAt(i);
            switch (c) {
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Creates a random string with a length within the given interval. The string contains only characters that
     * can be represented as a single code point.
     *
     * @param rnd       The random used to create the strings.
     * @param minLength The minimum string length.
     * @param maxLength The maximum string length (inclusive).
     * @return A random String.
     */
    public static String getRandomString(Random rnd, int minLength, int maxLength) {
        int len = rnd.nextInt(maxLength - minLength + 1) + minLength;

        char[] data = new char[len];
        for (int i = 0; i < data.length; i++) {
            data[i] = (char) (rnd.nextInt(0x7fff) + 1);
        }
        return new String(data);
    }

    /**
     * Creates a random string with a length within the given interval. The string contains only characters that
     * can be represented as a single code point.
     *
     * @param rnd       The random used to create the strings.
     * @param minLength The minimum string length.
     * @param maxLength The maximum string length (inclusive).
     * @param minValue  The minimum character value to occur.
     * @param maxValue  The maximum character value to occur.
     * @return A random String.
     */
    public static String getRandomString(Random rnd, int minLength, int maxLength, char minValue, char maxValue) {
        int len = rnd.nextInt(maxLength - minLength + 1) + minLength;

        char[] data = new char[len];
        int diff = maxValue - minValue + 1;

        for (int i = 0; i < data.length; i++) {
            data[i] = (char) (rnd.nextInt(diff) + minValue);
        }
        return new String(data);
    }

    /**
     *
     * @param d
     * @return
     */
    public String encodeToString(byte[] d){
        return Base64.getEncoder().encodeToString(d);
    }

    /**
     *
     * @param s
     * @return
     */
    public byte[] decodeToBytes(String s){
        return Base64.getDecoder().decode(s);
    }
}
