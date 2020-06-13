import java.util.Objects;

public class Account {

    private int accountNum;
    private String accountName, street, state, zip, current;

    public Account(int accountNum, String accountName, String street, String state, String zip, String current) {
        this.accountNum = accountNum;
        this.accountName = accountName;
        this.street = street;
        this.state = state;
        this.zip = zip;
        this.current = current;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(int accountNum) {
        this.accountNum = accountNum;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Account))
            return false;
        Account a = (Account)o;
        return accountNum == a.accountNum
            && Objects.equals(accountName, a.accountName)
            && Objects.equals(street, a.street)
            && Objects.equals(state, a.state)
            && Objects.equals(zip, a.zip)
            && Objects.equals(current, a.current);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, accountNum, street, state, zip, current);
    }

    @Override
    public String toString() {
        return String.format("%d, %s, %s, %s, %s, %s",
        accountName, accountNum, street, state, zip, current);
    }
}

