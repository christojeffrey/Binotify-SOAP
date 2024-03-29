package binotify.services;

import javax.jws.WebService;

import binotify.dao.LoggingImpl;
import binotify.dao.SubscriptionImpl;
import binotify.model.Respond;
import binotify.security.ApiKey;
import com.google.protobuf.Api;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
        ApiKey key;
        String IP = this.getReqIP();
        String uri = this.getReqURI();
        String this_method_name = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();

        // check API KEY
        try {
            key = new ApiKey(api_key);
            key.isValid(this_method_name);
        } catch (Exception e) {
            e.printStackTrace();
            return new Respond("error", e.getMessage());
        }

        StringBuilder desc = new StringBuilder();
        desc.append(key.getService());
        desc.append(": ");
        desc.append(this_method_name);

        // add logging
        LoggingImpl.create(IP,desc.toString(),uri);

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

    public Respond checkSubscription(String creator_id, String subscriber_id, String api_key) {
        ApiKey key;
        String IP = this.getReqIP();
        String uri = this.getReqURI();
        String this_method_name = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();


        // check API KEY
        try {
            key = new ApiKey(api_key);
            key.isValid(this_method_name);
        } catch (Exception e) {
            e.printStackTrace();
            return new Respond("error", e.getMessage());
        }

        StringBuilder desc = new StringBuilder();
        desc.append(key.getService());
        desc.append(": ");
        desc.append(this_method_name);

        // add logging
        LoggingImpl.create(IP,desc.toString(),uri);


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

    public Respond updateSubscription( String creator_id, String subscriber_id, String status, String api_key) {
        ApiKey key;
        String IP = this.getReqIP();
        String uri = this.getReqURI();
        String this_method_name = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();

        // check API KEY
        try {
            key = new ApiKey(api_key);
            key.isValid(this_method_name);
        } catch (Exception e) {
            e.printStackTrace();
            return new Respond("error", e.getMessage());
        }

        StringBuilder desc = new StringBuilder();
        desc.append(key.getService());
        desc.append(": ");
        desc.append(this_method_name);

        // add logging
        LoggingImpl.create(IP,desc.toString(),uri);


        // update the status of a subscription
        try {

            boolean isSuccess = SubscriptionImpl.update(creator_id, subscriber_id, status);
            if (!isSuccess) {
                return new Respond("error", "subscription does not exist");
            }

            // callback to update table subscription in binotify app
            try {
                URL callback_url = new URL("http://binotify_app_frontend:80/api/subscription/updateSubscription.php");
                HttpURLConnection con = (HttpURLConnection) callback_url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);

                // create and send the json
                String jsonBodyString = String.format("{\"creator_id\": \"%s\", \"subscriber_id\": \"%s\", \"status\": \"%s\"}", creator_id, subscriber_id, status);
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonBodyString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
                
                // read response from input stream
                try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("response hitting php: " + response.toString());
                }
                con.getResponseCode();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new Respond("updated");
        } catch (Exception e) {
            e.printStackTrace();
            return new Respond("error", e.getMessage());
        }
    }

    public Respond generateApiKey(String password, String csp, String validUntil, String service) {
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

        key = new ApiKey(valid_until_d, csp.split(","), service);

        return new Respond("token", key.getKey());
    }
    
    public List<Subscription> getAllSubscriptionRequests(String api_key) {
        ApiKey key;
        String IP = this.getReqIP();
        String uri = this.getReqURI();
        String this_method_name = new Object() {}
                .getClass()
                .getEnclosingMethod()
                .getName();

        // check API KEY
        try {
            key = new ApiKey(api_key);
            key.isValid(this_method_name);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Subscription>();
        }

        StringBuilder desc = new StringBuilder();
        desc.append(key.getService());
        desc.append(": ");
        desc.append(this_method_name);

        // add logging
        LoggingImpl.create(IP,desc.toString(),uri);
        
        try {
            List<Subscription> subscriptions = SubscriptionImpl.getAll();
            return subscriptions;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Subscription>();
        }
    }
}