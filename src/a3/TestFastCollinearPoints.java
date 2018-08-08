/******************************************************************************
 *  Dependencies: algs4
 *  Author: Dallas Fraser
 *  A Class for for testing fast collinear points class
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package a3;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestFastCollinearPoints {
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
	public void testFastCollinearPoints() {
		// only need one test
		FastCollinearPoints bcp = new FastCollinearPoints(this.points);
		assertEquals(bcp.numberOfSegments(), 2);
		LineSegment[] lines = bcp.segments();
		assertEquals(lines.length, 2);
		assertEquals(lines[0].toString(), new LineSegment(new Point(0, 0), new Point(3, 3)).toString());
		assertEquals(lines[1].toString(), new LineSegment(new Point(0, 3), new Point(9, 12)).toString());
	}

	@Test
	public void testVerticalCollinearPointsVerticalLine() {
		this.points = new Point[] { new Point(1000, 17000), new Point(1000, 27000), new Point(1000, 31000),
				new Point(1000, 28000) };
		FastCollinearPoints bcp = new FastCollinearPoints(this.points);
		assertEquals(bcp.numberOfSegments(), 1);
		LineSegment[] lines = bcp.segments();
		assertEquals(lines.length, 1);
		assertEquals(lines[0].toString(), new LineSegment(new Point(1000, 17000), new Point(1000, 31000)).toString());
	}

	@Test
	public void testHorizontalCollinearPoints() {
		this.points = new Point[] {new Point(7453, 14118),
				new Point(2682, 14118),
				new Point(7821, 14118),
				new Point(5067, 14118),
				new Point(9972, 4652),
				new Point(16307, 4652),
				new Point(5766, 4652),
				new Point(4750, 4652),
				new Point(13291, 7996),
				new Point(20547, 7996),
				new Point(10411, 7996),
				new Point(8934, 7996),
				new Point(1888, 7657),
				new Point(7599, 7657),
				new Point(12772, 7657),
				new Point(13832, 7657),
				new Point(10375, 12711),
				new Point(14226, 12711),
				new Point(20385, 12711),
				new Point(18177, 12711) };
		FastCollinearPoints bcp = new FastCollinearPoints(this.points);
		assertEquals(bcp.numberOfSegments(), 5);
		LineSegment[] lines = bcp.segments();
		assertEquals(lines.length, 5);
		assertEquals(lines[0].toString(), new LineSegment(new Point(2682, 14118), new Point(7821, 14118)).toString());
		assertEquals(lines[1].toString(), new LineSegment(new Point(1888, 7657), new Point(13832, 7657)).toString());
		assertEquals(lines[2].toString(), new LineSegment(new Point(4750, 4652), new Point(16307, 4652)).toString());
		assertEquals(lines[3].toString(), new LineSegment(new Point(8934, 7996), new Point(20547, 7996)).toString());
		assertEquals(lines[4].toString(), new LineSegment(new Point(10375, 12711), new Point(20385, 12711)).toString());
	}

	@Test
	public void testCourseraCase9() {
		this.points = new Point[] {
				new Point(9000, 9000),
				new Point(8000, 8000),
				new Point(7000, 7000),
				new Point(6000, 6000),
				new Point(5000, 5000),
				new Point(4000, 4000),
				new Point(3000, 3000),
				new Point(2000, 2000),
				new Point(1000, 1000) };
		FastCollinearPoints bcp = new FastCollinearPoints(this.points);
		assertEquals(bcp.numberOfSegments(), 1);
		LineSegment[] lines = bcp.segments();
		assertEquals(lines.length, 1);
		assertEquals(lines[0].toString(), new LineSegment(new Point(1000, 1000), new Point(9000, 9000)).toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDuplicatePointsTestCase1() {
		this.points = new Point[] { new Point(42156, 28089), new Point(20049, 13216), new Point(20049, 13216),
				new Point(1000, 28000) };
		new FastCollinearPoints(this.points);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDuplicatePointsTestCase2() {
		this.points = new Point[] { new Point(20049, 13216), new Point(42156, 28089), new Point(20049, 13216),
				new Point(1000, 28000) };
		new FastCollinearPoints(this.points);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDuplicatePointsTestCase3() {
		this.points = new Point[] { new Point(20049, 13216), new Point(20049, 13216), new Point(42156, 28089) };
		new FastCollinearPoints(this.points);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPoints() {
		new FastCollinearPoints(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPointTestCase1() {
		this.points = new Point[] { null, new Point(42156, 28089), new Point(20049, 13216), new Point(1000, 28000) };
		new FastCollinearPoints(this.points);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPointTestCase2() {
		this.points = new Point[] { new Point(42156, 28089), null, new Point(20049, 13216), new Point(1000, 28000) };
		new FastCollinearPoints(this.points);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPointTestCase3() {
		this.points = new Point[] { new Point(42156, 28089), new Point(20049, 13216), null, new Point(1000, 28000) };
		new FastCollinearPoints(this.points);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPointTestCase4() {
		this.points = new Point[] { new Point(42156, 28089), new Point(20049, 13216), new Point(1000, 28000), null };
		new FastCollinearPoints(this.points);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOneNullPoint() {
		this.points = new Point[] { null };
		new FastCollinearPoints(this.points);
	}
}