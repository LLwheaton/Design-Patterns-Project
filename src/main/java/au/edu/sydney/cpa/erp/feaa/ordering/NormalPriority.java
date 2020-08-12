// Added class
package au.edu.sydney.cpa.erp.feaa.ordering;

/**
 * Implements OrderPriority interface. This is one of the new classes created as part of the Bridge pattern.
 */
public class NormalPriority implements OrderPriority {

    private double criticalLoading;

    /*
       To use the Bridge pattern to separate the Order classes and
       their methods, criticalLoading is needed to correctly calculate the
       commission (method used in OrderType classes). The default for this
       is 0, implying that a non-critical order has 0% critical loading added
       to the commission.
     */
    public NormalPriority() {
        this.criticalLoading = 0.0;
    }

    @Override
    public double getCriticalLoading() {
        return criticalLoading;
    }

    @Override
    public boolean isCritical() {
        return false;
    }
}
