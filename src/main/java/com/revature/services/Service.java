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
        return result; 




    }

    //aka register a new employee account
    public void insertIntoEmpDatabase(String s){
        ObjectMapper mapper = new ObjectMapper();
        employee emp;
        try {
            emp = mapper.readValue(s, employee.class);
            repo.saveToDatabase(emp);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }

    public List<employee> getAllEmpolyees(){
        return repo.getAllEmployees();
    }


    //ticket services

    public List<Ticket> getAllPendingTickets(){
        return repo.getAllPendingTickets();

    }
    public List<Ticket> getAllUserTickets(employee emp){
        return repo.getAllUserTickets(emp);

    }

    
}
