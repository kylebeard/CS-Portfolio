
/**
 * Dictionary implementation using Seperate Chaining
 * 
 * @author Kyle Beard
 *
 * @param <K>
 * @param <V>
 */

public class HashDictionaryChained<K, V> implements DictionaryInterface<K, V> {

	// hash table
	private List<TableElement<K, V>>[] dictionary;

	// initial size of hash table
	private static int DEFAULT_SIZE = 13;

	// the number of elements in the hash table
	private int numberOfElements;

	// the current capacity of the hash table
	// this is a prime number
	private int currentCapacity;

	/**
	 * Inner class representing an element in the hash table This consists of a [Key:Value] mapping
	 *
	 * @param <K>
	 *            Key
	 * @param <V>
	 *            Value
	 */
	@SuppressWarnings("hiding")
	private class TableElement<K, V> {
		private K key;
		private V value;
		protected boolean isRemoved;

		private TableElement(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Two TableElement objects are equals if they both have the same key
		 */
		@SuppressWarnings("unchecked")
		public boolean equals(Object other) {

			if (other instanceof TableElement) {
				TableElement<K, V> candidate = (TableElement<K, V>) other;

				if ((this.getKey()).equals(candidate.getKey()))
					return true;
			}

			return false;
		}

		private K getKey() {
			return key;
		}

		private V getValue() {
			return value;
		}

		private void setKey(K key) {
			this.key = key;
		}

		private void setValue(V value) {
			this.value = value;
		}
	}

	public HashDictionaryChained() {
		this(DEFAULT_SIZE);
	}

	@SuppressWarnings("unchecked")
	public HashDictionaryChained(int size) {
		if (size < 0)
			throw new IllegalArgumentException();

		// dictionary = (TableElement<K, V>[]) new TableElement[size];
		dictionary = (LinkedList<TableElement<K, V>>[]) new LinkedList[size];
		numberOfElements = 0;
		currentCapacity = size;
	}

	/**
	 * Returns the hash value in the range [0 .. currentCapacity-1]
	 * 
	 * @param key
	 * @return int
	 */
	private int hashValue(K key) {
		return (Math.abs(key.hashCode()) % currentCapacity);
	}

	@Override
	public V put(K key, V value) {
		TableElement<K, V> element = new TableElement<K, V>(key, value);

		int hash = hashValue(key);

		if (dictionary[hash] == null) {
			dictionary[hash] = new LinkedList<TableElement<K, V>>();
		}
		// add element to the table
		if (dictionary[hash].getLength() == 0) { // empty list
			dictionary[hash].add(element);
			numberOfElements++;

		} else {
			for (int i = 0; i < dictionary[hash].getLength(); i++) {
				if (dictionary[hash].get(i).key.equals(key)) { // just updating the value don't increment # of elements
					dictionary[hash].add(element, i);
					return value;
				}
			}
			dictionary[hash].add(element);
			numberOfElements++;
		}

		return value;
	}

	@Override
	public V get(K key) {
		V value = null;
		int hash = hashValue(key);

		if (dictionary[hash] == null) // hash location is empty
			return null;

		// loop through the linked list checking for the key
		// and return the value if found
		for (int i = 0; i < dictionary[hash].getLength(); i++) {
			if (dictionary[hash].get(i).key.equals(key))
				return dictionary[hash].get(i).value;
		}

		return null;
	}

	@Override
	public boolean contains(K key) {
		int hash = hashValue(key);

		if (dictionary[hash] == null) // list is empty
			return false;

		// loop through list checking for the key
		for (int i = 0; i < dictionary[hash].getLength(); i++) {
			if (dictionary[hash].get(i).key.equals(key))
				return true;
		}
		return false;
	}

	@Override
	public V remove(K key) {
		int hash = hashValue(key);
		V value = null;

		if (dictionary[hash] == null)
			return null;

		// loop through the list checking for the key
		for (int i = 0; i < dictionary[hash].getLength(); i++) {
			if (dictionary[hash].get(i).key.equals(key)) {
				value = dictionary[hash].get(i).value;
				dictionary[hash].remove(i);
				numberOfElements--;
				return value;
			}
		}
		return null;
	}

	@Override
	public int size() {
		return numberOfElements;
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new ArraySet<K>();

		if (numberOfElements == 0) // special case - dictionary is empty
			return null;

		// loop through each linked list in the array
		for (int i = 0; i < dictionary.length; i++) {
			if (dictionary[i] != null) {
				for (int j = 0; j < dictionary[i].getLength(); j++) {
					set.add(dictionary[i].get(j).key);
				}
			}
		}
		return set;
	}

	@Override
	public Set<V> valueSet() {
		Set<V> set = new ArraySet<V>();

		if (numberOfElements == 0) // special case - dictionary is empty
			return null;

		// loop through each linked list in the array
		for (int i = 0; i < dictionary.length; i++) {
			if (dictionary[i] != null) {
				for (int j = 0; j < dictionary[i].getLength(); j++) {
					set.add(dictionary[i].get(j).value);
				}
			}
		}
		return set;
	}

}
