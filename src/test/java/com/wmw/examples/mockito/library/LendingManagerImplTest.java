/**
 *
 * @author Wei-Ming Wu
 *
 *
 * Copyright 2014 Wei-Ming Wu
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package com.wmw.examples.mockito.library;

import static org.mockito.Matchers.any;
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
  }

  @Test(expected = IllegalStateException.class)
  public void bookCanNotBeReturnedIfRecordIsNotUpdated() {
    when(dao.findByBook(book)).thenReturn(Arrays.asList(oldRecord, newRecord));
    when(dao.save(any(LibraryRecord.class))).thenReturn(false);
    manager.returnBook(book);
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
