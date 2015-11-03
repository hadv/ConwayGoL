package com.company.demo.palindrome;

/**
 * A demo program to check a given string is <em>palindrome</em> or not.
 *
 * @author hadv
 */
public class Palindrome {

    /**
     * The endpoint for the program
     * @param args input parameters of the program.
     * @throws IllegalArgumentException
     */
    public static void main(String[] args) throws IllegalArgumentException {
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
     * @exception IllegalArgumentException if the given input string is {@code null}.
     */
    public static boolean palindrome(String s) throws IllegalArgumentException {
        if (s == null) throw new IllegalArgumentException();
        System.out.print("\"" + s + "\"");
        s = s.replaceAll("[^a-zA-Z0-9]", "");
        StringBuffer buffer = new StringBuffer(s);

        if (s.equalsIgnoreCase(buffer.reverse().toString())) return true;
        return false;
    }
}
