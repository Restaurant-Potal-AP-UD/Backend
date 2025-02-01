package com.dinneconnect.auth.login_register.utilities;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.dinneconnect.auth.login_register.DTO.UserResponseDTO;

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
    public UserResponseDTO UserToUserDTO(UserResponseDTO user) {
        return new UserResponseDTO(
                user.getCode(),
                user.getName(),
                user.getSurname(),
                user.getUsername(),
                user.getEmail(),
                user.getCreationDate());
    }

    /**
     * Converts a list of User entities to a list of UserRequestDTOs.
     * 
     * @param list_users the list of user entities to convert
     * @return a list of corresponding UserRequestDTOs
     */
    public List<UserResponseDTO> UsersToUserDTOs(List<UserResponseDTO> list_users) {
        List<UserResponseDTO> users = new ArrayList<>();

        for (UserResponseDTO item : list_users) {
            System.out.println(item);
            users.add(UserToUserDTO(new UserResponseDTO(
                    item.getCode(),
                    item.getName(),
                    item.getSurname(),
                    item.getUsername(),
                    item.getEmail(),
                    item.getCreationDate())));
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
