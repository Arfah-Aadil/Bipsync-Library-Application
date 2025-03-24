package library.libraryapp.model;

import java.time.LocalDateTime;

public class userInventoryItem {
    private int itemId;
    private String type;
    private String model;
    private String status;
    private String location;
    private String company;
    private LocalDateTime acquisitionDate;
    private int stock;
    private int onLoan;
    private boolean checkedOutByUser;
    private int transactionId;  // Tracks the associated transaction

    public userInventoryItem() {}

    public userInventoryItem(int itemId, String type, String model, String status, String location, String company, LocalDateTime acquisitionDate, int stock, int onLoan, boolean checkedOutByUser, int transactionId) {
        this.itemId = itemId;
        this.type = type;
        this.model = model;
        this.status = status;
        this.location = location;
        this.company = company;
        this.acquisitionDate = acquisitionDate;
        this.stock = stock;
        this.onLoan = onLoan;
        this.checkedOutByUser = checkedOutByUser;
        this.transactionId = transactionId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDateTime getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(LocalDateTime acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getOnLoan() {
        return onLoan;
    }

    public void setOnLoan(int onLoan) {
        this.onLoan = onLoan;
    }

    public boolean isCheckedOutByUser() {
        return checkedOutByUser;
    }

    public void setCheckedOutByUser(boolean checkedOutByUser) {
        this.checkedOutByUser = checkedOutByUser;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
}
