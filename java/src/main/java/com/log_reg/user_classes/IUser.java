/**
 * This class works as a blueprint for the actual User class
 * It contains the basic attributes and methods that will be used in the User
 * class
 * 
 * @author Sebastian Avendano
 * @version 1.0
 * @since 2024-10-07
 */
package com.log_reg.user_classes;

public interface IUser {
    public abstract void setNewPassword(String rawPassword);

    public abstract void setNewEmail(String rawEmail);

    public abstract void setNewUsername(String Username);
}