package binotify.services;

import javax.jws.WebService;

import binotify.model.Respond;

@WebService
public interface BinotifyService {

    // return respond example
    public Respond helloWorld();
    // with argument example
    public String helloFriend(String name);

    public Respond newSubscription(String creator_id, String subscriber_id);

    public Respond checkSubscription( String creator_id, String subscriber_id);

    public Respond updateSubscription( String creator_id, String subscriber_id, String status);
}
