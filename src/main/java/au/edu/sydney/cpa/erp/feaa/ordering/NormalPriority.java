package au.edu.sydney.cpa.erp.feaa.ordering;


public class NormalPriority implements OrderPriority {

    private double criticalLoading;

    public NormalPriority(){
        this.criticalLoading = 0.0;
    }

    @Override
    public double getCriticalLoading() {
        //Not critical so returns 0.0
        //Might be a code smell, to discuss
        return criticalLoading;
    }

    @Override
    public boolean isCritical() {
        return false;
    }

}
