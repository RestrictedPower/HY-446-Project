package test_generators;

import extras.Utils;

import java.util.ArrayList;

public class Insert4Remove1Generator extends GenericGenerator {

    public Insert4Remove1Generator(String generatorName) {
        super(generatorName);
    }

    public ArrayList<String> generateInstructionList() {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Integer> currentValues = new ArrayList<>();
        int operations = (getDataSize() + 4) / 5;
        for (int i = 0; i < operations; i++) {
            for (int j = 0; j < 4; j++) {
                int insertValue = Utils.randomInt();
                currentValues.add(insertValue);
                generateInsertInstruction(insertValue);
            }
            int removeIndex = Utils.randomIntInRange(0, currentValues.size() - 1);
            int removeValue = currentValues.get(removeIndex);
            currentValues.set(removeIndex, currentValues.get(currentValues.size() - 1));
            generateRemoveInstruction(removeValue);
            currentValues.remove(currentValues.size() - 1);
        }
        return getInstructionLists();
    }
}
