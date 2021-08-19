package ehealth.middleware.models;

import java.io.Serializable;
import java.util.Date;

public class Measurement implements Serializable 
{

    private static final long serialVersionUID = 1L;
    private int userId;
    private int deviceId;
    private Date time;
    private String type;
    private String value;

    public Measurement() 
    {

    }

    public Measurement(int userId, int deviceId, Date time, String type, String value) 
    {
        this.userId = userId;
        this.deviceId = deviceId;
        this.time = time;
        this.type = type;
        this.value = value;
    }

    public int getUserId() 
    {
        return this.userId;
    }

    public void setUserId(int userId) 
    {
        this.userId = userId;
    }

    public int getDeviceId() 
    {
        return this.deviceId;
    }

    public void setDeviceId(int deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Date getTime() 
    {
        return this.time;
    }

    public void setTime(Date time) 
    {
        this.time = time;
    }

    public String getType() 
    {
        return this.type;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getValue() 
    {
        return this.value;
    }

    public void setValue(String value) 
    {
        this.value = value;
    }

}