package binotify.services;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface LoggingService {
    @WebMethod
    public String getLogging();
}
