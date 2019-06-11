import javax.swing.*;

public class PalindromesTest
{
	static int count1 = 0;
	static int count2 = 0;
	static int count3 = 0;
	static int binaryCount = 0;
	static int decimalCount = 0;
	
	public static void main(String[] args)
	{	
		String decimal = "0";
		String binary = "0";
		long time = 0;
		long start = System.currentTimeMillis();
		
		//Testing for first method (binary and decimal)
		System.out.println("Method 1:");
		for (int i = 0; i< 1000000; i++)
		{
			decimal = String.valueOf(i);
			 if (reverseCheck(decimal))
				 decimalCount++;
				 
			binary = decimalToBinary(decimal);
			if (reverseCheck(binary))
				binaryCount++;
			
			//Display the no. of operations at each 50,000
			if (i % 50000 == 0)
			{
				System.out.println(i + ": " + count1);
			}
		}
		System.out.println("1000000: " + count1);
		System.out.println("No. of Decimal palindromes: " + decimalCount);
		System.out.println("No. of Binary palindromes: " + binaryCount);
		time = System.currentTimeMillis() - start;
		System.out.println("Time: " + time + "ms");
		
		//Testing for second method (binary and decimal)
		binaryCount = 0;
		decimalCount = 0;
		System.out.println("\nMethod 2:");
		start = System.currentTimeMillis();
		for (int i = 0; i< 1000000; i++)
		{
			decimal = String.valueOf(i);
			if (elementCheck(decimal))
				 decimalCount++;
			
			binary = decimalToBinary(decimal);
			if (elementCheck(binary))
				binaryCount++;
			
			//Display the no. of operations at each 50,000
			if (i % 50000 == 0)
			{
				System.out.println(i + ": " + count2);
			}
		}
		System.out.println("1000000: " + count2);
		System.out.println("No. of Decimal palindromes: " + decimalCount);
		System.out.println("No. of Binary palindromes: " + binaryCount);
		time = System.currentTimeMillis() - start;
		System.out.println("Time: " + time + "ms");
		
		//Testing for third method (binary and decimal)
		binaryCount = 0;
		decimalCount = 0;
		System.out.println("\nMethod 3:");
		start = System.currentTimeMillis();
		for (int i = 0; i< 1000000; i++)
		{
			decimal = String.valueOf(i);
			if (stackQueueCheck(decimal))
				decimalCount++;
			
			binary = decimalToBinary(decimal);
			if (stackQueueCheck(binary))
				binaryCount++;
			
			//Display the no. of operations at each 50,000
			if (i % 50000 == 0)
			{
				System.out.println(i + ": " + count3);
			}
		}
		System.out.println("1000000: " + count3);
		System.out.println("No. of Decimal palindromes: " + decimalCount);
		System.out.println("No. of Binary palindromes: " + binaryCount);
		time = System.currentTimeMillis() - start;
		System.out.println("Time: " + time + "ms\n");
		
		int both = decimalAndBinary();
		System.out.println("\nTimes when palindromes occur in both: " + both);
	}
	
	public static boolean reverseCheck (String palindrome)
	{
		String reversed = "";
		count1++;
		
		//Cycle through the input, starting at the end and working backwards
		for (int i = palindrome.length() - 1; i >= 0; i--)
		{
			count1 += 6;
			//Add each digit to the new String
			reversed +=  palindrome.charAt(i);
			count1 += 2;
		}
		
		if (reversed.equals(palindrome))
		{
			count1 += 2;
			return true;
		} //else
		count1++;
		return false;
	}
	
	public static boolean elementCheck (String palindrome)
	{
		for (int i = 0; i < palindrome.length(); i++)
		{
			count2 += 5;
			//Compare first to last, second to second last, etc.
			if (palindrome.charAt(i) != palindrome.charAt(palindrome.length()-1-i))
			{
				count2 += 9;
				return false;
			}
		} //else
		count2++;
		return true;
	}
	
	public static boolean stackQueueCheck (String palindrome)
	{
		Stack s = new ArrayStack();
		Queue q = new ArrayQueue();
		count3 += 2;
		
		//Add each value to the stack and queue
		for (int i = 0; i < palindrome.length(); i++)
		{
			count3 += 4;
			s.push(palindrome.charAt(i));
			q.enqueue(palindrome.charAt(i));
			count3 += 4;
		}
		
		for (int i = 0; i < palindrome.length(); i++)
		{
			count3 += 4;
			//Compare most recent on the stack to the oldest in the queue
			if (s.pop() != q.dequeue())
			{
				count3 += 4;
				return false;
			}
		} //else
		count3++;
		return true;
	}
	
	public static String decimalToBinary (String decimal)
	{
		String binary = "";
		int decimalValue = Integer.valueOf(decimal);
		int remainder = 0;
		
		while (decimalValue > 0 )
		{
			//Get remainder after dividing by 2
			remainder = decimalValue % 2;
			decimalValue = decimalValue / 2;
			
			//Put the new remainder on the left of the String
			binary = remainder + binary;
		}
		return binary;
	}
	
	//Testing where palindromes occur in both decimal and binary
	public static int decimalAndBinary ()
	{
		String decimal = "";
		String binary = "";
		int both = 0;
		for (int i = 0; i < 1000000; i++)
		{
			decimal = String.valueOf(i);
			binary = decimalToBinary(decimal);
				
			if (reverseCheck(decimal) && elementCheck(binary))
			{
				both++;
			}
		}
		return both;
	}
}