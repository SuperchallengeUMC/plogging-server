package com.umc.plogging.src.crews.model.crew;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor

public class Crew {
    public int crewIdx;
    public int userIdx;
    public char status;
    public String name;
    public String description;
    public int howmany;
    public Timestamp targetDay;
    public String contact;
}
