package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;

public class Controller extends HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String httpVerb = exchange.getRequestMethod();

        switch (httpVerb) {
            case "GET":
                //getRequest(exchange);
                break;
            case "PUT":
                //putRequest(exchange);
                break;
            case "POST":
                postRequest(exchange);
                break;
            default:
                //You can add implementation details if the user access a http verb not supported at this url
                String someResponse = "HTTP Verb not supported";

                //exchange.sendResponseHeaders(404, someResponse.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(someResponse.getBytes());
                os.close();
                break;
        }
    }
    //Get requests specifically from /someUrl
    public void getRequest(HttpExchange exchange) throws IOException
    {
        String someResponse = "You selected the get response!";

        //exchange.sendResponseHeaders(200, someResponse.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(someResponse.getBytes());
        os.close();
    }

    public void putRequest(HttpExchange exchange) throws IOException
    {
        String someResponse = "You selected the put response!";

        //exchange.sendResponseHeaders(200, someResponse.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(someResponse.getBytes());
        os.close();
    }

    //for registering new accounts
    private void postRequest(HttpExchange exchange) throws IOException{

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
    
        //exchange.sendResponseHeaders()
        OutputStream os = exchange.getResponseBody();
        os.write(textBuilder.toString().getBytes());
        os.close();
    }
}