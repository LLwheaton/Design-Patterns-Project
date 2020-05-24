package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Order;

public class NormalPriority implements OrderPriority {

    private double criticalLoading;

    public NormalPriority(){
        this.criticalLoading = 1.0;
    }

    @Override
    public double getTotalCommission() {
        return 0;
    }

    @Override
    public String generateInvoiceData() {
        return null;
    }

    @Override
    public Order copy() {
        return null;
    }

    @Override
    public String longDesc() {
        return null;
    }

    @Override
    public double getCriticalLoading() {
        //Not critical so returns 1.0
        //Might be a code smell, to discuss
        return criticalLoading;
    }

    @Override
    public boolean isCritical() {
        return false;
    }

    @Override
    public String toString(){
        return "Normal";
    }
}
