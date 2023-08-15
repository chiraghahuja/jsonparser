package io.github.chiraghahuja.jsonparser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.databind.cfg.CoercionInputShape.EmptyObject;
import static java.nio.file.Files.readString;
import static org.junit.jupiter.api.Assertions.*;
import io.github.chiraghahuja.jsonparser.Person;

public class TestDumpToFile {

    @TempDir
    Path tempDir; // Temporary directory for test files

    @Test
    public void testDumpToFileWithEmptyList() throws IOException {
        List<Object> emptyList = new ArrayList<>();
        Path outputPath = tempDir.resolve("output.json");

        JsonUtils.dumpToFile(emptyList, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = readString(outputPath);
        assertEquals("[]", content);
    }

    @Test
    public void testDumpToFileWithSingleObject() throws IOException {
        // Create a Person object from the external source
        Person person = new Person("John", 30, "New York");
        List<Object> objectList = List.of(person);
        Path outputPath = tempDir.resolve("output.json");

        JsonUtils.dumpToFile(objectList, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = readString(outputPath);
        assertEquals("[{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}]", content);
    }

    //Test dumpToFile with multiple objects
    @Test
    public void testDumpToFileWithMultipleObjects() throws IOException {
        // Create a Person object from the external source
        Person person1 = new Person("John", 30, "New York");
        Person person2 = new Person("Jane", 25, "Los Angeles");
        List<Object> objectList = List.of(person1, person2);
        Path outputPath = tempDir.resolve("output.json");

        JsonUtils.dumpToFile(objectList, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = readString(outputPath);
        assertEquals("[{\"name\":\"John\",\"age\":30,\"city\":\"New York\"},{\"name\":\"Jane\",\"age\":25,\"city\":\"Los Angeles\"}]", content);
    }

    //Test dumpToFile with null object
    @Test
    public void testDumpToFileWithNullObject() throws IOException {
        List<Object> objectList = new ArrayList<>();
        objectList.add(null);
        Path outputPath = tempDir.resolve("output.json");

        JsonUtils.dumpToFile(objectList, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = readString(outputPath);
        assertEquals("[null]", content);
    }

    //Test dumpToFile with complex object
    @Test
    public void testDumpToFileWithComplexObject() throws IOException {
        // Create a Person object from the external source
        Person person = new Person("John", 30, "New York");
        List<Object> objectList = List.of(person);
        Path outputPath = tempDir.resolve("output.json");

        JsonUtils.dumpToFile(objectList, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = readString(outputPath);
        assertEquals("[{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}]", content);
    }

    //Test dumpToFile with empty string
    @Test
    public void testDumpToFileWithEmptyString() throws IOException {
        List<Object> objectList = new ArrayList<>();
        objectList.add("");
        Path outputPath = tempDir.resolve("output.json");

        JsonUtils.dumpToFile(objectList, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = readString(outputPath);
        assertEquals("[\"\"]", content);
    }

    //Test dumpToFile with empty object
    @Test
    public void testDumpToFileWithEmptyObject() throws IOException {
        Object emptyObject = new Object();
        List<Object> objectList = List.of(emptyObject);
        Path outputPath = tempDir.resolve("output.json");

        JsonUtils.dumpToFile(objectList, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = Files.readString(outputPath);
        assertEquals("[{}]", content);
    }

    // Test with special unicode characters
    @Test
    public void testDumpToFileWithUnicodeCharacters() throws IOException {
        List<Object> objectList = new ArrayList<>();
        objectList.add("Hello \u00A9");
        Path outputPath = tempDir.resolve("output.json");

        JsonUtils.dumpToFile(objectList, outputPath.toString());

        assertTrue(Files.exists(outputPath));
        String content = readString(outputPath);
        assertEquals("[\"Hello \u00A9\"]", content);
    }


    // More test methods...

}

