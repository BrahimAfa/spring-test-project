/**
 * Created by: subha_babu
 * Created on: 6/16/2021
 **/
package com.attsw.attsw_exam.serviceimpl;

import com.attsw.attsw_exam.enums.Status;
import com.attsw.attsw_exam.model.Teacher;
import com.attsw.attsw_exam.repository.TeacherRepository;
import com.attsw.attsw_exam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TeacherServiceImpl implements TeacherService {

    private static final Logger logger = Logger.getLogger(TeacherServiceImpl.class.getName());
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {

        teacher.setStatus(Status.ACTIVE.getStatusSeq());
        logger.info("Teacher Saved Successfully!!");
        return this.teacherRepository.save(teacher);

    }

    @Override
    public Teacher updateTeacher(Teacher teacher, Teacher dbTeacher) {

        teacher.setStatus(Status.ACTIVE.getStatusSeq());
        teacher.setCreatedDate(dbTeacher.getCreatedDate());
        return this.teacherRepository.save(teacher);

    }

    @Override
    public Teacher deleteTeacher(Teacher teacher) {

        teacher.setStatus(Status.DELETED.getStatusSeq());
        if (teacher.getStudent() != null) {
            teacher.getStudent().forEach(rec -> rec.setStatus(Status.DELETED.getStatusSeq()));
        }
        return this.teacherRepository.save(teacher);

    }

    @Override
    public List<Teacher> findAllActive() {
        return this.teacherRepository.findAllByStatus(Status.ACTIVE.getStatusSeq());

    }

    @Override
    public List<Teacher> findAllDeactive() {
        return this.teacherRepository.findAllByStatus(Status.DELETED.getStatusSeq());

    }

    @Override
    public List<Teacher> findAll() {
        return this.teacherRepository.findAll();

    }


    @Override
    public Optional<Teacher> findByIdAndStatus(Integer id, Integer status) {
        return this.teacherRepository.findByIdAndStatus(id, Status.ACTIVE.getStatusSeq());
    }

    @Override
    public Optional<Teacher> findByEmailAndStatus(String email, Integer status) {
        return this.teacherRepository.findByEmailAndStatus(email, Status.ACTIVE.getStatusSeq());
    }

}
