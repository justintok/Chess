package com.company.project3;

import java.io.*;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

public class Mix {

	/**
	 * Holds the user's message as it is mixed
	 */
	private DoubleLinkedList<Character> message;

	/**
	 * Contains the commands needed to undo the mixed up message
	 */
	private String undoCommands;

	/**
	 * Holds each string that has been copied/cut from the message
	 */
	private clipBdLinkedList clipBoard;

	/**
	 * the original message entered by the user
	 */
	private String userMessage;

	/**
	 * A text scanner used to take the user's input
	 */
	private Scanner scan;

	/**
	 * Constructor
	 */
	public Mix() {
		scan = new Scanner(System.in);
		message = new DoubleLinkedList<Character>();
		clipBoard = new clipBdLinkedList();

		undoCommands = "";
	}

	/**
	 * The Main method
	 * @param args Takes a String argument as the user's message
	 */
	public static void main(String[] args) {
		Mix mix = new Mix();
		if(args.length == 0) {
			System.out.println("Enter your message: ");
			Scanner scnr = new Scanner(System.in);
			mix.userMessage = scnr.nextLine();
		}else{
			mix.userMessage = args[0];
			System.out.println("\n" + mix.userMessage);
		}
		mix.initLinkedList();
		mix.mixture();
	}

	/**
	 * Enters the user's message into a linked list
	 */
	public void initLinkedList(){
        char[] charMessage = userMessage.toCharArray();
        message.top = new NodeD<>(charMessage[0],new NodeD<>(),null);
        message.cursor = message.top;
        for(int i = 1; i < charMessage.length; i++){
            message.cursor.setNext(new NodeD(charMessage[i],null,message.cursor));
            message.cursor = message.cursor.getNext();
        }
    }

	/**
	 * Asks for commands from the user to mix up the message
	 */
	private void mixture() {
		do {
			DisplayMessage();
			System.out.print("Command: \n");

			// save state
			DoubleLinkedList<Character> currMessage =  message;
			String currUndoCommands = undoCommands;

			try {
				String command = scan.next("[Qbdrpcxzh]");

				switch (command) {
				case "Q":
					System.out.println("\nEnter a file name: ");
					save(scan.next());
					System.out.println ("\nFinal mixed up message: \"" + message+"\"");
					System.exit(0);
				case "b":
					try {
						String token = scan.next();
						int index = scan.nextInt();
						insertbefore(token, index);
					}catch(Exception e){
						throw new IllegalArgumentException("The Insert command should be entered in the form 'b * #' with * being the string to be inserted and # the index at which to be inserted.\n");
					}
					break;
				case "d":
					String c = scan.next();
                    delete(c);
                    break;
				case "r":
				    try { //remove
				    	int start = scan.nextInt();
				    	int stop = scan.nextInt();
				    	if(start == 0 && stop == userMessage.length()-1){
				    		throw new IllegalArgumentException("Cannot remove whole contents of message");
						}
                        remove(start,stop);
                        break;
                    }catch(Exception e){ //replace
				    	String delStr = scan.next();
				    	String repStr = scan.next();
				        replace(delStr, repStr);
				        break;
                    }
				case "c":
					try {
						copy(scan.nextInt(), scan.nextInt(), scan.nextInt());
					}catch(Exception e){
						throw new IllegalArgumentException("The Copy command should be entered in the form 'c # # #' with # being integer values.\nThe integers represent the start, stop, and clipboard number respectively\n");
					}
					break;
				case "x":
					try {
						cut(scan.nextInt(), scan.nextInt(), scan.nextInt());
					}catch(Exception e){
						throw new IllegalArgumentException("The Cut command should be entered in the form 'x # # #' with # being integer values.\nThe integers represent the start, stop, and clipboard number respectively\n");
					}
					break;
				case "p":
					try{
					paste(scan.nextInt(), scan.nextInt());
					}catch(Exception e){
						throw new IllegalArgumentException("The Paste command should be entered in the form 'p # #' with # being integer values.\nThe integers represent the paste-index and clipboard number respectively\n");
					}
					break;
				case "z":
					random();
					break;
				case "h":
					helpPage();
					break;
				}
				scan.nextLine();   // should flush the buffer
			}
			catch (Exception e ) {
				System.out.println ("Error on input");

				//Prints what the user entered incorrectly
				String errorMessage = "";
				for(int i = 0; i < e.toString().length(); i++){
					if(e.toString().charAt(i) == ':') {
						errorMessage = e.toString().substring(i + 2);
						break;
					}
				}
				System.out.print(errorMessage + "\n");
				System.out.println("Previous state restored.");

				scan = new Scanner(System.in);  // should completely flush the buffer

				// restore state;
				undoCommands = currUndoCommands;
				message = currMessage ;
			}

		} while (true);
	}


//Function Helper Methods ---------------------------------------------------------------

