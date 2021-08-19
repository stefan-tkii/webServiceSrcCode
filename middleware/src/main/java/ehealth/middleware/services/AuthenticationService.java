package ehealth.middleware.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ehealth.middleware.models.FullUser;
import ehealth.middleware.models.LoginData;
import ehealth.middleware.models.LoginUser;
import ehealth.middleware.models.User;
import ehealth.middleware.models.UserDevice;

public class AuthenticationService 
{

    private static final String CONN_STRING = "jdbc:sqlserver://localhost\\sqlexpress;databaseName=E-Zdravstvo;";
    private static final String DB_USER = "stefan";
    private static final String DB_PASS = "123456";

    public AuthenticationService()
    {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(CONN_STRING, DB_USER, DB_PASS);
            if (conn != null) 
            {
                conn.close();
                return;
            }
            else
            {
                return;
            }
        }
        catch(SQLException e)
        {
            conn = null;
            return;
        }
    }
    
    public User register(FullUser user)
    {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(CONN_STRING, DB_USER, DB_PASS);
            if (conn != null) 
            {
                String sql1 = "SELECT * FROM Person WHERE MBR_ID=? OR EMAIL=?";
                PreparedStatement stm = conn.prepareStatement(sql1);
                stm.setString(1, user.getMbr());
                stm.setString(2, user.getEmail());
                ResultSet result = stm.executeQuery();
                if(result.next())
                {
                    stm.close();
                    conn.close();
                    return null;
                }
                else
                {
                    stm.close();
                    boolean username_flag = checkForExistingUsername(user.getUsername(), conn);
                    if(!username_flag)
                    {
                        String sql2 = "INSERT INTO Person (MBR_ID, FIRST_NAME, LAST_NAME, DATE_BIRTH, HEIGHT, WEIGHT, EMAIL)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?)";
                        Date birthDate = null;
                        try
                        {
                            birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(user.getBirthDate());
                            java.sql.Date sqlDate = new java.sql.Date(birthDate.getTime());
                            stm = conn.prepareStatement(sql2);
                            stm.setString(1, user.getMbr());
                            stm.setString(2, user.getFirstName());
                            stm.setString(3, user.getLastName());
                            stm.setDate(4, sqlDate);
                            stm.setInt(5, user.getHeight());
                            stm.setInt(6, user.getWeight());
                            stm.setString(7, user.getEmail());
                            int rows = stm.executeUpdate();
                            if(rows == 0)
                            {
                                stm.close();
                                conn.close();
                                return null;
                            }
                            else
                            {
                                stm.close();
                                String sql3 = "INSERT INTO Person_Login (MBR_ID, PERSON_USERNAME, PERSON_PASSWORD)"
                                + " VALUES (?, ?, ?)";
                                stm = conn.prepareStatement(sql3);
                                stm.setString(1, user.getMbr());
                                stm.setString(2, user.getUsername());
                                stm.setString(3, user.getPassword());
                                rows = stm.executeUpdate();
                                if(rows == 0)
                                {
                                    stm.close();
                                    conn.close();
                                    return null;
                                }
                                else
                                {  
                                    Date bbDate = new SimpleDateFormat("dd/MM/yyyy").parse(user.getBirthDate());
                                    User us = new User(Integer.parseInt(user.getMbr()), 
                                    user.getFirstName(), user.getLastName(), bbDate, 
                                    user.getHeight(), user.getWeight(), user.getEmail());
                                    stm.close();
                                    conn.close();
                                    return us;
                                }
                            }
                        }
                        catch(ParseException ex)
                        {
                            ex.printStackTrace();
                            return null;
                        }
                    }
                    else
                    {
                        conn.close();
                        return null;
                    }
                }
            }
            else
            {
                return null;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
        finally 
        {
            try 
            {
                if (conn != null && !conn.isClosed()) 
                {
                    conn.close();
                }
            } 
            catch (SQLException ex) 
            {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }

    private boolean checkForExistingUsername(String username, Connection conn)
    {
        try
        {
            conn = DriverManager.getConnection(CONN_STRING, DB_USER, DB_PASS);
            String sql4 = "SELECT * FROM Person_Login WHERE PERSON_USERNAME=?";
            PreparedStatement stm = conn.prepareStatement(sql4);
            stm.setString(1, username);
            ResultSet res = stm.executeQuery();
            if(res.next())
            {
                stm.close();
                return true;
            }
            else
            {
                stm.close();
                return false;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return true;
        }
    }

    public LoginData login(LoginUser user)
    {
        List<UserDevice> devicesList = new ArrayList<>();
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(CONN_STRING, DB_USER, DB_PASS);
            if(conn != null)
            {
                String sql1 = "SELECT * FROM Person_Login WHERE PERSON_USERNAME=? AND PERSON_PASSWORD=?";
                PreparedStatement stm = conn.prepareStatement(sql1);
                stm.setString(1, user.getUsername());
                stm.setString(2, user.getPassword());
                ResultSet result = stm.executeQuery();
                if(result.next())
                {
                   int id = result.getInt("MBR_ID");
                   stm.close();
                   String sql2 = "SELECT * FROM Person WHERE MBR_ID=?";
                   stm = conn.prepareStatement(sql2);
                   stm.setInt(1, id);
                   result = stm.executeQuery();
                   if(result.next())
                   {
                        String fName = result.getString("FIRST_NAME");
                        String lName = result.getString("LAST_NAME");
                        Date bDate = result.getTimestamp("DATE_BIRTH");
                        int height = result.getInt("HEIGHT");
                        int weight = result.getInt("WEIGHT");
                        String email = result.getString("EMAIL");
                        LoginData data = new LoginData(id, fName, lName, 
                        bDate, height, weight, email, devicesList);
                        stm.close();
                        String sql3 = "SELECT * FROM Person_Device WHERE MBR_ID=?";
                        stm = conn.prepareStatement(sql3);
                        stm.setInt(1, id);
                        result = stm.executeQuery();
                        while(result.next())
                        {
                            int devId = result.getInt("DEVICE_ID");
                            String type = result.getString("TYPE_DEVICE");
                            String name = result.getString("NAME_DEVICE");
                            UserDevice device = new UserDevice(devId, id, type, name);
                            devicesList.add(device);
                        }
                        data.setDevices(devicesList);
                        stm.close();
                        conn.close();
                        return data;
                   }
                   else
                   {
                       stm.close();
                       conn.close();
                       return null;
                   }
                }
                else
                {
                    stm.close();
                    conn.close();
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally 
        {
            try 
            {
                if (conn != null && !conn.isClosed()) 
                {
                    conn.close();
                }
            } 
            catch (SQLException ex) 
            {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }

}