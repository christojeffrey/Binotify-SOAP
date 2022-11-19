package binotify.services;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import binotify.dao.LoggingImpl;
import binotify.model.Logging;

@WebService
public class LoggingServiceImpl implements LoggingService {

    @Resource
    private WebServiceContext wsContext;

    @Inject
    private LoggingImpl loggingImpl = new LoggingImpl();

    @WebMethod
    public Logging getLogging(int id) {
        return loggingImpl.get(id);
    }
}
