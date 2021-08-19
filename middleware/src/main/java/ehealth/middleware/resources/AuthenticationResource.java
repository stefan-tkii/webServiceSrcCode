package ehealth.middleware.resources;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import ehealth.middleware.exceptions.InvalidDateFormatException;
import ehealth.middleware.models.ErrorMessage;
import ehealth.middleware.models.FullUser;
import ehealth.middleware.models.LoginData;
import ehealth.middleware.models.LoginUser;
import ehealth.middleware.models.User;
import ehealth.middleware.services.AuthenticationService;

@Path("/auth")
public class AuthenticationResource 
{
    
    private AuthenticationService service = new AuthenticationService(); 

    @PUT
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(FullUser user, @Context UriInfo uriInfo)
    {
        if(user.equals(null))
        {
            ErrorMessage msg = new ErrorMessage("Not acceptable", 406, "Please provide an input object with credentials.");
            Response rsp = Response.status(Status.NOT_ACCEPTABLE)
            .entity(msg).build();
            throw new NotAcceptableException(rsp);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            Date birth = null;
            birth = sdf.parse(user.getBirthDate());
            if(birth == null)
            {
                ErrorMessage msg = new ErrorMessage("Not acceptable", 406, "Please provide a birth date in a valid format of: dd/MM/yyyy.");
                throw new InvalidDateFormatException(msg);
            }
            User registered = service.register(user);
            if(registered == null)
            {
                return Response.status(Response.Status.BAD_REQUEST)
                .entity("Error while registering a new account, the MBR: " + 
                user.getMbr() + ", Email: " + user.getEmail() + " or Username: " + user.getUsername() + " is already taken.").build();
            }
            else
            {
                URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(registered.getId())).build();
                return Response.created(uri)
                .build();
            }
        }
        catch (ParseException e) 
        {
            ErrorMessage msg = new ErrorMessage("Not acceptable", 406, "Please provide a birth date in a valid format of: dd-MM-yyyy.");
            throw new InvalidDateFormatException(msg);
        }
    }

    @PUT
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(LoginUser user)
    {
        if(user.equals(null))
        {
            ErrorMessage msg = new ErrorMessage("Not acceptable", 406, "Please provide an input object with credentials.");
            Response rsp = Response.status(Status.NOT_ACCEPTABLE)
            .entity(msg).build();
            throw new NotAcceptableException(rsp);
        }
        LoginData logged = service.login(user);
        if(logged == null)
        {
            ErrorMessage msg = new ErrorMessage("Not found", 404, "Incorrect username and/or password.");
            Response rsp = Response.status(Status.NOT_FOUND)
            .entity(msg).build();
            throw new NotFoundException(rsp);
        }
        else
        {
            return Response.status(Status.OK)
            .entity(logged).build();
        }
    }

}