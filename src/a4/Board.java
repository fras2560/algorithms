package a4;
import java.util.ArrayList;
import java.util.List;

public class Board {
  private final int[][] blocks;
  private final int dimension;
  private final static int OPEN_BLOCK = 0;
  /**
   * The default constructor for the board.
   * @param blocks a N by N board of blocks
   */
  public Board(int[][] blocks) {
    // construct a board from an n-by-n array of block}
    // (where blocks[i][j] = block in row i, column j)
    if (blocks == null) {
      throw new IllegalArgumentException("Blocks cannot be null for a board");
    }
    this.dimension = blocks.length;
    this.blocks = deepCopy(blocks, blocks.length);
  }

  /**
   * Returns the dimensions of the boards (N X N).
   * @return the dimension size
   */
  public int dimension() {
    // board dimension n
    return this.dimension;
    
  }

  /**
   * Gets the hamming measurement.
   *
   * <p>
   * Hamming priority function. The number of blocks in the wrong position,
   * plus the number of moves made so far to get to the search node.
   * Intuitively, a search node with a small number of blocks in the wrong position
   * is close to the goal, and we prefer a search node that have been
   * reached using a small number of moves.
   * </p>
   * @return the hamming measurement for the board
   */
  public int hamming() {

    // number of blocks out of place
    int hammingMeasurement = 0;
    for (int position = 1; position <= this.dimension() * this.dimension(); position++) {
      if (this.blocks[getRow(position)][getColumn(position)] !=  OPEN_BLOCK
          && this.blocks[getRow(position)][getColumn(position)] != position) {
        hammingMeasurement++;
      }
    }
    return hammingMeasurement;
  }

  /**
   * Get the manhattan measurement.
   *
   * <p>
   * Manhattan priority function. The sum of the Manhattan distances
   * (sum of the vertical and horizontal distance) from the blocks to their
   * goal positions, plus the number of moves made so far to get to the search node.
   * </p>
   * @return the manhattan measurement of the board
   */
  public int manhattan() {

    // sum of Manhattan distances between blocks and goal
    int manhattanMeasurement = 0;
    for (int position = 1; position <= this.dimension() * this.dimension(); position++) {
      manhattanMeasurement += manhattanHelper(position);
    }
    return manhattanMeasurement;
  }

  private int manhattanHelper(int position) {
    int expectedRow = getRow(position);
    int expectedColumn = getColumn(position);

    // check if it is in the expected position
    if (this.blocks[expectedRow][expectedColumn] == position) {
      return 0;
    }

    // else find where it is in the board
    int foundColumn;
    for (int row = 0; row < dimension(); row++) {
      foundColumn = indexOf(position, this.blocks[row]); 
      if (foundColumn != -1) {
        return Math.abs(expectedRow - row) + Math.abs(foundColumn - expectedColumn);
      }
    }
    return 0;
  }

  private static int indexOf(int value, int[] a) {
    for (int index = 0; index < a.length; index++) {
      if (a[index] == value) {
        return index;
      }
    }
    return -1;
  }

  /**
   * Returns whether the 8-puzzle problem is at the goal state.
   *
   * @return true if at goal state, false otherwise
   */
  public boolean isGoal() {
    // is this board the goal board?
    for (int position = 1; position <= this.dimension() * this.dimension(); position++) {
      if (this.blocks[getRow(position)][getColumn(position)] != OPEN_BLOCK
          && this.blocks[getRow(position)][getColumn(position)] != position) {
        return false;
      }
    }
    return true;
  }

  /**
   * A board the is created by exchanging a pair of blocks.
   * @return a twin board
   */
  public Board twin() {
    for (int position = 1; position < this.dimension() * this.dimension(); position++) {
      if (this.blocks[getRow(position)][getColumn(position)] != OPEN_BLOCK
          && this.blocks[getRow(position+1)][getColumn(position+1)] != OPEN_BLOCK
          && getRow(position) == getRow(position+1)) {
          return swapPositions(position, position+1);
      }
    }
    throw new RuntimeException();
  }

  private Board swapPositions(int p1, int p2) {
    // create a copy of the blocks
    int[][]swapBlocks = deepCopy(this.blocks, this.dimension());

    // swap two of the blocks
    int temp;
    temp = swapBlocks[getRow(p1)][getColumn(p1)];
    swapBlocks[getRow(p1)][getColumn(p1)] = swapBlocks[getRow(p2)][getColumn(p2)];
    swapBlocks[getRow(p2)][getColumn(p2)] = temp;
    return new Board(swapBlocks);
  }

  /**
   * Returns whether if two boards are equals.
   * @return true if boards are equal, false otherwise
   */
  public boolean equals(Object y) {

    // if null then they are not equal
    if(y == null) {
      return false;
    }

    // does this board equal y?
    if (y.getClass().equals(Board.class) && ((Board) y).dimension() == this.dimension()) {
      Board other = (Board) y;

      // loop through all of the board
      for (int position = 1; position <= this.dimension() * this.dimension(); position++) {
        if (this.blocks[getRow(position)][getColumn(position)]
            !=  other.blocks[getRow(position)][getColumn(position)]) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * Returns a iterator through a group of neighbor boards.
   * Iterable
   * @return a iterator through neighbor boards
   */
  public Iterable<Board> neighbors() {
    List<Board> neighbor = new ArrayList<Board>();
    for (int position = 1; position <= this.dimension() * this.dimension(); position++) {
      if (this.blocks[getRow(position)][getColumn(position)] == OPEN_BLOCK) {

        // check above
        if (getRow(position) > 0) {
          neighbor.add(swapPositions(position, position - this.dimension()));
        }

        // check below
        if (getRow(position) + 1 < this.dimension()) {
          neighbor.add(swapPositions(position, position + this.dimension()));
        }
        

        // check left
        if (getColumn(position) > 0) {
          neighbor.add(swapPositions(position, position - 1));
        }

        // check right
        if (getColumn(position) + 1 < this.dimension()) {
          neighbor.add(swapPositions(position, position + 1));
        }
        break;
      }
    }
    return neighbor;
  }

  private int getRow(int position) {
    return ((position - 1) / this.dimension());
  }

  private int getColumn(int position) {
    return (position - 1) % this.dimension();
    
  }

  private static int[][] deepCopy(int[][] blocksToCopy, int size) {
    int[][] newBlocks = new int[size][];
    for (int i = 0; i < size; i++) {
      newBlocks[i] = blocksToCopy[i].clone();
    }
    return newBlocks;
  }

  /**
   * Returns a string representation of the board.
   * @return returns a string representation of the board
   */
  public String toString() {

    // string representation of this board (in the output format specified below)
    // loop through all of the board
    StringBuilder builder = new StringBuilder();
    builder.append(this.dimension() + "\n");
    for (int row = 0; row < this.dimension(); row++) {
      for (int col = 0; col < this.dimension(); col++) {
        builder.append(String.format("%2d ", this.blocks[row][col]));
      }
      builder.append("\n");
    }
    return builder.toString();
  }

  public static void main(String[] args) {
    // unit tests (not graded)
  }

}
