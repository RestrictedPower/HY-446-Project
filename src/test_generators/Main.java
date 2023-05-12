package test_generators;

import java.util.ArrayList;
import extras.Utils;

public class Main {
    public static ArrayList<GenericGenerator> getAllGenerators() {
        ArrayList<GenericGenerator> generators = new ArrayList<>();
//        generators.add(new ReverseRemovalGenerator("ReverseRemoval"));
        generators.add(new Insert5Remove1Generator("Insert5Remove1"));
        return generators;
    }



    public static void main(String[] args) {
        int[] dataSizes = new int[] {100, 1_000, 100_000, 1_000_000, 10_000_000};
        dataSizes = new int[] {1_000_00};
        for(int dataSize : dataSizes){
            for (GenericGenerator gen : getAllGenerators()) {
                gen.setDataSize(dataSize);
                String outfileName = gen.getGeneratorName() + "_" + dataSize;
                Utils.writeListOnFile(gen.getInstructionList(), "GeneratedTests", outfileName);
            }
        }
    }
}

