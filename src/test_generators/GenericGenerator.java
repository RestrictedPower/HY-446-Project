package test_generators;

import java.util.ArrayList;

public abstract class GenericGenerator {
    int dataSize;
    String generatorName;
    private ArrayList<String> instructionList;

    public GenericGenerator(String generatorName) {
        instructionList = new ArrayList<>();
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

    public void generateInsertInstruction(int value) {
        instructionList.add("i " + value);
    }

    public void generateRemoveInstruction(int value) {
        instructionList.add("r " + value);
    }

    public void generatePollInstruction() {
        instructionList.add("p");
    }

    public ArrayList<String> getInstructionList() {
        return instructionList;
    }

    public abstract void generateInstructionList();
}
