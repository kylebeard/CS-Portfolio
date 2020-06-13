
public class PriorityQueue<T extends Comparable<? super T>> implements Queue<T>{
	
	MaxHeap<T> heap = new MaxHeap<T>();
	
	@Override
	public void add(T item) {
		heap.add(item);
	}

	@Override
	public T remove() {
		return heap.remove();
	}

	@Override
	public boolean isEmpty() {
		return heap.isEmpty();
	}

	@Override
	public int size() {
		return heap.getSize();
	}

}
