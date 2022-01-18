package com.umc.plogging.src.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLoginReq {
    private String nickName;
    private String password;
}
