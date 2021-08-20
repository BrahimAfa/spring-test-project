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
public class TeacherSaveUpdateCustomServiceTest {

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
    public void testSaveTeacher() {

        List<Student> listODStudent2 = new ArrayList<>();
        Student student2 = new Student();
        student2.setId(192);
        student2.setAge(29);
        student2.setCollageName("Raju bhai");
        student2.setContactNo("0753833833");
        listODStudent2.add(student2);

        TeacherDto teacher3Dto = new TeacherDto();
        teacher3Dto.setStatus(Status.ACTIVE.getStatusSeq());
        teacher3Dto.setAddress("Panjab");
        teacher3Dto.setContactNo("0672728882");
        teacher3Dto.setEmail("shewag@gmail.com");
        teacher3Dto.setName("shewag");
        teacher3Dto.setId(128);

        Teacher teacher3 = this.modelMapper.map(teacher3Dto,Teacher.class);
        teacher3.setStudent(listODStudent2);

        when(teacherRepository.save(teacher3)).thenReturn(teacher3);

        assertEquals(teacher3,teacherService.saveTeacher(teacher3));
        assertEquals(teacher3.getContactNo(),teacher3Dto.getContactNo());
        assertEquals(teacher3.getId(),teacher3Dto.getId());
        assertEquals(teacher3.getEmail(),teacher3Dto.getEmail());
        assertEquals(teacher3.getAddress(),teacher3Dto.getAddress());
        assertEquals(teacher3.getName(),teacher3Dto.getName());
    }

    @Test
    public void testUpdateTeacher() {

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
        teacher3.setEmail("shewagv1@gmail.com");
        teacher3.setName("shewag");
        teacher3.setId(128);
        teacher3.setStudent(listODStudent2);

        teacherService.updateTeacher(teacher3,teacher3);
        verify(teacherRepository,times(1)).save(teacher3);
    }


}
