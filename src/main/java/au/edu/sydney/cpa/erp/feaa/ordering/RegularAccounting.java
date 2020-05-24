package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.Map;

public class RegularAccounting implements OrderType {
    private int maxCountedEmployees;
    private boolean isAudit;

    public RegularAccounting(int maxCountedEmployees){
        this.maxCountedEmployees = maxCountedEmployees;
        this.isAudit = false;
    }

    @Override
    public double getCommission(Map<Report, Integer> reports) {
        double cost = 0.0; //Normal Reg
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

    @Override
    public String toString(){
        return "Regular Acounting";
    }
}
