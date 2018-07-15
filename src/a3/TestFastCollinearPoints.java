package a3;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestFastCollinearPoints {
    private Point[] points;
    @Before
    public void setUp() throws Exception {
        this.points = new Point[]{new Point(0, 0),
                                  new Point(1, 1),
                                  new Point(2, 2),
                                  new Point(0, 3),
                                  new Point(3, 6),
                                  new Point(6, 9),
                                  new Point(3, 3),
                                  new Point(9, 12)};
    }

    @Test
    public void testFastCollinearPoints() {
        // only need one test
        FastCollinearPoints bcp = new FastCollinearPoints(this.points);
        assertEquals(bcp.numberOfSegments(), 2);
        LineSegment[] lines = bcp.segments();
        assertEquals(lines.length, 2);
        assertEquals(lines[0].toString(), new LineSegment(new Point(0, 0), new Point(3, 3)).toString());
        assertEquals(lines[1].toString(), new LineSegment(new Point(0, 3), new Point(9, 12)).toString());
    }
}