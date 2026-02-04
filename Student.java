public class Student{
    int id;
    String name;
    float marks;

    Student(){
        id = 0;
        name = "None";
        marks = 0;
    }

    Student(int i,String n,float m){
        id = i;
        name = n;
        marks = m;
    }

    void displayDetails(){
        System.out.println("Student ID:"+id);
        System.out.println("Student Name:"+name);
        System.out.println("Student Marks:"+marks);
    }
    public static void main(String[] args){
        Student s1 = new Student();
        s1.displayDetails();

        Student s2 = new Student(101,"Rahul",85);
        s2.displayDetails();
    }
}
