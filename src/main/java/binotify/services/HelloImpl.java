package binotify.services;


import javax.annotation.Resource;
import javax.jws.*;
import javax.xml.ws.WebServiceContext;

@WebService(endpointInterface = "binotify.services.Hello")
public class HelloImpl implements Hello{

    @Resource
    WebServiceContext context;


    @WebMethod
    public String helloWorld() {
        return "Hello World!";
    }
}
