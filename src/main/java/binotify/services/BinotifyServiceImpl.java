package binotify.services;

import javax.jws.WebService;

import binotify.dao.LoggingImpl;
import binotify.dao.SubscriptionImpl;
import binotify.model.Respond;
import binotify.security.ApiKey;
import com.sun.net.httpserver.HttpExchange;
import binotify.model.Subscription;

import java.net.InetSocketAddress;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService(endpointInterface = "binotify.services.BinotifyService")
public class BinotifyServiceImpl implements BinotifyService {

    @Resource
    WebServiceContext wsContext;
    
    // not a web method. used to get the IP address of the clients
    public String getReqIP(){
        MessageContext mc = wsContext.getMessageContext();
        HttpExchange exchange = (HttpExchange)mc.get("com.sun.xml.ws.http.exchange");
        InetSocketAddress remoteAddress = exchange.getRemoteAddress();
        String remoteHost = remoteAddress.getAddress().toString().split("/")[1];
        return remoteHost;
    }

    public String getReqURI(){
        MessageContext mc = wsContext.getMessageContext();
        HttpExchange exchange = (HttpExchange)mc.get("com.sun.xml.ws.http.exchange");
        String uri = String.valueOf(exchange.getRequestURI());
        return uri;
    }



    public Respond helloWorld() {
        return new Respond("Hello World");
    }

    public String helloFriend(String name) {
        return "Hello " + name + "!"; 
    }

    public Respond newSubscription(String creator_id, String subscriber_id, String api_key) {
        // add logging
        String IP = this.getReqIP();
        String uri = this.getReqURI();
        String this_method_name = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();
        LoggingImpl.create(IP,this_method_name,uri);

        // create a new subscription
        try {
            ApiKey key = new ApiKey(api_key);
            key.isValid(this_method_name);

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

    public Respond checkSubscription(String creator_id, String subscriber_id, String api_key) {
        // add logging
        String IP = this.getReqIP();
        String uri = this.getReqURI();
        String this_method_name = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();
        LoggingImpl.create(IP,this_method_name,uri);


        // get the status of a subscription
        try {
            ApiKey key = new ApiKey(api_key);
            key.isValid(this_method_name);

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

    public Respond updateSubscription( String creator_id, String subscriber_id, String status, String api_key) {
        // do logging
        String IP = this.getReqIP();
        String uri = this.getReqURI();
        String this_method_name = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();
        LoggingImpl.create(IP,this_method_name,uri);


        // update the status of a subscription
        try {
            ApiKey key = new ApiKey(api_key);
            key.isValid(this_method_name);

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

    public Respond generateApiKey(String password, String csp, String validUntil) {
        // add logging
        String IP = this.getReqIP();
        String uri = this.getReqURI();
        String this_method_name = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();
        LoggingImpl.create(IP,this_method_name,uri);
        ApiKey key;
        Date valid_until_d;

        if (!ApiKey.checkPassword(password)){
            return new Respond("error", "Password incorrect! Not allowed to generate API key.");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            valid_until_d = formatter.parse(validUntil);
        } catch (Exception e){
            return new Respond("error", "Date format must be dd/mm/yyyy");
        }

        key = new ApiKey(valid_until_d, csp.split(","));

        return new Respond("token", key.getKey());
    }
    
    public List<Subscription> getAllSubscriptionRequests(String api_key) {
        // add logging
        String IP = this.getReqIP();
        String uri = this.getReqURI();
        String this_method_name = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();
        LoggingImpl.create(IP,this_method_name,uri);
        
        try {
            ApiKey key = new ApiKey(api_key);
            key.isValid(this_method_name);

            List<Subscription> subscriptions = SubscriptionImpl.getAll();
            return subscriptions;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Subscription>();
        }
    }
}