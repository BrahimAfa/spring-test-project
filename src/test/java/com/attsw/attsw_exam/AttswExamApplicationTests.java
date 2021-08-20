package com.attsw.attsw_exam;

import com.attsw.attsw_exam.dto.StudentDto;
import com.attsw.attsw_exam.dto.TeacherDto;
import com.attsw.attsw_exam.enums.Status;
import com.attsw.attsw_exam.model.SharedModel;
import com.attsw.attsw_exam.model.Student;
import com.attsw.attsw_exam.model.Teacher;
import com.attsw.attsw_exam.repository.StudentRepository;
import com.attsw.attsw_exam.repository.TeacherRepository;
import com.attsw.attsw_exam.service.StudentService;
import com.attsw.attsw_exam.service.TeacherService;
import com.attsw.attsw_exam.utility.DateAuditingProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
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

import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = AttswExamApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class AttswExamApplicationTests {

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

	@Test
	public void testDeleteTeacher() {

		List<Student> listODStudent2 = new ArrayList<>();
		Student student2 = new Student();
		student2.setId(192);
		student2.setAge(29);
		student2.setCollageName("Raju bhai");
		student2.setContactNo("0753833833");
		student2.setStatus(Status.ACTIVE.getStatusSeq());
		listODStudent2.add(student2);

		Teacher teacher3 = new Teacher();
		teacher3.setStatus(Status.ACTIVE.getStatusSeq());
		teacher3.setAddress("Panjab");
		teacher3.setContactNo("0672728882");
		teacher3.setEmail("shewag@gmail.com");
		teacher3.setName("shewag");
		teacher3.setId(128);
		teacher3.setStudent(listODStudent2);

		teacherService.deleteTeacher(teacher3);
		verify(teacherRepository,times(1)).save(teacher3);
		assertEquals(teacher3.getStudent().get(0).getStatus(), Status.DELETED.getStatusSeq());

	}

	@Test
	public void testDeleteTeacherWithoutStudent() {

		Teacher teacher3 = new Teacher();
		teacher3.setStatus(Status.ACTIVE.getStatusSeq());
		teacher3.setAddress("Panjab");
		teacher3.setContactNo("0672728882");
		teacher3.setEmail("shewag@gmail.com");
		teacher3.setName("shewag");
		teacher3.setId(128);

		teacherService.deleteTeacher(teacher3);
		verify(teacherRepository,times(1)).save(teacher3);

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
	public void testFindAllByCollleageName() {

		Student student2 = new Student();
		student2.setContactNo("0783838883");


		studentService.findAllByContactNo(student2.getContactNo());
		verify(studentRepository,times(1)).findAllByContactNo(student2.getContactNo());

	}

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
	public void testSaveService() {

		Student student2 = new Student();
		student2.setId(192);
		student2.setAge(29);
		student2.setCollageName("Raju bhai");
		student2.setContactNo("0753833833");
		student2.setCreatedDate(new Date());
		student2.setLastModifiedDate(new Date());

		when(studentRepository.save(student2)).thenReturn(student2);

		assertEquals(student2,studentService.saveStudent(student2));
	}

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

    @Test
    public void testDeleteStudentService() {

        StudentDto student2Dto = new StudentDto();
		student2Dto.setId(192);
		student2Dto.setAge(29);
		student2Dto.setCollageName("Raju bhai");
		student2Dto.setContactNo("0753833833");
		student2Dto.setStatus(Status.ACTIVE.getStatusSeq());
		Student student2 = this.modelMapper.map(student2Dto,Student.class);

        studentService.deleteStudent(student2);
        verify(studentRepository,times(1)).save(student2);
        assertEquals(student2.getContactNo(),student2Dto.getContactNo());
		assertEquals(student2.getId(),student2Dto.getId());
		assertEquals(student2.getAge(),student2Dto.getAge());
		assertEquals(student2.getCollageName(),student2Dto.getCollageName());
		assertEquals(student2.getName(),student2Dto.getName());
		assertEquals(student2.getLastModifiedDate(),student2Dto.getLastModifiedDate());
		assertNotEquals(student2.getStatus(),student2Dto.getStatus());
    }

	@Test
	public void testFindStudentByAge() {

		StudentDto student2 = new StudentDto();
		student2.setAge(44);

		studentService.findAllByAge(student2.getAge());
		verify(studentRepository,times(1)).findAllByAge(student2.getAge());

	}

	@Test
	public void testFindStudentByCollageName() {

		StudentDto student2 = new StudentDto();
		student2.setCollageName("Rahula");

		studentService.findAllByCollageName(student2.getCollageName());
		verify(studentRepository,times(1)).findAllByCollageName(student2.getCollageName());

	}

	@Test
	public void testFindByIdAndStatus() {

		StudentDto student2 = new StudentDto();
		student2.setId(123);
		student2.setStatus(Status.ACTIVE.getStatusSeq());

		studentService.findByIdAndStatus(student2.getId(),student2.getStatus());
		verify(studentRepository,times(1)).findByIdAndStatus(student2.getId(),student2.getStatus());

	}


    /*student controller class*/

	@Test
	public void findAllTest() throws Exception {

		Mockito.when(studentRepository.findAll()).thenReturn(
				Collections.emptyList()
		);

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.get("/student/findAll")
						.accept(MediaType.APPLICATION_JSON)
		).andReturn();

		System.out.println(mvcResult);

		Mockito.verify(studentRepository).findAllByStatus(Status.ACTIVE.getStatusSeq());
	}

	@Test
    public void findAllTeachers() throws Exception {

        Mockito.when(teacherRepository.findAll()).thenReturn(
                Collections.emptyList()
        );

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/teacher/findAll")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        Mockito.verify(teacherRepository).findAll();
    }

	@Test
	public void findAllActiveTeachers() throws Exception {

		Mockito.when(teacherRepository.findAll()).thenReturn(
				Collections.emptyList()
		);

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.get("/teacher/findAllActive")
						.accept(MediaType.APPLICATION_JSON)
		).andReturn();

		Mockito.verify(teacherRepository).findAllByStatus(Status.ACTIVE.getStatusSeq());
	}

	@Test
	public void findAllDeactiveTeachers() throws Exception {

		Mockito.when(teacherRepository.findAll()).thenReturn(
				Collections.emptyList()
		);

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.get("/teacher/findAllDeactive")
						.accept(MediaType.APPLICATION_JSON)
		).andReturn();

		Mockito.verify(teacherRepository).findAllByStatus(Status.DELETED.getStatusSeq());
	}

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

	@Test
	public void updateTeachersControlerTestServerErrorResponse() throws Exception {

		List<Student> listODStudent2 = new ArrayList<>();
		Student student2 = new Student();
		student2.setId(123);
		student2.setAge(29);
		student2.setCollageName("Raju bhai");
		student2.setContactNo("0753833833");
		listODStudent2.add(student2);

		Teacher teacher3 = new Teacher();
		teacher3.setId(99);
		teacher3.setStatus(Status.ACTIVE.getStatusSeq());
		teacher3.setAddress("Panjab");
		teacher3.setContactNo("0672728882");
		teacher3.setEmail("shewagv1@gmail.com");
		teacher3.setName("shewag");
		//teacher3.setStudent(listODStudent2);

		Gson gson = new Gson();
		String jsonStringTeacher = gson.toJson(teacher3);
        when(teacherRepository.findByIdAndStatus(teacher3.getId(),Status.ACTIVE.getStatusSeq())).thenReturn(java.util.Optional.ofNullable(teacher3));
		when(teacherRepository.save(teacher3)).thenReturn(teacher3);

		mockMvc.perform(MockMvcRequestBuilders.put("/teacher")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStringTeacher))
				.andExpect(status().isInternalServerError());

		Mockito.verify(teacherRepository).findByIdAndStatus(teacher3.getId(),teacher3.getStatus());
	}

    @Test
    public void updateTeachersControlerTestSuccessResponse() throws Exception {

        Teacher teacher3 = new Teacher();
        teacher3.setId(99);
        teacher3.setStatus(Status.ACTIVE.getStatusSeq());
        teacher3.setAddress("Panjab updated record");
        teacher3.setContactNo("0672728882");
        teacher3.setEmail("shewagv1@gmail.com");
        teacher3.setName("shewag");

        Teacher teacher4 = new Teacher();
        teacher4.setId(99);
        teacher4.setStatus(Status.ACTIVE.getStatusSeq());
        teacher4.setAddress("Panjab from db");
        teacher4.setContactNo("0672728882");
        teacher4.setEmail("shewagv1@gmail.com");
        teacher4.setName("shewag");

        Gson gson = new Gson();
        String jsonStringTeacher = gson.toJson(teacher3);
        when(teacherRepository.findByIdAndStatus(teacher4.getId(),Status.ACTIVE.getStatusSeq())).thenReturn(java.util.Optional.ofNullable(teacher4));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher4);

        mockMvc.perform(MockMvcRequestBuilders.put("/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStringTeacher))
                .andExpect(status().isOk());

        Mockito.verify(teacherRepository).findByIdAndStatus(teacher3.getId(),teacher3.getStatus());
    }

	@Test
	public void deleteTeachersControlerTest() throws Exception {

		Teacher teacher3 = new Teacher();
		teacher3.setId(99);
		teacher3.setStatus(Status.ACTIVE.getStatusSeq());
		teacher3.setAddress("Panjab");
		teacher3.setContactNo("0672728882");
		teacher3.setEmail("shewagv1@gmail.com");
		teacher3.setName("shewag");

		Gson gson = new Gson();
		String jsonStringTeacher = gson.toJson(teacher3);

		when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher3);

		mockMvc.perform(MockMvcRequestBuilders.delete("/teacher/99")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStringTeacher))
				.andExpect(status().isNotFound());

		Mockito.verify(teacherRepository).findByIdAndStatus(teacher3.getId(),teacher3.getStatus());
	}

    @Test
    public void deleteSuceessTeachersControlerTest() throws Exception {

        Teacher teacher3 = new Teacher();
        teacher3.setId(99);
        teacher3.setStatus(Status.ACTIVE.getStatusSeq());
        teacher3.setAddress("Panjab");
        teacher3.setContactNo("0672728882");
        teacher3.setEmail("shewagv1@gmail.com");
        teacher3.setName("shewag");

        Gson gson = new Gson();
        when(teacherRepository.findByIdAndStatus(teacher3.getId(),Status.ACTIVE.getStatusSeq())).thenReturn(java.util.Optional.ofNullable(teacher3));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher3);
        String jsonStringTeacher = gson.toJson(teacher3);


        mockMvc.perform(MockMvcRequestBuilders.delete("/teacher/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStringTeacher))
                .andExpect(status().isOk());

        Mockito.verify(teacherRepository).save(teacher3);
    }

	@Test
	public void findTeacherByIdControlerTest() throws Exception {

		Teacher teacher3 = new Teacher();
		teacher3.setId(99);
		teacher3.setStatus(Status.ACTIVE.getStatusSeq());
		teacher3.setAddress("Panjab");
		teacher3.setContactNo("0672728882");
		teacher3.setEmail("shewagv1@gmail.com");
		teacher3.setName("shewag");

		Gson gson = new Gson();
		String jsonStringTeacher = gson.toJson(teacher3);

		when(teacherRepository.findByIdAndStatus(teacher3.getId(),Status.ACTIVE.getStatusSeq())).thenReturn(java.util.Optional.ofNullable(teacher3));

		mockMvc.perform(MockMvcRequestBuilders.get("/teacher/99")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStringTeacher))
				.andExpect(status().isOk());

		Mockito.verify(teacherRepository).findByIdAndStatus(teacher3.getId(),teacher3.getStatus());
	}

	@Test
	public void testFinalAllActiveDifControlerTest() throws Exception {

		Teacher teacher3 = new Teacher();
		teacher3.setId(99);
		teacher3.setStatus(Status.DELETED.getStatusSeq());
		teacher3.setAddress("Panjab");
		teacher3.setContactNo("0672728882");
		teacher3.setEmail("shewagv1@gmail.com");
		teacher3.setName("shewag");

		Teacher teacher2 = new Teacher();
		teacher3.setId(100);
		teacher3.setStatus(Status.DELETED.getStatusSeq());
		teacher3.setAddress("Maharasht");
		teacher3.setContactNo("0464383883");
		teacher3.setEmail("mahen@gmail.com");
		teacher3.setName("mahen");

		List<Teacher> listOFDeleletedTeachers = new ArrayList<>();
		listOFDeleletedTeachers.add(teacher3);
		listOFDeleletedTeachers.add(teacher2);

		Mockito.when(teacherRepository.findAllByStatus(Status.DELETED.getStatusSeq())).thenReturn(listOFDeleletedTeachers);

		mockMvc.perform(MockMvcRequestBuilders.get("/teacher/findAllActive")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isNotFound());

		/*MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.get("/teacher/findAllActive")
						.accept(MediaType.APPLICATION_JSON)
		).andReturn();*/

		Mockito.verify(teacherRepository).findAllByStatus(Status.ACTIVE.getStatusSeq());

	}

	@Test
	public void testFinalAllActiveReturnOkControlerTest() throws Exception {

		Teacher teacher3 = new Teacher();
		teacher3.setId(99);
		teacher3.setStatus(Status.ACTIVE.getStatusSeq());
		teacher3.setAddress("Panjab");
		teacher3.setContactNo("0672728882");
		teacher3.setEmail("shewagv1@gmail.com");
		teacher3.setName("shewag");

		Teacher teacher2 = new Teacher();
		teacher3.setId(100);
		teacher3.setStatus(Status.ACTIVE.getStatusSeq());
		teacher3.setAddress("Maharasht");
		teacher3.setContactNo("0464383883");
		teacher3.setEmail("mahen@gmail.com");
		teacher3.setName("mahen");

		List<Teacher> listOFActiveTeachers = new ArrayList<>();
		listOFActiveTeachers.add(teacher3);
		listOFActiveTeachers.add(teacher2);

		Mockito.when(teacherRepository.findAllByStatus(Status.ACTIVE.getStatusSeq())).thenReturn(listOFActiveTeachers);

		mockMvc.perform(MockMvcRequestBuilders.get("/teacher/findAllActive")
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().isOk());

		/*MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.get("/teacher/findAllActive")
						.accept(MediaType.APPLICATION_JSON)
		).andReturn();*/

		Mockito.verify(teacherRepository).findAllByStatus(Status.ACTIVE.getStatusSeq());

	}

	@Test
	public void saveStudentControlerTest() throws Exception {

		Student student2 = new Student();
		student2.setId(233);
		student2.setAge(29);
		student2.setCollageName("Raju bhai");
		student2.setContactNo("0753833833");
		student2.setStatus(Status.ACTIVE.getStatusSeq());
		student2.setName("Rohith");

		Gson gson = new Gson();
		String jsonStringStudent = gson.toJson(student2);

		when(studentRepository.save(any(Student.class))).thenReturn(student2);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/student")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStringStudent))
				.andExpect(status().isAccepted())
				.andReturn();

		String actualResponseBody =
				mvcResult.getResponse().getContentAsString();
		Assertions.assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(student2));
	}

	@Test
	public void updateStudentControlerTest() throws Exception {

		Student student2 = new Student();
		student2.setId(233);
		student2.setCollageName("Raju bhai");

		Gson gson = new Gson();
		String jsonStringStudent = gson.toJson(student2);

		when(studentRepository.save(any(Student.class))).thenReturn(student2);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/student")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStringStudent))
				.andExpect(status().isAccepted())
				.andReturn();

		String actualResponseBody =
				mvcResult.getResponse().getContentAsString();
		Assertions.assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(student2));
	}

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

	@Test
	public void applicationStarts() {
		AttswExamApplication.main(new String[] {});
        Optional<TemporalAccessor> now = dateAuditingProvider.getNow();
        assertEquals(now,now);

    }

    /*..///....student controller class*/



}
