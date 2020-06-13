import static org.junit.Assert.*;  
import org.junit.Test;

public class TestMaxHeap {
	
	@Test
	public void testAdd(){
		MaxHeap<Integer> heap = new MaxHeap<Integer>();
		
		assertTrue(heap.isEmpty());
		assertEquals(0,heap.getSize());
		
		heap.add(5);
		heap.add(10);
		heap.add(12);
		heap.add(13);
		heap.add(8);
		heap.add(31);
		heap.add(40);
		heap.add(55);
		heap.add(14);
		heap.add(11);
		
		assertFalse(heap.isEmpty());
		assertEquals(10, heap.getSize());
		assertEquals(new Integer(55), heap.front());
	}
	
	@Test
	public void testRemove() {
		MaxHeap<Integer> heap = new MaxHeap<Integer>();
		
		heap.add(5);
		heap.add(1);
		heap.add(16);
		heap.add(8);
		assertEquals(new Integer(16), heap.remove());
		assertEquals(new Integer(8), heap.remove());
		assertEquals(new Integer(5), heap.remove());
		assertEquals(new Integer(1), heap.remove());
		
		heap.add(5);
		heap.add(10);
		heap.add(12);
		heap.add(13);
		heap.add(8);
		heap.add(31);
		heap.add(40);
		heap.add(55);
		heap.add(14);
		heap.add(11);
		
		
		assertFalse(heap.isEmpty());
		assertEquals(10,heap.getSize());
		assertEquals(new Integer(55), heap.remove());
		
		assertEquals(9,heap.getSize());
		assertEquals(new Integer(40), heap.remove());
		
		assertEquals(8,heap.getSize());
		assertEquals(new Integer(31), heap.remove());
		
		assertEquals(7,heap.getSize());
		assertEquals(new Integer(14), heap.remove());
		
		assertEquals(6,heap.getSize());
		assertEquals(new Integer(13), heap.remove());
		
		assertEquals(5,heap.getSize());
		assertEquals(new Integer(12), heap.remove());
		
		assertEquals(4,heap.getSize());
		assertEquals(new Integer(11), heap.remove());
		
		assertEquals(3,heap.getSize());
		assertEquals(new Integer(10), heap.remove());
		
		assertEquals(2,heap.getSize());
		assertEquals(new Integer(8), heap.remove());
		
		assertEquals(1,heap.getSize());
		assertEquals(new Integer(5), heap.remove());	
	}

}