package a2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_SIZE = 4;
    private int size;
    private Item items[];
    public RandomizedQueue() {
        // construct an empty randomized queue
        this.size = 0;
        this.items = (Item[]) new Object[INIT_SIZE];
    }

    public boolean isEmpty() {
        // is the randomized queue empty?
        return this.size == 0;
    }

    public int size() {
        // return the number of items on the randomized queue
        return this.size;
    }

    public void enqueue(Item item) {
        // add the item
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.size == this.items.length) {
                // the array is full
                // double the size of the array
                this.resize(this.items.length * 2);
        }
        this.items[this.size] = item;
        this.size++;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int pos = 0;
        int copyPos = 0;
        while (pos < this.items.length) {
            if (this.items[pos] != null) {
                copy[copyPos] = this.items[pos];
                copyPos++;
            }
            pos++;
            
        }
        this.items = copy;
    }

    public Item dequeue() {
        // remove and return a random item
        // get the position of a random element
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
        if (this.size < (this.items.length / 2) && this.items.length > INIT_SIZE) {
            // resize if half of its is not used
            this.resize(this.items.length / 2);
        }
        return item;
    }

    public Item sample() {
        // return a random item (but do not remove it)
        // remove and return a random item
        // get the position of a random element
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int pos = StdRandom.uniform(0, this.size);
        // get the item
        Item item = this.items[pos];
        return item;
    }

    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int pos = 0;
        private RandomizedQueueIterator() {
            StdRandom.shuffle(items, 0, size);
        }
        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return this.pos != size;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
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
    }
}
