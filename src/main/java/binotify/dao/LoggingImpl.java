package binotify.dao;

import binotify.dao.LoggingInterface;
import binotify.model.Logging;
import java.sql.*;
import java.util.Optional;

public class LoggingImpl implements LoggingInterface {
    // private LoggingImpl() {
    // }
    private static LoggingImpl instance = new LoggingImpl();

    public static LoggingImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Logging> get(int id) {
        String query = "SELECT * FROM logging WHERE id = ?";
        Optional<Logging> logging = Optional.empty();

        String description;
        String IP;
        String endpoint;
        String requested_at;

        Connection conn = DataSourceFactory.getConn();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    description = resultSet.getString("description");
                    IP = resultSet.getString("IP");
                    endpoint = resultSet.getString("endpoint");
                    requested_at = resultSet.getString("requested_at");
                    logging = Optional.of(new Logging(id, description, IP, endpoint, requested_at));
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return logging;
    }
}
