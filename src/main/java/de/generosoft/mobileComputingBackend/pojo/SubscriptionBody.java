package de.generosoft.mobileComputingBackend.pojo;

import org.jetbrains.annotations.NotNull;

public class SubscriptionBody extends Credentials {

    private @NotNull String classRoomName;

    public SubscriptionBody() {
    }

    public SubscriptionBody(final @NotNull String email, final @NotNull String password, final @NotNull String classRoomName) {
        super(email, password);
        this.classRoomName = classRoomName;
    }

    public @NotNull String getClassRoomName() {
        return classRoomName;
    }

}
