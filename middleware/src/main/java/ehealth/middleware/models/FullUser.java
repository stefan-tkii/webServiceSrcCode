package ehealth.middleware.models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FullUser implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    private String mbr;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDate;
    private int height;
    private int weight;
    private String email;

    public FullUser() 
    {

    }

    public FullUser(String mbr, String username, String password, String firstName, String lastName, String birthDate, int height, 
    int weight, String email) 
    {
        this.mbr = mbr;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
        this.email = email;
    }

    public String getMbr() 
    {
        return this.mbr;
    }

    public void setMbr(String mbr) 
    {
        this.mbr = mbr;
    }

    public String getUsername() 
    {
        return this.username;
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getPassword() 
    {
        return this.password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getFirstName() 
    {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
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

    public String getBirthDate() 
    {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) 
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