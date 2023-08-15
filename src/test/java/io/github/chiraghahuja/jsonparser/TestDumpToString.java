package io.github.chiraghahuja.jsonparser;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.chiraghahuja.jsonparser.Person;

public class TestDumpToString {

    @Test
    public void testDumpToStringWithEmptyList() throws JsonProcessingException {
        // Arrange
        List<Object> emptyList = new ArrayList<>();

        // Act
        String jsonString = JsonUtils.dumpToString(emptyList);

        // Assert
        assertEquals("[]", jsonString);
    }

    @Test
    public void testDumpToStringWithSingleObject() throws JsonProcessingException {
        // Arrange
        Person person = new Person("John", 30, "New York");
        List<Object> objectList = List.<Object>of(person);

        // Act
        String jsonString = JsonUtils.dumpToString(objectList);

        // Assert
        assertEquals("[{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}]", jsonString);
    }

    @Test
    public void testDumpToStringWithMultipleObjects() throws JsonProcessingException {
        // Arrange
        Person person1 = new Person("John", 30, "New York");
        Person person2 = new Person("Jane", 25, "Los Angeles");
        List<Object> objectList = List.of(person1, person2);

        // Act
        String jsonString = JsonUtils.dumpToString(objectList);

        // Assert
        assertEquals("[{\"name\":\"John\",\"age\":30,\"city\":\"New York\"},{\"name\":\"Jane\",\"age\":25,\"city\":\"Los Angeles\"}]", jsonString);
    }

    @Test
    public void testDumpToStringWithNullObject() throws JsonProcessingException {
        List<Object> objectList = new ArrayList<>();
        objectList.add(null);
        String jsonString = JsonUtils.dumpToString(objectList);
        assertEquals("[null]", jsonString);
    }


    @Test
    public void testDumpToStringWithComplexObject() throws JsonProcessingException {
        Person person = new Person("John", 30, "New York");
        List<Object> objectList = List.of(person);
        String jsonString = JsonUtils.dumpToString(objectList);
        assertEquals("[{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}]", jsonString);
    }

    @Test
    public void testDumpToStringWithUnicodeCharacters() throws JsonProcessingException {
        String unicodeString = "こんにちは"; // Hello in Japanese
        List<Object> objectList = List.of(unicodeString);
        String jsonString = JsonUtils.dumpToString(objectList);
        assertEquals("[\"" + unicodeString + "\"]", jsonString);
    }
}

