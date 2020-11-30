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
        if (checkBrackets(statement)) {
            char[] charArray = statement.toCharArray();
            Tree newTree = new Tree();
            int count = 0;
            while (count < charArray.length) {
                if (charArray[count] == '(') {
                    count = addNewNode(newTree.root, count, charArray);
                } else {
                    newTree.root.expression.append(charArray[count]);
                    count++;
                }
            }
            System.out.println(newTree.root);
        }
        return "";
    }

    private int addNewNode(Node parent, int i, char[] charArray) {
        int count = i + 1;
        Node newNode = new Node(parent);
        parent.child.add(newNode);
        while (true) {
            //System.out.println(charArray[count]);
            if (charArray[count] == '(') count = addNewNode(newNode, count, charArray);
            if (charArray[count] == ')') {
                count++;
                break;
            }
            newNode.expression.append(charArray[count]);
            if (count == charArray.length) break;
            count++;
        }
        return count;
    }

    private boolean checkBrackets(String str) {
        int check = 0;
        for (int i = 0; i < str.length(); i++) {
            if (check < 0) return false;
            String symbol = str.substring(i, i + 1);
            if (symbol.equals("(")) { check++; continue; }
            if (symbol.equals(")")) check--;
        }
        if (check == 0)  return true;
        else return false;
    }

}
