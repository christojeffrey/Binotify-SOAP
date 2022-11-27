package binotify.services;
import java.util.List;

import javax.jws.WebService;

import binotify.model.Respond;
import binotify.model.RespondData;
import binotify.model.Subscription;

@WebService
public interface BinotifyService {

    // return respond example
    public Respond helloWorld();
    // with argument example
    public String helloFriend(String name);

    public Respond newSubscription(String creator_id, String subscriber_id, String api_key);

    public Respond checkSubscription( String creator_id, String subscriber_id, String api_key);

    public List<Subscription> getAllSubscriptionRequests(String api_key);

    public Respond updateSubscription( String creator_id, String subscriber_id, String status, String api_key);

    public Respond generateApiKey(String password, String csp, String validUntil);
}
