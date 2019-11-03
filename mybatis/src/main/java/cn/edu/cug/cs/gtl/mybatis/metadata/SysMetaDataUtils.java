package cn.edu.cug.cs.gtl.mybatis.metadata;

import cn.edu.cug.cs.gtl.mybatis.Session;
import cn.edu.cug.cs.gtl.mybatis.mysql.MysqlMetaDataUtils;
import cn.edu.cug.cs.gtl.mybatis.oracle.OracleMetaDataUtils;
import cn.edu.cug.cs.gtl.mybatis.postgresql.PostgresqlMetaDataUtils;
import cn.edu.cug.cs.gtl.mybatis.sqlite.SqliteMetaDataUtils;
import cn.edu.cug.cs.gtl.mybatis.sqlserver.SqlserverMetaDataUtils;

import java.sql.SQLException;
import java.util.List;

public interface SysMetaDataUtils extends MetaDataUtils {
    void setSession(Session session);


    List<String> getUserTableNames(String user);

    static  SysMetaDataUtils create(Session session) throws SQLException {
        String url = session.getURL();
        if(url.contains("oracle")){
            SysMetaDataUtils s = new OracleMetaDataUtils();
            s.setSession(session);
            return s;
        }
        else if(url.contains("mysql")){
            SysMetaDataUtils s = new MysqlMetaDataUtils();
            s.setSession(session);
            return s;
        }
        else if(url.contains("sqlserver")){
            SysMetaDataUtils s = new SqlserverMetaDataUtils();
            s.setSession(session);
            return s;
        }
        else if(url.contains("sqlite")){
            SysMetaDataUtils s = new SqliteMetaDataUtils();
            s.setSession(session);
            return s;
        }
        else if(url.contains("postgresql")){
            SysMetaDataUtils s = new PostgresqlMetaDataUtils();
            s.setSession(session);
            return s;
        }
        else{
            throw new SQLException("unsupported database system");
        }
    }

}
