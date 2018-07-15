package a3;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] lines;
    private final int numberSegments;
    private final static double EPSILON = 0.000000001; 
    public FastCollinearPoints(Point[] points) {
        int numSegments = 0;
        LineSegment[] tempLines = new LineSegment[points.length]; 
        for (int i = 0; i < points.length; i++) {
            Point origin = points[i];
            Arrays.sort(points, i+1, points.length, origin.slopeOrder());
            numSegments = this.pullLines(tempLines, numSegments, origin, points, i+1);
        }
        this.lines = FastCollinearPoints.resize(tempLines, numSegments);
        this.numberSegments = numSegments;
    }

    private int pullLines(LineSegment[] lines, int lineCount, Point origin, Point[] points, int i) {
        // now check values
        double prev = 0;
        int count = 1;
        Point smallest = origin;
        Point largest = origin;
        while (i < points.length) {
            if (Math.abs(origin.slopeTo(points[i])  - prev) < EPSILON) {
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

    public int numberOfSegments() {
        return this.numberSegments;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(this.lines, this.lines.length);
    }

    public static LineSegment[] resize(LineSegment[] array, int capacity) {
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
