package de.generosoft.mobileComputingBackend.rest.subscription;

public class SubscriptionBody {

    private String email;
    private String password;
    private String classRoomName;

    public SubscriptionBody() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getClassRoomName() {
        return classRoomName;
    }
}
