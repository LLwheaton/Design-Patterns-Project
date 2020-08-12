// Modified class
package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.database.TestDatabase;
import au.edu.sydney.cpa.erp.ordering.Client;
import java.util.ArrayList;
import java.util.List;

/**
 * This class was implemented with Lazy Load pattern. Instead of pulling
 * all the data from the database at the start, the value is only initialised
 * when it is actually needed, which removes the lag in CLI when loading Client information.
 * To allow for values to be null, a list is maintained of values already loaded. This works
 * by the getter methods first checking this list - if it exists, return it, otherwise initialise
 * by pulling the value from the database, then adding it to the list.
 */
public class ClientImpl implements Client {

    private final int id;
    private final AuthToken token;
    private String fName;
    private String lName;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private String suburb;
    private String state;
    private String postCode;
    private String internalAccounting;
    private String businessName;
    private String pigeonCoopID;

    private final List<String> loadedValues = new ArrayList<>();

    public ClientImpl(AuthToken token, int id) {

        this.id = id;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getFName() {
        String value = "fName";
        if(!loadedValues.contains(value)) {
            this.fName = TestDatabase.getInstance().getClientField(token, id, "fName");
            loadedValues.add(value);
        }
        return fName;
    }

    @Override
    public String getLName() {
        String value = "lName";
        if(!loadedValues.contains(value)) {
            this.lName = TestDatabase.getInstance().getClientField(token, id, "lName");
            loadedValues.add(value);
        }
        return lName;
    }

    @Override
    public String getPhoneNumber() {
        String value = "phoneNumber";
        if(!loadedValues.contains(value)) {
            this.phoneNumber = TestDatabase.getInstance().getClientField(token, id, "phoneNumber");
            loadedValues.add(value);
        }
        return phoneNumber;
    }

    @Override
    public String getEmailAddress() {
        String value = "emailAddress";
        if(!loadedValues.contains(value)) {
            this.emailAddress = TestDatabase.getInstance().getClientField(token, id, "emailAddress");
            loadedValues.add(value);
        }
        return emailAddress;
    }

    @Override
    public String getAddress() {
        String value = "address";
        if(!loadedValues.contains(value)) {
            this.address = TestDatabase.getInstance().getClientField(token, id, "address");
            loadedValues.add(value);
        }
        return address;
    }

    @Override
    public String getSuburb() {
        String value = "suburb";
        if(!loadedValues.contains(value)) {
            this.suburb = TestDatabase.getInstance().getClientField(token, id, "suburb");
            loadedValues.add(value);
        }
        return suburb;
    }

    @Override
    public String getState() {
        String value = "state";
        if(!loadedValues.contains(value)) {
            this.state = TestDatabase.getInstance().getClientField(token, id, "state");
            loadedValues.add(value);
        }
        return state;
    }

    @Override
    public String getPostCode() {
        String value = "postCode";
        if(!loadedValues.contains(value)) {
            this.postCode = TestDatabase.getInstance().getClientField(token, id, "postCode");
            loadedValues.add(value);
        }
        return postCode;
    }

    @Override
    public String getInternalAccounting() {
        String value = "internal accounting";
        if(!loadedValues.contains(value)) {
            this.internalAccounting = TestDatabase.getInstance().getClientField(token, id, "internal accounting");
            loadedValues.add(value);
        }
        return internalAccounting;
    }

    @Override
    public String getBusinessName() {
        String value = "businessName";
        if(!loadedValues.contains(value)) {
            this.businessName = TestDatabase.getInstance().getClientField(token, id, "businessName");
            loadedValues.add(value);
        }
        return businessName;
    }

    @Override
    public String getPigeonCoopID() {
        String value = "pigeonCoopID";
        if(!loadedValues.contains(value)) {
            this.pigeonCoopID = TestDatabase.getInstance().getClientField(token, id, "pigeonCoopID");
            loadedValues.add(value);
        }
        return pigeonCoopID;
    }
}

