package binotify.dao;

import java.security.interfaces.ECKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import binotify.model.Subscription;

// a singleton. behaves like a function call. this is the primary way to interact with the subscrtion table in database
public class SubscriptionImpl {

    private static SubscriptionImpl instance = new SubscriptionImpl();
    
    private SubscriptionImpl() {
    }

    public static SubscriptionImpl getInstance() {
        return instance;
    }
    // get 
    // Endpoint Check Status Permintaan
    // for US: memvalidasi apakah user terkait memiliki subscription terhadap penyanyi
    // caller: REST service
    public static Subscription get(String creator_id, String subscriber_id) {
        String query = "SELECT * FROM Subscription WHERE creator_id = ? AND subscriber_id = ?";
        String status = null;
        Connection conn = DataSourceFactory.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, creator_id);
            ps.setString(2, subscriber_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                status = rs.getString("status");
            }

            // todo: handle when get returns no result
            return new Subscription(creator_id, subscriber_id, status);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // create a subscription
    // Menerima Permintaan Subscription
    // caller: Binotify App
     public static void create(String creator_id, String subscriber_id){
        String query = "INSERT INTO Subscription (creator_id, subscriber_id) VALUES (?, ?)";
        Connection conn = DataSourceFactory.getConn();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, subscriber_id);
            statement.setString(2, creator_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Menerima Penerimaan / Penolakan Permintaan Subscription
    // caller: REST service
     public static void update( String creator_id, String subscriber_id, String status){
        String query = "UPDATE Subscription SET status = ? WHERE subscriber_id = ? AND creator_id = ?";
        Connection conn = DataSourceFactory.getConn();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, status);
            statement.setString(2, subscriber_id);
            statement.setString(3, creator_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }   

    // for US: admin meminta list request subsctiption (status = pending)
    // caller: REST service
    public static List<Subscription> getAll() {
        String query = "SELECT * FROM Subscription WHERE status = ?";
        Connection conn = DataSourceFactory.getConn();
        List<Subscription> subscriptions = new ArrayList<Subscription>();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, "PENDING");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Subscription subscription = new Subscription(
                    rs.getString("creator_id"),
                    rs.getString("subscriber_id"),
                    rs.getString("status")
                );
                subscriptions.add(subscription);
            }
            
            // todo: handle when get returns no result
            return subscriptions;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
