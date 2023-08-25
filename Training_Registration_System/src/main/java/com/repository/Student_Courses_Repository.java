package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.entity.Student_Courses;

@Repository
public interface Student_Courses_Repository extends JpaRepository<Student_Courses, Integer> {

}
