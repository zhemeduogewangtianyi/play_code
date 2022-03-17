package com.opencode.spring.application.service.impl;

import com.opencode.spring.application.Student;
import com.opencode.spring.application.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private Student student;

    @Override
    public Student getStu() {
        return student;
    }

}
