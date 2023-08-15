package io.github.chiraghahuja.jsonparser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

public class TestLoadFromFile {

    @TempDir
    Path tempDir; // Temporary directory for test files

    @Test
    public void testLoadFromFileWithValidJson() throws IOException, JsonUtils.InvalidJsonFormatException {
        // Create a temporary JSON file with valid content
        String jsonContent = "[{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}]";
        Path jsonFilePath = tempDir.resolve("valid.json");
        Files.write(jsonFilePath, jsonContent.getBytes());

        // Load from the temporary JSON file
        List<JsonNode> jsonNodes = JsonUtils.loadFromFile(jsonFilePath.toString());

        // Assertions
        assertEquals(1, jsonNodes.size());
        assertEquals("John", jsonNodes.get(0).get("name").asText());
        assertEquals(30, jsonNodes.get(0).get("age").asInt());
        assertEquals("New York", jsonNodes.get(0).get("city").asText());
    }

    @Test
    public void testLoadFromFileWithInvalidJson() {
        try {
            // Create a temporary JSON file with invalid content
            String jsonContent = "invalid json";
            Path jsonFilePath = tempDir.resolve("invalid.json");
            Files.write(jsonFilePath, jsonContent.getBytes());

            // Assert that the expected exception is thrown
            assertThrows(JsonUtils.InvalidJsonFormatException.class, () -> {
                JsonUtils.loadFromFile(jsonFilePath.toString());
            });
        } catch (IOException e) {
            fail("Failed to create temporary file: " + e.getMessage());
        }
    }

    @Test
    public void testLoadFromFileWithUnicode() throws IOException, JsonUtils.InvalidJsonFormatException {
        String jsonContent = "{\"text\": \"\\u00a9 All rights reserved\"}";
        Path jsonFilePath = tempDir.resolve("unicode.json");
        Files.write(jsonFilePath, jsonContent.getBytes());

        List<JsonNode> nodes = JsonUtils.loadFromFile(jsonFilePath.toString());
        assertEquals(1, nodes.size());
        assertEquals("© All rights reserved", nodes.get(0).get("text").asText());
    }

    // Test for loading from a file with multiple JSON objects
    @Test
    public void testLoadFromFileWithMultipleJsonObjects() throws IOException, JsonUtils.InvalidJsonFormatException {
        String jsonContent = "[{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}, {\"name\": \"Jane\", \"age\": 25, \"city\": \"San Francisco\"}]";
        Path jsonFilePath = tempDir.resolve("multiple.json");
        Files.write(jsonFilePath, jsonContent.getBytes());

        List<JsonNode> nodes = JsonUtils.loadFromFile(jsonFilePath.toString());
        assertEquals(2, nodes.size());
        assertEquals("John", nodes.get(0).get("name").asText());
        assertEquals(30, nodes.get(0).get("age").asInt());
        assertEquals("New York", nodes.get(0).get("city").asText());
        assertEquals("Jane", nodes.get(1).get("name").asText());
        assertEquals(25, nodes.get(1).get("age").asInt());
        assertEquals("San Francisco", nodes.get(1).get("city").asText());
    }

    @Test
    public void testLoadFromFileWithBoundaryValues() throws IOException, JsonUtils.InvalidJsonFormatException {
        String jsonContent = "{\"value\": 2147483647}"; // Maximum positive int value
        Path jsonFilePath = tempDir.resolve("boundary.json");
        Files.write(jsonFilePath, jsonContent.getBytes());

        List<JsonNode> nodes = JsonUtils.loadFromFile(jsonFilePath.toString());
        assertEquals(1, nodes.size());
        assertEquals(2147483647, nodes.get(0).get("value").asInt());
    }

    @Test
    public void testLoadFromFileWithSpecialCharacters() throws IOException, JsonUtils.InvalidJsonFormatException {
        String jsonContent = "{\"name\": \"John\", \"special\": \"!@#$%^&*()_+{}[]|\\\\\"}";
        Path jsonFilePath = tempDir.resolve("special.json");
        Files.write(jsonFilePath, jsonContent.getBytes());

        List<JsonNode> nodes = JsonUtils.loadFromFile(jsonFilePath.toString());
        assertEquals(1, nodes.size());
        assertEquals("John", nodes.get(0).get("name").asText());
        assertEquals("!@#$%^&*()_+{}[]|\\", nodes.get(0).get("special").asText());
    }



    // Add more test cases as needed...
}

