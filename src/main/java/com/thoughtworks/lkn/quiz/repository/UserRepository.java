package com.thoughtworks.lkn.quiz.repository;

import com.thoughtworks.lkn.quiz.domain.Education;
import com.thoughtworks.lkn.quiz.domain.User;
import com.thoughtworks.lkn.quiz.exception.Error;
import com.thoughtworks.lkn.quiz.exception.UserNotFoundException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final List<User> userList = UserListSingletonFactory.getInstance();

    public UserRepository() {
        List<Education> educationList = new ArrayList<>();
        educationList.add(new Education(Long.parseLong("0"), Long.parseLong("1997"),
                            "Student", "I love my country so much."));
        educationList.add(new Education(Long.parseLong("0"), Long.parseLong("2000"),
                "Teacher", "茶，无论是作为人生道路上的陪伴，还是生活中的记忆，都扮演着一个平淡且重要的角色."));
        educationList.add(new Education(Long.parseLong("0"), Long.parseLong("2008"),
                "Professor", "人生就像喝茶的过程，需要慢慢品，细细回味，茶泡久了会苦，就像生活会遇见不开心的事情一样"));
        educationList.add(new Education(Long.parseLong("0"), Long.parseLong("2020"),
                "President", "生活亦如喝茶一样，有时候工作累了，停下来喝一杯茶，缓解工作的劳累;"));
        User user = User.builder()
                .id(Long.parseLong("0"))
                .age(Long.parseLong("18")).name("JACK")
                .avatar("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy" +
                        "/it/u=2319772070,3114389419&fm=26&gp=0.jpg")
                .description("我们总是说：“待沧海桑田，还有一" +
                        "壶茶”、“浮华褪尽，还有一壶茶”、“时光流逝，还有一壶茶”" +
                        ";那么，是什么样的一壶茶，能让大家在沧海桑田、浮华褪尽、" +
                        "时光流逝后却依然挂念，想要拥有?")
                .educationList(educationList)
                .build();
        this.userList.add(user);
    }

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
        if (optionalUser.isPresent()){
            return optionalUser.get().getEducationList();
        } else {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            String errorDate = simpleDateFormat.format(date);
            String message = "用户不存在";
            Error error = Error.builder()
                    .error("Not Found").message(message)
                    .status(404).timeStamp(errorDate)
                    .build();
            throw new UserNotFoundException(error);
        }

    }

    public void save(User user) {
        if (userList.isEmpty()) {
            user.setId(Long.parseLong("0"));
        } else {
            user.setId((long) userList.size());
        }
        userList.add(user);
    }

    public void saveEducation(Education education) {
        User user = findUserById(education.getUserId());
        if (user != null) {
            user.getEducationList().add(education);
        }
    }
}
