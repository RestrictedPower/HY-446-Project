package test_generators;

import java.util.ArrayList;

import extras.Utils;

public class Main {

    public static ArrayList<GenericGenerator> getAllGenerators() {
        ArrayList<GenericGenerator> generators = new ArrayList<>();
        generators.add(new ReverseRemovalGenerator("ReverseRemoval"));
        generators.add(new Insert4Remove1Generator("Insert4Remove1"));
        generators.add(new InsertRemoveRandom("InsertRemoveRandomP3", 3));
        generators.add(new InsertRemoveRandom("InsertRemoveRandomP5", 5));
        return generators;
    }

    private static String sizeForFilename(int size) {
        if (size >= 1_000_000) return size / 1_000_000 + "m";
        if (size >= 1_000) return size / 1_000 + "k";
        return size + "";
    }

    public static void main(String[] args) {
        int[] dataSizes = new int[]{1_000, 100_000, 1_000_000};
        for (int dataSize : dataSizes) {
            for (GenericGenerator gen : getAllGenerators()) {
                gen.setDataSize(dataSize);
                String outfileName = gen.getGeneratorName() + "_" + sizeForFilename(dataSize);
                Utils.writeListOnFile(gen.getInstructionList(), "GeneratedTests", outfileName);
            }
        }
    }
}

