package com.wmw.examples.mockito.library;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LendingManagerImplTest {

  @InjectMocks
  LendingManagerImpl manager;

  @Mock
  LibraryRecordDAO dao;

  @Mock
  Book book;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testBorrowBook() {
    when(dao.save(any(LibraryRecord.class))).thenReturn(true);
    manager.borrowBook(book);
  }

}
