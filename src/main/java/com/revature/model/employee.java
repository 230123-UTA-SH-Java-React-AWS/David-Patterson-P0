package com.revature.model;

public class employee {
    public String login;
    public String password;
    public boolean managerStatus;

    public employee(String login, String password){
        this.login = login;
        this.password = password;
        this.managerStatus = false;
    }

    //should only be doable by other managers
    public void makeManager(){
        this.managerStatus = true;
        System.out.println("employee promoted to manager");
    }
    public void demoteManager(){
        this.managerStatus = false;
        System.out.println("manager demoted to employee");
    }
    //



    public boolean isManager(){
        if(managerStatus){
            return true;
        } else {
            return false;
        }
    }
}
