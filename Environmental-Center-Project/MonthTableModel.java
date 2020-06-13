//import static EntityTableModel;

public class MonthTableModel extends EntityTableModel<Month> {
    public final Attribute<String> FISCAL_YEAR=
        new MutableAttribute<>("Fiscal Year", String.class, Month::getFiscalYear, Month::setFiscalYear);
    public final Attribute<String> MONTH = 
        new MutableAttribute<>("Month", String.class, Month::getMonth, Month::setMonth);
    public final Attribute<Float> GALLONS = 
        new MutableAttribute<>("Gallons", Float.class, Month::getGallons, Month::setGallons);
    public final Attribute<Float> COST = 
        new MutableAttribute<>("Cost", Float.class, Month::getCost, Month::setCost);

    public MonthTableModel() {
        setColumns(FISCAL_YEAR, MONTH, GALLONS, COST);
    }
}