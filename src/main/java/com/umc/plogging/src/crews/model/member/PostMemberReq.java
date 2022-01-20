package com.umc.plogging.src.crews.model.member;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PostMemberReq {
    private int crewIdx;
    private int userIdx;
}

