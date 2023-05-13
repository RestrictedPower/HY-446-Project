package test_generators;

import java.util.ArrayList;

public abstract class GenericGenerator {

    int dataSize;
    String generatorName;

    public GenericGenerator(String generatorName) {
        setGeneratorName(generatorName);
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setGeneratorName(String generatorName) {
        this.generatorName = generatorName;
    }

    public String getGeneratorName() {
        return generatorName;
    }

    public String generateInsertInstruction(int value) {
        return "i " + value;
    }

    public String generateRemoveInstruction(int value) {
        return "r " + value;
    }

    public String generatePollInstruction() {
        return "p";
    }

    public abstract ArrayList<String> getInstructionList();
}
