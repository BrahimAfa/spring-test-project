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
import java.io.Serializable;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "student")
public class    Student extends SharedModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "student_id")
    private Integer id;

    @Basic
    @Column(name = "student_name")
    private String name;

    @Basic
    @Column(name = "student_age")
    private Integer age;

    @Basic
    @Column(name = "student_collage_name")
    private String collageName;

    @Basic
    @Column(name = "student_contact_no")
    private String contactNo;

    public Student() {
        super();
    }

}
