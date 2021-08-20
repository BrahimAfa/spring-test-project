/**
 * Created by: nuwan_r
 * Created on: 6/18/2021
 **/
package com.attsw.attsw_exam;

import com.attsw.attsw_exam.enums.Status;
import com.attsw.attsw_exam.model.Student;
import com.attsw.attsw_exam.repository.StudentRepository;
import com.attsw.attsw_exam.repository.TeacherRepository;
import com.attsw.attsw_exam.service.StudentService;
import com.attsw.attsw_exam.service.TeacherService;
import com.attsw.attsw_exam.utility.DateAuditingProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AttswExamApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class GetAndDeleteStudentControllerTest {

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
    public void deleteStudentControlerTest() throws Exception {

        Student student2 = new Student();
        student2.setId(233);
        student2.setStatus(Status.ACTIVE.getStatusSeq());
        student2.setName("arjubn");
        student2.setCollageName("analda");
        student2.setContactNo("0783923292");

        Gson gson = new Gson();
        String jsonStringStudent = gson.toJson(student2);
        when(studentRepository.findByIdAndStatus(student2.getId(),Status.ACTIVE.getStatusSeq())).thenReturn(java.util.Optional.ofNullable(student2));
        when(studentRepository.save(any(Student.class))).thenReturn(student2);


        mockMvc.perform(MockMvcRequestBuilders.delete("/student/233")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk());

        Mockito.verify(studentRepository).save(student2);
    }

    @Test
    public void getStudentControlerTest() throws Exception {

        Student student2 = new Student();
        student2.setId(233);
        student2.setStatus(Status.ACTIVE.getStatusSeq());
        student2.setName("arjubn");
        student2.setCollageName("analda");
        student2.setContactNo("0783923292");

        when(studentRepository.findByIdAndStatus(student2.getId(),Status.ACTIVE.getStatusSeq())).thenReturn(java.util.Optional.ofNullable(student2));


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/233")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody =
                mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(student2));
        Mockito.verify(studentRepository).findByIdAndStatus(student2.getId(),Status.ACTIVE.getStatusSeq());
    }


}
