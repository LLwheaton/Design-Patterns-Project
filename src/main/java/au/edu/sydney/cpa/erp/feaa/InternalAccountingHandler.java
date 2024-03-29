// Added class
package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.InternalAccounting;
import au.edu.sydney.cpa.erp.ordering.Client;

/**
 * Implements ContactMethod interface. Used as one of the handlers for
 * Chain of Responsibility pattern for handling of client contact methods.
 */
public class InternalAccountingHandler implements ContactMethod {

    private ContactMethod next;

    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String internalAccounting = client.getInternalAccounting();
        String businessName = client.getBusinessName();
        if (null != internalAccounting && null != businessName) {
            InternalAccounting.sendInvoice(token, client.getFName(), client.getLName(), data, internalAccounting,businessName);
            return true;
        }
        if(next == null){
            return false;
        }
        return next.sendInvoice(token, client, data);
    }

    @Override
    public void setNext(ContactMethod next) {
        this.next = next;
    }
}
