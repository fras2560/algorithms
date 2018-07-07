package a2;


import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public Permutation() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("No argument given");
        } else {
            int k = Integer.parseInt(args[0]);
            RandomizedQueue<String> queue = new RandomizedQueue<String>();
            while (!StdIn.isEmpty()) {
                queue.enqueue(StdIn.readString());
            }   
            int i = 0;
            for (String value : queue) {
                if (i == k) {
                    break;
                }
                i++;
                System.out.println(value);
            }
        }
    }
}
