package com.umc.plogging.src.plog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetArchive {
    private String nickName;
    private List<String> archiveImg;
}
