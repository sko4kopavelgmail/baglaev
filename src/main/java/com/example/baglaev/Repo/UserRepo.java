package com.example.baglaev.Repo;

import com.example.baglaev.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUserName(String userName);
}
