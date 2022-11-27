package binotify.dao;

import java.util.List;
import binotify.model.Subscription;

public interface SubscriptionInterface {
    public Subscription get(String creator_id, String subscriber_id);
    public List<Subscription> getAll();
    public void create(String creator_id, String subscriber_id);
    public void update(String creator_id, String subscriber_id, String status);
    public void delete(String creator_id, String subscriber_id);
}