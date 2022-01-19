package com.umc.plogging.src.crews.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor

public class PostCrewReq {
    private int userIdx;
    private Timestamp createdAt;
    private String name;
    private String description;
    private int howmany;
    private Timestamp targetDay;
    private String contact;
    private String region;
}
