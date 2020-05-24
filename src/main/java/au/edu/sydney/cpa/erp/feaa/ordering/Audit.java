package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.Map;

public class Audit implements OrderType {
    private int maxCountedEmployees;
    private boolean isAudit;

    public Audit (){
        this.maxCountedEmployees = 1;
        this.isAudit = true;
    }
    @Override
    public double getCommission(Map<Report, Integer> reports) {
        double cost = 0.0; //Normal audit
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

    @Override
    public String toString(){
        return "Audit";
    }

}
