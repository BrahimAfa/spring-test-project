/**
 * Created by: subha_babu
 * Created on: 6/16/2021
 **/
package com.attsw.attsw_exam.controller;

import com.attsw.attsw_exam.dto.StudentDto;
import com.attsw.attsw_exam.enums.Status;
import com.attsw.attsw_exam.model.Student;
import com.attsw.attsw_exam.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;
    private ModelMapper modelMapper;

    @Autowired
    public StudentController(StudentService studentService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public ResponseEntity<Student> createStudent(@RequestBody StudentDto studentDto) {

        Student student = modelMapper.map(studentDto, Student.class);
        return Optional.ofNullable(student)
                .map(rec -> Optional.ofNullable(this.studentService.saveStudent(rec))
                        .map(recSav -> new ResponseEntity<>(recSav, HttpStatus.ACCEPTED))
                        .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }

    @PutMapping()
    public ResponseEntity<Student> updateStudent(@RequestBody StudentDto studentDto) {

        Student student = modelMapper.map(studentDto, Student.class);
        return Optional.ofNullable(student)
                .map(rec -> Optional.ofNullable(this.studentService.updateStudent(rec))
                        .map(recSav -> new ResponseEntity<>(recSav, HttpStatus.ACCEPTED))
                        .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("studentId") Integer studentId) {

        return Optional.ofNullable(studentId)
                .map(rec -> this.studentService.findByIdAndStatus(studentId, Status.ACTIVE.getStatusSeq())
                        .map(filRec -> Optional.ofNullable(this.studentService.deleteStudent(filRec))
                                .map(deletedRec -> new ResponseEntity<>(deletedRec, HttpStatus.OK))
                                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> findStudent(@PathVariable("studentId") Integer studentId) {
        return Optional.ofNullable(studentId).map(rec -> this.studentService
                .findByIdAndStatus(studentId, Status.ACTIVE.getStatusSeq())
                .map(filRec -> new ResponseEntity<>(filRec, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Student>> findAllStudent() {
        return new ResponseEntity<>(this.studentService.findAll(), HttpStatus.OK);
    }

}
