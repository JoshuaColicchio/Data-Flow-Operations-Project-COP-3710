package org.colicchio;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {

  /**
   * Saves an author to the Author table in the database.
   *
   * @param author The author object to save.
   * @return True if successful.
   */
  public static boolean saveAuthor(Author author) {
    Connection connection = null;
    PreparedStatement statement = null;
    boolean success = false;
    try {
      if ((connection = getConnection()) != null) {
        statement =
            connection.prepareStatement(
                "INSERT INTO author (author_name, author_email, author_url) VALUES (?,?,?)");
        statement.setString(1, author.getAuthorName());

        if (author.getAuthorEmail().isBlank() || author.getAuthorEmail().isEmpty()) {
          statement.setString(2, author.getAuthorName() + " did not provide an email.");
        } else {
          statement.setString(2, author.getAuthorEmail());
        }

        if (author.getAuthorUrl().isBlank() || author.getAuthorUrl().isEmpty()) {
          statement.setString(3, author.getAuthorName() + " did not provide a URL.");
        } else {
          statement.setString(3, author.getAuthorUrl());
        }

        statement.executeUpdate();
        success = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        System.out.println("Error closing database connections. Reason: " + e.getMessage());
      }
    }
    return success;
  }

  /**
   * Saves the book object from the CSV row.
   *
   * @param row The CSV row the book details are taken from.
   * @return True if successful.
   */
  public static boolean saveCsv(String[] row) {
    Connection connection = null;
    PreparedStatement statement = null;
    boolean success = false;
    try {
      if ((connection = getConnection()) != null) {
        // First we save the book details
        statement =
            connection.prepareStatement(
                "INSERT OR IGNORE INTO book (isbn, publisher_name, author_name, book_title) VALUES (?,?,?,?)");

        // row represents [isbn, title, author, publisher, store, location]
        statement.setString(1, row[0]); // ISBN
        statement.setString(2, row[3]); // Publisher
        statement.setString(3, row[2]); // Author
        statement.setString(4, row[1]); // Title
        statement.executeUpdate();
        success = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        System.out.println("Error closing database connections. Reason: " + e.getMessage());
      }
    }
    return success;
  }

  private static Connection getConnection() {
    Connection connection = null;
    if (Files.exists(Paths.get("src/main/resources/bookstore.db"))) { // Ensure the DB exists
      try {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/bookstore.db");
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    }
    return connection;
  }
}
