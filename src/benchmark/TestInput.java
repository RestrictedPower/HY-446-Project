package benchmark;

public class TestInput {

    int size;
    int iteratorIdx;
    private Operation[] operationType;
    private int[] values;

    public TestInput(int size, Operation[] operationType, int[] values) {
        this.operationType = operationType;
        this.values = values;
        this.size = size;
        reset();
    }

    public void reset() {
        iteratorIdx = -1;
    }

    public boolean next() {
        iteratorIdx++;
        return iteratorIdx < size;
    }

    public Operation getCurrentOperationType() {
        return operationType[iteratorIdx];
    }

    public int getCurrentValue() {
        return values[iteratorIdx];
    }
}
