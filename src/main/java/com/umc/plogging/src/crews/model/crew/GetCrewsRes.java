package com.umc.plogging.src.crews.model.crew;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor

public class GetCrewsRes {
    private int crewIdx;
    private char status;
    private String name;
    private Timestamp targetDay;
    private String region;
    private int howmany;
    private int currentNum;
    private String userImage;
}
