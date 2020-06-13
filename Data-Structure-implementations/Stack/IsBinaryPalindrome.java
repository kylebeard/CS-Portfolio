
/**
 * Program that returns whether a decimal number is palindrome in base 2
 * 
 * @author kylebeard
 */
import java.util.Scanner;

public class IsBinaryPalindrome {
	private static Stack<Character> list1 = new ListStack<Character>();
	private static Stack<Character> list2 = new ListStack<Character>();

	public static void main(String[] args) {
		System.out.println(-2 % 12);
		System.out.println(10 % 12);
		// scanner to read in decimal
		Scanner scan = new Scanner(System.in);
		int number = 0;

		do {
			System.out.println("Enter a number > than 0:");
			number = scan.nextInt();

			// put binary number into char array
			char[] binaryNumber = divideByTwo(number);

			// pushes binary number in forwards
			for (int i = 0; i < binaryNumber.length; i++) {
				list1.push(binaryNumber[i]);
			}

			// pushes binary number in backwards
			for (int i = binaryNumber.length - 1; i >= 0; i--) {
				list2.push(binaryNumber[i]);
			}

			// compares each integer to see if it is palindrome
			for (int i = 0; i < binaryNumber.length; i++) {
				char char1 = list1.peek();
				char char2 = list2.peek();
				if (char1 == char2) {
					list1.pop();
					list2.pop();
				} else {
					System.out.println("This number is not a palindrome!");
					break;
				}
				if (i == binaryNumber.length - 1)
					System.out.println("This number is a palindrome!");
			}

		} while (number != 0);
		System.out.println("Number must be larger than 0.");
		scan.close();
	}

	/**
	 * Converts base 10 number into binary
	 * 
	 * @param decimal
	 * @return binary number as a char array
	 */
	private static char[] divideByTwo(int decimal) {
		Stack<Character> s = new ListStack<Character>();

		// converts decimal to binary and pushes it onto the stack
		while (decimal > 0) {
			char remainder = (char) (decimal % 2);
			s.push(remainder);
			decimal = decimal / 2;
		}
		char[] binNum = new char[s.size()];

		// put each int on the stack into char array
		while (!s.isEmpty()) {
			for (int i = 0; i < binNum.length; i++) {
				binNum[i] = s.pop();
			}
		}
		return binNum;
	}
}
