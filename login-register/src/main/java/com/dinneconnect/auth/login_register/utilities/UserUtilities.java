package com.dinneconnect.auth.login_register.utilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dinneconnect.auth.login_register.DTO.UserRequestDTO;
import com.dinneconnect.auth.login_register.models.User;

/**
 * Utility class for converting User entities to UserRequestDTOs.
 * 
 * @author Sebastian Avendaño Rodriguez
 * @since 2024/11/03
 * @version 1.0
 */
@Component
public class UserUtilities {

    /**
     * Converts a User entity to a UserRequestDTO.
     * 
     * @param user the user entity to convert
     * @return the corresponding UserRequestDTO
     */
    public UserRequestDTO UserToUserDTO(User user) {
        return new UserRequestDTO(user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getCreationDate().toString(),
                user.getVerified(),
                user.getReservation(),
                user.getRestaurantIds());
    }

    /**
     * Converts a list of User entities to a list of UserRequestDTOs.
     * 
     * @param list_users the list of user entities to convert
     * @return a list of corresponding UserRequestDTOs
     */
    public List<UserRequestDTO> UsersToUserDTOs(List<User> list_users) {
        List<UserRequestDTO> users = new ArrayList<>();

        for (User item : list_users) {
            users.add(UserToUserDTO(item));
        }
        return users;
    }
}
