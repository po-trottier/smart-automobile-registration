package structures;

import javafx.util.Pair;
import java.util.ArrayList;

public class Sequence {
  private LinkedNode head = null;

  public void migrate(HashMap map) {
    // TODO Iterate through every entry of the map and add a new LinkedNode for it
  }

  public Pair[] get(String key) {
    // If we don't have a head then we can't get anything
    if (this.head == null)
      return null;
    // Iterate through every element until you find the right key
    LinkedNode iterator = this.head;
    while (!iterator.getKey().equals(key)) {
      if (iterator.isLast())
        return null;
      iterator = iterator.getNext();
    }
    // Return the element found's values
    return iterator.getValues();
  }

  public void add(String key, Pair<String, Object> value) {
    // Reserve the "History" keyword for the program
    if (value.getKey().equals("History"))
      throw new RuntimeException("\"History\" is a reserved keyword. Please use another name.");
    // If we don't have a head, create it
    if (this.head == null) {
      this.head = new LinkedNode(key);
      this.head.addValue(value);
      return;
    }
    // Otherwise find the tail or the existing key
    LinkedNode iterator = this.head;
    while (!iterator.isLast()) {
      // If the key exists the simply add the data
      if (iterator.getKey().equals(key)) {
        iterator.addValue(value);
        return;
      }
      iterator = iterator.getNext();
    }
    // Set the tail's next pointer to a new node, making the new node the new tail
    LinkedNode newNode = new LinkedNode(key);
    newNode.addValue(value);
    iterator.setNext(newNode);
  }

  public void remove(String key) {
    // If we don't have a head then we can't remove anything
    if (this.head == null)
      return;
    // Iterate through every element until your find the right key
    LinkedNode iterator = this.head;
    while (!iterator.getKey().equals(key)) {
      if (iterator.isLast())
        return;
      iterator = iterator.getNext();
    }
    // Set the history for that key and remove the current values
    iterator.remove();
  }

  public String[] allKeys() {
    // If we don't have a head then we can't return anything
    if (this.head == null)
      return null;
    // Iterate through every element and add all the keys to an ArrayList
    LinkedNode iterator = this.head;
    ArrayList<String> keys = new ArrayList<>();
    while (!iterator.isLast()) {
      keys.add(iterator.getKey());
      iterator = iterator.getNext();
    }
    // Add the last element's key to the list
    keys.add(iterator.getKey());
    // Return the sorted array list in the form of an Array
    return sortKeys(keys);
  }

  public String nextKey(String key) {
    // If we don't have a head then we can't get anything
    if (this.head == null)
      return null;
    // Iterate through every element until you find the right key
    LinkedNode iterator = this.head;
    while (!iterator.getKey().equals(key)) {
      // If this is the last element, then there is no next key
      if (iterator.isLast())
        return null;
      iterator = iterator.getNext();
    }
    // Return the next element's key
    return iterator.getNext().getKey();
  }

  public String prevKey(String key) {
    // If we don't have a head then we can't get anything
    if (this.head == null)
      return null;
    // Iterate through every element until you find the right key
    LinkedNode iterator = this.head;
    LinkedNode lastIterator = null;
    while (!iterator.getKey().equals(key)) {
      // If this is the last element and the key didn't match the the key doesn't exist
      if (iterator.isLast())
          return null;
      lastIterator = iterator;
      iterator = iterator.getNext();
    }
    // If lastIterator is null, the head is the only element and there's not previous
    if (lastIterator == null)
      return null;
    // Return the previous element's key
    return lastIterator.getKey();
  }

  public boolean isEmpty() {
    // Return whether the sequence is empty or not
    return this.head.isEmpty() && this.head.isLast();
  }

  private String[] sortKeys(ArrayList<String> keys) {
    // Make the ArrayList a regular Array
    String[] sorted = keys.toArray(new String[0]);
    int size = sorted.length;
    // Heap Sort the Array
    for (int i = size / 2 - 1; i >= 0; i--)
      heapify(sorted, size, i);
    for (int i = size - 1; i >= 0; i--) {
      String x = sorted[0];
      sorted[0] = sorted[i];
      sorted[i] = x;
      heapify(sorted, i, 0);
    }
    return sorted;
  }

  private void heapify(String[] array, int heapSize, int i) {
    // Initialize largest as root
    int largest = i;
    // Index of the left child
    int left  = 2 * i + 1;
    // Index of the right child
    int right  = 2 * i + 2;
    // If left child is larger than root
    if (left < heapSize && compareStrings(array[left], array[largest]))
      largest = left ;
    // If right child is larger than largest so far
    if (right < heapSize && compareStrings(array[right], array[largest]))
      largest = right ;
    // If largest is not root
    if (largest != i) {
      String swap = array[i];
      array[i] = array[largest];
      array[largest] = swap;
      // Recursive call to  heapify the sub-tree
      heapify(array, heapSize, largest);
    }
  }

  private boolean compareStrings(String a, String b) {
    // Returns true if string "a" is 'bigger' than string "b"
    for (int i = 0; i < a.length(); i++) {
      if (a.charAt(i) > b.charAt(i))
        return true;
      else if (a.charAt(i) < b.charAt(i))
        return false;
    }
    return a.length() < b.length();
  }
}
