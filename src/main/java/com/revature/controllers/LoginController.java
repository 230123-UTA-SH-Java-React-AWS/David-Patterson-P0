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


public class LoginController implements HttpHandler{

    private Service serv;

    public LoginController(){
        this.serv = null;
    }
    public LoginController(Service serv){
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
            case "PUT":
                putRequest(exchange);
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
    //Get requests specifically from /someUrl
    //login
    private void getRequest(HttpExchange exchange) throws IOException
    {
        System.out.println("attemting to login...");
        String someResponse;
        boolean CredentialsCorrect = false;
        
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

        CredentialsCorrect = serv.login(textBuilder.toString());
        System.out.println("login atempt complete");

        if(CredentialsCorrect){
            someResponse = "login success.";
            

        } else {
            someResponse = "login failure, password or username was incorrect";
        }

        exchange.sendResponseHeaders(200, someResponse.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(someResponse.getBytes());
        os.close();
    }
    //does nothing, just here for my own reference
    private void putRequest(HttpExchange exchange) throws IOException
    {
        String someResponse = "You selected the put response!";

        exchange.sendResponseHeaders(200, someResponse.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(someResponse.getBytes());
        os.close();
    }
    //for registering new accounts
    private void postRequest(HttpExchange exchange) throws IOException{
        System.out.println("sending registration request...");

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
    
        //
        //String someResponse = "You selected the post response!";
        boolean success;
        success = serv.insertIntoEmpDatabase(textBuilder.toString());
        String response = textBuilder.toString();
        if(!success){
            response = "registration failed, username might be taken";
        }

    
        //exchange.sendResponseHeaders()
        exchange.sendResponseHeaders(200, response.getBytes().length);
        System.out.println("registration request sent.");


        OutputStream os = exchange.getResponseBody();
        os.write(textBuilder.toString().getBytes());
        os.close();
    
    }
}
