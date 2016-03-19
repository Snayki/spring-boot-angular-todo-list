package com.todo.dao;

import com.todo.entity.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Snayki on 16.03.2016.
 */
@Repository
public interface TodoDao extends CrudRepository<Todo, Integer> {
}
