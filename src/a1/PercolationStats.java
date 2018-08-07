/******************************************************************************
 *  Dependencies: algs4
 *  Author: Dallas Fraser
 *  A program to test various statistics about percolation
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package a1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * This class runs an experiment to determine p* for percolation
 * @author Dallas Fraser
 * @since 2018-06-06
 */
public class PercolationStats {
	private final double[] results;
	private final int trials;
	private static final double CONFIDENCE_95 = 1.96;

	/**
	 * The constructors that does x number of <i>trials</i> on a board size <i>n</i>
	 *
	 * @param n
	 *            the size of the board
	 * @param trials
	 *            the number of trials
	 */
	public PercolationStats(int n, int trials) {
		if (trials <= 0 || n <= 0) {
			throw new IllegalArgumentException();
		}

		// run the number of trials
		this.results = new double[trials];
		for (int trial = 0; trial < trials; trial++) {
			this.results[trial] = this.runTrial(n);
		}
		this.trials = trials;
	}

	/**
	 * Performs on trial and Returns the number of open sites needed until it
	 * percolates
	 *
	 * @param n
	 *            the size of the structure
	 * @return the number of open sites are a percentage
	 */
	private double runTrial(int n) {
		Percolation percolation = new Percolation(n);
		while (!percolation.percolates()) {
			percolation.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
		}
		return ((double) percolation.numberOfOpenSites() / (double) (n*n));
	}

	/**
	 * Returns the mean of the results
	 *
	 * @return the mean
	 */
	public double mean() {
		return StdStats.mean(this.results);
	}

	/**
	 * Returns the standard deviation from the mean for the results
	 *
	 * @return the standard deviation
	 */
	public double stddev() {
		return StdStats.stddev(this.results);
	}

	/**
	 * Returns the lower end of the confidence interval
	 *
	 * @return lower end of the confidence interval
	 */
	public double confidenceLo() {
		return this.mean() - ((PercolationStats.CONFIDENCE_95 * this.stddev()) / this.trials);
	}

	/**
	 * Returns the upper end of the confidence interval
	 *
	 * @return upper end of the confidence interval
	 */
	public double confidenceHi() {
		return this.mean() + ((PercolationStats.CONFIDENCE_95 * this.stddev()) / this.trials);
	}

	/**
	 * Ouputs the mean, standard deviation and the 95% confidence interval
	 *
	 * @param args
	 *            (size, trials) the size of the structure, the number of trials to
	 *            run
	 */
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