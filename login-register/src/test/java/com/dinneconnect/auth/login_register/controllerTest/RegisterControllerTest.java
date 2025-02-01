// /**
// * This class performs integration tests for the Register Controller.
// * The tests verify the functionality of the user registration endpoints,
// * handling cases such as correct registration, duplicate email, duplicate
// * username, and null email.
// *
// * The tests use Spring Boot Test and TestRestTemplate for setup and
// execution.
// * The @SpringBootTest annotation starts the application context for
// integration testing.
// * The @ActiveProfiles annotation sets the active profile to "test".
// *
// * Dependencies:
// * - Spring Boot
// * - JUnit 5
// * - TestRestTemplate
// *
// * Created by: sebas
// */
// package com.dinneconnect.auth.login_register.controllerTest;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.util.Map;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.context.ActiveProfiles;

// import com.dinneconnect.auth.login_register.DTO.RegisterDTO;
// import com.dinneconnect.auth.login_register.models.User;
// import com.dinneconnect.auth.login_register.services.UserService;

// /**
// * Integration tests for the Register Controller.
// */
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @ActiveProfiles("test")
// public class RegisterControllerTest {

// @Autowired
// private TestRestTemplate restTemplate;

// @Autowired
// private UserService userservice;

// private User user;
// private RegisterDTO userDTO;

// /**
// * Sets up the test environment before each test.
// * Deletes all existing users and creates a unique user for testing.
// */
// @BeforeEach
// public void setup() {
// userservice.getAllUsers().forEach(user ->
// userservice.deleteUserById(user.getId()));
// this.userDTO = new RegisterDTO("sebas", "aven", "save", "sebas@gamail.com",
// "123456782209");
// user = new User(this.userDTO);
// userservice.createUser(this.user);
// }

// /**
// * Tests correct user registration.
// * Verifies that the response status is CREATED.
// */
// @Test
// public void testCorrectRegister() {
// userDTO = new RegisterDTO("sebas2", "aven2", "save2", "sebas222@gamail.com",
// "1234567809");
// ResponseEntity<Map> response = restTemplate.postForEntity("/api/post-user/",
// this.userDTO, Map.class);

// assertEquals(HttpStatus.CREATED, response.getStatusCode());
// }

// /**
// * Tests user registration with a duplicate email.
// * Verifies that the response status is BAD_REQUEST.
// */
// @Test
// public void testDuplicateEmail() {
// userDTO = new RegisterDTO("sebas", "aven", "save1", "sebas@gamail.com",
// "123456782209");
// ResponseEntity<Map> response = restTemplate.postForEntity("/api/post-user/",
// userDTO, Map.class);

// assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
// }

// /**
// * Tests user registration with a duplicate username.
// * Verifies that the response status is BAD_REQUEST.
// */
// @Test
// public void testDuplicaUsername() {
// userDTO = new RegisterDTO("sebas", "aven", "save", "sebas12@gamail.com",
// "123456782209");
// ResponseEntity<Map> response = restTemplate.postForEntity("/api/post-user/",
// userDTO, Map.class);

// assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
// }

// /**
// * Tests user registration with a null email.
// * Verifies that the response status is BAD_REQUEST.
// */
// @Test
// public void testNullEmail() {
// userDTO = new RegisterDTO("sebas2", "aven2", "save21", "", "1234567809");
// ResponseEntity<Map> response = restTemplate.postForEntity("/api/post-user/",
// userDTO, Map.class);

// assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
// }
// }
