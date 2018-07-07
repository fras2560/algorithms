package a2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private int last;
    private Item items[];
    private final static int INIT_SIZE = 4;
    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        // construct an empty randomized queue
        this.last = -1;
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
        if (this.last + 1 == this.items.length) {
            // the array is full
            // double the size of the array
            this.resize(this.items.length * 2);
        }
        // move to the next position
        this.last++;
        this.items[this.last] = item;
        this.size++;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int pos = 0;
        int copy_pos = 0;
        while (pos < this.items.length) {
            if(this.items[pos] != null) {
                copy[copy_pos] = this.items[pos];
                copy_pos++;
            }
            pos++;
            
        }
        this.items = copy;
        // the last item that was added
        this.last = copy_pos - 1;
    }

    public Item dequeue() {
        // remove and return a random item
        // get the position of a random element
        if(this.size == 0) {
            throw new NoSuchElementException();
        }
        int pos = StdRandom.uniform(0, this.last + 1);
        while(this.items[pos] == null) {
            // goes to 0 if get to end
            pos = (pos + 1) % this.items.length;
        }
        // remove the item
        Item item = this.items[pos];
        this.items[pos] = null;
        this.size --;
        // if item removed was last item in the queue
        // move the last position down
        if(this.size == 0) {
            // no item left
            this.last = -1;
        }else {
            // at least one so move down until find it
            while(this.items[this.last] == null) {
                this.last--;
            }
        }
        if((this.last < (this.items.length / 2) || this.size < (this.items.length / 2)) && this.items.length > INIT_SIZE ) {
            // resize if half of its is not used
            this.resize(this.items.length / 2);
        }
        return item;
    }

    public Item sample() {
        // return a random item (but do not remove it)
        // remove and return a random item
        // get the position of a random element
        if(this.size == 0) {
            throw new NoSuchElementException();
        }
        int pos = StdRandom.uniform(0, this.last + 1);
        while(this.items[pos] == null) {
            // goes to 0 if get to end
            pos = (pos + 1) % this.size;
        }
        // get the item
        Item item = this.items[pos];
        return item;
    }

    private void removeNulls() {
        this.resize(this.size);
    }

    public Iterator<Item> iterator(){
        // return an independent iterator over items in random order
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>{
        private int pos = 0;
        private RandomizedQueueIterator() {
            removeNulls();
            StdRandom.shuffle(items);
        }
        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return this.pos != size;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
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
