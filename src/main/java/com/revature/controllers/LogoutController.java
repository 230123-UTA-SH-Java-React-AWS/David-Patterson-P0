package com.revature.controllers;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import com.revature.services.Service;

public class LogoutController implements HttpHandler {
    private Service serv;

    public LogoutController(){
        this.serv = null;
    }
    public LogoutController(Service serv){
        this.serv = serv;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String httpVerb = exchange.getRequestMethod();

        //get = login
        //post = register
        //other actions should probably be done by other controllers(maybe figure out how to do that). Alternatively I could make the behaviors change after login, but that would only allow one more get/post action; I'll need more than that.)

        switch (httpVerb) {
            case "GET":
                getRequest(exchange);
                break;
            default:
                //You can add implementation details if the user access a http verb not supported at this url
                String someResponse = "HTTP Verb not supported";

                exchange.sendResponseHeaders(404, someResponse.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(someResponse.getBytes());
                os.close();
                break;
        }
    }
    private void getRequest(HttpExchange exchange) throws IOException
    {
        serv.loggedIn = false;
        serv.CurrentUser = null;
        String someResponse = "you are logged out";

        exchange.sendResponseHeaders(200, someResponse.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(someResponse.getBytes());
        os.close();
    }

    
}
