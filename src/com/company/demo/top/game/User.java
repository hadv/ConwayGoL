package com.company.demo.top.game;

import java.util.Comparator;

public class User {

    private String userName;
    private int point;

    public User(int mark, String userName) {
        this.point = mark;
        this.userName = userName;
    }

    public int getPoint() {
        return point;
    }

    public String getUserName() {
        return userName;
    }
}

class UserComparator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        return Integer.compare(o1.getPoint(), o2.getPoint());
    }
}
