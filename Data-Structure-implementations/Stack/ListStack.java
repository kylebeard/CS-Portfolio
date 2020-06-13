/**
 * Implementation of a stack using an arrayList
 * 
 * @author kylebeard, joelee cherrington
 */
import java.util.ArrayList;
import java.util.List;

public class ListStack<T> implements Stack<T> {

    List<T> list = new ArrayList<T>();
    public ListStack() {
       
    }

    @Override
    public void push(T item) {
        list.add(0, item);
    }

    @Override
    public T pop() {
        if(list.isEmpty()) {
            return null; 
           } else {
           return list.remove(0);
       }
    }

    @Override
    public T peek() {
        if(list.isEmpty()) {
         return null; 
        } else {
        return list.get(0);
    }
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

}
