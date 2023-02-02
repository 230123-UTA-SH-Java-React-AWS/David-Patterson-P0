package com.revature;
import com.sun.net.httpserver.*;
import com.sun.net.httpserver.HttpServer;
import com.revature.controllers.Controller;
import java.net.InetSocketAddress;


/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws Exception {
        //HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        //System.out.println("Hello World!");
        //employee emp1 = new employee("emp3", "pass3");
        //Services serv = new Services();

        //serv.repo.saveToDatabase(emp1);

        //ConnectionUtil.getConnection();
        System.out.println("Starting backend server...");

        //HttpServer server;
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/someURL",  (HttpHandler) new Controller());
        //server.createContext("/otherURL", (HttpHandler) new AnotherController());


        server.setExecutor(null);
        server.start();
    }    
}
