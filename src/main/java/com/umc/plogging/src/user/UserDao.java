package com.umc.plogging.src.user;


import com.umc.plogging.src.user.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createUser(PostUserReq postuserReq){
        String createUserQuery = "insert into User (userImage,name, comment ,password) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{
                postuserReq.getUserImage(),postuserReq.getNickName(), postuserReq.getComment(), postuserReq.getPassword()
        };
        this.jdbcTemplate.update(createUserQuery, createUserParams);
    }

    public int checkNickName(String nickName){
        return this.jdbcTemplate.queryForObject("select exists(select name from User where name = ?)",
                int.class,
                nickName);
    }

    public LoginInfo checkNickNameAccount(String nickName){
        return this.jdbcTemplate.queryForObject("select password, userIdx from User where name=?",
                (rs, rowNum) -> new LoginInfo(
                        rs.getString("password"),
                        rs.getInt("userIdx")),
                nickName);
    }
}
