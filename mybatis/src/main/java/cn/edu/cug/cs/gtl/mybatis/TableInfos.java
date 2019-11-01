package cn.edu.cug.cs.gtl.mybatis;

import cn.edu.cug.cs.gtl.protos.TableInfo;

import java.util.List;

public interface TableInfos {
    boolean isExist(String tabName);
    TableInfo getUserTable(String tabName);
    List<TableInfo> getUserTables();
}
