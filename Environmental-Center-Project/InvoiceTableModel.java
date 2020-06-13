import java.sql.Date;

public class InvoiceTableModel extends EntityTableModel<Invoice> {

	public final Attribute<Integer> INVOICE_NUM = 
		new MutableAttribute<>("Invoice Number", Integer.class, Invoice::getInvoiceNum, Invoice::setInvoiceNum);
	public final Attribute<Integer> ACCOUNT_NUM = 
		new MutableAttribute<>("Account", Integer.class, Invoice::getAccountNum, Invoice::setAccountNum);
	public final Attribute<Double> WATER = 
		new MutableAttribute<>("Water Charges", Double.class, Invoice::getWater, Invoice::setWater);
	public final Attribute<Double> SEWER = 
		new MutableAttribute<>("Sewer Charges", Double.class, Invoice::getSewer, Invoice::setSewer);
	public final Attribute<Double> SANI = 
		new MutableAttribute<>("Sanitation Charges", Double.class, Invoice::getSani, Invoice::setSani);
	public final Attribute<Double> STORM = 
		new MutableAttribute<>("Storm Charges", Double.class, Invoice::getStorm, Invoice::setStorm);
	public final Attribute<Double> STREET_LIGHT = 
		new MutableAttribute<>("Street Light Charges", Double.class, Invoice::getStreetLight, Invoice::setStreetLight);
	public final Attribute<Double> FRANCH_FEE = 
		new MutableAttribute<>("Franchise Fee", Double.class, Invoice::getFranchFee, Invoice::setFranchFee);
	public final Attribute<Double> ADJ = 
		new MutableAttribute<>("Adjustments", Double.class, Invoice::getAdj, Invoice::setAdj);
	public final Attribute<Double> TOTAL_CHARGES = 
		new MutableAttribute<>("Total Charges", Double.class, Invoice::getTotalCharges, Invoice::setTotalCharges);
	public final Attribute<String> DUE_DATE = 
		new MutableAttribute<>("Due Date", String.class, Invoice::getDueDate, Invoice::setDueDate);
	public final Attribute<String> SERVICE_FROM = 
		new MutableAttribute<>("Service Date From", String.class, Invoice::getServiceFrom, Invoice::setServiceFrom);
	public final Attribute<String> SERVICE_TO = 
		new MutableAttribute<>("Service Date To", String.class, Invoice::getServiceTo, Invoice::setServiceTo);
	public final Attribute<Double> BILLING_UNITS = 
		new MutableAttribute<>("Billing Units", Double.class, Invoice::getUnits, Invoice::setUnits);

	public InvoiceTableModel() {
		setColumns(INVOICE_NUM, ACCOUNT_NUM, WATER, SEWER, SANI, STORM, STREET_LIGHT, FRANCH_FEE, ADJ, TOTAL_CHARGES, DUE_DATE, SERVICE_FROM, SERVICE_TO, BILLING_UNITS);
	}

}
