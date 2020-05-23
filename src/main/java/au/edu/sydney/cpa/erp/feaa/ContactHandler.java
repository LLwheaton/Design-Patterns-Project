package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.*;
import au.edu.sydney.cpa.erp.ordering.Client;

import java.util.Arrays;
import java.util.List;

public class ContactHandler {
    public static boolean sendInvoice(AuthToken token, Client client, List<ContactMethod> priority, String data) {
        //Initially link the chains here by looping through the list once
        for(int i = 0; i < priority.size() - 1; i++){
            priority.get(i).setNext(priority.get(i+1));
        }
        //Call the beginning of the chain to handle the invoice
        return priority.get(0).sendInvoice(token, client, data);
    }
    public static List<String> getKnownMethods() {
        return Arrays.asList(
                "Carrier Pigeon",
                "Email",
                "Mail",
                "Internal Accounting",
                "Phone call",
                "SMS"
        );
    }
}
