package com.example.demo.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.example.demo.model.Savings;

@DataJpaTest
@Sql("SavingsRepositoryTest.sql")
public class SavingsRepositoryTest {
	@Autowired
	SavingsRepository repo;

	@BeforeEach
	void setUp() {
	}

	@Test
	void test_findByUserId() {
		List<Savings> result = repo.findByUserId(101L);
		assertThat(result).hasSize(2);
		assertThat(result).allMatch(s -> s.getUserId() == 101L);
		result.forEach(savings -> {
			System.out.println(savings);
		});
	}
}
