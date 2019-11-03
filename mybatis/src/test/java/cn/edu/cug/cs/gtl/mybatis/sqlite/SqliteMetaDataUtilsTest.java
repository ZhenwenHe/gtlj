package cn.edu.cug.cs.gtl.mybatis.sqlite;

import cn.edu.cug.cs.gtl.mybatis.Session;
import cn.edu.cug.cs.gtl.mybatis.metadata.UserMetaDataUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class SqliteMetaDataUtilsTest {

    @Test
    public void isTableExist() {
        try {
            Session session= Session.open();
            String url = session.getURL();
            if(url.contains("sqlite")==false)
                return;

            UserMetaDataUtils userMetaDataUtils= new UserMetaDataUtils(session);

            boolean b = userMetaDataUtils.isTableExist("hax_nn");
            System.out.print(b);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void getTableInfo() {
    }

    @Test
    public void createUserDictionaries() {

        try {
            Session session= Session.open();
            String url = session.getURL();
            if(url.contains("sqlite")==false)
                return;

            UserMetaDataUtils userMetaDataUtils= new UserMetaDataUtils(session);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getUserTableNames(){
        try {
            Session session= Session.open();
            String url = session.getURL();
            if(url.contains("sqlite")==false)
                return;

            UserMetaDataUtils userMetaDataUtils= new UserMetaDataUtils(session);
            System.out.print(userMetaDataUtils.getUserTableNames(null));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}