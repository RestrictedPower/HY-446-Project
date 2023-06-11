package stresstest;

import java.util.ArrayList;

public class IntegerArrayListPQ {
    public ArrayList<Integer> arr;

    public IntegerArrayListPQ() {
        arr = new ArrayList<Integer>();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return arr.size();
    }

    public void add(Integer i) {
        arr.add(i);
    }

    public Integer poll() {
        if (arr.isEmpty()) throw new RuntimeException("empty!");
        int min = 0;
        for (int i = 0; i < arr.size(); i++) if (arr.get(i) < arr.get(min)) min = i;
        return arr.remove(min);
    }

    public Integer peek() {
        if (arr.isEmpty()) throw new RuntimeException("empty!");
        int min = 0;
        for (int i = 0; i < arr.size(); i++) if (arr.get(i) < arr.get(min)) min = i;
        return arr.get(min);
    }

    public boolean remove(Integer v) {
        int idx = -1;
        for (int i = 0; i < arr.size(); i++) if (arr.get(i).equals(v)) idx = i;
        if (idx == -1) return false;
        arr.remove(idx);
        return true;
    }
}
