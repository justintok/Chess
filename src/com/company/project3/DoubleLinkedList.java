package com.company.project3;

import org.w3c.dom.Node;

public class DoubleLinkedList<E>  {

	protected NodeD<E> top;      // The first NodeD<E> in the list

	protected NodeD<E> cursor;  // The current NodeD<E> in the list

	/**
	 * Constructor
	 */
	public DoubleLinkedList() {
		top = null;
		cursor = null;
	}

	/******************************************************************************************
	 * Gets the value of the node at the given position
	 * @param position The position number of the wanted node
	 * @return the value of the node at the given position
	 */
	public E get(int position) {
		cursor = top;
		for (int i = 0; i < position; i++)
			cursor = cursor.getNext();
		return cursor.getData();
	}

	/******************************************************************************************
	 * @return the String value of the entire linked list
	 */
	public String toString() {
		String retVal = "";
		NodeD<E> cur = top;
		while (cur != null) {
			retVal += cur.getData();
			cur = cur.getNext();
		}

		return retVal;
	}

	/******************************************************************************************
	 * Deletes the node at the given position
	 * @param position the position number of the desired node
	 */
	public void delete(int position) {
		cursor = top;

		//If deleting first value
		if (position == 0) {
			top = top.getNext();
			top.setPrev(null);
			return;
		}

		//Finds the position
		for (int i = 0; i < position; i++){
			cursor = cursor.getNext();
			if(cursor == null){
				throw new IllegalArgumentException("position input param was out of bounds");
			}
		}

		//If deleting last value
		if(cursor.getNext() == null){
			cursor.getPrev().setNext(null);
			return;
		}

		//If deleting any other value
		cursor.getPrev().setNext(cursor.getNext());
		cursor.getNext().setPrev(cursor.getPrev());
	}

	/******************************************************************************************
	 * Inserts a node at the given position and the given value
	 * @param character The value/data of the new node
	 * @param index The position of the new node
	 */
    public void insert(E character, int index) {
        cursor = top;

        //If inserting at first index
        if(index == 0){
            NodeD temp = top;
            top = new NodeD<>(character,temp,null);
            temp.setPrev(top);
            return;
        }

        //Finds the index
        for (int i = 0; i < index; i++){
            NodeD temp = cursor;
        	cursor = cursor.getNext();

        	//Check for going out of bounds
            if (cursor == null) {

            	//if inserting just after last node, add to end of message
            	if(i == index-1){
            		temp.setNext(new NodeD(character,null,temp));
					return;
            	//If inserting more than one place beyond last node, throw error
				}else
                	throw new IllegalArgumentException("given index is out of bounds");
            }
        }

        //Make new node for new character inserted
        NodeD temp = new NodeD<>(character,cursor,cursor.getPrev());
        cursor.getPrev().setNext(temp);
        cursor.setPrev(temp);
    }

}
