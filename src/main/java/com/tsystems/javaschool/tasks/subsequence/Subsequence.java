package com.tsystems.javaschool.tasks.subsequence;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        if (x == null || y == null) throw new IllegalArgumentException();
        else {
            if (y.size() < x.size()) return false;
            List<Pair<Integer, Integer>> index = new ArrayList<>();
            for (int y_i = 0; y_i < y.size(); y_i++) {
                for(int x_i = 0; x_i < x.size(); x_i++) {
                    if (x.get(x_i) == y.get(y_i)) index.add(new Pair(y_i, x_i));
                }
            }
            boolean result = true;
            int count = 1;
            while (count < index.size()) {
                if (index.get(count - 1).getKey() <= index.get(count).getKey() &&
                        index.get(count - 1).getValue() <= index.get(count).getValue()) {
                    result = true;
                    count++;
                }
                else {
                    result = false;
                    break;
                }
            }
            return result;
        }
    }
}
