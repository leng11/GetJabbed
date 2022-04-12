package com.example.controller;

import com.example.dao.UserDao;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/details")
public class UserController {
    @Autowired
    private UserDao userDao;


    @PostMapping("/register")
    public User registerPerson(@RequestBody User user) {
        return userDao.save(user);
    }


    @GetMapping("/display")
    public User findById(@RequestParam(name = "ssn") String official_id) {
        return userDao.findById(official_id);
    }


}