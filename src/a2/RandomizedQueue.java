/******************************************************************************
 *  Dependencies: algs4.jar
 *  Author: Dallas Fraser
 *  A data structure implementing the RandomizedQueue.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package a2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * The randomized queue
 *
 * @author dfraser
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
	private static final int INIT_SIZE = 4;
	private int size;
	private Item items[];

	/**
	 * Constructor for an empty queue
	 */
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		this.size = 0;
		this.items = (Item[]) new Object[INIT_SIZE];
	}

	/**
	 * Returns whether the queue is empty or not
	 *
	 * @return true if the queue is empty, false otherwise
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Returns the size of queue
	 *
	 * @return the size of the queue
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Adds an item to the queue
	 *
	 * @param item
	 *            the item to add to the queue
	 */
	public void enqueue(Item item) {

		// add the item
		if (item == null) {
			throw new IllegalArgumentException();
		}

		// the array is full
		// double the size of the array
		if (this.size == this.items.length) {
			this.resize(this.items.length * 2);
		}

		this.items[this.size] = item;
		this.size++;
	}

	/**
	 * Resizes the queue to the given capacity
	 *
	 * @param capacity
	 *            the new capacity of the queue
	 */
	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		Item[] copy = (Item[]) new Object[capacity];
		int pos = 0;
		int copyPos = 0;

		// loop through all the items and ignore nulls
		while (pos < this.items.length) {
			if (this.items[pos] != null) {
				copy[copyPos] = this.items[pos];
				copyPos++;
			}
			pos++;
		}
		this.items = copy;
	}

	/**
	 * Removes a random item from the queue
	 *
	 * @return the remove item
	 */
	public Item dequeue() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		int pos = StdRandom.uniform(0, this.size);

		// remove the item
		Item item = this.items[pos];
		Item last = this.items[this.size - 1];
		this.items[pos] = last;
		this.items[this.size - 1] = null;
		this.items[pos] = last;
		this.size--;

		// resize if half of its is not used
		if (this.size < (this.items.length / 2) && this.items.length > INIT_SIZE) {
			this.resize(this.items.length / 2);
		}
		return item;
	}

	/**
	 * Returns a random item but does not remove it
	 *
	 * @return A random item in the queue
	 * @throws NoSuchElementException
	 *             raised when called on empty queue
	 */
	public Item sample() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}

		int pos = StdRandom.uniform(0, this.size);
		return this.items[pos];
	}

	/**
	 * Returns the an Iterator to iterate through the randomized queue in random
	 * order
	 */
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	/**
	 * A Randomized Queue Iterator that is used to iterate through the
	 * RandomizedQueue
	 *
	 * @author dfraser
	 *
	 */
	private class RandomizedQueueIterator implements Iterator<Item> {
		private int pos = 0;

		/**
		 * The constructor
		 */
		private RandomizedQueueIterator() {
			StdRandom.shuffle(items, 0, size);
		}

		@Override
		public boolean hasNext() {
			return this.pos != size;
		}

		@Override
		public Item next() {
			if (this.pos == size) {
				throw new NoSuchElementException();
			}
			Item item = items[this.pos];
			this.pos++;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {

		// unit testing (optional)
		StdOut.println("Testing using JUnit - See TestRandomizedQueue");
	}
}
