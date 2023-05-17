package test_generators;

import extras.Utils;

import java.util.ArrayList;

public class Insert4Poll1Generator extends GenericGenerator {

    public Insert4Poll1Generator(String generatorName) {
        super(generatorName);
    }

    public ArrayList<String> generateInstructionList() {
        int operations = (getDataSize() + 4) / 5;
        for (int i = 0; i < operations; i++) {
            for (int j = 0; j < 4; j++) {
                int insertValue = Utils.randomInt();
                generateInsertInstruction(insertValue);
            }
            generatePollInstruction();
        }
        return getInstructionLists();
    }
}
