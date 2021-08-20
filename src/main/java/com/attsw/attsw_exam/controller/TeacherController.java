/**
 * Created by: subha_babu
 * Created on: 6/16/2021
 **/
package com.attsw.attsw_exam.controller;

import com.attsw.attsw_exam.dto.TeacherDto;
import com.attsw.attsw_exam.enums.Status;
import com.attsw.attsw_exam.model.Teacher;
import com.attsw.attsw_exam.service.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    private final TeacherService teacherService;
    private ModelMapper modelMapper;

    @Autowired
    public TeacherController(TeacherService teacherService, ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.modelMapper = modelMapper;
    }

    /*save teacher*/
    @PostMapping()
    public ResponseEntity<Teacher> createTeacher(@RequestBody TeacherDto teacherDto) {

        Teacher teacher = modelMapper.map(teacherDto, Teacher.class);
        return Optional.ofNullable(teacher)
                .map(teacherObj -> this.teacherService.findByEmailAndStatus(teacher.getEmail(), Status.ACTIVE.getStatusSeq())
                        .map(updatingRec -> new ResponseEntity<>(updatingRec, HttpStatus.BAD_REQUEST))
                        .orElseGet(() -> Optional.ofNullable(this.teacherService.saveTeacher(teacher))
                                .map(val -> new ResponseEntity<>(val, HttpStatus.ACCEPTED))
                                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR))))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }

    /*update teacher*/
    @PutMapping()
    public ResponseEntity<Teacher> updateTeacher(@RequestBody TeacherDto teacherDto) {

        Teacher teacher = modelMapper.map(teacherDto, Teacher.class);
        return Optional.ofNullable(teacher)
                .map(rec -> this.teacherService.findByIdAndStatus(teacher.getId(), Status.ACTIVE.getStatusSeq())
                        .map(updatingRec -> Optional.ofNullable(this.teacherService.updateTeacher(teacher,updatingRec))
                                .map(val -> new ResponseEntity<>(val, HttpStatus.OK))
                                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }

    /*delete teacher*/
    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable("teacherId") Integer teacherId) {
        return Optional.ofNullable(teacherId)
                .map(rec -> this.teacherService.findByIdAndStatus(teacherId, Status.ACTIVE.getStatusSeq())
                        .map(filRec -> Optional.ofNullable(this.teacherService.deleteTeacher(filRec))
                                .map(deletedRec -> new ResponseEntity<>(deletedRec, HttpStatus.OK))
                                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


    /*find teacher*/
    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> findTeacher(@PathVariable("teacherId") Integer teacherId) {
        return Optional.ofNullable(teacherId).map(rec -> this.teacherService
                .findByIdAndStatus(teacherId, Status.ACTIVE.getStatusSeq())
                .map(filRec -> new ResponseEntity<>(filRec, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Teacher>> findAllTeachers() {
        List<Teacher> listOfTeachers = this.teacherService.findAll();
        return new ResponseEntity<>(listOfTeachers, HttpStatus.OK);
    }

    @GetMapping("/findAllActive")
    public ResponseEntity<List<Teacher>> findAllActiveTeachers() {
        List<Teacher> allActiveTeachers = this.teacherService.findAllActive();
        if ( allActiveTeachers.isEmpty() ) {
            return new ResponseEntity<>(allActiveTeachers, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(allActiveTeachers, HttpStatus.OK);
        }
    }

    @GetMapping("/findAllDeactive")
    public ResponseEntity<List<Teacher>> findAllDeactiveTeachers() {
        List<Teacher> findAllDeactiveTeachers = this.teacherService.findAllDeactive();
        return new ResponseEntity<>(findAllDeactiveTeachers, HttpStatus.OK);
    }


}
