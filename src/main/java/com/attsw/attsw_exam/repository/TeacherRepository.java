/**
 * Created by: subha_babu
 * Created on: 6/16/2021
 */
package com.attsw.attsw_exam.repository;

import com.attsw.attsw_exam.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Optional<Teacher> findById(Integer id);

    Optional<Teacher> findByIdAndStatus(Integer id, Integer status);

    List<Teacher> findAllByStatus(Integer status);

    Optional<Teacher> findByEmailAndStatus(String email, Integer status);

    Optional<Teacher> findByEmail(String email);

    List<Teacher> findAll();

}
