package com.umc.plogging.src.crews.model.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor

public class GetMemberRes {
    private int memberIdx;
    private int userIdx;
    private String name;
    private String comment;
    private String userImage;
    private String isKing;
}