	/**
	 * Removes a given string from the message
	 * @param start The starting index of the string to be removed
	 * @param stop The end index of the string to be removed
	 */
	private void remove(int start, int stop) {
        int count = stop - start;
        String piece = message.toString().substring(start,stop+1);
        message.delete(start);
	    while(count > 0){
            message.delete(start);
            count--;
        }

	    //Below code is for undo command
        char[] list = piece.toCharArray();
        for (int i = 0; i < list.length; i++) {
            if(list[i] == ' '){
                list[i] = '~';
            }
        }
        piece = new String();
	    for(char c : list){
	        piece += c;
        }
	    undoCommands = "b " + piece + " " + start + "\n" + undoCommands;
	}

	/**
	 * Deletes all instances of the given character in the message
	 * @param c The character the user wants to delete
	 */
    private void delete(String c) {
	    if(c.length() > 1 || c.length() < 1){
	        throw new IllegalArgumentException("Input is not a single character");
        }
	    char chr = c.charAt(0);
	    NodeD temp = message.top;
	    int count = 0;
	    while(temp != null){
	        if(temp.getData().equals(chr) || (temp.getData().equals(' ') && chr == '~')){
	            message.delete(count);
	            undoCommands = "b " + c + " " + count + "\n" + undoCommands;
            }else
            	count++;
	        temp = temp.getNext();
        }
    }

	/**
	 * Replaces a given character with a different character
	 * @param c The character that the user wants to be replaced
	 * @param r The character that the user wants to replace c with
	 */
	private void replace(String c, String r){
        if((c.length() > 1 || c.length() < 1) || (r.length() > 1 || r.length() < 1)){
            throw new IllegalArgumentException("at least one of the inputs is not a single character");
        }
        char chr = c.charAt(0);
        char rep = r.charAt(0);
        if(chr == ' '){
        	chr = '~';
		}
        if(rep == '~'){
        	rep = ' ';
		}
        NodeD temp = message.top;
        int count = 0;
        int undoCount = 1;
        while(temp != null){
            if(temp.getData().equals(chr) || (temp.getData().equals(' ') && chr == '~')){
                temp.setData(rep);
				undoCommands = "r " + (count+undoCount) + " " + (count+undoCount) + "\n" + undoCommands;
				undoCommands = "b " + chr + " " + (count+undoCount-1) + "\n" + undoCommands;
				undoCount++;
            }else
                count++;
            temp = temp.getNext();
        }
    }

	/**
	 * Removes a string within the message and saves it to a clip board
	 * @param start The starting index of the string to be cut
	 * @param stop The end index of the string to be cut
	 * @param clipNum The number of the clip board to which the string will be saved
	 */
	private void cut(int start, int stop, int clipNum) {
		copy(start,stop,clipNum);
		remove(start,stop);
	}

	/**
	 * Copies a string within the message and saves it to a clip board
	 * @param start The starting index of the string to be cut
	 * @param stop The end index of the string to be cut
	 * @param clipNum The number of the clip board to which the string will be saved
	 */
	private void copy(int start, int stop, int clipNum) {
		int count = stop -start;
		if(count < 0){
			throw new IllegalArgumentException("stop input may not come earlier in the string than star");
		}
		String temp = "";
		for (int i = start; i != stop+1;i++){
			temp += message.get(i);

		}
		clipBoard.addClip(temp,clipNum);
		System.out.println("Clip board => '" + temp + "'");

	}

