package a5;

import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
/**
 *  The kd tree.
 *
 *  <p>
 *  A 2d-tree divides the unit square in a simple way: all the points to the left of the root go in the left subtree;
 *  all those to the right go in the right subtree; and so forth, recursively.
 *  </p>
 * @author Dallas Fraser
 */
public class KdTree {
  /**
   * The current level of the tree.
   *
   * @author Dallas Fraser
   */
  private  enum Level {

    /** A vertical level. */
    VERTICAL("VERTICAL"),

    /** A horizontal level. */
    HORIZONTAL("HORIZONTAL");

    /** String representation of the level. */
    private final String level;

    /** Enum constructor. */
    private Level(String level) {
      this.level = level;
    }

    /** Get the next level. */
    private Level nextLevel() {
      return this.level.equals("VERTICAL") ? Level.HORIZONTAL : Level.VERTICAL;
    }

    private static boolean isVertical(Level level) {
      return level.level.equals("VERTICAL");
    }
  };

  /** The size of the tree. */
  private int size;

  /** The root of the tree. */
  private Node root;

  /**
   * A node in the kd tree.
   *
   * @author Dallas
   */
  private class Node {

    /** The level of the node. */
    private Level level;

    /** The point where the node is. */
    private Point2D point;

    /** The rectangle outline of the node and its children. */
    private RectHV rectangle;

    /** The left child of the current node. */
    private Node left;

    /** The right child of the current node. */
    private Node right;

    /**
     * The node of the kd tree.
     *
     * @param point the point the node occurs
     * @param level the level of the node
     * @param rectangle the rectangle the node is part of
     */
    public Node(Point2D point, Level level, RectHV rectangle) {
      this.point = point;
      this.level = level;
      this.rectangle = rectangle;
    }

    /**
     * Returns true if given point is a left child of the node.
     *
     * @param point the point to check if it is less than (left child)
     * @return true if point is left child of the node, false otherwise
     */
    public boolean isLeftChildOfNode(Point2D point) {
      if (Level.isVertical(this.level)) {
        return this.point.x() > point.x();
      } else {
        return this.point.y() > point.y(); 
      }
    }

    public RectHV leftChildRectangle() {
      return Level.isVertical(this.level)
              ? new RectHV(this.rectangle.xmin(), this.rectangle.ymin(), this.point.x(), this.rectangle.ymax())
              : new RectHV(this.rectangle.xmin(), this.rectangle.ymin(), this.rectangle.xmax(), this.point.y());
    }

    public RectHV rightChildRectangle() {
      return Level.isVertical(this.level)
              ? new RectHV(this.point.x(), this.rectangle.ymin(), this.rectangle.xmax(), this.rectangle.ymax())
              : new RectHV(this.rectangle.xmin(), this.point.y(), this.rectangle.xmax(), this.rectangle.ymax());
    }
  }

  public KdTree() {

    // construct an empty 2d tree
    this.size = 0;
    this.root = null;
  }

  /**
   * Returns whether the point set is empty or not.
   *
   * @return true if empty, false otherwise
   */
  public boolean isEmpty() {

    // is the set empty?
    return this.size == 0; 
  }

  /**
   * Returns the size of the set.
   *
   * @return the size of the set
   */
  public int size() {

    // number of points in the set 
    return this.size;
  }

  /**
   * Inserts the given point into the set.
   *
   * @param point the point to insert
   */
  public void insert(Point2D point) {

    // add the point to the set (if it is not already in the set)
    if (point == null) {
      throw new IllegalArgumentException();
    }

    if (this.root == null) {
      this.root = new Node(point, Level.VERTICAL, new RectHV(0, 0, 1, 1));
      size += 1;
      return;
    }

    Node previous = null;
    Node current = this.root;
    while (current != null) {
      if (current.point.equals(point)) {
        return;
      }
      previous = current;
      current = current.isLeftChildOfNode(point) ? current.left : current.right;
    }
    if (previous.isLeftChildOfNode(point)) {
      previous.left = new Node(point, previous.level.nextLevel(), previous.leftChildRectangle());
    } else {
      previous.right = new Node(point, previous.level.nextLevel(), previous.rightChildRectangle());
    }
    size += 1;
  }

  /**
   * Checks whether the set contains the given point.
   *
   * @param point to check for
   * @return true if the set contains the point, false otherwise
   */
  public boolean contains(Point2D point) {

    // does the set contain point point?
    if (point == null) {
      throw new IllegalArgumentException();
    }

    Node current = this.root;
    while (current != null) {
      if (current.point.equals(point)) {
        return true;
      }
      current = current.isLeftChildOfNode(point) ? current.left : current.right;
    }
    return false; 
  }


  /**
   * Draw the points to the standard draw.
   */
  public void draw() {
    // draw all points to standard draw
    // SKIP
  }

  /**
   * Returns all the points that are inside the rectangle.
   *
   * @param rectangle the rectangle boundary
   * @return an iterable list of points
   */
  public Iterable<Point2D> range(RectHV rectangle) {
    if (rectangle == null) {
      throw new IllegalArgumentException();
    }

    // all points that are inside the rectangle (or on the boundary)
    return this.rangeAux(root, rectangle); 
  }

  /**
   * Returns all the points that are inside the rectangle (auxillary).
   *
   * @param current the current node
   * @param rectangle the rectangle boundary
   * @return an list of poitns inside the given rectangle
   */
  private List<Point2D> rangeAux(Node current, RectHV rectangle) {
    if (current == null) {
      return new LinkedList<>();
    }

    List<Point2D> points = new LinkedList<>();
    if (rectangle.contains(current.point)) {
      points.addAll(this.rangeAux(current.left, rectangle));
      points.addAll(this.rangeAux(current.right, rectangle));
      points.add(current.point);
      return points;
    }

    if (current.isLeftChildOfNode(new Point2D(rectangle.xmin(), rectangle.ymin()))) {
      points.addAll(this.rangeAux(current.left, rectangle));
    }

    if (current.isLeftChildOfNode(new Point2D(rectangle.xmax(), rectangle.ymax()))) {
      points.addAll(this.rangeAux(current.right, rectangle));
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
    return point == null ? null : this.nearestAux(this.root, point, this.root.point); 
  }

  /**
   * Checks what point in the set is nearest to the given point (auxillary).
   *
   * @param current the current node
   * @param point the point to find the nearest to
   * @param closest the closest point so far
   * @return the nearest point, unless empty than null
   */
  public Point2D nearestAux(Node current, Point2D point, Point2D closest) {
    if (current == null) {
      return closest;
    }

    double closestDist = closest.distanceTo(point);
    if (current.rectangle.distanceTo(point) < closestDist) {
        double nodeDist = current.point.distanceTo(point);
        if (nodeDist < closestDist) {
            closest = current.point;
        }
        if (current.isLeftChildOfNode(point)) {
            closest = this.nearestAux(current.left, point, closest);
            closest = this.nearestAux(current.right, point, closest);
        } else {
            closest = this.nearestAux(current.right, point, closest);
            closest = this.nearestAux(current.left, point, closest);
        }
    }
    return closest;
  }

}
