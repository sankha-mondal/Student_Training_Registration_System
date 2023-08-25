package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.entity.Student;

@Repository
public interface Student_Repository extends JpaRepository<Student, String> {

}
