package binotify;
import binotify.services.LoggingServiceImpl;
import binotify.services.*;

import javax.xml.ws.Endpoint;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Endpoint.publish("http://localhost:8080/api/hello", new HelloImpl());
        Endpoint.publish("http://localhost:8080/api/logging", new LoggingServiceImpl());
    }
}
