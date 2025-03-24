package library.libraryapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class userTransaction {
    private int transactionId;
    private int itemId;
    private int userId;
    private LocalDateTime checkoutDate;
    private LocalDate dueDate;
    private LocalDate checkinDate;
    private String status;



    public userTransaction() {
    }

    public userTransaction(int itemId, int userId, String status) {
        this.itemId = itemId;
        this.userId = userId;
        this.status = status;
        this.checkoutDate = LocalDateTime.now();
        this.dueDate = LocalDate.now().plusDays(28);

    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDateTime checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(LocalDate checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
