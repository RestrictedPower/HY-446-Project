package benchmark;

import extras.Utils;

import java.util.ArrayList;

public class Result {
    private ArrayList<String> testNames;
    private ArrayList<Long> executionTimes;

    public Result() {
        this.testNames = new ArrayList<>();
        this.executionTimes = new ArrayList<>();
    }

    public void addResult(String testName, long execTime) {
        testNames.add(testName);
        executionTimes.add(execTime);
    }

    public void exportResultsToFile(String filename) {
        ArrayList<String> merged = new ArrayList<>();
        for (int i = 0; i < testNames.size(); i++) {
            merged.add(testNames.get(i) + ";" + executionTimes.get(i));
        }
        Utils.writeListOnFile(merged, "Results", filename);
    }
}
