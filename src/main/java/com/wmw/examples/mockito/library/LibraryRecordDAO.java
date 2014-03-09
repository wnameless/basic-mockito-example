package com.wmw.examples.mockito.library;

import java.util.List;

public interface LibraryRecordDAO {

  public List<LibraryRecord> findByBook(Book book);

  public boolean save(LibraryRecord record);

}
