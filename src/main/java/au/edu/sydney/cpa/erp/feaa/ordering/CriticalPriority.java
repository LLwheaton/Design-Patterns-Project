// Added class
package au.edu.sydney.cpa.erp.feaa.ordering;

/**
 * Implements OrderPriority interface. This is one of the new classes created as part of the Bridge pattern.
 */
public class CriticalPriority implements OrderPriority {

    private double criticalLoading;

    public CriticalPriority(double criticalLoading){
        this.criticalLoading = criticalLoading;
    }

    @Override
    public double getCriticalLoading() {
        return criticalLoading;
    }

    @Override
    public boolean isCritical() {
        return true;
    }

}
