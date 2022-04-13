package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

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
