/**
 * Class for the Binary Tree guessing game
 */

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class GuessingGame implements Serializable{
	
	//Needed for Serializable implementation
	private static final long serialVersionUID = 1L;

	public static void main (String args[]) throws ClassNotFoundException
	{
		//Booleans to start the game in the default version
		boolean playing = true;
		boolean loaded = false;
		
		//Create a tree
		BinaryNode<String> node = new BinaryNode<String>();
		BinaryNode<String> root = createTree(node);
		BinaryNodeInterface<String> currentNode = root;
		
		//Begin the game
		while (playing)
		{
			while (!currentNode.isLeaf() && playing)
			{
				//Present the Yes/No answers to the user
				int answer = JOptionPane.showConfirmDialog(null, currentNode.getData(), "Guessing Game", JOptionPane.YES_NO_OPTION); 
				
				switch (answer)
				{ //Yes = Go Left
				case (JOptionPane.YES_OPTION):
					if (currentNode.hasLeftChild())
						currentNode = currentNode.getLeftChild();
				break;
				//No = Go right
				case (JOptionPane.NO_OPTION):
					if (currentNode.hasRightChild())
						currentNode = currentNode.getRightChild();
				break;
				//Quit the game
				case (JOptionPane.CLOSED_OPTION):
					playing = false;
				break;
				}
			}
			//Allow user to quit the game early by clicking the 'X'
			if (!playing)
				break;
			
			//At the leaf: got an answer that is either right or wrong
			//Present guess to user, and store their answer
			int answer = JOptionPane.showConfirmDialog(null, currentNode.getData(), "Guessing Game", JOptionPane.YES_NO_OPTION); 
			if (answer == JOptionPane.YES_OPTION)
			{
				String [] options = { "Play again?", "Store the tree?", "Load a stored tree?", "Quit?"};
				int choice = JOptionPane.showOptionDialog(
	                    null,
	                    "Answer is correct! Please choose an option to proceed ",
	                    "Correct!",
	                    JOptionPane.YES_NO_CANCEL_OPTION,
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    options,
	                    options[3]);
				
				//if the user on 'X' it should do the same as quit option
				if (choice == -1)
						choice = 3;
			
				//Present options after the game is over
	            String option = options[choice];
	            switch (option)
	            {
	            case "Play again?":
	            	currentNode = root;
	            	break;
	            case "Store the tree?":
	            	System.out.println("Storing the tree: ");
	            	store(root);
	            	printTree(root);
	            	playing = false;
	            	break;
	            case "Load a stored tree?":
	            	System.out.println("Loading the tree: ");
	            	loaded = true;
	            	currentNode = load();
	            	printTree(currentNode);
	            	break;
	            case "Quit?":
	            	playing = false;
	            	break;
	            }
			}
			//Guess was wrong. Get new question and answer from user
			else if (answer == JOptionPane.NO_OPTION)
			{
				String userAnswer = JOptionPane.showInputDialog(null, "Sorry, this answer has not been created yet.\n Please enter the answer: ");
				String userQuestion = JOptionPane.showInputDialog(null, "Please enter a question to distinguish this guess: ");
				
				//Switch the failed guess with the new question and add the guesses as children
				BinaryNode<String> movedNode = new BinaryNode<String>(currentNode.getData());
				
				if (userAnswer != null)
				{
					BinaryNode<String> newGuess = new BinaryNode<String>("Is it a " + userAnswer.toLowerCase() + "?");
					currentNode.setData(userQuestion);
					currentNode.setRightChild(movedNode);
					currentNode.setLeftChild(newGuess);
				}
			}
			// User hits 'X' on the dialog to quit
			else playing = false;
			
			if (!loaded)
				currentNode = root;
			//playing = false;
		}
	}
	
	//Create tree structure using BinaryNodes
	public static BinaryNode<String> createTree(BinaryNode<String> node)
	{
		//Declare initial nodes, starting with the leaves
		BinaryNode<String> penguin = new BinaryNode<String>("Is it a penguin?");
		BinaryNode<String> pigeon = new BinaryNode<String>("Is it a pigeon?");
		BinaryNode<String> snake = new BinaryNode<String>("Is it a snake?");
		BinaryNode<String> cow = new BinaryNode<String>("Is it a cow?");
		BinaryNode<String> chair = new BinaryNode<String>("Is it a chair?");
		BinaryNode<String> person = new BinaryNode<String>("Is it a person?");

		//Link nodes together
		BinaryNode<String> fly = new BinaryNode<String>("Can it fly?", pigeon, penguin);
		BinaryNode<String> bird = new BinaryNode<String>("Is it a bird?", fly, snake);
		BinaryNode<String> mammal = new BinaryNode<String>("Is it a mammal?", cow, bird);
		BinaryNode<String> object = new BinaryNode<String>("Is it an object?", chair, person);
		
		//Root Node
		BinaryNode<String> animal = new BinaryNode<String>("Are you thinking of an animal?", mammal, object);
		return animal;
	}
	
	//Inputs the root node from tree into a .ser file
	public static void store (BinaryNodeInterface<String> node)
	{
        try
        {
        	//Create file 'nodes' in the same folder
            FileOutputStream fileOut = new FileOutputStream("nodes.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            //Write root node with connections to file
            out.writeObject(node);
            out.close();
            fileOut.close();
        }
        catch (IOException i)
        {
            i.printStackTrace();
        }
    } 
	
	//returns root node from the stored tree
	public static BinaryNode<String> load ()
	{
		//Create node to hold read in data
        BinaryNode<String> node = new BinaryNode<String>();
        try
        {
        	//Read from file 'nodes' in the folder
            FileInputStream fileIn = new FileInputStream("nodes.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            //Read in the root node of tree, with its connections
            node =  (BinaryNode<String>) in.readObject();
            in.close();
            fileIn.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch (ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }
        return node;
	}
	
	//Prints each node, working from left to right, using recursion
	public static void printTree(BinaryNodeInterface<String> node)
	{
		if (node != null)
		{
			printTree(node.getLeftChild());
			
			System.out.println(node.getData());
			
			printTree(node.getRightChild());
		}
	}
}