package au.edu.sydney.cpa.erp.feaa.reports;

import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.Arrays;
import java.util.Objects;

public class ReportImpl implements Report {

    //Value Object requires it's values to be immutable
    private final String name;
    private final double commissionPerEmployee;
    private double[] legalData;
    private double[] cashFlowData;
    private double[] mergesData;
    private double[] tallyingData;
    private double[] deductionsData;

    public ReportImpl(String name,
                      double commissionPerEmployee,
                      double[] legalData,
                      double[] cashFlowData,
                      double[] mergesData,
                      double[] tallyingData,
                      double[] deductionsData) {
        this.name = name;
        this.commissionPerEmployee = commissionPerEmployee;
        this.legalData = legalData;
        this.cashFlowData = cashFlowData;
        this.mergesData = mergesData;
        this.tallyingData = tallyingData;
        this.deductionsData = deductionsData;
    }

    @Override
    public String getReportName() {
        return name;
    }

    @Override
    public double getCommission() {
        return commissionPerEmployee;
    }

    @Override
    public double[] getLegalData() {
        return legalData;
    }

    @Override
    public double[] getCashFlowData() {
        return cashFlowData;
    }

    @Override
    public double[] getMergesData() {
        return mergesData;
    }

    @Override
    public double[] getTallyingData() {
        return tallyingData;
    }

    @Override
    public double[] getDeductionsData() {
        return deductionsData;
    }

    @Override
    public String toString() {

        return String.format("%s", name);
    }

    //Makes sure it is of ReportImpl type and calls equals method
    public boolean equals(Object other){
        if(!(other instanceof ReportImpl)){
            return false;
        }
        return equals((ReportImpl)other);
    }

    //checking for equality between all variables
    public boolean equals(ReportImpl other){
        if (this.getCommission() == other.getCommission() &&
                this.name.equals(other.getReportName()) &&
                Arrays.equals(this.legalData, other.getLegalData()) &&
                Arrays.equals(this.cashFlowData, other.getCashFlowData()) &&
                Arrays.equals(this.mergesData, other.getMergesData()) &&
                Arrays.equals(this.tallyingData, other.getTallyingData()) &&
                Arrays.equals(this.deductionsData, other.getDeductionsData())) {
            return true;
        }
        return false;
    }
    //hash code is combined objects
    public int hashCode(){
        return Objects.hash(name, commissionPerEmployee, legalData, cashFlowData, mergesData, tallyingData, deductionsData);
    }
}
