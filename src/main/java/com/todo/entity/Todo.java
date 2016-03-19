package com.todo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.todo.utils.jackson.View;

import javax.persistence.*;

/**
 * Created by Snayki on 16.03.2016.
 */
@Entity
@Table(name="todo_list")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    @JsonView(View.Public.class)
    private int id;

    @JsonView(View.Public.class)
    @Column(name="title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonView(View.Public.class)
    @Column(name="done")
    private Boolean done = Boolean.FALSE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
