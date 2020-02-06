package org.colicchio;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonParser {

  /**
   * Function that reads the specified JSON file, if it exists, and returns a list of objects of the
   * specified type.
   *
   * @param jsonFile The path of the JSON file to read.
   * @param TypeOfT The type of Object to parse JSON Objects as.
   * @return The list of parsed objects. Null if the file did not exist.
   */
  public static <T> T[] readJson(String jsonFile, Type TypeOfT) throws FileNotFoundException {
    T[] returnVal = null;
    if (checkFile(jsonFile)) {
      Gson gson = new Gson();
      JsonReader jsonReader = new JsonReader(new FileReader(jsonFile));
      returnVal = gson.fromJson(jsonReader, TypeOfT);
    }
    return returnVal;
  }

  /**
   * Checks whether or not the given file exists
   *
   * @param jsonFile The path to the file to check.
   * @return True if it exists
   */
  private static boolean checkFile(String jsonFile) {
    boolean exists = true;
    if (!Files.exists(Paths.get(jsonFile))) {
      exists = false;
      System.out.println("The specified JSON file does not exist.");
    }
    return exists;
  }
}
