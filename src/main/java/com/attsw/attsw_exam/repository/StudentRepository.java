/**
 * Created by: subha_babu
 * Created on: 6/16/2021
 */
package com.attsw.attsw_exam.repository;

import com.attsw.attsw_exam.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findById(Integer id);

    Optional<Student> findByIdAndStatus(Integer id, Integer status);

    List<Student> findAllByStatus(Integer status);

    List<Student> findAll();

    Optional<Student> findAllByAge(Integer age);

    Optional<Student> findAllByContactNo(String mobileNo);

    Optional<Student> findAllByCollageName(String collageName);



}
