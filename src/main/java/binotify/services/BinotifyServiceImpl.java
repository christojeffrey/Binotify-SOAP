package binotify.services;

import javax.jws.WebService;

import binotify.dao.LoggingImpl;
import binotify.dao.SubscriptionImpl;
import binotify.model.Respond;
import com.sun.net.httpserver.HttpExchange;

import java.net.InetSocketAddress;
import java.net.URI;

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
        String remoteHost = remoteAddress.getHostName();
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

    public Respond newSubscription(String creator_id, String subscriber_id) {
        // todo: add logging
        String IP = this.getReqIP();
        String uri = this.getReqURI();
        String description = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();
        LoggingImpl.create(IP,description,uri);



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
        String IP = this.getReqIP();
        String uri = this.getReqURI();
        String description = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();
        LoggingImpl.create(IP,description,uri);


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
        String IP = this.getReqIP();
        String uri = this.getReqURI();
        String description = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();
        LoggingImpl.create(IP,description,uri);


        // update the status of a subscription
        try {
            SubscriptionImpl.update(creator_id, subscriber_id, status);
            return new Respond("updated");
        } catch (Exception e) {
            e.printStackTrace();
            return new Respond("error", e.getMessage());
        }
    }
    
}
