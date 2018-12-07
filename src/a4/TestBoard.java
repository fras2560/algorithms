package a4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestBoard {
  @Test
  public void testDimension() {
    int[][] blocks = {{1}};
    assertEquals(new Board(blocks).dimension(), 1);
    int[][] blocks2 = {{1, 2}, {0, 3}};
    assertEquals(new Board(blocks2).dimension(), 2);
    int[][] blocks3 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    assertEquals(new Board(blocks3).dimension(), 3);
  }

  @Test
  public void testHamming() {
    int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
    Board board = new Board(blocks);
    assertEquals(board.hamming(), 5);
  }

  @Test
  public void testManhattan() {
    int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
    Board board = new Board(blocks);
    assertEquals(board.manhattan(), 10);
  }

  @Test
  public void testIsGoal() {

    // done case
    int[][] blocksGoal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    assertTrue(new Board(blocksGoal).isGoal());

    // not done case
    int[][] blocksNotGoal = {{0, 2, 3}, {4, 5, 6}, {7, 8, 1}};
    assertFalse(new Board(blocksNotGoal).isGoal());
  }

  @Test
  public void testTwin() {
    int[][] blocks = {{1}};
    Board board = new Board(blocks);
    Board twin = board.twin();
    assertTrue(board.equals(twin));
    
    int tests = 0;
    while (tests < 9) {
      int[][] blocks2 = {{1, 2}, {0, 3}};
      board = new Board(blocks2);
      twin = board.twin();
      assertFalse(board.equals(twin));
      assertFalse(twin.equals(board));
      tests++;
    }
    
  }

  @Test
  public void testEqualsObject() {

    // declare the compare object to
    int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    Board board = new Board(blocks);

    // case where they are equal
    assertTrue(board.equals(board));
    assertTrue(board.equals(new Board(blocks)));
    int[][] blocks2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    assertTrue(board.equals(new Board(blocks2)));

    // cases where they are not equal
    int[][] blocks3 = {{1}};
    assertFalse(board.equals(new Board(blocks3)));
    int[][] blocks4 = {{1, 2}, {0, 3}};
    assertFalse(board.equals(new Board(blocks4)));
    int[][] blocks5 = {{0, 2, 3}, {4, 5, 6}, {7, 8, 1}};
    assertFalse(board.equals(new Board(blocks5)));
  }

  @Test
  public void testNeighbors2D() {
    // bottom left corner case
    int[][] blocksBl = {{1, 2}, {0, 3}};
    int[][] blocksBl1 = {{0, 2}, {1, 3}};
    int[][] blocksBl2 = {{1, 2}, {3, 0}};
    Board board = new Board(blocksBl);
    Board[] expectBl = {new Board(blocksBl1), new Board(blocksBl2)};
    int i = 0;
    for (Board neighbor : board.neighbors()) {
      assertTrue(expectBl[i].equals(neighbor));
      assertTrue(neighbor.equals(expectBl[i]));
      i++;
    }

    // top left corner case
    int[][] blocksTl = {{0, 2}, {1, 3}};
    int[][] blocksTl1 = {{1, 2}, {0, 3}};
    int[][] blocksTl2 = {{2, 0}, {1, 3}};
    board = new Board(blocksTl);
    Board[] expectTl = {new Board(blocksTl1), new Board(blocksTl2)};
    i = 0;
    for (Board neighbor : board.neighbors()) {
      assertTrue(expectTl[i].equals(neighbor));
      assertTrue(neighbor.equals(expectTl[i]));
      i++;
    }

    // top right corner case
    int[][] blocksTr = {{1, 0}, {2, 3}};
    int[][] blocksTr1 = {{1, 3}, {2, 0}};
    int[][] blocksTr2 = {{0, 1}, {2, 3}};
    board = new Board(blocksTr);
    Board[] expectTr = {new Board(blocksTr1), new Board(blocksTr2)};
    i = 0;
    for (Board neighbor : board.neighbors()) {
      assertTrue(expectTr[i].equals(neighbor));
      assertTrue(neighbor.equals(expectTr[i]));
      i++;
    }

    // bottom right corner case
    int[][] blocksBr = {{1, 3}, {2, 0}};
    int[][] blocksBr1 = {{1, 0}, {2, 3}};
    int[][] blocksBr2 = {{1, 3}, {0, 2}};
    board = new Board(blocksBr);
    Board[] expectBr = {new Board(blocksBr1), new Board(blocksBr2)};
    i = 0;
    for (Board neighbor : board.neighbors()) {
      assertTrue(expectBr[i].equals(neighbor));
      assertTrue(neighbor.equals(expectBr[i]));
      i++;
    }
  }

  @Test
  public void testNeighbors3D() {
    // test the middle case
    int[][] blocks = {{1, 2, 3}, {4, 0, 5}, {6, 7, 8}};
    int[][] blocks1 = {{1, 0, 3}, {4, 2, 5}, {6, 7, 8}};
    int[][] blocks2 = {{1, 2, 3}, {4, 7, 5}, {6, 0, 8}};
    int[][] blocks3 = {{1, 2, 3}, {0, 4, 5}, {6, 7, 8}};
    int[][] blocks4 = {{1, 2, 3}, {4, 5, 0}, {6, 7, 8}};
    Board board = new Board(blocks);
    Board[] expectBr = {new Board(blocks1), new Board(blocks2),
        new Board(blocks3), new Board(blocks4)};
    int i = 0;
    for (Board neighbor : board.neighbors()) {
      assertTrue(expectBr[i].equals(neighbor));
      assertTrue(neighbor.equals(expectBr[i]));
      i++;
    }
    
  }

  @Test
  public void testToString() {
    int[][] blocks = {{1}};
    Board board = new Board(blocks);
    String expect = "-\n[1]\n-";
    assertEquals(board.toString(), expect);

    int[][] blocks2 = {{1, 2}, {0, 3}};
    board = new Board(blocks2);
    expect = "--\n[1, 2]\n[0, 3]\n--";
    assertEquals(board.toString(), expect);

    int[][] blocks3 = {{0, 2, 3}, {4, 5, 6}, {7, 8, 1}};
    board = new Board(blocks3);
    expect = "---\n[0, 2, 3]\n[4, 5, 6]\n[7, 8, 1]\n---";
    assertEquals(board.toString(), expect);
  }

}
