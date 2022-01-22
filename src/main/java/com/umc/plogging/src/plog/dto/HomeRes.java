package com.umc.plogging.src.plog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomeRes {
    private String userImage;
    private String name;
    private String plogSum;
    private String distanceSum;
    private String timeSum;
    private List<Calender> calendar;
}
