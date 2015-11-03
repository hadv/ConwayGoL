package com.company.demo.combination;

import java.util.Arrays;

public class Permutation {
    public static void main(String[] args) {
        StringPermutation sp = new StringPermutation("12345");

        int i = 1;
        System.out.println(i + "\t" + sp.toString());
        while (sp.hashNext()) {
            System.out.println(++i + "\t" + sp.next());
        }
    }
}

/**
 *
 */
class StringPermutation {
    private String[] permutationArray;

    public StringPermutation(String str) {
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException();
        }
        int len = str.length();
        permutationArray = new String[len];
        for (int i = 0; i < len; i++) {
            permutationArray[i] = str.substring(i, i+1);
        }
        Arrays.sort(permutationArray);
    }

    public boolean hashNext() {
        int len = permutationArray.length;

        for (int i = len - 1; i > 0; i--) {
            if (permutationArray[i].compareTo(permutationArray[i-1]) > 0) {
                return true;
            }
        }
        return false;
    }

    public String next() {
        if (!hashNext()) {
            return null;
        }

        int len = permutationArray.length;
        int idx = -1;
        for (int i = len - 1; i > 0; i--) {
            if (permutationArray[i].compareTo(permutationArray[i-1]) > 0) {
                idx = i;
                break;
            }
        }

        Arrays.sort(permutationArray, idx, len);

        for (int i = idx; i < len; i++) {
            if (permutationArray[i].compareTo(permutationArray[idx-1]) > 0) {
                String tmp = permutationArray[i];
                permutationArray[i] = permutationArray[idx - 1];
                permutationArray[idx - 1] = tmp;
                break;
            }
        }
        return toString();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < permutationArray.length; i++) {
            sb.append(permutationArray[i]);
        }
        return sb.toString();
    }
}
