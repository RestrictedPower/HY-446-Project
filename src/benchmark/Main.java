package benchmark;

import extras.Utils;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> files = Utils.getAllFilesInDirectory("GeneratedTests");
        Result res = BenchmarkUtilities.benchmarkAllFiles(files);
        res.exportResultsToFile("results.csv");
        Utils.dbg(files);
//        double res = BenchmarkUtilities.benchmarkFile("GeneratedTests/Insert5Remove1_100000");
//        System.out.println("Result: " + res);
    }
}

