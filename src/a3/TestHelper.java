package a3;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestHelper {
    private Point p1;
    private Point p2;
    private Point p3;
    private Point p4;
    @Before
    public void setUp() throws Exception {
        p1 = new Point(1, 0);
        p2 = new Point(0, 1);
        p3 = new Point(1, 1);
        p4 = new Point(0, 0);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSmallestPoint() {
        assertEquals(Helper.smallestPoint(p1, p2, p3, p4), p4);
    }

    @Test
    public void testLargestPoint() {
        assertEquals(Helper.largestPoint(p1, p2, p3, p4), p3);
    }

    @Test
    public void testResize() {
        fail("Not yet implemented");
    }

}
