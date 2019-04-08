package com.company.project3;



public class clipBdLinkedList {
    //LinkedList<NodeCB> CBLinkedList = new LinkedList<NodeCB>();
    private NodeCB top;
    private NodeCB tail;

    public clipBdLinkedList() {
        tail = top = null;
    }


    public void addClip(String clip){

        if (top == null){
            top.setData(clip);
        }
        else {
            NodeCB temp = top;
            top = new NodeCB(clip, temp, null);
            temp.setPrev(top);
        }
    }

    public NodeCB getClip(int position){
        NodeCB temp = top;
        for (int i = 0; i < position; i++)
            if(temp.getClipBoardNumber() == position){
                return temp;
            }
            temp = temp.getNext();
        return null;
    }


    // create methods you need.

}
