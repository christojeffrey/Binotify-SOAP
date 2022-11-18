package binotify.services;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface Hello {
    @WebMethod
    public String helloWorld();
}

