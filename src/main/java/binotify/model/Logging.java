package binotify.model;

public class Logging {
    // id, description, IP, endpoint, requested_at
    private int id;
    private String description;
    private String IP;
    private String endpoint;
    private String requested_at;
    
    public Logging(int id, String description, String IP, String endpoint, String requested_at) {
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
    public String get_requested_at() {
        return requested_at;
    }

}
