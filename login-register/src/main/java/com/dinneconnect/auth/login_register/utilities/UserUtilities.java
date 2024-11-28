package com.dinneconnect.auth.login_register.utilities;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.dinneconnect.auth.login_register.DTO.UserResponseDTO;
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
    public UserResponseDTO UserToUserDTO(User user) {
        return new UserResponseDTO(user.getId(),
                user.getName(),
                user.getSurname(),
                user.getUsername(),
                user.getEmail(),
                user.getCreationDate().toString(),
                user.getVerified(),
                user.getReservation());
    }

    /**
     * Converts a list of User entities to a list of UserRequestDTOs.
     * 
     * @param list_users the list of user entities to convert
     * @return a list of corresponding UserRequestDTOs
     */
    public List<UserResponseDTO> UsersToUserDTOs(List<User> list_users) {
        List<UserResponseDTO> users = new ArrayList<>();

        for (User item : list_users) {
            users.add(UserToUserDTO(item));
        }
        return users;
    }

    public String uuidToString(byte[] binaryUUID) {
        ByteBuffer bb = ByteBuffer.wrap(binaryUUID);
        long mostSigBits = bb.getLong();
        long leastSigBits = bb.getLong();
        return new UUID(mostSigBits, leastSigBits).toString();
    }

    public byte[] uuidToBinary(String uuid) {
        UUID u = UUID.fromString(uuid);
        ByteBuffer bb = ByteBuffer.allocate(16);
        bb.putLong(u.getMostSignificantBits());
        bb.putLong(u.getLeastSignificantBits());
        return bb.array();
    }

}
