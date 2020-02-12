package org.colicchio;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

  // List that holds all fields from the csv file
  private List fileRows = new ArrayList();

  /**
   * Function that reads the provided CSV file using OpenCSV.
   *
   * @param inFile The CSV file to read from.
   */
  public CsvParser(String inFile) throws IOException, CsvValidationException {
    if (checkFile(inFile)) { // Make sure the file exists
      FileInputStream csvStream = new FileInputStream(inFile);
      InputStreamReader inputStream = new InputStreamReader(csvStream, StandardCharsets.UTF_8);
      CSVReader reader = new CSVReader((inputStream));

      String[] nextLine;
      while ((nextLine = reader.readNext()) != null) {
        fileRows.add(nextLine);
      }

      // Close the reader when finished.
      reader.close();
    }
  }

  protected void saveToDatabase() {
    if (fileRows.size() == 0) { // No data loaded
      System.out.println("No data found in the specified CSV file.");
    } else {
      int rowNumber = 1; // Current row being saved.
      for (Object row : fileRows.subList(1, fileRows.size() - 1)) {
        if (!DatabaseManager.saveCsv((String[]) row)) {
          System.out.println(
              String.format("Error saving CSV file to database. (Row %d)", rowNumber));
        }
        rowNumber++;
      }
    }
  }

  protected void printCsv() {
    for (Object row : fileRows) {
      for (String fields : (String[]) row) {
        System.out.print(fields + ", ");
      }
      System.out.println("\b\b\n---------------------");
    }
  }

  /**
   * Function that checks if the specified CSV file exists.
   *
   * @param csvFilePath The path to the CSV file to check.
   * @return True, if the file exists.
   */
  private boolean checkFile(String csvFilePath) {
    boolean exists = true;
    if (!Files.exists(Paths.get(csvFilePath))) {
      exists = false;
      System.out.println("Specified CSV file does not exist.");
    }
    return exists;
  }
}
