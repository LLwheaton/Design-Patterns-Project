package au.edu.sydney.cpa.erp.feaa.ordering;

public class CriticalPriority implements OrderPriority {
    private double criticalLoading;

    public CriticalPriority(double criticalLoading){
        this.criticalLoading = criticalLoading;
    }

    public double getCriticalLoading(){
        return criticalLoading;
    }

    @Override
    public boolean isCritical() {
        return true;
    }

}
