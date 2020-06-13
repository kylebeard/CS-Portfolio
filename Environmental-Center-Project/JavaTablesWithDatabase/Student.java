import java.util.Objects;

public class Student {

    public enum Status {
        FULL_TIME, PART_TIME, SPECIAL
    };

    private int id;
    private String first, last;
    private Status status;

    public Student(int id, String first, String last, Status status) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student))
            return false;
        Student s = (Student)o;
        return id == s.id
            && Objects.equals(first, s.first)
            && Objects.equals(last, s.last)
            && status == s.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first, last, status);
    }

    @Override
    public String toString() {
        return String.format("[%08d] %s, %s {%s}",
                id, last, first, status);
    }
}

