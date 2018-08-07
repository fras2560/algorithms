/******************************************************************************
 *  Dependencies: algs4
 *  Author: Dallas Fraser
 *  A Class for implementing a brute approach to finding collinear points
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package a3;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * A Class for implementing a brute approach to finding collinear points
 *
 * @author dfraser
 *
 */
public class BruteCollinearPoints {
	private static final double EPSILON = 0.000000001;
	private final LineSegment[] lines;
	private final int numberSegments;

	/**
	 * A constructor for finding the collinear points for the given points using a
	 * brute force approach
	 *
	 * @param points
	 *            the list of points
	 */
	public BruteCollinearPoints(Point[] points) {
		double s1;
		double s2;
		double s3;
		int segmentCount = 0;
		LineSegment[] lineSegments = new LineSegment[points.length];

		// four loops to find all points that are collinear
		for (int p1 = 0; p1 < points.length; p1++) {
			for (int p2 = p1 + 1; p2 < points.length; p2++) {
				for (int p3 = p2 + 1; p3 < points.length; p3++) {
					for (int p4 = p3 + 1; p4 < points.length; p4++) {
						s1 = points[p1].slopeTo(points[p2]);
						s2 = points[p1].slopeTo(points[p3]);
						s3 = points[p1].slopeTo(points[p4]);

						// compare the floating point to lines closer than EPILON or equal (vertical
						// lines)
						if ((Math.abs(s1 - s2) < EPSILON && Math.abs(s1 - s3) < EPSILON) || (s1 == s2 && s1 == s3)) {
							if (segmentCount == lineSegments.length) {
								lineSegments = BruteCollinearPoints.resize(lineSegments, segmentCount * 2);
							}
							lineSegments[segmentCount] = new LineSegment(BruteCollinearPoints.smallestPoint(points[p1],
									points[p2],
									points[p3],
									points[p4]),
									BruteCollinearPoints.largestPoint(points[p1],
											points[p2],
											points[p3],
											points[p4])
									);
							segmentCount += 1;
						}
					}
				}
			}
		}
		this.lines = BruteCollinearPoints.resize(lineSegments, segmentCount);
		this.numberSegments = segmentCount;
	}

	/**
	 * Returns the number of line segments
	 *
	 * @return the number of segments
	 */
	public int numberOfSegments() {
		return this.numberSegments;
	}

	/**
	 * Returns the line segments
	 *
	 * @return an array copy of the line segments
	 */
	public LineSegment[] segments() {
		return Arrays.copyOf(this.lines, this.lines.length);
	}

	/**
	 * Returns the smallest point of the four given points
	 *
	 * @param p1
	 *            a point
	 * @param p2
	 *            a point
	 * @param p3
	 *            a point
	 * @param p4
	 *            a point
	 * @return the smallest point
	 */
	private static Point smallestPoint(Point p1, Point p2, Point p3, Point p4) {
		Point smallest;
		if (p1.compareTo(p2) <= 0 && p1.compareTo(p3) <= 0 && p1.compareTo(p4) <= 0) {
			smallest = p1;
		} else if (p2.compareTo(p3) <= 0 && p2.compareTo(p4) <= 0) {
			smallest = p2;
		} else if (p3.compareTo(p4) <= 0) {
			smallest = p3;
		} else {
			smallest = p4;
		}
		return smallest;
	}

	/**
	 * Returns the largest point of the four given points
	 *
	 * @param p1
	 *            a point
	 * @param p2
	 *            a point
	 * @param p3
	 *            a point
	 * @param p4
	 *            a point
	 * @return the largest point
	 */
	private static Point largestPoint(Point p1, Point p2, Point p3, Point p4) {
		Point largest;
		if (p1.compareTo(p2) >= 0 && p1.compareTo(p3) >= 0 && p1.compareTo(p4) >= 0) {
			largest = p1;
		} else if (p2.compareTo(p3) >= 0 && p2.compareTo(p4) >= 0) {
			largest = p2;
		} else if (p3.compareTo(p4) >= 0) {
			largest = p3;
		} else {
			largest = p4;
		}
		return largest;
	}

	/**
	 * Returns a resized segment
	 *
	 * @param array
	 *            the array of lines
	 * @param capacity
	 *            the new capacity to resize to
	 * @return a resized array of line segments
	 */
	private static LineSegment[] resize(LineSegment[] array, int capacity) {
		LineSegment[] copy = new LineSegment[capacity];
		int pos = 0;
		int copyPos = 0;
		while (pos < array.length) {
			if (array[pos] != null) {
				copy[copyPos] = array[pos];
				copyPos++;
			}
			pos++;
		}
		return copy;
	}

	/**
	 * The main for testing
	 *
	 * @param args
	 *            n the number of points
	 */
	public static void main(String[] args) {

		// read the n points from a file
		In in = new In(args[0]);
		int n = in.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}
}
