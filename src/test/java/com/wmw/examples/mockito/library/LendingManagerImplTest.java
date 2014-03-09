package com.wmw.examples.mockito.library;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class LendingManagerImplTest {

  LendingManagerImpl manager;

  LibraryRecordDAO dao;

  Book book;

  @Before
  public void setUp() throws Exception {
    manager = new LendingManagerImpl();
    dao = new LibraryRecordDAO() {

      @Override
      public List<LibraryRecord> findByBook(Book book) {
        return new ArrayList<LibraryRecord>();
      }

      @Override
      public boolean save(LibraryRecord record) {
        return true;
      }

    };
    manager.setLibraryRecordDAO(dao);
    book = new Book();
  }

  @Test
  public void testBorrowBook() {
    manager.borrowBook(book);
  }

}
