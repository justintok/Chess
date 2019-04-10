package com.company.project3;

import java.io.Serializable;

public class NodeD<E> implements Serializable {

    public E data;      //The node's data
    public NodeD next;  //The node that comes after 'this' node
    public NodeD prev;  //The node that comes before 'this' node

    /**
     * Constructor
     */
    public NodeD() {
        super();
    }

    /**
     * Constructor
     * @param data The data value of the new NodeD
     * @param next The NodeD that comes after 'this' NodeD
     * @param prev The NodeD that comes before 'this' NodeD
     */
    public NodeD(E data, NodeD next, NodeD prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    /******************************************************************************************
     * @return The data value of the NodeD
     */
    public E getData() {
        return data;
    }

    /******************************************************************************************
     * Sets the data value of the NodeD
     * @param data2 The new value of the NodeD
     */
    public void setData(E data2) {
        this.data = data2;
    }

    /******************************************************************************************
     * @return the NodeD after 'this' NodeD
     */
    public NodeD getNext() {
        return next;
    }

    /******************************************************************************************
     * Sets the NodeD that comes after 'this' NodeD
     * @param next the next NodeD
     */
    public void setNext(NodeD next) {
        this.next = next;
    }

    /******************************************************************************************
     * @return the NodeD that comes before 'this' NodeD
     */
    public NodeD getPrev() {
        return prev;
    }

    /******************************************************************************************
     * Sets the NodeD that comes before 'this' NodeD
     * @param prev the previous NodeD
     */
    public void setPrev(NodeD prev) {
        this.prev = prev;
    }
}
