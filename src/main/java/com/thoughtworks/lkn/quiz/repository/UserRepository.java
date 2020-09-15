package com.thoughtworks.lkn.quiz.repository;

import com.thoughtworks.lkn.quiz.domain.Education;
import com.thoughtworks.lkn.quiz.domain.User;

import java.util.List;
import java.util.Optional;

public class UserRepository {
    private List<User> userList = UserListSingletonFactory.getInstance();

    public User findUserById(Long id) {
        Optional<User> optionalUser = userList.stream().filter(user -> user.getId().equals(id)).findFirst();
        User user = null;
        if (optionalUser.isPresent()){
            user =  optionalUser.get();
        }
        return user;
    }

    public List<Education> findUserEducationsById(Long id) {
        Optional<User> optionalUser = userList.stream().filter(user -> user.getId().equals(id)).findFirst();
        List<Education> educationList = null;
        if (optionalUser.isPresent()){
            educationList = optionalUser.get().getEducationList();
        }
        return educationList;
    }

    public void save(User user) {
        if (userList.isEmpty()) {
            user.setId(Long.parseLong("0"));
        } else {
            user.setId((long) userList.size());
        }
        userList.add(user);
    }
}
