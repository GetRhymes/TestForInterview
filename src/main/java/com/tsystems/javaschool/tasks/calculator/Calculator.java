package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.DoubleStream.of;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        if (statement == null || statement.isEmpty() || statement.contains(",")) {
            return null;
        }
        Tree newTree = new Tree();
        if (checkBrackets(statement)) {
            char[] charArray = statement.toCharArray();
            int count = 0;
            boolean wasSign = false;
            Node currentNode = newTree.root;
            while (count < charArray.length) {
                if (charArray[count] == '(') {
                    Node newNode = new Node(currentNode);
                    currentNode.child.add(newNode);
                    currentNode = newNode;
                    count++;
                } else if (charArray[count] == ')') {
                    currentNode = currentNode.parent;
                    count++;
                } else {
                    if (charArray[count] == '+' || charArray[count] == '-' || charArray[count] == '/' || charArray[count] == '*' || charArray[count] == '.') {
                        if (wasSign)
                            return null;
                        wasSign = true;
                    } else {
                        wasSign = false;
                    }
                    currentNode.expression.append(charArray[count]);
                    count++;
                }
            }
        } else {
            return null;
        }
        Double d;
        try {

            d = newTree.root.calculate();
        } catch (ArithmeticException ex) {
            return null;
        }
        if (Math.round(d) - d == 0) {
            return Long.toString(Math.round(d));
        } else
            return Double.toString(d);
    }

    private boolean checkBrackets(String str) {
        int check = 0;
        for (int i = 0; i < str.length(); i++) {
            if (check < 0) return false;
            String symbol = str.substring(i, i + 1);
            if (symbol.equals("(")) {
                check++;
                continue;
            }
            if (symbol.equals(")")) check--;
        }
        if (check == 0) return true;
        else return false;
    }

}
