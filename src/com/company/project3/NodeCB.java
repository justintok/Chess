package com.company.project3;

public class NodeCB {

    private int clipBoardNumber;                //The number of the clip board
    private NodeD<Character> topOfClipBoard;    //The very first clip board
    private NodeCB next;                        //The node that comes after 'this' node
    private NodeCB prev;                        //The node that comes before 'this' node
    private String data;                        //The value that 'this' node holds

    /**
     * Constructor
     * @param data The value of the new NodeCB
     * @param next The NodeCB that comes after the new NodeCB
     * @param prev The NodeCB that comes before the new NodeCB
     * @param number The clip board number of the new NodeCB
     */
    public NodeCB(String data, NodeCB next, NodeCB prev, int number) {
        this.next = next;
        this.prev = prev;
        this.data = data;
        this.clipBoardNumber = number;
    }

    /******************************************************************************************
     * Sets the value of the NodeCB
     * @param data A String value
     */
    public void setData(String data){
        this.data = data;
    }

    /******************************************************************************************
     * Get the value of the NodeCB
     * @return the value of the NodeCB
     */
    public String getData(){
        return this.data;
    }

    /******************************************************************************************
     * Gets the NodeCB that comes after 'this' NodeCB
     * @return the next NodeCB
     */
    public NodeCB getNext(){
        return this.next;
    }

    /******************************************************************************************
     * Sets the next NodeCB
     * @param next a NodeCB to be after 'this' NodeCB
     */
    public void setNext(NodeCB next){
        this.next = next;
    }

    /******************************************************************************************
     * Gets the previous NodeCB
     * @return the NodeCB that's before 'this' NodeCB
     */
    public NodeCB getPrev(){
        return this.prev;
    }

    /******************************************************************************************
     * Sets the previous NodeCB
     * @param prev a NodeCB to be before 'this' NodeCB
     */
    public void setPrev(NodeCB prev){
        this.prev = prev;
    }

    /******************************************************************************************
     * @return the clipboard number of 'this' NodeCB
     */
    public int getClipBoardNumber() {
        return clipBoardNumber;
    }

    /******************************************************************************************
     * Sets 'this' NodeCB's clipboard number
     * @param clipBoardNumber a integer value
     */
    public void setClipBoardNumber(int clipBoardNumber) {
        this.clipBoardNumber = clipBoardNumber;
    }

    /******************************************************************************************
     * @return the top NodeCB
     */
    public NodeD getTopOfClipBoard() {
        return topOfClipBoard;
    }

    /******************************************************************************************
     * Sets the top NodeCB to a new NodeCB
     * @param topOfClipBoard The new NodeCB to be set to top
     */
    public void setTopOfClipBoard(NodeD topOfClipBoard) {
        this.topOfClipBoard = topOfClipBoard;
    }
}
