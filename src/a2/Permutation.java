package a2;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public Permutation() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("No argument given");
            System.exit(0);
        }
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        for(String entry: StdIn.readAllStrings()) {
            queue.enqueue(entry);
        }   
        int i = 0;
        for(String value : queue) {
            if(i == k) {
                break;
            }
            i++;
            System.out.println(value);
        }
    }
}
