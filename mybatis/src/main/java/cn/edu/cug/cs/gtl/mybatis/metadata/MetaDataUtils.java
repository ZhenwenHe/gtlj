package cn.edu.cug.cs.gtl.mybatis.metadata;

import cn.edu.cug.cs.gtl.mybatis.Session;
import cn.edu.cug.cs.gtl.protos.TableInfo;
import cn.edu.cug.cs.gtl.protos.ColumnInfo;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

public interface MetaDataUtils {

    /**
     *
     * @return
     */
    Session getSession();
    /**
     *
     * @param tabName
     * @return
     */
    boolean isTableExist(String tabName);


    /**
     *
     * @param tabName
     * @param colName
     * @return
     */
    default ColumnInfo getColumnInfo(String tabName , String colName){
        List<ColumnInfo> ls =  getColumnInfos(tabName);
        for(ColumnInfo ci : ls){
            if(ci.getName().equals(colName))
                return ci;
        }
        return null;
    }

    /**
     *
     * @param tabName
     * @return
     */
    default List<ColumnInfo> getColumnInfos(String tabName){
        TableInfo tableInfo = getTableInfo(tabName);
        return tableInfo.getColumnInfoList();
    }

    /**
     *
     * @param tabName
     * @return
     */
    TableInfo getTableInfo(String tabName);

    default void createUserDictionaries(){
        String sqlDictTabInfo =null;
        {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("create table DictTabInfo  (");
            sqlBuilder.append("TCNAME VARCHAR(128),");
            sqlBuilder.append("TENAME VARCHAR(128) not null,");
            sqlBuilder.append("TCODE VARCHAR(128),");
            sqlBuilder.append("TTYPE VARCHAR(64),");
            sqlBuilder.append("TCHECK VARCHAR(256),");
            sqlBuilder.append("TMEMO VARCHAR(512),");
            sqlBuilder.append("TSCHEMA VARCHAR(128),");
            sqlBuilder.append("TTAG VARCHAR(256),");
            sqlBuilder.append("TSQL VARCHAR(256),");
            sqlBuilder.append("PRIMARY KEY (TENAME))");
            sqlDictTabInfo = sqlBuilder.toString();
        }

        String sqlDictFieldInfo =null;
        {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("create table DICT_FIELDINFO  (");
            sqlBuilder.append("FCNAME VARCHAR(128) not null,");
            sqlBuilder.append("FENAME VARCHAR(128) not null,");
            sqlBuilder.append("FCODE VARCHAR(128),");
            sqlBuilder.append("FTYPE VARCHAR(64),");
            sqlBuilder.append("FLENGTH INT,");
            sqlBuilder.append("FPRECISION INT,");
            sqlBuilder.append("FSCALE INT,");
            sqlBuilder.append("FISNULL VARCHAR(6),");
            sqlBuilder.append("FDEFAULT VARCHAR(256),");
            sqlBuilder.append("FMAX VARCHAR(256),");
            sqlBuilder.append("FMIN VARCHAR(256),");
            sqlBuilder.append("FCHECK VARCHAR(256),");
            sqlBuilder.append("FMEMO VARCHAR(512),");
            sqlBuilder.append("FTABLENAME VARCHAR(128),");
            sqlBuilder.append("FTAG VARCHAR(128),");
            sqlBuilder.append("FCHARSET VARCHAR(64),");
            sqlBuilder.append("FENUM VARCHAR(512),");
            sqlBuilder.append("FPROCEDURE BLOB,");
            sqlBuilder.append("PRIMARY KEY (FENAME,FTABLENAME))");
            sqlDictFieldInfo = sqlBuilder.toString();
        }

        getSession().execute(sqlDictTabInfo);
        getSession().execute(sqlDictFieldInfo);
        getSession().commit();
    }


}
