package io.github.chiraghahuja.jsonparser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList; // import the ArrayList class
import java.util.List;


import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.JAXBContext;

/**
 * The JSON class provides a utility for parsing JSON strings and validating the format.
 * It supports parsing JSON objects (dictionaries) or arrays of objects (lists of dictionaries).
 */
public class JsonUtils  {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        // Disable FAIL_ON_EMPTY_BEANS feature
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    /**
     * Constructs a JSON object. This constructor is private to prevent instantiation from outside the class.
     */
    private JsonUtils() {
        // Private constructor to prevent instantiation from outside
    }

    /**
     * Parses a JSON string and returns a list of JsonNode objects (dictionaries) representing the JSON structure.
     * The JSON can be either a JSON object (dictionary) or an array of JSON objects (list of dictionaries).
     *
     * @param jsonString the JSON string to parse
     * @return a list of JsonNode objects representing the JSON structure
     * @throws InvalidJsonFormatException if the JSON string is not formatted correctly
     *
     * @apiNote Example usage:
     * <pre>{@code
     * // Example 1: Parsing a JSON object
     * String jsonObjectString = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";
     *
     * try {
     *     List<JsonNode> jsonNodes = JSON.loads(jsonObjectString);
     *     System.out.println("Valid JSON object format");
     *
     *     // Process the list of JsonNode objects representing the JSON object
     *     for (JsonNode jsonNode : jsonNodes) {
     *         System.out.println("JsonNode: " + jsonNode);
     *     }
     * } catch (JSON.InvalidJsonFormatException e) {
     *     System.out.println("Invalid JSON format: " + e.getMessage());
     *     // Handle the exception
     * }
     *
     * // Example 2: Parsing a JSON array of objects
     * String jsonArrayString = "[{\"fruit\": \"apple\", \"color\": \"red\"}, {\"fruit\": \"banana\", \"color\": \"yellow\"}]";
     *
     * try {
     *     List<JsonNode> jsonNodes = JSON.loads(jsonArrayString);
     *     System.out.println("Valid JSON array of objects format");
     *
     *     // Process the list of JsonNode objects representing the JSON array
     *     for (JsonNode jsonNode : jsonNodes) {
     *         System.out.println("JsonNode: " + jsonNode);
     *     }
     * } catch (JSON.InvalidJsonFormatException e) {
     *     System.out.println("Invalid JSON format: " + e.getMessage());
     *     // Handle the exception
     * }
     * }</pre>
     */
    public static List<JsonNode>  loadFromString (String jsonString) throws InvalidJsonFormatException {
        try {
            //ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            if (jsonNode.isObject()) {
                List<JsonNode> jsonNodes = new ArrayList<>();
                jsonNodes.add(jsonNode);
                return jsonNodes;
            } else if (jsonNode.isArray()) {
                List<JsonNode> jsonNodes = new ArrayList<>();
                for (JsonNode element : jsonNode) {
                    if (!element.isObject()) {
                        throw new InvalidJsonFormatException("Invalid JSON format: Array elements are not objects");
                    }
                    jsonNodes.add(element);
                }
                return jsonNodes;
            } else {
                throw new InvalidJsonFormatException("Invalid JSON format: JSON is neither an object nor an array");
            }
        } catch (Exception e) {
            throw new InvalidJsonFormatException("Invalid JSON format: " + e.getMessage());
        }
    }

    /**
     * Serializes a list of Java objects to a JSON string.
     *
     * @return the JSON string representation of the list of objects
     * @throws JsonProcessingException if an error occurs during serialization
     */
    public static String dumpToString (List<?> objects) throws JsonProcessingException {
        //ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(objects);
    }

    /**
     * Parses a JSON file and returns a list of JsonNode objects representing the JSON structure.
     * The JSON can be either a JSON object (dictionary) or an array of JSON objects (list of dictionaries).
     *
     * @param filePath the file path of the JSON file to parse
     * @return a list of JsonNode objects representing the JSON structure
     * @throws IOException                if an error occurs while reading the JSON file
     * @throws InvalidJsonFormatException if the JSON content is not formatted correctly
     *
     * @apiNote Example usage:
     * <pre>{@code
     * // Example: Parsing a JSON file
     * String filePath = "path/to/your/json/file.json";
     *
     * try {
     *     List<JsonNode> jsonNodes = JsonUtils.loadf(filePath);
     *     System.out.println("Valid JSON format");
     *
     *     // Process the list of JsonNode objects
     *     for (JsonNode jsonNode : jsonNodes) {
     *         System.out.println("JsonNode: " + jsonNode);
     *     }
     * } catch (JsonUtils.InvalidJsonFormatException e) {
     *     System.out.println("Invalid JSON format: " + e.getMessage());
     *     // Handle the exception
     * } catch (IOException e) {
     *     System.out.println("Error reading JSON file: " + e.getMessage());
     *     // Handle the exception
     * }
     * }</pre>
     */
    public static List<JsonNode> loadFromFile (String filePath) throws IOException, InvalidJsonFormatException {
        byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
        String jsonString = new String(jsonData, StandardCharsets.UTF_8);
        return loadFromString(jsonString);
    }



    /**
     * Serializes a list of Java objects to a JSON string and writes it to a file.
     *
     * @param objects   the list of objects to serialize
     * @param filePath  the file path to write the JSON content
     * @throws IOException if an error occurs while writing the file
     *
     * @apiNote Example usage:
     * <pre>{@code
     * // Example: Writing JSON to a file
     * List<Person> persons = Arrays.asList(
     *         new Person("John", 30, "123 Main St", "john@example.com"),
     *         new Person("Jane", 25, "456 Elm St", "jane@example.com")
     * );
     * String filePath = "path/to/your/output/file.json";
     *
     * try {
     *     JsonUtils.dumpf(persons, filePath);
     *     System.out.println("JSON written to file successfully");
     * } catch (IOException e) {
     *     System.out.println("Error writing JSON to file: " + e.getMessage());
     *     // Handle the exception
     * }
     * }</pre>
     */
    public static void dumpToFile (List<?> objects, String filePath) throws IOException {
        String json = dumpToString(objects);
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(json);
        fileWriter.close();
    }


    /**
     * Exception class representing an invalid JSON format.
     * It is thrown when the JSON string is not formatted correctly.
     */
    public static class InvalidJsonFormatException extends Exception {

        /**
         * Constructs an InvalidJsonFormatException with the specified error message.
         *
         * @param message the error message
         */
        public InvalidJsonFormatException(String message) {
            super(message);
        }
    }
}




