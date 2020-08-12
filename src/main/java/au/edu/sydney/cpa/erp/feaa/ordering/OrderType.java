// Added interface
package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Report;
import java.util.Map;

/**
 * In the current application there are two Order types for the Bridge pattern: Audit and RegularAccounting.
 * Any new Order types can easily implement this interface.
 */
public interface OrderType {

    /**
     * Calculates the cost for all report commissions.
     * @param reports The reports used to calculate the cost
     * @return The accumulative cost of commissions in the reports given
     */
    double getCommission(Map<Report, Integer> reports);

    /**
     * Simple getter.
     * @return the variable for maxCountedEmployees
     */
    int getMaxCountedEmployees();

    /**
     * Simple getter.
     * @return boolean for if the order type is an Audit
     */
    boolean isAudit();
}
