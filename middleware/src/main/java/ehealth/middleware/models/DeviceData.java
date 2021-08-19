package ehealth.middleware.models;

import java.io.Serializable;

public class DeviceData implements Serializable
{

    private static final long serialVersionUID = 1L;
    private int userId;
    private String type;
    private String name;

    public DeviceData() 
    {
        
    }

    public DeviceData(int userId, String type, String name) 
    {
        this.userId = userId;
        this.type = type;
        this.name = name;
    }

    public int getUserId() 
    {
        return this.userId;
    }

    public void setUserId(int userId) 
    {
        this.userId = userId;
    }

    public String getType() 
    {
        return this.type;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getName() 
    {
        return this.name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

}