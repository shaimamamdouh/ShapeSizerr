package test.driver;



public class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        super();
        this.radius = radius;
    }

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return Math.PI * 2 * radius;
    }

    @Override
    public String howToDraw() {
        return "Draw a circle with radius " + radius;
    }

    @Override
    public String toString() {
        return "Circle with radius " + radius + "\nArea: " + getArea() + "\nPerimeter: " + getPerimeter();
    }

    

   
   

   

   
}