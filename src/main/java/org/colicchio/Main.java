package org.colicchio;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.opencsv.exceptions.CsvValidationException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import java.io.*;

public class Main {
  public static void main(String[] args) throws IOException, CsvValidationException {

    // =================================== CSV PARSING ==================================
    System.out.println("Begin CSV parsing");
    CsvParser csvParser = new CsvParser("src/main/resources/bookstore_report2.csv");
    csvParser.printCsv();
    System.out.println("End CSV parsing\n");

    // =================================== JSON PARSING =================================
    Gson gson = new Gson();
    JsonReader jread = new JsonReader(new FileReader("src/main/resources/authors.json"));
    AuthorParser[] authors = gson.fromJson(jread, AuthorParser[].class);

    System.out.println("Begin JSON parsing");
    for (var element : authors) {
      System.out.println(
          element.getAuthorName() + " " + element.getAuthorEmail() + " " + element.getAuthorUrl());
      System.out.println("----------------------------");
    }
    System.out.println("End JSON parsing\n");

    // ================================= HTML/XML PARSING ===============================
    System.out.println("Begin HTML/XML parsing");
    // Not doing any error checking because this will most likely be moved into its own class later
    // and I'll do it there, this is really just to see how Jsoup works
    InputStream inStream = new FileInputStream("src/main/resources/authors.xml");
    Document doc = Jsoup.parse(inStream, null, "", Parser.xmlParser());
    for (Element e : doc.select("author")) {
      System.out.println(e.text());
    }
    System.out.println("End HTML/XML parsing");
  }
}
