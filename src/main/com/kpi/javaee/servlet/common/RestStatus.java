package com.kpi.javaee.servlet.common;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum RestStatus {

    UPDATED("Successfully Updated"),
    DELETED("Successfully deleted"),
    INSERTED("Successfully inserted");

    private String status;

    @JsonCreator
    RestStatus(@JsonProperty("status") String status) {
        this.status = status;
    }
}
