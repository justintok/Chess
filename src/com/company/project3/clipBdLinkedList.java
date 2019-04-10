package com.company.project3;



public class clipBdLinkedList {

    /**
     * The Top clip board node
     */
    private NodeCB top;

    /**
     * The last clip board node
     */
    private NodeCB tail;

    /**
     * Constructor
     */
    public clipBdLinkedList() {
        tail = top = null;
    }

    /******************************************************************************************
     * Adds a new clip board to the linked list
     * @param clip The string to be saved
     * @param number The clip board number
     */
    public void addClip(String clip, int number){

        if (top == null){
            top = new NodeCB(clip,null,null, number);
        }
        else {
            NodeCB temp = top;
            top = new NodeCB(clip, temp, null, number);
            temp.setPrev(top);
        }
    }

    /******************************************************************************************
     * Returns the string of the clip board at the given clipboard number
     * @param number The clip board number
     * @return The string at the given clip board number
     */
    public String getClip(int number){
        NodeCB temp = top;
        while(temp != null) {
            if (temp.getClipBoardNumber() == number) {
                return temp.getData();
            }
            temp = temp.getNext();
        }
        return null;
    }

}
