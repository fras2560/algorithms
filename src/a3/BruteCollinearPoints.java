package a3;
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
                                this.resize(this.numberSegments * 2);
                            }
                            this.lines[this.numberSegments] = new LineSegment(this.smallestPoint(points[p1],
                                                                                                 points[p2],
                                                                                                 points[p3],
                                                                                                 points[p4]),
                                                                              this.largestPoint(points[p1],
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
    }

    private Point smallestPoint(Point p1, Point p2, Point p3, Point p4) {
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

    private Point largestPoint(Point p1, Point p2, Point p3, Point p4) {
        Point largest;
        if(p1.compareTo(p2) <= 0 && p1.compareTo(p3) <= 0 && p1.compareTo(p4) <= 0) {
            largest = p1;
        } else if (p2.compareTo(p3) <= 0 && p2.compareTo(p4) <= 0) {
            largest = p2;
        } else if (p3.compareTo(p4) <= 0) {
            largest = p3;
        } else {
            largest = p4;
        }
        return largest;
    }

    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        int pos = 0;
        int copyPos = 0;
        while (pos < this.lines.length) {
            if (this.lines[pos] != null) {
                copy[copyPos] = this.lines[pos];
                copyPos++;
            }
            pos++;
            
        }
        this.lines = copy;
    }

    public int numberOfSegments() {
        return this.numberSegments;
    }

    public LineSegment[] segments() {
        return this.lines;
    }
}
