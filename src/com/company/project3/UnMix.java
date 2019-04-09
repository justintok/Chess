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

				// put undo commands here
			}
		} catch (Exception e) {
			System.out.println("Error in command!  Problem!!!! in undo commands");
			System.exit(0);
		}
		finally {
			scan.close();
		}

		return message.toString();
	}

	private void unMixture(String filename, String userMessage) {
		String original = UnMixUsingFile (filename, userMessage);
		System.out.println ("The Original message was: " + original);
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
}
