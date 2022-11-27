package binotify.services;

import java.util.List;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

import binotify.dao.LoggingImpl;
import binotify.dao.SubscriptionImpl;
import binotify.model.Respond;
import binotify.model.RespondData;
import binotify.model.Subscription;

import java.net.InetSocketAddress;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
            SubscriptionImpl.create(subscriber_id, creator_id);
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
            String status = SubscriptionImpl.get(creator_id, subscriber_id).get_status();
            return new Respond(status);
        } catch (Exception e) {
            e.printStackTrace();
            return new Respond("error", e.getMessage());
        }
    }

    public Respond updateSubscription( String creator_id, String subscriber_id, String status) {
        // todo: add logging

        // update the status of a subscription
        try {
            SubscriptionImpl.update(creator_id, subscriber_id, status);
            return new Respond("updated");
        } catch (Exception e) {
            e.printStackTrace();
            return new Respond("error", e.getMessage());
        }
    }
    
    public List<Subscription> getAllSubscriptionRequests() {
        // todo: add logging

        // update the status of a subscription
        
        try {
            List<Subscription> subscriptions = SubscriptionImpl.getAll();
            return subscriptions;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Subscription>();
        }
    }
}