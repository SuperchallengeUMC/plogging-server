package com.umc.plogging.src.crews.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor

public class GetCrewRes {
    public int crewIdx;
    public int userIdx;
    public String status;
    public String name;
    public Timestamp targetDay;
    public String region;
    public String userImage;
}
