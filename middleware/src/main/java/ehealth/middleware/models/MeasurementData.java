package ehealth.middleware.models;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MeasurementData implements Serializable
{

    private static final long serialVersionUID = 1L;
    private List<Measurement> measurements;

    public MeasurementData() 
    {
        
    }

    public MeasurementData(List<Measurement> measurements) 
    {
        this.measurements = measurements;
    }

    public List<Measurement> getMeasurements() 
    {
        return this.measurements;
    }

    public void setMeasurements(List<Measurement> measurements) 
    {
        this.measurements = measurements;
    }

}