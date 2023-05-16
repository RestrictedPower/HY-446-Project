package benchmark;

import extras.Utils;

import java.util.ArrayList;

public class Result {
    private ArrayList<String> testNames;
    private ArrayList<long[]> executionTimes;

    public Result() {
        this.testNames = new ArrayList<>();
        this.executionTimes = new ArrayList<>();
    }

    public void addResult(String testName, long[] execTimes) {
        testNames.add(testName);
        executionTimes.add(execTimes);
    }

    public void exportResultsToFile(String filename) {
        ArrayList<String> merged = new ArrayList<>();
        for (int i = 0; i < testNames.size(); i++) {
            StringBuilder times = new StringBuilder();
            for(long singleTime : executionTimes.get(i)) times.append(";"+singleTime);
            merged.add(testNames.get(i) + times.toString());
        }
        Utils.writeListOnFile(merged, "Results", filename);
    }
}
