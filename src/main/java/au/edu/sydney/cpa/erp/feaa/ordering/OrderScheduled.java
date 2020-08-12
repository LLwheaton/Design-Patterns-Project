// Added class
package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;
import au.edu.sydney.cpa.erp.ordering.ScheduledOrder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Implements the ScheduledOrder interface, and also inherits OrderNonScheduled. Several methods
 * are overridden from the Parent class to account for the differences between Scheduled and NonScheduled Orders.
 * This is a big improvement over the original application because there is now only one Scheduled class, and only
 * one NonScheduled class. This is part of the Bridge pattern.
 */
public class OrderScheduled extends OrderNonScheduled implements ScheduledOrder {

    private int numQuarters;

    public OrderScheduled(OrderPriority priority, OrderType type, int id, int client, LocalDateTime date, int numQuarters) {
        super(priority, type, id, client, date);
        this.numQuarters = numQuarters;
    }

    @Override
    public double getRecurringCost() {
        return super.getTotalCommission();
    }

    /*
       Unique to Scheduled
     */
    @Override
    public int getNumberOfQuarters() {
        return numQuarters;
    }

    /*
       Total commission for Scheduled Orders include numQuarters
     */
    @Override
    public double getTotalCommission() {
        return super.getTotalCommission() * numQuarters;
    }

    /*
       Different variations of this method existed, and is fixed using some Boolean checks.
     */
    @Override
    public String generateInvoiceData() {

        if(getPriority().isCritical()) { //Critical Orders return a smaller String
            return String.format("Your priority business account will be charged: $%,.2f each quarter for %d quarters, with a total overall cost of: $%,.2f" +
                    "\nPlease see your internal accounting department for itemised details.", getRecurringCost(), getNumberOfQuarters(), getTotalCommission());
        } else {
            StringBuilder sb = new StringBuilder();

            sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
            sb.append("The cost to provide these services: $");
            sb.append(String.format("%,.2f", getRecurringCost()));
            sb.append(" each quarter, with a total overall cost of: $");
            sb.append(String.format("%,.2f", getTotalCommission()));
            sb.append("\nPlease see below for details:\n");

            Map<Report, Integer> reports = getReports();
            List<Report> keyList = new ArrayList<>(reports.keySet());
            keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

            for (Report report : keyList) {
                sb.append("\tReport name: ");
                sb.append(report.getReportName());
                sb.append("\tEmployee Count: ");
                sb.append(reports.get(report));
                sb.append("\tCost per employee: ");
                sb.append(String.format("$%,.2f", report.getCommission()));
                if(!getOrderType().isAudit()) { //Boolean check needed for Audits
                    if (reports.get(report) > getOrderType().getMaxCountedEmployees()) {
                        sb.append("\tThis report cost has been capped.");
                    }
                }
                sb.append("\tSubtotal: ");
                sb.append(String.format("$%,.2f\n", report.getCommission() * reports.get(report)));
            }

            return sb.toString();
        }

    }

    @Override
    public Order copy() {
        Map<Report, Integer> products = super.getReports();

        Order copy = new OrderScheduled(getPriority(), getOrderType(), getOrderID(), getClient(), getOrderDate(), numQuarters);
        for (Report report : products.keySet()) {
            copy.setReport(report, products.get(report));
        }
        return copy;
    }

    @Override
    public String shortDesc() {
        return String.format("ID:%s $%,.2f per quarter, $%,.2f total", super.getOrderID(), getRecurringCost(), getTotalCommission());
    }

    /*
       Multiple variations of this method existed, so Boolean checks were needed to ensure String
       was correctly generated.
     */
    @Override
    public String longDesc() {

        double totalBaseCost = 0.0;
        double loadedCostPerQuarter = super.getTotalCommission();
        double totalLoadedCost = this.getTotalCommission();
        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(super.getReports().keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for (Report report : keyList) {
            double subtotal;
            if(getOrderType().isAudit()) { //Boolean check for Audits
                subtotal = report.getCommission() * super.getReports().get(report);

            } else {
                subtotal = report.getCommission() * Math.min(getOrderType().getMaxCountedEmployees(), super.getReports().get(report));
            }
            totalBaseCost += subtotal;

            reportSB.append(String.format("\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f",
                    report.getReportName(),
                    super.getReports().get(report),
                    report.getCommission(),
                    subtotal));


            if(!getOrderType().isAudit()) {
                if (super.getReports().get(report) > getOrderType().getMaxCountedEmployees()) {
                    reportSB.append(" *CAPPED*\n");
                } else {
                    reportSB.append("\n");
                }
            } else {
                reportSB.append("\n");
            }
        }

        if(getPriority().isCritical()){
            String r = String.format(super.isFinalised() ? "" : "*NOT FINALISED*\n" +
                            "Order details (id #%d)\n" +
                            "Date: %s\n" +
                            "Number of quarters: %d\n" +
                            "Reports:\n" +
                            "%s" +
                            "Critical Loading: $%,.2f\n" +
                            "Recurring cost: $%,.2f\n" +
                            "Total cost: $%,.2f\n",
                    super.getOrderID(),
                    super.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    numQuarters,
                    reportSB.toString(),
                    totalLoadedCost - (totalBaseCost * numQuarters),
                    loadedCostPerQuarter,
                    totalLoadedCost
            );
            return r;
        } else {
            String r =  String.format(super.isFinalised() ? "" : "*NOT FINALISED*\n" +
                            "Order details (id #%d)\n" +
                            "Date: %s\n" +
                            "Number of quarters: %d\n" +
                            "Reports:\n" +
                            "%s" +
                            "Recurring cost: $%,.2f\n" +
                            "Total cost: $%,.2f\n",
                    super.getOrderID(),
                    super.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    numQuarters,
                    reportSB.toString(),
                    super.getTotalCommission(),
                    this.getTotalCommission()

            );
            return r;
        }
    }
}
