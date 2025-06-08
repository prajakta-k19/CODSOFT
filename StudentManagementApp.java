import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class Student implements Serializable{
    private String name;
    private String roll;
    private String grade;
    public Student(String name, String roll, String grade) {
        this.name = name;
        this.roll = roll;
        this.grade = grade;
    }
    public String getName(){ 
        return name; 
    }
    public String getRoll(){
        return roll; 
    }
    public String getGrade(){ 
        return grade; 
    }
    public void setName(String name){
        this.name = name; 
    }
    public void setRoll(String roll){ 
        this.roll = roll; 
    }
    public void setGrade(String grade){ 
        this.grade = grade; 
    }
}

public class StudentManagementApp extends JFrame {
    private ArrayList<Student> students;
    private DefaultTableModel tableModel;
    private JTable table;
    private File file = new File("students.dat");
    public StudentManagementApp() {
        setTitle("Student Management System");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        students = loadData();
        String[] columns = {"Name", "Roll No", "Grade"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        refreshTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton add = new JButton("Add Student");
        JButton edit = new JButton("Edit Student");
        JButton delete = new JButton("Remove Student");
        JButton search = new JButton("Search");
        JButton exit = new JButton("Exit");
        add.setPreferredSize(new Dimension(130, 35));
        edit.setPreferredSize(new Dimension(130, 35));
        delete.setPreferredSize(new Dimension(130, 35));
        search.setPreferredSize(new Dimension(130, 35));
        exit.setPreferredSize(new Dimension(130, 35));
        panel.add(add);
        panel.add(edit);
        panel.add(delete);
        panel.add(search);
        panel.add(exit);
        add(panel, BorderLayout.SOUTH);
        add.addActionListener(e -> addStudent());
        edit.addActionListener(e -> editStudent());
        delete.addActionListener(e -> deleteStudent());
        search.addActionListener(e -> searchStudent());
        exit.addActionListener(e -> {
            saveData();
            dispose();
        });  
        setVisible(true);
    }

    private void addStudent() {
        JTextField nameField = new JTextField();
        JTextField rollField = new JTextField();
        JTextField gradeField = new JTextField();
        Object[] fields = {
            "Name:", nameField,
            "Roll Number:", rollField,
            "Grade:", gradeField
        };
        int result = JOptionPane.showConfirmDialog(this, fields, "Add New Student", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String roll = rollField.getText().trim();
            String grade = gradeField.getText().trim();
            if (name.isEmpty() || roll.isEmpty() || grade.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }
            for (Student s : students) {
                if (s.getRoll().equalsIgnoreCase(roll)) {
                    JOptionPane.showMessageDialog(this, "A student with this roll number already exists.");
                    return;
                }
            }
            students.add(new Student(name, roll, grade));
            refreshTable();
        }
    }

    private void editStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.");
            return;
        }
        Student s = students.get(row);
        JTextField nameField = new JTextField(s.getName());
        JTextField rollField = new JTextField(s.getRoll());
        JTextField gradeField = new JTextField(s.getGrade());
        Object[] fields = {
            "Name:", nameField,
            "Roll Number:", rollField,
            "Grade:", gradeField
        };
        int result = JOptionPane.showConfirmDialog(this, fields, "Edit Student", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String roll = rollField.getText().trim();
            String grade = gradeField.getText().trim();
            if (name.isEmpty() || roll.isEmpty() || grade.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }
            for (int i = 0; i < students.size(); i++) {
                if (i != row && students.get(i).getRoll().equalsIgnoreCase(roll)) {
                    JOptionPane.showMessageDialog(this, "A student with this roll number already exists.");
                    return;
                }
            }
            s.setName(name);
            s.setRoll(roll);
            s.setGrade(grade);
            refreshTable();
        }
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            students.remove(row);
            refreshTable();
        }
    }

    private void searchStudent() {
        String roll = JOptionPane.showInputDialog(this, "Enter roll number to search:");
        if (roll == null || roll.trim().isEmpty()) return;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getRoll().equalsIgnoreCase(roll.trim())) {
                table.setRowSelectionInterval(i, i);
                table.scrollRectToVisible(table.getCellRect(i, 0, true));
                JOptionPane.showMessageDialog(this, "Student found and highlighted.");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Student with roll number " + roll + " not found.");
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : students) {
            tableModel.addRow(new Object[]{s.getName(), s.getRoll(), s.getGrade()});
        }
    }

    private ArrayList<Student> loadData() {
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Student>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(students);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save data.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementApp::new);
    }
}