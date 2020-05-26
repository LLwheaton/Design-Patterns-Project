package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthModule;
import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.database.TestDatabase;
import au.edu.sydney.cpa.erp.ordering.Client;
import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;
import au.edu.sydney.cpa.erp.feaa.ordering.*;
import au.edu.sydney.cpa.erp.feaa.reports.ReportDatabase;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("Duplicates")
public class FEAAFacade {
    private AuthToken token;
    private UnitOfWork uow;

    public boolean login(String userName, String password) {
        token = AuthModule.login(userName, password);

        uow = new UnitOfWork(token);

        return null != token;
    }

    public List<Integer> getAllOrders() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();

        List<Order> orders = database.getOrders(token);

        List<Integer> result = new ArrayList<>();

        for (Order order : orders) {
            result.add(order.getOrderID());
        }

        return result;
    }

    public Integer createOrder(int clientID, LocalDateTime date, boolean isCritical, boolean isScheduled, int orderType, int criticalLoadingRaw, int maxCountedEmployees, int numQuarters) {
        if (null == token) {
            throw new SecurityException();
        }

        double criticalLoading = criticalLoadingRaw / 100.0;

        Order order;

        if (!TestDatabase.getInstance().getClientIDs(token).contains(clientID)) {
            throw new IllegalArgumentException("Invalid client ID");
        }

        int id = TestDatabase.getInstance().getNextOrderID();

        OrderPriority priority;
        OrderType type;
        if(isCritical) {
            priority = new CriticalPriority(criticalLoading);
        } else {
            priority = new NormalPriority();
        }

        if(1 == orderType) {
            type = new RegularAccounting(maxCountedEmployees);
        } else if (2 == orderType) {
            type = new Audit();
        } else {
            return null;
        }

        if (isScheduled) {
            order = new OrderScheduled(priority, type, id, clientID, date, numQuarters);
        } else {
            order = new OrderNonScheduled(priority, type, id, clientID, date);
        }

        TestDatabase.getInstance().saveOrder(token, order);
        return order.getOrderID();
    }

    public List<Integer> getAllClientIDs() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.getClientIDs(token);
    }

    public Client getClient(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        return new ClientImpl(token, id);
    }

    public boolean removeOrder(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.removeOrder(token, id);
    }

    public List<Report> getAllReports() {
        if (null == token) {
            throw new SecurityException();
        }

        //Added, will need to remove before final submission**************************************
        long t = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("FEAAFacade.getAllReports ram used: " + t/1024/1024); //MB

        return new ArrayList<>(ReportDatabase.getTestReports());
    }

    /*
       The switch case in this method originally used Enums, this was changed to create
       new Handlers for the Chain of Responsibility pattern.
     */
    public boolean finaliseOrder(int orderID, List<String> contactPriority) {
        if (null == token) {
            throw new SecurityException();
        }

        List<ContactMethod> contactPriorityAsMethods = new ArrayList<>();

        if (null != contactPriority) {
            for (String method: contactPriority) {
                switch (method.toLowerCase()) {
                    case "internal accounting":
                        contactPriorityAsMethods.add(new InternalAccountingHandler());
                        break;
                    case "email":
                        contactPriorityAsMethods.add(new EmailHandler());
                        break;
                    case "carrier pigeon":
                        contactPriorityAsMethods.add(new CarrierPidgeonHandler());
                        break;
                    case "mail":
                        contactPriorityAsMethods.add(new MailHandler());
                        break;
                    case "phone call":
                        contactPriorityAsMethods.add(new PhoneCallHandler());
                        break;
                    case "sms":
                        contactPriorityAsMethods.add(new SMSHandler());
                        break;
                    default:
                        break;
                }
            }
        }

        /*
           If the list is size 0, it needs to be set to default contact methods
           for the client. Originally there were Enums here but they are now replaced
           with the created of new Handlers for the Chain of Responsibility pattern.
         */
        if (contactPriorityAsMethods.size() == 0) {
            contactPriorityAsMethods = Arrays.asList(
                    new InternalAccountingHandler(),
                    new EmailHandler(),
                    new CarrierPidgeonHandler(),
                    new MailHandler(),
                    new PhoneCallHandler()
            );
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        order.finalise();
        TestDatabase.getInstance().saveOrder(token, order);
        return ContactHandler.sendInvoice(token, getClient(order.getClient()), contactPriorityAsMethods, order.generateInvoiceData());
    }

    public void logout() {
        AuthModule.logout(token);
        token = null;
    }

    public double getOrderTotalCommission(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        if (null == order) {
            return 0.0;
        }

        return order.getTotalCommission();
    }

    public void orderLineSet(int orderID, Report report, int numEmployees) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            System.out.println("got here");
            return;
        }

        order.setReport(report, numEmployees);

        TestDatabase.getInstance().saveOrder(token, order);
    }

    public String getOrderLongDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return null;
        }

        return order.longDesc();
    }

    public String getOrderShortDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return null;
        }

        return order.shortDesc();
    }

    public List<String> getKnownContactMethods() {
        if (null == token) {
            throw new SecurityException();
        }
        return ContactHandler.getKnownMethods();
    }
}
