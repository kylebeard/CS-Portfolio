/**
*Abstract class representing a basic algorithm 
*@author Kyle Beard
*/
import java.util.Random;

public abstract class Algorithm<T extends Comparable<? super T>> {

	public abstract void apply(T[] array);

	public long time(T[] array) {
		long start, end;
		start = System.currentTimeMillis();

		// invoke the apply method
		this.apply(array);

		end = System.currentTimeMillis();

		// returns elapsed time
		return (end - start);
	}

	/**
	 * Create an array containing n random Integers.
	 */
	@SuppressWarnings("unused")
	public static Integer[] createRandomArray(int n) {
		Random rand = new Random();
		Integer[] intArray = new Integer[n];

		// assign n random ints to the array
		for (int i = 0; i < n; i++) {
			intArray[i] = rand.nextInt();
		}
		return intArray;
	}

}
