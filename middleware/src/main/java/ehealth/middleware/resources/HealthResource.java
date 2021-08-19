package ehealth.middleware.resources;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ehealth.middleware.exceptions.InvalidDateFormatException;
import ehealth.middleware.models.DeviceData;
import ehealth.middleware.models.ErrorMessage;
import ehealth.middleware.models.MeasurementData;
import ehealth.middleware.models.MeasurementRequest;
import ehealth.middleware.models.UserDevice;
import ehealth.middleware.services.DatabaseService;

@Path("/ehealth")
public class HealthResource 
{
    
    private DatabaseService service = new DatabaseService();

    @PUT
    @Path("/addDevice")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDevice(DeviceData device)
    {
        if(device.equals(null))
        {
            ErrorMessage msg = new ErrorMessage("Not acceptable", 406, "Please provide an input object with credentials.");
            Response rsp = Response.status(Status.NOT_ACCEPTABLE)
            .entity(msg).build();
            throw new NotAcceptableException(rsp);
        }
        UserDevice dvc = service.addDevice(device);
        if(dvc == null)
        {
            ErrorMessage msg = new ErrorMessage("Not found", 404, "Either an incorrect user ID is provided" +
            " or the device's name: " + device.getName() + " is already in use by this user.");
            Response rsp = Response.status(Status.NOT_FOUND)
            .entity(msg).build();
            throw new NotFoundException(rsp);
        }
        else
        {
            return Response.status(Status.OK)
            .entity(dvc).build();
        }
    }

    @PUT
    @Path("/object")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getDataViaObject(MeasurementRequest object) 
    {
        if (object.equals(null)) 
        {
            ErrorMessage msg = new ErrorMessage("Not acceptable", 406, "Please provide an input object.");
            Response rsp = Response.status(Status.NOT_ACCEPTABLE).entity(msg).build();
            throw new NotAcceptableException(rsp);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS");
        try 
        {
            Date start = sdf.parse(object.getFromDate());
            Date end = sdf.parse(object.getToDate());
            if(start.after(end))
            {
                ErrorMessage msg = new ErrorMessage("Not acceptable", 406, "The start time is after the end time.");
                Response rsp = Response.status(Status.NOT_ACCEPTABLE).entity(msg).build();
                throw new NotAcceptableException(rsp);
            }
            Timestamp tmp_start = new Timestamp(start.getTime());
            Timestamp tmp_end = new Timestamp(end.getTime());
            MeasurementData dbData = service.getMeasurements(object, tmp_start, tmp_end);
            if(dbData == null)
            {
                ErrorMessage msg = new ErrorMessage("Not found", 404, "The input does not match with any database records."
                + " Either the MBR=" + object.getUserId() + " doesn't exist or the device ID=" + object.getDeviceId() +
                " is not associated with that user or simply doesn't exist.");
                Response rsp = Response.status(Status.NOT_FOUND).entity(msg).build();
                throw new NotFoundException(rsp);
            }
            else
            {
                return Response.status(Status.OK)
                .entity(dbData).build();
            }
        } 
        catch (ParseException e) 
        {
            ErrorMessage msg = new ErrorMessage("Not acceptable", 406, "Please provide a Start and an End date in a valid format of: MM-dd-yyyy HH:mm:ss.SSS.");
            throw new InvalidDateFormatException(msg);
        }
    }
    
}