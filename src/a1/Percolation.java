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
    private boolean[]sites;
    private final int n;
    private final int top;
    private final int bottom;
    private int openedSites;
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

    private int rowColTransposed(int row, int col) {
        if (row <= 0 || row > this.n || col <= 0  || col > this.n) {
            throw new IllegalArgumentException();
        }
        int index = (col) + this.n * (row - 1);
        return index;
    }

    private void connect(int index1, int index2) {
        if (this.sites[index1] && this.sites[index2]) {
            this.connections.union(index1, index2);
        }
    }

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
        if (col > 1) {
            this.connect(index, this.rowColTransposed(row, col - 1));
        }
        if (col < this.n) {
            this.connect(index, this.rowColTransposed(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        int index = this.rowColTransposed(row, col);
        return this.sites[index];
    }

    public boolean isFull(int row, int col) {
        int index = this.rowColTransposed(row, col);
        return this.connections.connected(index, this.top);
    }

    public int numberOfOpenSites() {
        return this.openedSites;
    }

    public boolean percolates() {
        return this.connections.connected(this.top, this.bottom);
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        StdOut.print(percolation.percolates());
        StdOut.println();
        percolation.open(1, 1);
        StdOut.print(percolation.percolates());
        StdOut.println();
        percolation.open(2, 2);
        StdOut.print(percolation.percolates());
        StdOut.println();
        percolation.open(3, 3);
        StdOut.print(percolation.percolates());
        StdOut.println();
        percolation.open(1, 2);
        StdOut.print(percolation.percolates());
        StdOut.println();
        percolation.open(2, 3);
        StdOut.print(percolation.percolates());
        StdOut.println();
    }
}