/**
 * Program that checks if a string is palindrome
 * 
 * @author kyle beard, joelee cherrington
 */

import java.util.List;
import java.util.Scanner;

public class PalindromeChecker {

	private static Stack<Character> list1 = new ListStack<Character>();
	private static Stack<Character> list2 = new ListStack<Character>();

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
			// pushes message onto stack backwards
			for (int i = charArray.length - 1; i >= 0; i--) {
				list2.push(charArray[i]);
			}

			// compares each char to see if it is palindrome
			for (int i = 0; i < charArray.length; i++) {
				char char1 = list1.peek();
				char char2 = list2.peek();
				if (char1 == char2) {
					list1.pop();
					list2.pop();
				} else {
					System.out.println("This word is not a palindrome!");
					break;
				}
				if (i == charArray.length - 1)
					System.out.println("This word is a palindrome!");
			}

		} while (message.length() != 0);
		scan.close();

	}

}
