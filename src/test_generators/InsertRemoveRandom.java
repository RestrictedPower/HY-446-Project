package test_generators;

import extras.Utils;

import java.util.ArrayList;

public class InsertRemoveRandom extends GenericGenerator {

    // The chance to make a removal is (1/probability)
    private int probability;

    public InsertRemoveRandom(String generatorName, int probability) {
        super(generatorName);
        this.probability = probability;
    }

    public ArrayList<String> generateInstructionList() {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Integer> currentValues = new ArrayList<>();
        int operations = getDataSize();
        for (int i = 0; i < operations; i++) {
            int rng = Utils.randomIntInRange(1, probability);
            if (rng == 1 && !currentValues.isEmpty()) { // Remove
                int removeIndex = Utils.randomIntInRange(0, currentValues.size() - 1);
                int removeValue = currentValues.get(removeIndex);
                currentValues.set(removeIndex, currentValues.get(currentValues.size() - 1));
                generateRemoveInstruction(removeValue);
                currentValues.remove(currentValues.size() - 1);
            } else {
                int insertValue = Utils.randomInt();
                currentValues.add(insertValue);
                generateInsertInstruction(insertValue);
            }
        }
        return getInstructionLists();
    }
}
