package com.company.project3;

public class DoubleLinkedList<E>  {
	protected NodeD<E> top;      // The first NodeD<E> in the list

    // This instance variable is not required, however if you
    // find it useful, then you are welcome to use it
	protected NodeD<E> cursor;  // The current NodeD<E> in the list

	public DoubleLinkedList() {
		top = null;
		cursor = null;
	}

	public E get(int position) {
		cursor = top;
		for (int i = 0; i < position; i++)
			cursor = cursor.getNext();
		return cursor.getData();

	}

	public String toString() {
		String retVal = "";
		NodeD<E> cur = top;
		while (cur != null) {
			retVal += cur.getData();
			cur = cur.getNext();
		}

		return retVal;
	}

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

	// Create the rest of the needed methods.


}
