package com.umc.plogging.src.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String userImage;
    private String nickName;
    private String password;
    private String comment;
}
