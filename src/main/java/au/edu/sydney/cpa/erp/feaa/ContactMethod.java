// Modified from Enum to Interface
package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.ordering.Client;

/**
 * Originally ContactMethod was an Enum, but this was changed to an Interface for
 * different kinds of Handlers. This is part of the Chain of Responsibility pattern.
 */
public interface ContactMethod {

    /**
     * This method handles sending the Invoice. Any Handler will pass this request to the
     * next Handler if unable to process the request itself.
     * @param token Token implying successful login credentials
     * @param client Client to send the invoice to
     * @param data Data for the invoice
     * @return whether the request was successful
     */
    boolean sendInvoice(AuthToken token, Client client, String data);

    /**
     * Sets the next Chain link to handle the request
     * @param next link in the chain
     */
    void setNext(ContactMethod next);
}
