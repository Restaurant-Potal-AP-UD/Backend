package com.dinneconnect.auth.login_register.utilities;

import java.util.ArrayList;
import java.util.List;

import com.dinneconnect.auth.login_register.DTO.UserRequestDTO;
import com.dinneconnect.auth.login_register.models.User;

public class UserUtilities {

    public UserRequestDTO UserToUserDTO(User user) {
        return new UserRequestDTO(user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getCreationDate().toString(),
                user.getVerified(),
                user.getReservation(),
                user.getRestaurantIds());
    }

    public List<UserRequestDTO> UsersToUserDTOs(List<User> list_users) {
        List<UserRequestDTO> users = new ArrayList<>();

        for (User item : list_users) {
            users.add(UserToUserDTO(item));
        }
        return users;
    }
}
