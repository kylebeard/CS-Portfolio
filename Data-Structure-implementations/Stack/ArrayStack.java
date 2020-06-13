/**
 * Implementation of a stack using an array
 * @author kyle beard, joelee cherrington
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class ArrayStack<T> implements Stack<T> {
    private final static int DEFAULT_CAPACITY = 10;
    private final static int CAPACITY_MULTIPLIER = 2;
    private T[] elements;
    private int numberOfElements = 0;
    private int capacity = 0;

    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayStack(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be >= 0");
        }

        this.capacity = capacity;
        elements = (T[]) new Object[capacity];
    }

    @Override
    public void push(T item) {
        ensureCapacity();
        elements[numberOfElements] = item;
        numberOfElements++;
    }

    @Override
    public T pop() {
        if (numberOfElements == 0) {
            return null;
        } else {
            numberOfElements--;
            T item = elements[numberOfElements];
            return item;
        }
    }

    @Override
    public T peek() {
        if (numberOfElements == 0) {
            return null;
        } else {
            return elements[numberOfElements - 1];
        }
    }

    @Override
    public boolean isEmpty() {
        if (numberOfElements == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return numberOfElements;
    }
    
    //ensures the array has a large enough capacity
    private void ensureCapacity() {
        if (numberOfElements == elements.length) {
            T[] newArray = (T[]) new Object[(numberOfElements + 1) * CAPACITY_MULTIPLIER];
            System.arraycopy(elements, 0, newArray, 0, numberOfElements);
            elements = newArray;
        }
    }

}
