package com.Dream11.services.Utils;

import com.Dream11.services.models.User;

public class UserUtils {
    public static User createUser(String id, String name, int credits){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setCredits(credits);
        return user;
    }
}
