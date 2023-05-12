package extras;

import java.io.*;
import java.util.*;

public class Utils {
    private static Random random = new Random();

    public static int randomIntInRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static int randomInt() {
        return random.nextInt();
    }

    public static void writeListOnFile(ArrayList<String> lines, String directory, String filename) {
        try {
            new File(directory).mkdirs();
            FileWriter fw = new FileWriter(directory + File.separator + filename);
            for (String i : lines) {
                fw.write(i);
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

