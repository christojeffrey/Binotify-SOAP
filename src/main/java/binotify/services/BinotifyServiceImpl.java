package binotify.services;

import javax.jws.WebService;

import binotify.dao.SubscriptionImpl;
import binotify.model.Respond;
import binotify.model.Subscription;

import java.net.InetSocketAddress;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.spi.http.HttpExchange;

@WebService(endpointInterface = "binotify.services.BinotifyService")
public class BinotifyServiceImpl implements BinotifyService {

    @Resource WebServiceContext context;
    
    // not a web method. used to get the IP address of the clients
    public String getInfo(){
        // still not working. need to search more on the internet. target: get the client's IP
        MessageContext mc = context.getMessageContext();
        HttpExchange exchange = (HttpExchange)mc.get("com.sun.xml.ws.http.exchange");
        InetSocketAddress remoteAddress = exchange.getRemoteAddress();
        String remoteHost = remoteAddress.getHostName();
        return "Your IP address is: " + remoteHost + " and your port is: " + remoteAddress.getPort();
    }

    public Respond helloWorld() {
        return new Respond("Hello World");
    }

    public String helloFriend(String name) {
        return "Hello " + name + "!"; 
    }

    public Respond newSubscription(String creator_id, String subscriber_id) {
        // todo: add logging

        // create a new subscription
        try {
            boolean isSuccess = SubscriptionImpl.create(subscriber_id, creator_id);
            if (!isSuccess) {
                return new Respond("error", "subscription already exists");
            }
            return new Respond("created");
        } catch (Exception e) {
            e.printStackTrace();
            return new Respond("error", e.getMessage());
        }
    }

    public Respond checkSubscription( String creator_id, String subscriber_id) {
        // todo: add logging

        // get the status of a subscription
        try {
           Subscription subs = SubscriptionImpl.get(creator_id, subscriber_id);
            // check if null
            if (subs == null) {
                return new Respond("error");
            }
            return new Respond(subs.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new Respond("error", e.getMessage());
        }
    }

    public Respond updateSubscription( String creator_id, String subscriber_id, String status) {
        // todo: add logging

        // update the status of a subscription
        try {
            boolean isSuccess = SubscriptionImpl.update(creator_id, subscriber_id, status);
            if (!isSuccess) {
                return new Respond("error", "subscription does not exist");
            }
            return new Respond("updated");
        } catch (Exception e) {
            e.printStackTrace();
            return new Respond("error", e.getMessage());
        }
    }
    
}
