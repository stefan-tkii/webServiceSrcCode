package ehealth.middleware.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginData implements Serializable
{

    private static final long serialVersionUID = 1L;
    private int id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private long height;
    private long weight;
    private String email;
    private List<UserDevice> devices;

    public LoginData() 
    {

    }

    public LoginData(int id, String firstName, String lastName, Date birthDate, long height, long weight, String email, 
    List<UserDevice> devices) 
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
        this.email = email;
        this.devices = devices;
    }

    public int getId() 
    {
        return this.id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getFirstName() 
    {
        return this.firstName;
    }

    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }

    public String getLastName() 
    {
        return this.lastName;
    }

    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

    public Date getBirthDate() 
    {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) 
    {
        this.birthDate = birthDate;
    }

    public long getHeight() 
    {
        return this.height;
    }

    public void setHeight(long height) 
    {
        this.height = height;
    }

    public long getWeight() 
    {
        return this.weight;
    }

    public void setWeight(long weight) 
    {
        this.weight = weight;
    }

    public String getEmail() 
    {
        return this.email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public List<UserDevice> getDevices() 
    {
        return this.devices;
    }

    public void setDevices(List<UserDevice> devices) 
    {
        this.devices = devices;
    }
 
}