package binotify.services;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import binotify.dao.LoggingImpl;

@WebService
public class LoggingServiceImpl implements LoggingService {

    @Resource
    private WebServiceContext wsContext;

    @Inject
    private LoggingImpl loggingImpl;

    @WebMethod
    public String getLogging() {
        return loggingImpl.get(1).toString();
    }
}