	/**
	 * Inserts a string from the clip boards at the given index
	 * @param index The index at which the user wants the string to be inserted
	 * @param clipNum The number of the clip board that holds the string to be inserted
	 */
	private void paste( int index, int clipNum) {
		if(clipBoard.getClip(clipNum) != null) {
			String temp = "" + clipBoard.getClip(clipNum);
			insertbefore(temp, index);
		}else{
			throw new IllegalArgumentException("The given clipboard number does not have a value");
		}
	}

	/**
	 * Inserts a string from the clip boards at the given index
	 * @param token The string that the user wants to insert
	 * @param index  The index at which the user wants the string to be inserted
	 */
	private void insertbefore(String token, int index) {
        for(int i = token.length()-1; i >= 0; i--){
            if(token.charAt(i) == '~'){
                message.insert(' ',index);
            }else
                message.insert(token.charAt(i),index);
        }
        undoCommands = "r " + index + " " + (index + (token.length()-1)) + "\n" + undoCommands;
	}

	/**
	 * Displays the user's message with aligning indexes
	 */
	private void DisplayMessage() {
		System.out.print ("Message:\n");
		userMessage = message.toString();
		for (int i = 0; i < userMessage.length(); i++) 
			System.out.printf ("%3d", i);
		System.out.format ("\n");
		for (char c : userMessage.toCharArray()) 
			System.out.format("%3c",c);
		System.out.format ("\n");
	}

	/**
	 * Saves the undo commands to the given file
	 * @param filename The name of the file to which the undo commands will be saved
	 */
	public void save(String filename) {

		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

		} catch (IOException e) {
			e.printStackTrace();
		}

		out.println(undoCommands);
		out.close();
	}

	/**
	 * Runs multiple insert, remove, delete, and replace commands to mix up the message
	 */
	private void random(){
		Random rand = new Random();
		int numFunctions = rand.nextInt(6)+5;
		int count = 0;
		while(count < numFunctions) {
			userMessage = message.toString();
			int function = rand.nextInt(4)+1;
			while((function == 2 || function == 3) && userMessage.length() == 1) {
				function = 1;
			}

			//InsertBefore
			if (function == 1) {

				int index = rand.nextInt(userMessage.length());
				char letter;
				int numLetters = rand.nextInt(5) + 1;
				String token = "";
				for (int n = 0; n < numLetters; n++) {
					letter = (char) (rand.nextInt(25) + 97);
					token += letter;
				}
				insertbefore(token, index);
			}

			//Remove
			if (function == 2) {
				int start = rand.nextInt(userMessage.length());
				int stop;
				if(userMessage.length() == 2 || start == userMessage.length()-1){
					stop = start;
				}else{
					stop = start + rand.nextInt((userMessage.length() - 1) - start);
				}
				remove(start, stop);

			//Delete
			}
			if (function == 3) {
				char[] charPick = userMessage.toCharArray();
				char letter = charPick[rand.nextInt(charPick.length)];
				if(letter == ' '){
					letter = '~';
				}
				String l = "";
				l += letter;
				delete(l);

			//Replace
			}
			if(function == 4){
				char[] charPick = userMessage.toCharArray();
				char letter = charPick[rand.nextInt(charPick.length)];
				String delLetter = "";
				delLetter += letter;
				letter = (char) (rand.nextInt(25) + 97);
				String repLetter = "";
				repLetter += letter;
				replace(delLetter, repLetter);
			}

			count++;
		}
	}

	/**
	 * Displays a list of all commands and their syntax available to the user
	 */
	private void helpPage() {
		System.out.println("Commands:");
		System.out.println("\tQ filename	means, quit! " + " save to filename" );			
		System.out.println("\t  ~ is used for a space character" );
        System.out.println("\tb * #\tinserts the element * at index #. All characters after # shift down" );
        System.out.println("\tr # *\tremoves the string between the range # to * inclusive");
		System.out.println("\th\tmeans to show this help page");
		System.out.println("\td *\tdeletes all instances of * found in the message" );
		System.out.println("\tr # *\treplaces all # in the message with *" );
		System.out.println("\tc # * &\tcopies the string between # and * to the & clipboard" );
		System.out.println("\tx # * &\tcuts the string between # and * to the & clipboard" );
		System.out.println("\tp # * \tpastes the string from the * clipboard at the # index" );
		System.out.println("\tz\tdoes a random set of the above functions on the message" );

	}
}
