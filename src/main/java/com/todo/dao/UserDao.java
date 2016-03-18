package com.todo.dao;

import com.todo.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Snayki on 18.03.2016.
 */
@Repository
public interface UserDao extends CrudRepository<User, Integer> {
    User findUserByUsername(String username);
}
