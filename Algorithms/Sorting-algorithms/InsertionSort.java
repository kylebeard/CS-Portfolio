/**
 * Implementation of Insertion Sort
 * @author kylebeard
 *
 * @param <T>
 */
public class InsertionSort<T extends Comparable<? super T>> extends SortingAlgorithm<T> {

	@Override
	public void sort(T[] array) {
		insertionSort(array, array.length);
	}

	/*
	 * Sorts the first n objects in an array into ascending order using selection sort algorithm.
	 */
	public static <T extends Comparable<? super T>> void insertionSort(T[] a, int n) {
		int j = 0;

		for (int i = 0; i < n; i++) {
			j = i;
			
			while (j > 0 && (a[j].compareTo(a[j - 1]) < 0)) {
				swap(a, j, j - 1);
				j--;
			}
		}
	}
}
