package com.revature.controllers;
import com.revature.services.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class EmployeeTicketController implements HttpHandler{
    private Service serv;

    public EmployeeTicketController(){
        this.serv = null;
    }
    public EmployeeTicketController(Service serv){
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
            case "POST":
                postRequest(exchange);
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
    //view previous tickets
    //should be able to filter by pending/approved/denied 
    private void getRequest(HttpExchange exchange) throws IOException{
        String status = exchange.getRequestHeaders().get("input").get(0);
        String someResponse = "You selected the get response!";
        //System.out.println(status);

        if(this.serv.loggedIn){

            someResponse = serv.getAllUserTickets(status);

        } else {
            someResponse = "please login to perform this action";
        }

        exchange.sendResponseHeaders(200, someResponse.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(someResponse.getBytes());
        os.close();
    }
    //submit new ticket
    private void postRequest(HttpExchange exchange) throws IOException{
        System.out.println("sending new Ticket");

        InputStream IS = exchange.getRequestBody();
        StringBuilder textBuilder = new StringBuilder();
        //ASCII
        //converts our binary to letters
        //try_resource block will automattically close the resource within the parans when done
        try (Reader reader = new BufferedReader(new InputStreamReader(IS,Charset.forName(StandardCharsets.UTF_8.name()))))
        {
            int c = 0;
            while ((c = reader.read()) != -1){
                textBuilder.append((char)c);
            }
        }
        exchange.sendResponseHeaders(200, textBuilder.toString().getBytes().length);

        serv.SubmitNewTicket(textBuilder.toString());
        System.out.println("Ticket submited Sucessfully.");

        OutputStream os = exchange.getResponseBody();
        os.write(textBuilder.toString().getBytes());
        os.close();
    }

}
