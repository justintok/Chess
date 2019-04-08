package com.company.project3;

public class clipBdLinkedList {
    private NodeCB top;
    private NodeCB tail;

    public clipBdLinkedList() {
        tail = top = null;
    }

    public void addClip(String clip){
        NodeCB temp = top;
        top = new NodeCB(clip,temp,null);
        temp.setPrev(top);
    }

    public NodeCB getClip(int position){
        NodeCB temp = top;
        for (int i = 0; i < position; i++)
            temp = temp.getNext();
        return temp;
    }
    // create methods you need.

}
