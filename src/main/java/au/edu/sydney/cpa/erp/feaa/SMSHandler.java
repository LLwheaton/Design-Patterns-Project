// Added class
package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.SMS;
import au.edu.sydney.cpa.erp.ordering.Client;

/**
 * Implements ContactMethod interface. Used as one of the handlers for
 * Chain of Responsibility pattern for handling of client contact methods.
 */
public class SMSHandler implements ContactMethod {

    private ContactMethod next;

    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String smsPhone = client.getPhoneNumber();
        if(smsPhone != null) {
            SMS.sendInvoice(token, client.getFName(), client.getLName(), data, smsPhone);
            return true;
        }
        if (null == next) {
            return false;
        }
        return next.sendInvoice(token, client, data);
    }

    @Override
    public void setNext(ContactMethod next) {
        this.next = next;
    }
}
