package library.libraryapp.model;

import java.time.LocalDateTime;

public class AdminInventoryItem {
    private int itemId;
    private String type;
    private String model;
    private String status;
    private String location;
    private String company;
    private LocalDateTime acquisitionDate;
    private int stock;
    private String description;

    public AdminInventoryItem() {}

    public AdminInventoryItem(int itemId, String type, String model, String status, String location, String company, LocalDateTime acquisitionDate, int stock, String description) {
        this.itemId = itemId;
        this.type = type;
        this.model = model;
        this.status = status;
        this.location = location;
        this.company = company;
        this.acquisitionDate = acquisitionDate;
        this.stock = stock;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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

}