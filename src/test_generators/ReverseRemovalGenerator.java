import java.util.ArrayList;

public class ReverseRemovalGenerator extends GenericGenerator {

    public ReverseRemovalGenerator() {
        super();
    }

    public ReverseRemovalGenerator(int dataSize) {
        super(dataSize);
    }

    public ArrayList<String> getInstructionList() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 1; i <= getDataSize(); i++) {
            result.add(generateInsertInstruction(i));
        }
        for (int i = getDataSize(); i >= 1; i--) {
            result.add(generateRemoveInstruction(i));
        }
        return result;
    }
}
