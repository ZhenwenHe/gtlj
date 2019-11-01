package cn.edu.cug.cs.gtl.cxf.sql;

import cn.edu.cug.cs.gtl.mybatis.URLCoder;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;

import java.io.InputStream;
import java.net.URL;

public class SqlClient {
    public static void main(String[] args) throws Exception {
        // Sent HTTP GET request to query
        String sql = "select FENAME, FCNAME, FTYPE from DICT_FIELDINFO where FTYPE ='BLOB'";
        sql= URLCoder.encode(sql,"utf-8");
        String strUrl = "http://localhost:8999/resultservice/execute/"+sql;
        URL url = new URL(strUrl);
        InputStream in = url.openStream();
        System.out.println(fromInputStream(in));

        // Sent HTTP GET request to query
        sql = "select count(*) from DICT_TABINFO";
        sql= URLCoder.encode(sql,"utf-8");
        strUrl = "http://localhost:9000/resultservice/execute/"+sql;
        url = new URL(strUrl);
        in = url.openStream();
        System.out.println(fromInputStream(in));

    }

    private static String fromInputStream(InputStream in) throws Exception {
        CachedOutputStream bos = new CachedOutputStream();
        IOUtils.copy(in, bos);
        in.close();
        bos.close();
        return bos.getOut().toString();
    }

}
