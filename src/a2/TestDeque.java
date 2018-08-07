/******************************************************************************
 *  Dependencies: none
 *  Author: Dallas Fraser
 *  Testing for the Deque structure
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package a2;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * A testing class for the deque
 *
 * @author dfraser
 *
 */
public class TestDeque {
	private Deque<Integer> deck;
	private static final String EMPTY_DECK_PROMPT = "Empty deck did not have a size of 0";

	/**
	 * Initializes an empty deque
	 */
	@Before
	public void setUp() {
		this.deck = new Deque<>();
	}

	/**
	 * Test case for the size method
	 */
	@Test
	public void testSize() {
		assertEquals(EMPTY_DECK_PROMPT, deck.size(), 0);
		// now add element
		this.deck.addFirst(1);
		assertEquals(EMPTY_DECK_PROMPT, deck.size(), 1);
		this.deck.addLast(1);
		assertEquals(EMPTY_DECK_PROMPT, deck.size(), 2);
		this.deck.addFirst(1);
		assertEquals(EMPTY_DECK_PROMPT, deck.size(), 3);
		this.deck.addLast(1);
		assertEquals(EMPTY_DECK_PROMPT, deck.size(), 4);
	}

	/**
	 * A test case for the size method faster removing
	 */
	@Test
	public void testSizeRemove() {
		this.deck.addFirst(1);
		this.deck.removeFirst();
		assertEquals(EMPTY_DECK_PROMPT, deck.size(), 0);
		this.deck.addLast(1);
		this.deck.removeLast();
		assertEquals(EMPTY_DECK_PROMPT, deck.size(), 0);
	}

	/**
	 * A test case for the is empty on an empty deque
	 */
	@Test
	public void testEmptyDeck() {
		assertEquals("Empty deck did not return its was empty", deck.isEmpty(), true);
	}

	/**
	 * A test case for non-empty deque by adding to the front
	 */
	@Test
	public void testEmptyDeckFirst() {
		this.deck.addFirst(1);
		assertEquals("Deck with element added to first is empty", deck.isEmpty(), false);
	}

	/**
	 * A test case for non-empty deque by adding to the end
	 */
	@Test
	public void testEmptyDeckLast() {
		// add an element
		this.deck.addFirst(1);
		assertEquals("Deck with element added to last is empty", deck.isEmpty(), false);
	}

	/**
	 * A test case for an empty deque by removing from the front
	 */
	@Test
	public void testEmptyDeckRemoveFirst() {
		this.deck.addFirst(1);
		this.deck.removeFirst();
		assertEquals("Deck with element removed from first is not empty", deck.isEmpty(), true);
	}

	/**
	 * A test case for an empty deque by removing from the end
	 */
	@Test
	public void testEmptyDeckRemoveLast() {
		this.deck.addFirst(1);
		this.deck.removeLast();
		assertEquals("Deck with element removed from last is not empty", deck.isEmpty(), true);
	}

	/**
	 * A test that adds to the front and removed items from the front
	 */
	@Test
	public void testAddFirst() {

		// add items to the front
		int i = 0;
		while (i < 3) {
			this.deck.addFirst(i);
			i++;
		}

		// remove items from the front until it is empty
		while(!this.deck.isEmpty()) {
			i--;
			assertEquals("Deck (built from addFirst) remove firstt did not give expected value",
					this.deck.removeFirst().intValue(),
					i);

		}
	}

	/**
	 * A test case for adding to the front and removing from the end until empty
	 */
	@Test
	public void testAddFirstRemoveLast() {

		// add some items to the front
		int i = 0;
		while (i < 3) {
			this.deck.addFirst(i);
			i++;
		}

		// remove items from the end until it empty
		i = 0;
		while(!this.deck.isEmpty()) {
			assertEquals("Deck (built from addFirst) remove last did not give expected value",
					this.deck.removeLast().intValue(),
					i);
			i++;
		}
	}

	/**
	 * A test case that adds to the front and then removes from end until empty
	 */
	@Test
	public void testAddLastRemoveLast() {
		// add some items to the front
		int i = 0;
		while (i < 3) {
			this.deck.addLast(i);
			i++;
		}

		// remove from the end until empty
		while(!this.deck.isEmpty()) {
			i--;
			assertEquals("Deck (built from addLast) removed last did not give expected value",
					this.deck.removeLast().intValue(),
					i);
		}
	}

	/**
	 * A test case that adds to the end and then removes from front until empty
	 */
	@Test
	public void testAddLastRemoveFirst() {

		// add some items to the end
		int i = 0;
		while (i < 3) {
			this.deck.addLast(i);
			i++;
		}

		// remove items until it is empty
		i = 0;
		while(!this.deck.isEmpty()) {
			assertEquals("Deck (built from addLast) removed first did not give expected value",
					this.deck.removeFirst().intValue(),
					i);
			i++;
		}
	}

	/**
	 * Test case for both add first and last and then removing
	 */
	@Test
	public void testComboFirstLast() {

		// add some elements to the back and start
		this.deck.addFirst(2);
		this.deck.addLast(3);
		this.deck.addFirst(1);
		this.deck.addLast(4);
		int i = 1;
		while (!this.deck.isEmpty()) {
			assertEquals("Deck (built using addLast and addFirst) did not give expected value",
					i,
					deck.removeFirst().intValue());
			i++;
		}

		// add some elements to the back and start
		this.deck.addFirst(2);
		this.deck.addLast(3);
		this.deck.addFirst(1);
		this.deck.addLast(4);
		i = 4;
		while (!this.deck.isEmpty()) {
			assertEquals("Deck (built using addLast and addFirst) did not give expected value",
					i,
					deck.removeLast().intValue());
			i--;
		}
	}

	/**
	 * A test case for the iterator
	 */
	@Test
	public void testIterator() {
		this.deck.addFirst(4);
		this.deck.addFirst(3);
		this.deck.addFirst(2);
		this.deck.addFirst(1);
		int j = 1;
		for (Integer i : this.deck) {
			assertEquals("Iterating deck did not give expected value", i.intValue(), j);
			j++;
		}

	}
}
