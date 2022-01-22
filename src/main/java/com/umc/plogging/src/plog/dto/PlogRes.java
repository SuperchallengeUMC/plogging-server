package com.umc.plogging.src.plog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlogRes {
    private String title;
    private String calorie;
    private String distance;
    private String time;
    private String date;
    private String record;
    private List<String> pictures = new ArrayList<>();

}
