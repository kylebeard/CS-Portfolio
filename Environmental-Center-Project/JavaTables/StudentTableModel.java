//import static EntityTableModel.*;

public class StudentTableModel extends EntityTableModel<Student> {

    public final Attribute<Integer> ID =
            new MutableAttribute<>("ID", Integer.class, Student::getId, Student::setId);
    public final Attribute<String> FIRST_NAME =
            new MutableAttribute<>("First name", String.class, Student::getFirst, Student::setFirst);
    public final Attribute<String> LAST_NAME =
            new MutableAttribute<>("Last name", String.class, Student::getLast, Student::setLast);
    public final Attribute<Student.Status> STATUS =
            new MutableAttribute<>("Status", Student.Status.class, Student::getStatus, Student::setStatus);

    public StudentTableModel() {
        setColumns(ID, FIRST_NAME, LAST_NAME, STATUS);
    }
}
