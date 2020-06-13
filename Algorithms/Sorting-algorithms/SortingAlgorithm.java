
public abstract class SortingAlgorithm<T extends Comparable<? super T>> extends Algorithm<T> {

	static final int NUMBER = 4;

	/**
	 * sorts the parameter array in ascending order (from smallest to largest)
	 */
	public abstract void sort(T[] array);

	/**
	 * Invoke the appropriate sorting algorithm.
	 */
	public void apply(T[] array) {
		this.sort(array);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends Comparable<? super T>> boolean isSorted(Comparable[] array) {
		boolean sorted = false;

		// if we make it through the loop, it is sorted
		for (int i = 1; i < array.length; i++) {
			if (array[i - 1].compareTo(array[i]) <= 0) {
				sorted = true;
			} else {
				sorted = false;
				break;
			}
		}
		return sorted;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void compareSorts(int n) {
		SortingAlgorithm[] sorts = new SortingAlgorithm[NUMBER];

		sorts[0] = new SelectionSort();
		sorts[1] = new InsertionSort();
		sorts[2] = new MergeSort();
		sorts[3] = new QuickSort();

		Integer[][] sortArray = new Integer[NUMBER][n];
		sortArray[0] = createRandomArray(n);

		System.arraycopy(sortArray[0], 0, sortArray[1], 0, sortArray[0].length);
		System.arraycopy(sortArray[0], 0, sortArray[2], 0, sortArray[0].length);
		System.arraycopy(sortArray[0], 0, sortArray[3], 0, sortArray[0].length);

		for (int i = 0; i < sorts.length; i++) {
			long time = sorts[i].time(sortArray[i]);
			if (i == 0) {
				System.out.println("The time for Selection Sort: " + time +"ms");
			} else if (i == 1) {
				System.out.println("The time for Insertion Sort: " + time+"ms");
			} else if (i == 2) {
				System.out.println("The time for Merge Sort: " + time+"ms");
			} else {
				System.out.println("The time for Quick Sort: " + time+"ms");
			}
		}
	}

	/**
	 * Task: Swaps the array elements a[i] and a[j].
	 * 
	 * @param a
	 *            an array of objects
	 * @param i
	 *            an integer >= 0 and < a.length
	 * @param j
	 *            an integer >= 0 and < a.length
	 */
	@SuppressWarnings("unused")

	protected static void swap(Object[] a, int i, int j) {
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

}
