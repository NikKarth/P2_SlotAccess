package com.example.slot_access.web;

import com.example.slot_access.model.ClassEntity;
import com.example.slot_access.model.CourseStudentEntity;
import com.example.slot_access.model.InstructorCourseEntity;
import com.example.slot_access.repository.ClassRepository;
import com.example.slot_access.repository.CourseStudentRepository;
import com.example.slot_access.repository.InstructorCourseRepository;
import jakarta.validation.constraints.NotBlank;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin")
public class AdminAccessController {

    private final ClassRepository classRepository;
    private final InstructorCourseRepository instructorCourseRepository;
    private final CourseStudentRepository courseStudentRepository;

    public AdminAccessController(
        ClassRepository classRepository,
        InstructorCourseRepository instructorCourseRepository,
        CourseStudentRepository courseStudentRepository
    ) {
        this.classRepository = classRepository;
        this.instructorCourseRepository = instructorCourseRepository;
        this.courseStudentRepository = courseStudentRepository;
    }

    @GetMapping("/login")
    public Map<String, String> loginCheck() {
        return Map.of("message", "Admin authenticated");
    }

    @PostMapping("/classes")
    @ResponseStatus(HttpStatus.CREATED)
    public ClassEntity createClass(@RequestBody CreateClassRequest request) {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setName(request.name());
        classEntity.setTerm(request.term());
        return classRepository.save(classEntity);
    }

    @PutMapping("/classes/{classId}/instructor")
    public ClassEntity assignInstructor(
        @PathVariable Long classId,
        @RequestBody AssignInstructorRequest request
    ) {
        ClassEntity classEntity = classRepository.findById(classId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found"));
        classEntity.setInstructorId(request.instructorId());
        return classRepository.save(classEntity);
    }

    @PostMapping("/instructors/{instructorId}/courses")
    @ResponseStatus(HttpStatus.CREATED)
    public InstructorCourseEntity assignCourseToInstructor(
        @PathVariable String instructorId,
        @RequestBody AssignCourseRequest request
    ) {
        InstructorCourseEntity assignment = new InstructorCourseEntity();
        assignment.setInstructorId(instructorId);
        assignment.setCourseId(request.courseId());
        return instructorCourseRepository.save(assignment);
    }

    @PostMapping("/courses/{courseId}/students")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseStudentEntity assignStudentToCourse(
        @PathVariable String courseId,
        @RequestBody AssignStudentRequest request
    ) {
        CourseStudentEntity assignment = new CourseStudentEntity();
        assignment.setCourseId(courseId);
        assignment.setStudentId(request.studentId());
        return courseStudentRepository.save(assignment);
    }

    public record CreateClassRequest(@NotBlank String name, @NotBlank String term) {
    }

    public record AssignInstructorRequest(@NotBlank String instructorId) {
    }

    public record AssignCourseRequest(@NotBlank String courseId) {
    }

    public record AssignStudentRequest(@NotBlank String studentId) {
    }
}
