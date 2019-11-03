package cn.edu.cug.cs.gtl.mybatis.metadata;

import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.mybatis.Session;
import cn.edu.cug.cs.gtl.protos.*;

import java.sql.SQLException;
import java.util.List;

/**
 * DictTabInfo
 * DICT_FIELDINFO
 */
public class UserMetaDataUtils implements MetaDataUtils {

    SysMetaDataUtils sysMetadataUtils;


    /**
     *
     * @param session
     * @throws SQLException
     */
    public UserMetaDataUtils(Session session) throws SQLException {
        this.sysMetadataUtils = SysMetaDataUtils.create(session);
        if(!this.sysMetadataUtils.isTableExist("DictTabInfo")){
            this.sysMetadataUtils.createUserDictionaries();
        }
    }

    /**
     * @return
     */
    @Override
    public Session getSession() {
        return sysMetadataUtils.getSession();
    }

    /**
     * @param tabName
     * @return
     */
    @Override
    public boolean isTableExist(String tabName) {
        return sysMetadataUtils.isTableExist(tabName);
//        try {
//            String sql = "select count(*) from DictTabInfo where TENAME = " +tabName;
//            SqlResult r = getSession().execute(sql);
//            if(r.getStatus()){
//                TextValue v = r.getDataset().getTuple(0).getElement(0).unpack(TextValue.class);
//                if(v!=null){
//                    int c = Integer.parseInt(v.getValue());
//                    if(c>=1) return true;
//                }
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return false;
    }

    /**
     * @param tabName
     * @return
     */
    @Override
    public TableInfo getTableInfo(String tabName) {
        try {
            String sql = "select * from DictTabInfo t , DICT_FIELDINFO f where t.TENAME = f.FTABLENAME and t.TENAME=" + tabName;
            SqlResult r = getSession().execute(sql);
            if(r.getStatus()){
                TableInfo.Builder builder = TableInfo.newBuilder();
                builder.setName(tabName);
                DataSet ds = r.getDataset();
                ColumnInfo.Builder cBuilder = ColumnInfo.newBuilder();
                for(Tuple t : ds.getTupleList()){
                    //TODO

                    builder.addColumnInfo(cBuilder.build());
                }
                return builder.build();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public List<String> getUserTableNames(String user){
        return sysMetadataUtils.getUserTableNames(user);
    }

    /**
     *
     * @return
     */
    public List<TableInfo> getUserTableTags(){
        return null;
    }


}
