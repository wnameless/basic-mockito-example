package com.wmw.examples.mockito.library;

import java.util.Date;

public class LibraryRecord {

  private long id;

  private Book book;

  private Date borrowingDate;

  private Date returningDate;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public Date getBorrowingDate() {
    return borrowingDate;
  }

  public void setBorrowingDate(Date borrowingDate) {
    this.borrowingDate = borrowingDate;
  }

  public Date getReturningDate() {
    return returningDate;
  }

  public void setReturningDate(Date returningDate) {
    this.returningDate = returningDate;
  }

}
