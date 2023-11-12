package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        FileToXMLMapper fileToXMLMapper = new FileToXMLMapper(new ReadFromFile());
        try {
            fileToXMLMapper.mappingFromFile("example.txt", "src/main/resources/output.xml");
        } catch (IOException e) {
            System.out.println("File not found or some other issue while reading it");
        }
    }
}