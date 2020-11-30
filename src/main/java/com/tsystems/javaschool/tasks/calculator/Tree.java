package com.tsystems.javaschool.tasks.calculator;

import com.sun.xml.internal.fastinfoset.util.CharArray;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    Node root;

    public Tree() {
        this.root = new Node(null);
    }

}

class Node {
    StringBuilder expression;
    Node parent;
    List<Node> child;

    public Node(Node parent) {
        this.parent = parent;
        this.expression = new StringBuilder();
        this.child = new ArrayList<>();
    }

    public String toString() {
        System.out.println(expression);
        for (int i = 0; i < child.size(); i++) child.get(i).toString();
        return expression.toString();
    }

    public String calculate() {
        int counter = 0;
        char[] charArray = expression.toString().toCharArray();
        StringBuilder numberArray = new StringBuilder();
        StringBuilder signArray = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] >= '0' && charArray[i] <= '9') {
                numberArray.append(charArray[i]);
            }
            if (charArray[i] == '+' || charArray[i] == '-' || charArray[i] == '/' || charArray[i] == '*') {
                if (i < charArray.length - 2) {
                    if (charArray[i + 1] == '+' || charArray[i + 1] == '-' || charArray[i + 1] == '/' || charArray[i + 1] == '*') {
                        numberArray.append(child.get(counter).calculate());
                        counter++;
                    }
                }
                if (i == charArray.length - 1) {
                    numberArray.append(child.get(counter).calculate());
                    counter++;
                }
                signArray.append(charArray[i]);
            }
        }

        List<Integer> indexMultiplication = new ArrayList<>();
        List<Integer> indexDivision = new ArrayList<>();
        List<Integer> indexAddition = new ArrayList<>();
        List<Integer> indexSubtraction = new ArrayList<>();
        for (int i = 0; i < signArray.toString().toCharArray().length; i++) {
            if (charArray[i] == '*') indexMultiplication.add(i);
            if (charArray[i] == '/') indexDivision.add(i);
            if (charArray[i] == '+') indexAddition.add(i);
            if (charArray[i] == '-') indexSubtraction.add(i);
        }

        for (int i = 0; i < indexMultiplication.size(); i++) {
            int res = (int) numberArray.toString().toCharArray()[indexMultiplication[i]] * (int) numberArray.toString().toCharArray()[i + 1];
            numberArray.append(res);
            numberArray.replace(i, i, numberArray.substring(numberArray.length() - 1));
            numberArray.delete(i + 1, i + 2);
        }

    }
}

