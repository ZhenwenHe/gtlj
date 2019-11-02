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


}
