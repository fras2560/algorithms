package a3;

public  class Helper {
    public static Point smallestPoint(Point p1, Point p2, Point p3, Point p4) {
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

    public static Point largestPoint(Point p1, Point p2, Point p3, Point p4) {
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
}
