package cn.edu.cug.cs.gtl.series.app;

import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;

public class HaxNNClassificationTest {

    @Test
    public void mainTest() {
        Connection connection = null;
        try
        {
            /**
             * Here is an example to select a file C:\work\mydatabase.db (in Windows)
             *
             * Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/work/mydatabase.db");
             * A UNIX (Linux, Mac OS X, etc) file /home/leo/work/mydatabase.db
             *
             * Connection connection = DriverManager.getConnection("jdbc:sqlite:/home/leo/work/mydatabase.db");
             *  SQLite supports on-memory database management, which does not create any database files.
             *  To use a memory database in your Java code, get the database connection as follows:
             * Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
             */
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:series.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}