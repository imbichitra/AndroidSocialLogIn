package com.bichi.storeimageindb;

public class Food {
    private int id;
    private String name;
    private byte[] image;

    public Food(int id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Food(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
