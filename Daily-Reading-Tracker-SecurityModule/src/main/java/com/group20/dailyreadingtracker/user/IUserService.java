package com.group20.dailyreadingtracker.user;

import java.util.List;

public interface IUserService {
    void save(User user);

    User findByEmail(String email);

    List<User> findAllUsers();
}
