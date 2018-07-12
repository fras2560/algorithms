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
}
