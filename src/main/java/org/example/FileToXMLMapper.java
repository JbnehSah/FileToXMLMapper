package org.example;


import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class FileToXMLMapper {

  /*P|förnamn|efternamn
    T|mobilnummer|fastnätsnummer
    A|gata|stad|postnummer
    F|namn|födelseår
    P kan följas av T, A och F
    F kan följas av T och A"*/

    private final ReadFromFile readFromFile;

    HashMap<String, List<String>> elements = new HashMap<>();

    public FileToXMLMapper(ReadFromFile readFromFile) {
        this.readFromFile = readFromFile;
        initElements();
    }

    public void initElements() {
        this.elements.put("P", List.of("<person>", "</person>", "<firstName>", "</firstName>", "<lastName>", "</lastName>"));
        this.elements.put("F", List.of("<family>", "</family>", "<name>", "</name>", "<born>", "</born>"));
        this.elements.put("T", List.of("<phone>", "</phone>", "<mobile>", "</mobile>", "<landline>", "</landline>"));
        this.elements.put("A", List.of("<address>", "</address>", "<street>", "</street>", "<city>", "</city>", "<zip>", "</zip>"));
    }

    public void mappingFromFile(String fileName, String outputFile) throws IOException {

        List<String> strings = readFromFile.readfiletolist(fileName);

        //https://www.w3schools.com/java/java_files_create.asp
        FileWriter fileWriter = new FileWriter(outputFile);
        boolean elementPersonAlreadyOpen = false;
        boolean elementFamilyAlreadyOpen = false;

        fileWriter.write("<people>");
        for (String s : strings) {

            String[] rowPart = s.split("\\|");
            String head = getTag(rowPart);

            if (is(head, "P")) {
                if (elementFamilyAlreadyOpen) {
                    fileWriter.write(elements.get("F").get(1));
                }
                elementPersonAlreadyOpen = handleIfElementAlreadyOpen(elementPersonAlreadyOpen, fileWriter, "P");
                writeElementsToFile(fileWriter, rowPart, "P");

            } else if (is(head, "T")) {
                fileWriter.write(elements.get("T").get(0));
                writeElementsToFile(fileWriter, rowPart, "T");
                fileWriter.write(elements.get("T").get(1));

            } else if (is(head, "F")) {

                elementFamilyAlreadyOpen = handleIfElementAlreadyOpen(elementFamilyAlreadyOpen, fileWriter, "F");
                writeElementsToFile(fileWriter, rowPart, "F");
            }
        }
        if (elementPersonAlreadyOpen) {
            fileWriter.write(elements.get("P").get(1));
        }
        fileWriter.write("</people>");
        fileWriter.close();

    }

    private void writeElementsToFile(FileWriter fileWriter, String[] rowPart, String tag) throws IOException {
        fileWriter.write(elements.get(tag).get(2));
        fileWriter.write(rowPart[1]);
        fileWriter.write(elements.get(tag).get(3));
        fileWriter.write(elements.get(tag).get(4));
        fileWriter.write(rowPart[2]);
        fileWriter.write(elements.get(tag).get(5));
    }

    private boolean handleIfElementAlreadyOpen(boolean elementPersonAlreadyOpen, FileWriter fileWriter, String tag) throws IOException {
        if (elementPersonAlreadyOpen) {
            fileWriter.write(elements.get(tag).get(1));
            fileWriter.write(elements.get(tag).get(0));
        } else {
            //first tag
            fileWriter.write(elements.get(tag).get(0));
            elementPersonAlreadyOpen = true;
        }
        return elementPersonAlreadyOpen;
    }

    private String getTag(String[] rowPart) {
        return rowPart[0];
    }

    private boolean is(String key, String compare) {
        return compare.equals(key);
    }
}



