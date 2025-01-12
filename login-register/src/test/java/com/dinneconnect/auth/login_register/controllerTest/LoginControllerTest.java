/**
 * This class performs integration tests for the Login Controller.
 * The tests verify the functionality of the login, user information update,
 * and user deletion endpoints.
 * 
 * The tests use Spring Boot Test, TestRestTemplate, and MockMvc for setup and execution.
 * The @SpringBootTest annotation starts the application context for integration testing.
 * The @AutoConfigureMockMvc annotation configures MockMvc for the tests.
 * The @ActiveProfiles annotation sets the active profile to "test".
 * 
 * Dependencies:
 * - Spring Boot
 * - JUnit 5
 * - TestRestTemplate
 * 
 * Created by: Sebastian Avendaño Rodriguez
 */
package com.dinneconnect.auth.login_register.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.dinneconnect.auth.login_register.DTO.LoginResponseDTO;
import com.dinneconnect.auth.login_register.DTO.RegisterDTO;
import com.dinneconnect.auth.login_register.DTO.UpdatePrimaryInfoDTO;
import com.dinneconnect.auth.login_register.models.User;
import com.dinneconnect.auth.login_register.services.UserService;

/**
 * Integration tests for the Login Controller.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginControllerTest {

        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
        private UserService userservice;

        public LoginResponseDTO tokenGood;
        private RegisterDTO userDTO;
        private UpdatePrimaryInfoDTO updt;

        /**
         * Sets up the test environment before each test.
         * Creates a unique user and obtains a valid authorization token.
         */
        @BeforeEach
        public void setup() {

                String uniqueEmail = "test" + System.currentTimeMillis() + "@gmail.com";
                String uniqueUsername = "test" + System.currentTimeMillis();

                userDTO = new RegisterDTO(
                                "sebas",
                                "aven",
                                uniqueUsername,
                                uniqueEmail,
                                "123456782209");

                userservice.createUser(new User(userDTO));
                tokenGood = new LoginResponseDTO(userservice.getUserByEmail(uniqueEmail).getId(),
                                userDTO.getUsername());
        }

        /**
         * Tests a successful user login and retrieves user information.
         * Verifies that the response status is OK.
         */
        @Test
        public void goodUserTest() {

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + this.tokenGood.getValue().get("token"));
                System.out.println("Authorization Token: " + this.tokenGood.getValue().get("token"));

                HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
                System.out.println("Request Entity: " + requestEntity);

                ResponseEntity<Map> response = restTemplate.exchange(
                                "/api/user/",
                                HttpMethod.GET,
                                requestEntity,
                                Map.class);

                assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        /**
         * Tests an unauthorized user login attempt with an invalid token.
         * Verifies that the response status is INTERNAL_SERVER_ERROR.
         */
        @Test
        public void badUserTest() {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization",
                                "eyJhbGciOiJIUzI1NIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0ZmUxNjAzOC1jODFhLTRlM2EtYTljMi0yMDdmNGExY2M3ZTMiLCJleHAiOjk5OTk5OTk5OTksImlhdCI6MTcwMDAwMDAwMH0.SudHBpuBEuC0fX4POEzV0azF4dNIZOlnJJEYgBYLIUg");
                HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

                ResponseEntity<Map> response = restTemplate.exchange(
                                "/api/user/",
                                HttpMethod.GET,
                                requestEntity,
                                Map.class);

                assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        }

        /**
         * Tests updating user primary information.
         * Verifies that the response status is OK.
         */
        @Test
        public void userInfoChangeGood() {
                this.updt = new UpdatePrimaryInfoDTO("Sebastian", "Avendaño", "perra.com", "holauwu@gmail.com");
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + this.tokenGood.getValue().get("token"));

                HttpEntity<UpdatePrimaryInfoDTO> requestEntity = new HttpEntity<>(updt, headers);

                ResponseEntity<String> response = restTemplate.exchange(
                                "/api/user/primary/",
                                HttpMethod.POST,
                                requestEntity,
                                String.class);

                assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        /**
         * Tests updating user primary information with wrong user UUID.
         * Verifies that the response status is BAD REQUEST.
         */
        @Test
        public void userInfoChangeBad() {
                this.updt = new UpdatePrimaryInfoDTO("Sebastian", "Avendaño", "perra.com", "holauwu@gmail.com");
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization",
                                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlM2NkZmM4Yi0yN2VmLTQ4YzctOGJlYi04MmY3NjZhM2UzZmMiLCJpYXQiOjE2OTM1NjYwMjUsImV4cCI6Nzc3NTYwMDAyNX0.KIhq13nlJyw2Tw77LHu-p2Q4cUwVinF-B54v6EdOiq0");

                HttpEntity<UpdatePrimaryInfoDTO> requestEntity = new HttpEntity<>(updt, headers);

                ResponseEntity<String> response = restTemplate.exchange(
                                "/api/user/primary/",
                                HttpMethod.POST,
                                requestEntity,
                                String.class);

                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        /**
         * Tests changing the user password.
         * Verifies that the response status is OK.
         */
        @Test
        public void userInfoChangePasswordGood() {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + this.tokenGood.getValue().get("token"));

                HttpEntity<String> requestEntity = new HttpEntity<>("sdgakshdasd", headers);

                ResponseEntity<String> response = restTemplate.exchange(
                                "/api/user/password/",
                                HttpMethod.POST,
                                requestEntity,
                                String.class);

                assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        /**
         * Tests changing the user password with non-existing User UUID.
         * Verifies that the response status is Bad Request.
         */
        @Test
        public void userInfoChangePasswordBad() {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization",
                                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlM2NkZmM4Yi0yN2VmLTQ4YzctOGJlYi04MmY3NjZhM2UzZmMiLCJpYXQiOjE2OTM1NjYwMjUsImV4cCI6Nzc3NTYwMDAyNX0.KIhq13nlJyw2Tw77LHu-p2Q4cUwVinF-B54v6EdOiq0");

                HttpEntity<String> requestEntity = new HttpEntity<>("sdgakshdasd", headers);

                ResponseEntity<String> response = restTemplate.exchange(
                                "/api/user/password/",
                                HttpMethod.POST,
                                requestEntity,
                                String.class);

                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        /**
         * Tests deleting a user account.
         * Verifies that the response status is OK.
         */
        @Test
        public void userDelete() {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + this.tokenGood.getValue().get("token"));

                HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

                ResponseEntity<String> response = restTemplate.exchange(
                                "/api/user/",
                                HttpMethod.DELETE,
                                requestEntity,
                                String.class);

                assertEquals(HttpStatus.OK, response.getStatusCode());
        }
}
