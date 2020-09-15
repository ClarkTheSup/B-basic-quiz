package com.thoughtworks.lkn.quiz.repository;

import com.thoughtworks.lkn.quiz.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserListSingletonFactory {
    private static final List<User> userList = new ArrayList<>();
    private UserListSingletonFactory() {}

    public static List<User> getInstance() {
        return userList;
    }
}
