// IBookManager.aidl
package com.example.binderdemo;

// Declare any non-default types here with import statements

import com.example.binderdemo.Book;

interface IBookManager {
  List<Book> getBookList();
  void addBook(in Book book);
}