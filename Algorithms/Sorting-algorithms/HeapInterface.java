/**
 * Interface for specifying operations on a heap.
 * 
 * The implementation of the heap may be either a
 * max or min heap.
 * 
 * @author Greg Gagne - April 2017
 *
 * @param <T>
 */
public interface HeapInterface<T extends Comparable<? super T>>
{
	/**
	 * Adds item to the heap.
	 * 
	 * @param item
	 */
	public void add(T item);
	
	/**
	 * Removes and returns the item at the head of the heap.
	 * 
	 * If the implementation is a min heap, removes and returns the minimum item.
	 * If the implementation is a max heap, removes and returns the maximum item.
	 * 
	 * Returns null if the heap is empty
	 */
	public T remove();
	
	/**
	 * Returns - but does not remove - the item at the head of the heap.
	 * 
	 * If the implementation is a min heap, returns the minimum item.
	 * If the implementation is a max heap, returns the maximum item.
	 * 
	 * Returns null if the heap is empty
	 */
	public T front();

	/**
	 * Returns true if the heap is empty, false otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Returns the number of elements in the heap.
	 */
	public int getSize();
} 
