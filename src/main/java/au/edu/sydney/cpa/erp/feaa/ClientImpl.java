package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.database.TestDatabase;
import au.edu.sydney.cpa.erp.ordering.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private List<String> loadedValues = new ArrayList<>();

    //Lazy load, initialising all values in constructor was removed
    //Now each getter initialises the value in order to return it, so
    //it only gets initialised when it's actually needed, instead of all
    //at the start
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

