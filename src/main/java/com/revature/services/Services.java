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
public class Services {
    public Repository repo = new Repository();

    public employee CurrentUser;

    //employee services

    public void login(String s){
        //figure out how to get these inputs from the controller
        String username = "";
        String password = "";

        this.CurrentUser = repo.login(username, password);
        if (this.CurrentUser == null){
            System.out.println("login failed, username or password may be wrong");
        }


    }

    //aka register a new employee account
    public void insertIntoEmpDatabase(String s){
        ObjectMapper mapper = new ObjectMapper();
        employee emp;
        try {
            emp = mapper.readValue(s, employee.class);
            repo.saveToDatabase(emp);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
