package com.revature.repository;
import com.revature.utils.*;
import com.revature.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Repository {
    public void saveToDatabase(employee emp){
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

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public employee login(String name, String password){
        String sql = "select * from users where username = ? and pass = ?";

        try (Connection con = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = con.prepareStatement(sql);

            prstmt.setString(1, name);
            prstmt.setString(2, password);
            //this one will expect a responce back
            ResultSet result = prstmt.executeQuery();
                
            employee emp = new employee(
                                            result.getString(1), 
                                            result.getString(2),
                                            result.getBoolean(3)
                                           );
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
    public List<Ticket> getAllPendingTickets(){
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

                listofPendingTicks.add(tick);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return listofPendingTicks;
    }



    public List<Ticket> getAllUserTickets(employee emp){
        String sql = "select * from Tickets where username = ?";

        List<Ticket> listofPendingTicks = new ArrayList<Ticket>();

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

                listofPendingTicks.add(tick);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return listofPendingTicks;
    }
    
}
