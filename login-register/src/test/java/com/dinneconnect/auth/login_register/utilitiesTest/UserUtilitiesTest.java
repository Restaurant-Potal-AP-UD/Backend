// /**
// * This class performs unit tests for the UserUtilities class.
// * The tests verify the functionality of methods such as:
// * - UserToUserDTO
// * - UsersToUserDTOs
// * - uuidToString
// * - uuidToBinary
// *
// * Dependencies:
// * - JUnit 5
// *
// * Created by: sebas
// */
// package com.dinneconnect.auth.login_register.utilitiesTest;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import java.nio.ByteBuffer;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.dinneconnect.auth.login_register.DTO.RegisterDTO;
// import com.dinneconnect.auth.login_register.DTO.UserResponseDTO;
// import com.dinneconnect.auth.login_register.models.User;
// import com.dinneconnect.auth.login_register.utilities.UserUtilities;

// /**
// * Unit tests for the UserUtilities class.
// */
// public class UserUtilitiesTest {

// private UserUtilities userUtilities;
// private User user;
// private List<User> users;
// private byte[] binaryGoodUUID;
// private String stringGoodUUID;

// /**
// * Sets up the test environment before each test.
// * Initializes the UserUtilities instance, sample users, and UUIDs for
// testing.
// */
// @BeforeEach
// public void setUp() {
// userUtilities = new UserUtilities();

// RegisterDTO registerUser1 = new RegisterDTO("Sebas", "Avendano", "SeAv",
// "asgdsg@gmail.com", "123456789");
// RegisterDTO registerUser2 = new RegisterDTO("Sessbas", "Avenano", "SeAva",
// "asgd2sg@gmail.com", "1234567890");
// this.user = new User(registerUser1);

// ArrayList<User> usersDto = new ArrayList<>();
// usersDto.add(this.user);
// usersDto.add(new User(registerUser2));

// this.users = usersDto;

// // Generate a random UUID and obtain its bytes
// this.stringGoodUUID = UUID.randomUUID().toString();
// this.binaryGoodUUID = uuidToBinary(stringGoodUUID);
// }

// /**
// * Converts a UUID string to a byte array.
// *
// * @param uuid the UUID string
// * @return the byte array representation of the UUID
// */
// private byte[] uuidToBinary(String uuid) {
// UUID u = UUID.fromString(uuid);
// ByteBuffer bb = ByteBuffer.allocate(16);
// bb.putLong(u.getMostSignificantBits());
// bb.putLong(u.getLeastSignificantBits());
// return bb.array();
// }

// /**
// * Tests the UserToUserDTO method.
// * Verifies that the conversion from User to UserResponseDTO is correct.
// */
// @Test
// public void UserToUserDTOTest() {
// UserResponseDTO userDTO = userUtilities.UserToUserDTO(this.user);
// assertNotNull(userDTO, "The UserResponseDTO should not be null.");
// assertEquals(this.user.getName(), userDTO.getName(), "Names should match.");
// assertEquals(this.user.getSurname(), userDTO.getSurname(), "Surnames should
// match.");
// assertEquals(this.user.getUsername(), userDTO.getUsername(), "Usernames
// should match.");
// assertEquals(this.user.getEmail(), userDTO.getEmail(), "Emails should
// match.");
// }

// /**
// * Tests the UsersToUserDTOs method.
// * Verifies that the conversion from a list of Users to a list of
// * UserResponseDTOs is correct.
// */
// @Test
// public void UsersToUserDTOsTest() {
// List<UserResponseDTO> userDTOs = userUtilities.UsersToUserDTOs(this.users);

// for (int i = 0; i < this.users.size(); i++) {
// assertEquals(this.users.get(i).getName(), userDTOs.get(i).getName(), "Names
// should match.");
// assertEquals(this.users.get(i).getSurname(), userDTOs.get(i).getSurname(),
// "Surnames should match.");
// assertEquals(this.users.get(i).getUsername(), userDTOs.get(i).getUsername(),
// "Usernames should match.");
// assertEquals(this.users.get(i).getEmail(), userDTOs.get(i).getEmail(),
// "Emails should match.");
// }
// }

// /**
// * Tests the uuidToString method.
// * Verifies that the conversion from a binary UUID to a string is correct.
// */
// @Test
// public void uuidToStringTest() {
// String convertedUUID = userUtilities.uuidToString(this.binaryGoodUUID);
// assertNotNull(convertedUUID, "The converted UUID string should not be
// null.");
// assertEquals(this.stringGoodUUID, convertedUUID, "The UUID string should
// match the original.");
// }

// /**
// * Tests the uuidToBinary method.
// * Verifies that the conversion from a UUID string to a binary array is
// correct.
// */
// @Test
// public void uuidToBinaryTest() {
// byte[] convertedBinaryUUID = userUtilities.uuidToBinary(this.stringGoodUUID);
// assertNotNull(convertedBinaryUUID, "The converted binary UUID should not be
// null.");
// assertEquals(this.binaryGoodUUID.length, convertedBinaryUUID.length,
// "The length of the binary UUID should match.");

// for (int i = 0; i < this.binaryGoodUUID.length; i++) {
// assertEquals(this.binaryGoodUUID[i], convertedBinaryUUID[i], "Each byte
// should match.");
// }
// }
// }
