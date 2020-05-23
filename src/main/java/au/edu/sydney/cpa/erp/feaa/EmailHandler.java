package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.Email;
import au.edu.sydney.cpa.erp.ordering.Client;

public class EmailHandler implements ContactMethod {

    private ContactMethod next;


    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String email = client.getEmailAddress();
        if (null != email) {
            Email.sendInvoice(token, client.getFName(), client.getLName(), data, email);
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
