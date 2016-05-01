package com.example.android.kvs.models;

public class User {
    private long id;
    private String name;
    private int age;
    private boolean guestFlag;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isGuest() {
        return guestFlag;
    }

    public User(long id, String name, int age, boolean guestFlag) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.guestFlag = guestFlag;
    }
}
