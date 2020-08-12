// Added class
package au.edu.sydney.cpa.erp.feaa.ordering;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.Map;

/**
 * Implements OrderType interface. This is one of the new classes created as part of the Bridge pattern.
 */
public class RegularAccounting implements OrderType {

    private int maxCountedEmployees;
    private boolean isAudit;

    /*
       Value for isAudit is set to false here, obviously since this type is not
       an audit. This is necessary for Scheduled and NonScheduled Orders to use
       as a check for generating Strings or computations.
     */
    public RegularAccounting(int maxCountedEmployees){
        this.maxCountedEmployees = maxCountedEmployees;
        this.isAudit = false;
    }

    @Override
    public double getCommission(Map<Report, Integer> reports) {
        double cost = 0.0;
        for (Report report : reports.keySet()) {
            cost += report.getCommission() * Math.min(maxCountedEmployees, reports.get(report));
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
