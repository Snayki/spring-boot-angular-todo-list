package com.todo.service.todo;

import com.todo.dao.TodoDao;
import com.todo.entity.Todo;
import com.todo.entity.User;
import com.todo.utils.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by Snayki on 17.03.2016.
 */
@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoDao todoDao;

    @Transactional(readOnly = true)
    @Override
    public List<Todo> findAll() {
        User user = SecurityUtils.getAuthenticatedUser();

        if(user == null) {
            return Collections.emptyList();
        }

        return todoDao.findAllByUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Todo findById(Integer id) {
        return todoDao.findOne(id);
    }

    @Transactional
    @Override
    public Todo create(Todo todo) {
        todo.setUser(SecurityUtils.getAuthenticatedUser());
        todoDao.save(todo);

        return todo;
    }

    @Transactional
    @Override
    public Todo update(Todo todo) {
        todoDao.save(todo);

        return todo;
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        todoDao.delete(id);
    }
}
