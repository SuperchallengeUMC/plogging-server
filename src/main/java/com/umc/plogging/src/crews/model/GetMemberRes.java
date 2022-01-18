package com.umc.plogging.src.crews.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor

public class GetMemberRes {
    public int memberIdx;
    public int crewIdx;
    public int userIdx;
    public Timestamp createdAt;
    public String comment;
    public char isKing;
}
