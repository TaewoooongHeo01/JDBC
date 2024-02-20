package com.example.hellojdbc.test;

import lombok.Data;

@Data
public class Student {

    private Long studentId;

    private String studentName;

    public Student(Long studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public Student() {};
}
