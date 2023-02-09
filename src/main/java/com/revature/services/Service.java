package com.revature.services;
import java.io.IOException;
import java.util.*;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

//import com.revature.controllers.*;
import com.revature.model.*;
import com.revature.repository.*;
//import com.revature.services.*;
//import com.revature.utils.*;

//controller gives strings to services
public class Service {
    public Repository repo = new Repository();

    public employee CurrentUser;
    public boolean loggedIn = false;

    //employee services

    public boolean login(String s){
        //figure out how to get these inputs from the controller. in the meantime it wil be done as a json string
        //String username = "";
        //String password = "";


        //unsure if its necesary to map it as a json object instead of just making s a username/password, but im not certain how I would do it otherwise
        ObjectMapper mapper = new ObjectMapper();
        employee emp;
        boolean result = false;
        try {
            emp = mapper.readValue(s, employee.class);
            this.CurrentUser = repo.login(emp.getLogin(),emp.getPassword());
            if (this.CurrentUser == null){
                System.out.println("login failed, username or password may be wrong");
                result = false;
            } else {
                result = true;
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(result){
            this.loggedIn = true;
        }
        return result; 




    }

    //aka register a new employee account
    public boolean insertIntoEmpDatabase(String s) {
        ObjectMapper mapper = new ObjectMapper();
        employee emp;
        boolean success = false;
        try {
            emp = mapper.readValue(s, employee.class);
            success = repo.saveToDatabase(emp);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;   
    }

    //needs to return a string if It ever goes to the controller
    public List<employee> getAllEmpolyees(){
        return repo.getAllEmployees();
    }


    //ticket services
    //processing
    //do we ever need ALL of them? 
    //needs to return a string if It ever goes to the controller
    public String getAllPendingTickets(){
        if(this.loggedIn){
            if(this.CurrentUser.isManager()){
                ObjectMapper mapper = new ObjectMapper();
                String jsonString;
                try {
                    jsonString = mapper.writeValueAsString(repo.getAllPendingTickets());
                    return jsonString;
                } catch (JsonParseException e) {
                    e.printStackTrace();
                    return null;
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                
            } else {
                System.out.println("must be a manager to perform this action");
                return null;
            }
        } else {
            System.out.println("must log in to perform this action");
            return null;
        }

    }
    //just the top priority one

    public String getNextPendingTicket(){
        if(this.loggedIn){
            if(this.CurrentUser.isManager()){
                ObjectMapper mapper = new ObjectMapper();
                String jsonString;
                try {
                    jsonString = mapper.writeValueAsString(repo.getNextPendingTicket());
                    return jsonString;
                } catch (JsonParseException e) {
                    e.printStackTrace();
                    return null;
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                System.out.println("must be a manager to perform this action");
                return "must be a manager to perform this action";
            }
        } else {
            System.out.println("must log in to perform this action");
            return "must log in to perform this action";
        }

    }
    public boolean ProccessNextPendingTicket(String status){

        return (repo.ProccessNextPendingTicket(status));

    }



    //user tickets
    public void SubmitNewTicket(String s){
        if(this.loggedIn){
            ObjectMapper mapper = new ObjectMapper();
            Ticket NewTick;
            try {
                NewTick = mapper.readValue(s, Ticket.class);
                repo.InsertNewTicket(NewTick, this.CurrentUser.login);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }        
        }
    }
    public String getAllUserTickets(String statusFilter){
        if(this.loggedIn){
            ObjectMapper mapper = new ObjectMapper();
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(repo.getAllUserTickets(this.CurrentUser, statusFilter));
                return jsonString;
            } catch (JsonParseException e) {
                e.printStackTrace();
                return null;
            } catch (JsonMappingException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }    

        } else {
            System.out.println("must log in to perform this action");
            return "must log in to perform this action";
        }

    }
}
