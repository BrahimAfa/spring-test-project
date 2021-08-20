/**
 * Created by: nuwan_r
 * Created on: 6/18/2021
 **/
package com.attsw.attsw_exam;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AttswExamApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class TeacherSaveControllerTest {

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
    public void saveTeachersControlerTest() throws Exception {

        List<Student> listODStudent2 = new ArrayList<>();
        Student student2 = new Student();
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
        teacher3.setStudent(listODStudent2);

        Gson gson = new Gson();
        String jsonStringTeacher = gson.toJson(teacher3);

        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher3);

        mockMvc.perform(MockMvcRequestBuilders.post("/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStringTeacher))
                .andExpect(status().isAccepted());

        Mockito.verify(teacherRepository).findByEmailAndStatus(teacher3.getEmail(),teacher3.getStatus());
    }


}
