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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AttswExamApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class TeacherActiveDeactiveCustomServiceTest {

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
    public void testDeactiveStudent() {

        Student student2 = new Student();
        student2.setId(100);
        student2.setAge(21);
        student2.setCollageName("Nalanda");
        student2.setContactNo("0782827377");
        student2.setStatus(Status.DELETED.getStatusSeq());

        Student student3 = new Student();
        student3.setId(101);
        student3.setAge(21);
        student3.setCollageName("Baya");
        student3.setContactNo("07883888383");
        student3.setStatus(Status.ACTIVE.getStatusSeq());


        when(studentRepository.findAllByStatus(Status.DELETED.getStatusSeq())).thenReturn(Stream
                .of(student2,student3).collect(Collectors.toList()));

        assertEquals(2,studentService.findAllDeactive().size());
    }

    @Test
    public void testSystemActiveStudent() {

        Student student2 = new Student();
        student2.setId(100);
        student2.setAge(21);
        student2.setCollageName("Nalanda");
        student2.setContactNo("0782827377");
        student2.setStatus(Status.ACTIVE.getStatusSeq());

        Student student3 = new Student();
        student3.setId(101);
        student3.setAge(21);
        student3.setCollageName("Baya");
        student3.setContactNo("07883888383");
        student3.setStatus(Status.ACTIVE.getStatusSeq());


        when(studentRepository.findAllByStatus(Status.ACTIVE.getStatusSeq())).thenReturn(Stream
                .of(student2,student3).collect(Collectors.toList()));

        assertEquals(2,studentService.findAllActiveStudent().size());
    }

    @Test
    public void testActiveStudent() {

        Student student1 = new Student();
        student1.setId(99);
        student1.setAge(23);
        student1.setCollageName("Ananda");
        student1.setContactNo("0788988988");
        student1.setStatus(Status.ACTIVE.getStatusSeq());


        studentService.findAllActiveStudent();
        verify(studentRepository,times(1)).findAllByStatus(Status.ACTIVE.getStatusSeq());

    }

    @Test
    public void testActiveTeachersList() {



        List<Student> listODStudent = new ArrayList<>();
        Student student1 = new Student();
        student1.setId(99);
        student1.setAge(23);
        student1.setCollageName("Ananda");
        student1.setContactNo("0788988988");
        listODStudent.add(student1);

        List<Student> listODStudent2 = new ArrayList<>();
        Student student2 = new Student();
        student2.setId(100);
        student2.setAge(21);
        student2.setCollageName("Nalanda");
        student2.setContactNo("0782827377");
        listODStudent2.add(student2);

        Teacher teacher1 = new Teacher();
        teacher1.setStatus(Status.ACTIVE.getStatusSeq());
        teacher1.setAddress("colombo");
        teacher1.setContactNo("0783833833");
        teacher1.setEmail("sanath@gmail.com");
        teacher1.setName("sanath");
        teacher1.setId(123);
        teacher1.setStudent(listODStudent);

        Teacher teacher2 = new Teacher();
        teacher2.setStatus(Status.ACTIVE.getStatusSeq());
        teacher2.setAddress("anawala");
        teacher2.setContactNo("0763738883");
        teacher2.setEmail("sachin@gmail.com");
        teacher2.setName("sachin");
        teacher2.setId(124);
        teacher2.setStudent(listODStudent2);

        when(teacherRepository.findAllByStatus(Status.ACTIVE.getStatusSeq())).thenReturn(Stream
                .of(teacher1,teacher2).collect(Collectors.toList()));

        assertEquals(2,teacherService.findAllActive().size());
    }

    @Test
    public void testDeactiveTeachers() {

        List<Student> listODStudent2 = new ArrayList<>();
        Student student2 = new Student();
        student2.setId(100);
        student2.setAge(21);
        student2.setCollageName("Nalanda");
        student2.setContactNo("0782827377");
        listODStudent2.add(student2);

        Teacher teacher3 = new Teacher();
        teacher3.setStatus(Status.ACTIVE.getStatusSeq());
        teacher3.setAddress("karachi");
        teacher3.setContactNo("0763738883");
        teacher3.setEmail("ganguli@gmail.com");
        teacher3.setName("ganguli");
        teacher3.setId(125);
        teacher3.setStudent(listODStudent2);


        when(teacherRepository.findAllByStatus(Status.DELETED.getStatusSeq())).thenReturn(Stream
                .of(teacher3).collect(Collectors.toList()));

        assertEquals(1,teacherService.findAllDeactive().size());
    }


}
