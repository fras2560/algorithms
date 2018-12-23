package a5;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A set of points in the unit square.
 *
 * @author Dallas
 *
 */
public class PointSET {
  /**
   * Holds the set of points.
   */
  private SET<Point2D> set;

  /**
   * Default constructor.
   */
  public PointSET() {

    // construct an empty set of points
    this.set = new SET<Point2D>();
  }

  /**
   * Returns whether the point set is empty or not.
   *
   * @return true if empty, false otherwise
   */
  public boolean isEmpty() {

    // is the set empty?
    return this.set.size() == 0;
  }

  /**
   * Returns the size of the set.
   *
   * @return the size of the set
   */
  public int size() {

    // number of points in the set
    return this.set.size();
  }

  /**
   * Inserts the given point into the set.
   *
   * @param point the point to insert
   */
  public void insert(Point2D point) {

    if (point == null) {
      throw new IllegalArgumentException();
    }

    // add the point to the set (if it is not already in the set)
    this.set.add(point);
  }

  /**
   * Checks whether the set contains the given point.
   *
   * @param point to check for
   * @return true if the set contains the point, false otherwise
   */
  public boolean contains(Point2D point) {
    if (point == null) {
      throw new IllegalArgumentException();
    }

    // does the set contain point p?
    return this.set.contains(point);
  }

  /**
   * Draw the points to the standard draw.
   */
  public void draw() {

    // draw all points to standard draw
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.01);

    // loop through all points
    for (Point2D point : this.set) {
      StdDraw.point(point.x(), point.y());
    }

    StdDraw.show();
  }

  /**
   * Returns all the points that are inside the rectangle.
   *
   * @param rect the rectangle boundary
   * @return an iterable list of points
   */
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }

    
    // all points that are inside the rectangle (or on the boundary)
    List<Point2D> points = new ArrayList<>();

    // loop through all points
    for (Point2D point : this.set) {
      if (rect.contains(point)) {
        points.add(point);
      }
    }

    return points;
  }

  /**
   * Checks what point in the set is nearest to the given point.
   *
   * @param point the point to find the nearest to
   * @return the nearest point, unless empty than null
   */
  public Point2D nearest(Point2D point) {
    if (point == null) {
      throw new IllegalArgumentException();
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    double minDis = Double.MAX_VALUE;
    Point2D closestPoint = null;

    for (Point2D check : this.set) {
      if (check.distanceSquaredTo(point) < minDis) {
        minDis = check.distanceSquaredTo(point);
        closestPoint = check;
      }
    }

    return closestPoint;
  }
}
