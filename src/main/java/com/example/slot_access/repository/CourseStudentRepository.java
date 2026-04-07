package com.example.slot_access.repository;

import com.example.slot_access.model.CourseStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseStudentRepository extends JpaRepository<CourseStudentEntity, Long> {
}
