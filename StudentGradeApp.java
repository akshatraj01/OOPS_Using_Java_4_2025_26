import java.util.Scanner;
import java.util.InputMismatchException;

public class StudentGradeApp{

    static class Student{
        private String name;
        private int marks;

        public Student(String name,int marks) {
            if(marks<0||marks>100) {
                throw new IllegalArgumentException("Marks must be between 0 and 100.");
            }
            this.name =name;
            this.marks =marks;
        }

        public String getName(){
            return name;
        }

        public int getMarks(){
            return marks;
        }
    }

    static class GradeCalculator{

        public static String calculateGrade(int marks){
            if (marks>=90)
                return "A";
            else if (marks>=75)
                return "B";
            else if (marks>=60)
                return "C";
            else if (marks>=40)
                return "D";
            else
                return "Fail";
        }
    }
        public static void main(String[] args){

        Scanner sc =new Scanner(System.in);

        try{
            System.out.print("Enter Student Name:");
            String name = sc.nextLine();

            System.out.print("Enter Marks(out of 100):");
            int marks = sc.nextInt();

            Student student = new Student(name, marks);
            String grade = GradeCalculator.calculateGrade(student.getMarks());

            System.out.println("\n Result");
            System.out.println("Student Name:" +student.getName());
            System.out.println("Marks Entered:" +student.getMarks());
            System.out.println("Grade Obtained:" +grade);

        } catch(InputMismatchException e){
            System.out.println("Invalid input,Enter numeric marks only.");
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        } finally{
            System.out.println("Marks evaluation done");
            sc.close();
        }
    }
}
