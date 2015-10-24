package com.company.demo.top.game;

import com.company.util.BoundedPriorityQueue;

import java.util.Arrays;

public class GameDemo {
    public static void main(String[] args) {
        BoundedPriorityQueue<User> top3 = new BoundedPriorityQueue<User>(3, new UserComparator());

        top3.add(new User(100, "FooFoo"));
        top3.add(new User(90, "BarBar"));
        top3.add(new User(80, "FooBar"));
        top3.add(new User(99, "BarFoo"));

        int i = 0;
        Object[] users  = top3.toArray();
        Arrays.sort(users);
        for (Object o : users) {
            i++;
            User u = (User)o;
            System.out.println(i + " \t " + u.getUserName() + " \t " + u.getPoint());
        }
    }
}
