package com.rivelles.guesswho.domain.model.session;

public record UserIdentifier(String ipAddress) {
    public UserIdentifier {
        if (!ipAddress.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$"))
            throw new RuntimeException("IP address format is wrong");
    }
}
