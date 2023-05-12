import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static ArrayList<GenericGenerator> getAllGenerators(){
        ArrayList<GenericGenerator> generators = new ArrayList<>();
        generators.add(new ReverseGenerator());
        return generators;
    }

    public static void writeListOnFile(ArrayList<String> lines, String directory, String filename){
        try {
            new File(directory).mkdirs();
            FileWriter fw = new FileWriter(directory + File.separator + filename);
            for(String i : lines){
                fw.write(i);
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        for(GenericGenerator gen : getAllGenerators()) {
            gen.setDataSize(15);
            writeListOnFile(gen.getInstructionList(), "Tests", "test.out");
        }
    }
}

