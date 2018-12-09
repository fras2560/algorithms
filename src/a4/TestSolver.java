package a4;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSolver {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testSolverCase1() {
    int[][] blocks = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
    Board board = new Board(blocks);
    Solver solver = new Solver(board);
    assertTrue(solver.isSolvable());
    assertEquals(solver.moves(), 4);

    // check the solution returned in proper order
    List<Board> solution = new ArrayList<Board>();
    int[][] step0 = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
    int[][] step1 = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};
    int[][] step2 = {{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};
    int[][] step3 = {{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};
    int[][] step4 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    solution.add(new Board(step0));
    solution.add(new Board(step1));
    solution.add(new Board(step2));
    solution.add(new Board(step3));
    solution.add(new Board(step4));
    int i = 0;
    for (Board step : solver.solution()) {
      assertTrue(step.equals(solution.get(i)));
      i++;
    }
  }

  @Test
  public void testSolverUnsolvableCase() {
    int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
    Board board = new Board(blocks);
    Solver solver = new Solver(board);
    assertFalse(solver.isSolvable());
    assertEquals(solver.moves(), -1);
    assertEquals(solver.solution(), null);
  }

  @Test
  public void testSolverUnsolvableCase2() {
      int[][] blocks = {{1, 0}, {2, 3}};
      Board board = new Board(blocks);
      Solver solver = new Solver(board);
      assertFalse(solver.isSolvable());
      assertEquals(solver.moves(), -1);
      assertEquals(solver.solution(), null);
  }

  @Test
  public void testSolverUnsolvableCase3() {
      int[][] blocks = {{1, 2, 3}, {4, 6, 5}, {7, 8, 0}};
      Board board = new Board(blocks);
      Solver solver = new Solver(board);
      assertFalse(solver.isSolvable());
      assertEquals(solver.moves(), -1);
      assertEquals(solver.solution(), null);
  }

  @Test
  public void testSolverUnsolvableCase4() {
      int[][] blocks = {{3, 2, 4, 8}, {1, 6, 0 ,12}, {5, 10, 7, 11}, {9, 13, 14, 15}};
      Board board = new Board(blocks);
      Solver solver = new Solver(board);
      assertFalse(solver.isSolvable());
      assertEquals(solver.moves(), -1);
      assertEquals(solver.solution(), null);
  }

}
