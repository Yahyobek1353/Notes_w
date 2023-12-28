package com.geeks.notes;

import java.io.Serializable;

public class Note implements Serializable {
    private String title;
    private String description;
    private String date;
    private int image;

    public Note() {

    }
    public Note(String title, String description, String date, int image) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getImage() {
        return image;
    }
}
