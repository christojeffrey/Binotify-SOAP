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

    // getter
    public String getCreatorId() {
        return creator_id;
    }
    public String getSubscriberId() {
        return subscriber_id;
    }
    public String getStatus() {
        return status;
    }

    // setter
    public void setCreatorId(String creator_id) {
        this.creator_id = creator_id;
    }
    public void setSubscriberId(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
