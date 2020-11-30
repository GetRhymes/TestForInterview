package com.tsystems.javaschool.tasks.calculator;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
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

    public Double calculate() {
        List<Double> nums = new LinkedList<>();
        List<Character> signs = new LinkedList<>();
        int i = 0;
        int childCtr = 0;
        boolean prevWasSign = false;
        while (i < this.expression.length()) {
            StringBuilder num = new StringBuilder();
            if (this.expression.charAt(i) >= '0' && this.expression.charAt(i) <= '9' || this.expression.charAt(i) == '.') {
                while (i < this.expression.length() && (this.expression.charAt(i) >= '0' && this.expression.charAt(i) <= '9' || this.expression.charAt(i) == '.')) {
                    num.append(this.expression.charAt(i));
                    i++;
                    prevWasSign = false;
                }
                nums.add(Double.parseDouble(num.toString())); //add num to nums list
            }
            if (i >= this.expression.length()) {
                break;
            }
            if (this.expression.charAt(i) == '+' || this.expression.charAt(i) == '/' ||
                    this.expression.charAt(i) == '-' || this.expression.charAt(i) == '*') {
                if ((prevWasSign || this.expression.length() - 1 == i) && child.size() > 0) {
                    Double d = child.get(childCtr).calculate();
                    nums.add(d);
                    childCtr++;
                } //if this sign is more then second, should invoke calculate in child number N
                prevWasSign = true;
                signs.add(this.expression.charAt(i));
                i++;
            }
        } //parse to calculate
        if (signs.contains('*') || signs.contains('/')) {
            while (signs.size() != 0 && (signs.contains('*') || signs.contains('/'))) {
                Pair<Character, Integer> pair = findHighPriorityOperation(signs);
                Character sign = pair.getKey();
                int index = pair.getValue();
                if (sign == '*' || sign == '/') {
                    if (sign == '*') {
                        nums.set(index, nums.get(index) * nums.get(index + 1));
                    }
                    if (sign == '/') {
                        if (nums.get(index + 1) == 0) {
                            throw new ArithmeticException("Dividing by zero");
                        }
                        nums.set(index, nums.get(index) / nums.get(index + 1));
                    }
                    signs.remove(sign);
                    if (nums.size() > 0)
                        nums.remove(index + 1);
                }
            }
        }
        if (signs.contains('+') || signs.contains('-')) {
            while (signs.size() != 0) {
                Pair<Character, Integer> pair = findOperation(signs);
                Character sign = pair.getKey();
                int index = pair.getValue();
                if (sign == '+' || sign == '-') {
                    if (sign == '+') {
                        nums.set(index, nums.get(index) + nums.get(index + 1));
                    }
                    if (sign == '-') {
                        nums.set(index, nums.get(index) - nums.get(index + 1));
                    }
                    signs.remove(sign);
                    if (nums.size() > 0)
                        nums.remove(index + 1);
                }
            }
        }
        return nums.get(0);
    }

    private Pair<Character, Integer> findHighPriorityOperation(List<Character> signs) {
        for (Character sign : signs) {
            if (sign == '*' || sign == '/') {
                return new Pair<>(sign, signs.indexOf(sign));
            }
        }
        throw new IllegalArgumentException();
    }

    private Pair<Character, Integer> findOperation(List<Character> signs) {
        for (Character sign : signs) {
            if (sign == '+' || sign == '-') {
                return new Pair<>(sign, signs.indexOf(sign));
            }
        }
        throw new IllegalArgumentException();
    }

}

