/******************************************************************************
 *  Dependencies: algs4
 *  Author: Dallas Fraser
 *  A Class for for testing points class
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package a3;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

public class TestPoint {
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

	@Test
	public void testSlopeTo() {
		// point slope to inself negative infinity
		assertEquals(p1.slopeTo(p1), Double.NEGATIVE_INFINITY, 0.01);
		assertEquals(p2.slopeTo(p2), Double.NEGATIVE_INFINITY, 0.01);
		assertEquals(p3.slopeTo(p3), Double.NEGATIVE_INFINITY, 0.01);
		assertEquals(p4.slopeTo(p4), Double.NEGATIVE_INFINITY, 0.01);
		// horizontal slope
		assertEquals(p1.slopeTo(p4), +0.0, 0.01);
		assertEquals(p2.slopeTo(p3), +0.0, 0.01);
		assertEquals(p4.slopeTo(p1), +0.0, 0.01);
		assertEquals(p3.slopeTo(p2), +0.0, 0.01);
		// vertical slope
		assertEquals(p1.slopeTo(p3), Double.POSITIVE_INFINITY, 0.01);
		assertEquals(p2.slopeTo(p4), Double.POSITIVE_INFINITY, 0.01);
		assertEquals(p3.slopeTo(p1), Double.POSITIVE_INFINITY, 0.01);
		assertEquals(p4.slopeTo(p2), Double.POSITIVE_INFINITY, 0.01);
		// just a normal slope
		assertEquals(p1.slopeTo(p2), -1.0, 0.01);
		assertEquals(p2.slopeTo(p1), -1.0, 0.01);
		assertEquals(p3.slopeTo(p4), 1.0, 0.01);
		assertEquals(p4.slopeTo(p3), 1.0, 0.01);
	}

	@Test
	public void testCompareTo() {
		// all points equal themself
		assertEquals(p1.compareTo(p1), 0);
		assertEquals(p2.compareTo(p2), 0);
		assertEquals(p3.compareTo(p3), 0);
		assertEquals(p4.compareTo(p4), 0);
		// point 3 dominates
		assertEquals(p3.compareTo(p1), 1);
		assertEquals(p3.compareTo(p2), 1);
		assertEquals(p3.compareTo(p4), 1);
		// point 2 is second
		assertEquals(p2.compareTo(p1), 1);
		assertEquals(p2.compareTo(p4), 1);
		// p1 greater than p4
		assertEquals(p1.compareTo(p4), 1);
		// now the reverse of above
		// point 4 dominated by all other points
		assertEquals(p4.compareTo(p1), -1);
		assertEquals(p4.compareTo(p2), -1);
		assertEquals(p4.compareTo(p3), -1);
		// point 1 dominated by 2 and 3
		assertEquals(p1.compareTo(p2), -1);
		assertEquals(p1.compareTo(p3), -1);
		// point 2 dominated by point 3
		assertEquals(p2.compareTo(p3), -1);
	}

	@Test
	public void testSlopeOrder() {
		Comparator<Point> cp = p1.slopeOrder();
		// p2 vs p3
		assertEquals(cp.compare(p2, p3), -1);
		assertEquals(cp.compare(p3, p2), 1);
		// p3 vs p4
		assertEquals(cp.compare(p3, p4), 1);
		assertEquals(cp.compare(p4, p3), -1);
		// p2 vs p4
		assertEquals(cp.compare(p2, p4), -1);
		assertEquals(cp.compare(p4, p2), 1);
		// p2 vs itself
		assertEquals(cp.compare(p2, p2), 0);
	}
}
