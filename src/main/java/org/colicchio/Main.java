package org.colicchio;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import org.jsoup.nodes.Element;

public class Main {
  public static void main(String[] args) throws IOException, CsvValidationException {

    /* Iteration 1: Data Flow Operations
     * Create a Maven project with OpenCSV working & read & print the contents
     * of a file (an action called parsing). */

    // CSV Parsing
    CsvParser csvParser = new CsvParser("src/main/resources/bookstore_report2.csv");
    csvParser.printCsv();
    System.out.println("\n\n"); // 3 lines between parsing groups

    // JSON parsing
    Author[] authors = JsonParser.readJson("src/main/resources/authors.json", Author[].class);
    for (Author author : authors) {
      System.out.println(
          author.getAuthorName() + " " + author.getAuthorEmail() + " " + author.getAuthorUrl());
    }
    System.out.println("\n\n"); // 3 lines between parsing groups

    // XML parsing
    for (Element e : XmlParser.readXml("src/main/resources/authors.xml").select("author")) {
      System.out.println(e.text());
    }
    // End of Iteration 1

    /* Iteration 2: Data Flow Operations
     * Add JDBC & GSON to your Maven project, read the contents of a CSV,
     * and insert this into the provided database.
     * You will need to insert into "author" from the json file (the three fields in the json file)
     * And "book" with some of the information from the csv file (see bottom) */
    csvParser.saveToDatabase();
    for (Author author : authors) {
      DatabaseManager.saveAuthor(author);
    }
    // Can't really do anything with the XML for iteration 2.
    // End of Iteration 2
  }
}
