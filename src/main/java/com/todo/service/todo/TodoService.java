package com.todo.service.todo;

import com.todo.entity.Todo;

import java.util.List;

/**
 * Created by Snayki on 17.03.2016.
 */
public interface TodoService {
    List<Todo> findAll();

    Todo findById(Integer id);

    Todo create(Todo todo);

    Todo update(Todo todo);

    void delete(Integer id);
}
