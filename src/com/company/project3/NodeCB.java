package com.company.project3;

public class NodeCB {
    private int clipBoardNumber;
    private NodeD<Character> topOfClipBoard;
    private NodeCB next;
    private NodeCB prev;
    private String data;

    public NodeCB(String data, NodeCB next, NodeCB prev, int number) {
        this.next = next;
        this.prev = prev;
        this.data = data;
        this.clipBoardNumber = number;
    }

    public void setData(String data){
        this.data = data;
    }

    public String getData(){
        return this.data;
    }

    public NodeCB getNext(){
        return this.next;
    }

    public void setNext(NodeCB next){
        this.next = next;
    }

    public NodeCB getPrev(){
        return this.prev;
    }

    public void setPrev(NodeCB prev){
        this.prev = prev;
    }

    public int getClipBoardNumber() {
        return clipBoardNumber;
    }

    public void setClipBoardNumber(int clipBoardNumber) {
        this.clipBoardNumber = clipBoardNumber;
    }

    public NodeD getTopOfClipBoard() {
        return topOfClipBoard;
    }

    public void setTopOfClipBoard(NodeD topOfClipBoard) {
        this.topOfClipBoard = topOfClipBoard;
    }
}
