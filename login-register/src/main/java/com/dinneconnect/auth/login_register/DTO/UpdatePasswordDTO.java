package com.dinneconnect.auth.login_register.DTO;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for updating a user's password.
 * This class is used to encapsulate the data required to update a password
 * for a user identified by a unique identifier (UUID).
 */
public class UpdatePasswordDTO {

    /**
     * The unique identifier of the user whose password is to be updated.
     * This field is marked as unused to suppress warnings, as it may not be
     * directly accessed outside of this class.
     */
    @SuppressWarnings("unused")
    private UUID id;

    /**
     * The new password for the user.
     */
    private String password;

    /**
     * Constructs a new UpdatePasswordDTO with the specified user ID and password.
     *
     * @param id       the unique identifier of the user
     * @param password the new password to be set for the user
     */
    public UpdatePasswordDTO(UUID id, String password) {
        this.id = id;
        this.password = password;
    }

    /**
     * Sets the password for the user.
     * Note: This method currently does not accept a parameter and will not
     * change the password as it stands. It should be modified to accept
     * a new password string to set the password correctly.
     */
    public void setPassword() {
        this.password = password;
    }

    /**
     * Retrieves the current password of the user.
     *
     * @return the current password
     */
    public String getPassword() {
        return this.password;
    }

}
