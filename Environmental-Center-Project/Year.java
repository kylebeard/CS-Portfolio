import java.util.Objects;

import jdk.nashorn.internal.ir.ReturnNode;

public class Year {
    private String fiscalYear;
    private float gallons, cost;

    public Year(String fiscalYear, float gallons, Float cost) {
        this.fiscalYear = fiscalYear;
        this.gallons = gallons;
        this.cost = cost;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
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
        if (!(o instanceof Year))
            return false;
        Year w = (Year)o;
        return fiscalYear == w.fiscalYear
            && Objects.equals(fiscalYear, w.fiscalYear)
            && Objects.equals(gallons, w.gallons)
            && Objects.equals(cost, w.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fiscalYear, gallons, cost);
    }

    @Override
    public String toString() {
        return String.format("%s, %d, %d", 
        fiscalYear, gallons, cost);
    }

}