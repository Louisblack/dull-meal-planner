package com.louishoughton.mealplanner.service;

public class UserNotFoundException extends RuntimeException {
    private final String userGuid;

    public UserNotFoundException(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getMessage() {
        return String.format("There are no users with the GUID %s", userGuid);

    }
}
