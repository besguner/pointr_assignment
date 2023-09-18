package io.cucumber.skeleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Belly {

  public static void main(String[] args) {
    List<TestData> tdList = new ArrayList<>();
    File file = new File("C:\\Users\\berk.esguner\\OneDrive - Accenture\\Desktop\\pointr\\maven\\src\\test\\config.txt");
    Scanner sc = null;
    try {
      sc = new Scanner(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    String username = "", password = "", searchTerm = "";
    while (sc != null && sc.hasNextLine()) {
      
      String nl = sc.nextLine();
      if (nl.isEmpty()) {
        tdList.add(new TestData(username, password, searchTerm));
      } else {
        if (nl.substring(0, 9).equals("Username:")) username =
          username =nl.substring(10, nl.length());
        if (nl.substring(0, 9).equals("Password:")) password =
          password=nl.substring(10, nl.length());
        if (nl.substring(0, 11).equals("SearchTerm:")) searchTerm =
          searchTerm=nl.substring(12, nl.length());
      }
    }
    System.out.println(tdList.get(0));
    System.out.println(tdList.get(1));
  }
}
