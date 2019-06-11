/** Paul Kirwan,
 *  February 2019
 *  Infix to Postfix Conversion using Stack Implementation
 */

import javax.swing.*;

public class InfixToPostFix {
	
	public static void main(String[] args) {
		//Ensure the user has entered a correct Infix Expression, before converting to Postfix
		boolean criteria = false;
		String input = "";
		Character[] infix = null;
		
		//Checks it is a correct Infix expression, or the user must re-enter expression
		while (!criteria) {
			//Call method to allow the user to input the expression
			input= inputString();
			
			//Convert String to Character array
			infix = toChar(input);
			
			//Check that the input is correct
			criteria = checkCriteria(infix);
		}
		
		//Convert infix to postfix
		String postfix = convertInfixToPostfix(infix);
		
		//Calculate answer from postfix expression
		double answer = evaluatePostfix(postfix);
		
		if (answer!=0)
			JOptionPane.showMessageDialog(null, "The result of this expression is: \n"
					+ "Infix: " + input
					+ "\nPostfix: " + postfix
					+ "\nResult: " + answer);
	}
	

    //Prompt the user to enter the expression as a String
	public static String inputString() {
	    String inputString = JOptionPane.showInputDialog(null, "Please enter a numerical expression between 3-20 characters: ");
	    return inputString;
	}
	
	//Convert String to Character array
	public static Character[] toChar (String input) {
		if ( input == null ) {
		     return null;
		 }
		 Character[] array = new Character[input.length()];
		 for (int i = 0; i < input.length() ; i++) {
		    array[i] = new Character(input.charAt(i));
		 }
		 return array;	
	}
	
	public static boolean checkCriteria(Character[] exp) {
		//Allows the cancel option to exit the dialogue box
		if (exp == null) {
			return true;
		}
		//Check the length of the character array
		else if (exp.length < 3 || exp.length > 20) {
        	
        	JOptionPane.showMessageDialog(null, "Error, the String must contain 3-20 characters, please re-enter.");
        	return false;
        }
		//Store previous character during loop. 'a' is arbitrary but must not be an int for the single digit check
		char prevChar = 'a';
		
        //Cycle through every character
        for(int i=0;i<exp.length;i++) {
        	//Only allow digits 0-9 and symbols +, -, *, /, ^, (, )
        	if (!(exp[i] >= '0' && exp[i] <= '9' || exp[i] == '+' ||
        			exp[i] == '-' || exp[i] == '*' || exp[i] == '/' || exp[i] == '^' || exp[i] == '(' || exp[i] == ')')) {
        		JOptionPane.showMessageDialog(null, "Incorrect character encountered: "+exp[i]+"\nPlease re-enter.");
        		return false;
        	}
        	//Ensures every integer has no more than a single digit (won't allow 10 or greater)
             if (exp[i] >= '0' && exp[i] <= '9' && prevChar >= '0' && prevChar <= '9') {
        		JOptionPane.showMessageDialog(null,"Integers must be 0-9. You entered these consecutive integers: "
        				+prevChar + exp[i]+"\nPlease re-enter.");
        		return false;
        	}
        	prevChar = exp[i];
        }
        return true;
	}
	
	//Greater value of integer means higher precedence
	public static int setPrecedence(char operator) {
		switch (operator) {
		case '^':
            return 3;
		case '*':
	    case '/':
	        return 2;
        case '+': 
	    case '-': 
	        return 1;
	        }
		return 0;
	}
	
	public static String convertInfixToPostfix(Character[] exp) {
		
		//Allows the cancel option to exit the dialogue box
		if (exp == null) {
			return "";
		}
		
		//Create a Stack and output String
		Stack s = new ArrayStack(20);
		String output = "";
		
		for (int i = 0; i<exp.length; i++) {
			
            char c = exp[i]; 
              
            //if an integer, it is added straight into the output String
            if (Character.isLetterOrDigit(c)) { 
                output += c; 
            }
            // If an opening bracket, push to stack
            else if (c == '(') {
                s.push(c); 
            }
            //If a closing bracket, pop every entry from the stack until we reach the opening bracket 
            else if (c == ')') {
                while (!s.isEmpty() && (Character) s.top() != '(') {
                    output += s.pop(); 
                }
                s.pop(); 
            } 
            else //must be an operator: order based on precedence
            { 
                while (!s.isEmpty() && setPrecedence(c) <= setPrecedence((Character)s.top())) {
                    output += s.pop(); 
                }
                s.push(c); 
            }
        }
        //pop all the operators from the stack to the output String
        while (!s.isEmpty()) {
            output += s.pop();
        }
        return output;
	}
	
	//Take in the Postfix expression and calculates answer
	public static double evaluatePostfix (String postfix) {
		
		Character[] postfixArr = toChar(postfix);
		Stack s = new ArrayStack(20);
		double answer = 0;
		
		for (int i = 0; i<postfixArr.length; i++) {
			char c = postfixArr[i];
			
			//If element = operand, push to stack
			if (Character.isLetterOrDigit(c)) {
				s.push((double)Character.getNumericValue(c));
			}
			
			//otherwise element = operator, pop two operands
			else {
				double hold1 = (double) s.pop();
				double hold2 = 0;
				
				//If too many operators were entered e.g. "7++5"
				if (!s.isEmpty()) {
					 hold2 = (double) s.pop();
				}
				else {
					JOptionPane.showMessageDialog(null,"Expression entered incorrectly.\nPlease re-enter expression.");
					return 0;
				}
					
				//use the necessary operator on the 2 popped values, then push to stack
				switch(c) {
				case '^':
		            answer = Math.pow(hold2,hold1);
		            s.push(answer);
		            break;
				case '*':
					answer = hold2*hold1;
					s.push(answer);
					break;
			    case '/':
			    	answer = hold2/hold1;
			    	s.push(answer);
			    	break;
		        case '+': 
		        	answer = hold2+hold1;
		        	s.push(answer);
		        	break;
			    case '-': 
			    	answer = hold2-hold1;
			    	s.push(answer);
			    	break;
			    }
			}
		}
		return answer;
	}
}