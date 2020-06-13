
import java.awt.ScrollPaneAdjustable;
import java.io.StringReader;

/**
 * Implementation of the List interface.
 * 
 * This implementation involves a single-linked list.
 * 
 * @author Kyle Beard
 *
 */
public class LinkedList<T> implements List<T> {
	// reference to the head of the linked list
	private Node head;

	// number of elements in the list
	private int numberOfElements;

	public LinkedList() {
		head = null;
	}

	/**
	 * Inner class representing a node in the linked list
	 */

	private class Node {
		private T data;
		private Node next;

		private Node(T data) {
			this(data, null);
		}

		private Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

	// Methods

	@Override
	public void add(T item) {
		// adds (appends) an item to the rear of the list

		Node newNode = new Node(item);
		Node current = head;

		if (isEmpty()) {
			// special case - first element being added to the list
			head = newNode;
		} else {
			while (current.next != null) {
				current = current.next;
			}

			// current now references the last item in the list
			current.next = newNode;
		}

		newNode.next = null;
		++numberOfElements;
	}

	@Override
	public boolean add(T item, int index) {

		// adds item to specified index

		Node newNode = new Node(item);
		Node current = head;

		if (isEmpty()) {
			// special case - adding first item to list
			head = newNode;
			numberOfElements++;
		} else if (index == 0) {
			// special case - adding item to head of the list
			newNode.next = head;
			head = newNode;
			numberOfElements++;
		} else {
			int currIndex = 0;

			while (currIndex < (index - 1)) {
				current = current.next;
				currIndex++;

			}
			newNode.next = current.next;
			current.next = newNode;
			numberOfElements++;
			return true;
		}
		return false;
	}

	@Override
	public boolean contains(T item) {
		Node current = head;
		boolean found = false;

		while (current != null && !found) {
			if (current.data.equals(item)) {
				found = true;
			}
			current = current.next;
		}
		return found;
	}

	@Override
	public T get(int index) {
		Node current = head;
		T obj = null;

		if (isEmpty() || index >= numberOfElements) {
			// special cases - list is empty or index is > size
			return null;
		} else {
			// loop through until i == index, then store current.data in obj
			for (int i = 0; i <= index; i++) {
				if (i == index) {
					obj = current.data;
				}
				current = current.next;
			}
		}

		return obj;
	}

	@Override
	public boolean remove(T item) {
		Node current = head;

		// special cases - if list is empty or size 1
		if (isEmpty()) {
			return false;
		} else if (head.next == null && head.data.equals(item)) {
			head = null;
			numberOfElements--;
			return true;
		}

		while (current.next != null) {
			if (head.data.equals(item)) {
				// special case - removing head
				head = head.next;
				numberOfElements--;
				return true;
			} else if (current.next.data.equals(item)) { // check if next nodes
															// data = item
				current.next = current.next.next;
				numberOfElements--;
				return true;
			} else {
				current = current.next;
			}

		}
		return false;
	}

	@Override
	public T remove(int index) {
		T rv = null;

		if (isEmpty() || index >= numberOfElements) {
			rv = null;
		} else if (index == 0) {
			// special case - first element in the list
			rv = head.data;
			head = head.next;
			numberOfElements--;
		} else {
			int currentIndex = 0;
			Node current = head;

			while (currentIndex < (index - 1)) {
				current = current.next;
				currentIndex++;
			}

			// current references the node we want to remove
			rv = current.next.data;
			current.next = current.next.next;
			numberOfElements--;
		}

		return rv;
	}

	@Override
	public int getLength() {
		return numberOfElements;
	}

	@Override
	public boolean isEmpty() {
		if (numberOfElements == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int getFrequency(T item) {
		int count = 0;
		Node current = head;

		if (isEmpty()) // special case - list is empty
			return count;

		if (head.data.equals(item) && head.next == null) // special case - counting the head
			count++;

		while (current.next != null) {
			if (current.data.equals(item)) {
				count++;
			}

			current = current.next;
		}
		if (current.data.equals(item)) // special case - item is last in the list
			count++;
		return count;
	}

	@Override
	public void clear() {
		head = null;
		numberOfElements = 0;
	}

}
