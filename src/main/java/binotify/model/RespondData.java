package binotify.model;
import java.util.List;

// still not used due to errors

public class RespondData<T> extends Respond{
    private List<T> data;

    public RespondData(String status, String message) {
        super(status, message);
    }
    
    public RespondData(List<T> data) {
        super();
        this.data = data;
    }

    // getter
    public List<T> getData() {
        return this.data;
    }

    // setter
    public void setData(List<T> data) {
        this.data = data;
    }
}
