package test.driver;

import java.util.Date;

public abstract class Shape implements Drwable {

    private Date dateCreated;
    private String color;

    public Shape(String color) {
        this.color = color;
        this.dateCreated = new Date();
    }

    public Shape() {
        this.color = "White";
        this.dateCreated = new Date();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public abstract double getArea();
    public abstract double getPerimeter();

    @Override
    public String toString() {
        return "Shape created on " + dateCreated + " with color " + color;
    }
    
}