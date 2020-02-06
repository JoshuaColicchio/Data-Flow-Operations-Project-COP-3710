package org.colicchio;

public class Author {
  private String author_name;
  private String author_email;
  private String author_url;

  protected void setAuthorName(String newName) {
    author_name = newName;
  }

  protected void setAuthorEmail(String newEmail) {
    author_email = newEmail;
  }

  protected void setAuthorUrl(String newUrl) {
    author_url = newUrl;
  }

  protected String getAuthorName() {
    return author_name;
  }

  protected String getAuthorEmail() {
    return author_email;
  }

  protected String getAuthorUrl() {
    return author_url;
  }
}
