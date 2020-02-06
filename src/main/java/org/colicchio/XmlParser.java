package org.colicchio;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XmlParser {

  /**
   * Function that parses the specified XML file into a Document object.
   *
   * @param xmlFile The path of the XML file to read.
   * @return The Document object parsed from the XML file. Null if the file does not exist.
   */
  public static Document readXml(String xmlFile) throws IOException {
    Document doc = null;
    if (checkFile(xmlFile)) {
      InputStream inputStream = new FileInputStream(xmlFile);
      doc = Jsoup.parse(inputStream, null, "", Parser.xmlParser());
    }
    return doc;
  }

  /**
   * Checks whether or not the given file exists
   *
   * @param xmlFile The path to the file to check.
   * @return True if it exists
   */
  private static boolean checkFile(String xmlFile) {
    boolean exists = true;
    if (!Files.exists(Paths.get(xmlFile))) {
      exists = false;
      System.out.println("The specified XML file does not exist.");
    }
    return exists;
  }
}
