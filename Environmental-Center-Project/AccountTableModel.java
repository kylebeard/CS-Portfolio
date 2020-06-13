//import static EntityTableModel.*;

public class AccountTableModel extends EntityTableModel<Account> {

    public final Attribute<Integer> ACCOUNT_NUM =
            new MutableAttribute<>("Account Number", Integer.class, Account::getAccountNum, Account::setAccountNum);
    public final Attribute<String> ACCOUNT_NAME =
            new MutableAttribute<>("Account Name", String.class, Account::getAccountName, Account::setAccountName);
    public final Attribute<String> STREET =
            new MutableAttribute<>("Street", String.class, Account::getStreet, Account::setStreet);
    public final Attribute<String> STATE =
            new MutableAttribute<>("State", String.class, Account::getState, Account::setState);
        public final Attribute<String> ZIP =
            new MutableAttribute<>("Zip", String.class, Account::getZip, Account::setZip);
    public final Attribute<String> CURRENT =
            new MutableAttribute<>("Current", String.class, Account::getCurrent, Account::setCurrent);

    public AccountTableModel() {
        setColumns(ACCOUNT_NUM, ACCOUNT_NAME, STREET, STATE, ZIP, CURRENT);
    }
}
