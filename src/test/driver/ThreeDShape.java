package test.driver;

public abstract class ThreeDShape extends Shape {

    public ThreeDShape(String color) {
        super(color);
    }

    public ThreeDShape() {
        super();
    }

    public abstract double getVolume();
}