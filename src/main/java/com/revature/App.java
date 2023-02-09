package com.revature;
import com.revature.services.*;
import com.sun.net.httpserver.*;
import com.sun.net.httpserver.HttpServer;
import com.revature.controllers.*;
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
        Service serv = new Service();

        //serv.repo.saveToDatabase(emp1);

        //ConnectionUtil.getConnection();
        System.out.println("Starting backend server...");

        //HttpServer server;
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        //serv used to retain logged user
        server.createContext("/Login",  (HttpHandler) new LoginController(serv));
        server.createContext("/MyTickets", (HttpHandler) new EmployeeTicketController(serv));
        server.createContext("/TicketProcessing", (HttpHandler) new ManagerTicketController(serv));
        server.createContext("/AllTicketProcessing", (HttpHandler) new AllPendingTicketController(serv));
        server.createContext("/Logout",  (HttpHandler) new LogoutController(serv));


        server.setExecutor(null);
        server.start();
    }    
}
