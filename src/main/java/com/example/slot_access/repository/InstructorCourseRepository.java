package com.example.slot_access.repository;

import com.example.slot_access.model.InstructorCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorCourseRepository extends JpaRepository<InstructorCourseEntity, Long> {
}
