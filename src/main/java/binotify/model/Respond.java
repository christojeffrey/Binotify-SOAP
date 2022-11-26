package binotify.model;


public class Respond {
    // attribute status
    private String status;
    // attribute message
    private String message;
    // constructor
    public Respond() {
        this.status = "OK";
    }
    public Respond(String status) {
        this.status = status;
    }
    public Respond(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // getter
    public String getStatus() {
        return this.status;
    }
    public String getMessage() {
        return this.message;
    }
    // setter
    public void setStatus(String status) {
        this.status = status;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
