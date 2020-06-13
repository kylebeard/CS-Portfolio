//import static EntityTableModel;

public class YearTableModel extends EntityTableModel<Year> {
    public final Attribute<String> FISCAL_YEAR=
        new MutableAttribute<>("Fiscal Year", String.class, Year::getFiscalYear, Year::setFiscalYear);
    public final Attribute<Float> GALLONS = 
        new MutableAttribute<>("Gallons", Float.class, Year::getGallons, Year::setGallons);
    public final Attribute<Float> COST = 
        new MutableAttribute<>("Cost", Float.class, Year::getCost, Year::setCost);

    public YearTableModel() {
        setColumns(FISCAL_YEAR, GALLONS, COST);
    }
}