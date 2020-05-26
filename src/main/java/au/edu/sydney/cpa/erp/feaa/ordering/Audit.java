package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Report;
import java.util.Map;

/**
 * Implements OrderType interface. This is one of the new Classes created as part of the Bridge pattern.
 */
public class Audit implements OrderType {

    private int maxCountedEmployees;
    private boolean isAudit;

    /*
      Audit has default value of maxCountedEmployees as 1 in order to correctly calculate commission.
      This was one of the necessary draw backs to implementing the Bridge and separating the different
      variations of methods within the original Orders.
     */
    public Audit () {
        this.maxCountedEmployees = 1;
        this.isAudit = true;
    }
    @Override
    public double getCommission(Map<Report, Integer> reports) {
        double cost = 0.0;
        for (Report report : reports.keySet()) {
            cost += reports.get(report) * report.getCommission();
        }
        return cost;
    }

    @Override
    public int getMaxCountedEmployees() {
        return maxCountedEmployees;
    }

    @Override
    public boolean isAudit() {
        return isAudit;
    }


}
