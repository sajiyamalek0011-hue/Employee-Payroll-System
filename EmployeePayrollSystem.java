import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

class Employee{
	int id;
	String name;
	double basicSalary;
	String department;
	
	Employee(int id, String name, double salary, String dept){
		this.id = id;
		this.name = name;
		this.basicSalary = salary;
		this.department = dept;
	}
	
	Employee(Employee e){
		id = e.id;
		name = e.name;
		basicSalary = e.basicSalary;
		department = e.department;
	}
	
	double calculateSalary(){
		double allowance = basicSalary * 0.10;
		double deduction = basicSalary * 0.05;
		return basicSalary + allowance - deduction;
	}

}

public class EmployeePayrollSystem extends JFrame{
	ArrayList<Employee> employees  = new ArrayList<>();
	JTextField idField = new JTextField();
	JTextField nameField = new JTextField();
	JTextField salaryField = new JTextField();
	
	JComboBox<String> deptBox = new JComboBox<>(
		new String[]{"IT", "HR", "Finanace", "Sales"});
		
		JTextArea output = new JTextArea();
		
		EmployeePayrollSystem() {
        setTitle("Employee Payroll System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
		
		form.add(new JLabel("Employee ID:"));
        form.add(idField);

        form.add(new JLabel("Employee Name:"));
        form.add(nameField);

        form.add(new JLabel("Basic Salary:"));
        form.add(salaryField);

        form.add(new JLabel("Department:"));
        form.add(deptBox);

        JButton addButton = new JButton("Add Employee");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton viewButton = new JButton("View All");

        form.add(addButton);
        form.add(saveButton);
        form.add(loadButton);
        form.add(viewButton);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);

        addButton.addActionListener(e -> addEmployee());
        saveButton.addActionListener(e -> saveEmployees());
        loadButton.addActionListener(e -> loadEmployees());
        viewButton.addActionListener(e -> viewEmployees());
		
		setVisible(true);
    }
	
	void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText());
            String dept = (String) deptBox.getSelectedItem();

            if (id <= 0 || name.isEmpty() || salary < 0) {
                JOptionPane.showMessageDialog(this,
                        "Enter valid employee details!");
                return;
            }
			
			for (Employee emp : employees) {
                if (emp.id == id) {
                    JOptionPane.showMessageDialog(this,
                            "Employee ID already exists!");
                    return;
                }
            }
			
			
			Employee emp = new Employee(id, name, salary, dept);
            employees.add(emp);

            JOptionPane.showMessageDialog(this,
                    "Employee added successfully!");

            idField.setText("");
            nameField.setText("");
            salaryField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Enter a valid ID and salary!");
        }
    }
	
	 void saveEmployees() {
        try (BufferedWriter bw =
                     new BufferedWriter(new FileWriter("employees.txt"))) {

            for (Employee emp : employees) {
                bw.write(emp.id + "|" + emp.name + "|"
                        + emp.basicSalary + "|" + emp.department);
                bw.newLine();
            }

            JOptionPane.showMessageDialog(this,
                    "Employees saved successfully!");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error saving file!");
        }
    }


    void loadEmployees() {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("employees.txt"))) {

            employees.clear();
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                Employee emp = new Employee(
                        Integer.parseInt(data[0]),
                        data[1],
                        Double.parseDouble(data[2]),
                        data[3]);

                employees.add(emp);
            }

            viewEmployees();

            JOptionPane.showMessageDialog(this,
                    "Employees loaded successfully!");

        } catch (IOException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "File not found or data is invalid!");
        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this,
                    "Employee file contains invalid records!");
        }
    }

    

void viewEmployees() {
    System.out.println("View All button clicked!");

    for (int i = 0; i < employees.size(); i++) {
        Employee emp = employees.get(i);

        System.out.println("ID: " + emp.id);
        System.out.println("Name: " + emp.name);
        System.out.println("Basic Salary: " + emp.basicSalary);
        System.out.println("Department: " + emp.department);
        System.out.println("Net Salary: " + emp.calculateSalary());
    }
}


    public static void main(String[] args) {
        new EmployeePayrollSystem();
    }
}
	
	
			


	

	
	