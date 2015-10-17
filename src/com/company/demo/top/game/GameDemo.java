package com.company.demo.top.game;

public class GameDemo {
    public static void main(String[] args) {
        FixedSizeSortedSet<User> top3 = new FixedSizeSortedSet<User>(new UserComparator());

        top3.add(new User(100, "FooFoo"));
        top3.add(new User(90, "BarBar"));
        top3.add(new User(80, "FooBar"));
        top3.add(new User(99, "BarFoo"));

        int i = 0;
        for (User u : top3.descendingSet()) {
            i++;
            System.out.println(i + " \t " + u.getUserName() + " \t " + u.getPoint());
        }
    }
}
