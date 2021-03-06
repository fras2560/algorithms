package a4;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {
  private final SearchNode solution;

  private class SearchNode implements Comparable<SearchNode> {
    private final int manhattan;
    private final int moves;
    private final Board board;
    private final SearchNode parent;

    /**
     * Constructor.
     * @param board the current board
     * @param parent the parent of the current board (could be null)
     * @param moves the number of moves
     */
    public SearchNode(Board board, SearchNode parent, int moves) {
      this.board = board;
      this.parent = parent;
      this.manhattan = board.manhattan();
      this.moves = moves;
    }

    /**
     * Returns a collection of the possible moves from the current node.
     * @return an iterable collection
     */
    public Iterable<SearchNode> children() {
      List<SearchNode> kids = new ArrayList<SearchNode>();
      for (Board kid : this.board.neighbors()) {
        if (this.parent == null || !this.parent.getBoard().equals(kid)) {
          kids.add(new SearchNode(kid, this, this.moves + 1));
        }
      }
      return kids;
    }

    @Override
    public int compareTo(SearchNode other) {
      if (this.moves + this.manhattan
          < other.moves + other.manhattan) {
        return -1;
      } else if (this.moves + this.manhattan
          == other.moves +other.manhattan) {
        return 0;
      }
      return 1;
    }

    /**
     * A getter for the number of moves.
     * @return the number of moves
     */
    public int getMoves() {
      return this.moves;
    }

    /**
     * A getter for the board of the node.
     * @return the board
     */
    public Board getBoard() {
      return this.board;
    }

    public SearchNode getParent() {
      return this.parent;
    }
  }

  /**
   * Default constructor that finds solution if one is available.
   * @param initial the initial board
   */
  public Solver(Board initial) {
    // find a solution to the initial board (using the A* algorithm)
    MinPQ<SearchNode> queue = new MinPQ<>();
    MinPQ<SearchNode> twinQueue = new MinPQ<>();
    queue.insert(new SearchNode(initial, null, 0));
    twinQueue.insert(new SearchNode(initial.twin(), null, 0));
    SearchNode boardSolution = null;
    SearchNode twinSolution = null;
    while (boardSolution == null && twinSolution == null) {
      boardSolution = starStep(queue);
      twinSolution = starStep(twinQueue);
    }
    this.solution = boardSolution;
  }

  private SearchNode starStep(MinPQ<SearchNode> search) {
    SearchNode current = search.delMin();
    if (current.getBoard().isGoal()) {
      return current;
    }
    for (SearchNode kid : current.children()) {
      search.insert(kid);
    }
    return null;
    
  }

  /**
   * Returns whether the solver is able to solve the board.
   * @return true if board is solvabled, false otherwise
   */
  public boolean isSolvable() {
    // is the initial board solvable?
    return this.solution != null;
  }

  /**
   * Returns the number of moves to the solution.
   * @return if solvabled the number of moves, otherwise -1
   */
  public int moves() {

    // min number of moves to solve initial board; -1 if unsolvable
    return (this.solution == null) ? -1 : this.solution.getMoves();
  }

  /**
   * Returns an collection of the board from the starting board to the solution.
   * @return a collection of boards that form the solution
   */
  public Iterable<Board> solution() {
    // sequence of boards in a shortest solution; null if unsolvable
    if (this.solution == null) {
      return null;
    }
    List<Board> steps = new ArrayList<Board>();
    SearchNode node = this.solution;
    while (node != null) {
      steps.add(node.getBoard());
      node = node.getParent();
    }
    Collections.reverse(steps);
    return steps;
  }

  public static void main(String[] args) {
    // solve a slider puzzle (given below)
  }
}
