/******************************************************************************
 *  Compilation:  javac Permuation.java
 *  Execution:    java Deque
 *  Dependencies: algs4.jar
 *  Author: Dallas Fraser
 *  A client program to test a randomized queue
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package a2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * A client to test to the randomized queue
 *
 * @author dfraser
 *
 */
public class Permutation {

	/**
	 * Takes in a random input based on the standard in
	 *
	 * @param args
	 *            the number element to print
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			StdOut.println("No argument given");
		} else {
			int k = Integer.parseInt(args[0]);
			RandomizedQueue<String> queue = new RandomizedQueue<>();

			// read in input and add it to the queue
			while (!StdIn.isEmpty()) {
				queue.enqueue(StdIn.readString());
			}

			// output the values
			int i = 0;
			for (String value : queue) {
				if (i == k) {
					break;
				}
				i++;
				StdOut.println(value);
			}
		}
	}
}
