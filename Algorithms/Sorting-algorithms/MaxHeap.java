/**
 * MaxHeap.java
 * Implementation of the HeapInterface as a maximum heap (max heap)
 * 
 * @author Kyle Beard
 * @param <T>
 */
public class MaxHeap<T extends Comparable<? super T>> implements HeapInterface<T> {
  private T[] elements;
  private int numberOfElements;
  private int capacity; // current capacity of the heap
  private static final int DEFAULT_CAPACITY = 15;

  public MaxHeap() {
    this(DEFAULT_CAPACITY);
  }

  @SuppressWarnings("unchecked")
  public MaxHeap(int capacity) {
    this.capacity = capacity;
    elements = (T[]) new Comparable[capacity + 1];
  }

  /**
   * Adds the passed item into the heap
   */
  public void add(T item) {
    ensureCapacity();
    int index = numberOfElements + 1;
    if (isEmpty()) {
      elements[1] = item;
    } else {
      elements[index] = item;
      addHelper(item, index);
    }
    numberOfElements++;
  }

  /**
   * Recursive algorithm for adding a passed item into the heap
   * 
   * @param item
   * @param index
   */
  private void addHelper(T item, int index) {
    T temp = null;

    if (index > 1 && elements[index].compareTo(elements[index / 2]) > 0) {
      temp = elements[index / 2];
      elements[index / 2] = elements[index];
      elements[index] = temp;
      addHelper(item, index / 2);
    } else {
      elements[index] = item;
    }
  }

  /**
   * Removes the item at the top of the heap
   */
  public T remove() {
    T item = null;
    if (!isEmpty()) {
      item = elements[1];
      elements[1] = elements[numberOfElements];
      elements[numberOfElements] = null;
      numberOfElements--;
      removeHelper(elements[1], 1);
    }
    return item;
  }

  /**
   * Recursive algorithm that removes the item at the top of the heap
   * 
   * @param item
   * @param index
   * @return the item removed
   */
  private T removeHelper(T item, int index) {
    T temp = null;

    if (index * 2 <= numberOfElements && elements[index * 2] != null) { // left child
      if (index * 2 + 1 <= numberOfElements && elements[(index * 2) + 1] != null) { // right
                                                                                    // child
        // left child is > right child
        if (elements[index * 2].compareTo(elements[(index * 2) + 1]) > 0) {
          if (index * 2 <= numberOfElements && elements[index].compareTo(elements[index * 2]) < 0) {
            temp = elements[index * 2];
            elements[index * 2] = elements[index];
            elements[index] = temp;
            removeHelper(item, index * 2);
          }
          // right child is > left child
        } else {
          if (index * 2 + 1 <= numberOfElements && elements[index].compareTo(elements[index * 2 + 1]) < 0) {
            temp = elements[(index * 2) + 1];
            elements[(index * 2) + 1] = elements[index];
            elements[index] = temp;
            removeHelper(item, (index * 2) + 1);
          }
        }
      } else {// left child is only child
        if (elements[index].compareTo(elements[index * 2]) < 0) {
          temp = elements[index * 2];
          elements[index * 2] = elements[index];
          elements[index] = temp;
          removeHelper(item, index * 2);
        }
      }

    }
    return elements[index];
  }

  public T front() {
    if (isEmpty())
      return null;
    else
      return elements[1];
  }

  public boolean isEmpty() {
    if (numberOfElements == 0) {
      return true;
    } else {
      return false;
    }
  }

  public int getSize() {
    return numberOfElements;
  }

  private void ensureCapacity() {
    // The following is a starting point
    if (numberOfElements + 1 == capacity) {
      elements = java.util.Arrays.copyOf(elements, 2 * elements.length);
      capacity = elements.length;
    }
  }
}
