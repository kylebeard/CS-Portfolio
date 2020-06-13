/**
 * Queue implementation using an ArrayList
 * 
 * @author kylebeard, max
 */
import java.util.ArrayList;

public class ListQueue<T> implements Queue<T> {

	private ArrayList<T> list = new ArrayList<T>();
	private int numberOfElements = 0;

	/**
	 * appends item to the queue
	 */
	@Override
	public void add(T item) {
		list.add(item);
		numberOfElements++;
	}

	/**
	 * removes item at the front of the queue
	 * @return the item, or null if queue is empty
	 */
	@Override
	public T remove() {
		if (!list.isEmpty()) {
			T item = list.remove(0);
			numberOfElements--;
			return item;
		}
		return null;
	}

	/**
	 * @return true if queue is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		if (numberOfElements == 0){
			return true;
		} else{
			return false;
		}
	}

	/**
	 * @return number of elements in the queue
	 */
	@Override
	public int size() {
		return numberOfElements;
	}

}
