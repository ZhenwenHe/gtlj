package cn.edu.cug.cs.gtl.solr.demo;

import java.sql.*;

public class SolrParallelSqlQueryApp {
    public static void main(String []args){
        //jdbc:solr://SOLR_ZK_CONNECTION_STRING?collection=COLLECTION_NAME
        String zkHost = "120.24.168.173:9983";
        Connection con = null;
        Statement stmt=null;
        ResultSet rs =null;
        try {
            con = DriverManager.getConnection("jdbc:solr://" + zkHost + "?collection=gtl");
            System.out.println(con.toString());
            stmt = con.createStatement();
            rs = stmt.executeQuery("select count(*) from gtl");

            while(rs.next()) {
                String a_s = rs.getString("id");
                String s = rs.getString("title");
            }

            rs.close();
            stmt.close();
            con.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
