package binotify.model;

import java.sql.Timestamp;

public class Logging {
    // id, description, IP, endpoint, requested_at
    private int id;
    private String description;
    private String IP;
    private String endpoint;
    private Timestamp requested_at;
    
    public Logging() {
    }

    public Logging(int id, String description, String IP, String endpoint, Timestamp requested_at) {
        this.id = id;
        this.description = description;
        this.IP = IP;
        this.endpoint = endpoint;
        this.requested_at = requested_at;
    }
    
    // getter function of all attributes
    public int get_id() {
        return id;
    }
    public String get_description() {
        return description;
    }
    public String get_IP() {
        return IP;
    }
    public String get_endpoint() {
        return endpoint;
    }
    public Timestamp get_requested_at() {
        return requested_at;
    }


    // setter function of all attributes
    public void set_id(int id) {
        this.id = id;
    }
    public void set_description(String description) {
        this.description = description;
    }
    public void set_IP(String IP) {
        this.IP = IP;
    }
    public void set_endpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    public void set_requested_at(Timestamp requested_at) {
        this.requested_at = requested_at;
    }

}
