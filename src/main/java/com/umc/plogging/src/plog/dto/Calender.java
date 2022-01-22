package com.umc.plogging.src.plog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
public class Calender {
    private int plogIdx;
    private Timestamp date;
}
