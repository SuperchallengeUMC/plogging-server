package com.umc.plogging.src.plog;

import com.umc.plogging.src.plog.dto.Calender;
import com.umc.plogging.src.plog.dto.PostPlogReq;
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

    public List<String> getEnd(int userIdx){
        List<String> endTime;
        endTime = (this.jdbcTemplate.queryForList("select endTime from Plog where userIdx=?",String.class
                ,userIdx));
        return endTime;
    }

    public String getEndByPlogIdx(int plogIdx){
        return this.jdbcTemplate.queryForObject("select endTime from Plog where plogIdx=?",String.class
                ,plogIdx);
    }

    public List<String> getDistance(int userIdx){
        List<String> distance;
        distance = (this.jdbcTemplate.queryForList("select distance from Plog where userIdx=?",String.class
                ,userIdx));
        return distance;
    }

    public List<String> getArchiveImg(int userIdx){
        List<String> archiving;
        archiving = (this.jdbcTemplate.queryForList("select archiving from Plog where userIdx=?",String.class
                ,userIdx));
        return archiving;
    }
    public String getDistanceByPlogIdx(int plogIdx){
        return this.jdbcTemplate.queryForObject("select distance from Plog where plogIdx=?",String.class
                ,plogIdx);
    }

    public String getCalorieByPlogIdx(int plogIdx){
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

    public List<String> getPlogImage(int plogIdx){
        List<String> pictures = new ArrayList<>();
        String picture =  this.jdbcTemplate.queryForObject("select picture from Plog where plogIdx=?",
                String.class,
                plogIdx);
        String archiveImg = this.jdbcTemplate.queryForObject("select archiving from Plog where plogIdx=?",
                String.class,
                plogIdx);
        pictures.add(picture);
        pictures.add(archiveImg);
        return pictures;
    }

    public int checkPlog(int plogIdx){
        return this.jdbcTemplate.queryForObject("select exists(select plogIdx from Plog where plogIdx=?)",
                Integer.class,
                plogIdx);
    }

    public void postPlog(int userIdx, PostPlogReq postPlogReq){
        String createUserQuery = "insert into Plog (userIdx,distance, calorie ,startTime,endTime,archiving,picture) VALUES (?,?,?,now(),?,?,?)";
        Object[] createUserParams = new Object[]{
                userIdx,postPlogReq.getDistance(), postPlogReq.getCalorie(),postPlogReq.getTime() ,postPlogReq.getPictures().get(0),postPlogReq.getPictures().get(1)
        };
        this.jdbcTemplate.update(createUserQuery, createUserParams);
    }

}
