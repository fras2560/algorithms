package a3;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lines;
    private int numberSegments;
	public FastCollinearPoints(Point[] points) {
		this.lines = new LineSegment[points.length];
		for (int i = 0; i < points.length; i++) {
			Point origin = points[i];
			Arrays.sort(points, i+1, points.length, origin.slopeOrder());
			this.pullLines(origin, points, i+1);
		}
        this.lines = Helper.resize(this.lines, this.numberSegments);
	}

	private void pullLines(Point origin, Point[] points, int i) {
		// now check values
		double prev = 0;
		int count = 1;
		Point smallest = origin;
		Point largest = origin;
		while (i < points.length) {
			System.out.println("Point:" + points[i] + " Origin:" + origin + " Smallest:" + smallest + " Largest:" + largest + " prev:" + prev + " count:" + count);
			if (origin.slopeTo(points[i]) == prev) {
				if (points[i].compareTo(smallest) <= 0) {
					smallest = points[i];
				}
				if (points[i].compareTo(largest) >= 0) {
					largest = points[i];
				}
				count += 1;
			} else {
				if(count >= 3) {
					if(this.numberSegments == this.lines.length) {
                        this.lines = Helper.resize(this.lines, this.numberSegments * 2);
                    }
                    this.lines[this.numberSegments] = new LineSegment(smallest, largest);
                    this.numberSegments += 1;
				}
				count = 1;
				smallest = points[i].compareTo(origin) <= 0 ? points[i] : origin;
				largest = points[i].compareTo(origin) >= 0 ? points[i] : origin;
				prev = origin.slopeTo(points[i]);
			}
			i++;
		}
		if (count >= 3) {
			if(this.numberSegments == this.lines.length) {
                this.lines = Helper.resize(this.lines, this.numberSegments * 2);
            }
            this.lines[this.numberSegments] = new LineSegment(smallest, largest);
            this.numberSegments += 1;
		}
		System.out.println();
	}
	public int numberOfSegments() {
		return this.numberSegments;
	}

	public LineSegment[] segments() {
		return this.lines;
	}
}
