package com.revature.repository;
import com.revature.utils.*;
import com.revature.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import java.util.PriorityQueue;
import java.util.LinkedList;


public class Repository {
    public LinkedList<Ticket> PendingTickets = new LinkedList<Ticket>();
    public boolean ticketsuptodate = true; //used to track if a new ticket has been submitted since the last time the collections above were updated from the database.
    public List<employee> AllEmps = new ArrayList<employee>();
    public List<Ticket> UserTickets = new ArrayList<Ticket>();
    public boolean saveToDatabase(employee emp){
        AllEmps = getAllEmployees();
        if(AllEmps.contains(emp)){
            return false;
        }

        String sql = "insert into users (username, pass, manager) values(?, ?, ?)";

        //JDBC API

        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);

            prstmt.setString(1, emp.getLogin());
            prstmt.setString(2, emp.getPassword());
            prstmt.setBoolean(3, emp.isManager());

            //this one will expect a responce back
            //prstmt.executeQuery();
            prstmt.execute();

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public employee login(String name, String password){
        String sql = "select * from users where username = ? and pass = ?";

        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);

            prstmt.setString(1, name);
            prstmt.setString(2, password);
            //this one will expect a responce back
            ResultSet result = prstmt.executeQuery();
            employee emp = null;
            if(result.next()){
                emp = new employee(
                                            result.getString(1), 
                                            result.getString(2),
                                            result.getBoolean(3)
                                           );
            }
            return emp;

        } catch (Exception e){
            e.printStackTrace();
            //username or password is incorrect
            return null;
        }

    }
    public List<employee> getAllEmployees(){
        String sql = "select * from users";

        List<employee> listofemp = new ArrayList<employee>();

        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);
            //this one will expect a responce back
            ResultSet result = prstmt.executeQuery();

            while(result.next()){
                employee employ = new employee(
                                                result.getString(1), 
                                                result.getString(2),
                                                result.getBoolean(3)
                                                );

                listofemp.add(employ);
            }

        } catch (Exception e){
            e.printStackTrace();
            //return null;
        }
        return listofemp;
    }


    //ticket repository methods
    //keeping this one to return a list instead of a queue
    public List<Ticket> getAllPendingTickets(){
        this.PendingTickets.clear();
        String sql = "select * from Tickets where pending = true";

        List<Ticket> listofPendingTicks = new ArrayList<Ticket>();

        try (Connection con = ConnectionUtil.getConnection()) {

            PreparedStatement prstmt = con.prepareStatement(sql);

            //this one will expect a responce back
            ResultSet result = prstmt.executeQuery();

            while(result.next()){
                Ticket tick = new Ticket(
                                        result.getInt(1),
                                        result.getString(2),
                                        result.getInt(3), 
                                        result.getString(4), 
                                        result.getBoolean(5), 
                                        result.getBoolean(6)
                                        );
                this.PendingTickets.add(tick);
                listofPendingTicks.add(tick);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        this.ticketsuptodate = true;
        return listofPendingTicks;
    }
    
    public Ticket getNextPendingTicket(){
        if(this.PendingTickets.isEmpty() || (!ticketsuptodate)){
            //fills the queue if it's empty or unupdated
            this.getAllPendingTickets();
        }
        //if still empty, peek will return null
        return this.PendingTickets.peek();
    }
    //this one is currently unused
    public LinkedList<Ticket> getPendingTicketQueue(){
        if(this.PendingTickets.isEmpty() || (!ticketsuptodate)){
            //fills the queue if it's empty or unupdated
            this.getAllPendingTickets();
        }
        return this.PendingTickets;
    }

    public boolean ProccessNextPendingTicket(String status){

        Ticket tick = this.PendingTickets.remove();
        String sql = "update Tickets";
        
        switch (status){
            case "APPROVE":
                sql += " set pending = false, approval = true"; //approved tickets cannot possibly be pending
                break;
            case "DENY":
                sql += " set pending = false, approval = false";
                break;
            default:
                return false;//update failed
        }
        sql += " where ticketID = ?";
        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);

            prstmt.setInt(1, tick.getID());
            prstmt.execute();
            this.ticketsuptodate = false;

            tick.approveTicket();//only chages data within this ticket, effectivly does nothing right now

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    

    public void InsertNewTicket(Ticket NewTick, String username){
        String sql = "insert into tickets (username, amount, description) values(?, ?, ?)";

        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);

            prstmt.setString(1, username);
            prstmt.setInt(2, NewTick.getAmount());
            prstmt.setString(3, NewTick.getDescription());
            prstmt.execute();
            this.ticketsuptodate = false;

        } catch (Exception e){
            e.printStackTrace();
        }
    }

/*     public List<Ticket> getAllUserTickets(employee emp, String filter){
        if(this.UserTickets.isEmpty() || (!ticketsuptodate)){
            //fills the queue if it's empty or unupdated
            this.FillAllUserTickets(emp, filter);
        }
        //if still empty, peek will return null
        return this.UserTickets;
    }
*/
    public List<Ticket> getAllUserTickets(employee emp, String filter){
        this.UserTickets.clear();
        String sql = "select * from Tickets where username = ?";

        switch (filter){
            case "PENDING":
                sql += " and pending = true";
                break;
            case "APPROVED":
                sql += " and approval = true"; //approved tickets cannot possibly be pending
                break;
            case "DENIED":
                sql += " and pending = false and approval = false";
                break;
            default:
        }
        try (Connection con = ConnectionUtil.getConnection()) {

            PreparedStatement prstmt = con.prepareStatement(sql);

            prstmt.setString(1, emp.getLogin());

            //this one will expect a responce back
            ResultSet result = prstmt.executeQuery();

            while(result.next()){
                Ticket tick = new Ticket(
                                        result.getInt(1),
                                        result.getString(2),
                                        result.getInt(3), 
                                        result.getString(4), 
                                        result.getBoolean(5), 
                                        result.getBoolean(6)
                                        );
                this.UserTickets.add(tick);

                //listofTicks.add(tick);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return this.UserTickets;
    }
    
}
