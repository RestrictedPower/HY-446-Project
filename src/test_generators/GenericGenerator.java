import java.util.ArrayList;

public abstract class GenericGenerator {

    int dataSize;

    public GenericGenerator() {}

    public GenericGenerator(int dataSize) {
        this.dataSize = dataSize;
    }
    public void setDataSize(int dataSize){
        this.dataSize = dataSize;
    }

    public int getDataSize(){
        return dataSize;
    }

    public String generateInsertInstruction(int value){
        return "insert " + value;
    }

    public String generateRemoveInstruction(int value){
        return "remove " + value;
    }

    public String generatePollInstraction(){
        return "poll";
    }

    public abstract ArrayList<String> getInstructionList();
}
