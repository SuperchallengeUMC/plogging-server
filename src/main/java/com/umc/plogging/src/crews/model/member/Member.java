package com.umc.plogging.src.crews.model.member;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor

public class Member {
    public int memberIdx;
    public int crewIdx;
    public int userIdx;
    public Timestamp createdAt;
    public String comment;
    public char isKing;
}
