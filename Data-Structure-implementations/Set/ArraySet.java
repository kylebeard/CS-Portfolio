/**
 * implementation of a set object using an array
 * @author kylebeard 
 */

import java.util.Iterator;

		@SuppressWarnings("unchecked")
public class ArraySet<T> implements Set<T> {
	public static final int CAPACITY_MULTIPLIER = 2;
	public static final int DEFAULT_CAPACITY = 15;

	private int capacity = 0;
	private int numberOfElements = 0;
	private T[] elements;

	/**
	 *  creates an arraySet with a default capacity of 15
	 */
	public ArraySet() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 *  creates arraySet that allows the size to be specified
	 * @param capacity
	 */
	public ArraySet(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity must be >= 0");
		}
		this.capacity = capacity;
		elements = (T[]) new Object[capacity];
	}

	/**
	 * Adds a new element to the set as long as it is not present in the set.
	 */
	@Override
	public void add(T element) {
		ensureCapacity();
		if (!(this.contains(element))) {
			elements[numberOfElements] = element;
			numberOfElements++;
		}
	}

	/**
	 * Adds an array of elements to the set as long as an element is not present
	 * in the set.
	 */
	@Override
	public void addAll(T[] elements) {
		for (int i = 0; i < elements.length; i++) {
			add(elements[i]);
		}

	}

	/**
	 * Determines whether a set contains a specified element.
	 *@return true if set contains element, false otherwise.
	 */
	@Override
	public boolean contains(T element) {
		if (indexOf(element) > -1)
			return true;
		else
			return false;
	}

	/**
	 * @return the size (in elements) in the set.
	 */
	@Override
	public int getSize() {
		return numberOfElements;
	}

	/**
	 * Removes the specified element from the set.
	 */
	@Override
	public void remove(T element) {
		int index = indexOf(element);

		if (index > -1) {
			numberOfElements--;
			elements[index] = elements[numberOfElements];
		}
		return;
	}

	/**
	 * Creates a new set that combines the contents if this set and anotherSet.
	 * If the same item occurs in each set, only one item appears in the new
	 * set.
	 *
	 * Both this set and anotherSet are unchanged.
	 *
	 * Returns the new set.
	 */
	@Override
	public Set<T> union(Set<T> anotherSet) {
		Set<T> returnSet = new ArraySet<T>();

		returnSet = anotherSet.difference(this);

		for (int i = 0; i < numberOfElements; i++) {
			if (this.contains(elements[i])) {
				returnSet.add(elements[i]);
			}
		}

		return returnSet;
	}

	/**
	 * Creates a new set that contains the objects that occur in both this set
	 * and anotherSet.
	 *
	 * Returns the new set.
	 */
	@Override
	public Set<T> intersection(Set<T> anotherSet) {
		Set<T> newSet = new ArraySet<T>();

		for (int i = 0; i < numberOfElements; i++)
			if (anotherSet.contains(elements[i])) {
				newSet.add(elements[i]);
			}
		return newSet;
	}

	/**
	 * Creates a new set containing the objects that would left in this set
	 * after removing those that also occur in anotherSet.
	 *
	 * Returns the new set.
	 */
	@Override
	public Set<T> difference(Set<T> anotherSet) {
		Set<T> newSet = new ArraySet<T>();

		for (int i = 0; i < numberOfElements; i++) {
			if (!(anotherSet.contains(elements[i]))) {
				newSet.add(elements[i]);
			}

		}
		return newSet;
	}

	/**
	 * Returns the index of a specified element, or -1 if the element is not
	 * present in the array.
	 */
	private int indexOf(T element) {
		int index = -1;
		for (int i = 0; i < numberOfElements; i++) {
			if (elements[i].equals(element)) {
				index = i;
				break;
			}
		}

		return index;
	}

	/**
	 * This ensures the array has sufficient capacity to store an additional
	 * element.
	 * 
	 * If the set is full, a new array is created that is CAPACITY_MULTIPLIER
	 * times larger than the original array. All existing elements are copied to
	 * the new array.
	 */
	private void ensureCapacity() {
		if (numberOfElements == capacity) {
			T[] newArray = (T[]) new Object[(numberOfElements + 1) * CAPACITY_MULTIPLIER];
			System.arraycopy(elements, 0, newArray, 0, numberOfElements);
			elements = newArray;
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new ArraySetIterator<T>();
	}

	private class ArraySetIterator<T> implements Iterator<T> {

		private int index = 0;
		
		/**
		 * checks if there is another element in the list
		 * 
		 * @return true if there is, false if not
		 */
		public boolean hasNext() {
			if (numberOfElements > index) {
				return true;
			} else {
				return false;
			}
		}
		/**
		 * returns next item in the list
		 */
		public T next() {
			if (hasNext()) {
				T nextItem = (T) elements[index]; //was saying "cannot convert from T to T"
												  //without cast
				index++;

				return nextItem;
			} else {
				throw new java.util.NoSuchElementException("No items remaining in the iteration.");
			}
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}