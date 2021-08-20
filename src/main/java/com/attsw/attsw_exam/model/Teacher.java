/**
 * Created by: subha_babu
 * Created on: 6/16/2021
 **/
package com.attsw.attsw_exam.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "teacher")
@Getter
@Setter
public class Teacher extends SharedModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "teacher_id")
    private Integer id;

    @Basic
    @Column(name = "teacher_name")
    private String name;

    @Basic
    @Column(name = "teacher_contactNo")
    private String contactNo;

    @Basic
    @Column(name = "teacher_address")
    private String address;

    @Basic
    @Email(message = "Email should be valid")
    @Column(name = "teacher_email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL,targetEntity = Student.class)
    @JoinColumn(name = "teacher_id_fk", referencedColumnName = "teacher_id")
    private List<Student> student;

    public Teacher() {
        super();

    }

}
