package com.example.shoppinglist;

public class Item {
    private int itemID;
    private String category;
    private String name;
    private String description;
    //private String categoryImgSrc;
    private float price;
    private boolean purchased;

    public Item( ) {
        itemID = -1;
        category = "None";
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int i) {
        itemID = i;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

/*    public String getCategoryImgSrc() {
        return categoryImgSrc;
    }

    public void setCategoryImgSrc(String imgsrc) {
        this.categoryImgSrc = imgsrc;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }


}
