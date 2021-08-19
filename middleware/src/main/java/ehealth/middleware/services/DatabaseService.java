package ehealth.middleware.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ehealth.middleware.models.DeviceData;
import ehealth.middleware.models.Measurement;
import ehealth.middleware.models.MeasurementData;
import ehealth.middleware.models.MeasurementRequest;
import ehealth.middleware.models.UserDevice;

public class DatabaseService 
{

    private static final String CONN_STRING = "jdbc:sqlserver://localhost\\sqlexpress;databaseName=E-Zdravstvo;";
    private static final String DB_USER = "stefan";
    private static final String DB_PASS = "123456";

    public DatabaseService()
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
    
    public UserDevice addDevice(DeviceData device)
    {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(CONN_STRING, DB_USER, DB_PASS);
            if(conn != null)
            {
                String sql1 = "SELECT * FROM Person_Device WHERE MBR_ID=? AND NAME_DEVICE=?";
                PreparedStatement stm = conn.prepareStatement(sql1);
                stm.setInt(1, device.getUserId());
                stm.setString(2, device.getName());
                ResultSet res = stm.executeQuery();
                if(res.next())
                {
                    stm.close();
                    conn.close();
                    return null;
                }
                else
                {
                    stm.close();
                    String sql2 =  "INSERT INTO Person_Device (MBR_ID, TYPE_DEVICE, NAME_DEVICE)" +
                    " VALUES (?, ?, ?)";
                    stm = conn.prepareStatement(sql2);
                    stm.setInt(1, device.getUserId());
                    stm.setString(2, device.getType());
                    stm.setString(3, device.getName());
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
                        String sql3 = "SELECT * FROM Person_Device WHERE MBR_ID=? AND NAME_DEVICE=?";
                        stm = conn.prepareStatement(sql3);
                        stm.setInt(1, device.getUserId());
                        stm.setString(2, device.getName());
                        res = stm.executeQuery();
                        if(res.next())
                        {
                            int id = res.getInt("DEVICE_ID");
                            UserDevice dev = new UserDevice(id, device.getUserId(), device.getType(), device.getName());
                            stm.close();
                            conn.close();
                            return dev;
                        }
                        else
                        {
                            stm.close();
                            conn.close();
                            return null;
                        }
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

    public MeasurementData getMeasurements(MeasurementRequest request, Timestamp before, Timestamp after)
    {
        List<Measurement> measurements = new ArrayList<>();
        MeasurementData data = new MeasurementData(measurements);
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(CONN_STRING, DB_USER, DB_PASS);
            if(conn != null)
            {
                String sql1 = "SELECT * FROM Person WHERE MBR_ID=?";
                PreparedStatement stm = conn.prepareStatement(sql1);
                stm.setInt(1, request.getUserId());
                ResultSet result = stm.executeQuery();
                if(result.next())
                {
                    stm.close();
                    String sql2 = "SELECT * FROM Person_Device WHERE MBR_ID=? AND DEVICE_ID=?";
                    stm = conn.prepareStatement(sql2);
                    stm.setInt(1, request.getUserId());
                    stm.setInt(2, request.getDeviceId());
                    result = stm.executeQuery();
                    if(result.next())
                    {
                        stm.close();
                        String sql3 = "SELECT * FROM Measurement WHERE MBR_ID=? AND DEVICE_ID=? AND TYPE_MEASUREMENT=? AND " +
                        "TIME_MEASUREMENT BETWEEN ? AND ? ORDER BY TIME_MEASUREMENT ASC";
                        stm = conn.prepareStatement(sql3);
                        stm.setInt(1, request.getUserId());
                        stm.setInt(2, request.getDeviceId());
                        stm.setString(3, request.getType());
                        stm.setTimestamp(4, before);
                        stm.setTimestamp(5, after);
                        result = stm.executeQuery();
                        while(result.next())
                        {
                            int userId = result.getInt("MBR_ID");
                            int deviceId = result.getInt("DEVICE_ID");
                            String type = result.getString("TYPE_MEASUREMENT");
                            String value = result.getString("VALUE_MEASUREMENT");
                            Date time = result.getTimestamp("TIME_MEASUREMENT");
                            Measurement m = new Measurement(userId, deviceId, time, type, value);
                            measurements.add(m);
                        }
                        data.setMeasurements(measurements);
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
    
}