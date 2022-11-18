package binotify;
import binotify.dao.LoggingImpl;
import binotify.services.*;

import javax.xml.ws.Endpoint;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Endpoint.publish("http://localhost:8081/api/hello", new HelloImpl());
        Endpoint.publish("http://localhost:8081/api/logging", new LoggingImpl());
    }
}
