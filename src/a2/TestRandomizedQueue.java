package a2;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestRandomizedQueue {
    private RandomizedQueue<Integer> rq;
    private static final int END_CONDITION = 10;
    @Before
    public void setUp() throws Exception {
        this.rq = new RandomizedQueue<Integer>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(timeout=100)
    public void testIsEmpty() {
        assertEquals("Init RQueue not empty", true, this.rq.isEmpty());
        this.rq.enqueue(new Integer(1));
        assertEquals("RQueue with one item is empty", false, this.rq.isEmpty());
        this.rq.dequeue();
        assertEquals("Empty RQueue where one item was removed is not empty", true, this.rq.isEmpty());
        this.rq.enqueue(new Integer(1));
        this.rq.enqueue(new Integer(2));
        assertEquals("RQueue with two item is empty", false, this.rq.isEmpty());
        this.rq.dequeue();
        assertEquals("RQueue with one item and one just removed is empty", false, this.rq.isEmpty());
        this.rq.dequeue();
        assertEquals("Empty RQueue where two item were removed is not empty", true, this.rq.isEmpty());
    }

    @Test(timeout=100)
    public void testSize() {
        assertEquals("Init RQueue is not size 0", 0, this.rq.size());
        this.rq.enqueue(new Integer(1));
        assertEquals("RQueue with one item is not size 1", 1, this.rq.size());
        this.rq.dequeue();
        assertEquals("Empty RQueue where one item was removed is not size 0", 0, this.rq.size());
        this.rq.enqueue(new Integer(1));
        this.rq.enqueue(new Integer(2));
        assertEquals("RQueue with two item is not size 2", 2, this.rq.size());
        this.rq.dequeue();
        assertEquals("RQueue with one item and one just removed is not size 1", 1, this.rq.size());
        this.rq.dequeue();
        assertEquals("Empty RQueue where two item were removed is not size 0", 0, this.rq.size());
    }

    @Test(timeout=100)
    public void testEnqueue() {
        // nothing really to test here except the resize
        for (int i = 0; i < END_CONDITION; i++) {
            this.rq.enqueue(new Integer(i));
        }
    }

    @Test(timeout=100)
    public void testDequeue() {
        int[] sorted_array = new int[END_CONDITION];
        for (int i = 0; i < END_CONDITION; i++) {
            this.rq.enqueue(new Integer(i));
            sorted_array[i] = i;
        }
        int[] arr = new int[END_CONDITION];
        int i = 0;
        while(!this.rq.isEmpty() && i < END_CONDITION) {
            arr[i] = this.rq.dequeue().intValue();
            i++;
        }
        assertEquals("There was not enough elements dequeue", i, END_CONDITION);
        assertFalse(Arrays.equals(arr, sorted_array));
        Arrays.sort(arr);
        assertArrayEquals("The dequeue elements removed elements", arr, sorted_array);
    }

    @Test(timeout=100)
    public void testSample() {
        this.rq.enqueue(new Integer(1));
        this.rq.enqueue(new Integer(2));
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
        assertEquals("The sample did not return a value after a large number of iterations", i1Count > 0, true);
        assertEquals("The sample did not return a value after a large number of iterations", i2Count > 0, true);
    }

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
    @Test(timeout=100)
    public void testIterator() {
        this.rq.enqueue(new Integer(1));
        this.rq.enqueue(new Integer(2));
        this.rq.enqueue(new Integer(3));
        this.rq.enqueue(new Integer(4));
        this.rq.enqueue(new Integer(5));
        int[] a1 = new int[5];
        int[] a2 = new int[5];
        int pos = 0;
        for(Integer i: this.rq) {
            a1[pos] = i.intValue();
            pos++;
        }
        pos = 0;
        for(Integer j: this.rq) {
            a2[pos] = j.intValue();
            pos++;
        }
        assertFalse("Two random iterators returns 5 values in the same order", Arrays.equals(a1, a2));
        assertTrue("One iterator did not return all 5 values", this.unique(a1));
        assertTrue("One iterator did not return all 5 values", this.unique(a2));
    }

}
