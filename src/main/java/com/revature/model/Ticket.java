package com.revature.model;

public class Ticket {
    private boolean pending; //ture by default
    private boolean approvalStatus; //true = approved, false = denied
    private int amount;
    private String description;



    public Ticket(int amt, String desc){
        amount = amt;
        description= desc;
        pending = true;
    }
    //protected? employees shouldn't be able to use these 2
    public void approveTicket(){
        if(pending){
            approvalStatus = true;
            System.out.println("ticket approved");
        } else {
            System.out.println("ticket cannot be changed after processing");
            return;
        }
        pending = false;
    }
    public void denyTicket(){
        if(pending){
            approvalStatus = false;
            System.out.println("ticket denied");
        } else {
            System.out.println("ticket cannot be changed after processing");
            return;
        }
        pending = false;
    }
    //

    public String getStatus(){
        if(pending){
            return "pending";
        } else {
            if(approvalStatus){
                return "approved";
            } else {
                return "denied";
            }
        }
    }
    public int getAmount(){
        return amount;
    } 
    public String getDescription(){
        return description;
    }
}
