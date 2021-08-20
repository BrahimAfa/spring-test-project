/**
 * Created by: nuwan_r
 * Created on: 6/18/2021
 **/
package com.attsw.attsw_exam;

import com.attsw.attsw_exam.dto.StudentDto;
import com.attsw.attsw_exam.enums.Status;
import com.attsw.attsw_exam.model.SharedModel;
import com.attsw.attsw_exam.model.Student;
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

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AttswExamApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class StudentUpdateCustomServiceTest {

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
    public void testUpdateStudentService() {

        StudentDto student2Dto = new StudentDto();
        student2Dto.setId(192);
        student2Dto.setAge(29);
        student2Dto.setName("Praveen");
        student2Dto.setCollageName("Raju bhai");
        student2Dto.setContactNo("0753833833");
        student2Dto.setCreatedDate(new Date());
        student2Dto.setLastModifiedDate(new Date());
        student2Dto.setStatus(Status.ACTIVE.getStatusSeq());

        Student student2 = this.modelMapper.map(student2Dto,Student.class);

        SharedModel sharedModel = new SharedModel(2);

        studentService.updateStudent(student2);
        verify(studentRepository,times(1)).save(student2);
        assertEquals(student2.getContactNo(),student2Dto.getContactNo());
        assertEquals(student2.getId(),student2Dto.getId());
        assertEquals(student2.getAge(),student2Dto.getAge());
        assertEquals(student2.getCollageName(),student2Dto.getCollageName());
        assertEquals(student2.getName(),student2Dto.getName());
        assertEquals(Status.findOne(2).getStatusSeq(),Status.ACTIVE.getStatusSeq());
        assertEquals(Status.findOne(2).getStatusName(),Status.ACTIVE.getStatusName());
        assertEquals(sharedModel.getStatus(),Status.ACTIVE.getStatusSeq());
    }


}
