package com.company.project3;



public class clipBdLinkedList {
    //LinkedList<NodeCB> CBLinkedList = new LinkedList<NodeCB>();
    private NodeCB top;
    private NodeCB tail;

    public clipBdLinkedList() {
        tail = top = null;
    }


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


    // create methods you need.

}
