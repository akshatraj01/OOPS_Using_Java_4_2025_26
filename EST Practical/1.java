class Shape {
    void area() {
        System.out.println("Area method in Shape class");
    }
}

class Circle extends Shape {
    double radius;

    Circle(double r) {
        radius = r;
    }

    void area() {
        double area = Math.PI * radius * radius;
        System.out.println("Area of Circle: " + area);
    }
}

class Rectangle extends Shape {
    double length, width;

    Rectangle(double l, double w) {
        length = l;
        width = w;
    }

    void area() {
        double area = length * width;
        System.out.println("Area of Rectangle: " + area);
    }
}
public class MethodOverridingDemo {
    public static void main(String[] args) {
        Shape s;

        s = new Circle(5);
        s.area();

        s = new Rectangle(7, 9);
        s.area();
    }
}
