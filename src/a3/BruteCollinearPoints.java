package a3;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
/**
 * 
 * @author dfraser
 *
 */
public class BruteCollinearPoints {
    private LineSegment[] lines;
    private int numberSegments;
    public BruteCollinearPoints(Point[] points) {
        double s1,s2,s3;
        this.numberSegments = 0;
        this.lines = new LineSegment[points.length];
        for(int p1 = 0; p1 < points.length; p1++) {
            for(int p2 = p1+1; p2 < points.length; p2++) {
                for(int p3 = p2+1; p3 < points.length; p3++) {
                    for(int p4 = p3+1; p4 < points.length; p4++) {
                        s1 = points[p1].slopeTo(points[p2]);
                        s2 = points[p1].slopeTo(points[p3]);
                        s3 = points[p1].slopeTo(points[p4]);
                        if(s1 == s2 && s1 == s3) {
                            if(this.numberSegments == this.lines.length) {
                                this.lines = BruteCollinearPoints.resize(this.lines, this.numberSegments * 2);
                            }
                            this.lines[this.numberSegments] = new LineSegment(BruteCollinearPoints.smallestPoint(points[p1],
                                                                                                   points[p2],
                                                                                                   points[p3],
                                                                                                   points[p4]),
                                                                              BruteCollinearPoints.largestPoint(points[p1],
                                                                                                  points[p2],
                                                                                                  points[p3],
                                                                                                  points[p4])
                                                                              );
                            this.numberSegments += 1;
                        }
                        
                    }
                }
            }
        }
        this.lines = BruteCollinearPoints.resize(this.lines, this.numberSegments);
    }

    public int numberOfSegments() {
        return this.numberSegments;
    }

    public LineSegment[] segments() {
        return this.lines;
    }

    private static Point smallestPoint(Point p1, Point p2, Point p3, Point p4) {
        Point smallest;
        if(p1.compareTo(p2) <= 0 && p1.compareTo(p3) <= 0 && p1.compareTo(p4) <= 0) {
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

    private static Point largestPoint(Point p1, Point p2, Point p3, Point p4) {
        Point largest;
        if(p1.compareTo(p2) >= 0 && p1.compareTo(p3) >= 0 && p1.compareTo(p4) >= 0) {
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
