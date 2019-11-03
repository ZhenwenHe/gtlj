package cn.edu.cug.cs.gtl.mybatis.oracle;

import cn.edu.cug.cs.gtl.mybatis.Session;
import cn.edu.cug.cs.gtl.mybatis.metadata.SysMetaDataUtils;
import cn.edu.cug.cs.gtl.protos.TableInfo;

import java.util.List;

public class OracleMetaDataUtils implements SysMetaDataUtils {
    private Session session;

    public OracleMetaDataUtils() {
    }

    /**
     * @return
     */
    @Override
    public Session getSession() {
        return this.session;
    }

    /**
     * @param tabName
     * @return
     */
    @Override
    public boolean isTableExist(String tabName) {
        return false;
    }

    /**
     * @param tabName
     * @return
     */
    @Override
    public TableInfo getTableInfo(String tabName) {
        return null;
    }

    @Override
    public void setSession(Session session) {
        this.session=session;
    }


    @Override
    public List<String> getUserTableNames(String user) {
        return null;
    }
}
