package ehealth.middleware;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ehealth.middleware.models.Measurement;
import ehealth.middleware.models.MeasurementRequest;
import ehealth.middleware.models.User;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SqlTest {

    public SqlTest() {

    }

    public static void main(String[] args) {
        SqlTest t = new SqlTest();
        //t.insertMeasurement();
        //t.printResultOfSelect();
        /*
        User u = t.printMixedResult("556613", "picke", "Jassumnevidliv21", "pece", "meckata", "13/12/2012", 176, 99,
                "klinecot@gmail.com");
        if (u == null) {
            System.out.println("Failed");
        } 
        else 
        {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try 
            {
                String json = ow.writeValueAsString(u);
                System.out.println(json);
            } 
            catch (JsonProcessingException e) 
            {
                System.out.println(e.getLocalizedMessage());
            }
        }
        */
        String dateString1 = "11-26-2019 08:05:00.000";
        String dateString2 = "11-26-2019 08:07:00.000";
        Date time1 = null;
        Date time2 = null;
        try 
        {
            time1 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS").parse(dateString1);
            time2 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS").parse(dateString2);
            Timestamp tmp1 = new Timestamp(time1.getTime());
            Timestamp tmp2 = new Timestamp(time2.getTime());
            MeasurementRequest req = new MeasurementRequest(214600, 6, "Heart rate", dateString1, dateString2);
            List<Measurement> list = t.getMeasurements(req, tmp1, tmp2);
            for(Measurement m : list)
            {
                System.out.println(m.getTime().toString());
                System.out.println(m.getValue());
            }
        } 
        catch (ParseException e) 
        {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void printResultOfSelect()
    {
        String dbURL = "jdbc:sqlserver://localhost\\sqlexpress;databaseName=E-Zdravstvo";
        Connection conn = null;
        System.out.println("Trying to establish a connection with the database...");
        try 
        {
            conn = DriverManager.getConnection(dbURL, "stefan", "123456");
            if(conn != null)
            {
                String dateString1 = "11-26-2019 08:06:02.000";
                String dateString2 = "11-26-2019 08:06:04.000";
                Date time1 = null;
                Date time2 = null;
                System.out.println("Preparing and executing the query...");
                time1 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS").parse(dateString1);
                time2 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS").parse(dateString2);
                Timestamp tmp1 = new Timestamp(time1.getTime());
                Timestamp tmp2 = new Timestamp(time2.getTime());
                String sql = "SELECT * FROM Measurement WHERE TIME_MEASUREMENT BETWEEN ? AND ?";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, "214600");
                stm.setString(2, "6");
                stm.setTimestamp(3, tmp1);
                stm.setTimestamp(4, tmp2);
                ResultSet result = stm.executeQuery();
                while(result.next())
                {
                    String type = result.getString("TYPE_MEASUREMENT");
                    String value = result.getString("VALUE_MEASUREMENT");
                    System.out.println("Measurement info: " + type + ", " + value);
                }
                result.close();
            }
            else
            {
                System.out.println("Failed to connect to the database.");
            }
        } 
        catch (SQLException e) 
        {
           System.out.println(e.getLocalizedMessage());
        } 
        catch (ParseException e) 
        {
            e.printStackTrace();
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

    private void insertMeasurement()
    {
        String dbURL = "jdbc:sqlserver://localhost\\sqlexpress;databaseName=E-Zdravstvo";
        Connection conn = null;
        try
        {
            System.out.println("Trying to establish a connection with the database...");
            conn = DriverManager.getConnection(dbURL, "stefan", "123456");
            if (conn != null) 
            {
                System.out.println("Preparing and executing the query...");
                String mbr = "214600";
                String deviceId = "6";
                String type = "Heart rate";
                String value = "98";
                String dateString = "11-26-2019 08:06:03.238";
                Date time = null;
                try
                {
                    time = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS").parse(dateString);
                    Timestamp sqlDate = new Timestamp(time.getTime());
                    String sql1 = "INSERT INTO Measurement (MBR_ID, DEVICE_ID, TIME_MEASUREMENT, TYPE_MEASUREMENT, VALUE_MEASUREMENT)" 
                    + " VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement stm = conn.prepareStatement(sql1);
                    stm.setString(1, mbr);
                    stm.setString(2, deviceId);
                    stm.setTimestamp(3, sqlDate);
                    stm.setString(4, type);
                    stm.setString(5, value);
                    int rows = stm.executeUpdate();
                    if(rows == 0)
                    {
                        System.out.println("Query execution has failed.");
                    }
                    else
                    {
                        System.out.println("A total of " + rows + " rows were affected by this query.");
                    }
                }
                catch(ParseException ex)
                {
                    System.out.println(ex.getLocalizedMessage());
                }
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

    private List<Measurement> getMeasurements(MeasurementRequest request, Timestamp before, Timestamp after)
    {
        List<Measurement> measurements = new ArrayList<>();
        Connection conn = null;
        try
        {
            String CONN_STRING = "jdbc:sqlserver://localhost\\sqlexpress;databaseName=E-Zdravstvo";
            String DB_USER = "stefan";
            String DB_PASS = "123456";
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
                        return measurements;
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

    private User printMixedResult(String mbr, String username, String password, String fName, String lName, 
    String bDate, int height, int weight, String email)
    {
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://localhost\\sqlexpress;databaseName=E-Zdravstvo;integratedSecurity=true";
        try
        {
            conn = DriverManager.getConnection(dbURL, "", "");
            if (conn != null) 
            {
                String sql1 = "SELECT * FROM Person WHERE MBR_ID=? OR EMAIL=?";
                PreparedStatement stm = conn.prepareStatement(sql1);
                stm.setString(1, mbr);
                stm.setString(2, email);
                ResultSet result = stm.executeQuery();
                if(result.next())
                {
                    System.out.println("Found existing.");
                    stm.close();
                    conn.close();
                    return null;
                }
                else
                {
                    stm.close();
                    String sql2 = "INSERT INTO Person (MBR_ID, FIRST_NAME, LAST_NAME, DATE_BIRTH, HEIGHT, WEIGHT, EMAIL)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";
                    Date birthDate = null;
                    try
                    {
                        birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(bDate);
                        java.sql.Date sqlDate = new java.sql.Date(birthDate.getTime());
                        stm = conn.prepareStatement(sql2);
                        stm.setString(1, mbr);
                        stm.setString(2, fName);
                        stm.setString(3, lName);
                        stm.setDate(4, sqlDate);
                        stm.setInt(5, height);
                        stm.setInt(6, weight);
                        stm.setString(7, email);
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
                            stm.setString(1, mbr);
                            stm.setString(2, username);
                            stm.setString(3, password);
                            rows = stm.executeUpdate();
                            if(rows == 0)
                            {
                                stm.close();
                                conn.close();
                                return null;
                            }
                            else
                            {  
                                Date bbDate = new SimpleDateFormat("dd/MM/yyyy").parse(bDate);
                                User us = new User(Integer.parseInt(mbr), 
                                fName, lName, bbDate, 
                                height, weight, email);
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