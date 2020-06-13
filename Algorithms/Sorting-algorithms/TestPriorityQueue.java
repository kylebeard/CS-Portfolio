import static org.junit.Assert.*;

import org.junit.Test;


public class TestPriorityQueue {

	@Test
	public void test() {
		Queue<Task> pq = new PriorityQueue<Task>();
		
		Task t1 = new Task("Google Chrome", 5);
		Task t2 = new Task("Microsoft Excel",1);
		Task t3 = new Task("Adobe Photoshop",16);
		Task t4 = new Task("Eclipse", 8);
		
		pq.add(t1);
		pq.add(t2);
		pq.add(t3);
		pq.add(t4);
		
		Task t = pq.remove();
		assertEquals(16,t.getPriority());
		
		t = pq.remove();
		assertEquals(8,t.getPriority());
		
		t = pq.remove();
		assertEquals(5,t.getPriority());
		
		t = pq.remove();
		assertEquals(1,t.getPriority());
	}

}
