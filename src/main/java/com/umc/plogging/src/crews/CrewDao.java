package com.umc.plogging.src.crews;

import com.umc.plogging.config.BaseException;

import com.umc.plogging.src.crews.model.crew.*;
import com.umc.plogging.src.crews.model.member.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.sql.DataSource;

@Repository
public class CrewDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 크루 생성
    public int createCrew(PostCrewReq postCrewReq) {
        String createCrewQuery = "insert into Crew (userIdx, createdAt, name, description, howmany, targetDay, contact, region) VALUES (?,NOW(),?,?,?,?,?,?)";
        Object[] createCrewParams = new Object[]{postCrewReq.getUserIdx(), postCrewReq.getName(), postCrewReq.getDescription(), postCrewReq.getHowmany(), postCrewReq.getTargetDay(), postCrewReq.getContact(), postCrewReq.getRegion()};
        this.jdbcTemplate.update(createCrewQuery, createCrewParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }
    
    // 크루 가입
    public int joinCrew(int crewIdx, int userIdxByJwt) throws BaseException {
        String joinCrewQuery = "insert into Member (crewIdx, userIdx, createdAt) VALUES (?,?,NOW())";
        Object[] joinCrewParams = new Object[]{crewIdx, userIdxByJwt};
        this.jdbcTemplate.update(joinCrewQuery, joinCrewParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // Crew 테이블에 존재하는 전체 크루들의 정보 조회
    public List<GetCrewsRes> getCrews() {
        String getCrewsQuery = "select C.crewIdx, C.name, C.targetDay, C.status, C.region, U.userImage\n" +
                "from Crew C\n" +
                "inner join User U where C.userIdx=U.userIdx;"; //Crew 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getCrewsQuery,
                (rs, rowNum) -> new GetCrewsRes(
                        rs.getInt("crewIdx"),
                        rs.getString("status").charAt(0),
                        rs.getString("name"),
                        rs.getTimestamp("targetDay"),
                        rs.getString("region"),
                        rs.getString("userImage")
                )
        );
    }

    public GetCrewRes getCrew(int crewIdx) {
        String getCrewQuery = "select C.crewIdx, C.name, C.targetDay, C.status, C.region, C.description, U.userImage\n" +
                "from Crew C\n" +
                "inner join User U where C.userIdx=U.userIdx and crewIdx=?";
        return this.jdbcTemplate.queryForObject(getCrewQuery,
                (rs, rowNum) -> new GetCrewRes(
                        rs.getInt("crewIdx"),
                        rs.getString("status"),
                        rs.getString("name"),
                        rs.getTimestamp("targetDay"),
                        rs.getString("region"),
                        rs.getString("description"),
                        rs.getString("userImage")
                ),
                crewIdx);
    }

    // 해당 region에 속하는 크루들의 정보 조회
    public List<GetCrewsRes> getCrewsByRegion(String region) {
        String getCrewsByRegionQuery = "select C.crewIdx, C.name, C.targetDay, C.status, C.region, U.userImage\n" +
                "from Crew C\n" +
                "inner join User U where C.userIdx=U.userIdx and C.region=?";
        return this.jdbcTemplate.query(getCrewsByRegionQuery,
                (rs, rowNum) -> new GetCrewsRes(
                        rs.getInt("crewIdx"),
                        rs.getString("status").charAt(0),
                        rs.getString("name"),
                        rs.getTimestamp("targetDay"),
                        rs.getString("region"),
                        rs.getString("userImage")
                ),
                region);
    }

    // 가입한 크루들의 정보 조회
    public List<GetCrewsRes> getCrewsByStatus(char status, int userIdxByJwt) {
        String getActiveCrewsQuery = "select C.crewIdx, C.status, C.name, C.targetDay, C.region, U.userImage\n" +
                "from Crew C\n" +
                "inner join Member M\n" +
                "    on C.crewIdx=M.crewIdx\n" +
                "inner join User U\n" +
                "    on M.userIdx=U.userIdx\n" +
                "where M.userIdx=? and timestampdiff(minute, NOW(), C.targetDay) >= 0";

        String getDoneCrewsQuery = "select C.crewIdx, C.status, C.name, C.targetDay C.region, U.userImage\n" +
                "from Crew C\n" +
                "inner join Member M\n" +
                "    on C.crewIdx=M.crewIdx\n" +
                "inner join User U\n" +
                "    on M.userIdx=U.userIdx\n" +
                "where M.userIdx=? and timestampdiff(minute, NOW(), C.targetDay) < 0;";

        if(status=="T".charAt(0)) {
            return this.jdbcTemplate.query(getActiveCrewsQuery,
                    (rs, rowNum) -> new GetCrewsRes(
                            rs.getInt("crewIdx"),
                            rs.getString("status").charAt(0),
                            rs.getString("name"),
                            rs.getTimestamp("targetDay"),
                            rs.getString("region"),
                            rs.getString("userImage")
                    ),
                    userIdxByJwt);
        }
        return this.jdbcTemplate.query(getDoneCrewsQuery,
                (rs, rowNum) -> new GetCrewsRes(
                        rs.getInt("crewIdx"),
                        rs.getString("status").charAt(0),
                        rs.getString("name"),
                        rs.getTimestamp("targetDay"),
                        rs.getString("region"),
                        rs.getString("userImage")
                ),
                userIdxByJwt);
    }

    // 크루에 가입한 유저 조회
    public List<GetMemberRes> getMembers(int crewIdx) {
        String getMembersQuery = "select M.crewIdx, M.userIdx, U.name, U.comment, U.userImage, M.isKing\n" +
                "from Member M\n" +
                "inner join User U\n" +
                "on M.userIdx = U.userIdx\n" +
                "where crewIdx=?";
        return this.jdbcTemplate.query(getMembersQuery,
                (rs, rowNum) -> new GetMemberRes(
                        rs.getInt("crewIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("comment"),
                        rs.getString("userImage"),
                        rs.getString("isKing")

                )
                , crewIdx
        );
    }

    // 크루 탈퇴 (미완성)
    public int deleteMember(int crewIdx, int userIdx){
        String deleteMemberQuery = "delete from Member where crewIdx = ? and userIdx=?";
        Object[] deleteMemberParams = new Object[]{crewIdx, userIdx};
        return this.jdbcTemplate.update(deleteMemberQuery, deleteMemberParams); // 성공 - 1, 실패 - 0
    }
}
