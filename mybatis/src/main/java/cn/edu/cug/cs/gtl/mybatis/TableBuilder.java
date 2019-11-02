package cn.edu.cug.cs.gtl.mybatis;

import org.apache.log.output.db.ColumnInfo;

import java.util.List;

public interface TableBuilder {
    boolean isExist(String tabName);
    ColumnInfo getColumnInfo(String colName);
    List<ColumnInfo> getColumnInfos(String tabName);
}
