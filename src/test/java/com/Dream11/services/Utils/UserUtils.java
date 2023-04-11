package com.Dream11.services.Utils;

import com.Dream11.services.models.User;

public class UserUtils {
    public static User createUser(){
        User user = new User();
        user.setId("1");
        user.setName("test");
        user.setCredits(100);
        return user;
    }
}
