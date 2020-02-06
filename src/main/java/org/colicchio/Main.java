package org.colicchio;

import com.opencsv.exceptions.CsvValidationException;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException, CsvValidationException {
    // CSV parsing
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
  }
}
