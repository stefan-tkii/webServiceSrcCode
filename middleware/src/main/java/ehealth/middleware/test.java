package ehealth.middleware;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class test 
{

    public test() 
    {

    }

    public static void main(String[] args) 
    {
        test t = new test();
        t.printConnectionDetails();
    }

    private void printConnectionDetails()
    {
        String dbURL = "jdbc:sqlserver://localhost\\sqlexpress;databaseName=E-Zdravstvo;";
        Connection conn = null;
        try
        {
            System.out.println("Trying to establish a connection with the database...");
            conn = DriverManager.getConnection(dbURL, "stefan", "123456");
            if (conn != null) 
            {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
            else
            {
                System.out.println("Failed to connect to the database.");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getLocalizedMessage());
        }
        finally 
        {
            try 
            {
                if (conn != null && !conn.isClosed()) 
                {
                    conn.close();
                    System.out.println("The connection is closed.");
                }
                else
                {
                    System.out.println("The connection is closed.");
                }
            } 
            catch (SQLException ex) 
            {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }

}