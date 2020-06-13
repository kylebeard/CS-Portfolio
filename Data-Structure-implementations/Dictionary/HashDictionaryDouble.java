
/**
 * Implementation of DictionaryInterface using Doubling Hashing
 * 
 * @author Kyle Beard
 */

public class HashDictionaryDouble<K, V> implements DictionaryInterface<K, V> {

	// initial size of hash table
	private static int DEFAULT_SIZE = 13;

	// When capacity exceeds this threshold, a new addition will trigger
	// rehashing
	private static double CAPACITY_THRESHOLD = 0.67;


	// the number of elements in the hash table
	private int numberOfElements;

	// the hash table
	private TableElement<K, V>[] dictionary;

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

	public HashDictionaryDouble() {
		this(DEFAULT_SIZE);
	}

	@SuppressWarnings("unchecked")
	public HashDictionaryDouble(int size) {
		if (size < 0)
			throw new IllegalArgumentException();

		dictionary = (TableElement<K, V>[]) new TableElement[size];
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

	/**
	 * This calls the appropriate hashing strategy
	 */
	public V put(K key, V value) {
		return linearProbing(key, value);
	}

	/**
	 * Private helper method that implements appropriate hashing strategy
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	private V linearProbing(K key, V value) {

		// re-hash if necessary
		ensureCapacity();

		// create the new element
		TableElement<K, V> element = new TableElement<K, V>(key, value);

		// get the hash value for the specified key
		int hash = hashValue(key);

		// use a simple linear probing
		while (dictionary[hash] != null && !dictionary[hash].key.equals(key)) {
			hash = (hash + getProbe(key)) % currentCapacity;
		}

		if (dictionary[hash] == null || dictionary[hash].isRemoved)
			numberOfElements++;

		dictionary[hash] = element;

		return value;

	}
	
	private int getProbe(K key){
		return (1 + (Math.abs(hashValue(key))) % (currentCapacity - 2) );	
	}

	public V get(K key) {
		int hash = hashValue(key);

		if (dictionary[hash] == null)
			return null;

		while (dictionary[hash] != null && !dictionary[hash].key.equals(key)) {
			hash = (hash + getProbe(key)) % currentCapacity;
		}
		if (dictionary[hash] == null) {
			return null;
		} else {
			return dictionary[hash].value;
		}
	}

	public boolean contains(K key) {

		// get the hash value for the specified key
		int hash = hashValue(key);
		
		if (dictionary[hash] == null) // special case - first spot is null
			return false;

		while (dictionary[hash] != null && !dictionary[hash].key.equals(key)) {
			hash = (hash + getProbe(key)) % currentCapacity;
		}
		
		if(dictionary[hash] == null)
			return false;
		
		if(dictionary[hash].isRemoved)
			return false;

		return true;
	}

	public V remove(K key) {
		
		// get the hash value for the specified key
		int hash = hashValue(key);
								
		if (dictionary[hash] == null) // special case - first spot is null
			return null;

		while (!dictionary[hash].key.equals(key)) {
			hash = (hash + getProbe(key)) % currentCapacity;
		}
		
		dictionary[hash].isRemoved = true;
		numberOfElements--;
		
		return dictionary[hash].getValue();
	}

	public int size() {
		return numberOfElements;
	}

	/**
	 * returns the next prime number that is least 2 larger than the current prime number.
	 */
	private int getNextPrime(int currentPrime) {
		// first we double the size of the current prime + 1
		currentPrime *= 2;
		currentPrime += 1;

		while (!isPrime(currentPrime))
			currentPrime++;

		return currentPrime;
	}

	/**
	 * Helper method that tests if an integer value is prime.
	 * 
	 * @param candidate
	 * @return True if candidate is prime, false otherwise.
	 */
	private boolean isPrime(int candidate) {
		boolean isPrime = true;

		// numbers <= 1 or even are not prime
		if ((candidate <= 1))
			isPrime = false;
		// 2 or 3 are prime
		else if ((candidate == 2) || (candidate == 3))
			isPrime = true;
		// even numbers are not prime
		else if ((candidate % 2) == 0)
			isPrime = false;
		// an odd integer >= 5 is prime if not evenly divisible
		// by every odd integer up to its square root
		// Source: Carrano.
		else {
			for (int i = 3; i <= Math.sqrt(candidate) + 1; i += 2)
				if (candidate % i == 0) {
					isPrime = false;
					break;
				}
		}

		return isPrime;
	}

	/**
	 * re-hash the elements in the dictionary
	 */
	@SuppressWarnings("unchecked")
	private void rehash() {
		K key;
		V value;
		currentCapacity = getNextPrime(dictionary.length);
		// create new dictionary with doubled length to the next prime
		TableElement<K, V>[] newDictionary = (TableElement<K, V>[]) new TableElement[currentCapacity];
		TableElement<K, V> element;

		for (int i = 0; i < dictionary.length; i++) {
			if (dictionary[i] != null) {
				key = dictionary[i].getKey();
				value = dictionary[i].getValue();

				int hash = hashValue(key); // get the hash value for the specified key

				// use a simple linear probing
				while (newDictionary[hash] != null) {
					hash = (hash + getProbe(key)) % currentCapacity;
				}

				element = new TableElement<K, V>(key, value);
				newDictionary[hash] = element;
			}
		}
		dictionary = newDictionary;
	}

	/**
	 * Return the current load factor
	 * 
	 * @return
	 */
	private double getLoadFactor() {
		return numberOfElements / (double) currentCapacity;
	}

	/**
	 * Ensure there is capacity to perform an addition
	 */
	private void ensureCapacity() {
		double loadFactor = getLoadFactor();

		if (loadFactor >= CAPACITY_THRESHOLD)
			rehash();
	}

	public Set<K> keySet() {
		Set<K> set = new ArraySet<K>();
		
		if(numberOfElements == 0) // special case - dictionary is empty
			return null;
		
		for(int i = 0; i < dictionary.length; i++){
			if(dictionary[i] != null && !dictionary[i].isRemoved){
				set.add(dictionary[i].getKey());
			}		
		}
		return set;
	}

	public Set<V> valueSet() {
		Set<V> set = new ArraySet<V>(numberOfElements);
		
		if(numberOfElements == 0) // special case - dictionary is empty
			return null;
		
		for(int i = 0; i < dictionary.length; i++){
			if(dictionary[i] != null && !dictionary[i].isRemoved){
				set.add(dictionary[i].getValue());
			}		
		}
		return set;
	}
}
