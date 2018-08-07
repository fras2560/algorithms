/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque
 *  Dependencies: algs4.jar
 *  Author: Dallas Fraser
 *  A data structure implementing the Deque.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package a2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

/**
 * An implementation for the Deque
 *
 * @author dfraser
 *
 * @param <Item>
 *            The item type of the deque
 */
public class Deque<Item> implements Iterable<Item> {
	private Node first;
	private Node last;
	private int size;

	/**
	 * Constructor that creates an empty deque
	 */
	public Deque() {
		// construct an empty deque
		this.first = null;
		this.last = null;
		this.size = 0;
	}

	/**
	 * Private class that implements a Node for the Deque
	 *
	 * @author dfraser
	 *
	 */
	private class Node {
		private Item item;
		private Node next;
		private Node prev;

		/**
		 * Constructor that sets the item of the node
		 *
		 * @param item
		 *            the item to set the node to
		 */
		public Node(Item item) {
			this.item = item;
		}

		/**
		 * A getter for the item
		 *
		 * @return Item the item of the node
		 */
		public Item getItem() {
			return this.item;
		}

		/**
		 * A getter for the next node
		 *
		 * @return Node the next node
		 */
		public Node getNext() {
			return this.next;
		}

		/**
		 * A getter for the previous node
		 *
		 * @return Node the previous node
		 */
		public Node getPrevious() {
			return this.prev;
		}

		/**
		 * A setter for the next node
		 *
		 * @param next
		 *            the next node
		 */
		public void setNext(Node next) {
			this.next = next;
		}

		/**
		 * A setter for the previous node
		 *
		 * @param previous
		 *            the previous node
		 */
		public void setPrevious(Node previous) {
			this.prev = previous;
		}
	}

	/**
	 * Returns whether the deque is empty
	 *
	 * @return true if deque is empty, false otherwise
	 */
	public boolean isEmpty() {
		// is the deque empty?
		return this.size == 0;
	}

	/**
	 * Returns the size of the deque
	 *
	 * @return the size of the deque
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Add to the start of the deque
	 *
	 * @param item
	 *            the item to add
	 */
	public void addFirst(Item item) {
		// add the item to the front
		if (item == null) {
			throw new IllegalArgumentException();
		}

		if (this.first == null) {

			// empty list
			this.first = new Node(item);
			this.last = this.first;
		} else {

			// this is one element in the list
			Node temp = this.first;
			this.first = new Node(item);
			this.first.setNext(temp);
			temp.setPrevious(this.first);
		}

		// increase the size
		this.size++;
	}

	/**
	 * Add to the end of the deque
	 *
	 * @param item
	 *            the item to add
	 */
	public void addLast(Item item) {

		// add the item to the end
		if (item == null) {
			throw new IllegalArgumentException();
		}

		if (this.last == null) {

			// empty deque
			this.first = new Node(item);
			this.last = this.first;
		} else {

			// at least one element
			Node temp = this.last;
			this.last = new Node(item);
			this.last.setPrevious(temp);
			temp.setNext(this.last);
		}
		this.size++;
	}

	/**
	 * Remove from the front of the deque
	 *
	 * @return Item the item from the front of the deque
	 */
	public Item removeFirst() {
		// remove and return the item from the front
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		Item result = null;
		if (this.size == 1) {
			// if only one element then need to update first too
			this.last = null;
			result = this.first.getItem();
			this.first = null;
		} else {
			// there is at least two elements
			result = this.first.getItem();
			this.first = this.first.getNext();
			this.first.setPrevious(null);
		}
		// just removed an element
		this.size--;
		return result;
	}

	/**
	 * Remove from the end of the deque
	 *
	 * @return Item the last item in the deque
	 */
	public Item removeLast() {

		// remove and return the item from the end
		if (this.size == 0) {
			throw new NoSuchElementException();
		}

		Item result = null;
		if (this.size == 1) {

			// if only one element then need to update first too
			this.first = null;
			result = this.last.getItem();
			this.last = null;
		} else {

			// there is at least two elements
			result = this.last.getItem();
			this.last = this.last.getPrevious();
			this.last.setNext(null);
		}

		// just removed an element
		this.size--;
		return result;
	}

	/**
	 * Returns the an Iterator to iterate through the deque from the front to end
	 */
	public Iterator<Item> iterator() {

		// return an iterator over items in order from front to end
		return new DequeIterator();
	}

	/**
	 * A Deque Iterator that is used to iterate through the deque
	 *
	 * @author dfraser
	 *
	 */
	private class DequeIterator implements Iterator<Item> {
		private Node current = first;

		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public Item next() {
			if (current == null) {
				throw new NoSuchElementException();
			}

			Item item = current.getItem();
			current = current.getNext();
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * No testing main program - see testing class TestDeque
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		// unit testing (optional)
		StdOut.println("Testing using JUnit - See TestDeque");
	}
}
