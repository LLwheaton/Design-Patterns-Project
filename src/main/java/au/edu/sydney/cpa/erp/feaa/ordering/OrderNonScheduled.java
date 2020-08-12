// Added class
package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * One of the main components of Bridge pattern, this class contains the bulk of the shared methods
 * between Orders. Any variations between the original Order classes (now removed) are handled
 * appropriately here, occasionally using information from OrderPriority and OrderType if needed.
 *
 * There are two kinds of priority: Critical and Normal
 * There are two kinds of types: Audit and RegularAccounting
 */
public class OrderNonScheduled implements Order {
    private Map<Report, Integer> reports = new HashMap<>();
    private final OrderPriority priority;
    private final OrderType type;
    private final int id;
    private int client;
    private LocalDateTime date;
    private boolean finalised = false;

    public OrderNonScheduled(OrderPriority priority, OrderType type, int id, int client, LocalDateTime date) {
        this.priority = priority;
        this.type = type;
        this.id = id;
        this.client = client;
        this.date = date;
    }

    @Override
    public int getOrderID() {
        return id;
    }

    /**
     * In the original application code, this method had multiple variations. To solve this I broke it up
     * to its most basic parts (seen below) and call on type and priority to add their parts if needed.
     * @return The total commission as a double
     */
    @Override
    public double getTotalCommission() {
        double cost = type.getCommission(reports);
        cost += cost * priority.getCriticalLoading();
        return cost;
    }

    @Override
    public LocalDateTime getOrderDate() {
        return date;
    }

    /*
       Initially, this method had a long check for equality between Report objects,
       however since the Reports are now using the Value Object pattern, the equality
       check can be removed, as the Map can do this for us (by comparing hash codes).
     */
    @Override
    public void setReport(Report report, int employeeCount) {
        if(finalised) {
            throw new IllegalStateException("Order was already finalised.");
        }
        reports.put(report, employeeCount);
    }

    @Override
    public Set<Report> getAllReports() {
        return reports.keySet();
    }

    /*
       Another equality check was removed here due to the Reports now being Value Objects.
     */
    @Override
    public int getReportEmployeeCount(Report report) {
        Integer result = reports.get(report);
        return null == result ? 0 : result;
    }

    /*
       Another method that had multiple variations. A few Boolean checks here for isAudit or isCritical were used
       to avoid using instanceof, but were also necessary to ensure Orders generated the invoice data String correctly.
     */
    @Override
    public String generateInvoiceData() {
        if(priority.isCritical()) { //Boolean check for Critical Orders
            return String.format("Your priority business account has been charged: $%,.2f" +
                    "\nPlease see your internal accounting department for itemised details.", getTotalCommission());
        } else {

            StringBuilder sb = new StringBuilder();

            sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
            sb.append("The cost to provide these services: $");
            sb.append(String.format("%,.2f", getTotalCommission()));
            sb.append("\nPlease see below for details:\n");
            List<Report> keyList = new ArrayList<>(reports.keySet());
            keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

            for (Report report : keyList) {
                double subtotal;
                if(type.isAudit()) { //Boolean check needed for Audit classes
                    subtotal = report.getCommission() * reports.get(report);
                } else {
                    subtotal = report.getCommission() * Math.min(type.getMaxCountedEmployees(), reports.get(report));
                }

                sb.append("\tReport name: ");
                sb.append(report.getReportName());
                sb.append("\tEmployee Count: ");
                sb.append(reports.get(report));
                sb.append("\tCost per employee: ");
                sb.append(String.format("$%,.2f", report.getCommission()));
                if(!type.isAudit()) {
                    if (reports.get(report) > type.getMaxCountedEmployees()) {
                        sb.append("\tThis report cost has been capped.");
                    }
                }
                sb.append("\tSubtotal: ");
                sb.append(String.format("$%,.2f\n", subtotal));
            }
            return sb.toString();
        }
    }

    @Override
    public int getClient() {
        return client;
    }

    @Override
    public void finalise() {
        this.finalised = true;
    }

    @Override
    public Order copy() {
        Order copy = new OrderNonScheduled(priority, type, id, client, date);
        for(Report report : reports.keySet()) {
            copy.setReport(report, reports.get(report));
        }
        //Bug fixed: copy of the Order is now finalised if original was
        if(finalised) {
            copy.finalise();
        }
        return copy;
    }

    @Override
    public String shortDesc() {
        return String.format("ID:%s $%,.2f", id, getTotalCommission());
    }

    /*
       Another method with multiple variations. A few Boolean checks were needed (like some previous methods) to make
       sure the final String for different Orders was constructed correctly.
     */
    @Override
    public String longDesc() {
        double baseCommission = 0.0;
        double loadedCommission = getTotalCommission();
        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(reports.keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for(Report report : keyList) {
            double subtotal;
            if(type.isAudit()) {
                subtotal = report.getCommission() * reports.get(report);
            } else {
                subtotal = report.getCommission() * Math.min(type.getMaxCountedEmployees(), reports.get(report));
            }
            baseCommission += subtotal;

            reportSB.append(String.format("\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f",
                    report.getReportName(),
                    reports.get(report),
                    report.getCommission(),
                    subtotal));
            if(!type.isAudit()) {
                if (reports.get(report) > type.getMaxCountedEmployees()) {
                    reportSB.append(" *CAPPED*\n");
                } else {
                    reportSB.append("\n");
                }
            } else {
                reportSB.append("\n");
            }
        }
        if(priority.isCritical()) { //Boolean check for critical orders, which have an extra line
            String r = String.format(finalised ? "" : "*NOT FINALISED*\n" +
                            "Order details (id #%d)\n" +
                            "Date: %s\n" +
                            "Reports:\n" +
                            "%s" +
                            "Critical Loading: $%,.2f\n" +
                            "Total cost: $%,.2f\n",
                    id,
                    date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    reportSB.toString(),
                    loadedCommission - baseCommission,
                    loadedCommission
            );
            return r;
        } else {
            String r = String.format(finalised ? "" : "*NOT FINALISED*\n" +
                            "Order details (id #%d)\n" +
                            "Date: %s\n" +
                            "Reports:\n" +
                            "%s" +
                            "Total cost: $%,.2f\n",
                    id,
                    date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    reportSB.toString(),
                    getTotalCommission()
            );
            return r;
        }
    }

    protected Map<Report, Integer> getReports() {
        return reports;
    }

    protected boolean isFinalised() {
        return finalised;
    }

    protected OrderType getOrderType() {
        return type;
    }

    protected OrderPriority getPriority() {
        return priority;
    }
}
