package a4;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {
  private SearchNode solution;

  private class SearchNode implements Comparable<SearchNode> {
    private int manhattan;
    private int hamming;
    private int moves;
    private Board board;
    private SearchNode parent;

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
      this.hamming = board.hamming();
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
      if (this.moves + (Math.min(this.manhattan, this.hamming))
          <= other.moves + Math.min(other.manhattan, other.hamming)) {
        return -1;
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
    int quit = 0;
    this.solution = null;
    MinPQ<SearchNode> queue = new MinPQ<>();
    MinPQ<SearchNode> twinQueue = new MinPQ<>();
    queue.insert(new SearchNode(initial, null, 0));
    twinQueue.insert(new SearchNode(initial.twin(), null, 0));
    SearchNode solution = null;
    SearchNode twinSolution = null;
    while (solution == null && twinSolution == null) {
      solution = starStep(queue, "Normal");
      twinSolution = starStep(twinQueue, "Twin");
      quit++;
      if (quit > 11) {
        break;
      }
    }
    if (solution != null) {
      System.out.println("Solution Found:" + solution.getBoard());
      System.out.println("Solution moves:" + solution.getMoves());
    } else {
      System.out.println("Solution Not Found:" + twinSolution);
    }
    
    this.solution = solution;

  }

  private SearchNode starStep(MinPQ<SearchNode> search, String title) {
    SearchNode current = search.delMin();
    System.out.println(title + current.getBoard());
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
