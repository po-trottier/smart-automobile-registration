package tests;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.AbstractMap.SimpleEntry;
import structures.SmartAR;

public class Utils {
  public static void title(String text) {
    System.out.println("\n===========================================");
    System.out.println(text);
    System.out.println("===========================================");
  }

  static String[] readFile(String file) {
    // Read the file in the "TestFiles" package
    var path = Paths.get("TestFiles", file);
    try {
      // Return an array of strings where each element is a line in the file
      return Files.readString(path).split("\\r?\\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
    // If something went wrong then return an empty array
    return new String[0];
  }

  static void populateAR(SmartAR ar, String[] content, int i) {
    // For every Key in the File, add an element in the structures.SmartAR
    for (String elem : content) {
      // Add the new element to the structures.SmartAR with the value <KEY>
      ar.add(elem, new SimpleEntry<>("Name", elem));
    }
    System.out.println("SmartAR #" + i + " Contains " + ar.allKeys().length + " Entries");
  }

  static void getSortedKeys(SmartAR ar, int i) {
    System.out.println("Test File #" + i + " Keys:");
    String outString = Arrays.toString(ar.allKeys());
    // Print part of the sorted keys array
    if (outString.length() > 90)
      System.out.println(outString.substring(0, 80) + "...]");
    else
      System.out.println(outString);
  }

  static ArrayList<String> removeRandom(SmartAR ar) {
    // Create an ArrayList of removed items
    ArrayList<String> removed = new ArrayList<>();
    // Get all the keys in the current structures.SmartAR
    String[] keys = ar.allKeys();
    // If we have less than 100 elements then return
    if (keys.length < 100)
      return null;
    // Otherwise remove 100 elements and add their key to the removed ArrayList
    for (int i = 0; i < 100; i++) {
      // The elements are chosen randomly
      removed.add(keys[new Random().nextInt(keys.length)]);
      ar.remove(removed.get(i));
    }
    return removed;
  }

  static void addElements(SmartAR ar, ArrayList<String> elements) {
    // If we don't have any removed elements then don't add anything
    if (elements == null)
      return;
    for (String element : elements) {
      // Generate a random number to append
      int random = new Random().nextInt(10000);
      // Add the \"new\" element to the structures.SmartAR with the value Name=Truck<RANDOM>
      ar.add(element, new SimpleEntry<>("NewName", "Truck" + random));
    }
  }

  static void getRemoved(SmartAR ar, ArrayList<String> elements, int i) {
    System.out.println("Re-Added Element #" + i + " Example:");
    if (elements == null || elements.size() < 1)
      System.out.println("[]");
    else
      System.out.println(Arrays.toString(ar.getValues(elements.get(0))));
  }

  static void getHistory(SmartAR ar, ArrayList<String> elements, int i) {
    System.out.println("Car History #" + i + " Example:");
    if (elements == null || elements.size() < 1)
      System.out.println("[]");
    else
      System.out.println(Arrays.toString(ar.prevCars(elements.get(0))));
  }

  static void moveForward(SmartAR ar, int i) {
    System.out.println("Iteration for structures.SmartAR #" + i);
    // Move forward 4 times
    String key = ar.firstKey();
    for (int j = 0; j < 5; j++) {
      if (key == null) {
        System.out.println("Nothing to iterate on...");
        return;
      }
      var value = ar.getValues(key);
      System.out.print(j + "(" + value[value.length - 1] + (j == 4 ? ")\n" : "), "));
      key = ar.nextKey(key);
    }
  }

  static void moveBackwards(SmartAR ar, int i) {
    System.out.println("Iteration for structures.SmartAR #" + i);
    String key = ar.firstKey();
    // Move forward so we can then move backwards
    for (int j = 0; j < 10; j++) {
      key = ar.nextKey(key);
    }
    // Move backwards
    for (int j = 4; j >= 0; j--) {
      if (key == null) {
        System.out.println("Nothing to iterate on...");
        return;
      }
      var value = ar.getValues(key);
      System.out.print(j + "(" + value[value.length - 1] + (j == 0 ? ")\n" : "), "));
      key = ar.prevKey(key);
    }
  }

  static void addNewItems(SmartAR ar, int i) {
    System.out.println("Adding 50 items to SmartAR #" + i);
    String[] newKeys = ar.generate(50);
    for (String key : newKeys) {
      int random = new Random().nextInt(10000);
      ar.add(key, new SimpleEntry<>("UniqueCar", "UniqueCar" + random));
    }
    if (ar.getValues(newKeys[0]) == null)
      System.out.println("Something went wrong while generating unique keys...");
    else
      System.out.println(Arrays.toString(ar.getValues(newKeys[0])) + " and more...");
  }
}
