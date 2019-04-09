package com.company.project3;
import java.io.*;
import java.util.Scanner;

public class UnMix {
	private DoubleLinkedList<Character> message;
	private String userMessage;

	public UnMix() {
		message = new DoubleLinkedList<Character>();
	}

	public static void main(String[] args) {
	    UnMix v = new UnMix();
	    v.userMessage = args[1];
	    v.initLinkedList();
	    //v.unMixture(fileName,v.userMessage);
		v.unMixture(args[0], args[1]);
	}

	public void initLinkedList(){
		char[] charMessage = userMessage.toCharArray();
		message.top = new NodeD<>(charMessage[0],new NodeD<>(),null);
		message.cursor = message.top;
		for(int i = 1; i < charMessage.length; i++){
			message.cursor.setNext(new NodeD(charMessage[i],null,message.cursor));
			message.cursor = message.cursor.getNext();
		}
	}

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

	private void unMixture(String filename, String userMessage) {
		String original = UnMixUsingFile (filename, userMessage);
		System.out.println ("\nThe original message was: " + original);
	}


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

	private void insertbefore(String token, int index) {
		for(int i = token.length()-1; i >= 0; i--){
			char insert = token.charAt(i);
			if(insert == '~'){
				insert = ' ';
			}
			message.insert(insert,index);
		}
	}

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
