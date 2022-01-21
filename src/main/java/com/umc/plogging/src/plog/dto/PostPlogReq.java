package com.umc.plogging.src.plog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPlogReq {
    private String calorie;
    private String distance;
    private String time;
    private List<String> pictures;
}
