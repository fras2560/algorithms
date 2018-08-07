/******************************************************************************
 *  Dependencies: algs4
 *  Author: Dallas Fraser
 *  A Class for for testing brute collinear points class
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package a3;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestBruteCollinearPoints {
	private Point[] points;
	@Before
	public void setUp() throws Exception {
		this.points = new Point[]{new Point(0, 0),
				new Point(1, 1),
				new Point(2, 2),
				new Point(0, 3),
				new Point(3, 6),
				new Point(6, 9),
				new Point(3, 3),
				new Point(9, 12)};
	}

	@Test
	public void testBruteCollinearPoints() {
		// only need on test
		BruteCollinearPoints bcp = new BruteCollinearPoints(this.points);
		assertEquals(bcp.numberOfSegments(), 2);
		LineSegment[] lines = bcp.segments();
		assertEquals(lines.length, 2);
		assertEquals(lines[0].toString(), new LineSegment(new Point(0, 0), new Point(3, 3)).toString());
		assertEquals(lines[1].toString(), new LineSegment(new Point(0, 3), new Point(9, 12)).toString());
	}

	@Test
	public void testBruteCollinearPointsVerticalLine() {
		this.points = new Point[] {new Point(1000, 17000),
				new Point(1000, 27000),
				new Point(1000, 31000),
				new Point(1000, 28000) };
		BruteCollinearPoints bcp = new BruteCollinearPoints(this.points);
		assertEquals(bcp.numberOfSegments(), 1);
		LineSegment[] lines = bcp.segments();
		assertEquals(lines.length, 1);
		assertEquals(lines[0].toString(), new LineSegment(new Point(1000, 17000), new Point(1000, 31000)).toString());
	}
}