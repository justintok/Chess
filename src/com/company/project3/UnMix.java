package com.company.project3;
import java.io.*;
import java.util.Scanner;

public class UnMix {

	/**
	 * Holds the mixed up message and is used to unmix
	 */
	private DoubleLinkedList<Character> message;

	/**
	 * The mixed up message
	 */
	private String userMessage;

	/**
	 * Constructor
	 */
	public UnMix() {
		message = new DoubleLinkedList<Character>();
	}

	/**
	 * The Main method
	 * @param args The filename of the undo commands and the mixed up message
	 */
	public static void main(String[] args) {
	    UnMix v = new UnMix();
	    v.userMessage = args[1];
	    v.initLinkedList();
		v.unMixture(args[0], args[1]);
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
	 * Enacts the given command on the linked list that holds the message
	 * @param command The given undo command
	 * @return The new message after the command has been processed
	 */
	public String processCommand(String command) {
		Scanner scan = new Scanner(command);
		char charInput;

		try {
			command = scan.next();
			switch (command.charAt(0)) {
				case 'b':
					String token = scan.next();
					int index = scan.nextInt();
					insertbefore(token,index);
					break;
				case 'r':
					int start = scan.nextInt();
					int stop = scan.nextInt();
					remove(start, stop);
					break;

				// put undo commands here
			}
		} catch (Exception e) {
			System.out.println("Error in command!  Problem in undo commands\n");
			e.printStackTrace();
			System.exit(0);
		}
		finally {
			scan.close();
		}

		return message.toString();
	}

	/**
	 * Runs the undo commands and prints the final message
	 * @param filename The name of the file that holds the undo commands
	 * @param userMessage The mixed up message
	 */
	private void unMixture(String filename, String userMessage) {
		String original = UnMixUsingFile (filename, userMessage);
		System.out.println ("\nThe original message was: " + original);
	}

	/**
	 * Takes commands found in the undo command file and processes them
	 * @param filename the name of the file that holds the undo commands
	 * @param userMessage the mixed up message
	 * @return The message after all commands in the undo command file have been processed
	 */
	public String UnMixUsingFile(String filename, String userMessage) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (scanner.hasNext()) {
			String command = scanner.nextLine();
			userMessage = processCommand(command);
		} 
		return userMessage;
	}


// Undo Commands ---------------------------------------------------------

	/**
	 * Inserts a string from the clip boards at the given index
	 * @param token The string that the user wants to insert
	 * @param index  The index at which the user wants the string to be inserted
	 */
	private void insertbefore(String token, int index) {
		for(int i = token.length()-1; i >= 0; i--){
			char insert = token.charAt(i);
			if(insert == '~'){
				insert = ' ';
			}
			message.insert(insert,index);
		}
	}

	/**
	 * Removes a given string from the message
	 * @param start The starting index of the string to be removed
	 * @param stop The end index of the string to be removed
	 */
	private void remove(int start, int stop) {
		int count = stop - start;
		String piece;
		try {
			piece = message.toString().substring(start, stop + 1);
		}catch(StringIndexOutOfBoundsException e){ //If the piece includes the last character of the message
			piece = message.toString().substring(start);
		}
		message.delete(start);
		while (count > 0) {
			message.delete(start);
			count--;
		}
		char[] list = piece.toCharArray();
		for (int i = 0; i < list.length; i++) {
			if (list[i] == ' ') {
				list[i] = '~';
			}
		}
		piece = new String();
		for (char c : list) {
			piece += c;
		}
	}
}
