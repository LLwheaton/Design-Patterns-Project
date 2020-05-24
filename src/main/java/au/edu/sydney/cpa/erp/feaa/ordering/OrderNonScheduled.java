package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderNonScheduled implements Order {
    private Map<Report, Integer> reports = new HashMap<>();
    private final OrderPriority priority;
    private final OrderType type;
    private final int id;
    private int client;
    private LocalDateTime date;
    private boolean finalised = false;

    public OrderNonScheduled(OrderPriority priority, OrderType type, int id, int client, LocalDateTime date){
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

    @Override
    public void setReport(Report report, int employeeCount) {
        if (finalised) throw new IllegalStateException("Order was already finalised.");

        // We can't rely on equal reports having the same object identity since they get
        // rebuilt over the network, so we have to check for presence and same values

        //Removed long check for equality and replaced with equals method
//        for (Report contained: reports.keySet()) {
//            if (contained.equals(report)) {
//                report = contained;
//                break;
//            }
//        }

        //Reports are Value Objects, so two of the same report will have the same hash value, thus the equality
        //check is no longer needed, since put() will just update the value (if key is equal), or add if it's new.

        reports.put(report, employeeCount);
    }

    @Override
    public Set<Report> getAllReports() {
        return reports.keySet();
    }

    @Override
    public int getReportEmployeeCount(Report report) {
        // We can't rely on equal reports having the same object identity since they get
        // rebuilt over the network, so we have to check for presence and same values

        //Removed long check for equality and replaced with equals method
//        for (Report contained: reports.keySet()) {
//            if (contained.equals(report)) {
//                report = contained;
//                break;
//            }
//        }
        //Again, since report is a value object, equality check can be taken out since in a map
        //reports with the same hash code (reports are equal to eachother) will just update the value
        //otherwise add if new
        Integer result = reports.get(report);
        return null == result ? 0 : result;
    }

    @Override
    public String generateInvoiceData() {
        if(priority.isCritical()){
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
                if(type.isAudit()){
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
                if(!type.isAudit()){
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
        //Do I need to copy priority and type differently?
        Order copy = new OrderNonScheduled(priority, type, id, client, date); //todo **********************
        for(Report report : reports.keySet()){
            copy.setReport(report, reports.get(report));
        }
        return copy;
    }

    @Override
    public String shortDesc() {
        return String.format("ID:%s $%,.2f", id, getTotalCommission());
    }

    @Override
    public String longDesc() {
        double baseCommission = 0.0;
        double loadedCommission = getTotalCommission();
        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(reports.keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for(Report report : keyList){
            double subtotal;
            if(type.isAudit()){
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
            if(!type.isAudit()){ //This is to avoid instanceof
                if (reports.get(report) > type.getMaxCountedEmployees()) {
                    reportSB.append(" *CAPPED*\n");
                } else {
                    reportSB.append("\n");
                }
            } else {
                reportSB.append("\n");
            }

        }
        if(priority.isCritical()){
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
//            System.out.println(r);
//            System.out.println("priority: " + priority.toString());
//            System.out.println("type: " + type.toString());
//            System.out.println("max employees: " + type.getMaxCountedEmployees());
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
//            System.out.println(r);
//            System.out.println("priority: " + priority.toString());
//            System.out.println("type: " + type.toString());
//            System.out.println("max employees: " + type.getMaxCountedEmployees());
            return r;
        }
    }

    protected Map<Report, Integer> getReports() {
        return reports;
    }

    protected boolean isFinalised() {
        return finalised;
    }

    protected OrderType getOrderType(){
        return type;
    }

    protected OrderPriority getPriority(){
        return priority;
    }
}
