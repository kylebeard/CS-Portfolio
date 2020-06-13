/**
 * This program implements the quick sort method 
 * @author kylebeard
 *
 * @param <T>
 */
public class QuickSort<T extends Comparable<? super T>> extends SortingAlgorithm<T> {

	@Override
	public void sort(T[] array) {
		quickSort(array, 0, array.length - 1);
	}

	public static <T extends Comparable<? super T>> void quickSort(T[] a, int left, int right) {
		int l = left;
		int r = right;
		T pivot = a[right];

		while (l <= r) {
			while (a[l].compareTo(pivot) < 0)
				l++;

			while (a[r].compareTo(pivot) > 0)
				r--;

			if (l <= r) {
				swap(a, l, r);

				l++;
				r--;
			} 
		}

		// call quick sort recursively
		if (left < r)
			quickSort(a, left, r);

		if (l < right)
			quickSort(a, l, right);
	}
}
