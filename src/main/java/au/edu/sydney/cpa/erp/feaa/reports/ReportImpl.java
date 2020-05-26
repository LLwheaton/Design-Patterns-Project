package au.edu.sydney.cpa.erp.feaa.reports;

import au.edu.sydney.cpa.erp.ordering.Report;
import java.util.Arrays;
import java.util.Objects;

/**
 * Implements Report using the Value Object pattern. This requires it's values to be
 * immutable. This is achieved by using 'final', as well as creating clones of arrays
 * as needed. Flyweight is also used here to cache the values to reduce RAM usage.
 */
public class ReportImpl implements Report {

    private final String name;
    private final double commissionPerEmployee;
    private final double[] legalData;
    private final double[] cashFlowData;
    private final double[] mergesData;
    private final double[] tallyingData;
    private final double[] deductionsData;

    public ReportImpl(String name,
                      double commissionPerEmployee,
                      double[] legalData,
                      double[] cashFlowData,
                      double[] mergesData,
                      double[] tallyingData,
                      double[] deductionsData) {
        this.name = ReportData.createName(name);
        this.commissionPerEmployee = ReportData.createCommission(commissionPerEmployee);
        this.legalData = (legalData == null) ? null : ReportData.createArray(legalData.clone());
        this.cashFlowData = (cashFlowData == null) ? null : ReportData.createArray(cashFlowData.clone());
        this.mergesData = (mergesData == null) ? null : ReportData.createArray(mergesData.clone());
        this.tallyingData = (tallyingData == null) ? null : ReportData.createArray(tallyingData.clone());
        this.deductionsData = (deductionsData == null) ? null : ReportData.createArray(deductionsData.clone());
    }

    @Override
    public String getReportName() {
        return new String(name);
    }

    @Override
    public double getCommission() {
        return commissionPerEmployee;
    }

    @Override
    public double[] getLegalData() {
        return legalData == null ? null : legalData.clone();
    }

    @Override
    public double[] getCashFlowData() {
        return cashFlowData == null ? null : cashFlowData.clone();
    }

    @Override
    public double[] getMergesData() {
        return mergesData == null ? null : mergesData.clone();
    }

    @Override
    public double[] getTallyingData() {
        return tallyingData == null ? null : tallyingData.clone();
    }

    @Override
    public double[] getDeductionsData() {
        return deductionsData == null ? null : deductionsData.clone();
    }

    @Override
    public String toString() {

        return String.format("%s", name);
    }

    /* Makes sure it is of ReportImpl type and calls equals method */
    public boolean equals(Object other) {
        if(!(other instanceof ReportImpl)) {
            return false;
        }
        return equals((ReportImpl)other);
    }

    /* Check for equality involves comparing all values inside ReportImpl */
    public boolean equals(ReportImpl other) {
        if (this.getCommission() == other.getCommission() &&
                this.name.equals(other.getReportName()) &&
                Arrays.equals(legalData, other.getLegalData()) &&
                Arrays.equals(cashFlowData, other.getCashFlowData()) &&
                Arrays.equals(mergesData, other.getMergesData()) &&
                Arrays.equals(tallyingData, other.getTallyingData()) &&
                Arrays.equals(deductionsData, other.getDeductionsData())) {
            return true;
        }
        return false;
    }

    /* Hash code is combined values */
    public int hashCode() {
        return Objects.hash(name, commissionPerEmployee, legalData, cashFlowData, mergesData, tallyingData, deductionsData);
    }
}
