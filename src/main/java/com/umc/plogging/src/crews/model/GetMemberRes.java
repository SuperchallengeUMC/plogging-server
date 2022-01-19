package com.umc.plogging.src.crews.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor

public class GetMemberRes {
    private int memberIdx;
    private int crewIdx;
    private int userIdx;
    private Timestamp createdAt;
    private String comment;
    private char isKing;
}
