package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Order;

public class CriticalPriority implements OrderPriority {
    private double criticalLoading;

    public CriticalPriority(double criticalLoading){
        this.criticalLoading = criticalLoading;
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

    public double getCriticalLoading(){
        return criticalLoading;
    }

    @Override
    public boolean isCritical() {
        return true;
    }

    @Override
    public String toString(){
        return "Critical";
    }

}
