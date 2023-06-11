package stresstest;

import extras.Utils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    /**
     * This is a stress test function. It performs @iterations tests that consist of @instructionAmount operations.
     *
     * On each operation we check the state and results of our PriorityQueue implementation against the IntegerArrayListPQ,
     * which is a simple slow iterative PQ implementation using an ArrayList.
     *
     * The test performs random insert and remove operations. A remove operation is performed with a probability of 1/@removeFactor.
     */
    public static boolean test(int instructionAmount, int iterations, int removeFactor, int insertLeftBound, int insertRightBound){
        for(int iteration = 0; iteration < iterations; iteration++){
            implementation.PriorityQueue<Integer> ourPQ = new implementation.PriorityQueue<Integer>();
            IntegerArrayListPQ pq = new IntegerArrayListPQ();
            ArrayList<Integer> inserted = new ArrayList<>();
            for(int i = 0; i<instructionAmount; i++){
                boolean remove = Utils.randomIntInRange(1, removeFactor) == 1;
                if(remove){
                    if(!inserted.isEmpty()){
                        int toRem = inserted.remove(Utils.randomIntInRange(0, inserted.size()-1));
                        if(ourPQ.remove(toRem) != pq.remove(toRem)) { System.out.println("Fail: Point - 1"); return false; }
                    }
                }else{
                    int x = Utils.randomIntInRange(insertLeftBound, insertRightBound);
                    ourPQ.add(x);
                    pq.add(x);
                    inserted.add(x);
                }
                if(ourPQ.isEmpty() != pq.isEmpty()) { System.out.println("Fail: Point - 2"); return false; }
                if(ourPQ.size() != pq.size()) { System.out.println("Fail: Point - 3"); return false; }
                if(!ourPQ.isEmpty() && !ourPQ.peek().equals(pq.peek())) { System.out.println("Fail: Point - 4"); return false; }
            }
            while(!pq.isEmpty()){
                if(ourPQ.isEmpty() != pq.isEmpty()) { System.out.println("Fail: Point - 5"); return false; }
                if(ourPQ.size() != pq.size()) { System.out.println("Fail: Point - 6"); return false; }
                if(!ourPQ.isEmpty() && !ourPQ.peek().equals(pq.peek())) { System.out.println("Fail: Point - 7"); return false; }
                if(!ourPQ.poll().equals(pq.poll())) { System.out.println("Fail: Point - 8"); return false; }
            }
            if(!pq.isEmpty() || !ourPQ.isEmpty()) { System.out.println("Fail: Point - 9"); return false; }
        }
        return true;
    }

    public static void main(String[] args){
        // Small amount of instructions and many iterations.
        int testIdx = 1;

        // Small tests with multiple iterations and a small amount of duplicate insertions
        for(int removeFactor = 2; removeFactor<=5; removeFactor++)
            System.out.println(test(50, 100000, removeFactor, (int) -1e9, (int) 1e9) ? ("Test "+(testIdx++)+" ok!") : ("Test "+(testIdx++)+" failed!"));

        // Small tests with multiple iterations and a large amount of duplicate insertions
        for(int removeFactor = 2; removeFactor<=5; removeFactor++)
            System.out.println(test(50, 100000, removeFactor, -10,10) ? ("Test "+(testIdx++)+" ok!") : ("Test "+(testIdx++)+" failed!"));

        // Large tests with few iterations and a small amount of duplicate insertions
        for(int removeFactor = 2; removeFactor<=5; removeFactor++)
            System.out.println(test(5000, 50, removeFactor, (int) -1e9, (int) 1e9) ? ("Test "+(testIdx++)+" ok!") : ("Test "+(testIdx++)+" failed!"));


        // Large tests with few iterations and a large amount of duplicate insertions
        for(int removeFactor = 2; removeFactor<=5; removeFactor++)
            System.out.println(test(5000, 50, removeFactor, -10,10) ? ("Test "+(testIdx++)+" ok!") : ("Test "+(testIdx++)+" failed!"));
    }
}
