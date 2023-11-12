package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFile {

    //https://www.baeldung.com/reading-file-in-java
    public List<String> readfiletolist(String fileName) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
       return readFromInputStream(inputStream);
    }

    private List<String> readFromInputStream(InputStream inputStream)
            throws IOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }
}
