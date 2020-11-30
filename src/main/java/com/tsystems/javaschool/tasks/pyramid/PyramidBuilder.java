package com.tsystems.javaschool.tasks.pyramid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */

    public int[][] buildPyramid(List<Integer> inputNumbers) {
        if (inputNumbers.contains(null) || inputNumbers.size() == 0) throw new CannotBuildPyramidException();
        else {
            int sizePyramid = countOfLine(inputNumbers.size());
            if (sizePyramid == 0) throw new CannotBuildPyramidException();
            else {
                Collections.sort(inputNumbers);
                int[][] result = new int[sizePyramid][0];
                for (int i = 1; i <= sizePyramid; i++)
                    result[i - 1] = createLineForPyramid(sizePyramid, i, needsElements(inputNumbers, i));
                return result;
            }
        }
    }

    private List<Integer> needsElements(List<Integer> inputNumbers, int currentLine) {
        List<Integer> result = new ArrayList<>();
        int startIndex = 0;
        for (int count = 0; count < currentLine; count++) startIndex += count;
        for (int i = startIndex; i < startIndex + currentLine; i++) result.add(inputNumbers.get(i));
        return result;
    }
    private int[] createLineForPyramid(int lines, int numberCurrentLine, List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();
        if (lines == numbers.size()) addMainNumbers(numbers, result);
        else {
            addTrailingZeros(lines - numberCurrentLine, result);
            addMainNumbers(numbers, result);
            addTrailingZeros(lines - numberCurrentLine, result);
        }
        return toIntArray(result);
    }

    private void addTrailingZeros(int number, List<Integer> currentList) {
        for (int i = 0; i < number; i++) currentList.add(0);
    }

    private void addMainNumbers(List<Integer> numbers, List<Integer> result) {
        for (int i = 0; i < numbers.size(); i++)
            if (i != numbers.size() - 1) {
                result.add(numbers.get(i));
                result.add(0);
            } else result.add(numbers.get(i));
    }

    private int countOfLine(int size) {
        if (size > 0) {
            int result = 1;
            while(true) {
                result++;
                int count = 0;
                for (int i = 1; i <= result; i++)  count += i;
                if (size - count > 0) continue;
                else if (size - count == 0) return result;
                else throw new CannotBuildPyramidException();
            }
        } else throw new CannotBuildPyramidException();
    }

    private int[] toIntArray(List<Integer> list) {
        int[] ret = new int[list.size()];
        for(int i = 0;i < ret.length;i++) ret[i] = list.get(i);
        return ret;
    }
}
