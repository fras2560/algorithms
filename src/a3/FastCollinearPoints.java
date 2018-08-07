/******************************************************************************
 *  Dependencies: algs4
 *  Author: Dallas Fraser
 *  A Class for implementing a faster approach using sorting to find collinear points
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package a3;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * A Class for implementing a faster approach using sorting to finding collinear
 * points
 *
 * @author dfraser
 *
 */
public class FastCollinearPoints {
	private static final double EPSILON = 0.000000001;
	private final LineSegment[] lines;
	private final int numberSegments;

	/**
	 * A constructor for finding the collinear points for the given points using a
	 * faster approach by sorting the points
	 *
	 * @param points
	 *            the list of points
	 */
	public FastCollinearPoints(Point[] points) {

		// points cannot be null
		if (points == null) {
			throw new IllegalArgumentException();
		}

		int numSegments = 0;
		LineSegment[] tempLines = new LineSegment[points.length];

		for (int i = 0; i < points.length; i++) {
			Point origin = points[i];
			try {
				Arrays.sort(points, i+1, points.length, origin.slopeOrder());
			} catch (NullPointerException exception) {
				throw new IllegalArgumentException();
			}
			numSegments = this.pullLines(tempLines, numSegments, origin, points, i+1);
		}

		this.lines = FastCollinearPoints.resize(tempLines, numSegments);
		this.numberSegments = numSegments;
	}

	/**
	 * Updates the lines array by adding any lines from the origin that are
	 * collinear
	 *
	 * @param lines
	 *            an array of the line segments
	 * @param lineCount
	 *            the number of lines
	 * @param origin
	 *            the origin point
	 * @param points
	 *            a list of points
	 * @param i
	 *            the point to start comparing at
	 * @return int the number of lines after pulling lines
	 */
	private int pullLines(LineSegment[] lines, int lineCount, Point origin, Point[] points, int i) {

		// origin cannot be null
		if (origin == null) {
			throw new IllegalArgumentException();
		}

		// now check values
		double slope;
		double prev = 0;
		int count = 1;
		Point smallest = origin;
		Point largest = origin;

		while (i < points.length) {
			slope = origin.slopeTo(points[i]);

			// check if there are any duplicate points
			if (slope == Double.NEGATIVE_INFINITY) {
				throw new IllegalArgumentException();
			}

			if (Math.abs(slope  - prev) < EPSILON ||  slope == prev) {
				if (points[i].compareTo(smallest) <= 0) {
					smallest = points[i];
				}
				if (points[i].compareTo(largest) >= 0) {
					largest = points[i];
				}
				count += 1;
			} else {
				if(count >= 3) {
					if(lineCount == lines.length) {
						lines = FastCollinearPoints.resize(lines, lineCount * 2);
					}
					lines[lineCount] = new LineSegment(smallest, largest);
					lineCount += 1;
				}
				count = 1;
				smallest = points[i].compareTo(origin) <= 0 ? points[i] : origin;
				largest = points[i].compareTo(origin) >= 0 ? points[i] : origin;
				prev = origin.slopeTo(points[i]);
			}
			i++;
		}

		if (count >= 3) {
			if(lineCount == lines.length) {
				lines = FastCollinearPoints.resize(lines, lineCount * 2);
			}
			lines[lineCount] = new LineSegment(smallest, largest);
			lineCount += 1;
		}
		return lineCount;
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
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}
}
