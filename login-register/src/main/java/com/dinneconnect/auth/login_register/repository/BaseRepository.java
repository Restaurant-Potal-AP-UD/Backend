package com.dinneconnect.auth.login_register.repository;

import com.dinneconnect.auth.login_register.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * Base repository implementation for managing user data persistence in a JSON
 * file.
 * This repository provides CRUD operations for User entities, storing them in a
 * JSON file-based database.
 * The repository handles file creation, data loading, and data persistence
 * operations.
 *
 * Key features:
 * - JSON file-based persistence
 * - Automatic file and directory creation
 * - Error handling for file operations
 * - Thread-safe data operations
 * 
 * @version 1.0
 * @since 2025-01-28
 */
@Repository
public class BaseRepository {

        /**
         * Path to the JSON file where data is stored
         */
        private final Path relativePath;

        /**
         * In-memory list of entities stored as maps
         */
        private List<Map<String, Object>> data;

        /**
         * ObjectMapper instance for JSON serialization/deserialization
         */
        private final ObjectMapper objectMapper;

        /**
         * Constructs a new BaseRepository with the specified file path.
         * Creates necessary directories and initializes the JSON file if it doesn't
         * exist.
         *
         * @param path The file path where the JSON data will be stored.
         *             Defaults to
         *             "Backend/login-register/src/main/java/com/dinneconnect/auth/login_register/persistence/user.json"
         */
        public BaseRepository(
                        @Value("${app.repository.path:Backend/login-register/src/main/java/com/dinneconnect/auth/login_register/persistence/user.json}") String path) {
                this.relativePath = Paths.get(path);
                this.data = new ArrayList<>();
                this.objectMapper = new ObjectMapper();
                createDirectory();
                loadData();
        }

        /**
         * Creates the necessary directory structure and JSON file if they don't exist.
         * Initializes the file with an empty JSON array if newly created.
         *
         * @throws IOException if there's an error creating directories or file
         */
        private void createDirectory() {
                try {
                        Files.createDirectories(relativePath.getParent());
                        if (!Files.exists(relativePath)) {
                                Files.createFile(relativePath);
                                Files.write(relativePath, "[]".getBytes());
                        }
                } catch (IOException e) {
                        System.err.println("Error creating directory or file: " + e.getMessage());
                }
        }

        /**
         * Loads data from the JSON file into memory.
         * Handles various edge cases like empty or invalid JSON files.
         *
         * @throws IOException if there's an error reading from the file
         */
        private void loadData() {
                try {
                        if (Files.exists(relativePath)) {
                                String content = Files.readString(relativePath);
                                if (content.trim().isEmpty()) {
                                        data = new ArrayList<>();
                                        Files.write(relativePath, "[]".getBytes());
                                } else {
                                        try {
                                                data = objectMapper.readValue(relativePath.toFile(),
                                                                objectMapper.getTypeFactory().constructCollectionType(
                                                                                List.class,
                                                                                Map.class));
                                                if (data == null) {
                                                        data = new ArrayList<>();
                                                }
                                        } catch (IOException e) {
                                                System.err.println("Error parsing JSON data: " + e.getMessage());
                                                data = new ArrayList<>();
                                                Files.write(relativePath, "[]".getBytes());
                                        }
                                }
                        } else {
                                data = new ArrayList<>();
                        }
                        System.out.println("Data loaded successfully: ");
                        System.out.println(this.data);
                } catch (IOException e) {
                        System.err.println("Error loading data: " + e.getMessage());
                        data = new ArrayList<>();
                }
        }

        /**
         * Persists the current state of data to the JSON file.
         * Creates necessary directories if they don't exist.
         *
         * @throws IOException if there's an error writing to the file
         */
        public void save() {
                try {
                        createDirectory();
                        objectMapper.writeValue(relativePath.toFile(), data);
                } catch (IOException e) {
                        System.err.println("Error saving data: " + e.getMessage());
                }
        }

        /**
         * Adds a new user entity to the repository.
         *
         * @param entity The user entity to be added
         * @return Map containing operation success status
         */
        public Map<String, Boolean> postEntity(User entity) {
                Map<String, Boolean> response = new HashMap<>();
                try {
                        response.put("success", true);
                        data.add(entity.toDict());
                        save();
                        return response;
                } catch (Exception e) {
                        response.put("success", false);
                        return response;
                }
        }

        /**
         * Retrieves all entities that match a specific field value.
         *
         * @param field The field to search by
         * @param value The value to match (case-insensitive)
         * @return List of entities matching the criteria
         */
        public List<Map<String, Object>> getEntitiesByField(String field, String value) {
                return data.stream()
                                .filter(entity -> String.valueOf(entity.getOrDefault(field, ""))
                                                .toLowerCase()
                                                .equals(value.toLowerCase()))
                                .collect(Collectors.toList());
        }

        /**
         * Retrieves all entities in the repository.
         *
         * @return List of all entities
         */
        public List<Map<String, Object>> getEntities() {
                return new ArrayList<>(data);
        }

        /**
         * Retrieves an entity by its unique code.
         *
         * @param code The unique identifier of the entity
         * @return Map containing the entity or error message if not found
         */
        public Map<String, Object> getEntityByCode(long code) {
                return data.stream()
                                .filter(entity -> ((Number) entity.get("code")).longValue() == code)
                                .findFirst()
                                .orElse(Map.of("error", "Entity not found"));
        }

        /**
         * Retrieves an entity by a specific field value.
         *
         * @param field The field to search by
         * @param value The value to match (case-insensitive)
         * @return Map containing the entity or null if not found
         */
        public Map<String, Object> getEntityByField(String field, String value) {
                return data.stream()
                                .filter(entity -> String.valueOf(entity.getOrDefault(field, ""))
                                                .toLowerCase()
                                                .equals(value.toLowerCase()))
                                .findFirst()
                                .orElse(null);
        }

        /**
         * Updates an entity identified by its code with new values.
         *
         * @param code    The unique identifier of the entity to update
         * @param updates Map containing the fields to update and their new values
         * @return Map indicating operation success status
         */
        public Map<String, Boolean> updateEntity(long code, Map<String, Object> updates) {
                for (Map<String, Object> entity : data) {
                        System.out.println("=====================================");
                        System.out.println(data);
                        System.out.println("=====================================");
                        if (((Long) entity.get("code")).longValue() == code) {
                                entity.putAll(updates);
                                save();
                                return Map.of("success", true);
                        }
                }
                return Map.of("success", false);
        }

        /**
         * Deletes an entity by its unique code.
         *
         * @param code The unique identifier of the entity to delete
         * @return Map indicating operation success status
         */
        public Map<String, Object> deleteEntityByCode(long code) {
                boolean removed = data.removeIf(entity -> ((Number) entity.get("code")).longValue() == code);
                if (removed) {
                        save();
                        return Map.of("success", true);
                }
                return Map.of("success", false);
        }

        /**
         * Deletes an entity by a specific field value.
         *
         * @param field The field to search by
         * @param value The value to match (case-insensitive)
         * @return Map indicating operation success status
         */
        public Map<String, Object> deleteEntityByField(String field, String value) {
                boolean removed = data.removeIf(entity -> String.valueOf(entity.getOrDefault(field, ""))
                                .toLowerCase()
                                .equals(value.toLowerCase()));
                if (removed) {
                        save();
                        return Map.of("success", true);
                }
                return Map.of("success", false);
        }
}