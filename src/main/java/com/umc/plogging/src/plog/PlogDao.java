package com.umc.plogging.src.plog;

import com.umc.plogging.src.plog.dto.Calender;
import com.umc.plogging.src.user.dto.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Repository
public class PlogDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String getName(int userIdx){
        return this.jdbcTemplate.queryForObject("select name from User where userIdx = ?",String.class,userIdx);
    }
    public String getUserImage(int userIdx){
        return this.jdbcTemplate.queryForObject("select userImage from User where userIdx = ?",String.class,userIdx);
    }
    public List<Timestamp> getStart(int userIdx){
        List<Timestamp> startTime;
        startTime = (this.jdbcTemplate.queryForList("select startTime from Plog where userIdx=?",Timestamp.class
                ,userIdx));
        return startTime;
    }

    public Timestamp getStartByPlogIdx(int plogIdx){
        return this.jdbcTemplate.queryForObject("select startTime from Plog where plogIdx=?",Timestamp.class
                ,plogIdx);
    }

    public List<Timestamp> getEnd(int userIdx){
        List<Timestamp> endTime;
        endTime = (this.jdbcTemplate.queryForList("select endTime from Plog where userIdx=?",Timestamp.class
                ,userIdx));
        return endTime;
    }

    public Timestamp getEndByPlogIdx(int plogIdx){
        return this.jdbcTemplate.queryForObject("select endTime from Plog where plogIdx=?",Timestamp.class
                ,plogIdx);
    }

    public List<String> getDistance(int userIdx){
        List<String> distance;
        distance = (this.jdbcTemplate.queryForList("select distance from Plog where userIdx=?",String.class
                ,userIdx));
        return distance;
    }
    public String getDistanceByPlogIdx(int plogIdx){
        return this.jdbcTemplate.queryForObject("select distance from Plog where plogIdx=?",String.class
                ,plogIdx);
    }


    public List<Calender> getCalender(int userIdx){
        return this.jdbcTemplate.query("select startTime, plogIdx from Plog where userIdx=?",
                (rs, rowNum) -> new Calender(
                        rs.getInt("plogIdx"),
                        rs.getTimestamp("startTime")),
                userIdx);
    }

    public String getPlogImage(int plogIdx){
        return this.jdbcTemplate.queryForObject("select archiving from Plog where plogIdx=?",
                String.class,
                plogIdx);
    }

    public int checkPlog(int plogIdx){
        return this.jdbcTemplate.queryForObject("select exists(select plogIdx from Plog where plogIdx=?)",
                Integer.class,
                plogIdx);
    }

}
