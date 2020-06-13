import java.util.Objects;

import jdk.nashorn.internal.ir.ReturnNode;

public class MonthAccount {
    private String fiscalYear, month;
    private int account;
    private float gallons, cost;

    public MonthAccount(String fiscalYear, String month, int account, float gallons, float cost) {
        this.fiscalYear = fiscalYear;
        this.month = month;
        this.account = account;
        this.gallons = gallons;
        this.cost = cost;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getAccount() {
        return this.account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public float getGallons() {
        return this.gallons;
    }

    public void setGallons(float gallons) {
        this.gallons = gallons;
    }

    public float getCost() {
        return this.cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MonthAccount))
            return false;
        MonthAccount w = (MonthAccount)o;
        return fiscalYear == w.fiscalYear
            && Objects.equals(fiscalYear, w.fiscalYear)
            && Objects.equals(month, w.month)
            && Objects.equals(gallons, w.gallons)
            && Objects.equals(cost, w.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fiscalYear, month, account, gallons, cost);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %d, %d, %d", 
        fiscalYear, month, account, gallons, cost);
    }

}