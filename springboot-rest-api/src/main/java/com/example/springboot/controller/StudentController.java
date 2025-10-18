package com.example.springboot.controller;

import com.example.springboot.bean.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    // http://localhost:8080/student
    @GetMapping("/student")
    public Student getStudent() {
        Student student = new Student(
                1,
                "Jeyakumar",
                "Moorthy"
        );
        return student;
    }

    // http://localhost:8080/students
    @GetMapping("/students")
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Jeyakumar", "Moorthy"));
        students.add(new Student(2, "Vinothkumar", "Moorthy"));
        students.add(new Student(3, "Usha", "Nandhini"));
        students.add(new Student(4, "Kavitha", "Moorthy"));
        return students;
        }
    }
