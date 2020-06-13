import java.sql.Date;
import java.util.Objects;

public class Invoice {
	
	private int invoiceNum;
	private int accountNum;
	private double water;
	private double sewer;
	private double sani;
	private double storm;
	private double streetLight;
	private double franchFee;
	private double adj;
	private double totalCharges;
	private String dueDate;
	private String serviceFrom;
	private String serviceTo;
	private double billingUnits;

	public Invoice(int account, int invoice, double water, double sewer, double sani, double storm, double light, double franch, double adj, double total, String due, String from, String to, double units) {
		this.invoiceNum = invoice;
		this.accountNum = account;
		this.water = water;
		this.sewer = water;
		this.sani = sani;
		this.storm = storm;
		this.streetLight = light;
		this.franchFee = franch;
		this.adj = adj;
		this.totalCharges = total;
		this.dueDate = due;
		this.serviceFrom = from;
		this.serviceTo = to;
		this.billingUnits = units;
	}
	
	public int getInvoiceNum() {
		return this.invoiceNum;
	}

	public void setInvoiceNum(int invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	
	public int getAccountNum() {
		return this.accountNum;
	}

	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}
	
	public double getWater() {
		return this.water;
	}

	public void setWater(double water) {
		this.water = water;
	}

	public double getSewer() {
		return this.sewer;
	}

	public void setSewer(double sewer) {
		this.sewer = sewer;
	}
	
	public double getSani() {
		return this.sani;
	}

	public void setSani(double sani) {
		this.sani = sani;
	}
	
	public double getStorm() {
		return this.storm;
	}
	
	public void setStorm(double storm) {
		this.storm = storm;
	}

	public double getStreetLight() {
		return this.streetLight;
	}
	
	public void setStreetLight(double streetLight) {
		this.streetLight = streetLight;
	}

	public double getFranchFee() {
		return this.franchFee;
	}

	public void setFranchFee(double franchFee) {
		this.franchFee = franchFee;
	}
	
	public double getAdj() {
		return this.adj;
	}

	public void setAdj(double adj) {
		this.adj = adj;
	}
	
	public double getTotalCharges() {
		return this.totalCharges;
	}

	public void setTotalCharges(double totalCharges) {
		this.totalCharges = totalCharges;
	}
	
	public String getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public String getServiceFrom() {
		return this.serviceFrom;
	}

	public void setServiceFrom(String serviceFrom) {
		this.serviceFrom = serviceFrom;
	}
	
	public String getServiceTo() {
		return this.serviceTo;
	}

	public void setServiceTo(String serviceTo) {
		this.serviceTo = serviceTo;
	}
	
	public double getUnits() {
		return this.billingUnits;
	}

	public void setUnits(double units) {
		this.billingUnits = units;
	}
	
	@Override
	public boolean equals(Object o) {
        if (!(o instanceof Invoice))
            return false;
        Invoice a = (Invoice)o;
        return accountNum == a.accountNum
            && Objects.equals(accountNum, a.accountNum)
            && Objects.equals(invoiceNum, a.invoiceNum)
            && Objects.equals(water, a.water)
            && Objects.equals(sewer, a.sewer)
			&& Objects.equals(sani, a.storm)
			&& Objects.equals(streetLight, a.streetLight)
			&& Objects.equals(franchFee, a.franchFee)
			&& Objects.equals(adj, a.adj)
			&& Objects.equals(totalCharges, a.totalCharges)
			&& Objects.equals(dueDate, a.dueDate)
			&& Objects.equals(serviceFrom, a.serviceFrom)
			&& Objects.equals(serviceTo, a.serviceTo)
			&& Objects.equals(billingUnits, a.billingUnits);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(invoiceNum, accountNum, water, sewer, sani, streetLight, franchFee, adj, totalCharges, dueDate, serviceFrom, serviceTo, billingUnits);
	}
	
	@Override
    public String toString() {
        return String.format("%d, %d, %d, %d, %d, %d, %d, %d, %d, %s, %s, %s, %d",
        invoiceNum, accountNum, water, sewer, sani, streetLight, franchFee, adj, totalCharges, dueDate, serviceFrom, serviceTo, billingUnits);
    }
}
