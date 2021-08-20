/**
 * Created by: nuwan_r
 * Created on: 6/18/2021
 **/
package com.attsw.attsw_exam;

import com.attsw.attsw_exam.dto.TeacherDto;
import com.attsw.attsw_exam.enums.Status;
import com.attsw.attsw_exam.model.Student;
import com.attsw.attsw_exam.model.Teacher;
import com.attsw.attsw_exam.repository.StudentRepository;
import com.attsw.attsw_exam.repository.TeacherRepository;
import com.attsw.attsw_exam.service.StudentService;
import com.attsw.attsw_exam.service.TeacherService;
import com.attsw.attsw_exam.utility.DateAuditingProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AttswExamApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class TeacherFindByCustomServiceTest {

    @Autowired
    private TeacherService teacherService;

    @MockBean
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DateAuditingProvider dateAuditingProvider;

    @Test
    public void testFindAllByCollleageName() {

        Student student2 = new Student();
        student2.setContactNo("0783838883");


        studentService.findAllByContactNo(student2.getContactNo());
        verify(studentRepository,times(1)).findAllByContactNo(student2.getContactNo());

    }

    @Test
    public void testFindByEmailTeacher() {

        TeacherDto teacher1Dto = new TeacherDto();
        teacher1Dto.setStatus(Status.ACTIVE.getStatusSeq());
        teacher1Dto.setAddress("colombo");
        teacher1Dto.setContactNo("0783833833");
        teacher1Dto.setEmail("sanath@gmail.com");
        teacher1Dto.setName("sanath");
        teacher1Dto.setId(123);
        Student student = new Student();
        student.setId(101);
        student.setAge(21);
        student.setCollageName("Baya");
        student.setContactNo("07883888383");
        List<Student> listOFStudent = new ArrayList<>();
        listOFStudent.add(student);
        teacher1Dto.setStudent(listOFStudent);
        Teacher mapedTeacher = this.modelMapper.map(teacher1Dto, Teacher.class);

        Teacher teacher2 = new Teacher();
        teacher2.setStatus(Status.ACTIVE.getStatusSeq());
        teacher2.setAddress("anawala");
        teacher2.setContactNo("0763738883");
        teacher2.setEmail("sachin@gmail.com");
        teacher2.setName("sachin");
        teacher2.setId(124);

        teacherService.findByEmailAndStatus("sachin@gmail.com", Status.ACTIVE.getStatusSeq());
        verify(teacherRepository, times(1)).findByEmailAndStatus("sachin@gmail.com", Status.ACTIVE.getStatusSeq());
        assertEquals(mapedTeacher.getName(),teacher1Dto.getName());
    }

    @Test
    public void testFindByIdTeacher() {

        List<Student> listODStudent2 = new ArrayList<>();
        Student student2 = new Student();
        student2.setId(192);
        student2.setAge(29);
        student2.setCollageName("Raju bhai");
        student2.setContactNo("0753833833");
        listODStudent2.add(student2);

        Teacher teacher3 = new Teacher();
        teacher3.setStatus(Status.ACTIVE.getStatusSeq());
        teacher3.setAddress("Panjab");
        teacher3.setContactNo("0672728882");
        teacher3.setEmail("shewag@gmail.com");
        teacher3.setName("shewag");
        teacher3.setId(128);
        teacher3.setStudent(listODStudent2);

        teacherService.findByIdAndStatus(128,Status.ACTIVE.getStatusSeq());
        verify(teacherRepository,times(1)).findByIdAndStatus(128,Status.ACTIVE.getStatusSeq());


    }



}
