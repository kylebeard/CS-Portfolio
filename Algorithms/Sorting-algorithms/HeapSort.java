/**
 * This program sorts comparable objects using a heap sorter
 * @author kylebeard
 *
 * @param <T>
 */
public class HeapSort<T extends Comparable<? super T>> extends SortingAlgorithm<T> {

	@Override
	public void sort(T[] array) {
		heapSort(array);
	}

	public static <T extends Comparable<? super T>> void heapSort(T[] a) {
		MaxHeap<T> heap = new MaxHeap<T>();

		// add it into the heap
		for (int i = 0; i < a.length; i++) {
			heap.add(a[i]);
		}

		// add it back to the heap starting at the end of the array
		for (int i = a.length - 1; i >= 0; i--) {
			a[i] = heap.remove();
			
		}
	}

}
