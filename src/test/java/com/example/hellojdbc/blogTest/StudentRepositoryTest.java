package com.example.hellojdbc.blogTest;

import com.example.hellojdbc.test.Student;
import com.example.hellojdbc.test.StudentRepository;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import static com.example.hellojdbc.connection.ConnectionConst.*;

@Slf4j
public class StudentRepositoryTest {

    StudentRepository studentRepository;

    @BeforeEach
    void setUserRepository() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        studentRepository = new StudentRepository(dataSource);
    }

    @Test
    void saveAndFind() throws Exception {

        long start = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            Long id = (long) i;
            String name = "Student" + i;
            Student student = new Student(id, name);
            studentRepository.save(student);
        }

        log.info("time: " + (System.nanoTime() - start));
    }
}
