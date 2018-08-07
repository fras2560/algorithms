/******************************************************************************
 *  Dependencies: none
 *  Author: Dallas Fraser
 *  Testing for the RandomizedQueue structure
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package a2;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * A testing class for the randomized queue
 *
 * @author dfraser
 *
 */
public class TestRandomizedQueue {

	private RandomizedQueue<Integer> rq;
	private static final int END_CONDITION = 10;

	/**
	 * Initializes an empty queue
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() {
		this.rq = new RandomizedQueue<>();
	}


	/**
	 * A test case for is empty method
	 */
	@Test(timeout=100)
	public void testIsEmpty() {
		assertEquals("Init RQueue not empty", true, this.rq.isEmpty());
		this.rq.enqueue(1);
		assertEquals("RQueue with one item is empty", false, this.rq.isEmpty());
		this.rq.dequeue();
		assertEquals("Empty RQueue where one item was removed is not empty", true, this.rq.isEmpty());
		this.rq.enqueue(1);
		this.rq.enqueue(2);
		assertEquals("RQueue with two item is empty", false, this.rq.isEmpty());
		this.rq.dequeue();
		assertEquals("RQueue with one item and one just removed is empty", false, this.rq.isEmpty());
		this.rq.dequeue();
		assertEquals("Empty RQueue where two item were removed is not empty", true, this.rq.isEmpty());
	}

	/**
	 * A test case for the size method
	 */
	@Test(timeout=100)
	public void testSize() {
		assertEquals("Init RQueue is not size 0", 0, this.rq.size());
		this.rq.enqueue(1);
		assertEquals("RQueue with one item is not size 1", 1, this.rq.size());
		this.rq.dequeue();
		assertEquals("Empty RQueue where one item was removed is not size 0", 0, this.rq.size());
		this.rq.enqueue(1);
		this.rq.enqueue(2);
		assertEquals("RQueue with two item is not size 2", 2, this.rq.size());
		this.rq.dequeue();
		assertEquals("RQueue with one item and one just removed is not size 1", 1, this.rq.size());
		this.rq.dequeue();
		assertEquals("Empty RQueue where two item were removed is not size 0", 0, this.rq.size());
	}

	/**
	 * A test case to ensure the private resize works
	 */
	@Test(timeout=100)
	public void testEnqueue() {
		for (int i = 0; i < END_CONDITION; i++) {
			this.rq.enqueue(i);
		}
	}

	/**
	 * A test case for the deque method
	 */
	@Test(timeout=100)
	public void testDequeue() {

		// add some values to the queue
		int[] sortedArray = new int[END_CONDITION];
		for (int i = 0; i < END_CONDITION; i++) {
			this.rq.enqueue(i);
			sortedArray[i] = i;
		}

		// now removes those values
		int[] arr = new int[END_CONDITION];
		int i = 0;
		while(!this.rq.isEmpty() && i < END_CONDITION) {
			arr[i] = this.rq.dequeue().intValue();
			i++;
		}

		// make sure all the values were removed
		assertEquals("There was not enough elements dequeue", i, END_CONDITION);
		assertFalse(Arrays.equals(arr, sortedArray));
		Arrays.sort(arr);
		assertArrayEquals("The dequeue elements removed elements", arr, sortedArray);
	}

	/**
	 * A test case for the sample method
	 */
	@Test(timeout=100)
	public void testSample() {

		// adds 1 and 2 to the queue
		this.rq.enqueue(1);
		this.rq.enqueue(2);

		// pull 10 samples
		int i = 0;
		int sample;
		int i1Count = 0;
		int i2Count = 0;
		while (i < 10) {
			sample = this.rq.sample().intValue();
			assertEquals("The sample did not return expected element", true, sample == 1 || sample == 2);
			if(sample == 1) {
				i1Count++;
			}else {
				i2Count++;
			}
			i++;
		}

		// both numbers should be pulled once
		assertEquals("The sample did not return a value after a large number of iterations", i1Count > 0, true);
		assertEquals("The sample did not return a value after a large number of iterations", i2Count > 0, true);
	}

	/**
	 * A helper method to ensure all the values are unique in the array
	 *
	 * @param values
	 *            an array of values
	 * @return true if all values are unique, false otherwise
	 */
	private boolean unique(int[] values) {
		for(int i = 0; i < values.length; i++) {
			for(int j = i + 1; j < values.length; j++) {
				if(values[i] == values[j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * A test case for the iterator
	 */
	@Test(timeout=100)
	public void testIterator() {
		// adds some values
		this.rq.enqueue(1);
		this.rq.enqueue(2);
		this.rq.enqueue(3);
		this.rq.enqueue(4);
		this.rq.enqueue(5);
		int[] a1 = new int[5];
		int[] a2 = new int[5];

		// iterator through the queue once
		int pos = 0;
		for(Integer i: this.rq) {
			a1[pos] = i.intValue();
			pos++;
		}

		// iterator through the queue a second time
		pos = 0;
		for(Integer j: this.rq) {
			a2[pos] = j.intValue();
			pos++;
		}

		// both samples should have no repeating value and in general should not be same
		// order
		assertFalse("Two random iterators returns 5 values in the same order", Arrays.equals(a1, a2));
		assertTrue("One iterator did not return all 5 values", this.unique(a1));
		assertTrue("One iterator did not return all 5 values", this.unique(a2));
	}

}
