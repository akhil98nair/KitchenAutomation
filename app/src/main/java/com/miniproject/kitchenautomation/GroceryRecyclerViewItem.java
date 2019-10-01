package com.miniproject.kitchenautomation;

public class GroceryRecyclerViewItem {

    // Save car name.
    private String carName;
    private String kg;

    // Save car image resource id.
    private int carImageId;

    public GroceryRecyclerViewItem(String carName, String kg, int carImageId) {
        this.carName = carName;
        this.kg = kg;
        this.carImageId = carImageId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }

    public int getCarImageId() {
        return carImageId;
    }

    public void setCarImageId(int carImageId) {
        this.carImageId = carImageId;
    }
}