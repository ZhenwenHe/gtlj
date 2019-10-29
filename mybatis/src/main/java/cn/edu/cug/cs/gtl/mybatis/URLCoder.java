package cn.edu.cug.cs.gtl.mybatis;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLCoder {
    /**
     *
     * @param sql
     * @param enc
     * @return
     */
    public static String encode(String sql, String enc){
        try {
            return URLEncoder.encode(sql,enc);
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param sql
     * @param enc
     * @return
     */
    public static String decode(String sql, String enc){
        try{
            return URLDecoder.decode(sql,enc);
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }
    }
}
