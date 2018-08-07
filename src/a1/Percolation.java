/******************************************************************************
 *  Dependencies: algs4
 *  Author: Dallas Fraser
 *  A data structure to see if something percolates.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package a1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * This class holds a data structure used to find when something percolates.
 * @author Dallas Fraser
 * @since 2018-06-06
 */
public class Percolation {
	private final WeightedQuickUnionUF connections;
	private final int n;
	private final int top;
	private final int bottom;
	private boolean[] sites;
	private int openedSites;

	/**
	 * The default constructor that creates an n by n square
	 *
	 * @param n
	 *            the size of n
	 */
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		this.sites = new boolean[n*n+2];
		for (int index = 0; index <= (n*n) + 1; index++) {
			this.sites[index] = false;
		}

		this.n = n;
		this.connections = new WeightedQuickUnionUF(n*n+2);
		this.top = 0;
		this.bottom = n*n + 1;
		this.sites[top] = true;
		this.sites[bottom] = true;
		this.openedSites = 0;
	}

	/**
	 * Returns the index into the data structure for row and col indices
	 *
	 * @param row
	 *            the row index
	 * @param col
	 *            the column index
	 * @return the index into the data structure
	 */
	private int rowColTransposed(int row, int col) {
		if (row <= 0 || row > this.n || col <= 0  || col > this.n) {
			throw new IllegalArgumentException();
		}
		return (col) + this.n * (row - 1);
	}

	/**
	 * Connects one index to another index
	 *
	 * @param index1
	 *            the first index
	 * @param index2
	 *            the second index
	 */
	private void connect(int index1, int index2) {
		if (this.sites[index1] && this.sites[index2]) {
			this.connections.union(index1, index2);
		}
	}

	/**
	 * Open the square in row <i> row </i> and column <i> col </i>
	 *
	 * @param row
	 *            the row index
	 * @param col
	 *            the column index
	 */
	public void open(int row, int col) {
		this.openedSites ++;
		int index = this.rowColTransposed(row, col);
		this.sites[index] = true;

		// do up and above first
		if (row == 1 ) {
			this.connect(this.top, index);
			this.connect(index, this.rowColTransposed(row + 1, col));
		} else if (row == this.n) {
			this.connect(this.bottom, index);
			this.connect(index, this.rowColTransposed(row - 1, col));
		} else {
			this.connect(index, this.rowColTransposed(row - 1, col));
			this.connect(index, this.rowColTransposed(row + 1, col));
		}

		// if not in the first or last column
		if (col > 1) {
			this.connect(index, this.rowColTransposed(row, col - 1));
		}
		if (col < this.n) {
			this.connect(index, this.rowColTransposed(row, col + 1));
		}
	}

	/**
	 * Returns whether the given cell is open
	 *
	 * @param row
	 *            the row index
	 * @param col
	 *            the column index
	 * @return true if cell is open, false otherwise
	 */
	public boolean isOpen(int row, int col) {
		int index = this.rowColTransposed(row, col);
		return this.sites[index];
	}

	/**
	 * Returns whether the given cell is full (has a path to top row)
	 *
	 * @param row
	 *            the row index
	 * @param col
	 *            the column index
	 * @return true if cell is full, false otherwise
	 */
	public boolean isFull(int row, int col) {
		int index = this.rowColTransposed(row, col);
		return this.connections.connected(index, this.top);
	}

	/**
	 * Returns the number of open sites
	 *
	 * @return the number of open sites
	 */
	public int numberOfOpenSites() {
		return this.openedSites;
	}

	/**
	 * Returns whether the structure percolates or not
	 *
	 * @return true if is percolates, false otherwise
	 */
	public boolean percolates() {
		return this.connections.connected(this.top, this.bottom);
	}

	/**
	 * Simple program for testing
	 *
	 * @param args
	 *            - none used
	 */
	public static void main(String[] args) {
		Percolation percolation = new Percolation(3);
		StdOut.print(percolation.percolates());
		StdOut.println();

		// open up the top left square
		percolation.open(1, 1);
		StdOut.print(percolation.percolates());
		StdOut.println();

		// open the center square
		percolation.open(2, 2);
		StdOut.print(percolation.percolates());
		StdOut.println();

		// open bottom right square
		percolation.open(3, 3);
		StdOut.print(percolation.percolates());
		StdOut.println();

		// open another square
		percolation.open(1, 2);
		StdOut.print(percolation.percolates());
		StdOut.println();

		// should now perculate
		percolation.open(2, 3);
		StdOut.print(percolation.percolates());
		StdOut.println();
	}
}