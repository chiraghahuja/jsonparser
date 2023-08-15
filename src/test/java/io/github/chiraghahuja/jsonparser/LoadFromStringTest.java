package io.github.chiraghahuja.jsonparser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class LoadFromStringTest
{
    @Test
    public void testLoadFromStringWithValidJson() throws JsonUtils.InvalidJsonFormatException {
        // Arrange
        String jsonObjectString = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";

        // Act
        List<JsonNode> nodes = JsonUtils.loadFromString(jsonObjectString);

        // Assert
        assertEquals(1, nodes.size());
        assertEquals("John", nodes.get(0).get("name").asText());
        assertEquals(30, nodes.get(0).get("age").asInt());
        assertEquals("New York", nodes.get(0).get("city").asText());

    }

    @Test
    public void testLoadFromStringWithInvalidJson() {
        String invalidJson = "invalid json"; // This is not a valid JSON string
        assertThrows(JsonUtils.InvalidJsonFormatException.class, () -> {
            JsonUtils.loadFromString(invalidJson);
        });
    }

    @Test
    public void testLoadFromStringWithNonObjectArray() {
        String invalidJsonArray = "[1, 2, 3]"; // This is an array of numbers, not objects
        assertThrows(JsonUtils.InvalidJsonFormatException.class, () -> {
            JsonUtils.loadFromString(invalidJsonArray);
        });
    }

    @Test
    public void testLoadFromStringWithMalformedJson() {
        String malformedJson = "{\"name\": \"John\", age: 30}"; // Missing quotes around "age"
        assertThrows(JsonUtils.InvalidJsonFormatException.class, () -> {
            JsonUtils.loadFromString(malformedJson);
        });
    }

    @Test
    public void testLoadFromStringWithEmptyJson() {
        String emptyJson = "";
        assertThrows(JsonUtils.InvalidJsonFormatException.class, () -> {
            JsonUtils.loadFromString(emptyJson);
        });
    }

    @Test
    public void testLoadFromStringWithEmptyJsonArray() throws JsonUtils.InvalidJsonFormatException {
        // Arrange
        String emptyJsonArray = "[]";

        // Act
        List<JsonNode> nodes = JsonUtils.loadFromString(emptyJsonArray);

        // Assert
        assertEquals(0, nodes.size());
    }

    //Test with nested JSON objects and arrays.
    @Test
    public void testLoadFromStringWithNestedJson() throws JsonUtils.InvalidJsonFormatException {
        // Arrange
        String nestedJson = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\", \"children\": [{\"name\": \"Sara\", \"age\": 5}, {\"name\": \"Alex\", \"age\": 8}], \"cars\": [\"BMW\", \"Audi\", \"Ford\"]}";

        // Act
        List<JsonNode> nodes = JsonUtils.loadFromString(nestedJson);

        // Assert
        assertEquals(1, nodes.size());
        assertEquals("John", nodes.get(0).get("name").asText());
        assertEquals(30, nodes.get(0).get("age").asInt());
        assertEquals("New York", nodes.get(0).get("city").asText());
        assertEquals(2, nodes.get(0).get("children").size());
        assertEquals("Sara", nodes.get(0).get("children").get(0).get("name").asText());
        assertEquals(5, nodes.get(0).get("children").get(0).get("age").asInt());
        assertEquals("Alex", nodes.get(0).get("children").get(1).get("name").asText());
        assertEquals(8, nodes.get(0).get("children").get(1).get("age").asInt());
        assertEquals(3, nodes.get(0).get("cars").size());
        assertEquals("BMW", nodes.get(0).get("cars").get(0).asText());
        assertEquals("Audi", nodes.get(0).get("cars").get(1).asText());
        assertEquals("Ford", nodes.get(0).get("cars").get(2).asText());
    }

    //Test with JSON strings containing escape characters (e.g., quotes, backslashes).
    @Test
    public void testLoadFromStringWithEscapedCharacters() throws JsonUtils.InvalidJsonFormatException {
        // Arrange
        String escapedJson = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\", \"children\": [{\"name\": \"Sara\", \"age\": 5}, {\"name\": \"Alex\", \"age\": 8}], \"cars\": [\"BMW\", \"Audi\", \"Ford\"], \"escaped\": \"\\\" \\\\ \\/ \\b \\f \\n \\r \\t\"}";

        // Act
        List<JsonNode> nodes = JsonUtils.loadFromString(escapedJson);

        // Assert
        assertEquals(1, nodes.size());
        assertEquals("John", nodes.get(0).get("name").asText());
        assertEquals(30, nodes.get(0).get("age").asInt());
        assertEquals("New York", nodes.get(0).get("city").asText());
        assertEquals(2, nodes.get(0).get("children").size());
        assertEquals("Sara", nodes.get(0).get("children").get(0).get("name").asText());
        assertEquals(5, nodes.get(0).get("children").get(0).get("age").asInt());
        assertEquals("Alex", nodes.get(0).get("children").get(1).get("name").asText());
        assertEquals(8, nodes.get(0).get("children").get(1).get("age").asInt());
        assertEquals(3, nodes.get(0).get("cars").size());
        assertEquals("BMW", nodes.get(0).get("cars").get(0).asText());
        assertEquals("Audi", nodes.get(0).get("cars").get(1).asText());
        assertEquals("Ford", nodes.get(0).get("cars").get(2).asText());
        assertEquals("\" \\ / \b \f \n \r \t", nodes.get(0).get("escaped").asText());
    }


    @Test
    public void testLoadFromStringWithSpecialCharacters() throws JsonUtils.InvalidJsonFormatException {
        // Arrange
        String specialCharactersJson = "{\"text\": \"Special characters: !@#$%^&*()_+{}[]|\\\\\"}";

        // Act
        List<JsonNode> nodes = JsonUtils.loadFromString(specialCharactersJson);

        // Assert
        assertEquals(1, nodes.size());
        assertEquals("Special characters: !@#$%^&*()_+{}[]|\\", nodes.get(0).get("text").asText());
    }

    // Test with JSON objects and arrays containing null values.
    @Test
    public void testLoadFromStringWithNullValues() throws JsonUtils.InvalidJsonFormatException {
        // Arrange
        String nullValuesJson = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\", \"children\": [{\"name\": \"Sara\", \"age\": 5}, {\"name\": \"Alex\", \"age\": 8}], \"cars\": [\"BMW\", \"Audi\", \"Ford\"], \"null\": null}";

        // Act
        List<JsonNode> nodes = JsonUtils.loadFromString(nullValuesJson);

        // Assert
        assertEquals(1, nodes.size());
        assertEquals("John", nodes.get(0).get("name").asText());
        assertEquals(30, nodes.get(0).get("age").asInt());
        assertEquals("New York", nodes.get(0).get("city").asText());
        assertEquals(2, nodes.get(0).get("children").size());
        assertEquals("Sara", nodes.get(0).get("children").get(0).get("name").asText());
        assertEquals(5, nodes.get(0).get("children").get(0).get("age").asInt());
        assertEquals("Alex", nodes.get(0).get("children").get(1).get("name").asText());
        assertEquals(8, nodes.get(0).get("children").get(1).get("age").asInt());
        assertEquals(3, nodes.get(0).get("cars").size());
        assertEquals("BMW", nodes.get(0).get("cars").get(0).asText());
        assertEquals("Audi", nodes.get(0).get("cars").get(1).asText());
        assertEquals("Ford", nodes.get(0).get("cars").get(2).asText());
        assertTrue(nodes.get(0).get("null").isNull());
    }

    //Test with JSON objects and arrays containing boolean values
    @Test
    public void testLoadFromStringWithBooleanValues() throws JsonUtils.InvalidJsonFormatException {
        // Arrange
        String booleanValuesJson = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\", \"children\": [{\"name\": \"Sara\", \"age\": 5}, {\"name\": \"Alex\", \"age\": 8}], \"cars\": [\"BMW\", \"Audi\", \"Ford\"], \"boolean\": true}";

        // Act
        List<JsonNode> nodes = JsonUtils.loadFromString(booleanValuesJson);

        // Assert
        assertEquals(1, nodes.size());
        assertEquals("John", nodes.get(0).get("name").asText());
        assertEquals(30, nodes.get(0).get("age").asInt());
        assertEquals("New York", nodes.get(0).get("city").asText());
        assertEquals(2, nodes.get(0).get("children").size());
        assertEquals("Sara", nodes.get(0).get("children").get(0).get("name").asText());
        assertEquals(5, nodes.get(0).get("children").get(0).get("age").asInt());
        assertEquals("Alex", nodes.get(0).get("children").get(1).get("name").asText());
        assertEquals(8, nodes.get(0).get("children").get(1).get("age").asInt());
        assertEquals(3, nodes.get(0).get("cars").size());
        assertEquals("BMW", nodes.get(0).get("cars").get(0).asText());
        assertEquals("Audi", nodes.get(0).get("cars").get(1).asText());
        assertEquals("Ford", nodes.get(0).get("cars").get(2).asText());
        assertTrue(nodes.get(0).get("boolean").asBoolean());
    }


}
