/**
 * Implementation of a set using an ArrayList
 * 
 * @author kylebeard
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListSet<T> implements Set<T> {
	public static final int CAPACITY_MULTIPLIER = 2;
	public static final int DEFAULT_CAPACITY = 15;

	//private int capacity = 0;
	//private int numberOfElements = 0;
	List<T> list = new ArrayList<T>();

	public ListSet() {
	    list = new ArrayList<T>();
	}

	@Override
	public void add(T element) {
		list.add(element);
	}

	@Override
	public void addAll(T[] elements) {
		for(int i = 0; i < elements.length; i++){
			list.add(elements[i]);
		}
	}

	@Override
	public boolean contains(T element) {
		if (list.contains(element)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public void remove(T element) {
		list.remove(element);
	}

	@Override
	public Set<T> union(Set<T> anotherSet) {
		Set<T> returnSet = new ArraySet<T>();
		Iterator<T> itr = list.iterator();
		returnSet = anotherSet.difference(this);
		
		while (itr.hasNext()) {
			T e = itr.next();
			if (this.contains(e)) {
				returnSet.add(e);
			}
		}
		return returnSet;
	}

	@Override
	public Set<T> intersection(Set<T> anotherSet) {
		Set<T> newSet = new ArraySet<T>();
		Iterator<T> itr = list.iterator();
		while (itr.hasNext()) {
			T e = itr.next();
			if (anotherSet.contains(e)) {
				newSet.add(e);
			}
		}
		return newSet;
	}

	@Override
	public Set<T> difference(Set<T> anotherSet) {
		Set<T> newSet = new ArraySet<T>();
		Iterator<T> itr = list.iterator();
		while (itr.hasNext()) {
			T e = itr.next();
			if (!(anotherSet.contains(e))) {
				newSet.add(e);
			}
		}
		return newSet;
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

}