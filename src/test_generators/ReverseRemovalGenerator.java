package test_generators;

import java.util.ArrayList;

public class ReverseRemovalGenerator extends GenericGenerator {

    public ReverseRemovalGenerator(String generatorName) {
        super(generatorName);
    }

    public void generateInstructionList() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 1; i <= getDataSize(); i++) {
            generateInsertInstruction(i);
        }
        for (int i = getDataSize(); i >= 1; i--) {
            generateRemoveInstruction(i);
        }
    }
}
