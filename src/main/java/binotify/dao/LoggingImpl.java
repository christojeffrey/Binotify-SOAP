package binotify.dao;

import binotify.model.Logging;
import java.sql.*;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

// a singleton. behaves like a function call. this is the primary way to interact with the subscrtion table in database
@WebService
public class LoggingImpl implements LoggingInterface {
    private static LoggingImpl instance = null;
    private LoggingImpl() {
    }
    public static LoggingImpl getInstance() {
        if(instance == null) {
            instance = new LoggingImpl();
        }
        return instance;
    }

    public static boolean create(String IP, String description, String endpoint){
    
        // description, IP, endpoint, requested_at
        return true;
    }
    
    



}
