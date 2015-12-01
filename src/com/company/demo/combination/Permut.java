package com.company.demo.combination;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Permut {

    /**
     * Main entry point of the program.
     *
     * @param args input parameters of the program if any.
     */
    public static void main(String args[]) {
        SortedSet<Character> input = new TreeSet<Character>();
        input.add('1');
        input.add('3');
        input.add('2');
        input.add('2');
        input.add('4');

        PermuteSet permutSet = new PermuteSet();
        permutSet.permut(input);
        for (String str : permutSet.getOutput()) {
            System.out.println(str);
        }
    }
}

class PermuteSet {

    /**
     * Store all the output of k-permutation of the input character set.
     */
    Set<String> output;

    /**
     * Create new instance of PermuteSet.
     */
    public PermuteSet() {
        output = new TreeSet<String>();
    }

    /**
     * Calculate all the k-permutation from input character set.
     *
     * @param input input character set
     * @return all k-permutation of given character set.
     */
    public Set<String> permut(Set<Character> input) {
        Set<String> retSet = new TreeSet<String>();
        if (input.size() == 1) {
            retSet.add(input.iterator().next().toString());
            output.addAll(retSet);
            return retSet;
        } else {
            for (Character ch : input) {
                SortedSet<Character> set = new TreeSet<Character>(input);
                set.remove(ch);
                for (String str : permut(set)) {
                    retSet.add(ch.toString() + str);
                }
                output.addAll(retSet);
            }
            return retSet;
        }
    }

    /**
     * Return all k-permutation set.
     *
     * @return all k-permutation set
     */
    public Set<String> getOutput() {
        return output;
    }
}