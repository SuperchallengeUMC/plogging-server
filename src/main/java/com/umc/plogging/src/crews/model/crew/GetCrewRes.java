package com.umc.plogging.src.crews.model.crew;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor

public class GetCrewRes {
    private int crewIdx;
    private String status;
    private String name;
    private Timestamp targetDay;
    private String region;
    private String description;
    private String userImage;
}

