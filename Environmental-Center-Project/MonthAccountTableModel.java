//import static EntityTableModel;

public class MonthAccountTableModel extends EntityTableModel<MonthAccount> {
    public final Attribute<String> FISCAL_YEAR=
        new MutableAttribute<>("Fiscal Year", String.class, MonthAccount::getFiscalYear, MonthAccount::setFiscalYear);
    public final Attribute<String> MONTH = 
        new MutableAttribute<>("Month", String.class, MonthAccount::getMonth, MonthAccount::setMonth);
    public final Attribute<Integer> ACCOUNT = 
        new MutableAttribute<>("Account", Integer.class, MonthAccount::getAccount, MonthAccount::setAccount);
    public final Attribute<Float> GALLONS = 
        new MutableAttribute<>("Gallons", Float.class, MonthAccount::getGallons, MonthAccount::setGallons);
    public final Attribute<Float> COST = 
        new MutableAttribute<>("Cost", Float.class, MonthAccount::getCost, MonthAccount::setCost);

    public MonthAccountTableModel() {
        setColumns(FISCAL_YEAR, ACCOUNT, MONTH, GALLONS, COST);
    }
}