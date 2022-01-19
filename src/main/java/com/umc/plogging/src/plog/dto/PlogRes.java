package com.umc.plogging.src.plog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlogRes {
    private String date;
    private String archiveImg;
    private String record;
}
