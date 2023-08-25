package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.entity.Courses;

@Repository
public interface Courses_Repository extends JpaRepository<Courses, Integer> {

}
