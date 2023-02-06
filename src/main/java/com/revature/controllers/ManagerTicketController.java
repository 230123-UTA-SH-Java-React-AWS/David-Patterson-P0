package com.revature.controllers;
import com.revature.services.Service;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
import java.io.OutputStream;
//import java.io.Reader;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ManagerTicketController implements HttpHandler{
    private Service serv;

    public ManagerTicketController(){
        this.serv = null;
    }
    public ManagerTicketController(Service serv){
        this.serv = serv;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String httpVerb = exchange.getRequestMethod();

        //get = look at tickets
        //post = make new ticket

        switch (httpVerb) {
            case "GET":
                getRequest(exchange);
                break;
            case "PUT":
                putRequest(exchange);
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


    //gets top pending ticket
    private void getRequest(HttpExchange exchange) throws IOException{
        String someResponse = "You selected the get response!";

        if(this.serv.loggedIn && this.serv.CurrentUser.isManager()){
            someResponse = "Next pending ticket: ";
            String next;
            next = serv.getNextPendingTicket();
            if (next.isEmpty()){
                someResponse = "no pending tickets to proccess";
            }
        } else {
            someResponse = "please login to manager account to perform this action";
        }

        exchange.sendResponseHeaders(200, someResponse.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(someResponse.getBytes());
        os.close();
    }
    //approve or deny top pending ticket
    private void putRequest(HttpExchange exchange) throws IOException
    {
        String status = exchange.getRequestHeaders().get("input").get(0);
        String someResponse = "You selected the put response!";

        if(this.serv.loggedIn && this.serv.CurrentUser.isManager()){
            someResponse = "Ticket Proccessed: ";
            String next;
            next = serv.getNextPendingTicket();
            someResponse += next;
            boolean success = serv.ProccessNextPendingTicket(status);
            if(!success){
                someResponse = "proccessing failed";
            } else if (next.isEmpty()){
                someResponse = "no pending tickets to proccess";
            }
        } else {
            someResponse = "please login to manager account to perform this action";
        }
        exchange.sendResponseHeaders(200, someResponse.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(someResponse.getBytes());
        os.close();
    }

}
