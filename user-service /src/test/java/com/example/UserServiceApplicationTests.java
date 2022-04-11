package com.example;

import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
class UserServiceApplicationTests {

UserServiceApplication usa;

	@Test
	void contextLoads() {
	}

	@BeforeAll
	static void beforeAll() {
		log.trace("");
	}

	@Test
	public void getAllRecords_success() throws Exception {



}}
