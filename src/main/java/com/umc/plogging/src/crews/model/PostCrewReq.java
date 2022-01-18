package com.umc.plogging.src.crews.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor

public class PostCrewReq {
    public int userIdx;
    public Timestamp createdAt;
    public String name;
    public String description;
    public int howmany;
    public Timestamp targetDay;
    public String contact;
    public String region;
}
