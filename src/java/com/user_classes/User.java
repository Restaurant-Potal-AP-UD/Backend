/**
 * This class performs all the User actions that will be on the web page
 * this simply make the actions to use into the application
 * 
 * @author Sebastian Avendano
 * @version 1.0
 * @since 2024-10-07
 */

package java.com.user_classes;

import java.time.*;
import java.util.List;

public class User implements IUser {

    public String name;
    public String surname;
    private String phoneNumber;
    private String email;
    private String password;
    private Instant creationDate;
    private Boolean activeReservation;
    private Boolean isActive;
    private Restaurant restaurant;
    private List<Booking> reservations;

    /**
     * This is the main constructor of the class, would receive the next params to
     * create an instance of an User class
     *
     * @param name        This is a String param that represents the name of the
     *                    user
     * @param surname     This is a String param that represents the surname of the
     *                    user
     * @param phoneNumber This is a String param that represents the name of the
     *                    user
     * @param email       This is a String param that represents the email of the
     *                    user
     * @param password    This is a String param that represents the password
     *                    un-hashed of the user
     */
    public User(String name, String surname, String phoneNumber, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.creationDate = Instant.now();
        this.activeReservation = false;
        this.isActive = false;
    }

    @Override
    /**
     * This mehtod will perform the actions to change a password. Indeed, the
     * password should be
     * validated
     * 
     * @implNote The password would be hashed, this implementation will be added in
     *           the next versions
     * 
     * @param rawPassword This param represents the new password to implement into
     *                    the user account
     * 
     * @return A simply string that will be telling us that everything gone correct
     */
    public void setNewPassword(String rawPassword) {
        if (this.password == rawPassword) {
            this.password = rawPassword;
        }
        System.out.println("You have been changed your password correctly");
    }

    @Override
    /**
     * This method will be used to change the user email
     * 
     * @implNote THe email should be validated
     * @param rawEmail This will represent the new email that the user will use
     * @return A simply string that will be telling us that everything gone correct
     */
    public void setNewEmail(String rawEmail) {
        if (this.email == rawEmail) {
            this.email = rawEmail;
        }
        System.out.println("You have been changed your email correctly");
    }

    @Override
    public void setNewUsername(String Username) {

    }

}
