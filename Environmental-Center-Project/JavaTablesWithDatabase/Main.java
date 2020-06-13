import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.List; // needed to resolve name clash between java.awt.List and java.util.List
import javax.swing.*;
import javax.swing.table.*;

public class Main extends JFrame {

    private static final String DB_URL =
        "jdbc:mysql://localhost/Students?user=java&password=who";

    private StudentTableModel studentTableModel;
    private JTable studentTable;

    public Main() {
        super("Student Database Demo");

        setLayout(new BorderLayout(5, 5));

        studentTableModel = new StudentTableModel();
        /*
        studentTableModel.addInstance(new Student(1, "William", "Hartnell", Student.Status.SPECIAL));
        studentTableModel.addInstance(new Student(2, "Patrick", "Troughton", Student.Status.FULL_TIME));
        studentTableModel.addInstance(new Student(3, "Jon", "Pertwee", Student.Status.PART_TIME));
        studentTableModel.addInstance(new Student(4, "Tom", "Baker", Student.Status.FULL_TIME));
        studentTableModel.addInstance(new Student(5, "Peter", "Davison", Student.Status.PART_TIME));
        studentTableModel.addInstance(new Student(6, "Colin", "Baker", Student.Status.PART_TIME));
        studentTableModel.addInstance(new Student(7, "Sylvester", "McCoy", Student.Status.FULL_TIME));
        studentTableModel.addInstance(new Student(8, "Paul", "McGann", Student.Status.FULL_TIME));
        studentTableModel.addInstance(new Student(9, "Christopher", "Eccleston", Student.Status.FULL_TIME));
        studentTableModel.addInstance(new Student(10, "David", "Tennant", Student.Status.PART_TIME));
        studentTableModel.addInstance(new Student(11, "Matt", "Smith", Student.Status.FULL_TIME));
        studentTableModel.addInstance(new Student(12, "Peter", "Capaldi", Student.Status.FULL_TIME));
        studentTableModel.addInstance(new Student(13, "Jodie", "Whittaker", Student.Status.FULL_TIME));
        */
        List<Student> students = fetchStudents();
        studentTableModel.addAll(students);

        studentTable = new JTable(studentTableModel);
        studentTable.setDefaultEditor(Student.Status.class, new EnumCellEditor<>(Student.Status.values()));

        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        JPanel sortPanel = new JPanel();
        JButton sortIdButton = new JButton("Sort by ID");
        JButton sortFirstButton = new JButton("Sort by first");
        JButton sortLastButton = new JButton("Sort by last");
        JButton sortStatusButton = new JButton("Sort by status");

        sortPanel.add(sortIdButton);
        sortPanel.add(sortFirstButton);
        sortPanel.add(sortLastButton);
        sortPanel.add(sortStatusButton);

        sortIdButton.addActionListener(e -> studentTableModel.sort(studentTableModel.ID));
        sortFirstButton.addActionListener(e -> studentTableModel.sort(studentTableModel.FIRST_NAME));
        sortLastButton.addActionListener(e -> studentTableModel.sort(studentTableModel.LAST_NAME));
        sortStatusButton.addActionListener(e -> studentTableModel.sort(studentTableModel.STATUS));

        add(sortPanel, BorderLayout.SOUTH);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private List<Student> fetchStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT id, fname, lname, status FROM Student");
            while (results.next()) {
                int id = results.getInt("id");
                String fname = results.getString("fname"),
                       lname = results.getString("lname"),
                       status = results.getString("status");
                Student student = new Student(id, fname, lname,
                    status.equals("FT") ? Student.Status.FULL_TIME
                        : status.equals("PT") ? Student.Status.PART_TIME
                        : Student.Status.SPECIAL);
                students.add(student);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error communicating with database: " + ex.getMessage(),
                "Database error",
                JOptionPane.ERROR_MESSAGE);
        }
        
        return students;
    }

    public static void main(String... args) {
        Main main = new Main();
        main.setVisible(true);
    }
}
