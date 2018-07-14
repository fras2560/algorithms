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
                                this.lines = Helper.resize(this.lines, this.numberSegments * 2);
                            }
                            this.lines[this.numberSegments] = new LineSegment(Helper.smallestPoint(points[p1],
                                                                                                   points[p2],
                                                                                                   points[p3],
                                                                                                   points[p4]),
                                                                              Helper.largestPoint(points[p1],
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
        this.lines = Helper.resize(this.lines, this.numberSegments);
    }

    public int numberOfSegments() {
        return this.numberSegments;
    }

    public LineSegment[] segments() {
        return this.lines;
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
