//import static EntityTableModel;

public class YearAccountTableModel extends EntityTableModel<YearAccount> {
    public final Attribute<String> FISCAL_YEAR=
        new MutableAttribute<>("Fiscal Year", String.class, YearAccount::getFiscalYear, YearAccount::setFiscalYear);
    public final Attribute<String> MONTH = 
        new MutableAttribute<>("Month", String.class, YearAccount::getMonth, YearAccount::setMonth);
    public final Attribute<Integer> ACCOUNT = 
        new MutableAttribute<>("Account", Integer.class, YearAccount::getAccount, YearAccount::setAccount);
    public final Attribute<Float> GALLONS = 
        new MutableAttribute<>("Gallons", Float.class, YearAccount::getGallons, YearAccount::setGallons);
    public final Attribute<Float> COST = 
        new MutableAttribute<>("Cost", Float.class, YearAccount::getCost, YearAccount::setCost);

    public YearAccountTableModel() {
        setColumns(FISCAL_YEAR, MONTH, ACCOUNT, GALLONS, COST);
    }
}