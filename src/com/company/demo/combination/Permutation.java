package com.company.demo.combination;

import java.util.ArrayList;
import java.util.List;

/**
 * The demo permutation string program. Given an input string
 * then print all the k-length permutation string f
 */
public class Permutation {

    /**
     * The end-point the demo program.
     * @param args input parameter of the program.
     */
    public static void main(String[] args) {
        System.out.println("----------- f1 ------------");
        List<String> output = f1("abc");
        for (String s: output) {
            System.out.println(s);
        }

        System.out.println("----------- f2 ------------");
        output = f2("abc");
        for (String s: output) {
            System.out.println(s);
        }
    }

    /**
     * Calculate all the k-length permutation of given string, 1 <= k <= n.
     *
     * @param s the given input string
     * @return list of all k-length permutation
     */
    public static List<String> f1(String s) {
        List<String> output = new ArrayList<String>();
        int len = s.length();
        for (int i = 1; i <= len; i++) {
            StringPermutation sp = new StringPermutation(i, s);
            sp.combination(0);
            output.addAll(sp.getOutputList());
        }
        return output;
    }

    /**
     * Calculate the n-length of permutation of given string.
     *
     * @param s the given input string
     * @return list of all n-length permutation
     */
    public static List<String> f2(String s) {
        int len = s.length();
        StringPermutation sp = new StringPermutation(len, s);
        sp.combination(0);
        return sp.getOutputList();
    }
}

/**
 * Calculate all the permutation string with k-length from input n-length string, <tt>1 <= k <= n<tt/>.
 * Then return a list of k-length permutation string.
 */
class StringPermutation {

    // Character array to present the input string
    private char[] input;

    // The boolean array to store the used flag of an item
    private boolean[] used;

    // The current permutation output from the input string
    private char[] output;

    // Output k-length
    private int k;

    // Output result all of the k-length permutation of input string
    private List<String> outputList;

    /**
     * Construct to create an instance of {@code StringPermutation} class
     * to calculate all the k-length permuration of given input string {@code str}
     *
     * @param k the input length of the permutation
     * @param str the given input string
     */
    public StringPermutation(int k, String str) {
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException();
        }
        int len = str.length();
        input = new char[len];
        used = new boolean[len];
        for (int i = 0; i < len; i++) {
            input[i] = str.charAt(i);
            used[i] = false;
        }

        this.k = k;
        output = new char[k];
        outputList = new ArrayList<String>();
    }

    /**
     * A back-tracking method to calculate all the k-length permutation of input n-length string.
     *
     * @param i the i-th item in the k-length
     */
    public void combination(int i) {
        int len = input.length;
        for (int j = 0; j < len; j++) {
            if (!used[j]) {
                output[i] = input[j];
                if (i == k - 1) {
                    outputList.add(new String(output));
                } else {
                    used[j] = true;
                    combination(i + 1);
                    used[j] = false;
                }
            }
        }
    }

    /**
     * Return the list of k-length permutation of input string.
     *
     * @return the output result of k-length permutation string.
     */
    public List<String> getOutputList() {
        return outputList;
    }
}
