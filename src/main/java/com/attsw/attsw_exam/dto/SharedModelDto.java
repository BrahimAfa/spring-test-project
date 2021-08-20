/**
 * Created by: nuwan_r
 * Created on: 6/19/2021
 **/
package com.attsw.attsw_exam.dto;

import java.util.Date;

public class SharedModelDto {

    private Date createdDate;
    private Date lastModifiedDate;
    private Integer status;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
