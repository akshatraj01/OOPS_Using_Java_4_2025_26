import java.util.ArrayList;
import java.util.List;

class Employee {
    int id;
    String name;
    String position;
    double salary;

    Employee(int id, String name, String position, double salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "John Doe", "Manager", 50000));
        employees.add(new Employee(2, "Jane Smith", "Developer", 60000));
        employees.add(new Employee(3, "Bob Johnson", "HR", 45000));
        employees.add(new Employee(4, "Alice Williams", "Chef", 35000));
        employees.add(new Employee(5, "Charlie Brown", "Receptionist", 30000));

        System.out.println("--- Employee Records ---");
        System.out.println("ID | Name | Position | Salary");
        System.out.println("---------------------------------");

        for (Employee emp : employees) {
            System.out.println(emp.id + " | " + emp.name + " | " + emp.position + " | $" + emp.salary);
        }
    }
}
