package library.libraryapp.Translogs;

import java.util.Date;

public class Translogs {
    private int transactionID;
    private int itemID;
    private int userID;
    private Date checkoutdate;
    private Date duedate;
    private Date checkindate;
    private String status;
    public Translogs(){}
    public Translogs(int transactionID, int itemID, int userID, Date checkoutdate, Date duedate, Date checkindate, String status){
        this.transactionID = transactionID;
        this.itemID = itemID;
        this.userID = userID;
        this.checkoutdate = checkoutdate;
        this.duedate = duedate;
        this.checkindate = checkindate;
        this.status = status;
    }
    public int getTransactionID(){
        return transactionID;
    }
    public void setTransactionID(int transactionID){
        this.transactionID = transactionID;
    }
    public int getItemID(){
        return itemID;
    }
    public void setItemID(int itemID){
        this.itemID = itemID;
    }
    public int getUserID(){
        return userID;
    }
    public void setUserID(int userID){
        this.userID = userID;
    }
    public Date getCheckoutdate(){
        return checkoutdate;
    }
    public void setCheckoutdate(Date checkoutdate){
        this.checkoutdate = checkoutdate;
    }
    public Date getDuedate() {
        return duedate;
    }
    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public Date getCheckindate() {
        return checkindate;
    }
    public void setCheckindate(Date checkindate) {
        this.checkindate = checkindate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
