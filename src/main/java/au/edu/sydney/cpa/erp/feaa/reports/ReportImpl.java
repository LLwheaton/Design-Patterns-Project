package au.edu.sydney.cpa.erp.feaa.reports;

import au.edu.sydney.cpa.erp.ordering.Report;
import com.google.common.primitives.ImmutableDoubleArray;

import java.util.Arrays;
import java.util.Objects;

public class ReportImpl implements Report {

    //Value Object requires it's values to be immutable
    private final String name;
    private final double commissionPerEmployee;
    //private double[] legalData;
    private final ImmutableDoubleArray legalData;
    //private double[] cashFlowData;
    private final ImmutableDoubleArray cashFlowData;
    //private double[] mergesData;
    private final ImmutableDoubleArray mergesData;
    //private double[] tallyingData;
    private final ImmutableDoubleArray tallyingData;
    //private double[] deductionsData;
    private final ImmutableDoubleArray deductionsData;

    public ReportImpl(String name,
                      double commissionPerEmployee,
                      double[] legalData,
                      double[] cashFlowData,
                      double[] mergesData,
                      double[] tallyingData,
                      double[] deductionsData) {
        this.name = name;
        this.commissionPerEmployee = commissionPerEmployee;
        this.legalData = (legalData == null) ? null : ImmutableDoubleArray.copyOf(legalData);
        this.cashFlowData = (cashFlowData == null) ? null : ImmutableDoubleArray.copyOf(cashFlowData);
        this.mergesData = (mergesData == null) ? null : ImmutableDoubleArray.copyOf(mergesData);
        this.tallyingData = (tallyingData == null) ? null : ImmutableDoubleArray.copyOf(tallyingData);
        this.deductionsData = (deductionsData == null) ? null : ImmutableDoubleArray.copyOf(deductionsData);
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
        if(legalData == null) {
            return null;
        }
        double[] legalDataArray = new double[legalData.length()];
        for(int i = 0; i < legalData.length(); i++) {
            legalDataArray[i] = legalData.get(i);
        }
        return legalDataArray;
    }

    @Override
    public double[] getCashFlowData() {
        if(cashFlowData == null) {
            return null;
        }
        double[] cashFlowDataArray = new double[cashFlowData.length()];
        for(int i = 0; i < cashFlowData.length(); i++) {
            cashFlowDataArray[i] =  cashFlowData.get(i);
        }
        return cashFlowDataArray;
    }

    @Override
    public double[] getMergesData() {
        if(mergesData == null) {
            return null;
        }
        double[] mergesDataArray = new double[mergesData.length()];
        for(int i = 0; i < mergesData.length(); i++){
            mergesDataArray[i] = mergesData.get(i);
        }
        return mergesDataArray;
    }

    @Override
    public double[] getTallyingData() {
        if(tallyingData == null) {
            return null;
        }
        double[] tallyDataArray = new double[tallyingData.length()];
        for(int i = 0; i < tallyingData.length(); i++){
            tallyDataArray[i] = tallyingData.get(i);
        }
        return tallyDataArray;
    }

    @Override
    public double[] getDeductionsData() {
        if(deductionsData == null){
            return null;
        }
        double[] deductionsArray = new double[deductionsData.length()];
        for(int i = 0; i < deductionsData.length(); i++){
            deductionsArray[i] = deductionsData.get(i);
        }
        return deductionsArray;
    }

    @Override
    public String toString() {

        return String.format("%s", name);
    }

    //Makes sure it is of ReportImpl type and calls equals method
    public boolean equals(Object other) {
        if(!(other instanceof ReportImpl)) {
            return false;
        }
        return equals((ReportImpl)other);
    }

    //checking for equality between all variables
    public boolean equals(ReportImpl other) {
        if (this.getCommission() == other.getCommission() &&
                this.name.equals(other.getReportName()) &&
                Arrays.equals(this.getLegalData(), other.getLegalData()) &&
                Arrays.equals(this.getCashFlowData(), other.getCashFlowData()) &&
                Arrays.equals(this.getMergesData(), other.getMergesData()) &&
                Arrays.equals(this.getTallyingData(), other.getTallyingData()) &&
                Arrays.equals(this.getDeductionsData(), other.getDeductionsData())) {
            return true;
        }
        return false;
    }
    //hash code is combined objects
    public int hashCode() {
        return Objects.hash(name, commissionPerEmployee, legalData, cashFlowData, mergesData, tallyingData, deductionsData);
    }
}
