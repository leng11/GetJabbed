package com.example.controller;

import com.example.dao.UserDao;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-controller")
public class UserController {
    @Autowired private UserDao userDao;


    @PostMapping("/register")
    public User registerPerson(@RequestBody User user) {
        return userDao.save(user);
    }


    @GetMapping("/listuserdata")
    public User findById(@RequestParam(name = "ssn") String ssn) {
        return userDao.findBySsn(ssn);
    }



}