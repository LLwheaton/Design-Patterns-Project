// Modified class
package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.ordering.Client;
import java.util.Arrays;
import java.util.List;

/**
 * The main part of the Chain of Responsibility pattern. The chain is created here and the request (send invoice)
 * is sent down the chain until handled by one of the Handlers. The original code had a long switch statement in
 * this class, so was removed and organised to be more streamlined.
 */
public class ContactHandler {
    public static boolean sendInvoice(AuthToken token, Client client, List<ContactMethod> priority, String data) {
        //Initially link the chains here by looping through the list once
        for(int i = 0; i < priority.size() - 1; i++){
            priority.get(i).setNext(priority.get(i + 1));
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
