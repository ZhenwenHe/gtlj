package cn.edu.cug.cs.gtl.mybatis.sqlite;

import cn.edu.cug.cs.gtl.mybatis.Session;
import cn.edu.cug.cs.gtl.mybatis.metadata.SysMetaDataUtils;
import cn.edu.cug.cs.gtl.protos.*;
import com.google.protobuf.Any;

import java.util.ArrayList;
import java.util.List;

public class SqliteMetaDataUtils implements SysMetaDataUtils {
    private Session session;

    public SqliteMetaDataUtils() {
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
        String sql = "select count(*) from sqlite_master where type='table' and name ='"
        +tabName+"'";
        try{
            SqlResult r = getSession().execute(sql);
            if(r.getStatus()==true){
                DataSet ds = r.getDataset();
                Any any = ds.getTuple(0).getElement(0);
                TextValue textValue=any.unpack(TextValue.class);
                if(textValue!=null){
                    if(Integer.parseInt(textValue.getValue())==1){
                        return true;
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * @param tabName
     * @return
     */
    @Override
    public TableInfo getTableInfo(String tabName) {
        String sql = "select name, sql from sqlite_master where type='table' and tbl_name= " + tabName;
        try{
            SqlResult r = getSession().execute(sql);
            if(r.getStatus()==true){
                DataSet ds = r.getDataset();
                Tuple t = ds.getTuple(0);
                if(t!=null){
                    TableInfo.Builder builder=TableInfo.newBuilder();
                    Any a = t.getElement(0);
                    TextValue textValue=a.unpack(TextValue.class);
                    if(textValue!=null){
                        builder.setName(textValue.getValue());
                    }
                    Any b = t.getElement(1);
                    textValue=b.unpack(TextValue.class);
                    if(textValue!=null){
                        builder.setSql(textValue.getValue());
                    }
                    return builder.build();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void setSession(Session session) {
        this.session=session;
    }

    @Override
    public List<String> getUserTableNames(String user) {
        String sql = "select name from sqlite_master where type='table' ";
        ArrayList<String> ls = new ArrayList<>();
        try{
            SqlResult r = getSession().execute(sql);
            if(r.getStatus()==true){
                DataSet ds = r.getDataset();
                for(Tuple t: ds.getTupleList()){
                    Any any =t.getElement(0);
                    TextValue textValue=any.unpack(TextValue.class);
                    if(textValue!=null){
                        ls.add(textValue.getValue());
                    }
                }
                return ls;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ls;
    }
}
