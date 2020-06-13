
/**
 * Program that checks if a string is palindrome using one stack
 * and one queue
 * 
 * @author kyle beard
 */

import java.util.List;
import java.util.Scanner;
import java.util.Queue;

public class PalindromeChecker {

	private static Stack<Character> list1 = new ListStack<Character>();
	private static ListQueue<Character> list2 = new ListQueue<Character>();

	public static void main(String[] args) {
		// read in string
		Scanner scan = new Scanner(System.in);
		String message = null;

		do {
			System.out.println("Enter a string:");

			message = scan.nextLine();
			message.toLowerCase(); // makes the checker case insensitive

			// convert message to char array
			char[] charArray = message.toCharArray();

			// pushes message onto stack forwards
			for (int i = 0; i < charArray.length; i++) {
				list1.push(charArray[i]);
			}
			// adds message to a queue
			for (int i = 0; i < charArray.length; i++) {
				list2.add(charArray[i]);
			}

			// compares each char to see if it is palindrome
			for (int i = 0; i < charArray.length; i++) {

				char char1 = list1.peek();
				char char2 = list2.remove();
				if (char1 == char2) {
					list1.pop();
				} else {
					System.out.println("This word is not a palindrome!");

					while (!list2.isEmpty())  // empty queue if it's not a palindrome
						list2.remove();
					
					break;
				}
				if (i == charArray.length - 1)
					System.out.println("This word is a palindrome!");
			}

		} while (message.length() != 0);
		scan.close();

	}

}
