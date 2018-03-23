package com.developer.dmp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String XML_PART_1 = "<string name=\"";
    private static final String XML_PART_2 = "\">";
    private static final String XML_PART_3 = "</string>";
    private static final String RESOURCES_OPEN = "<resources>";
    private static final String RESOURCES_CLOSE = "</resources>";
    private static final String OUTPUT_DELIM = "####################";
    private static final String HELP_MESSAGE = "******* MENU *******" +
            "\n1. Parse Android Studio strings.xml file" +
            "\n2. Rebuild Android Studio strings.xml file";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        String choice;
        boolean valid;

        // present user with menu and act accordingly
        do {
            print(HELP_MESSAGE);
            System.out.print("> ");
            choice = input.nextLine();
            valid = false;

            switch (choice) {
                case "1":
                    valid = true;
                    print("\n"+OUTPUT_DELIM);
                    print("Parsing strings file...");
                    parseStringsFile();
                    print("Done!");
                    print(OUTPUT_DELIM+"\n");
                    break;
                case "2":
                    valid = true;
                    print("\n"+OUTPUT_DELIM);
                    print("Re-creating strings.xml");
                    recreateStringsFile();
                    print("Done!");
                    print(OUTPUT_DELIM+"\n");
                    break;
            }
        } while (valid);

        print("Program terminated.");
    }

    /**
     * Method to read a user's strings.xml file and parse out
     * the names of all the strings and their contents.  This
     * information is stored in different files in the output
     * directory.
     */
    private static void parseStringsFile() {
        try {
            // obtain and read our xml file - this comes from Android Studio
            File inputFile = new File("input/strings.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // get all of the <string> lines into a list
            NodeList nList = doc.getElementsByTagName("string");

            // create files to write to
            BufferedWriter stringsContentWriter = new BufferedWriter(new FileWriter("output/strings_content.txt"));
            BufferedWriter stringsNameWriter = new BufferedWriter(new FileWriter("output/strings_name.txt"));

            // loop through all our information
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Element element = (Element)nNode;

                // write info to respective files
                stringsNameWriter.write(element.getAttribute("name")+"\n");
                stringsContentWriter.write(nNode.getFirstChild().getNodeValue().trim()+"\n");
            }

            // close resources
            stringsNameWriter.close();
            stringsContentWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to rebuild a strings.xml file given the
     * new input (another language)
     */
    private static void recreateStringsFile() {

        try {
            // get all the content from the two files
            List<String> stringsName = Files.readAllLines(
                    new File("output/strings_name.txt").toPath(), StandardCharsets.UTF_8);
            List<String> convertedStringsContent = Files.readAllLines(
                    new File("input/strings_content_converted.txt").toPath(), StandardCharsets.UTF_8);

            // create our output file
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("output/strings.xml")));
            writer.write(RESOURCES_OPEN+"\n");

            // loop to recreate strings.xml file
            for (int i = 0; i < stringsName.size(); i++) {
                writeToFile(stringsName.get(i), convertedStringsContent.get(i), writer);
            }
            writer.write(RESOURCES_CLOSE);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to write information to a file to rebuild the strings.xml
     * file format for Android Studio
     *
     * @param name - the name of the string
     * @param content - the content converted to whatever language
     * @param writer - object to access a direct file
     * @throws IOException - if we have trouble writing to file
     */
    private static void writeToFile(String name, String content, BufferedWriter writer) throws IOException {
        writer.write("\t"+XML_PART_1+name+XML_PART_2+content+XML_PART_3+"\n");
    }

    /**
     * Helper method to write information to the screen.
     *
     * @param message - the text to display
     */
    private static void print(String message) {
        System.out.println(message);
    }
}
