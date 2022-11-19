package binotify.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import binotify.model.Logging;

@WebService
public interface LoggingService {
    @WebMethod
    public Logging getLogging(int id);
}
