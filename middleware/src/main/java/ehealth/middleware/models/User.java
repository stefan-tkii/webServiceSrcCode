package ehealth.middleware.models;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User implements Serializable
{

    private static final long serialVersionUID = 1L;
    private int id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private int height;
    private int weight;
    private String email;

    public User() 
    {

    }

    public User(int id, String firstName, String lastName, Date birthDate, int height, int weight, String email) 
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
        this.email = email;
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

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) 
    {
        this.birthDate = birthDate;
    }

    public int getHeight() 
    {
        return this.height;
    }

    public void setHeight(int height) 
    {
        this.height = height;
    }

    public int getWeight() 
    {
        return this.weight;
    }

    public void setWeight(int weight) 
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

}