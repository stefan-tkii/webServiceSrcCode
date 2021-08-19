package ehealth.middleware.models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MeasurementRequest implements Serializable
{

    private static final long serialVersionUID = 1L;
    private int userId;
    private int deviceId;
    private String type;
    private String fromDate;
    private String toDate;

    public MeasurementRequest() 
    {

    }

    public MeasurementRequest(int userId, int deviceId, String type, String fromDate, String toDate) 
    {
        this.userId = userId;
        this.deviceId = deviceId;
        this.type = type;
        this.fromDate = fromDate;
        this.toDate = toDate;
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

    public String getType() 
    {
        return this.type;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getFromDate() 
    {
        return this.fromDate;
    }

    public void setFromDate(String fromDate) {

        this.fromDate = fromDate;
    }

    public String getToDate() 
    {
        return this.toDate;
    }

    public void setToDate(String toDate) 
    {
        this.toDate = toDate;
    }

}