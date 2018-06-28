package a2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDeque {
    private Deque<Integer> deck;
    @Before
    public void setUp() throws Exception {
        this.deck = new Deque<Integer>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSize() {
        assertEquals("Empty deck did not have a size of 0", deck.size(), 0);
        // now add element
        this.deck.addFirst(new Integer(1));
        assertEquals("Empty deck did not have a size of 0", deck.size(), 1);
        this.deck.addLast(new Integer(1));
        assertEquals("Empty deck did not have a size of 0", deck.size(), 2);
        this.deck.addFirst(new Integer(1));
        assertEquals("Empty deck did not have a size of 0", deck.size(), 3);
        this.deck.addLast(new Integer(1));
        assertEquals("Empty deck did not have a size of 0", deck.size(), 4);
    }

    @Test
    public void testSizeRemove() {
        this.deck.addFirst(new Integer(1));
        this.deck.removeFirst();
        assertEquals("Empty deck (removed first) did not have a size of 0", deck.size(), 0);
        this.deck.addLast(new Integer(1));
        this.deck.removeLast();
        assertEquals("Empty deck (removed last) did not have a size of 0", deck.size(), 0);
    }

    @Test
    public void testEmptyDeck() {
        // empty deck
        assertEquals("Empty deck did not return its was empty", deck.isEmpty(), true);
        
    }

    @Test
    public void testEmptyDeckFirst() {
     // add an element
        this.deck.addFirst(new Integer(1));
        assertEquals("Deck with element added to first is empty", deck.isEmpty(), false);
    }

    @Test
    public void testEmptyDeckLast() {
     // add an element
        this.deck.addFirst(new Integer(1));
        assertEquals("Deck with element added to last is empty", deck.isEmpty(), false);
    }

    @Test
    public void testEmptyDeckRemoveFirst() {
     // add an element
        this.deck.addFirst(new Integer(1));
        this.deck.removeFirst();
        assertEquals("Deck with element removed from first is not empty", deck.isEmpty(), true);
    }

    @Test
    public void testEmptyDeckRemoveLast() {
     // add an element
        this.deck.addFirst(new Integer(1));
        this.deck.removeLast();
        assertEquals("Deck with element removed from last is not empty", deck.isEmpty(), true);
    }

    @Test
    public void testAddFirst() {
        int i = 0;
        while (i < 3) {
            this.deck.addFirst(new Integer(i));
            i++;
        }
        while(!this.deck.isEmpty()) {
            i--;
            assertEquals("Deck (built from addFirst) remove firstt did not give expected value",
                         this.deck.removeFirst().intValue(),
                         i);
            
        }
    }

    @Test
    public void testAddFirstRemoveLast() {
        int i = 0;
        while (i < 3) {
            this.deck.addFirst(new Integer(i));
            i++;
        }
        i = 0;
        while(!this.deck.isEmpty()) {
            assertEquals("Deck (built from addFirst) remove last did not give expected value",
                         this.deck.removeLast().intValue(),
                         i);
            i++;
        }
    }

    @Test
    public void testAddLastRemoveLast() {
        int i = 0;
        while (i < 3) {
            this.deck.addLast(new Integer(i));
            i++;
        }
        while(!this.deck.isEmpty()) {
            i--;
            assertEquals("Deck (built from addLast) removed last did not give expected value",
                         this.deck.removeLast().intValue(),
                         i);
        }
    }

    @Test
    public void testAddLastRemoveFirst() {
        int i = 0;
        while (i < 3) {
            this.deck.addLast(new Integer(i));
            i++;
        }
        i = 0;
        while(!this.deck.isEmpty()) {
            assertEquals("Deck (built from addLast) removed first did not give expected value",
                         this.deck.removeFirst().intValue(),
                         i);
            i++;
        }
    }

    @Test
    public void testComboFirstLast() {
        /* Test both add first and add last and the removal by add first*/
        this.deck.addFirst(new Integer(2));
        this.deck.addLast(new Integer(3));
        this.deck.addFirst(new Integer(1));
        this.deck.addLast(new Integer(4));
        int i = 1;
        while (!this.deck.isEmpty()) {
            assertEquals("Deck (built using addLast and addFirst) did not give expected value",
                         i,
                         deck.removeFirst().intValue());
            i++;
        }
        this.deck.addFirst(new Integer(2));
        this.deck.addLast(new Integer(3));
        this.deck.addFirst(new Integer(1));
        this.deck.addLast(new Integer(4));
        i = 4;
        while (!this.deck.isEmpty()) {
            assertEquals("Deck (built using addLast and addFirst) did not give expected value",
                         i,
                         deck.removeLast().intValue());
            i--;
        }
    }

    @Test
    public void testIterator() {
        this.deck.addFirst(new Integer(4));
        this.deck.addFirst(new Integer(3));
        this.deck.addFirst(new Integer(2));
        this.deck.addFirst(new Integer(1));
        int j = 1;
        for (Integer i : this.deck) {
            assertEquals("Iterating deck did not give expected value", i.intValue(), j);
            j++;
        }
        
    }
}
