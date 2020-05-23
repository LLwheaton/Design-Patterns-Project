package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.ordering.Client;

public interface ContactMethod {
//    CARRIER_PIGEON,
//    EMAIL,
//    MAIL,
//    INTERNAL_ACCOUNTING,
//    PHONECALL,
//    SMS
boolean sendInvoice(AuthToken token, Client client, String data);
void setNext(ContactMethod next);
}
