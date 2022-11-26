package binotify;
import binotify.services.*;

import javax.xml.ws.Endpoint;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Endpoint.publish("http://0.0.0.0:8080/api/binotify", new BinotifyServiceImpl());
    }
}
