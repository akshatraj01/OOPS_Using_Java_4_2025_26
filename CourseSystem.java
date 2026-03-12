import java.io.*;
import java.util.*;

//Student 
class Student {
    int studentId;
    String studentName;

    public Student(int studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }
}
// Course
class Course {

    int courseId;
    String courseName;
    int maxSeats;
    List<Student> enrolledStudents = new ArrayList<>();

    public Course(int id, String name, int seats) {
        this.courseId = id;
        this.courseName = name;
        this.maxSeats = seats;
    }

    public void displayCourse() {
        System.out.println(courseId + " - " + courseName +
                " Seats: " + enrolledStudents.size() + "/" + maxSeats);
    }
}

//Custom Exception
class CourseFullException extends Exception {
    public CourseFullException(String msg) {
        super(msg);
    }
}

class CourseNotFoundException extends Exception {
    public CourseNotFoundException(String msg) {
        super(msg);
    }
}

class DuplicateEnrollmentException extends Exception {
    public DuplicateEnrollmentException(String msg) {
        super(msg);
    }
}
//Service
class CourseService {

    List<Course> courses = new ArrayList<>();

    public void addCourse(Course c) {
        courses.add(c);
    }

    public void enrollStudent(int courseId, Student s)
            throws CourseFullException,CourseNotFoundException,DuplicateEnrollmentException{

        Course found = null;

        for (Course c : courses) {
            if (c.courseId == courseId) {
                found = c;
                break;
            }
        }

        if (found == null)
            throw new CourseNotFoundException("Course not found");

        for (Student st : found.enrolledStudents) {
            if (st.studentId == s.studentId)
                throw new DuplicateEnrollmentException("Student already enrolled");
        }

        if (found.enrolledStudents.size() >= found.maxSeats)
            throw new CourseFullException("Course full");

        found.enrolledStudents.add(s);

        saveToFile(found,s);
    }
//File Write
    private void saveToFile(Course c, Student s) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("courses.txt", true))) {

            bw.write(c.courseId + "," + c.courseName + "," +
                    s.studentId + "," + s.studentName);
            bw.newLine();

        } catch (IOException e) {
            System.out.println("File write error");
        }
    }

    public void viewCourses() {

        for (Course c : courses)
            c.displayCourse();

        System.out.println("\nFile Records:");

        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {

            String line;
            while ((line = br.readLine()) != null)
                System.out.println(line);

        } catch (IOException e) {
            System.out.println("File read error");
        }
    }
}
//Main
public class CourseSystem {

    public static void main(String[] args){

        CourseService service = new CourseService();

        Course c1 = new Course(101,"Java",2);
        Course c2 = new Course(102,"DSA",2);

        service.addCourse(c1);
        service.addCourse(c2);

        Student s1 = new Student(1,"Akshat");
        Student s2 = new Student(2,"Rahul");
        Student s3 = new Student(3,"Simran");

        try {
            service.enrollStudent(101,s1);
            service.enrollStudent(101,s2);

            service.enrollStudent(101,s1);

        }catch (CourseFullException e){
            System.out.println(e.getMessage());

        }catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());

        }catch (DuplicateEnrollmentException e) {
            System.out.println(e.getMessage());
        }
        service.viewCourses();
    }
}