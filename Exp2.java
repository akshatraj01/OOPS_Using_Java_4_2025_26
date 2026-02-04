public class AreaCalculation{

    void area(int side){
        int result = side*side;
        System.out.println("Area of Square:"+result);
    }

    void area(int length, int breadth){
        int result = length*breadth;
        System.out.println("Area of Rectangle:"+result);
    }

    void area(double radius){
        double result = 3.14*radius*radius;
        System.out.println("Area of Circle:"+result);
    }
    public static void main(String[] args){
        AreaCalculation obj = new AreaCalculation();

        obj.area(10);        
        obj.area(12,9);   
        obj.area(7.0);         
    }
} 
