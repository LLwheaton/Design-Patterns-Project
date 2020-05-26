package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.CarrierPigeon;
import au.edu.sydney.cpa.erp.ordering.Client;

/**
 * Implements ContactMethod interface. Used as one of the handlers for
 * Chain of Responsibility pattern for handling of client contact methods.
 */
public class CarrierPidgeonHandler implements ContactMethod {

    private ContactMethod next;

    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String pigeonCoopID = client.getPigeonCoopID();
        if (null != pigeonCoopID) {
            CarrierPigeon.sendInvoice(token, client.getFName(), client.getLName(), data, pigeonCoopID);
            return true;
        }
        if(next == null) {
            return false;
        }
        return next.sendInvoice(token, client, data);
    }

    @Override
    public void setNext(ContactMethod next) {
        this.next = next;
    }
}
