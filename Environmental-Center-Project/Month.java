import java.util.Objects;

import jdk.nashorn.internal.ir.ReturnNode;

public class Month {
    private String fiscalYear, month;
    private float gallons, cost;

    public Month(String fiscalYear, String month, float gallons, float cost) {
        this.fiscalYear = fiscalYear;
        this.month = month;
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
        if (!(o instanceof Month))
            return false;
        Month w = (Month)o;
        return fiscalYear == w.fiscalYear
            && Objects.equals(fiscalYear, w.fiscalYear)
            && Objects.equals(month, w.month)
            && Objects.equals(gallons, w.gallons)
            && Objects.equals(cost, w.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fiscalYear, month, gallons, cost);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %d %d, %d", 
        fiscalYear, month, gallons, cost);
    }

}