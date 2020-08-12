// Added interface
package au.edu.sydney.cpa.erp.feaa.ordering;

/**
 * In the current application there are two Order priorities for the Bridge pattern: Critical and Normal.
 */
public interface OrderPriority {

    /**
     * Simple getter.
     * @return The double value of criticalLoading
     */
    double getCriticalLoading();

    /**
     * Simple getter.
     * @return Whether the Order is a Critical Order
     */
    boolean isCritical();
}
