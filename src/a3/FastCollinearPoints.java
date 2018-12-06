/******************************************************************************
 *  Dependencies: algs4
 *  Author: Dallas Fraser
 *  A Class for implementing a faster approach using sorting to find collinear points
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package a3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * A Class for implementing a faster approach using sorting to finding collinear
 * points.
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
   * faster approach by sorting the points.
   *
   * @param points the list of points
   */
  public FastCollinearPoints(Point[] points) {

    // points cannot be null or any particular point
    if (points == null || Arrays.asList(points).contains(null)) {
      throw new IllegalArgumentException();
    }

    List<Point> pointList = new ArrayList<>(Arrays.asList(points));
    List<Point[]> tempLines = new ArrayList<>();
    while (!pointList.isEmpty()) {
      Point origin = pointList.remove(0);
      Collections.sort(pointList, origin.slopeOrder());
      this.pullLines(tempLines, origin, pointList);
    }
    this.lines = createLines(tempLines);
    this.numberSegments = tempLines.size();
  }

  /**
   * Creates the line segment array from the given points.
   *
   * @param lineSegments a list of two points forming a line
   * @return an array of line segments
   */
  private static LineSegment[] createLines(List<Point[]> lineSegments) {
    LineSegment[] createdLines = new LineSegment[lineSegments.size()];
    for (int i = 0; i < lineSegments.size(); i++) {
      createdLines[i] = new LineSegment(lineSegments.get(i)[0], lineSegments.get(i)[1]);
    }
    return createdLines;
  }

  /**
   * Pulls the collinear lines and adds them to the given lines list.
   *
   * @param lines  a list of lines
   * @param origin the origin point
   * @param points a list of points
   */
  private void pullLines(List<Point[]> lineSegments, Point origin, List<Point> points) {

    // initialize some values
    int count;
    double prevSlope;
    Point current = null;
    Point smallest;
    Point largest;

    // get an iterator and init current to the first element
    Iterator<Point> point = points.iterator();
    if (!point.hasNext()) {
      return;
    }
    current = nextPoint(point);
    while (current != null) {

      // set the largest and smallest, and count and previous slope
      count = 0;
      prevSlope = origin.slopeTo(current);
      smallest = origin.compareTo(current) <= 0 ? origin : current;
      largest = origin.compareTo(current) >= 0 ? origin : current;

      // see how many points in a row have the same slope to the origin
      while (current != null && FastCollinearPoints.sameSlope(prevSlope, origin.slopeTo(current))) {
        largest = largest.compareTo(current) >= 0 ? largest : current;
        smallest = smallest.compareTo(current) <= 0 ? smallest : current;
        count += 1;
        current = nextPoint(point);
      }

      Point[] line = new Point[] { smallest, largest };
      // check to see if have enough points and is an unique line
      if (count >= 3 && FastCollinearPoints.uniqueLineSegment(lineSegments, line)) {
        lineSegments.add(line);
      }
    }
  }

  /**
   * Returns the number of line segments.
   *
   * @return the number of segments
   */
  public int numberOfSegments() {
    return this.numberSegments;
  }

  /**
   * Returns whether the two given slopes are the same.
   *
   * @param s1 the first slope
   * @param s2 the second slope
   * @return true if slopes are considered the same, false otherwise
   */
  private static boolean sameSlope(double s1, double s2) {
    if (s1 == Double.NEGATIVE_INFINITY || s2 == Double.NEGATIVE_INFINITY) {
      throw new IllegalArgumentException();
    }
    return Math.abs(s1 - s2) < EPSILON || s1 == s2;
  }

  /**
   * Returns the next point or null if no point is available.
   *
   * @param points the points iterator
   * @return Point or null
   */
  private static Point nextPoint(Iterator<Point> points) {
    if (points.hasNext()) {
      return points.next();
    }
    return null;
  }

  /**
   * Returns the line segments.
   *
   * @return an array copy of the line segments
   */
  public LineSegment[] segments() {
    return Arrays.copyOf(this.lines, this.lines.length);
  }

  /**
   * Check to see if the line segment is unique and not already added yet.
   *
   * @param lines     the list of lines
   * @param lineCheck the line to check
   * @return true if unique, false otherwise
   */
  private static boolean uniqueLineSegment(List<Point[]> lineSegments, Point[] lineCheck) {
    for (Point[] linePoints : lineSegments) {
      if ((linePoints[0].compareTo(lineCheck[0]) == 0 && linePoints[1].compareTo(lineCheck[1]) == 0)
          || (linePoints[0].compareTo(lineCheck[0]) == 0
              && sameSlope(linePoints[0].slopeTo(lineCheck[1]), linePoints[0].slopeTo(linePoints[1])))
          || (linePoints[1].compareTo(lineCheck[1]) == 0
              && sameSlope(linePoints[1].slopeTo(lineCheck[0]), linePoints[1].slopeTo(linePoints[0])))) {
        // line are identical
        return false;
      }
    }
    return true;
  }

  /**
   * The main for testing.
   *
   * @param args n the number of points
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
