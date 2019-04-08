package com.company.project3;

import java.io.*;
import java.util.Hashtable;
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
        message.top = new NodeD();
        message.top.setData(charMessage[0]);
        message.top.setPrev(null);
        message.top.setNext(new NodeD());
        message.top.getNext().setData(charMessage[1]);
        message.cursor = message.top.getNext();
        for(int i = 2; i < charMessage.length; i++){
            message.cursor.setNext(new NodeD(charMessage[i],null,message.cursor));
            message.cursor = message.cursor.getNext();
        }
    }

	private void mixture() {
		do {
			DisplayMessage();
			System.out.print("Command: \n");

			// save state
			DoubleLinkedList<Character> currMessage =  new DoubleLinkedList<>();
			String currUndoCommands = undoCommands;

			try {
				String command = scan.next("[Qbdrpcxh]");

				switch (command) {
				case "Q":
					save(scan.next());
					System.out.println ("Final mixed up message: \"" + message+"\"");
					System.exit(0);
				case "b":
					insertbefore(scan.next(), scan.nextInt());
					break;
				case "d":
                    delete(scan.next());
                    break;
				case "r":
				    try {
                        remove(scan.nextInt(), scan.nextInt());
                        break;
                    }catch(Exception e){
				        replace(scan.next(), scan.next());
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

	private void remove(int start, int stop) {
        int count = stop - start;
        message.delete(start);
	    while(count > 0){
            message.delete(start);
            count--;
        }
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
            }
	        temp = temp.getNext();
            count++;
        }
    }

    private void replace(String c, String r){
        if((c.length() > 1 || c.length() < 1) && (r.length() > 1 || r.length() < 1)){
            throw new IllegalArgumentException("at least one of the inputs is not a single character");
        }
        char chr = c.charAt(0);
        char rep = r.charAt(0);
        NodeD temp = message.top;
        int count = 0;
        while(temp != null){
            if(temp.getData().equals(chr) || (temp.getData().equals(' ') && chr == '~')){
                temp.setData(rep);
            }else
                count++;
            temp = temp.getNext();
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
            message.insert(token.charAt(i),index);
        }
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

	private void helpPage() {
		System.out.println("Commands:");
		System.out.println("\tQ filename	means, quit! " + " save to filename" );			
		System.out.println("\t  ~ is used for a space character" );
        System.out.println("\tb * #\tinserts the element * at index #. All characters after # shift down" );
        System.out.println("\tr # *\tremoves the string between the range # to * inclusive");
		System.out.println("\th\tmeans to show this help page");
		System.out.println("\td *\tdeletes all instances of * found in the message" );
		System.out.println("\tr # *\treplaces all # in the message with *" );

	}
}
