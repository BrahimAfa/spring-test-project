package com.attsw.attsw_exam.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {

    DELETED(0, "Deleted"),
    OPEN(1, "Open"),
    ACTIVE(2, "Active"),
    INACTIVE(3, "Inacative"),
    SUBMITTED(4, "Submitted"),
    NOT_SUBMITTED(5, "Not submitted");

    private final Integer statusSeq;
    private final String statusName;

    Status(Integer statusSeq, String statusName) {
        this.statusSeq = statusSeq;
        this.statusName = statusName;
    }

    public Integer getStatusSeq() {
        return statusSeq;
    }

    public String getStatusName() {
        return statusName;
    }

    public static Status findOne(Integer statusSeq) {
        return Arrays.stream(Status.values())
                .filter(x -> x.statusSeq.equals(statusSeq))
                .findFirst()
                .orElse(null);
    }

}
