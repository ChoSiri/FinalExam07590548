package com.example.finalexam07590548;

public interface UserManagerHelper {
    public static final String DATABASE_NAME = "login_android";
    public static final int DATABASE_VERSION = 1;

    public long registerUser(User user);
    public User checkUserLogin(User user);
}
