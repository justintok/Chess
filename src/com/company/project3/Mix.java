package com.company.project3;

import java.io.*;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

public class Mix {

	private DoubleLinkedList<Character> message;
	private String undoCommands;
	private Hashtable<Integer, DoubleLinkedList<Character>> clipBoards;
	private clipBdLinkedList clipBoard;

	private String userMessage;
	private Scanner scan;

	public Mix() {
		scan = new Scanner(System.in);
		message = new DoubleLinkedList<Character>();
		clipBoards = new Hashtable<Integer, DoubleLinkedList<Character>>();

		undoCommands = "";
	}

	public static void main(String[] args) {
		Mix mix = new Mix();
		System.out.println("Enter your message: ");
		Scanner scnr = new Scanner(System.in);
		mix.userMessage = scnr.nextLine();
		mix.initLinkedList();
		mix.mixture();
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
					String token = scan.next();
					int index = scan.nextInt();
					insertbefore(token,index);
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
					copy(scan.nextInt(), scan.nextInt(), scan.nextInt());
					break;
				case "x":
					cut(scan.nextInt(), scan.nextInt(), scan.nextInt());
					break;
				case "p":
					paste(scan.nextInt(), scan.nextInt());
					break;
				case "z":
					random();
					break;
				case "h":
					helpPage();
					break;

					// all the rest of the commands have not been done
                    // No "real" error checking has been done.
				}
				scan.nextLine();   // should flush the buffer
			}
			catch (Exception e ) {
				System.out.println ("Error on input, previous state restored.");
				scan = new Scanner(System.in);  // should completely flush the buffer

				// restore state;
				undoCommands = currUndoCommands;
				message = currMessage ;
			}

		} while (true);
	}


//Function Helper Methods ---------------------------------------------------------------

	private void remove(int start, int stop) {
        int count = stop - start;
        String piece = message.toString().substring(start,stop+1);
        message.delete(start);
	    while(count > 0){
            message.delete(start);
            count--;
        }
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

    private void replace(String c, String r){
        if((c.length() > 1 || c.length() < 1) && (r.length() > 1 || r.length() < 1)){
            throw new IllegalArgumentException("at least one of the inputs is not a single character");
        }
        char chr = c.charAt(0);
        char rep = r.charAt(0);
        if(rep == '~'){
        	rep = ' ';
		}
        NodeD temp = message.top;
        int count = 0;
        while(temp != null){
            if(temp.getData().equals(chr) || (temp.getData().equals(' ') && chr == '~')){
                temp.setData(rep);
				undoCommands = "r " + (count+1) + " " + (count+1) + "\n" + undoCommands;
				undoCommands = "b " + chr + " " + count + "\n" + undoCommands;
            }else
                count++;
            temp = temp.getNext();
        }
		if(rep == ' '){
			rep = '~';
		}
    }

	private void cut(int start, int stop, int clipNum) {
		remove(start,stop);
		copy(start,stop,clipNum);

	}

	private void copy(int start, int stop, int clipNum) {
		int count = stop -start;
		String temp = "";
		for (int i = 0; i<= count;i++){
			temp = temp + message.get(start+i);

		}
		clipBoard.addClip(temp);

	}

	private void paste( int index, int clipNum) {
		String temp = "" + clipBoard.getClip(clipNum);
		insertbefore(temp,index);

	}
         
	private void insertbefore(String token, int index) {
        for(int i = token.length()-1; i >= 0; i--){
            if(token.charAt(i) == '~'){
                message.insert(' ',index);
            }else
                message.insert(token.charAt(i),index);
        }
        undoCommands = "r " + index + " " + (index + (token.length()-1)) + "\n" + undoCommands;
	}

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

	private void random(){
		Random rand = new Random();
		int numFunctions = rand.nextInt(6)+5;
		int count = 0;
		while(count < numFunctions) {
			userMessage = message.toString();
			int function = rand.nextInt(4)+1;
			while(((function == 2 || function == 3) && userMessage.length() == 1)) {
				function = rand.nextInt(4)+1;
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

			//Remove
			}
			if (function == 2) {
				int start = rand.nextInt(userMessage.length());
				int stop = start + rand.nextInt((userMessage.length() - 1) - start);
				if(userMessage.length() == 2){
					stop = start;
				}
				remove(start, stop);

			//Delete
			}
			if (function == 3) {
				char[] charPick = userMessage.toCharArray();
				char letter = charPick[rand.nextInt(charPick.length)];
				String l = "";
				l += letter;delete(l);

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

	private void helpPage() {
		System.out.println("Commands:");
		System.out.println("\tQ filename	means, quit! " + " save to filename" );			
		System.out.println("\t  ~ is used for a space character" );
        System.out.println("\tb * #\tinserts the element * at index #. All characters after # shift down" );
        System.out.println("\tr # *\tremoves the string between the range # to * inclusive");
		System.out.println("\th\tmeans to show this help page");
		System.out.println("\td *\tdeletes all instances of * found in the message" );
		System.out.println("\tr # *\treplaces all # in the message with *" );
		System.out.println("\tz\tdoes a random set of the above functions on the message" );

	}
}
