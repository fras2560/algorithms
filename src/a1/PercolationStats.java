package a1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

/**
 * This class runs an experiment to determine p* for percolation
 * @author Dallas Fraser
 * @since 2018-06-06
 */
public class PercolationStats {
    private final double[] results;
    private final int trials;
    private final static double CONFIDENCE_95 = 1.96;
    public PercolationStats(int n, int trials) {
        if (trials <= 0 || n <= 0) {
            throw new IllegalArgumentException();
        }
        this.results = new double[trials];
        for (int trial = 0; trial < trials; trial++) {
            this.results[trial] = this.runTrial(n);
        }
        this.trials = trials;
    }

    private double runTrial(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            percolation.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
        }
        return ((double) percolation.numberOfOpenSites() / (double) (n*n));
    }

    public double mean() {
        return StdStats.mean(this.results);
    }

    public double stddev() {
        return StdStats.stddev(this.results);
    }

    public double confidenceLo() {
        return this.mean() - ((PercolationStats.CONFIDENCE_95 * this.stddev()) / this.trials);
    }

    public double confidenceHi() {
        return this.mean() + ((PercolationStats.CONFIDENCE_95 * this.stddev()) / this.trials);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("Not enough command line arguments: java-algs4 PercolationStats n T");
        } else {
            PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            StdOut.println("mean                    =" + stats.mean());
            StdOut.println("stddev                  =" + stats.stddev());
            StdOut.println("95% confidence interval =" + "[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
        }
    }
}