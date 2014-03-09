package com.wmw.examples.mockito.library;

public interface LendingManager {

  public LibraryRecord borrowBook(Book book);

  public LibraryRecord returnBook(Book book);

}
