package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileToXMLMapperIntegrationTest {

    private FileToXMLMapper fileToXMLMapper;
    private final ReadFromFile readFromFile = new ReadFromFile();

    @BeforeEach
    public void setUp() {
        fileToXMLMapper = new FileToXMLMapper(readFromFile);

    }

    @Test
    public void testReadFile() throws IOException {
        fileToXMLMapper.mappingFromFile("example.txt", "src/main/resources/output.xml");
        String expected = "<people><person><firstName>Elof</firstName><lastName>Sundin</lastName><phone><mobile>073-101801</mobile><landline>018-101801</landline></phone><family><name>Hans</name><born>1967</born></family><family><name>Anna</name><born>1969</born><phone><mobile>073-101802</mobile><landline>08-101802</landline></phone></family></person><person><firstName>Boris</firstName><lastName>Johnson</lastName></person></people>";
        Path path = Paths.get("src/main/resources/output.xml");
        String read = String.join("", Files.readAllLines(path));
        assertEquals(expected, read);
    }

    @Test
    public void testReadFile1() throws IOException {
        fileToXMLMapper.mappingFromFile("test1.txt", "src/test/resources/testOutput1.xml");
        String expected = "<people><person><firstName>Elof</firstName><lastName>Sundin</lastName></person><person><firstName>Boris</firstName><lastName>Johnson</lastName></person></people>";
        Path path = Paths.get("src/test/resources/testOutput1.xml");
        String read = String.join("", Files.readAllLines(path));
        assertEquals(expected, read);
    }

    @Test
    public void testReadFile2() throws IOException {
        fileToXMLMapper.mappingFromFile("test2.txt", "src/test/resources/testOutput2.xml");
        String expected = "<people><person><firstName>Elof</firstName><lastName>Sundin</lastName><family><name>Hans</name><born>1967</born></family><family><name>Anna</name><born>1969</born><phone><mobile>073-101802</mobile><landline>08-101802</landline></phone></person></people>";
        Path path = Paths.get("src/test/resources/testOutput2.xml");
        String read = String.join("", Files.readAllLines(path));
        assertEquals(expected, read);
    }

    @Test
    public void testReadFile3() throws IOException {
        fileToXMLMapper.mappingFromFile("test3.txt", "src/test/resources/testOutput3.xml");
        String expected = "<people><person><firstName>Elof</firstName><lastName>Sundin</lastName></person></people>";
        Path path = Paths.get("src/test/resources/testOutput3.xml");
        String read = String.join("", Files.readAllLines(path));
        assertEquals(expected, read);
    }
}