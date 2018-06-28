package a2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;
    public Deque() {
        // construct an empty deque
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    private class Node{
        public Item item;
        public Node next;
        public Node prev;
        public Node(Item item) {
            this.item = item;
        }
    }

    public boolean isEmpty() {
     // is the deque empty?
        return this.size == 0;
    }

    public int size() {
        // return the number of items on the deque
        return this.size;
    }

    public void addFirst(Item item) {
        // add the item to the front
        if(item == null) {
            throw new IllegalArgumentException();
        }
        if(this.first == null) {
            // empty list
            this.first = new Node(item);
            this.last = this.first;
        }else {
            // this is one element in the list
            Node temp = this.first;
            this.first = new Node(item);
            this.first.next = temp;
            temp.prev = this.first;
        }
        // increase the size
        this.size++;
        return;
    }

    public void addLast(Item item) {
        // add the item to the end
        if(item == null) {
            throw new IllegalArgumentException();
        }
        if(this.last == null) {
            // empty deque
            this.first = new Node(item);
            this.last = this.first;
        }else {
            // at least one element
            Node temp = this.last;
            this.last = new Node(item);
            this.last.prev = temp;
            temp.next = this.last;
        }
        this.size++;
        return;
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if(this.size == 0) {
            throw new NoSuchElementException();
        }
        Item result = null;
        if(this.size == 1) {
            // if only one element then need to update first too
            this.last = null;
            result = this.first.item;
            this.first = null;
        }else {
            // there is at least two elements
            result = this.first.item;
            this.first = this.first.next;
            this.first.prev = null;
        }
        // just removed an element
        this.size--;
        return result;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if(this.size == 0) {
            throw new NoSuchElementException();
        }
        Item result = null;
        if(this.size == 1) {
            // if only one element then need to update first too
            this.first = null;
            result = this.last.item;
            this.last = null;
        }else {
            // there is at least two elements
            result = this.last.item;
            this.last = this.last.prev;
            this.last.next = null;
        }
        // just removed an element
        this.size--;
        return result;
    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>{
        private Node current = first;

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return this.current != null;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        // unit testing (optional)
        System.out.println("Testing using JUnit - See TestDeque");
    }
}
