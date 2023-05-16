package benchmark;

import extras.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.regex.Pattern;

public class BenchmarkUtilities {
    static final int BENCHMARK_TIMES = 3;

    public static Result benchmarkAllFiles(ArrayList<String> files) {
        Result res = new Result();
        for (String file : files) {
            String[] splittedFileName = file.split(Pattern.quote(File.separator));
            String filename = splittedFileName[splittedFileName.length - 1];
            res.addResult(filename, benchmarkSingleFile(file));
        }
        return res;
    }

    private static long[] benchmarkSingleFile(String fileLocation) {
        System.out.println("Benchmarking: " + fileLocation);
        TestInput input = parseFile(fileLocation);
        long[] results = new long[BENCHMARK_TIMES];
        for (int i = 0; i < BENCHMARK_TIMES; i++) {
            input.reset();
            long temp = benchmarkOnce(input);
            results[i] = temp;
        }
        return results;
    }

    private static long benchmarkOnce(TestInput input) {
        long timeCounter = System.currentTimeMillis();
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        while (input.next()) {
            Operation op = input.getCurrentOperationType();
            if (op == Operation.INSERT) {
                pq.add(input.getCurrentValue());
            } else if (op == Operation.REMOVE) {
                pq.remove(input.getCurrentValue());
            } else if (op == Operation.POLL) {
                pq.poll();
            }
        }
        timeCounter = System.currentTimeMillis() - timeCounter;
        return timeCounter;
    }

    private static TestInput parseFile(String fileLocation) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileLocation));
            int lines = 0;
            while (br.readLine() != null) lines++;
            br.close();
            br = new BufferedReader(new FileReader(fileLocation));

            Operation[] opType = new Operation[lines];
            int[] value = new int[lines];
            for (int i = 0; i < lines; i++) {
                String line = br.readLine();
                String[] args = line.split(" ");
                if (args[0].equals("i")) {
                    opType[i] = Operation.INSERT;
                    value[i] = Integer.parseInt(args[1]);
                } else if (args[0].equals("r")) {
                    opType[i] = Operation.REMOVE;
                    value[i] = Integer.parseInt(args[1]);
                } else if (args[0].equals("p")) {
                    opType[i] = Operation.POLL;
                } else throw new RuntimeException("Invalid operation: " + line);
            }
            br.close();
            return new TestInput(lines, opType, value);
        } catch (IOException e) {
            return null;
        }
    }
}
