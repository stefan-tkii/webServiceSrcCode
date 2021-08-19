package ehealth.middleware.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ehealth.middleware.models.ErrorMessage;

public class InvalidDateFormatException extends WebApplicationException
{

    private static final long serialVersionUID = 1L;
    
    public InvalidDateFormatException(ErrorMessage message)
    {
        super(Response.status(Response.Status.NOT_ACCEPTABLE)
        .entity(message).type(MediaType.APPLICATION_JSON).build());
    }
    
}