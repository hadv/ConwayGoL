package com.company.demo.palindrome;

/**
 * A demo program to check a given string is <em>palindrome</em> or not.
 *
 * @author hadv
 */
public class Palindrome {

    public static void main(String[] args) {
        System.out.println(": " + palindrome("RACE CAR"));
        System.out.println(": " + palindrome("Was it a car or a cat I saw"));
        System.out.println(": " + palindrome("Never odd or even"));
        System.out.println(": " + palindrome("ABBA"));
        System.out.println(": " + palindrome("ABHBA"));
        System.out.println(": " + palindrome("step on no pets"));
        System.out.println(": " + palindrome("In girum imus nocte et consumimur igni"));
        System.out.println(": " + palindrome("Eva, can I stab bats in a cave?"));
        System.out.println(": " + palindrome("Mr. Owl ate my metal worm"));
        System.out.println(": " + palindrome("A man, a plan, a canal - Panama!"));
        System.out.println(": " + palindrome("Doc, note: I dissent. A fast never prevents a fatness. I diet on cod"));
    }

    /**
     * Check a given input string is <em>palindrome</em> or not.
     *
     * @param s the given string to check if it's a <em>palindrome</em> or not.
     * @return {@code true} if the given string is <em>palindrome</em>; otherwise return {@code false}.
     */
    public static boolean palindrome(String s) {
        System.out.print("\"" + s + "\"");
        s = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        int len = s.length();

        int mid = len/2;

        for (int i = 0; i < mid; i++) {
            if (s.charAt(i) != s.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
