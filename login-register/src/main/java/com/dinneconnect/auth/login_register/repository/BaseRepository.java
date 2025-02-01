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

@Repository
public class BaseRepository {
        private final Path relativePath;
        private List<Map<String, Object>> data;
        private final ObjectMapper objectMapper;

        public BaseRepository(
                        @Value("${app.repository.path:Backend/login-register/src/main/java/com/dinneconnect/auth/login_register/persistence/user.json}") String path) {
                this.relativePath = Paths.get(path);
                this.data = new ArrayList<>();
                this.objectMapper = new ObjectMapper();
                createDirectory();
                loadData();
        }

        private void createDirectory() {
                try {
                        Files.createDirectories(relativePath.getParent());
                        // Create the file if it doesn't exist
                        if (!Files.exists(relativePath)) {
                                Files.createFile(relativePath);
                                // Initialize with empty array
                                Files.write(relativePath, "[]".getBytes());
                        }
                } catch (IOException e) {
                        System.err.println("Error creating directory or file: " + e.getMessage());
                }
        }

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
                                                // If JSON is invalid, initialize with empty array
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

        public void save() {
                try {
                        createDirectory();
                        objectMapper.writeValue(relativePath.toFile(), data);
                } catch (IOException e) {
                        System.err.println("Error saving data: " + e.getMessage());
                }
        }

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

        public List<Map<String, Object>> getEntitiesByField(String field, String value) {
                return data.stream()
                                .filter(entity -> String.valueOf(entity.getOrDefault(field, ""))
                                                .toLowerCase()
                                                .equals(value.toLowerCase()))
                                .collect(Collectors.toList());
        }

        public List<Map<String, Object>> getEntities() {
                return new ArrayList<>(data);
        }

        public Map<String, Object> getEntityByCode(long code) {
                return data.stream()
                                .filter(entity -> ((Number) entity.get("code")).longValue() == code)
                                .findFirst()
                                .orElse(Map.of("error", "Entity not found"));
        }

        public Map<String, Object> getEntityByField(String field, String value) {
                return data.stream()
                                .filter(entity -> String.valueOf(entity.getOrDefault(field, ""))
                                                .toLowerCase()
                                                .equals(value.toLowerCase()))
                                .findFirst()
                                .orElse(null);
        }

        public Map<String, Object> updateEntity(long code, Map<String, Object> updates) {
                for (Map<String, Object> entity : data) {
                        if (((Number) entity.get("code")).longValue() == code) {
                                entity.putAll(updates);
                                save();
                                return Map.of("success", true);
                        }
                }
                return Map.of("success", false);
        }

        public Map<String, Object> deleteEntityByCode(long code) {
                boolean removed = data.removeIf(entity -> ((Number) entity.get("code")).longValue() == code);
                if (removed) {
                        save();
                        return Map.of("success", true);
                }
                return Map.of("success", false);
        }

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