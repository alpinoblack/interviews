package com.example.interviews.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ParserQuestion {

  /**
   * Please review the class before deployment in production below and suggest improvements. There
   * are many problems here, both high-level design mistakes,and low-level implementation bugs.
   */
  public class Parser {

    private File file;

    public synchronized void setFile(File f) {
      file = f;
    }

    public synchronized File getFile() {
      return file;
    }

    public String getFileContent() throws IOException {
      InputStream i = new FileInputStream(file);
      String output = "";
      int data;
      while ((data = i.read()) > 0) {
        output += (char) data;
      }
      return output;
    }

    public String getFileContentWithoutUnicode() throws IOException {
      InputStream i = new FileInputStream(file);
      String output = "";
      int data;
      while ((data = i.read()) > 0) {
        if (data < 0x80) {
          output += (char) data;
        }
      }
      return output;
    }

    public void saveFileContent(String content) throws IOException {
      OutputStream o = new FileOutputStream(file);
      for (int i = 0; i < content.length(); i += 1) {
        o.write(content.charAt(i));
      }
    }
  }

  public static class MyParser {

    private final File file; //no more synchronized, file can't be changed

    public File getFile() {
      return file;
    }

    public MyParser(final File file) {
      this.file = file; //file is part of the object and can't be changed
    }

    public String getFileContent() throws IOException {
      return getFileContentInternal(value -> true); //don't filter, always return true
      //not a lot of difference
      //between getFileContent() and getFileContentWithoutUnicode()
      //can use a single function to implement each gets a different
      //lambda
    }

    public String getFileContentWithoutUnicode() throws IOException {
      return getFileContentInternal(value -> value < 0x80); //filter only things above 0x80
    }

    private String getFileContentInternal(final Predicate<Integer> charFilter) throws IOException {
      try (final InputStream i = new FileInputStream(file)) { //try-with-resources so now the
        // resource is automatically closed
        final var output = new StringBuilder(i.available()); //StringBuilder instead of always creating
        // a new string, can also give it initial capacity based on the file size (available())
        int data;
        while ((data = i.read()) > 0) {
          if (charFilter.test(data)) { //using the lambda
            output.append((char) data); //instead of reading it byte by byte,
            //can read id in batches
          }
        }
        return output.toString(); //only when finished generate the string and return
      }
    }

    public void saveFileContent(String content) throws IOException {
      try (final OutputStream o = new FileOutputStream(file)) { //try with resources
        for (final char c: content.toCharArray()) {//simple foreach loop instead of while loop
          o.write(c); //also writing in batches
        }
      }
    }
  }

}
