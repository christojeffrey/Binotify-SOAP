package binotify.dao;

import binotify.dao.LoggingInterface;
import binotify.model.Logging;
import java.sql.*;
import java.util.Optional;
import javax.jws.WebService;

@WebService
public class LoggingImpl implements LoggingInterface {
    // private LoggingImpl() {
    // }
    private static LoggingImpl instance = new LoggingImpl();

    public static LoggingImpl getInstance() {
        return instance;
    }

    @Override
    public Logging get(int id) {
        String query = "SELECT * FROM logging WHERE id = ?";
        Logging logging = new Logging();

        Connection conn = DataSourceFactory.getConn();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    logging.set_id(resultSet.getInt("id"));
                    logging.set_description(resultSet.getString("description"));
                    logging.set_IP(resultSet.getString("IP"));
                    logging.set_endpoint(resultSet.getString("endpoint"));
                    logging.set_requested_at(resultSet.getTimestamp("requested_at"));
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return logging;
    }
}
