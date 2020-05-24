package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.Map;

public interface OrderType {
    double getCommission(Map<Report, Integer> reports);
    int getMaxCountedEmployees();
    boolean isAudit();

}
