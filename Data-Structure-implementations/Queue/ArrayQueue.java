
/**
 * Queue implementation using an array
 * 
 * @author kylebeard, max
 */
import java.util.Stack;

public class ArrayQueue<T> implements Queue<T> {
	private Stack<T> stack = new Stack<T>();

	private static final int DEFAULT_CAPACITY = 10;
	private static final int CAPACITY_MULTIPLIER = 2;
	private T[] elements;
	private int numberOfElements = 0;
	private int front = 0;
	private int rear = 0;

	// constructs empty queue with a capacity of 10
	public ArrayQueue() {
		this(DEFAULT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public ArrayQueue(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity must be >= 0");
		}

		elements = (T[]) new Object[capacity];
	}

	/**
	 * adds item to queue and wraps around to 0 if rear is at the end of the
	 * queue
	 */
	@Override
	public void add(T item) {
		ensureCapacity();
		elements[rear] = item;
		numberOfElements++;
		if (rear == elements.length - 1) {
			rear = 0;
		} else {
			rear++;
		}
	}

	/**
	 * removes item at the front of the queue
	 * 
	 * @return removed item or null if the queue is empty
	 */
	@Override
	public T remove() {
		if (numberOfElements == 0) {
			return null;
		} else {
			T item = elements[front];
			if (front == elements.length - 1) {
				front = 0;
			} else {
				front++;
			}
			numberOfElements--;
			return item;
		}
	}

	/**
	 * @return true if queue is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		if (numberOfElements == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the number of elements in the queue
	 */
	@Override
	public int size() {
		return numberOfElements;
	}

	/**
	 * ensures the queue never runs out of space
	 * 
	 * @return a new queue double the size with front at 0 and rear at
	 *         numberOfElements
	 */
	public void ensureCapacity() {
		if (numberOfElements == elements.length) {
			Object[] newArray = new Object[(numberOfElements + 1) * CAPACITY_MULTIPLIER];
			int index = 0;
			newArray[index] = elements[front];
			index++;
			front++;
			while (index < numberOfElements) {
				if (front == elements.length) {
					front = 0;
				}
				newArray[index] = elements[front];
				index++;
				front++;
			}
			elements = (T[]) newArray;
			rear = numberOfElements;
			front = 0;
		}
	}
}
