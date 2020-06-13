import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class Main extends JFrame {

    private StudentTableModel studentTableModel;
    private JTable studentTable;

    public Main() {
        super("Student Database Demo");

        setLayout(new BorderLayout(5, 5));

        studentTableModel = new StudentTableModel();
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

    public static void main(String... args) {
        Main main = new Main();
        main.setVisible(true);
    }
}
