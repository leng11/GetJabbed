package com.example.controller;

import com.example.dao.UserDao;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/v1/vaccine/users/")
public class UserController {
    @Autowired
    private UserDao userDao;


    @PostMapping("/register")
    public User registerPerson(@RequestBody User user) {
        return userDao.save(user);
    }


    @GetMapping("/data")
    public User findById(@RequestParam(name = "officialid") int officialid) {
        return userDao.findByOfficialid(officialid);
    }


    @GetMapping("/list")
    public List< User > findAll() {
        return userDao.findAll();
    }

}