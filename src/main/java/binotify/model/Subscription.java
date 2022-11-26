package binotify.model;

// repsentation of the table in the database
public class Subscription {
    // creator_id, subscriber_id, status
    private String creator_id;
    private String subscriber_id;
    private String status;

    public Subscription() {
    }

    public Subscription(String creator_id, String subscriber_id, String status) {
        this.creator_id = creator_id;
        this.subscriber_id = subscriber_id;
        this.status = status;
    }

    // getter function of all attributes
    public String get_creator_id() {
        return creator_id;
    }
    public String get_subscriber_id() {
        return subscriber_id;
    }
    public String get_status() {
        return status;
    }

    // setter function of all attributes
    public void set_creator_id(String creator_id) {
        this.creator_id = creator_id;
    }
    public void set_subscriber_id(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }
    public void set_status(String status) {
        this.status = status;
    }
}
