package com.revature.model;

public class employee {
    public String login;
    public String password;
    private boolean managerStatus;


    public employee(){
        this.managerStatus = false;
    }
    public employee(String login, String password){
        this.login = login;
        this.password = password;
        this.managerStatus = false;
    }
    public employee(String login, String password, boolean managerStatus){
        this.login = login;
        this.password = password;
        this.managerStatus = managerStatus;
    }

    @Override
    public String toString() {
        return "employee [login=" + login + ", password=" + password + ", managerStatus=" + managerStatus + "]";
    }

    public String getLogin(){
        return login;
    }
    public void setLogin(String login){
        this.login = login;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String isManagerString(){
        if(managerStatus){
            return "true";
        } else {
            return "false";
        }
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
