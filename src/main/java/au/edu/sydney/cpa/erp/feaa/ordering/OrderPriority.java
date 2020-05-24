package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Order;

public interface OrderPriority {

    double getTotalCommission();
    String generateInvoiceData();
    Order copy();
    String longDesc();
    double getCriticalLoading();
    boolean isCritical();
}
