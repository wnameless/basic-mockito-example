package com.wmw.examples.mockito.library;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;

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

  @Mock
  LibraryRecord oldRecord;

  @Mock
  LibraryRecord newRecord;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(oldRecord.getBorrowingDate()).thenReturn(new Date());
    when(oldRecord.getReturningDate()).thenReturn(new Date());
    when(newRecord.getBorrowingDate()).thenReturn(new Date());
    when(dao.save(any(LibraryRecord.class))).thenReturn(true);
  }

  @Test
  public void testBorrowBook() {
    manager.borrowBook(book);
    verify(dao, times(1)).findByBook(book);
    verify(dao, times(1)).save(any(LibraryRecord.class));
  }

  @Test(expected = IllegalStateException.class)
  public void unreturnedBookCanNotBeBorrowed() {
    when(dao.findByBook(book)).thenReturn(Arrays.asList(oldRecord, newRecord));
    manager.borrowBook(book);
  }

  @Test(expected = IllegalStateException.class)
  public void bookCanNotBeBorrowedIfRecordUnsaved() {
    when(dao.save(any(LibraryRecord.class))).thenReturn(false);
    manager.borrowBook(book);
  }

  @Test
  public void testReturnBook() {
    when(dao.findByBook(book)).thenReturn(Arrays.asList(newRecord));
    manager.returnBook(book);
    verify(dao, times(1)).findByBook(book);
    verify(dao, times(1)).save(any(LibraryRecord.class));
  }

  @Test(expected = IllegalStateException.class)
  public void unborrowedBookCanNotBeReturned() {
    when(dao.findByBook(book)).thenReturn(Arrays.asList(oldRecord, newRecord));
    when(newRecord.getReturningDate()).thenReturn(new Date());
    manager.returnBook(book);
  }

  @Test(expected = IllegalStateException.class)
  public void emptyBorrowingDateIsNotAllowed() {
    when(dao.findByBook(book)).thenReturn(Arrays.asList(oldRecord));
    when(oldRecord.getBorrowingDate()).thenReturn(null);
    manager.borrowBook(book);
  }

  @Test(expected = IllegalStateException.class)
  public void moreThan1UnreturnedRecordIsNotAllowed() {
    when(dao.findByBook(book)).thenReturn(Arrays.asList(newRecord, newRecord));
    manager.returnBook(book);
  }

}
