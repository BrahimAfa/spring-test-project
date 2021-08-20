/**
 * Created by: subha_babu
 * Created on: 6/16/2021
 **/
package com.attsw.attsw_exam.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto extends SharedModelDto {

    private Integer id;

    private String name;

    private Integer age;

    private String collageName;

    private String contactNo;


}
