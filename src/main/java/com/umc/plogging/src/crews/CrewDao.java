package com.umc.plogging.src.crews;

import com.umc.plogging.config.BaseException;
import com.umc.plogging.src.crews.model.*;

import com.umc.plogging.utils.JwtService;
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

    // 크루 탈퇴
    /*
    public DeleteCrewRes deleteCrew(int crewIdx){
        String deleteCrewQuery = "delete from Member where crewIdx = ?";
        return this.jdbcTemplate.queryForObject(deleteCrewQuery,
                (rs, rowNum)-> new DeleteCrewRes(
                        rs.getInt("crewIdx")
                ),
                crewIdx);
    }
     */
    
    // 크루 가입
    public int joinCrew(int crewIdx, int userIdxByJwt) throws BaseException {
        String joinCrewQuery = "insert into Member (crewIdx, userIdx, createdAt) VALUES (?,?,NOW())";
        Object[] joinCrewParams = new Object[]{crewIdx, userIdxByJwt};
        this.jdbcTemplate.update(joinCrewQuery, joinCrewParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // Crew 테이블에 존재하는 전체 크루들의 정보 조회
    public List<GetCrewRes> getCrews() {
        String getCrewsQuery = "select C.crewIdx, C.userIdx, C.name, C.targetDay, C.status, C.region, U.userImage\n" +
                "from Crew C\n" +
                "inner join User U where C.userIdx=U.userIdx;"; //Crew 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getCrewsQuery,
                (rs, rowNum) -> new GetCrewRes(
                        rs.getInt("crewIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("status"),
                        rs.getString("name"),
                        rs.getTimestamp("targetDay"),
                        rs.getString("region"),
                        rs.getString("userImage")
                )
        );
    }

    // 해당 region에 속하는 크루들의 정보 조회
    public List<GetCrewRes> getCrewsByRegion(String region) {
        String getCrewsByRegionQuery = "select C.crewIdx, C.userIdx, C.name, C.targetDay, C.status, C.region, U.userImage\n" +
                "from Crew C\n" +
                "inner join User U where C.userIdx=U.userIdx and C.region=?";
        return this.jdbcTemplate.query(getCrewsByRegionQuery,
                (rs, rowNum) -> new GetCrewRes(
                        rs.getInt("crewIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("status"),
                        rs.getString("name"),
                        rs.getTimestamp("targetDay"),
                        rs.getString("region"),
                        rs.getString("userImage")
                ),
                region);
    }

    // 크루 탈퇴
    /*
    public DeleteCrewRes deleteCrew(int crewIdx){
        String deleteCrewQuery = "delete from Member where crewIdx = ?";
        return this.jdbcTemplate.queryForObject(deleteCrewQuery,
                (rs, rowNum)-> new DeleteCrewRes(
                        rs.getInt("crewIdx")
                ),
                crewIdx);
    }
     */
}
