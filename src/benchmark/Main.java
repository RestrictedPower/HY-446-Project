package benchmark;

import java.io.*;
import java.util.PriorityQueue;

public class Main {

    public static long benchmarkFile(String fileLocation) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileLocation));
            int lines = 0;
            while (br.readLine() != null) lines++;
            br.close();
            br = new BufferedReader(new FileReader(fileLocation));
            int[] opType = new int[lines];
            int[] value = new int[lines];
            for(int i = 0; i<lines; i++){
                String line = br.readLine();
                String[] args = line.split(" ");
                if(args[0].equals("i")){
                    opType[i] = 0;
                    value[i] = Integer.parseInt(args[1]);
                }else if(args[0].equals("r")){
                    opType[i] = 1;
                    value[i] = Integer.parseInt(args[1]);
                }else if(args[0].equals("p")){
                    opType[i] = 2;
                    value[i] = Integer.parseInt(args[1]);
                }else throw new RuntimeException("Invalid operation: "+line);
            }
            long timeCounter = System.nanoTime();
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            for(int i = 0; i<lines; i++){
                if(opType[i] == 0) {
                    pq.add(value[i]);
                }
                else if(opType[i] == 1){
                    pq.remove(value[i]);
                }else if(opType[i] == 2){
                    pq.poll();
                }
            }
            timeCounter = System.nanoTime() - timeCounter;
            System.out.println("Total time: " + timeCounter);
            br.close();
            return timeCounter;
        } catch (IOException e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        System.out.println(benchmarkFile("GeneratedTests/Insert5Remove1_100000"));
    }
}

